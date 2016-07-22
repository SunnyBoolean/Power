package com.geo.power.ui.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.geo.com.geo.power.util.BitmapUtils;
import com.geo.com.geo.power.util.DensityUtil;
import com.geo.com.geo.power.util.ScreenUtil;
import com.geo.widget.colorpicker.ColorPicker;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Slider;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.FileOutputStream;
import java.util.ArrayList;

import ui.geo.com.power.R;


/**
 * Created by Administrator on 2016/6/3.
 * 增加习惯壁纸
 */
public class AddWallpaperActivity extends BaseActivity implements OnGestureListener {
    private LinearLayout mLineaLayoutContainer, mInputContentContainer;
    private BottomSheetDialog mBottomSheetDialog;
    private Bitmap mBgBitmap,mGeneralBitmap;
    private Paint mPaint;
    GestureDetector detector;
    /**
     * 绘制画布
     */
    private Canvas mBitmapCanvas;
    /**
     * 文本字体大小
     */
    private int mTextSize = 14;
    /**
     * 字体颜色
     */
    private int mTextColor = Color.BLACK;

    private Slider mTextSizeSlider;
    private GridView mColorGridView;
    /**
     * 输入的文本
     */
    private String mInputContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwallpaper);
        detector = new GestureDetector(this, this);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initCompontent() {
        super.initCompontent();
        mLineaLayoutContainer = (LinearLayout) findViewById(R.id.add_wallpaper_container);
        View seleBg = findViewById(R.id.add_wallpaper_bg_w);
        //跳转选择背景图片
        View addContent = findViewById(R.id.add_wallpaper_content_s);
        View textType = findViewById(R.id.add_wallpaper_content_texttype);
        View textColor = findViewById(R.id.add_wallpaper_content_textcolor);
        mColorGridView = (GridView) findViewById(R.id.color_picker_gridview);
        mInputContentContainer = (LinearLayout) findViewById(R.id.add_wallpaper_container_content);
        textType.setOnClickListener(this);
        addContent.setOnClickListener(this);
        seleBg.setOnClickListener(this);
        textColor.setOnClickListener(this);
    }

    @Override
    public void handlOnClickListener(View v) {
        super.handlOnClickListener(v);
        switch (v.getId()) {
            case R.id.add_wallpaper_bg_w:  //选择背景图片
//                Intent intent = new Intent(mContext, WallPaperSelectActivity.class);
//                startActivityForResult(intent, 1);
                ImageSelectorActivity.start(AddWallpaperActivity.this, 1, ImageSelectorActivity.MODE_SINGLE, true, true, false);
                break;
            case R.id.add_wallpaper_content_s: //添加文本
                showDialogForTextContent();
                break;
            case R.id.add_wallpaper_content_texttype:  //字体
                showBottomSheetForTextSize();
                break;
            case R.id.add_wallpaper_content_textcolor:  //字体颜色
                setContentColor();
                break;
        }
    }

    private void setContentColor() {
        final ColorPicker colorPicker = new ColorPicker(mContext);
        colorPicker.setDefaultColorButton(Color.BLACK).setColumns(5).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                mTextColor = color;
                changeTextStyle(true);
                colorPicker.dismissDialog();
            }
        }).addListenerButton("newButton", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int color) {
                mTextColor = color;
                changeTextStyle(true);
                colorPicker.dismissDialog();
            }
        }).setRoundColorButton(true);
        colorPicker.disableDefaultButtons(false);
        colorPicker.show();
    }

    private void changeTextStyle(boolean isColor) {
        int childs = mInputContentContainer.getChildCount();
        for (int i = 0; i < childs; i++) {
            TextView tv = (TextView) mInputContentContainer.getChildAt(i);
            if (isColor) {
                tv.setTextColor(mTextColor);
            } else {
                tv.setTextSize(mTextSize);
            }
        }
    }

    /**
     * 设置背景文字
     */
    private void setContentForWallpaper() {
        TextView tv = new TextView(mContext);
        tv.setText(mInputContent);
        tv.setTextColor(mTextColor);
        tv.setTextSize(mTextSize);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginTop = DensityUtil.dip2px(mContext, 25);
        params.gravity = Gravity.CENTER;
        params.topMargin = marginTop;
        tv.setLayoutParams(params);
        mInputContentContainer.addView(tv);

        //如果对添加的文字不满意可以长按删除
        int childs = mInputContentContainer.getChildCount();
        drawTextAtBitmap(mInputContent);
        for (int i = 0; i < childs; i++) {
            final TextView temp = (TextView) mInputContentContainer.getChildAt(i);
            //长按删除
            temp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteContentItem(temp);
                    return false;
                }
            });
        }
    }

    private void deleteContentItem(final TextView textv) {
        Dialog.Builder build = new Dialog.Builder(R.style.SimpleDialogLight) {
            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv = (TextView) dialog.findViewById(R.id.telete_add_wallp_ctext_item);
                tv.setText("\"" + textv.getText().toString() + "\"");
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                mInputContentContainer.removeView(textv);

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        build.title("确认删除内容：")
                .positiveAction("删除")
                .negativeAction("取消")
                .contentView(R.layout.delete_add_wallpaper_content_item);

        DialogFragment fragment = DialogFragment.newInstance(build);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 输入内容
     */
    private void showDialogForTextContent() {
        Dialog.Builder build = new Dialog.Builder(R.style.SimpleDialogLight) {
            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                EditText et_pass = (EditText) fragment.getDialog().findViewById(R.id.custom_et_password);
                mInputContent = et_pass.getText().toString();
                setContentForWallpaper();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        build.title("壁纸内容")
                .positiveAction("完成")
                .negativeAction("取消")
                .contentView(R.layout.add_wallpaper_text_content);

        DialogFragment fragment = DialogFragment.newInstance(build);
        fragment.show(getSupportFragmentManager(), null);
    }

    /**
     * 选择字体
     */
    private void showBottomSheetForTextSize() {
        mBottomSheetDialog = new BottomSheetDialog(mContext, R.style.Material_App_BottomSheetDialog);
        View content = LayoutInflater.from(mContext).inflate(R.layout.add_wallpaper_texttype_layout, null);
        mBottomSheetDialog.contentView(content)
                .show();
        //字体大小
        mTextSizeSlider = (Slider) content.findViewById(R.id.add_wallpaper_textsize_slider);
        mTextSizeSlider.setPrimaryColor(mCommonColor);
        mTextSizeSlider.setValueRange(3, 32, true);
        mTextSizeSlider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                mTextSize = view.getValue();
                changeTextStyle(false);

            }
        });
        com.rey.material.widget.Button cancer = (Button) content.findViewById(R.id.add_wallpaper_textsize_slder_cancel);
        cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        com.rey.material.widget.Button confirm = (Button) content.findViewById(R.id.add_wallpaper_textsize_slider_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSize = mTextSizeSlider.getValue();
                mBottomSheetDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            if (images != null && images.size() > 0) {
                String url = images.get(0);
                mBgBitmap = BitmapUtils.getBitmap(AddWallpaperActivity.this, url);
                Drawable drawable = new BitmapDrawable(mBgBitmap);
                mInputContentContainer.setBackground(drawable);

                int x = mBgBitmap.getWidth();
                int y = mBgBitmap.getHeight(); // 创建一个和原图同样大小的位图
                mGeneralBitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
                mBitmapCanvas = new Canvas(mGeneralBitmap);
                mPaint = new Paint(); // 在原始位置0，0插入原图
                mBitmapCanvas.drawBitmap(mBgBitmap, 0, 0, mPaint);
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
        mToolBar.setTitle("壁纸");
        Menu menu = mToolBar.getMenu();
        //第二个参数是itemid，就根据这个来判断单击事件了
        MenuItem item = menu.add(0, 1, 2, "完成");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 1 && mBgBitmap != null) {
                    wallPaperDone();
                } else {
                    Toast.makeText(mContext, "请先设置壁纸图片", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }


    private Bitmap drawTextAtBitmap(String text) {
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize); // 在原图指定位置写上字
        mBitmapCanvas.drawText(text, 53, 30, mPaint);
        mBitmapCanvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        mBitmapCanvas.restore();
        return mGeneralBitmap;
    }

    private void wallPaperDone() {

        //打开图像缓存
        try {
//            View content = View.inflate(mContext, R.layout.activity_addwallpaper, null);
//            mInputContentContainer.setDrawingCacheEnabled(true);
//            int[] size = ScreenUtil.getScreenSize(mContext);
//            Bitmap bit = Bitmap.createBitmap(mInputContentContainer.getWidth(), mInputContentContainer.getHeight(),
//                    Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bit);
//            mInputContentContainer.draw(canvas);
//            //将截图保存在SD卡根目录的test.png图像文件中
//            FileOutputStream fosa = new FileOutputStream("/sdcard/test.png");
//            //将Bitmap对象中的图像数据压缩成png格式的图像数据，并将这些数据保存在test.png文件中
//            bit.compress(Bitmap.CompressFormat.PNG, 100, fosa);
//            //关闭文件输出流
//            fosa.close();

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            wallpaperManager.setBitmap(mGeneralBitmap);
//            setWallpaper(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * Notified when a long press occurs with the initial on down {@link MotionEvent}
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float startY = e1.getRawY();
        float endY = e2.getRawY();
        float result = endY - startY;
        if (result > 0) {
            showSnackBar("向下滑动");
            if (mToolBar != null) {
                mToolBar.setVisibility(View.GONE);
            }
        } else {
            showSnackBar("向上滑动");
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

}
