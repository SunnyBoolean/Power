package com.geo.power.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.geo.com.geo.power.bean.UserInfo;
import com.geo.com.geo.power.util.BitmapUtils;
import com.rey.material.widget.EditText;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import ui.geo.com.power.R;

/**
 * Created by 李伟 on 2016/6/22.
 * 账号注册下一步资料完整
 */
public class RegisterNextActivity extends BaseActivity {
    private ImageView mUserImg;
    private EditText mNickEt, mPaswdEt, mConfirmEt;
    private Button mRegisterBtn;
    /**
     * 电话号
     */
    private String mPhoneNumber;
    /**
     * 图像
     */
    private String mUserImurl;
    /**
     * 昵称
     */
    private String mNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_next_layout, false);
    }

    @Override
    protected void initCompontent() {
        super.initCompontent();
        mUserImg = (ImageView) findViewById(R.id.register_uimg_select);
        mNickEt = (EditText) findViewById(R.id.register_usernick_input);
        mPaswdEt = (EditText) findViewById(R.id.register_asd_password);
        mConfirmEt = (EditText) findViewById(R.id.register_asd_password_confirm);
        mRegisterBtn = (Button) findViewById(R.id.register_next_btn_send);
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra("phoneNum");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mUserImg.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.register_next_btn_send:  //开始注册
                sigin();
                break;
            case R.id.register_uimg_select:  //选择头像
                ImageSelectorActivity.start(RegisterNextActivity.this, 15, ImageSelectorActivity.MODE_SINGLE, true, true, true);

                break;
        }
    }

    private boolean checkRegis() {
        String paswd = mPaswdEt.getText().toString().trim();
        String cpaswd = mConfirmEt.getText().toString().trim();
        if (TextUtils.isEmpty(paswd)) {
            return false;
        }
        if (TextUtils.isEmpty(cpaswd)) {
            return false;
        }
        if (!paswd.equals(cpaswd)) {
            return false;
        }
        return true;
    }

    private void sigin() {
        mNick = mNickEt.getText().toString().trim();
        if (!checkRegis()) {
            showToast("两次密码输入不一致");
            return;
        }
        //首先检查该昵称是否已被注册，如果昵称已被注册则不允许使用
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("username", mNick);
        query.findObjects(mContext, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> object) {
                if (object == null || object.size() <= 0) {
                    //账号已存在
                    showToast("账号已存在");
                    return;
                }


                //上传头像
                final BmobFile bmobFile = new BmobFile(new File(mUserImurl));
                //首先上传图头像图片
                bmobFile.uploadblock(mContext, new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        //如果头像上传成功就开始注册
                        //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                        String fullUrl = bmobFile.getFileUrl(mContext);
                        UserInfo bu = new UserInfo();
                        bu.setUsername(mNick);
                        bu.uimg = fullUrl;
                        bu.setPassword("123");
                        bu.setMobilePhoneNumber(mPhoneNumber);
//注意：不能用save方法进行注册
                        bu.signUp(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                                //通过BmobUser.getCurrentUser(context)方法获取登录成功后的本地用户信息
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                showToast("注册失败");
                                // TODO Auto-generated method stub
                            }
                        });

                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showToast("头像上传失败");
                    }
                });

            }

            @Override
            public void onError(int code, String msg) {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选中的图片
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            String str = images.get(0);
            if (!TextUtils.isEmpty(str)) {
                Bitmap bitmap = BitmapUtils.getBitmap(RegisterNextActivity.this, str);
                mUserImg.setImageBitmap(bitmap);
                mUserImurl = str;
            }
        }
    }
}
