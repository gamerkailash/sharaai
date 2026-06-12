package com.sharaai.ukhane;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class FacebookPost extends Activity {

    public static String messageToPostOnFacebook = "";
    public static byte[] bitMapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        shareMessage();
        finish();
    }

    private void shareMessage() {
        if (messageToPostOnFacebook == null || messageToPostOnFacebook.isEmpty()) return;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToPostOnFacebook);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
