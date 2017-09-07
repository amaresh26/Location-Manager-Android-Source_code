package in.apptozee.locman.activites;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.apptozee.locman.R;
import in.apptozee.locman.database.DBHelper;
import in.apptozee.locman.model.LocationModel;

/**
 * Created by amareshjana on 04/04/17.
 */

public class AddLocation extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView userImgIv;
    private ImageView getAddressIv;
    private EditText regNameEt;
    private EditText locationNameEt;
    private EditText regMobileEt;
    private EditText regAddressEt;
    private EditText regEmailEt;
    private EditText regLandmarkEt;
    private Button submitBtn;
    private String address = "";
    String imageStr = "";

    private Location currentLocation;
    LatLng markerPosition;
    private double currentLatitude;
    private double currentLongitude;
    String latitude = "", longitude = "";
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        setToolbar("Add Location", 1);
        findViews();
        locationServices();
    }

    private void findViews() {
        userImgIv = (ImageView) findViewById(R.id.user_img_iv);
        getAddressIv = (ImageView) findViewById(R.id.get_address_iv);
        regNameEt = (EditText) findViewById(R.id.reg_name_et);
        regMobileEt = (EditText) findViewById(R.id.reg_mobile_et);
        regAddressEt = (EditText) findViewById(R.id.reg_address_et);
        regEmailEt = (EditText) findViewById(R.id.reg_email_et);
        locationNameEt = (EditText) findViewById(R.id.reg_location_name_et);
        regLandmarkEt = (EditText) findViewById(R.id.reg_landmark_et);
        submitBtn = (Button) findViewById(R.id.submit_btn);

        userImgIv.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        getAddressIv.setOnClickListener(this);
    }

    private void locationServices() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(AddLocation.this).build();
            mGoogleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(AddLocation.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mIntent = new Intent(AddLocation.this, DashboardActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == userImgIv) {
            //take to gallary or cemara to ge tthe images of the user
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        } else if (v == submitBtn) {
            String username = regNameEt.getText().toString();
            String mobile = regMobileEt.getText().toString();
            String address = regAddressEt.getText().toString();
            String email = regEmailEt.getText().toString();
            String landmark = regLandmarkEt.getText().toString();
            String locationName = locationNameEt.getText().toString();

            if (username.equals("")) {
                regNameEt.setError("Enter the value");
                regNameEt.requestFocus();
                return;
            } else if (mobile.equals("")) {
                regMobileEt.setError("Enter the value");
                regMobileEt.requestFocus();
                return;
            } else if (address.equals("")) {
                regAddressEt.setError("Enter the value");
                regAddressEt.requestFocus();
                return;
            } else if (email.equals("")) {
                regEmailEt.setError("Enter the value");
                regEmailEt.requestFocus();
                return;
            } else if (landmark.equals("")) {
                regLandmarkEt.setError("Enter the value");
                regLandmarkEt.requestFocus();
                return;
            } else if (locationName.equals("")) {
                locationNameEt.setError("Enter the value");
                locationNameEt.requestFocus();
                return;
            }

            LocationModel mLocationModel = new LocationModel();
            mLocationModel.setFullName(username);
            mLocationModel.setMobile_no(mobile);
            mLocationModel.setAddress(address);
            mLocationModel.setEmail(email);
            mLocationModel.setLandmark(landmark);
            mLocationModel.setPlaceName(locationName);
            mLocationModel.setLatitude(latitude);
            mLocationModel.setLongitude(longitude);
            mLocationModel.setUser_img(imageStr);

            DBHelper mDbHelper = new DBHelper(AddLocation.this);
            long id = mDbHelper.insertLocation(mLocationModel);
            if (id > 0) {
                makeToast("Inserted", 1);
                Intent mIntent = new Intent(AddLocation.this, DashboardActivity.class);
                startActivity(mIntent);
                finish();
            }
        } else if (v == getAddressIv) {
            // to get the address of the location
            LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            currentLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                if (currentLocation == null)
                    currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (markerPosition == null) {
                    markerPosition = new LatLng(currentLatitude, currentLongitude);
                    try {
                        latitude = String.valueOf(markerPosition.latitude);
                        longitude = String.valueOf(markerPosition.longitude);
                        testing(markerPosition.latitude, markerPosition.longitude);
                        regAddressEt.setText(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                        makeToast("Please enter the location", 1);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                makeToast("Please enter the location", 1);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageStr = BitMapToString(photo);
            userImgIv.setImageBitmap(photo);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }

    private void testing(double lat, double lon) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
        String add = addresses.get(0).getAddressLine(0) + addresses.get(0).getAddressLine(1) + addresses.get(0).getAddressLine(2) + " , " + addresses.get(0).getAddressLine(3);
//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String local = addresses.get(0).getSubLocality();
//        String postal = addresses.get(0).getPostalCode();
//        address = add + " " + local + " " + city + " " + state + " " + postalpostal;
        address = add;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
