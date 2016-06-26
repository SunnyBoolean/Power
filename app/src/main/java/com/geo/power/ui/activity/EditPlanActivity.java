package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geo.com.geo.power.bean.PlanHistoryInfo;
import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.BitmapUtils;
import com.github.lazylibrary.util.DateUtil;
import com.rey.material.widget.EditText;
import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/11.
 */
public class EditPlanActivity extends BaseActivity {
    private PlanInfo mPlanInfo;
    private View mPlanLocation;
    private TextView mLocationTv;
    private ArrayList<String> mSelectPics = new ArrayList<String>();
    private ImageAdapter mPicAdapter;
    private final String flag = "HelloPowerWorld";
    private GridView mPicGridView;
    private PlanInfo mAddPlanInfo = new PlanInfo();
    private EditText mContentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_plan_activity_layout);
    }

    @Override
    protected void initCompontent() {
        super.initCompontent();
        mPlanLocation = findViewById(R.id.editslongplan_location_click);
        mLocationTv = (TextView) findViewById(R.id.editplongplan_location_tvshow);
        mContentEt = (EditText) findViewById(R.id.editplan_inputcontent);
        mPicGridView = (GridView) findViewById(R.id.list_edithomeplan_img_select_gridview);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mPlanInfo = (PlanInfo) intent.getSerializableExtra("plan_info");

        mSelectPics.clear();
        mSelectPics.add(flag);
        mPicAdapter = new ImageAdapter();
        mPicGridView.setAdapter(mPicAdapter);

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "完成");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 1) {
                    uploadPlan();
                }
                return false;
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPlanLocation.setOnClickListener(this);
        mPicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mSelectPics.get(mPicAdapter.getCount() - position - 1);
                if (url.equals(flag)) {
                    ImageSelectorActivity.start(EditPlanActivity.this, 9, ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                } else {
//                    ImageSelectorActivity.start(AddLongPlanActivity.this);
                }

            }
        });
    }

    /**
     * 必要性检查
     *
     * @return
     */
    private boolean checkUpload() {
        String content = mContentEt.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("内容不能为空");
            return false;
        }
        return true;
    }

    /**
     * 上传计划
     */
    private void uploadPlan() {
        //检查不通过就不上传任何数据
        if (!checkUpload()) {
            return;
        }
        mAddPlanInfo.content = mContentEt.getText().toString();
        mAddPlanInfo.uid = BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId();
        mAddPlanInfo.originalPlanId = mPlanInfo.getObjectId();
        mAddPlanInfo.author = BmobUser.getCurrentUser(mContext, UserInfo.class);
        //如果没有图片就直接上报内容
        if (mAddPlanInfo.picLists.size() <= 0) {
            mAddPlanInfo.save(mContext, new SaveListener() {
                @Override
                public void onSuccess() {
                    showToast("更新成功！");
                    //当更新完毕后需要对原始数据表的执行次数加1
                    updateOrignalPlan();
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("更新失败！");
                    mPlanInfo.increment("hadDotimes");
                }
            });
        } else {
            final String[] filePaths = new String[mAddPlanInfo.picLists.size()];
            final String[] pics = mAddPlanInfo.picLists.toArray(filePaths);
            BmobFile.uploadBatch(mContext, pics, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files, List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                        //do something
                        mAddPlanInfo.picLists.clear();
                        mAddPlanInfo.picLists.addAll(urls);
                        mAddPlanInfo.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                showToast("更新成功！");
                                //当更新完毕后需要对原始数据表的执行次数加1
                                updateOrignalPlan();
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("更新失败！");
                            }
                        });
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    showToast("上传失败" + statuscode + ",错误描述：" + errormsg);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }
            });

        }
    }

    private void updateOrignalPlan() {
        mPlanInfo.increment("hadDotimes");
        mPlanInfo.update(mContext);
    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.editslongplan_location_click:
                Intent intent = new Intent(mContext, AddLongPlanLocationActivity.class);
                startActivityForResult(intent, AddLongPlanLocationActivity.REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选中的图片
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            mSelectPics.addAll(images);
            mAddPlanInfo.picLists.addAll(images);
            mPicAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == AddLongPlanLocationActivity.REQUEST_CODE && data != null) {  //选择的位置
//            PoiItem item = (PoiItem) data.getSerializableExtra("location");
            String addr = data.getStringExtra("address");
            if (!TextUtils.isEmpty(addr)) {
                mLocationTv.setText(addr);
                mLocationTv.setTextColor(mCommonColor);
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.add_planlocation_on);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                mLocationTv.setCompoundDrawables(drawable, null, null, null);
                mAddPlanInfo.curLongitude = data.getDoubleExtra("lon", 0.0);
                mAddPlanInfo.curLatitude = data.getDoubleExtra("lat", 0.0);
                mAddPlanInfo.locationArrd = addr;

            } else {
                mLocationTv.setText("不显示位置信息");
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.add_longplan_location);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                mLocationTv.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    private class ImageAdapter extends BaseAdapter {

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return mSelectPics.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelectPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String url = mSelectPics.get(getCount() - position - 1);
            IViewHolder holder;
            if (convertView == null) {
                holder = new IViewHolder();
                convertView = View.inflate(mContext, R.layout.item_image_select, null);
                holder.img = (ImageView) convertView.findViewById(R.id.add_longplan_pics);
                convertView.setTag(holder);
            } else {
                holder = (IViewHolder) convertView.getTag();
            }
            if (flag.equals(url)) {
//                return convertView;
            } else {
                Bitmap bitmap = BitmapUtils.getBitmap(EditPlanActivity.this, url);
//                BitmapFactory.dec
                holder.img.setImageBitmap(bitmap);
            }
            return convertView;
        }

        class IViewHolder {
            ImageView img;
        }
    }

}
