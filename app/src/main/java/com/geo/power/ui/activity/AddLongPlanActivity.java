package com.geo.power.ui.activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.bean.PlanInfo;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.BitmapUtils;
import com.geo.com.geo.power.util.ScreenUtil;
import com.github.lazylibrary.util.DateUtil;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Switch;
import com.yongchun.library.view.ImageSelectorActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import ui.geo.com.power.R;

/**
 * Created by Administrator on 2016/6/3.
 */
public class AddLongPlanActivity extends BaseActivity {
    private View mSelectCategory, mPlanDeadline, mPlanNotify, mPlanLocation, mPlanJieduan, mAddPicBtn;
    private TextView mSelectCategoryContent, mPlanDeadLineTv, mPlanNotifyShow, mIsPublicTV, mLocationTv;
    private ImageView mCategorySelectegIV, mPlanDeadlineIm, mPlanNotifyIm;
    private EditText mContentInputEt;
    private Switch mIsPublicSwitch;
    private GridView mPicGridView;
    private ArrayList<String> mSelectPics = new ArrayList<String>();
    private ImageAdapter mPicAdapter;
    private final String flag = "HelloPowerWorld";
    private int mHourdo,mMuildo;
    /**
     * 新增计划实体类
     */
    private PlanInfo mAddPlanInfo = new PlanInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_longplan);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mSelectCategory = findViewById(R.id.add_longplan_selcategory_click);
        mSelectCategoryContent = (TextView) findViewById(R.id.add_longplan_selcategory_content);
        mCategorySelectegIV = (ImageView) findViewById(R.id.add_longplan_selcategory_imvtag);
        mPlanDeadline = findViewById(R.id.add_longplan_plandeadline_click);
        mPlanNotify = findViewById(R.id.add_longplan_plannotify_click);
        mPlanDeadLineTv = (TextView) findViewById(R.id.add_longplan_plandeadline__tvshow);
        mPlanDeadlineIm = (ImageView) findViewById(R.id.add_longplan_plandeadline__imgone);
        mPlanNotifyShow = (TextView) findViewById(R.id.add_longplan_plannotify_noftivshow);
        mPlanNotifyIm = (ImageView) findViewById(R.id.add_longplan_plannotify_imgone);
        mPlanLocation = findViewById(R.id.addlongplan_location_click);
        mIsPublicSwitch = (Switch) findViewById(R.id.add_longplan_pubprio_switcher);
        mIsPublicTV = (TextView) findViewById(R.id.add_longplan_pubprio_cv);
        mAddPicBtn = findViewById(R.id.add_longplan_pics);
        mPicGridView = (GridView) findViewById(R.id.list_homeplan_img_select_gridview);
        mLocationTv = (TextView) findViewById(R.id.addlongplan_location_tvshow);
        mContentInputEt = (EditText) findViewById(R.id.add_plan_content_ino);
        mSelectPics.clear();
        mSelectPics.add(flag);
        mPicAdapter = new ImageAdapter();
        mPicGridView.setAdapter(mPicAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSelectCategory.setOnClickListener(this);
        mPlanDeadline.setOnClickListener(this);
        mPlanNotify.setOnClickListener(this);
        mPlanLocation.setOnClickListener(this);
//        mAddPicBtn.setOnClickListener(this);
        mIsPublicSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked) {
                    mIsPublicTV.setText("公开");
                    mIsPublicTV.setTextColor(mCommonColor);
                    mAddPlanInfo.ispublie = 0;
                } else {
                    mIsPublicTV.setText("私有");
                    mIsPublicTV.setTextColor(Color.GRAY);
                    mAddPlanInfo.ispublie = 1;
                }
            }
        });
        mPicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mSelectPics.get(mPicAdapter.getCount() - position - 1);
                if (url.equals(flag)) {
                    ImageSelectorActivity.start(AddLongPlanActivity.this, 9, ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                } else {
//                    ImageSelectorActivity.start(AddLongPlanActivity.this);
                }

            }
        });
    }

    /**
     * 所有的单击事件在这里处理
     *
     * @param v
     */
    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.addlongplan_location_click:  //选择位置
                Intent intent = new Intent(mContext, AddLongPlanLocationActivity.class);
                startActivityForResult(intent, AddLongPlanLocationActivity.REQUEST_CODE);
                break;
            case R.id.add_longplan_selcategory_click:  //选择分类
