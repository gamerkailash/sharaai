package com.sharaai.ukhane;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class FacebookPost extends Activity {
 
	public static String messageToPostOnFacebook = "";
    private static final String APP_ID = "540162482724625";//"540162482724625";//"1404595479775267";//"540162482724625";//"467880969921913";
    //                     ///////////////540162482724625
    private static final String[] PERMISSIONS = new String[]{"publish_stream"};
 
    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-credentials";

    private Facebook facebook;
    private String messageToPost;
//    private String fbdata;
    public static int distanceCovered = 0;

    public boolean saveCredentials(Facebook facebook) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, facebook.getAccessToken());
        editor.putLong(EXPIRES, facebook.getAccessExpires());
        return editor.commit();
    }

    public boolean restoreCredentials(Facebook facebook) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
        facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
        return facebook.isSessionValid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		
		setContentView(R.layout.pausemenu);
        
        
        facebook = new Facebook(APP_ID);
        
        restoreCredentials(facebook);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        ///setContentView(R.layout.facebook_layout);

        String facebookMessage = getIntent().getStringExtra("facebookMessage");
        if (facebookMessage == null) {
            facebookMessage = "Has Made New High Score :"  ;
        }
        messageToPost = facebookMessage;
        
        if (!facebook.isSessionValid()) {
            loginAndPostToWall();
        } else {
            postToWall(messageToPost);
        }
    }

    public void doNotShare(View button) {
        finish();
    }

    public void share(View button) {
        if (!facebook.isSessionValid()) {
            loginAndPostToWall();
        } else {
            postToWall(messageToPost);
        }
    }

    public void loginAndPostToWall() {
        facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
    }

    public void postToWall(String message) {
//        Bundle parameters = new Bundle();
    	new MyAsyncTask().execute(message);
    	showToast("Please wait...");
    	System.out.println(" xxxx zzzzzzzzzzzzzz111111111finish();");
    	finish();
//        parameters.putByteArray("picture",  MessageDetailsActivity.bitMapData);
//        parameters.putString("picture", "https://lh4.ggpht.com/mn0UTMdRzN6D51A_W3PMMsAcy6zO1HBpmH470kkyJbd5N0xqLlqvK6jgz18fScixPjjG");
       
        
        ///////////wwwww
//        parameters.putString("description","a Bubbley is a playful fish, who makes bubbles of different  colors.");
//        parameters.putString("link", "https://play.google.com/store/apps/details?id=com.game.bubbley");
//        parameters.putString("name", "Bubbley's Bubbles");
//        parameters.putString("caption", "Get it now for your Android Phone.");
//       
//        String ss = getResources().getString(R.string.messagedetailheading);
//        parameters.putString("message", ss+" has just scored 4000" +  " while playing Bubbley's Bubbles game on Android Phone.");
      ////////////wwwww
//        messageToPostOnFacebook = "Hi";
//        System.out.println(" xxxx zzzzzzzzzzz "+messageToPostOnFacebook);
//        parameters.putString("message", messageToPostOnFacebook);
//        try {
//            facebook.request("me");
//            String response = facebook.request("me/feed", parameters, "POST");
//            Log.d("Tests", "got response: " + response);
//            if (response == null || response.equals("") ||
//                    response.equals("false")) {
//                showToast("Blank response.");
//            } else {
//                showToast("Message posted to your facebook wall!");
//            }
//            finish();
//        } catch (Exception e) {
//            showToast("Failed to post to wall!");
////            dataReadWriteOperation(DATA_WRITE);
//            e.printStackTrace();
//            finish();
//        }
    }

   
    public static byte[] bitMapData;  
    public static Bitmap bitmap1;
    
    class MyAsyncTask extends AsyncTask<String,Void,Boolean>
    {
    	public Boolean doInBackground(String ...message){

//    		FileOutputStream outStream; 
//   		 File file;
//   		        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//   		        	
//   		    file = new File(extStorageDirectory, "ic_launcher.PNG");
//   		    try {
//   		        outStream = new FileOutputStream(file);
//   		     bitmap1.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//   		        outStream.flush();
//   		        outStream.close();
//   		    } catch (FileNotFoundException e) {
//   		        e.printStackTrace();
//   		    } catch (IOException e) {
//   		        e.printStackTrace();
//   		    }
   		    
   		    
   		    
//    		Bundle parameters = new Bundle();
//    		parameters.putString("message", message[0]);
//    		parameters.putString("description", "topic share");
    		 Bundle parameters = new Bundle();
//     		messageToPostOnFacebook = "Hi";
             System.out.println(" xxxx zzzzzzzzzzz "+messageToPostOnFacebook);
            // if(bitmap1==null)
            // {
            //	 System.out.println(" xxxx mmmmmmmmmmmmmmmmmmmmmmmmmqqqqqqqqq");
            // }
             //parameters.putParcelable("picture", bitmap1);
             parameters.putString("message", messageToPostOnFacebook);
            

             parameters.putByteArray("picture", bitMapData);
//             parameters.putString("picture", Uri.fromFile(file));
             
//             parameters.putp
    		try {
//    			facebook.request("me");   // <-------------  here it fails and jups to catch
//    			String response = facebook.request("me/feed", parameters, "POST");
    			facebook.request("me");
    			String response = facebook.request("me/photos", parameters, "POST");
                
    			Log.d("Tests", "got response: " + response);
    			if (response == null || response.equals("") ||
    					response.equals("false")) {
    				return Boolean.FALSE;
    			}
    			else {
    				return Boolean.TRUE;
    			}
    		} catch (Exception e) {

    			e.printStackTrace();
    			return Boolean.FALSE;
    		}
    	} 

    	public void onPostExecute(Boolean result){
    		if(result == Boolean.TRUE){
    			showToast("posted successfully");
    		}else{
    			showToast("couldn't post to FB.");
    		}
//    		finish();
    	}
    }
    
    
    
    class LoginDialogListener implements Facebook.DialogListener {
        public void onComplete(Bundle values) {
            saveCredentials(facebook);
            if (messageToPost != null) {
                postToWall(messageToPost);
            }
        }

        public void onFacebookError(FacebookError error) {
        	System.out.println(" xxxx kkkkkkkkkk11  "+error.getMessage());
            showToast("Authentication with Facebook failed!");
            finish();
        }

        public void onError(DialogError error) {
        	System.out.println(" xxxx kkkkkkkkkk22  "+error.getMessage()); 
            showToast("Authentication with Facebook failed!");
            finish();
        }

        public void onCancel() {
            showToast("Authentication with Facebook cancelled!");
            finish();
        }
    }


