package com.sharaai.ukhane;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyApp extends Application {
	private static MyApp instance = new MyApp();
	public MyApp()
	{
		instance = this;
	}
	public static Context getContext()
	{
		return instance;
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
		
//		Parse.initialize(this, "ziMIn8fANsCvRc3mK9Hl95mtFzN5OF7UeNde3Sgh", "Eo8pc27hwXnPWW8lorZD4o4PWwYWy95r0md3qh23");
		
		 
//		Parse.initialize(this, "ziMIn8fANsCvRc3mK9Hl95mtFzN5OF7UeNde3Sgh", "Eo8pc27hwXnPWW8IorZD4o4PWwYWy95r0md3qh23");
		Parse.initialize(this, "d1ZzSTz7pPq1HYmE85BBzRtYYFUiPT4eEWGjsYCs", "FLvCiyJR5nOnhjj5YQvsFWaQKl7eiwPItDokp7st");

		//ParseObject testObject = new ParseObject("TestObject");
		PushService.setDefaultPushCallback(this, ClientLogo.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
//		ParseAnalytics.trackAppOpened(getIntent());
	}
}