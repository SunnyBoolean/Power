package com.geo.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.webkit.WebView;

/**
 * 封装了WebView的下拉刷新
 * 
 * @author Li Hong
 * @since 2013-8-22
 */
public class PullToRefreshWebView extends PullToRefreshBase<WebView> {
    /**
     * 构造方法
     * 
     * @param context context
     */
    public PullToRefreshWebView(Context context) {
        this(context, null);
    }
    
    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @see com.
     */
    @Override
    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        WebView webView = new WebView(context);
        return webView;
    }

    /**
     * @see
     */
    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    /**
     * @see
     */
    @Override
    protected boolean isReadyForPullUp() {
        float value = mRefreshableView.getContentHeight() * mRefreshableView.getScale();
        float exactContentHeight = (float) Math.floor(value);
        return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
    }
}
