package com.ifancc.campus.ui;

/**
 * Created by lg on 13-12-20.
 */


        import android.annotation.TargetApi;
        import android.app.ListFragment;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;
        import com.ifancc.campus.R;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment1 extends ListFragment{
    String[] presidents={
            "Dwight D. Eisenhower",
            "John F. Kennedy",
            "Lyndon B. Johnson",
            "Richard Nixon",
            "Gerald Ford",
            "Jimmy Carter",
            "Ronald Reagan",
            "George H. W. Bush",
            "Bill Clinton",
            "George W. Bush",
            "Barack Obama",
            "Gerald Ford",
            "Jimmy Carter",
            "Ronald Reagan",
            "George H. W. Bush",
            "Bill Clinton",
            "George W. Bush",
            "Barack Obama"
    };
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment1, container, false);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final String[] from = {"ContactName", "Message"};
        final int[] to = {R.id.ContactName ,R.id.ContactMessage};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(), R.layout.chat_list_item,
                from,to);
        //Log.d(TAG, "preSetListAdapter");
        this.setListAdapter(adapter);
        //Log.d(TAG, "settedListAdapter");
    }
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> content = new ArrayList<Map<String,Object>>();

        for (int i=0;i<12;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ContactName","bowmanHan");
            map.put("Message","go to lunch");
            content.add(map);

        }
        return content;
    }
    public void onListItemClick(ListView parent,View v, int position, long id){
        Toast.makeText(getActivity(),"You have selected"+position,
                Toast.LENGTH_SHORT).show();
    }
}

