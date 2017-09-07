package in.apptozee.locman.activites;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.apptozee.locman.R;
import in.apptozee.locman.utiles.Permissions;
import in.apptozee.locman.utiles.SessionManager;

/**
 * Created by amareshjana on 28/03/17.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;
    private TextView registerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Permissions permissions = new Permissions(LoginActivity.this);
        permissions.requestPermissionsAllPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE});
        findViews();
    }

    /**
     * this is used for identifying the view in the layout and
     * button onclick
     */
    private void findViews() {
        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerTv = (TextView) findViewById(R.id.register_tv);

        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    /**
     * all the button wills will be handled here
     * this is an overridden method by implements OnClickListener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();

            if (username.equals("admin") && password.equals("password")) {
                SessionManager mSessionManager = new SessionManager(LoginActivity.this);
                mSessionManager.setUSERNAME(username);
                mSessionManager.setPASSWORD(password);
                Intent mLoginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(mLoginIntent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Username and Password is wrong", Toast.LENGTH_LONG).show();
            }
        } else if (v == registerTv) {
            Intent mLoginIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(mLoginIntent);
        }
    }
}
