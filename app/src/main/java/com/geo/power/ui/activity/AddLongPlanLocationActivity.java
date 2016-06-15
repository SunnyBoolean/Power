package com.geo.power.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.geo.com.geo.power.bean.LbsAddressInfo;
import com.geo.com.geo.power.bean.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/7.
 */
public class AddLongPlanLocationActivity extends BaseActivity implements LocationSource,
        AMapLocationListener, RadioGroup.OnCheckedChangeListener {
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption locationOption;
    private ListView mLocationListLview;
    private List<LbsAddressInfo> mAddressDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlongplan_loacation);

    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mLocationListLview = (ListView) findViewById(R.id.location_listview);
    //开始定位
        startLocation();
    }

    private void startLocation() {
        mlocationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(locationOption);
        // 设置定位监听
        mlocationClient.setLocationListener(this);
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double lat = amapLocation.getLatitude();//获取纬度
                double lon = amapLocation.getLongitude();//获取经度
                BmobGeoPoint gpoint = new BmobGeoPoint(lon, lat);
                LbsAddressInfo info = new LbsAddressInfo();
//                info.mGpsAdd = gpoint;
//                info.save(mContext, new SaveListener() {
//                    @Override
//                    public void onSuccess() {
//                        showToast("保存成功");
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//                });


                BmobQuery<LbsAddressInfo> bmobQuery = new BmobQuery<LbsAddressInfo>();
                bmobQuery.addWhereNear("gpsAdd", gpoint);
                bmobQuery.setLimit(10);    //获取最接近用户地点的10条数据
                bmobQuery.findObjects(this, new FindListener<LbsAddressInfo>() {
                    @Override
                    public void onSuccess(List<LbsAddressInfo> object) {
                        for(LbsAddressInfo lbs:object){
                           BmobGeoPoint point= lbs.mGpsAdd;
                            String str = point.toString();
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        // TODO Auto-generated method stub
                        showToast("error:"+msg);
                    }
                });
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mlocationClient.onDestroy();
        mlocationClient = null;
        locationOption = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
    private class AddrAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 0;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
