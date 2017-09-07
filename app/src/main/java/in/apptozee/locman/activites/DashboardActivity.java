package in.apptozee.locman.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import in.apptozee.locman.R;
import in.apptozee.locman.database.DBHelper;
import in.apptozee.locman.model.LocationModel;
import in.apptozee.locman.utiles.SessionManager;

public class DashboardActivity extends NavigationDrawer {

    ListView addressList;
    DBHelper mDbHelper;
    private Toolbar toolbar;
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //this is to set the toolbar
//        setToolbar("Dashboard", 0);
        setNavigationMenu();
        //finding view
        addressList = (ListView) findViewById(R.id.address_list);
        //getting the data form db
        mDbHelper = new DBHelper(DashboardActivity.this);
        final ArrayList<String> mArrayNames = mDbHelper.getLocationUserNames();

        ArrayAdapter mAdapter = new ArrayAdapter(DashboardActivity.this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1, mArrayNames);
        addressList.setAdapter(mAdapter);

        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationModel mLocationModel = mDbHelper.getLocationDetails(mArrayNames.get(position));
                Intent mIntent = new Intent(DashboardActivity.this, LocationDetailsActivity.class);
                mIntent.putExtra("LocationModel", mLocationModel);
                startActivity(mIntent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_view:
                Intent mIntent = new Intent(DashboardActivity.this, MapActivity.class);
                startActivity(mIntent);
                break;
            case R.id.add_location:
                Intent mInt = new Intent(DashboardActivity.this, AddLocation.class);
                startActivity(mInt);
                finish();
                break;
        }
        return true;
    }

    private void setNavigationMenu() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_container);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_my_contacts:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_feedback:
                        nextIntent(FeedbacknSuggestionsActivity.class);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_logout:
                        nextIntent(LoginActivity.class);
                        SessionManager mSessionManager = new SessionManager(DashboardActivity.this);
                        mSessionManager.setUSERNAME("");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.menu_contact_us:
                        Uri uri = Uri.parse("http://www.apptozee.in");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else
            finish();
    }
}