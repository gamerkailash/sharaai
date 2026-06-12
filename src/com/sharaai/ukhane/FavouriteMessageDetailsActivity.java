package com.sharaai.ukhane;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.view.View.OnTouchListener;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class FavouriteMessageDetailsActivity extends Activity implements AppConstants
{
	public static float width;
	public static float height;

	public static int lastUser = 0;
	public static int currentUser = 0;
	public static int currentCategory = 0;
	public static int messageStartId = 0;
	public static int messageEndId = 0;
	public static int messageCurrentId = 0;

	public static int totalMessage = 0;

	public static int categoryId = 0;

	private FavouriteMessageDetailView mFavouriteMessageDetailView;
	private HorizontalPager mPager;
	private RadioGroup mRadioGroup;


	public static LayoutInflater mInflater;
	DatabaseReadWrite databaseWriter;

	public ArrayList<Integer> StoredMessageID = new ArrayList<Integer>();

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.favouritelevels_selection);

		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();

		initDrawer();
		inmobi();

		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);

		imageButton2.setBackgroundResource(R.drawable.rateit_1);

		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentUser = 0;
				CategoryListActivity.currentUser = 0;
				MessageListActivity.currentUser = 0;
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



		mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
		mRadioGroup = (RadioGroup) findViewById(R.id.tabslevel);

		mFavouriteMessageDetailView = (FavouriteMessageDetailView) findViewById(R.id.snake);
		mFavouriteMessageDetailView.requestLayout();
		mFavouriteMessageDetailView.mFavouritemessageDetailActivity = this;

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

		StoredMessageID = new ArrayList<Integer>();

		load();





		mPager.setOnScreenSwitchListener(onScreenSwitchListener);
		mPager.setCurrentScreen(/*messageCurrentId*/0, false);



		mRadioGroup.setClickable(false);

		imageButtonFavourite = (ImageView) findViewById(R.id.favouriteButton);
		imageButtonFavourite.setImageResource(R.drawable.favouritebutton_1);
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

	
	
	public TextView textViewMessageNo;

	public ImageView imageButton1;
	public ImageView imageButton2;
	public ImageView imageButton3;

	public ImageView imageButtonFavourite;

	public static int textId = 0;

	boolean isDone = false;

	ProgressDialog progress;
	Handler handler = new Handler();

int delay = 100;

