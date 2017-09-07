package in.apptozee.locman.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import in.apptozee.locman.R;
import in.apptozee.locman.utiles.SessionManager;

public class SplashActivity extends AppCompatActivity {

    CountDownTimer counter;
    private static int SPLASH_TIME_OUT = 1000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(SplashActivity.this);

        startTimer();
        counter.start();
    }

    //here we are making this class to wait for SPLASH_TIME_OUT given globally
    private void startTimer() {
        counter = new CountDownTimer(SPLASH_TIME_OUT, 500) {
            @Override
            public void onFinish() {

                if (sessionManager.getUSERNAME().equals("")) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
            }
        };
    }
}