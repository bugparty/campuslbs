package com.ifancc.campus.ui.widget;

/**
 * Created by Administrator on 14-2-15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifancc.campus.R;

import java.util.HashMap;
import java.util.List;
/**
 * Created by Administrator on 14-2-14.
 */
public class SetupAdapter extends BaseAdapter {
    private Context mContext;
    private List<HashMap<String, Object>> dataList;
    public SetupAdapter(Context context,List<HashMap<String,Object>> dataList){
        this.mContext=context;
        this.dataList=dataList;
    }
    public int getCount(){
        return dataList.size();
    }
    public HashMap<String,Object> getItem(int position){
        return dataList.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.setup_list_item,null);
            holder.text1=(TextView) convertView.findViewById(R.id.ItemText1);
            holder.text2=(TextView) convertView.findViewById(R.id.ItemText2);
            holder.text3=(TextView) convertView.findViewById(R.id.ItemText3);
            holder.text4=(TextView) convertView.findViewById(R.id.ItemText4);
            holder.text5=(TextView) convertView.findViewById(R.id.ItemText5);
            holder.text6=(TextView) convertView.findViewById(R.id.ItemText6);
            holder.text7=(TextView) convertView.findViewById(R.id.ItemText7);
            holder.text8=(TextView) convertView.findViewById(R.id.ItemText8);
            holder.text9=(TextView) convertView.findViewById(R.id.ItemText9);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.text1.setText((String)getItem(position).get("text1"));
        holder.text2.setText((String)getItem(position).get("text2"));
        holder.text3.setText((String)getItem(position).get("text3"));
        holder.text4.setText((String)getItem(position).get("text4"));
        holder.text5.setText((String)getItem(position).get("text5"));
        holder.text6.setText((String)getItem(position).get("text6"));
        holder.text7.setText((String)getItem(position).get("text7"));
        holder.text8.setText((String)getItem(position).get("text8"));
        holder.text9.setText((String)getItem(position).get("text9"));
        return convertView;
    }
    final class ViewHolder{
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        TextView text6;
        TextView text7;
        TextView text8;
        TextView text9;
    }
}
