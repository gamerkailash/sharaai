package com.sharaai.ukhane;

//import com.ig.gameView.RefreshHandler;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class UTV extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
//	        setRequestedOrientation(0);
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, // keep screen
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			CategoryListActivity.iratedone = false;
			
		setContentView(R.layout.utv);
		mRedrawHandler.sleep(delay);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		{
			setResult(RESULT_OK);
	        finish();
		}
//		switch(resultCode)
//	    {
//	    case CategoryListActivity.RESULT_CLOSE_ALL:
//	        setResult(CategoryListActivity.RESULT_CLOSE_ALL);
//	        finish();
//	        break;
//	        default:
//	        	setResult(CategoryListActivity.RESULT_CLOSE_ALL);
//	        finish();
//	        	break;
//	    }
//	    super.onActivityResult(requestCode, CategoryListActivity.RESULT_CLOSE_ALL, data);
	}
	public long delay=1000;
    private RefreshHandler mRedrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) 
		{
//				sleep(delay);
			CategoryListActivity.currentUser = 0;
			Intent intent = new Intent(UTV.this,CategoryListActivity.class);
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
	
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		
//		mRedrawHandler.removeMessages(0);
//		super.onBackPressed();
//		
//	}
	
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
