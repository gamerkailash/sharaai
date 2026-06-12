package com.sharaai.ukhane;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.facebook.android.Facebook;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.sharaai.ukhane.ClientLogo.RefreshHandler;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Telephony;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
//import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager; 
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
//import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class MessageDetailsActivity extends Activity implements AppConstants
{
	public static float width;
	public static float height;

	public static int lastUser = 0;
	public static int currentUser = 0;
	public static int currentCategory = 0;
	public static int messageStartId = 0;
	public static int messageEndId = 0;
	public static int messageCurrentId = 0;

	public static int categoryId = 0;

	private MessageDetailView mMessageDetailView;
	private HorizontalPager mPager;
	private RadioGroup mRadioGroup;


	public static LayoutInflater mInflater;
	DatabaseReadWrite databaseWriter;


	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//		setRequestedOrientation(0);
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.levels_selection);

		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();
		
		initDrawer();
		inmobi();

		mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
		mRadioGroup = (RadioGroup) findViewById(R.id.tabslevel);

		mMessageDetailView = (MessageDetailView) findViewById(R.id.snake);
		mMessageDetailView.requestLayout();
		mMessageDetailView.messageDetailActivity = this;

		messageStartId = categoryMessageDeailStartId[currentCategory][currentUser];
		messageEndId = categoryMessageDeailEndId[currentCategory][currentUser] ;

		/*DatabaseReadWrite*/ 
		try {
			databaseWriter = new DatabaseReadWrite(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		databaseWriter.openToWrite();
		
		
		textViewMessageNo = (TextView) findViewById(R.id.messagenumber);
		
		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);
		if(currentUser==0)
			imageButton1.setBackgroundResource(R.drawable.tunein_1);
		else
		imageButton3.setBackgroundResource(R.drawable.stream_1);

		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentUser = 0;
				CategoryListActivity.currentUser = 0;
				MessageListActivity.currentUser = 0;
//			refreshList();
				imageButton1.setBackgroundResource(R.drawable.tunein_1);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream);
				//						startActivity(new Intent(getBaseContext(), MessageDetailsActivity.class));

				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish(); 
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit_1);
				imageButton3.setBackgroundResource(R.drawable.stream);

			startActivityForResult(new Intent(getBaseContext(), FavouriteUserListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
			setResult(RESULT_OK);
			finish(); 
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CategoryListActivity.currentUser = 1;
				MessageListActivity.currentUser = 1;
				currentUser = 1;
				//						refreshList();
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream_1);
				//						startActivity(new Intent(getBaseContext(), MessageDetailsActivity.class));
				//						finish();
				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish();
			}
		}); 

		load();
		
		
		
		
		
		
	}
	
	
	IMBanner banner;	
	void inmobi()
	{
		try {
//			InMobi.initialize(this, "58597300c5d0468691396967233bb27c");
			 
			banner = (IMBanner) findViewById(R.id.banner);
			banner.loadBanner();
			banner.setVisibility(View.GONE);
			
			banner.setIMBannerListener(new IMBannerListener() {
		        public void onShowBannerScreen(IMBanner arg0) {
		        	banner.setVisibility(View.VISIBLE);
		                }
		            public void onLeaveApplication(IMBanner arg0) {
		            	banner.setVisibility(View.VISIBLE);
		        }
		        public void onDismissBannerScreen(IMBanner arg0) {
		        }
		                public void onBannerRequestSucceeded(IMBanner arg0) {
		                	banner.setVisibility(View.VISIBLE);
		                }
		                public void onBannerInteraction(IMBanner arg0, Map<String, String> arg1) {
		                	banner.setVisibility(View.VISIBLE);
		        }
						public void onBannerRequestFailed(IMBanner arg0,
								IMErrorCode arg1) {
							banner.setVisibility(View.GONE);
							// TODO Auto-generated method stub
							
						}
		    });
			
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}

	
	Handler handler = new Handler();
	ImageView internalImageView;
	TextView internalTextView;
	ImageView leftArrow;
	ImageView rightArrow;
	
	int previousImageNo1 = -1;
	int previousImageNo = -1;
	int currentImageNo = -1;

	
	public void load()
	{
			for(int i=messageStartId,j=0;i<=messageEndId;i++,j++)
			{ 
				View view1 = new View(this);
				view1 = mInflater.inflate(R.layout.levels_selectionhori, null);

			/*ImageView*/ internalImageView= (ImageView) view1.findViewById(R.id.imageforsmsdetails);
			
			
			Random rn = new Random();	
			previousImageNo1 = previousImageNo;
			previousImageNo = currentImageNo;
			
			currentImageNo = (messageCurrentId+1)%5;
			
			 internalImageView.setImageResource(categoryDetailImage[categoryId][currentImageNo]);
			 

			//ImageView internalImageView1= (ImageView) view1.findViewById(R.id.buttonleft1);
			//internalImageView1.setImageResource(R.drawable.leftarrow);

			
			/*TextView*/ internalTextView= (TextView) view1.findViewById(R.id.textView1);
				internalTextView.setText(i+messageCurrentId);
				
				leftArrow= (ImageView) view1.findViewById(R.id.buttonleftarrow);
//				if(messageCurrentId==0)
//					leftArrow.setImageResource(R.drawable.l_arrow);

				rightArrow= (ImageView) view1.findViewById(R.id.buttonrightarrow);
//				if(messageCurrentId==messageEndId-messageStartId)
//					rightArrow.setImageResource(R.drawable.r_arrow);
				
				if(messageCurrentId==0)
					leftArrow.setImageResource(R.drawable.l_arrow);
				else
					leftArrow.setImageResource(R.drawable.leftarrow);

				//leftArrow.setAlpha(60);
				if(messageCurrentId==messageEndId-messageStartId)
					rightArrow.setImageResource(R.drawable.r_arrow);
				else
					rightArrow.setImageResource(R.drawable.rightarrow);
				mPager.addView(view1);
				
				// create your dynamic views here.      

				View viewRadio = new View(this);
				viewRadio = mInflater.inflate(R.layout.radiobutton, null);

				RadioButton internalRadioView= (RadioButton) viewRadio.findViewById(R.id.radio_btn_00);
				internalRadioView.setId(j);
				internalRadioView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
							mRadioGroup.check(arg0.getId());
							dontChange = false;
					}
				});
				internalRadioView.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
				});
				mRadioGroup.addView(viewRadio);
				break;
			}
			mPager.setOnScreenSwitchListener(onScreenSwitchListener);
			mPager.setCurrentScreen(/*messageCurrentId*/0, false);
			currentScreen = mPager.getCurrentScreen();
			currentScreen = messageCurrentId;
			mRadioGroup.setClickable(false);

			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			imageButtonFavourite = (ImageView) findViewById(R.id.favouriteButton);

			for(int i=0;i<databaseWriter.LongMessageID.size();i++)
			{
				if(databaseWriter.LongMessageID.get(i)==MessageIdtostore || databaseWriter.OtherMessageID.get(i)==MessageIdtostore)
				{
					if(databaseWriter.CurrentStatus.get(i)==1)
					{
						imageButtonFavourite.setImageResource(R.drawable.favouritebutton_1);
					}
					break;
				}
			}
	}
	
	
	public void printmemory()
	{
		MemoryInfo mi = new MemoryInfo();
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		activityManager.getMemoryInfo(mi);
		long availableMegs = mi.availMem / 1048576L;
		System.out.println(" xxxx availableMegs "+availableMegs);
	}
	
	public TextView textViewMessageNo;
	
	public ImageView imageButton1;
	public ImageView imageButton2;
	public ImageView imageButton3;

	public ImageView imageButtonFavourite;

	public static int textId = 0;

	boolean isDone = false;


	public static int dir = 0;
	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener = new HorizontalPager.OnScreenSwitchListener() {
		public void onScreenSwitched(final int screen) {
			if(dir==-1)
			{
				moveLeft();
			}else if(dir==1)
			{
				moveRight();
			}

			//			i/
			dir=0;
		}
	};

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public static int currentScreen = 0;
//	public static int currentScreen1 = 0;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		currentScreen = mPager.getCurrentScreen();
		//		mPager.setCurrentScreen(currentScreen, false);
		// super.onPause();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//		super.onActivityResult(requestCode, resultCode, data);
		System.out.println(" xxxx dd requestCode "+requestCode+" resultCode "+resultCode+" RESULT_OK "+RESULT_OK);
		if(resultCode==RESULT_OK)
		{
			setResult(RESULT_OK);
			finish();
		}
	}
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		//		setResult(1);
		finish();
	}
	public void socialNetwrok(View view)
	{
//		startFb();
		currentScreen = mPager.getCurrentScreen();
		if(view.getId()==R.id.favouriteButton)
		{
			int shotMessageIdtostore = categoryMessageStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			int categoryIdtostore = categoryId;
			int duplicateMessageIdToStore = -1;
			int currentStatusTOStore = 1;

			boolean updated = false;
			if(databaseWriter.CategoryID!=null)
			{
				if(databaseWriter.LongMessageID.size()>0)
				{
					for(int i=0;i<databaseWriter.LongMessageID.size();i++)
					{
						if(databaseWriter.LongMessageID.get(i)==MessageIdtostore || databaseWriter.OtherMessageID.get(i)==MessageIdtostore)
						{
							if(databaseWriter.CurrentStatus.get(i)==1)
							{
								currentStatusTOStore = 0;
							}
							if(databaseWriter.LongMessageID.get(i)==MessageIdtostore)
							{
								databaseWriter.openToUpdate(currentStatusTOStore, 3, MessageIdtostore);
							}else
							{
								databaseWriter.openToUpdate(currentStatusTOStore, 4, MessageIdtostore);
							}
							//update
							updated = true;
							break;
						}
					}
				}
			}
			if(!updated)
			{

				try {
					databaseWriter.openToWrite(categoryIdtostore, shotMessageIdtostore, MessageIdtostore,duplicateMessageIdToStore, currentStatusTOStore,currentUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			databaseWriter.openToRead();
			if(currentStatusTOStore==1)
			{
				imageButtonFavourite.setImageResource(R.drawable.favouritebutton_1);
			}else
			{
				imageButtonFavourite.setImageResource(R.drawable.favouritebutton);
			}
		}
	}
	public int isMessageComflict(String strMessage)
	{
//		categoryId
		int conflictIdOfMsg = -1;
//		System.out.println(" xxxx oooooooooo "+strMessage);
		for(int i=0;i<categoryUniqueId.length;i++)
		{
//			System.out.println(" xxxx oooooooooo i "+i+" categoryId "+categoryId);
			if(currentUser==0)
			{
				if(categoryUniqueId[i][1]==categoryId)
				{
//					System.out.println(" xxxx ooooooooo 222222222222222");
					if(categoryMessageDeailStartId[i][1]>0 && categoryMessageDeailEndId[i][1]>0)
					{
						for(int j=categoryMessageDeailStartId[i][1]; j<categoryMessageDeailEndId[i][1];j++)
						{
							String ss1 = getResources().getString(j);
//							System.out.println(" xxxx ooooooooo ss1 "+ss1);
							if(strMessage.compareTo(ss1)==0)
							{
//								System.out.println(" xxxx ooooooooo conflictIdOfMsg");
								conflictIdOfMsg = j;
								break;
							}
						}
					}
				}
			}else{
				if(categoryUniqueId[i][0]==categoryId)
				{
					if(categoryMessageDeailStartId[i][0]>0 && categoryMessageDeailEndId[i][0]>0)
					{
						for(int j=categoryMessageDeailStartId[i][0]; j<categoryMessageDeailEndId[i][0];j++)
						{
							String ss1 = getResources().getString(j);
							if(strMessage.compareTo(ss1)==0)
							{
								conflictIdOfMsg = j;
//								System.out.println(" xxxx conflictIdOfMsg "+conflictIdOfMsg);
								break;
							}
						}
					}
				}
			}
		}
		return conflictIdOfMsg;
	}
	int delayfed = 100;
	
	boolean isAnim = false;
	public void updateView(){
		
		if(messageCurrentId==0)
			leftArrow.setImageResource(R.drawable.l_arrow);
		else
			leftArrow.setImageResource(R.drawable.leftarrow);

		//leftArrow.setAlpha(60);
		if(messageCurrentId==messageEndId-messageStartId)
			rightArrow.setImageResource(R.drawable.r_arrow);
		else
			rightArrow.setImageResource(R.drawable.rightarrow);

		
		for(int i=messageStartId,j=0;i<=messageEndId;i++,j++)
		{ 

			final Context cc = this;


			final Animation inImg = new AlphaAnimation(1.0f, 0.0f);
			inImg.setDuration(delayfed);
			internalImageView.startAnimation(inImg);


			internalTextView.setText(i+lastmessageId);
			final Animation in = new AlphaAnimation(0.0f, 1.0f);
			in.setDuration(delayfed);

			final Animation out = new AlphaAnimation(1.0f, 0.0f);
		    out.setDuration(delayfed);
		    
			internalTextView.startAnimation(out);

			isAnim = true;
			

		    out.setAnimationListener(new AnimationListener() {

		       // @Override
		        public void onAnimationEnd(Animation animation) {
//		        	mPager.removeAllViews();

		        	Random rn = new Random();	
		        	previousImageNo1 = previousImageNo;
					previousImageNo = currentImageNo;
					
					currentImageNo = (messageCurrentId+1)%5;
					
					 internalImageView.setImageResource(categoryDetailImage[categoryId][currentImageNo]);
					 

					final Animation inImg = new AlphaAnimation(0.0f, 1.0f);
					inImg.setDuration(delayfed);
					internalImageView.startAnimation(inImg);


					internalTextView.setText(messageStartId+messageCurrentId);
					final Animation in = new AlphaAnimation(0.0f, 1.0f);
					in.setDuration(delayfed);
					internalTextView.startAnimation(in);

				    in.setAnimationListener(new AnimationListener() {
					       // @Override
					        public void onAnimationEnd(Animation animation) {
					        	isAnim = false;
					        }
							public void onAnimationRepeat(Animation arg0) {
							}
							public void onAnimationStart(Animation animation) {
							}
					    });
		        }

				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
				}

				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
		    });

		    
			// create your dynamic views here.      

			break;
		}

//		currentScreen1 = messageCurrentId;

		int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
		imageButtonFavourite = (ImageView) findViewById(R.id.favouriteButton);

		for(int i=0;i<databaseWriter.LongMessageID.size();i++)
		{
			if(databaseWriter.LongMessageID.get(i)==MessageIdtostore || databaseWriter.OtherMessageID.get(i)==MessageIdtostore)
			{
				if(databaseWriter.CurrentStatus.get(i)==1)
				{
					imageButtonFavourite.setImageResource(R.drawable.favouritebutton_1);
				}else
					{
					imageButtonFavourite.setImageResource(R.drawable.favouritebutton);
				}
				break;
			}else
			{
				imageButtonFavourite.setImageResource(R.drawable.favouritebutton);
			}
		}

	}

	
	public void moveLeft()
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId>0)
		{
			messageCurrentId--;
			//mPager.removeAllViews();
			
			updateView();
			
		}
		setMessageNumber();
	}
	public void moveRight()
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId<messageEndId-messageStartId)
		{
			messageCurrentId++;
		//mPager.removeAllViews();
		
		updateView();
		
		setMessageNumber();
		}
	}

	
	
	
	public void moveLeft(View v)
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId>0)
		{
			messageCurrentId--;
		//mPager.removeAllViews();
		
		updateView();
		
		}
		setMessageNumber();
	}
	int lastmessageId = 0;
	public void moveRight(View v)
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId<messageEndId-messageStartId)
		{
			messageCurrentId++;
		//mPager.removeAllViews();
		
		updateView();
		
		setMessageNumber();
		}
	}
	public void scrollviewclick(View v)
	{
		printmemory(); 
	}
