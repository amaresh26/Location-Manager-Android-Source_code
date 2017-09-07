package in.apptozee.locman.model;

import java.io.Serializable;

/**
 * Created by amareshjana on 04/04/17.
 */

public class LocationModel implements Serializable {

    private String fullName;
    private String mobile_no;
    private String address;
    private String email;
    private String latitude;
    private String longitude;
    private String landmark;
    private String user_img;
    private String placeName;

    public LocationModel(String fullName, String mobile_no, String address, String email, String latitude,
                         String longitude, String landmark, String user_img, String placeName) {
        this.fullName = fullName;
        this.mobile_no = mobile_no;
        this.address = address;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.landmark = landmark;
        this.user_img = user_img;
        this.placeName = placeName;
    }

    public LocationModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
