package com.ifancc.campus.ui.user;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.ifancc.campus.R;
import com.ifancc.campus.ui.BaseActivity;
import com.ifancc.campus.ui.HomeActivity;

/**
 * Created by Administrator on 13-11-17.
 */
public class Info extends TabActivity {
    private TabHost mTabHost = null;
    private TabWidget mTabWidget = null;
    private ListView muserpagelist;
    private String [] muserpagelistTexts;
    private String [] muserpagelistText;
    private LinearLayout muser_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);
/*显示App icon左侧的back键*/
     //   ActionBar actionBar = getActionBar();// actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setContent(
                R.id.LinearLayout001).setIndicator("个人信息"));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setContent(
                R.id.LinearLayout002).setIndicator("日志"));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setContent(
                R.id.LinearLayout003).setIndicator("相册"));
        muser_friends=(LinearLayout)this.findViewById(R.id.user_friends);
        muserpagelist=(ListView)this.findViewById(R.id.user_details_list);

        muserpagelistTexts=getResources().getStringArray(R.array.userpage_list);
        muserpagelistText=getResources().getStringArray(R.array.navigation_list);
        muserpagelist.setAdapter(new ArrayAdapter<String>(this,R.layout.userpagelist_item,R.id.userpage_list_text1,muserpagelistTexts));

        muser_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Info.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case  android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.out_form_left,R.anim.in_form_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
