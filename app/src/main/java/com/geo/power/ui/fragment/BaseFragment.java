package com.geo.power.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/18.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    public Context mContext;
    public SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        handlerClick(v);
    }

    protected void handlerClick(View view) {

    }

    /**
     * 显示提示文字
     * @param contentview
     * @param content
     */
    public void showSnackBar(View contentview, String content) {
        Snackbar.make(contentview, content, Snackbar.LENGTH_LONG)
                .setAction("关闭", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    final public String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));
    }
    /**
     * 这个接口用于Fragmen和Fragment之间的通信
     */
    public interface TaskCallback {
        public void handleTask();
    }
}
