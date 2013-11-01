package com.ifancc.campus.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ifancc.campus.R;

public class HomeActivity extends BaseActivity {
    private ListView mNavigation_list;
    private String[] mNavigation_texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mNavigation_list = (ListView) findViewById(R.id.navigation_list);
        mNavigation_texts = getResources().getStringArray(R.array.navigation_list);
        mNavigation_list.setAdapter(new ArrayAdapter<String>(this, R.layout.navigation_list_item,
                R.id.navigation_list_tv, mNavigation_texts));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
