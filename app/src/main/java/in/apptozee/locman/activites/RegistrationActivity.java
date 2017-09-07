package in.apptozee.locman.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.apptozee.locman.R;
import in.apptozee.locman.database.DBHelper;
import in.apptozee.locman.model.UserModel;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    private EditText nameEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private EditText conformPasswordEt;
    private EditText mobileNoEt;
    private EditText emailEt;
    private Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViews();
    }

    @Override
    public void onClick(View v) {
        if (v == signUpBtn) {
            String nameStr = nameEt.getText().toString();
            String usernameStr = usernameEt.getText().toString();
            String passwordStr = passwordEt.getText().toString();
            String conformPasswordStr = conformPasswordEt.getText().toString();
            String mobileStr = mobileNoEt.getText().toString();
            String emailStr = emailEt.getText().toString();

            /**
             * inserting the data in a model which is encapusulation
             * by setting the data to the object and data is binded into
             * the model object
             */
            UserModel mUserModel = new UserModel();
            mUserModel.setFullName(nameStr);
            mUserModel.setUsername(usernameStr);
            mUserModel.setPassword(passwordStr);
            mUserModel.setMobileNo(mobileStr);
            mUserModel.setEmail(emailStr);

            /**
             * creating the object for DBHelper to use the methods of database files
             */
            DBHelper mDBDbHelper = new DBHelper(RegistrationActivity.this);
            /**
             * calling the insertUser with the help of object of DBHelper
             * with a parameter userModel to the method which is used to
             * get the data from the model object for manipulations in DBHelper class
             */
            mDBDbHelper.insertUser(mUserModel);
        }
    }


    private void findViews() {
        nameEt = (EditText) findViewById(R.id.name_et);
        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        conformPasswordEt = (EditText) findViewById(R.id.conform_password_et);
        mobileNoEt = (EditText) findViewById(R.id.mobile_no_et);
        emailEt = (EditText) findViewById(R.id.email_et);
        signUpBtn = (Button) findViewById(R.id.signup_btn);

        signUpBtn.setOnClickListener(this);
    }
}
