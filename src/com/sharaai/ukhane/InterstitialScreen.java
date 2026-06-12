package com.sharaai.ukhane;


import java.util.Map;

import com.inmobi.monetization.IMErrorCode;
import com.inmobi.monetization.IMInterstitial;
import com.inmobi.monetization.IMInterstitialListener;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class InterstitialScreen {

	public void initAd(Context context)
	{
		interstitial = new IMInterstitial((Activity) context, "58597300c5d0468691396967233bb27c");
		
		System.out.println("sssssssss 1pppp 0000");
		
		adInterstitialListener = new AdInterstitialListener();
		interstitial.setIMInterstitialListener(adInterstitialListener);
		interstitial.loadInterstitial();

	}
	
	
	
	public void onShowAd(View view){
		if(interstitial != null) 
			interstitial.show();
	}
	private IMInterstitial interstitial;
	private AdInterstitialListener adInterstitialListener;

	class AdInterstitialListener implements  IMInterstitialListener {

//		@Override
		public void onLeaveApplication(IMInterstitial arg0) {
			System.out.println("sssssssss 1pppp 1111");
//			handler.sendEmptyMessage(ON_LEAVE_APP);			
		}
//		@Override
		public void onDismissInterstitialScreen(IMInterstitial arg0) {
			System.out.println("sssssssss 1pppp 2222");
//			handler.sendEmptyMessage(ON_DISMISS_MODAL_AD);
		}

//		@Override
		public void onInterstitialFailed(IMInterstitial arg0, IMErrorCode eCode) {
			System.out.println("sssssssss 1pppp 3333");
//			Message msg = handler.obtainMessage(AD_REQUEST_FAILED);
//			msg.obj = eCode;
//			handler.sendMessage(msg);	
		}

//		@Override
		public void onInterstitialInteraction(IMInterstitial arg0,
				Map<String, String> arg1) {
			System.out.println("sssssssss 1pppp 4444");
			// no-op
		}

//		@Override
		public void onInterstitialLoaded(IMInterstitial arg0) {
			System.out.println("sssssssss 1pppp 5555");
//			handler.sendEmptyMessage(AD_REQUEST_SUCCEEDED);	
			onShowAd(null);
		}

//		@Override
		public void onShowInterstitialScreen(IMInterstitial arg0) {
//			handler.sendEmptyMessage(ON_SHOW_MODAL_AD);
			System.out.println("sssssssss 1pppp 6666");
		}
	};
	
}