boolean isAnim = false;
	public void updateView()
	{
		int tmpCat = 0;
		

		if(messageCurrentId==0)
			leftArrow.setImageResource(R.drawable.l_arrow);
		else
			leftArrow.setImageResource(R.drawable.leftarrow);

		if(messageCurrentId==totalMessage-1)
			rightArrow.setImageResource(R.drawable.r_arrow);
		else
			rightArrow.setImageResource(R.drawable.rightarrow);

		

		final Animation inImg = new AlphaAnimation(1.0f, 0.0f);
		inImg.setDuration(delay);
		internalImageView.startAnimation(inImg);


		final Animation in = new AlphaAnimation(1.0f, 0.0f);
		in.setDuration(delay);
		final Animation out = new AlphaAnimation(1.0f, 0.0f);
	    out.setDuration(delay);
	    
		internalTextView.startAnimation(out);
		isAnim = true;
		out.setAnimationListener(new AnimationListener() {

		       // @Override
		        public void onAnimationEnd(Animation animation) {
//		        	mPager.removeAllViews();
		        	int tmpCat = 0;

		    		for(int cat=0;cat<categoryUniqueId.length;cat++)
		    		{
		    			if(currentUser==0)
		    			{
		    				if(categoryUniqueId[cat][0]==currentCategory)
		    				{
		    					tmpCat  = cat;
		    					break;
		    				}
		    			}else
		    			{
		    				if(categoryUniqueId[cat][1]==currentCategory)
		    				{
		    					tmpCat  = cat;
		    					break;
		    				}
		    			}
		    		}
		    		
		    		Random rn = new Random();	
		    		previousImageNo1 = previousImageNo;
					previousImageNo = currentImageNo;
					
					currentImageNo = (messageCurrentId+1)%5;

					currentImageNo = (StoredMessageID.get(messageCurrentId)-categoryMessageDeailStartId[currentCategory][currentUser]+1)%5;
					
					 internalImageView.setImageResource(categoryDetailImage[tmpCat][currentImageNo]);
					 
		    		final Animation inImg = new AlphaAnimation(0.0f, 1.0f);
		    		inImg.setDuration(delay);
		    		internalImageView.startAnimation(inImg);


		    		internalTextView.setText(StoredMessageID.get(messageCurrentId));
		    		final Animation in = new AlphaAnimation(0.0f, 1.0f);
		    		in.setDuration(delay);
		    		final Animation out = new AlphaAnimation(1.0f, 0.0f);
		    	    out.setDuration(delay);
		    	    
		    		internalTextView.startAnimation(in);

		    		int MessageIdtostore = StoredMessageID.get(currentScreen);
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
		    		
		    		totalMessage = StoredMessageID.size();

		    		setMessageNumber();
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
		
		
		int MessageIdtostore = StoredMessageID.get(currentScreen);
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
		totalMessage = StoredMessageID.size();

		setMessageNumber();
		
	}

	public void moveLeft()
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId>0)
		{
			messageCurrentId--;

			currentScreen = messageCurrentId; 
			//mPager.removeAllViews();
			updateView();

		}
	}
	public void moveRight()
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId<totalMessage-1)
		{
			messageCurrentId++;
			currentScreen = messageCurrentId;
			//mPager.removeAllViews();

			updateView();

		}
	}

	public void moveLeft(View v)
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId>0)
		{
			messageCurrentId--;

			currentScreen = messageCurrentId; 
			//mPager.removeAllViews();

			updateView();
			
		}
	}
	int lastmessageId = 0;
	public void moveRight(View v)
	{
		if(isAnim)return;
		lastmessageId = messageCurrentId;
		if(messageCurrentId<totalMessage-1)
		{
			messageCurrentId++;
			currentScreen = messageCurrentId;
			//mPager.removeAllViews();

			updateView();
			
		}
	}

	//	public ArrayList<Integer> MessageIDOfFav = new ArrayList<Integer>();
	ImageView internalImageView;
	TextView internalTextView;
	ImageView leftArrow;
	ImageView rightArrow;
	
	int previousImageNo1 = -1;
	int previousImageNo = -1;
	int currentImageNo = -1;
	
	public void load()
	{

		if(DatabaseReadWrite.CategoryID!=null)
		{
			for(int j=0,k=0;j<=DatabaseReadWrite.CategoryID.size()-1;j++)
			{
				//				if(DatabaseReadWrite.CategoryID.get(j)==currentCategory && DatabaseReadWrite.CurrentStatus.get(j)==1)
				if(DatabaseReadWrite.CategoryID.get(j)==currentCategory && DatabaseReadWrite.CurrentStatus.get(j)==1 && DatabaseReadWrite.CurrentUserId.get(j)==currentUser)
				{
					System.out.println(" xxxx11 bbbbbbbb  "+DatabaseReadWrite.LongMessageID.get(j)+ " k "+k+" "+messageCurrentId);
					StoredMessageID.add(DatabaseReadWrite.LongMessageID.get(j));

					if(k==messageCurrentId)
					{

						View view1 = new View(this);
						view1 = mInflater.inflate(R.layout.levels_selectionhori, null);



						/*ImageView*/ internalImageView= (ImageView) view1.findViewById(R.id.imageforsmsdetails);
						int tmpCat = 0;
						for(int cat=0;cat<categoryUniqueId.length;cat++)
						{
							if(currentUser==0)
							{
								if(categoryUniqueId[cat][0]==currentCategory)
								{
									tmpCat  = cat;
									break;
								}
							}else
							{
								if(categoryUniqueId[cat][1]==currentCategory)
								{
									tmpCat  = cat;
									break;
								}
							}
						}
						
						Random rn = new Random();	
						previousImageNo1 = previousImageNo;
						previousImageNo = currentImageNo;
						
						currentImageNo = (messageCurrentId+1)%5;

						currentImageNo = (StoredMessageID.get(messageCurrentId)-categoryMessageDeailStartId[currentCategory][currentUser]+1)%5;

						
						 internalImageView.setImageResource(categoryDetailImage[tmpCat][currentImageNo]);
						 

						/*TextView*/ internalTextView= (TextView) view1.findViewById(R.id.textView1);
						internalTextView.setText(DatabaseReadWrite.LongMessageID.get(j));
						
						leftArrow= (ImageView) view1.findViewById(R.id.buttonleftarrow);

						rightArrow= (ImageView) view1.findViewById(R.id.buttonrightarrow);

						if(messageCurrentId==0)
							leftArrow.setImageResource(R.drawable.l_arrow);
						else
							leftArrow.setImageResource(R.drawable.leftarrow);

						if(messageCurrentId==totalMessage-1)
							rightArrow.setImageResource(R.drawable.r_arrow);
						else
							rightArrow.setImageResource(R.drawable.rightarrow);

						
						mPager.addView(view1);

						View viewRadio = new View(this);
						viewRadio = mInflater.inflate(R.layout.radiobutton, null);

						RadioButton internalRadioView= (RadioButton) viewRadio.findViewById(R.id.radio_btn_00);
						internalRadioView.setId(k);
						//					k++;
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
						//				break;
					}k++;
				}
			}		
		}

	}
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
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//		super.onActivityResult(requestCode, resultCode, data);
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
		if(view.getId()==R.id.favouriteButton)
		{
			int MessageIdtostore = StoredMessageID.get(currentScreen);
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
							try {
								databaseWriter.openToUpdate(currentStatusTOStore, 3, MessageIdtostore);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//update
							updated = true;
							break;
						}
					}
				}
			}
			try {
				databaseWriter.openToRead();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(currentStatusTOStore==1)
			{
				imageButtonFavourite.setImageResource(R.drawable.favouritebutton_1);
			}else
			{
				imageButtonFavourite.setImageResource(R.drawable.favouritebutton);
			}

		}
	}


	public static byte[] bitMapData;
	
	public Bitmap createImage(int ss)
	{
		 Bitmap ssss = null;
		 InputStream is = getResources().openRawResource(/*R.drawable.powermeterhud*/ss);
		 ssss = BitmapFactory.decodeStream(is);
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
                networkType = "offline";
                return false;
            }
            else
            {
            	int type = ni.getType();
            	if(type == 0)
                    {
                        networkType = "cell";
                        return true;
                    }else
                    {
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

		int tmpCat = 0;

		for(int cat=0;cat<categoryUniqueId.length;cat++)
		{
			if(currentUser==0)
			{
				if(categoryUniqueId[cat][0]==currentCategory)
				{
					tmpCat  = cat;
					break;
				}
			}else
			{
				if(categoryUniqueId[cat][1]==currentCategory)
				{
					tmpCat  = cat;
					break;
				}
			}
		}
		
		Bitmap bitmap = null;//createImage(R.drawable.icon);

		
		bitmap = BitmapFactory.decodeResource( getResources(), categoryDetailImage[tmpCat][currentImageNo]);
		
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		/*byte[]*/ FacebookPost.bitMapData = stream.toByteArray();
		
		
		String Name = getResources().getString(categoryUniqueIdName[currentCategory]);
		int MessageIdtostore = StoredMessageID.get(currentScreen);
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
		int tmpCat = 0;

		for(int cat=0;cat<categoryUniqueId.length;cat++)
		{
			if(currentUser==0)
			{
				if(categoryUniqueId[cat][0]==currentCategory)
				{
					tmpCat  = cat;
					break;
				}
			}else
			{
				if(categoryUniqueId[cat][1]==currentCategory)
				{
					tmpCat  = cat;
					break;
				}
			}
		}

		
		 FileOutputStream outStream; 
		 File file;
		Bitmap bm = null;
		
		
		bm = BitmapFactory.decodeResource( getResources(), categoryDetailImage[tmpCat][currentImageNo]);
		
		        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		        	
		    file = new File(extStorageDirectory, "ukhane.PNG");
		    try {
		        outStream = new FileOutputStream(file);
		        if(bm.getWidth()>500)
		        {
		        	bm = Bitmap.createScaledBitmap(bm, bm.getWidth()/2, bm.getHeight()/2, true);
		        }
		        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		        outStream.flush();
		        outStream.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		
	
		
		
		
		String Name = getResources().getString(categoryUniqueIdName[currentCategory]);


		//currentScreen = mPager.getCurrentScreen();
		int MessageIdtostore = StoredMessageID.get(currentScreen);
		String message = getResources().getString(MessageIdtostore);

		Intent i = new Intent(Intent.ACTION_SEND);
//		i.setType("message/rfc822");
		i.setType("application/octet-stream");

		//i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, /*"subject of email"*/Name);
		//		i.putExtra(Intent.EXTRA_TEXT   , /*"body of email"*/Html.fromHtml(getResources().getString(R.string.downloadlink)));
		Uri myUri = Uri.parse(("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name)));
		String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";
		
		String downloadalllink = "<br><br>Android - "+ myUri + "<br>iOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "<br><br>Watch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";
		
		String emailDetail = getResources().getString(R.string.email_friend)+"<br><br>"+getResources().getString(R.string.email_hi)+" "+link+" "+getResources().getString(R.string.email_program_de)+" <b>"+Name+"</b>..."+"<br><br><b>"+message+"</b><br><br>"+getResources().getString(R.string.email_download)+": "/*+link*/+downloadalllink;
		//		i.putExtra(Intent.EXTRA_TEXT   ,Html.fromHtml(getResources().getString(R.string.downloadlink)+"<a href=\""+ myUri+
		//                "\">"+getResources().getString(R.string.downloadlink)+"</a>"));

		i.putExtra(Intent.EXTRA_TEXT   ,Html.fromHtml(emailDetail)); 

		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

//		i.setType("message/rfc822") ;
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
			String Name = getResources().getString(categoryUniqueIdName[currentCategory]);
			//currentScreen = mPager.getCurrentScreen();
			int MessageIdtostore = StoredMessageID.get(currentScreen);
			String message = getResources().getString(MessageIdtostore);


			Uri myUri = Uri.parse("https://play.google.com/store/apps/details?id="+getResources().getString(R.string.package_name));
			String link = "<a href=\""+ myUri+"\">"+getResources().getString(R.string.email_program)+"</a>";

			//		String emailDetail = Name+"<br><br>"+message+"<br><br>"+getResources().getString(R.string.email_program_de)+link;
			//		String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_program_de)+link;
			String downloadalllink = "\nAndroid - "+ myUri + "\niOS -  https://itunes.apple.com/in/app/ukhane/id733497072?mt=8" + "\n\nWatch on YouTube -  https://www.youtube.com/watch?v=JjBYQiKLW4k";

			String emailDetail = Name+"\n\n"+message+"\n\n"+getResources().getString(R.string.email_download)+" "+/*myUri*/downloadalllink;

			String ss = getResources().getString((R.string.b_details1));
			if (Build.VERSION.SDK_INT > 18) //At least KitKat
		    {
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
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setData(Uri.parse("sms:"));
			
			smsIntent.putExtra("sms_body", emailDetail);
		//	smsIntent.setType("vnd.android-dir/mms-sms");
			startActivity(smsIntent);
			}
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

		//		currentScreen = mPager.getCurrentScreen();
		System.out.println(" xxxx socialNetwrok 111111");
		int MessageIdtostore = categoryMessageDeailStartId[currentCategory][currentUser]+currentScreen;
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

		String Name = getResources().getString(categoryUniqueIdName[currentCategory]);
		//		currentScreen = mPager.getCurrentScreen();
		int MessageIdtostore = StoredMessageID.get(currentScreen);
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
//			startActivity(Intent.createChooser(waIntent, "Share with"));
			startActivity(waIntent);
			}				
			catch(ActivityNotFoundException activityNotFoundException) {
				Toast toast = Toast.makeText(this, "WhatsApp not installed on this device", Toast.LENGTH_SHORT); 
		         toast.setGravity(Gravity.CENTER, 0, 0); 
		        toast.show();  
            }   
		} else {
			Toast toast = Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT); 
	         toast.setGravity(Gravity.CENTER, 0, 0); 
	        toast.show();  
		}
	}
	public void onClickTwitter(View view) {
	}



	public void refreshList()
	{
		if(true)return;
		//		currentScreen = mPager.getCurrentScreen();
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
		mPager.setCurrentScreen(currentScreen, false);
		mRadioGroup.check(currentScreen);
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
			setMessageNumber();
		}
	}

	public void setMessageNumber()
	{
		int messageno = totalMessage;//mPager.getChildCount();
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

		int screenNumber = messageCurrentId+1;
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
		
		mFavouriteMessageDetailView.messageNo = message1+"/"+message;
		mFavouriteMessageDetailView.invalidate();

	}

	boolean dontChange = false;

	
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
	            	 Utilities.onDrawerItemClick(FavouriteMessageDetailsActivity.this,pos);
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
