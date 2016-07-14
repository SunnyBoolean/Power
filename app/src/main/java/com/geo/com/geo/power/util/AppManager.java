package com.geo.com.geo.power.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.geo.com.geo.power.Constants;
import com.geo.com.geo.power.bean.UserInfo;
import com.geo.power.ui.PowerApplication;


/**
 * 获取本应用平台的安装包信息
 * 主要是在检查版本更新时会获取版本号来比较
 * @author liwei
 * 
 */
public class AppManager {
	public static PackageInfo getPackageInfo(Context context){
		PackageManager pm = context.getPackageManager();
		PackageInfo info =null;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 退出应用
	 * @param context
	 */
	public static void exitApp(Context context){
		for(Activity activity: PowerApplication.mActivis){
			activity.finish();
		}
		SharedPreferences mSharedPreference= context.getSharedPreferences(Constants.SP_NAME,
				Activity.MODE_PRIVATE);
		mSharedPreference.edit().putBoolean(Constants.SP_KEY_ISFIRSTUSE,false).commit();
	}
}
