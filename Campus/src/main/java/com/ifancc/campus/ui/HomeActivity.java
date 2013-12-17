package com.ifancc.campus.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ifancc.campus.R;
import com.ifancc.campus.ui.user.Info;
import com.ifancc.campus.util.LogUtils;

public class HomeActivity extends BaseActivity {
    private ListView mNavigation_list;
    private String[] mNavigation_texts;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mLeftDrawer;
    private String mTitle = "hello";
    private String mDrawerTitle = "drawer";
    private ActionBar mActionBar;
    private RelativeLayout user_homePage;
    private View mHomeListView;
    private CardsFragment mHomeList;
    private static final String TAG = LogUtils.makeLogTag(HomeActivity.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Dynamic load the ListFragment for Home Screen
        mHomeListView = findViewById(R.id.home_listview);
        mHomeList = new CardsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.home_listview,mHomeList).commit();



        user_homePage=(RelativeLayout)findViewById(R.id.user_homePage);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.homeDrawer);
        mNavigation_list = (ListView) findViewById(R.id.navigation_list);
        mActionBar = getSupportActionBar();
        mLeftDrawer = findViewById(R.id.leftDrawer);
        mDrawerToggle = new NavigationActionDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,
                R.string.open_drawer,R.string.close_drawer);


        mNavigation_texts = getResources().getStringArray(R.array.navigation_list);
        mNavigation_list.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_list_item,
                R.id.navigation_list_tv, mNavigation_texts));
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //Enable ActionBar Button
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        user_homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(HomeActivity.this,Info.class);
                startActivity(intent);
                overridePendingTransition( R.anim.out_form_left,R.anim.in_form_right);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mLeftDrawer);
        //hide some menu item
       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //handle Drawer Open and Close
    private class NavigationActionDrawerToggle extends ActionBarDrawerToggle{
        public NavigationActionDrawerToggle(Activity activity, DrawerLayout drawerLayout,
                                            int drawerImageRes, int openDrawerContentDescRes,
                                            int closeDrawerContentDescRes) {
            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        /** Called when a drawer has settled in a completely closed state. */
        public void onDrawerClosed(View view) {
            getSupportActionBar().setTitle(mTitle);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }

        /** Called when a drawer has settled in a completely open state. */

        public void onDrawerOpened(View drawerView) {
            getSupportActionBar().setTitle(mDrawerTitle);
            invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
        }
    }
}
