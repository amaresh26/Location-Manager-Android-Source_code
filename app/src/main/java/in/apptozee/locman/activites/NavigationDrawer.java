package in.apptozee.locman.activites;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.apptozee.locman.R;


/**
 * Created by amareshjana on 31/08/16.
 */
public class NavigationDrawer extends BaseActivity {

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        NavigationView navigationView = (NavigationView) fullView.findViewById(R.id.navigationView);
        LinearLayout lay = (LinearLayout) navigationView.getHeaderView(0);
        TextView navHeaderTitle = (TextView) lay.findViewById(R.id.nav_name);
        navHeaderTitle.setText("Amaresh Jana");

        TextView navHeaderSubTitle = (TextView) lay.findViewById(R.id.nav_mobile);
        navHeaderSubTitle.setText("+91-9493111157");

        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (useToolbar()) {
            setSupportActionBar(toolbar);
            setTitle("Dashboard");
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    protected boolean useToolbar() {
        return true;
    }
}