//    public void parseFacebookData() {
//        String fbUrl = "http://games.igstudios.in/igpromo/getpromo/getPostData.php?";//com.indiagames.testandroid
//        String appname = "com.indiagames.barfi";//"testapp";//com.indiagames.ipl2012KKR";//com.indiagames.testandroid;
//        String reqParam = "appname=" + appname + "&p=newandroid";
//        fbUrl = fbUrl + reqParam;
//        fbdata = ConnectData(fbUrl);
//        ////Log.d(TAG,"fbData="+fbdata);
//
//    }

    public String ConnectData(String urlStr) {
        try {
            String content = "";
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 1000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 2000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpGet request = new HttpGet(urlStr);
            HttpResponse response = client.execute(request);
            // Get the response
            InputStreamReader in = new InputStreamReader(response.getEntity().getContent());
            BufferedReader rd = new BufferedReader(in);
            String line = "";
            String message = "";
            while ((line = rd.readLine()) != null) {
                content = content + line;
            }
            in.close();
            return content;
        } catch (Exception e) {
//			Log.d(TAG,"Error in ConnectData="+e);
            return "";
        }

    }

    private void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    	Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT); 
    	
       
         toast.setGravity(Gravity.CENTER, 0, 0); 
        toast.show();  
    }

//    public void dataReadWriteOperation(byte modeOfOperation) {
//
//       if (modeOfOperation == DATA_WRITE) {
//            SharedPreferences.Editor editor = Barfi_InterfaceActivity.storeItemSharedPreferences.edit();
//            editor.putBoolean("misHeighScore",true);
//            editor.commit();
//        }
//
//    }
}
