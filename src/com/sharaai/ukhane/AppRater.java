package com.sharaai.ukhane;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppRater {
    private final static String APP_TITLE = "Ukhane";// App Name
    private final static String APP_PNAME = "com.sharaai.ukhane";//"com.whatsapp";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 2;//3;//Min number of days 
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches
 
    public static void app_launched(Context mContext) {
    	SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }   
    public static AlertDialog alertDialog;
    public static AlertDialog.Builder builder;
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
//        final Dialog dialog = new Dialog(mContext);
        
        builder = new AlertDialog.Builder(mContext);
        alertDialog = builder.create();
        
//        /*dialog*/alertDialog.setTitle("Rate " + APP_TITLE);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(mContext);
     // You Can Customise your Title here 
     title.setText("Rate " + APP_TITLE);
     title.setBackgroundColor(Color.DKGRAY);
     title.setPadding(10, 10, 10, 10);
     title.setGravity(Gravity.CENTER);
     title.setTextColor(Color.WHITE);
     title.setTextSize(20);

     /*dialog*/alertDialog.setCustomTitle(title);
     
        
        TextView tv = new TextView(mContext);
        tv.setText("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");
        tv.setWidth((int) (CategoryListActivity.width*2/3));
        tv.setPadding(4, 0, 4, 10);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ll.addView(tv);
        
 
        Button b1 = new Button(mContext);
        b1.setText("Rate " + APP_TITLE);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                /*dialog*/alertDialog.dismiss();
            }
        });        
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	/*dialog*/alertDialog.dismiss();
            	editor.putLong("date_firstlaunch", System.currentTimeMillis());
            	editor.commit();
            }
        });
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                /*dialog*/alertDialog.dismiss();
            }
        });
        ll.addView(b3);

        ll.setBackgroundColor(Color.TRANSPARENT);
		
        alertDialog.setCanceledOnTouchOutside(false);        
        /*dialog*/alertDialog.setView(ll);//ContentView(ll);  
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ((Activity) mContext).getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        /*dialog*/alertDialog.show();        
    }
}