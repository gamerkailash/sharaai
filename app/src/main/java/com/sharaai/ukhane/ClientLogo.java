package com.sharaai.ukhane;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class ClientLogo extends Activity {

    public long delay = 1500;
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.clientlogo);
        mRedrawHandler.sleep(delay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(ClientLogo.this, UTV.class);
            startActivityForResult(intent, CategoryListActivity.RESULT_CLOSE_ALL);
            finish();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }
}
