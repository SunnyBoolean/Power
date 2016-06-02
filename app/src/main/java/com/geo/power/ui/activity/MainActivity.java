package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.power.ui.fragment.DiscoverFragment;
import com.geo.power.ui.fragment.HomeFragment;
import com.geo.power.ui.fragment.PersonalCenterFragment;

import android.support.design.widget.FloatingActionButton;

import java.util.List;

import ui.geo.com.power.R;

public class MainActivity extends BaseActivity implements DiscoverFragment.FMCallback {
    private RadioButton mHomeBtn, mDiscoverBtn, mPersonerCenterBtn;
    private TextView mSettingBtn, mAboutBtn;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mAddPlanFAB;
    private NavigationView mNavigationView;

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
        mSettingBtn = (TextView) findViewById(R.id.home_main_setting_btn);
        mAboutBtn = (TextView) findViewById(R.id.home_main_about_btn);
        mAddPlanFAB = (FloatingActionButton) findViewById(R.id.home_addplan);
        changedRadioButtonByClick(mHomeBtn);
        setSlideMenuWidth();
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

        MenuItem itemmore = menu.add(0, 2, 3, "更多");
        itemmore.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemmore.setIcon(R.drawable.home_main_more);
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
                        showToast("选择更多");
                        break;
                    case android.R.id.home:

                        Toast.makeText(MainActivity.this, "你单击Home键", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }


    protected void initListener() {

        mHomeBtn.setOnClickListener(this);
        mDiscoverBtn.setOnClickListener(this);
        mPersonerCenterBtn.setOnClickListener(this);
        mAddPlanFAB.setOnClickListener(this);
//        mSettingBtn.setOnClickListener(this);
//        mAboutBtn.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                //首先关闭菜单
                mDrawerLayout.closeDrawers();
                switch (id) {
                    case R.id.menu_item_home_slidemenu_xiguan: //习惯壁纸
                        changedRadioButtonByClick(mHomeBtn);
                        break;
                    case R.id.menu_item_home_slidemenu_xinyuan: //心愿寄语
                        changedRadioButtonByClick(mHomeBtn);
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
    protected void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_home_home:
                changedRadioButtonByClick(mHomeBtn);
                break;
            case R.id.main_home_discover:
                changedRadioButtonByClick(mDiscoverBtn);
                break;
            case R.id.main_home_personal:
                changedRadioButtonByClick(mPersonerCenterBtn);
                break;
            case R.id.home_main_about_btn: //关于
                intent.setClass(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.home_main_setting_btn:  //设置:
                intent.setClass(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.home_addplan:  //添加计划:
                intent.setClass(mContext, AddPlanActivity.class);
                startActivity(intent);
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
            case R.id.main_home_discover:
                DiscoverFragment questionFragment = (DiscoverFragment) fm
                        .findFragmentByTag("discover_fragment");
                if (questionFragment == null) {
                    questionFragment = DiscoverFragment.getInstance();
                    ft.add(R.id.main_fragment_container, questionFragment,
                            "discover_fragment");
                }
                questionFragment.setFMCallback(this);
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
        btn.setSelected(true);

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

    @Override
    public FragmentManager getFragmentM() {
        return getFragmentManagerHelp();
    }
}
