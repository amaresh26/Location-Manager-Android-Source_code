package in.apptozee.locman.activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import in.apptozee.locman.R;
import in.apptozee.locman.model.LocationModel;

/**
 * Created by amareshjana on 05/04/17.
 */

public class LocationDetailsActivity extends BaseActivity {

    private ImageView userImgIv;
    private EditText nameTv;
    private EditText mobileTv;
    private EditText addressTv;
    private ImageView drivingIv;
    private EditText emailTv;
    private EditText locationNameTv;
    private EditText landmarkTv;
    private String latitude = "", longitude = "";

    private void findViews() {
        userImgIv = (ImageView) findViewById(R.id.user_img_iv);
        nameTv = (EditText) findViewById(R.id.name_tv);
        mobileTv = (EditText) findViewById(R.id.mobile_tv);
        addressTv = (EditText) findViewById(R.id.address_tv);
        drivingIv = (ImageView) findViewById(R.id.driving_iv);
        emailTv = (EditText) findViewById(R.id.email_tv);
        locationNameTv = (EditText) findViewById(R.id.location_name_tv);
        landmarkTv = (EditText) findViewById(R.id.landmark_tv);

        drivingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + latitude + "," + longitude));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        setToolbar("Location Details", 1);
        findViews();
        //making the editText not editable
        editableFeilds(false);

        /**
         * get the object from the intent and setting the data
         * for the layout if null data cant be set
         */
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            LocationModel mLocationModel = (LocationModel) mBundle.getSerializable("LocationModel");
            //setting the data to the layout by using this method
            setDataToLayout(mLocationModel);
        } else {
            makeToast("Something went wrong!!", 1);
        }
    }

    private void editableFeilds(boolean b) {
        nameTv.setEnabled(b);
        mobileTv.setEnabled(b);
        addressTv.setEnabled(b);
        emailTv.setEnabled(b);
        locationNameTv.setEnabled(b);
        landmarkTv.setEnabled(b);

    }

    /**
     * @param mLocationModel serializable object where all the information is
     *                       encapsulated inside the object
     *                       setting the data correspondingly
     */
    private void setDataToLayout(LocationModel mLocationModel) {
        nameTv.setText(mLocationModel.getFullName());
        mobileTv.setText(mLocationModel.getMobile_no());
        addressTv.setText(mLocationModel.getAddress());
        emailTv.setText(mLocationModel.getEmail());
        locationNameTv.setText(mLocationModel.getPlaceName());
        landmarkTv.setText(mLocationModel.getLandmark());
        userImgIv.setImageBitmap(StringToBitMap(mLocationModel.getUser_img()));
        latitude = mLocationModel.getLatitude();
        longitude = mLocationModel.getLongitude();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent mIntent = new Intent(LocationDetailsActivity.this, DashboardActivity.class);
            startActivity(mIntent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(LocationDetailsActivity.this, DashboardActivity.class);
        startActivity(mIntent);
        finish();
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
