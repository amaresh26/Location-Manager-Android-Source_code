package in.apptozee.locman.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import in.apptozee.locman.R;

/**
 * Created by amareshjana on 28/03/17.
 */

public class BaseActivity extends AppCompatActivity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
    }

    public void makeToast(String msg, int length) {
        if (length == 0)
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void nextIntent(Class<?> nextClass) {
        Intent nextIntent = new Intent(mContext, nextClass);
        startActivity(nextIntent);
    }

    public void setToolbar(String name, int withBack) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (withBack == 1)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolBarTitle.setText(name);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void showNoNetworkDialogue(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        System.exit(0);
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
