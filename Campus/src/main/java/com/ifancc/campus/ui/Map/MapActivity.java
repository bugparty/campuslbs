package com.ifancc.campus.ui.Map;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.ifancc.campus.R;

/**
 * Created by Administrator on 13-12-17.
 */
public class MapActivity extends Activity {
    public GeoPoint myAdd;
    BMapManager mBMapMan = null;
    MapView mMapView = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBMapMan = new BMapManager(getApplication());
        mBMapMan.init("tvagardUUrud4rrQQGa4jevz", null);
//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
        setContentView(R.layout.activity_map);
        inint();
        MyLocationoverlay();
        OverData();
    }

    void inint() {
        mMapView = (MapView) findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);
//设置启用内置的缩放控件
        MapController mMapController = mMapView.getController();
// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        GeoPoint point = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));
//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(point);//设置地图中心点
        mMapController.setZoom(12);//设置地图zoom级别
    }

    GeoPoint MylocationData() {
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
        LocationData locData = new LocationData();
//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）
        locData.latitude = 39.945;
        locData.longitude = 116.404;
        myAdd = new GeoPoint((int) (locData.latitude * 1e6), (int) (locData.longitude * 1e6));
        return myAdd;
    }

    void MyLocationoverlay() {
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
        LocationData locData = new LocationData();
//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要使用百度经纬度坐标（bd09ll）
        locData.latitude = 39.945;
        locData.longitude = 116.404;
        locData.direction = 2.0f;
        myLocationOverlay.setData(locData);
        mMapView.getOverlays().add(myLocationOverlay);
        mMapView.refresh();
        mMapView.getController().animateTo(new GeoPoint((int) (locData.latitude * 1e6),
                (int) (locData.longitude * 1e6)));
    }

    class OverlayTest extends ItemizedOverlay<OverlayItem> {
        public OverlayTest(Drawable mark, MapView mapView) {
            //用MapView构造ItemizedOverlay
            super(mark, mapView);
        }

        protected boolean onTap(int index) {
            //在此处理item点击事件
            Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            return true;
        }

        public boolean onTap(GeoPoint pt, MapView mapView) {
            //在此处理MapView的点击事件，当返回 true时
            super.onTap(pt, mapView);
            return false;
        }
        // 自2.1.1 开始，使用 add/remove 管理overlay , 无需重写以下接口
        /*
        @Override
        protected OverlayItem createItem(int i) {
                return mGeoList.get(i);
        }

        @Override
        public int size() {
                return mGeoList.size();
        }
        */
    }

    void OverData() {
        /**
         *  在想要添加Overlay的地方使用以下代码，
         *  比如Activity的onCreate()中
         */
//准备要添加的Overlay
        double mLat1 = 39.90923;
        double mLon1 = 116.397428;
        double mLat2 = 39.9022;
        double mLon2 = 116.3922;
        double mLat3 = 39.917723;
        double mLon3 = 116.3722;
        MylocationData();
// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
        GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
        GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
        GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));

//准备overlay图像数据，根据实情情况修复
        Drawable mark = getResources().getDrawable(R.drawable.gps);
//用OverlayItem准备Overlay数据
        OverlayItem item1 = new OverlayItem(p1, "item1", "item1");
//使用setMarker()方法设置overlay图片,如果不设置则使用构建ItemizedOverlay时的默认设置
        OverlayItem item2 = new OverlayItem(p2, "item2", "item2");
        item2.setMarker(mark);
        OverlayItem item3 = new OverlayItem(p3, "item3", "item3");
        OverlayItem item4 = new OverlayItem(myAdd, "item4", "item4");
        item4.setMarker(getResources().getDrawable(R.drawable.icon_marka));

//创建IteminizedOverlay
        OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
//将IteminizedOverlay添加到MapView中

        mMapView.getOverlays().clear();
        mMapView.getOverlays().add(itemOverlay);

//现在所有准备工作已准备好，使用以下方法管理overlay.
//添加overlay, 当批量添加Overlay时使用addItem(List<OverlayItem>)效率更高
        itemOverlay.addItem(item1);
        itemOverlay.addItem(item2);
        itemOverlay.addItem(item3);
        itemOverlay.addItem(item4);
        mMapView.refresh();
//删除overlay .
//itemOverlay.removeItem(itemOverlay.getItem(0));
//mMapView.refresh();
//清除overlay
// itemOverlay.removeAll();
// mMapView.refresh();
    }
}

