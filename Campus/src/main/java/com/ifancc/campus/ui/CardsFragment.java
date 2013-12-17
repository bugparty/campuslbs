package com.ifancc.campus.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.ifancc.campus.R;
import com.ifancc.campus.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bowman on 13-12-10.
 */

public class CardsFragment extends ListFragment {
    protected Context context;
    private String TAG = LogUtils.makeLogTag(CardsFragment.class);
    private SimpleAdapter  adapter;

    /**
     *在初始化时加载布局
     * @param inflater ...
     * @param container ...
     * @param savedInstanceState ...
     * @return 渲染好的View
     */
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        Log.i(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = getActivity();
        Log.i(TAG, "onCreate");
        final String[] from = {"username", "date"};
        final int[] to = {R.id.message_username,R.id.message_date};
        adapter = new SimpleAdapter(context,getData(), R.layout.home_fragment_content,
                from,to);
        //Log.d(TAG, "preSetListAdapter");
        this.setListAdapter(adapter);
        //Log.d(TAG, "settedListAdapter");
    }
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> content = new ArrayList<Map<String,Object>>();

        for (int i=0;i<3;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username","bowmanHan");
            map.put("date","大约一首歌的时间");
            content.add(map);

        }
        return content;
    }


}
