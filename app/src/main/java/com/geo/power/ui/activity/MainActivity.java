package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.DensityUtil;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.fragment.DiscoverFragment;
import com.geo.power.ui.fragment.HomeDongtaiFragment;
import com.geo.power.ui.fragment.HomeFragment;
import com.geo.power.ui.fragment.PersonalCenterFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Spinner;

import android.support.design.widget.FloatingActionButton;

import java.util.List;

import cn.bmob.v3.BmobUser;
import ui.geo.com.power.R;

public class MainActivity extends HomeBaseActivity {
    private RadioButton mHomeBtn, mDiscoverBtn, mPersonerCenterBtn, mDongtaiBtn;
    private TextView mSettingBtn, mAboutBtn;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mAddPlanFAB;
    private NavigationView mNavigationView;
    private BottomSheetDialog mBottomSheetDialog;
    private Spinner mSpinner;
    private MenuItem mContitionMi;
    private ImageView mUserImage;
    private TextView mUserName;
    private LoadCallback mLoadCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PLog(P_TAG, "子类类onCreate()");
        setContentView(R.layout.activity_main, false);
//        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initCompontent() {
        PLog(P_TAG, "子类initCompontent()");
        super.initCompontent();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigationview);
        mHomeBtn = (RadioButton) findViewById(R.id.main_home_home);
        mDiscoverBtn = (RadioButton) findViewById(R.id.main_home_discover);
        mPersonerCenterBtn = (RadioButton) findViewById(R.id.main_home_personal);
        mDongtaiBtn = (RadioButton) findViewById(R.id.main_home_dongtai);
        mSettingBtn = (TextView) findViewById(R.id.home_main_setting_btn);
        mAboutBtn = (TextView) findViewById(R.id.home_main_about_btn);
        mAddPlanFAB = (FloatingActionButton) findViewById(R.id.home_addplan);
        mSpinner = (Spinner) findViewById(R.id.spinner_labels);
       View header = mNavigationView.getHeaderView(0);
        mUserImage = (ImageView) header.findViewById(R.id.homemain_uimgs);
        mUserName = (TextView) header.findViewById(R.id.homemain_unamesd);
        changedRadioButtonByClick(mHomeBtn);
        setSlideMenuWidth();
        initUser();
    }

    /**
     * 初始化用户信息，如加载用户头像、名称等
     */
    private void initUser() {
        UserInfo user = BmobUser.getCurrentUser(this, UserInfo.class);
        if (!TextUtils.isEmpty(user.uimg) && mUserImage!=null) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            ImageLoader.getInstance().displayImage(user.uimg, mUserImage, defaultOptions);
        }
        if(mUserName!=null)
        mUserName.setText(user.getUsername());
    }

    /**
     * 设置侧滑菜单的宽度
     */
    private void setSlideMenuWidth() {
        int[] screen = ScreenUtil.getScreenSize(this);
        ViewGroup.LayoutParams lay = mNavigationView.getLayoutParams();
        lay.width = (screen[0] * 5) / 7;
        mNavigationView.setLayoutParams(lay);
        //默认选中生活选项
        mNavigationView.setCheckedItem(R.id.menu_item_home_slidemenu_xiguan);

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
//        setSupportActionBar(mToolBar);这个方法会报错
        mToolBar.setNavigationIcon(R.drawable.home_main_menu);//设置导航按钮，典型的就是返回箭头
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }

        });
//        mToolBar.setTitle("Power");//设置标题
        int color = Color.parseColor("#FFFFFF");
        mToolBar.setTitleTextColor(color);  //设置标题字体颜色
        //----------设置菜单，这里就设置一个Search菜单
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "搜索");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setIcon(R.drawable.search);

        mContitionMi = menu.add(0, 2, 3, "排序");
        mContitionMi.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mContitionMi.setIcon(R.drawable.home_list_condition);
//        mOrderView =
        //---------- 对子菜单MenuItem进行响应 ------------
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
//                        Toast.makeText(MainActivity.this, "你想搜索什么", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, SearchActivity.class);
                        startActivity(intent);
                        break;

                    case 2:      //更多