//          //      popupWindowForPlanTag();
                showPlanCategoryDialog();
                break;
            case R.id.add_longplan_plandeadline_click:  //计划截止时间
                showPlanDeadline();
                break;
            case R.id.add_longplan_plannotify_click:  //定时提醒
                showPlanNotiyTime();
            case R.id.add_longplan_pics:   //添加图片

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

    /**
     * 计划提醒时间
     */
    private void showPlanNotiyTime() {
        com.rey.material.app.Dialog.Builder builder = new TimePickerDialog.Builder(true ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 24, 00) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
//                Toast.makeText(mContext, "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                String time = dialog.getFormattedTime(SimpleDateFormat.getTimeInstance());
                mPlanNotifyShow.setText("每天" + time);
                mHourdo = dialog.getHour();
                mMuildo = dialog.getMinute();
                mPlanNotifyIm.setVisibility(View.VISIBLE);
                mPlanNotifyShow.setTextColor(mCommonColor);
                mAddPlanInfo.notifyDate = time;
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("完成")
                .negativeAction("取消");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 选择计划截至时间
     */
    private void showPlanDeadline() {
        com.rey.material.app.Dialog.Builder builder = new DatePickerDialog.Builder(true ? R.style.Material_App_Dialog_DatePicker_Light : R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                String udate = date.replace("年", "-").replace("月", "-").replace("日", "");
                mPlanDeadLineTv.setText(date + "完成计划");
                mPlanDeadLineTv.setTextColor(mCommonColor);
                mPlanDeadlineIm.setVisibility(View.VISIBLE);
                mAddPlanInfo.completeDate = udate;
//                Toast.makeText(mContext, "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
//                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("完成")
                .negativeAction("取消");

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 显示计划分类的选择Dialog
     */
    private void showPlanCategoryDialog() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.add_longplan_selecategory_menu, null);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(content);
        int[] size = ScreenUtil.getScreenSize(mContext);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (size[1] * 0.5); // 高度设置为屏幕的0.5
        p.width = (int) (size[0] * 0.7); // 宽度设置为屏幕的0.8
        dialog.getWindow().setAttributes(p);
        dialog.show();

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectCategoryContent.setTextColor(mCommonColor);
                    mSelectCategoryContent.setText(view.getText().toString());
                    mCategorySelectegIV.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                    mAddPlanInfo.category = view.getText().toString();
                }
            });
        }
    }

    /**
     * 暂时无用，之前用于计划的分类选择
     */
    private void popupWindowForPlanTag() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.add_longplan_selecategory_menu, null);
        final PopupWindow popwindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popwindow.dismiss();
                    return true;
                }

                return false;
            }
        });
        int[] size = ScreenUtil.getScreenSize(mContext);
        int read = (int) (size[0] * 0.6);
        popwindow.setWidth(read);
        popwindow.setTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        popwindow.setBackgroundDrawable(cd);
//        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
        popwindow.showAtLocation(findViewById(R.id.addplan_kjkdsa), Gravity.CENTER, 0, 0);
        //设置背景变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int color = Color.parseColor("#004d40");
                    mSelectCategoryContent.setTextColor(mCommonColor);
                    mSelectCategoryContent.setText(view.getText().toString());
                    mCategorySelectegIV.setVisibility(View.VISIBLE);
                    popwindow.dismiss();
                }
            });
        }
    }

    /**
     * 关于Toolbar的操作均在此完成
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("新建计划");
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "发送");
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

    /**
     * 必要性检查
     *
     * @return
     */
    private boolean checkUpload() {
        String content = mContentInputEt.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("内容不能为空");
            return false;
        } else if (TextUtils.isEmpty(mAddPlanInfo.completeDate)) {
            showToast("计划截止时间不能为空");
            return false;
        } else if (TextUtils.isEmpty(mAddPlanInfo.category)) {
            showToast("分类不能为空");
            return false;
        }
        return true;
    }
public void setAlarm(){
    //操作：发送一个广播，广播接收后Toast提示定时操作完成
    Intent intent =new Intent(mContext, PlanAlarmActivity.class);
    intent.setAction("short");
    PendingIntent sender=
            PendingIntent.getBroadcast(mContext, 0, intent, 0);

//设定一个五秒后的时间
    Calendar calendar=Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, mHourdo);
    calendar.set(Calendar.MINUTE, mMuildo);
    calendar.setTimeInMillis(System.currentTimeMillis());

    AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
    alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
}
    /**
     * 上传计划
     */
    private void uploadPlan() {
        //检查不通过就不上传任何数据
        if (!checkUpload()) {
            return;
        }
        showProgress();
        mAddPlanInfo.startDate = DateUtil.getCurDateOnlyDay();
        mAddPlanInfo.content = mContentInputEt.getText().toString();
        mAddPlanInfo.author = BmobUser.getCurrentUser(mContext, UserInfo.class);
        mAddPlanInfo.uid = BmobUser.getCurrentUser(mContext, UserInfo.class).getObjectId();
        int totalDay = DateUtil.daysBetween(mAddPlanInfo.startDate, mAddPlanInfo.completeDate, "yyyy-MM-dd");
        mAddPlanInfo.plantotalDay = totalDay;
        //如果没有图片就直接上报内容
        if (mAddPlanInfo.picLists.size() <= 0) {
            mAddPlanInfo.save(mContext, new SaveListener() {
                @Override
                public void onSuccess() {
                    setAlarm();
                    showToast("创建成功！");

                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("创建失败！");
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
                                showToast("创建成功！");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("创建失败！");
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

    private void showProgress() {
        com.rey.material.app.Dialog.Builder build = new com.rey.material.app.Dialog.Builder(R.style.SimpleDialogLight) {
            @Override
            protected void onBuildDone(com.rey.material.app.Dialog dialog) {
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
//        build.title("确认删除内容：")
//                .positiveAction("删除")
//                .negativeAction("取消")
//                .contentView(R.layout.delete_add_wallpaper_content_item);
        build.contentView(R.layout.add_plan_progress);
        DialogFragment fragment = DialogFragment.newInstance(build);
        fragment.show(getSupportFragmentManager(), null);
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
                Bitmap bitmap = BitmapUtils.getBitmap(AddLongPlanActivity.this, url);
//                BitmapFactory.dec
                holder.img.setImageBitmap(bitmap);
            }
            return convertView;
        }

        class IViewHolder {
            ImageView img;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddPlanInfo.picLists.clear();
        mAddPlanInfo = null;
    }

}
