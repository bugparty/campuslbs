package com.ifancc.campus.Map;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.ifancc.campus.R;

/**
 * Created by Administrator on 13-12-17.
 */
public class MapActivity extends Activity {
     BMapManager mBMapMan=null ;
     MapView mMapView=null;
    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        mBMapMan=new BMapManager(getApplication());
        mBMapMan.init("89BDB0A98BF9D9C3303503EF11D28CE75EB40AB1", null);
//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
        setContentView(R.layout.activity_map);
        mMapView=(MapView)findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);
//设置启用内置的缩放控件
        MapController mMapController=mMapView.getController();
// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(point);//设置地图中心点
        mMapController.setZoom(12);//设置地图zoom级别

    }

}
