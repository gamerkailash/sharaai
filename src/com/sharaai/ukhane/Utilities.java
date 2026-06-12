package com.sharaai.ukhane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.inmobi.re.controller.util.StartActivityForResultCallback;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Toast;

public class Utilities  {

	private static String TAG = "Utilities";
	public static ArrayList<String> getDrawerData(){

		ArrayList<String> mStatusList = new ArrayList<String>();
		mStatusList.add("HOME");
		mStatusList.add("APPS & GAMES");
		mStatusList.add("CONTACT US");
		mStatusList.add("RATE");
		mStatusList.add("PRIVACY POLICY");
		return mStatusList;

	}
	public static ArrayList<Integer> getDrawerIconData(){

		ArrayList<Integer> mIconList = new ArrayList<Integer>();
		mIconList.add(R.drawable.app_home);
		mIconList.add(R.drawable.app_appgames);
		mIconList.add(R.drawable.app_contactus);
		mIconList.add(R.drawable.app_rate);
		mIconList.add(R.drawable.app_policy);
		return mIconList;

	}
	static int RESULT_CLOSE_ALL = 2;
	public static void onDrawerItemClick(Activity a, int pos){
		Intent intent = null;
		
		switch(pos){
		case 1:
			
			
//			Activity a = (Activity) mContext;
			CategoryListActivity.currentUser = 0;
			CategoryListActivity.lastUser=3;
			MessageListActivity.currentUser = 0;
			intent = new Intent(a, CategoryListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			a.startActivity(intent);
			//a.startActivityForResult(intent, RESULT_CLOSE_ALL);
			a.setResult(a.RESULT_OK);
			//a.finish();
			//a.startActivityForResult(new Intent(a, CategoryListActivity.class), RESULT_CLOSE_ALL);

			break;
		case 2:
			intent = new Intent(a, AppGamesActivity.class);
			//a.startActivity(intent);
			a.startActivityForResult(intent, RESULT_CLOSE_ALL);
			break;
		case 3:
			//intent = new Intent(a, ContactusActivity.class);
			
		//	intent.putExtra("webview_url_key","http://www.sharaai.com/home/contact-us");
			//a.startActivity(intent);
			sendEmail(a);
			break;
		case 4:
			String APP_PNAME = "com.sharaai.ukhane";
			 a.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
			break;
		case 5:
			intent = new Intent(a, ContactusActivity.class);
			
			intent.putExtra("webview_url_key","http://www.sharaai.com/home/about/privacy-policy");
			//a.startActivity(intent);
			a.startActivityForResult(intent, RESULT_CLOSE_ALL);
			break;
			
		}
	}
	
	public static void launchGame(Activity a, int pos){
		String APP_PNAME = "com.sharaai.ukhane";
		switch(pos){
		case 1:
			APP_PNAME = "com.sharaai.desertsafari";
			break;
		case 2:
			APP_PNAME = "com.sharaai.icecontrol";
			break;
		case 3:
			APP_PNAME = "com.sharaai.santatrouble";
			break;
		}
		a.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
	}
	public static void sendEmail(Activity activity){
		Intent i = new Intent(Intent.ACTION_SENDTO);
		//i.setType("application/octet-stream");
		i.setData(Uri.parse("mailto:"));
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@sharaai.com"});
		//i.putExtra(Intent.EXTRA_SUBJECT, /*"subject of email"*/Name);
//		i.putExtra(Intent.EXTRA_TEXT   , /*"body of email"*/Html.fromHtml(getResources().getString(R.string.downloadlink)));
//		i.setType("application/octet-stream");
		try {
			activity.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(activity, "Please configure your E-mail account and try again", Toast.LENGTH_SHORT).show();
		}
		
	
	}
}