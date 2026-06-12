package com.sharaai.ukhane;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ContactusActivity extends Activity {
	
	ProgressBar progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.contactus_layout);
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	private void init()
	{
		initDrawer();
	//	TextView tv  = (TextView) findViewById(R.id.textView_policy);
	//	tv.setText(Html.fromHtml("<html><body><font size=20sp color=red>Hello</font><font size=12sp color=white>World</font></body><html>"));
		
	//	String text1= "Privacy Policy\n";
	//	String text2= "1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s" +
	//			"1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s" +
	//			"1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s" +
	//			"1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s" +
	//			"1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s" +
	//			"1. Introduction dsds dsds dsdsd sdsds dsdsd sdsdsdsdsds dsds dsds ds assss s s s s s s s ssas   sds d sd s ds d sd s d sd s d sd s d sd s d sd s ds dd s ds dd s";
	//	String text3= "1. Introduction";
		
	//	SpannableString span1 = new SpannableString(text1);
	//	span1.setSpan(R.style.Style0, 0, text1.length(), 0);
		
	//	SpannableString span2 = new SpannableString(text2);
	//	span2.setSpan(/*new AbsoluteSizeSpan(22)*/R.style.Style10, 0, text2.length(), 0);
		
	//	CharSequence finalText = TextUtils.concat(span1,"", span2);
		//tv.setText(finalText);;
		
		
		
		
	}
	/*
	private void init()
	{
		initDrawer();
		String url = "";
		if(getIntent().getExtras()!=null)
		{
			url = getIntent().getExtras().getString("webview_url_key");
		}
		WebView mTermsCondition = (WebView) findViewById(R.id.termscondition_webview);
		
		progress = (ProgressBar) findViewById(R.id.progressbarId);
		progress.getIndeterminateDrawable();
//	    .setColorFilter(getResources().getColor(R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
		mTermsCondition.setWebViewClient(new WebViewClient(){
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url){
		      view.loadUrl(url);
		      return true;
		    }
			@Override
			public void onPageFinished(WebView view, String url) {
				progress.setVisibility(View.GONE);
			}
		});
		mTermsCondition.loadUrl(url);
	}*/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	
	/*final*/ DrawerLayout drawer;
	ListView navList;
	RelativeLayout rr;
	private DrawerViewAdapter mStatusAdapter ;
	public void initDrawer(){
		mStatusAdapter = new DrawerViewAdapter(this, Utilities.getDrawerData(), Utilities.getDrawerIconData());
		
		
	     /*final DrawerLayout*/ drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
	     /*final*/ /*ListView*/ navList = (ListView) findViewById(R.id.drawer);
//	     navList.setAdapter(adapter);
	     
	     LayoutInflater myinflater = getLayoutInflater();
	     ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.header_drawer, navList, false);
	     navList.addHeaderView(myHeader, null, false);
	     	
	     navList.setAdapter(mStatusAdapter);
	     
	     navList.setOnItemClickListener(new OnItemClickListener(){
	             public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
	            	 Utilities.onDrawerItemClick(ContactusActivity.this,pos);
	                     drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
	                             @Override
	                             public void onDrawerClosed(View drawerView){
	                                     super.onDrawerClosed(drawerView);

	                             }
	                     });
	                     drawer.closeDrawer(navList);
	             }
	     });
	}
	public void openDrawer(View v){
		drawer.openDrawer(navList);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		{
			setResult(RESULT_OK);
	        finish();
		}
	    
	}

}
