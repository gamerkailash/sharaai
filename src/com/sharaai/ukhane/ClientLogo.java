package com.sharaai.ukhane;

 
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.inmobi.commons.InMobi;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;
public class ClientLogo extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.clientlogo);
		mRedrawHandler.sleep(delay);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		{
			setResult(RESULT_OK);
	        finish();
		}
	}
	public long delay=1000;
    private RefreshHandler mRedrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) 
		{
			Intent intent = new Intent(ClientLogo.this,UTV.class);
			startActivityForResult(intent, CategoryListActivity.RESULT_CLOSE_ALL);
			finish();
		}

		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
			super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mRedrawHandler.removeMessages(0);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mRedrawHandler.sleep(1000);
		super.onResume();
	}
	
}