//                        showToast("选择更多");
                        popupWindowForListOrder();
                        break;
                    case android.R.id.home:

                        Toast.makeText(MainActivity.this, "你单击Home键", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });


        spinner();
    }
    //弹出排序菜单
    private void popupWindowForListOrder() {
        LinearLayout content = (LinearLayout) View.inflate(mContext, R.layout.home_main_clistorder_menu, null);
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
        popwindow.setWidth(size[0] / 2);
        popwindow.setTouchable(true);
        popwindow.setBackgroundDrawable(new BitmapDrawable());
        popwindow.setOutsideTouchable(true);
//        popwindow.showAsDropDown(mToolBar);
        int disy = DensityUtil.dip2px(mContext, 80);
        int disx = DensityUtil.dip2px(mContext, 4);
        popwindow.showAtLocation(mToolBar, Gravity.RIGHT | Gravity.TOP, disx, disy);

        int childs = content.getChildCount();
        for (int i = 0; i < childs; i++) {
            final TextView view = (TextView) content.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String order = view.getText().toString().trim();
                    if("时间最新".equals(order)){
                        mLoadCallBack.doLoadForOrder(0);
                    }else if("参与最多".equals(order)){
                        mLoadCallBack.doLoadForOrder(1);
                    }else if("执行最多".equals(order)){
                        mLoadCallBack.doLoadForOrder(2);
                    }else if("已完成".equals(order)){
                        mLoadCallBack.doLoadForOrder(3);
                    }

                    popwindow.dismiss();
                }
            });
        }
    }

    private void spinner() {
        final int tsize = DensityUtil.dip2px(mContext, 6);
       final String[] items = {"生活", "运动", "学习", "工作"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        mSpinner.setAdapter(adapter);
        mSpinner.setmShowTextColor(Color.WHITE);
        mSpinner.setShowTextSize(tsize);
        mSpinner.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {
                mLoadCallBack.doLoadForCategory(items[position]);
                return true;
            }
        });
    }

    protected void initListener() {

        mHomeBtn.setOnClickListener(this);
        mDiscoverBtn.setOnClickListener(this);
        mPersonerCenterBtn.setOnClickListener(this);
        mAddPlanFAB.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mAboutBtn.setOnClickListener(this);
        mDongtaiBtn.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                //首先关闭菜单
                mDrawerLayout.closeDrawers();
                switch (id) {
                    case R.id.menu_item_home_slidemenu_xiguan: //习惯壁纸
                        break;
                    case R.id.menu_item_home_slidemenu_xinyuan: //心愿瓶子
                        Intent intent = new Intent();
                        intent.setClass(mContext, DiscoverDreamActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_item_home_slidemenu_jingyan:  //经验之谈
                        Intent intent1 = new Intent();
                        intent1.setClass(mContext, ExperenceActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
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
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_home_home:    //主界面/发现
                mSpinner.setVisibility(View.VISIBLE);
                mContitionMi.setVisible(true);
                changedRadioButtonByClick(mHomeBtn);
                break;
            case R.id.main_home_discover:   //计划
                mSpinner.setVisibility(View.GONE);
                changedRadioButtonByClick(mDiscoverBtn);
                mContitionMi.setVisible(false);
                break;
            case R.id.main_home_dongtai:  //动态
                mSpinner.setVisibility(View.GONE);
                changedRadioButtonByClick(mDongtaiBtn);
                mContitionMi.setVisible(false);
                break;
            case R.id.main_home_personal:  //个人中心
                mSpinner.setVisibility(View.GONE);
                changedRadioButtonByClick(mPersonerCenterBtn);
                mContitionMi.setVisible(false);
                break;
            case R.id.home_main_about_btn: //关于
                intent.setClass(mContext, AboutActivity.class);
                mContitionMi.setVisible(false);
                startActivity(intent);
                break;
            case R.id.home_main_setting_btn:  //设置:
                intent.setClass(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.home_addplan:  //添加计划:
                showBottomSheet();
                break;
        }
    }

    public void changedRadioButtonByClick(RadioButton buttonView) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> list = fm.getFragments();
        if (list != null) {
            for (Fragment fg : list) {
                ft.hide(fg);
            }
        }
        changeFocus(buttonView);
        switch (buttonView.getId()) {
            case R.id.main_home_home:
                HomeFragment homeFragment = (HomeFragment) fm
                        .findFragmentByTag("home_fragment");
                if (homeFragment == null) {
                    homeFragment = HomeFragment.getInstance();
                    ft.add(R.id.main_fragment_container, homeFragment, "home_fragment");
                }
                ft.show(homeFragment);
                break;
            case R.id.main_home_dongtai:
                HomeDongtaiFragment dongtai_fragment = (HomeDongtaiFragment) fm
                        .findFragmentByTag("dongtai_fragment");
                if (dongtai_fragment == null) {
                    dongtai_fragment = HomeDongtaiFragment.getInstance();
                    ft.add(R.id.main_fragment_container, dongtai_fragment,
                            "dongtai_fragment");
                }
                ft.show(dongtai_fragment);
                break;

            case R.id.main_home_discover:
                DiscoverFragment questionFragment = (DiscoverFragment) fm
                        .findFragmentByTag("discover_fragment");
                if (questionFragment == null) {
                    questionFragment = DiscoverFragment.getInstance();
                    ft.add(R.id.main_fragment_container, questionFragment,
                            "discover_fragment");
                }
                ft.show(questionFragment);
                break;

            case R.id.main_home_personal:
                PersonalCenterFragment personcenterFragment = (PersonalCenterFragment) fm
                        .findFragmentByTag("personal_center_fragment");
                if (personcenterFragment == null) {
                    personcenterFragment = PersonalCenterFragment.getInstance();
                    ft.add(R.id.main_fragment_container, personcenterFragment,
                            "personal_center_fragment");
                }
                ft.show(personcenterFragment);
                break;
            default:
                break;

        }
        ft.commitAllowingStateLoss();
    }

    public FragmentManager getFragmentManagerHelp() {

        FragmentManager fm = getSupportFragmentManager();
        return fm;
    }

    /**
     * 用于控制导航栏图标颜色变化
     *
     * @param btn
     */
    private void changeFocus(RadioButton btn) {
        mHomeBtn.setSelected(false);
        mDiscoverBtn.setSelected(false);
        mPersonerCenterBtn.setSelected(false);
        mDongtaiBtn.setSelected(false);
        btn.setSelected(true);

    }

    /**
     * 显示新增计划菜单
     */
    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.home_add_view_bottomsheet, null);
        View close = content.findViewById(R.id.home_addplan_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        //长期计划
        View cqjh = content.findViewById(R.id.home_addplan_cqjh);
        cqjh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(mContext, AddLongPlanActivity.class);
                startActivity(intent);
            }
        });
        //习惯壁纸
        View xgbz = content.findViewById(R.id.home_addplan_xgbz);
        xgbz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(mContext, AddWallpaperActivity.class);
                startActivity(intent);
            }
        });
        //心愿寄语
        View xyjy = content.findViewById(R.id.home_addplan_xyjy);
        xyjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(mContext, AddDreamPlanActivity.class);
                startActivity(intent);
            }
        });
        //随想笔记
        View sxbj = content.findViewById(R.id.home_addplan_sxbj);
        sxbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent intent = new Intent(mContext, AddNoteActivity.class);
                startActivity(intent);
            }
        });
        mBottomSheetDialog.contentView(content)
                .show();
    }

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
     public void setmLoadCallBackListener(LoadCallback listener){
         this.mLoadCallBack = listener;
     }
    /**
     * 一个接口，在主界面的选择计划分类和排序时需要通知GineFragment去加载数据，因此该接口是在HomeFragment中去实现的
     */
    public interface LoadCallback{
        public void doLoadForCategory(String category);
        //0表示按照时间最新、1参与最多、2执行最多、3已完成
        public void doLoadForOrder(int order);
    }
}
