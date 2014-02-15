package com.ifancc.campus.ui.widget;

/**
 * Created by Administrator on 14-2-15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.ifancc.campus.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class SetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("text1",this.getString(R.string.text1));
        map.put("text2",this.getString(R.string.text2));
        map.put("text3",this.getString(R.string.text3));
        map.put("text4",this.getString(R.string.text4));
        map.put("text5",this.getString(R.string.text5));
        map.put("text6",this.getString(R.string.text6));
        map.put("text7",this.getString(R.string.text7));
        map.put("text8",this.getString(R.string.text8));
        map.put("text9",this.getString(R.string.text9));
        data.add(map);

        ListView listView=(ListView)findViewById(R.id.list);
        SetupAdapter adapter=new SetupAdapter(SetupActivity.this,data);
        listView.setAdapter(adapter);
    }
}
