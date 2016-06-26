package com.geo.power.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.geo.com.geo.power.util.DensityUtil;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/7.
 */
public class AddLongPlanLocationActivity extends BaseActivity {
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption locationOption;
    private TextView mLoadMoreTv;
    public final static int REQUEST_CODE = 1001;
    private ListView mLocationListLview;
    private List<PoiItem> mAddressDatas;
    private AddrAdapter mAdapter;
    private PoiSearch.Query mPoiQuery;
    private String mCurCity;
    private boolean hasData = false;
    private ProgressView mLoadProgress;
    private View mLoadContainer;
    /**
     * 每一页加载的数据数
     */
    private final int mPageSize = 10;
    /**
     * 当前记载的数据
     */
    private int mCurPage = 0;

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
        mLoadMoreTv = (TextView) findViewById(R.id.location_my_loadmore);

        View header = View.inflate(mContext,R.layout.location_item_header,null);
        mLocationListLview.addHeaderView(header);
        mLoadProgress = (ProgressView) header.findViewById(R.id.progress_pv_circular);
        mLoadContainer = header.findViewById(R.id.location_load_contain);

        mLoadProgress.start();
        //开始定位
        startLocation();
        initData();

    }

    private void initData() {
        if (mAddressDatas == null) {
            mAddressDatas = new ArrayList<PoiItem>();
        } else {
            mAddressDatas.clear();
        }

        mAdapter = new AddrAdapter(mAddressDatas);
        mLocationListLview.setAdapter(mAdapter);

    }

    /**
     * 开始定位
     */
    private void startLocation() {
        mlocationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(locationOption);
        // 设置定位监听
        mlocationClient.setLocationListener(new PLocationChanged());
        mlocationClient.startLocation();
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        String keyWord = "商务住宅";
        if (mPoiQuery == null) {
            //这个地方应该是由定位来获取
            mPoiQuery = new PoiSearch.Query(keyWord, "", "武汉市");
        }
        // keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域的编码，是必须设置参数
        mPoiQuery.setPageSize(mPageSize);// 设置每页最多返回多少条poiitem
        mPoiQuery.setPageNum(mCurPage);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, mPoiQuery);//初始化poiSearch对象
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                List<PoiItem> items = poiResult.getPois();
                mLoadContainer.setVisibility(View.GONE);
                mLoadMoreTv.setVisibility(View.GONE);
                mLoadProgress.stop();
                mAddressDatas.addAll(items);
                mAdapter.notifyDataSetChanged();
                hasData = true;
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });//设置回调数据的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }
    @Override
    protected void initListener() {
        super.initListener();
        //对ListView进行监听，如果滚动到底部就自行加载数据
        mLocationListLview.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int lastItem = mLocationListLview.getLastVisiblePosition();
                if (lastItem + 1 == totalItemCount) {  //说明已经滑到屏幕底部了
                    if(hasData){
                        mLoadMoreTv.setVisibility(View.VISIBLE);
                    }
                    mCurPage++;
                    doSearchQuery();
                }
            }

            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {

            }
        });
        mLocationListLview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                if(position ==0){
                    intent.putExtra("address","");
                    setResult(RESULT_OK,intent);
                    finish();
                    return;
                }
                PoiItem item = (PoiItem) mAdapter.getItem(position-1);
                String addr = item.getProvinceName()+item.getCityName()+item.getSnippet();
                intent.putExtra("detail",addr);
                intent.putExtra("address",item.getTitle());
                intent.putExtra("lat",item.getLatLonPoint().getLatitude());
                intent.putExtra("lon",item.getLatLonPoint().getLongitude());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * 定位监听器
     */
    private class PLocationChanged implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    mCurCity = amapLocation.getCity();
                    doSearchQuery();

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    mLoadProgress.stop();
                    mLoadContainer.setVisibility(View.GONE);
                    showToast("定位失败了");
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("我的位置");
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


    private class AddrAdapter extends BaseAdapter {
        private List<PoiItem> mdata;

        public AddrAdapter(List<PoiItem> data) {
            this.mdata = data;
        }

        @Override
        public int getCount() {
            return mdata.size();
        }

        @Override
        public Object getItem(int position) {
            return mdata.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PViewHolder holder;
            if (convertView == null) {
                holder = new PViewHolder();
                convertView = View.inflate(mContext, R.layout.location_item, null);
                holder.contentTv = (TextView) convertView.findViewById(R.id.location_descrip_item);
                holder.titleTv = (TextView) convertView.findViewById(R.id.location_title_item);
                holder.distanceTv = (TextView) convertView.findViewById(R.id.location_distance_item);
                convertView.setTag(holder);
            } else {
                holder = (PViewHolder) convertView.getTag();
            }
            PoiItem poiiitem = mdata.get(position);
            holder.titleTv.setText(poiiitem.getTitle());
            holder.contentTv.setText(poiiitem.getSnippet());
            holder.distanceTv.setText(poiiitem.getDistance() + " Km");
            return convertView;
        }

        private class PViewHolder {
            TextView titleTv;
            TextView contentTv;
            TextView distanceTv;
        }
    }
}