//	public static byte[] bitMapData;
	public Bitmap createImage(int ss)
	{
		 Bitmap ssss = null;
		 ssss = BitmapFactory.decodeResource(getResources(), ss);
		return ssss;
	}
	protected  boolean isNetwork()
    {
        String networkType = "unknown";
        try
        { 
            ConnectivityManager mConnectivityManager = (ConnectivityManager)getSystemService("connectivity");
            NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
            if(ni == null)
            {
            	System.out.println(" xxxx offline ");
                networkType = "offline";
                return false;
            }
            else
            {
            	System.out.println(" xxxx ni.getState().ordinal() "+ni.getState().ordinal());
            	int type = ni.getType();
            	System.out.println(" xxxx type "+type);
            	if(type == 0)
                    {
            		//System.out.println(" xxxx ppppppppppppppppppp11111 ");
                        networkType = "cell";
                        return true;
                    }else
                    {
                    	//System.out.println(" xxxx ppppppppppppppppppp222222 ");
//                    	return false;
                    	networkType = "other";
                        return true;
                    }
            	
            }
        }
        catch(Exception exception) { System.out.println(" xxxx exception "+exception.getMessage());}
        return false;
    }
	public void startFb(View v)
	{
		
		if(!isNetwork())
		{
			Toast toast = Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT); 
	         toast.setGravity(Gravity.CENTER, 0, 0); 
	        toast.show();
			return;
		}
		
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon); 
		Bitmap bitmap = null;//createImage(R.drawable.icon);
 
		bitmap = BitmapFactory.decodeResource( getResources(), categoryDetailImage[categoryId][currentImageNo]);
		
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		/*byte[]*/ FacebookPost.bitMapData = stream.toByteArray();
		//FacebookPost.bitmap1 = bitmap;
		
		//now use this to post -- 

        //parameters.putByteArray("picture", bitMapData); mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener()); 
        
        
        
		String Name = getResources().getString(categoryUniqueIdName[categoryId]);
		currentScreen = mPager.getCurrentScreen();
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			String message = getResources().getString(MessageIdtostore);
			
		Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
		String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";
		
