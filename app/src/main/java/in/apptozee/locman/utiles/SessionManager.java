package in.apptozee.locman.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amareshjana on 28/03/17.
 */

public class SessionManager {

    private static final String SHARED_PREFERENCE = "in.apptozee.locadd";

    private static SharedPreferences mPreference;
    private static SharedPreferences.Editor mEditor;

    private static String FULL_NAME = "full_name";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";


    public SessionManager(Context mContext) {
        mPreference = mContext.getSharedPreferences(SHARED_PREFERENCE, Activity.MODE_PRIVATE);
        mEditor = mPreference.edit();
    }

    public String getFullName() {
        return mPreference.getString(FULL_NAME, "");
    }

    public void setFullName(String fullName) {
        mEditor.putString(FULL_NAME, fullName);
        mEditor.commit();
    }

    public void setUSERNAME(String username) {
        mEditor.putString(USERNAME, username);
        mEditor.commit();
    }

    public String getUSERNAME() {
        return mPreference.getString(USERNAME, "");
    }

    public void setPASSWORD(String password) {
        mEditor.putString(PASSWORD, password);
        mEditor.commit();
    }

    public String getPASSWORD() {
        return mPreference.getString(PASSWORD, "");
    }
}