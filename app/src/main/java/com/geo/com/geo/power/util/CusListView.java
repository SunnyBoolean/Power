package com.geo.com.geo.power.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 这个主要是解决ListView和ScrollView冲突而重写的的一个ListView
 * 
 * @author liwei
 * 
 */
public class CusListView extends ListView {
	public CusListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CusListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CusListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

		MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);

	}
}
