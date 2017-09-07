package in.apptozee.locman.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import in.apptozee.locman.model.LocationModel;
import in.apptozee.locman.model.UserModel;

/**
 * Created by amareshjana on 28/03/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "locadd.db";
    private static final int DB_VERSION = 1;
    SQLiteDatabase db;

    //user table fields
    private final String USER_TABLE = "user_table";
    private final String USER_ID = "user_id";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String MOBILE_NO = "mobile_no";
    private final String EMAIL = "email";
    private final String FULL_NAME = "full_name";

    //queries for creating tables
    private final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + USERNAME + " VARCHAR NOT NULL, "
            + PASSWORD + " VARCHAR NOT NULL, "
            + MOBILE_NO + " TEXT NOT NULL, "
            + EMAIL + " TEXT NOT NULL, "
            + FULL_NAME + " TEXT NOT NULL);";

    private final String LOCATION_TABLE = "location_table";
    private final String USER_IMG = "user_img";
    private final String LOCATION_ID = "location_id";
    private final String ADDRESS = "address";
    private final String LANDMARK = "land_mark";
    private final String LATITUDE = "latitude";
    private final String LONGITUDE = "longitude";
    private final String PLACE_NAME = "place_name";

    private final String CREATE_LOCATION_TABLE = "CREATE TABLE " + LOCATION_TABLE + "("
            + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + FULL_NAME + " VARCHAR NOT NULL, "
            + USER_IMG + " TEXT NOT NULL, "
            + MOBILE_NO + " TEXT NOT NULL, "
            + PLACE_NAME + " TEXT NOT NULL, "
            + EMAIL + " TEXT NOT NULL, "
            + LONGITUDE + " TEXT NOT NULL, "
            + LATITUDE + " TEXT NOT NULL, "
            + ADDRESS + " TEXT NOT NULL, "
            + LANDMARK + " TEXT NOT NULL);";

    public DBHelper(Context mContext) {
        super(mContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //insertion of data into database will be done here
    public long insertUser(UserModel mUserModel) {
        long id = 0;
        db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(USERNAME, mUserModel.getUsername());
            cv.put(PASSWORD, mUserModel.getPassword());
            cv.put(MOBILE_NO, mUserModel.getMobileNo());
            cv.put(EMAIL, mUserModel.getEmail());
            cv.put(FULL_NAME, mUserModel.getFullName());

            id = db.insert(USER_TABLE, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        } finally {
            if (db != null)
                db.close();
        }
        return id;
    }

    //insertion of data into database will be done here
    public long insertLocation(LocationModel mLocationModel) {
        long id = 0;
        db = getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(FULL_NAME, mLocationModel.getFullName());
            cv.put(USER_IMG, mLocationModel.getUser_img());
            cv.put(MOBILE_NO, mLocationModel.getMobile_no());
            cv.put(EMAIL, mLocationModel.getEmail());
            cv.put(LATITUDE, mLocationModel.getLatitude());
            cv.put(PLACE_NAME, mLocationModel.getPlaceName());
            cv.put(LONGITUDE, mLocationModel.getLongitude());
            cv.put(ADDRESS, mLocationModel.getAddress());
            cv.put(LANDMARK, mLocationModel.getLandmark());

            id = db.insert(LOCATION_TABLE, null, cv);
            Log.e("Inserted id", id + "");
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        } finally {
            if (db != null)
                db.close();
        }
        return id;
    }

    public ArrayList<String> getLocationUserNames() {
        ArrayList<String> mLocationNames = new ArrayList<>();
        db = getReadableDatabase();
        Cursor mCursor;
        String query = "Select * from " + LOCATION_TABLE;
        mCursor = db.rawQuery(query, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    mLocationNames.add(mCursor.getString(mCursor.getColumnIndex(FULL_NAME)));
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
            if (mCursor != null)
                mCursor.close();
        }
        return mLocationNames;
    }

    //used to check the user is present or not in the db

    public LocationModel getLocationDetails(String fullName) {
        LocationModel mLocationModel = new LocationModel();
        db = getWritableDatabase();
        Cursor cursor = null;
        try {
            String Query = "Select * from " + LOCATION_TABLE + " where " + FULL_NAME + " = ?";
            cursor = db.rawQuery(Query, new String[]{fullName});
            if (cursor.moveToFirst()) {
                do {
                    mLocationModel.setFullName(cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                    mLocationModel.setUser_img(cursor.getString(cursor.getColumnIndex(USER_IMG)));
                    mLocationModel.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    mLocationModel.setMobile_no(cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
                    mLocationModel.setLongitude(cursor.getString(cursor.getColumnIndex(LONGITUDE)));
                    mLocationModel.setLatitude(cursor.getString(cursor.getColumnIndex(LATITUDE)));
                    mLocationModel.setLandmark(cursor.getString(cursor.getColumnIndex(LANDMARK)));
                    mLocationModel.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    mLocationModel.setPlaceName(cursor.getString(cursor.getColumnIndex(PLACE_NAME)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
        return mLocationModel;
    }

    public ArrayList<LocationModel> getAllLocation() {
        ArrayList<LocationModel> mArrayLocation = new ArrayList<>();
        LocationModel mLocationModel;
        db = getReadableDatabase();
        Cursor cursor = null;
        try {
            String Query = "Select * from " + LOCATION_TABLE;
            cursor = db.rawQuery(Query, null);
            if (cursor.moveToFirst()) {
                do {
                    mLocationModel = new LocationModel();
                    mLocationModel.setFullName(cursor.getString(cursor.getColumnIndex(FULL_NAME)));
                    mLocationModel.setUser_img(cursor.getString(cursor.getColumnIndex(USER_IMG)));
                    mLocationModel.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    mLocationModel.setMobile_no(cursor.getString(cursor.getColumnIndex(MOBILE_NO)));
                    mLocationModel.setLongitude(cursor.getString(cursor.getColumnIndex(LONGITUDE)));
                    mLocationModel.setLatitude(cursor.getString(cursor.getColumnIndex(LATITUDE)));
                    mLocationModel.setLandmark(cursor.getString(cursor.getColumnIndex(LANDMARK)));
                    mLocationModel.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    mLocationModel.setPlaceName(cursor.getString(cursor.getColumnIndex(PLACE_NAME)));
                    mArrayLocation.add(mLocationModel);
                } while (cursor.moveToNext());
            }

        } catch (
                Exception e)

        {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
            e.printStackTrace();
        } finally

        {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }

        return mArrayLocation;
    }
}
