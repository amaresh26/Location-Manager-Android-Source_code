package in.apptozee.locman.model;

/**
 * Created by amareshjana on 27/03/17.
 */

public class UserModel {

    private String username ="";
    private String password ="";
    private String mobileNo ="";
    private String email ="";
    private String fullName ="";

    public UserModel(){}

    public UserModel(String username, String password, String mobileNo, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