//		String emailDetail = Name+"<br><br>"+message+"<br><br>"+getResources().getString(R.string.email_program_de)+link;
//		String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_program_de)+link;

		String downloadalllink = "\n\nAndroid - "+ myUri + "\niOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "\n\nWatch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";

		String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_download)+": "+/*myUri*/downloadalllink;

					FacebookPost.messageToPostOnFacebook = emailDetail;
				
		Intent intent = new Intent(this, FacebookPost.class);
        startActivity(intent); 
	}
	
	
	public void sendEmail(View v)
	{
		 FileOutputStream outStream; 
		 File file;
		Bitmap bm = null;
		
		
		bm = BitmapFactory.decodeResource( getResources(), categoryDetailImage[categoryId][currentImageNo]);
		
		        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

		        
		    file = new File(extStorageDirectory, "ukhane.PNG");
		    try {
		        outStream = new FileOutputStream(file);
//		        Bitmap resized = Bitmap.createScaledBitmap(bm, bm.getWidth()/2, bm.getHeight()/2, true);
//System.out.println(" xxxx ddddddddddddddddddd  "+bm.getWidth());

		        if(bm.getWidth()>500)
		        {
		        	bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/2, bm.getHeight()/2, true);
		        }
		       // System.out.println(" xxxx ddddddddddddddddddd11  "+bm.getWidth());
		        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		        outStream.flush();
		        outStream.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		
		
		String Name = getResources().getString(categoryUniqueIdName[categoryId]);
		
		currentScreen = mPager.getCurrentScreen();
		System.out.println(" xxxx socialNetwrok 111111");
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			String message = getResources().getString(MessageIdtostore);
		
		Intent i = new Intent(Intent.ACTION_SEND);
//		i.setType("message/rfc822");
//		i.setType("image/png");
		i.setType("application/octet-stream");
		//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, /*"subject of email"*/Name);
//		i.putExtra(Intent.EXTRA_TEXT   , /*"body of email"*/Html.fromHtml(getResources().getString(R.string.downloadlink)));
		Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";
		
String downloadalllink = "<br><br>Android - "+ myUri + "<br>iOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "<br><br>Watch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";

String emailDetail = getResources().getString(R.string.email_friend)+"<br><br>"+getResources().getString(R.string.email_hi)+" "+link+" "+getResources().getString(R.string.email_program_de)+" <b>"+Name+"</b>..."+"<br><br><b>"+message+"</b><br><br>"+getResources().getString(R.string.email_download)+": "/*+link*/+downloadalllink;
//		i.putExtra(Intent.EXTRA_TEXT   ,Html.fromHtml(getResources().getString(R.string.downloadlink)+"<a href=\""+ myUri+
//                "\">"+getResources().getString(R.string.downloadlink)+"</a>"));
		
		
		i.putExtra(Intent.EXTRA_TEXT   ,Html.fromHtml(emailDetail)); 
		


		
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		i.setType("application/octet-stream");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(this, "Please configure your E-mail account and try again", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	
	public void sendSMS(View v)
	{
		try {
			String Name = getResources().getString(categoryUniqueIdName[categoryId]);
			//currentScreen = mPager.getCurrentScreen();
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			String message = getResources().getString(MessageIdtostore);

			Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
			String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";

			//		String emailDetail = Name+"<br><br>"+message+"<br><br>"+getResources().getString(R.string.email_program_de)+link;
			//		String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_program_de)+link;

			String downloadalllink = "\nAndroid - "+ myUri + "\niOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "\n\nWatch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";

			String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_download)+" "+/*myUri*/downloadalllink;

			String ss = getResources().getString((R.string.b_details1));
			System.out.println("ssssssssssssssssssss1 "+Build.VERSION.SDK_INT);
			if (Build.VERSION.SDK_INT > 18) //At least KitKat
		    {
				//System.out.println("ssssssssssssssssss1111");
		        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); //Need to change the build to API 19
  
		        Intent sendIntent = new Intent(Intent.ACTION_SEND);
		        sendIntent.setType("text/plain");
		        sendIntent.putExtra(Intent.EXTRA_TEXT, emailDetail);

		        if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
		        {
		            sendIntent.setPackage(defaultSmsPackageName);
		        }
		        startActivity(sendIntent);

		    }
			else{
				//System.out.println("ssssssssssssssssss2222");
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setData(Uri.parse("sms:"));

			smsIntent.putExtra("sms_body", emailDetail);
			//smsIntent.setType("vnd.android-dir/mms-sms");
			startActivity(smsIntent);
			}
			
			//					startActivity(Intent.createChooser(smsIntent, "Send SMS..."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast toast = Toast.makeText(this, "Unable to send SMS, please try later", Toast.LENGTH_SHORT); 
			toast.setGravity(Gravity.CENTER, 0, 0); 
			toast.show();
			e.printStackTrace();
		}
	}
	public void onClickWhatsApp1(View view) {
String Name = getResources().getString(categoryUniqueIdName[categoryId]);
		
		currentScreen = mPager.getCurrentScreen();
		System.out.println(" xxxx socialNetwrok 111111");
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			String message = getResources().getString(MessageIdtostore);
		
Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";
		String emailDetail = getResources().getString(R.string.email_friend)+"<br><br>"+getResources().getString(R.string.email_hi)+" "+link+" "+getResources().getString(R.string.email_program_de)+" "+Name+"<br><br>"+message+"<br><br>"+getResources().getString(R.string.email_program_de)+" "+link;
//		i.putExtra(Intent.EXTRA_TEXT   ,Html.fromHtml(getResources().getString(R.string.downloadlink)+"<a href=\""+ myUri+
//                "\">"+getResources().getString(R.string.downloadlink)+"</a>"));
		
		
		
		
		
		
		Intent waIntent = new Intent(Intent.ACTION_SEND);
		waIntent.setType("text/plain");
		String text = "YOUR TEXT HERE";
		waIntent.setPackage("com.whatsapp");
		if (waIntent != null) {
			waIntent.putExtra(Intent.EXTRA_TEXT, emailDetail);//
			startActivity(Intent.createChooser(waIntent, "Share with"));
		} else {
			Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
			.show();
		}

	}
	public void onClickWhatsApp(View view) {
//		Uri myUri = Uri.parse("http://www.google.com/");
//		String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.downloadlink)+"</a>";
//		String emailDetail = getResources().getString(R.string.downloadlink)+" "+myUri;
//		
		 
		String Name = getResources().getString(categoryUniqueIdName[categoryId]);
		currentScreen = mPager.getCurrentScreen();
			int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+/*currentScreen1*/messageCurrentId;
			String message = getResources().getString(MessageIdtostore);
			
		Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
		String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";
		
		String downloadalllink = "\n\nAndroid - "+ myUri + "\niOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "\n\nWatch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";
		
		String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_download)+": "+/*myUri*/downloadalllink;
			

			
			
			Intent waIntent = new Intent(Intent.ACTION_SEND);
			waIntent.setType("text/plain");
			String text = "YOUR TEXT HERE";
			waIntent.setPackage("com.whatsapp");
			if (waIntent != null) {
				try {

				waIntent.putExtra(Intent.EXTRA_TEXT, (emailDetail));//
//				startActivity(Intent.createChooser(waIntent, "Share with"));
				startActivity(waIntent);
				}				
				catch(ActivityNotFoundException activityNotFoundException) {
					Toast toast = Toast.makeText(this, "WhatsApp not installed on this device", Toast.LENGTH_SHORT); 
			         toast.setGravity(Gravity.CENTER, 0, 0); 
			        toast.show();
                }   
			} else {
//				Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
//				.show();
				Toast toast = Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT); 
		         toast.setGravity(Gravity.CENTER, 0, 0); 
		        toast.show();  
			}
		}
	public void onClickTwitter(View view) {
//		pauseMenu();
	}
	public AlertDialog.Builder builder11;
	public void pauseMenu() {
		builder11 = new AlertDialog.Builder(this);
		if (builder != null) {
			if (builder.isShowing()) {
				builder.dismiss();
			}
		}

		System.out.println(" xxxx pauseMenupauseMenu ");
		LayoutInflater eulaInflater = LayoutInflater.from(this);
		View eulaLayout = eulaInflater.inflate(R.layout.pausemenu, null);
//		Button sound = (Button) eulaLayout.findViewById(R.id.sound);
//			sound.setBackgroundResource(R.drawable.sound_off);

		
		builder = builder11.create();//new AlertDialog.Builder(this);
//		.setIcon(R.drawable.pause).setTitle("Pause")
		
//		.setView(eulaLayout)
		builder.setView(eulaLayout,0,0,0,0);
		
		builder.show();

//		builder.getWindow().setLayout((int)(width/3)*2, (int)(height/3)*2);
		builder.getWindow().setLayout((int)(width-30)*1, (int)(height-60)*1);
		builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		builder.setOnCancelListener(new OnCancelListener() {

//			@Override
			public void onCancel(DialogInterface dialog) {
				builder.dismiss();
				}
		});
	}
	public AlertDialog builder;
	public void refreshList()
	{
		//		if(true)return;
		currentScreen = mPager.getCurrentScreen();
		if(lastUser!=currentUser)
		{
			lastUser = currentUser;
			mPager.removeAllViews();
			mRadioGroup.removeAllViews();
			messageStartId = categoryMessageDeailStartId[currentCategory][currentUser];
			messageEndId = categoryMessageDeailEndId[currentCategory][currentUser] ;
			if(currentUser==0)
			{
				imageButton1.setBackgroundResource(R.drawable.tunein_1);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream);
			}else
			{
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream_1);
			}
			for(int i=messageStartId;i<=messageEndId;i++)
			{  
				View view = new View(this);
				view = mInflater.inflate(R.layout.levels_selectionhori, null);
				TextView internalTextView= (TextView) view.findViewById(R.id.textView1);
				internalTextView.setText(i);
				mPager.addView(view);
			}

			for(int i=messageStartId, j=0;i<=messageEndId;i++,j++)
			{  
				View viewRadio = new View(this);
				viewRadio = mInflater.inflate(R.layout.radiobutton, null);
				RadioButton internalRadioView= (RadioButton) viewRadio.findViewById(R.id.radio_btn_00);
				internalRadioView.setId(j);
				internalRadioView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						// TODO Auto-generated method stub
						mRadioGroup.check(arg0.getId());
						//						mPager.setCurrentScreen(arg0.getId(), true);
					}

				});
				internalRadioView.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						System.out.println(" xxxx vvvvvvvvvvvvvvvvvvv");
						// TODO Auto-generated method stub
						return true;
					}
				});
				mRadioGroup.addView(viewRadio);
			}
		}
		mPager.setCurrentScreen(/*currentScreen1*/messageCurrentId, false);
		mRadioGroup.check(/*currentScreen1*/messageCurrentId);
		setMessageNumber();
	}
	public void radiobuttonclicked(View view)
	{
		mRadioGroup.setClickable(false);
	}
	@Override
	protected void onResume() {
		super.onResume();
		//		refreshList();
		PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		boolean isScreenOn = true;//powerManager.isScreenOn();
		isAnim = false;
		if (isScreenOn) {
			dontChange = true;
			//			mPager.setCurrentScreen(currentScreen, false); 
			mRadioGroup.check(/*currentScreen1*/messageCurrentId);
			setMessageNumber();
		}
	}
	public void setMessageNumber()
	{
		int messageno =  messageEndId-messageStartId+1;//mPager.getChildCount();
		String message = "";
		String message1 = "";
		if(messageno<10)
		{
			message = getResources().getString(R.string.message0+messageno);
		}else if(messageno<100)
		{
			String ss = ""+messageno;
			String ss1 = ss.substring(0, 1);
			String ss2 = ss.substring(1, 2);
			int ss1int = Integer.parseInt(ss1);
			int ss2int = Integer.parseInt(ss2);
			message = getResources().getString(R.string.message0+ss1int)+getResources().getString(R.string.message0+ss2int);
		
		}else if(messageno<1000)
		{
			String ss = ""+messageno;
			String ss1 = ss.substring(0, 1);
			String ss2 = ss.substring(1, 2);
			String ss3 = ss.substring(2, 3);
			int ss1int = Integer.parseInt(ss1);
			int ss2int = Integer.parseInt(ss2);
			int ss3int = Integer.parseInt(ss3);
			message = getResources().getString(R.string.message0+ss1int)+getResources().getString(R.string.message0+ss2int)+getResources().getString(R.string.message0+ss3int);
		}
		
		int screenNumber = /*currentScreen1*/messageCurrentId+1;
		if(screenNumber<10)
		{
			message1 = getResources().getString(R.string.message0+screenNumber);
		}else if(screenNumber<100)
		{
			String ss = ""+screenNumber;
			String ss1 = ss.substring(0, 1);
			String ss2 = ss.substring(1, 2);
			int ss1int = Integer.parseInt(ss1);
			int ss2int = Integer.parseInt(ss2);
			message1 = getResources().getString(R.string.message0+ss1int)+getResources().getString(R.string.message0+ss2int);
		
		}else if(screenNumber<1000)
		{
			String ss = ""+screenNumber;
			String ss1 = ss.substring(0, 1);
			String ss2 = ss.substring(1, 2);
			String ss3 = ss.substring(2, 3);
			int ss1int = Integer.parseInt(ss1);
			int ss2int = Integer.parseInt(ss2);
			int ss3int = Integer.parseInt(ss3);
			message1 = getResources().getString(R.string.message0+ss1int)+getResources().getString(R.string.message0+ss2int)+getResources().getString(R.string.message0+ss3int);
		}
		textViewMessageNo.setText(message1+"/"+message);
		mMessageDetailView.messageNo = message1+"/"+message;
		mMessageDetailView.invalidate();
	}
	boolean dontChange = false;

	ProgressDialog progress;
	public long delay=1000;
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    int counter = 0;
	class RefreshHandler extends Handler {

		@Override
		public void handleMessage(Message msg) 
		{
		}

		public void sleep(long delayMillis) {
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};
	
	
	
	
	/*final*/ DrawerLayout drawer;
	ListView navList;
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
	            	 Utilities.onDrawerItemClick(MessageDetailsActivity.this,pos);
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
}
