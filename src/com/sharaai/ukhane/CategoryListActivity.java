package com.sharaai.ukhane;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;







import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryListActivity extends ListActivity implements AppConstants {
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayAdapter<String> adapter1;
	int dataCounter=0;
	public static final int RESULT_CLOSE_ALL = 2;

	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;

	public static float width;
	public static float height;

	public ImageView imageButton1;
	public ImageView imageButton2;
	public ImageView imageButton3;
	private CategoryView mCategoryView;

	public static int lastUser = 0;
	public static int currentUser = 0;
	public static int categoryStartId = 0;
	public static int categoryEndId = 0;
	
	IMBanner banner;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

	       
	//	InMobi.initialize(this, "58597300c5d0468691396967233bb27c");
    
//		InMobi.initialize(this, "53d0ef05be81426ea33d9e7005a32a94");
		 

		 
		
		setContentView(R.layout.category);
		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();

		initDrawer();
		
		
		DisplayMetrics outmt = new DisplayMetrics();
		display.getMetrics(outmt);
		float density = getResources().getDisplayMetrics().density;
		float dpwd = outmt.widthPixels/density;
		float dpht = outmt.heightPixels/density;
		
		
		 
		inmobi();
		
		
		
		
		
		
		mCategoryView = (CategoryView) findViewById(R.id.snake);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mCategoryView.requestLayout();
		mCategoryView.mCategoryActivity = this;

		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);

		//imageButton1.setImageResource(R.drawable.tunein_1);
		if(currentUser==0)
			imageButton1.setBackgroundResource(R.drawable.tunein_1);
		else if(currentUser==1)
			imageButton3.setBackgroundResource(R.drawable.stream_1);
			
		DatabaseReadWrite datawr = null;
		try {
			datawr = new DatabaseReadWrite(this);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//		datawr.openToUpdate();
		try {
			datawr.openToWrite();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			datawr.openToRead();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		/*CustomAdapter*/ adapter = new CustomAdapter(this, R.layout.message_list,R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);

		
		if(currentUser==0)
		{
			categoryStartId = categoryStartId_Mail;
			categoryEndId = categoryEndId_Mail;
		}
		for(int i=categoryStartId;i<=categoryEndId;i++)
		{
			try {
				String ss = getResources().getString((i));
				rd = new RowData(i,ss,"");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(rd);
			adapter.notifyDataSetChanged();
		}
		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if(currentUser==1)
					inmobiLoadbanner();
				
				currentUser = 0;
				categoryStartId = categoryStartId_Mail;
				categoryEndId = categoryEndId_Mail;
				refreshList();
				
				imageButton1.setBackgroundResource(R.drawable.tunein_1);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream);
				mCategoryView.invalidate();
			}
		});
		imageButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit_1);
				imageButton3.setBackgroundResource(R.drawable.stream);
				
				startActivityForResult(new Intent(getBaseContext(), FavouriteUserListActivity.class), RESULT_CLOSE_ALL);
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if(currentUser==0)
				inmobiLoadbanner();
				
				currentUser = 1;
				categoryStartId = categoryStartId_Femail;
				categoryEndId = categoryEndId_Femail;
				refreshList();
				
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream_1);
				
				mCategoryView.invalidate();
				
			}
		});
		System.out.println(" xxxx iratedone "+iratedone);
	//if(!iratedone)
	{
		System.out.println(" xxxx11 iratedone "+iratedone);
		iratedone = true;
		mappRater = new AppRater();
		mappRater.app_launched(this);
	}
	}
	
	void inmobiLoadbanner()
	{
		try {
			if(banner!=null)
			banner.loadBanner();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void inmobi()
	{
		try {
			InMobi.initialize(this, "58597300c5d0468691396967233bb27c");
			 
			banner = (IMBanner) findViewById(R.id.banner);
			banner.loadBanner();
			banner.setVisibility(View.GONE);

			//System.out.println("sssssssss pppp1111");
			banner.setIMBannerListener(new IMBannerListener() {
		        public void onShowBannerScreen(IMBanner arg0) {
		        	System.out.println("sssssssss pppp 2222");
		        	banner.setVisibility(View.VISIBLE);
		                }
		            public void onLeaveApplication(IMBanner arg0) {
		            	//System.out.println("sssssssss pppp 3333");
		            	banner.setVisibility(View.VISIBLE);
		        }
		        public void onDismissBannerScreen(IMBanner arg0) {
		        	//System.out.println("sssssssss pppp 4444");
		        }
		                public void onBannerRequestSucceeded(IMBanner arg0) {
		                	System.out.println("sssssssss pppp 5555");
		                	banner.setVisibility(View.VISIBLE);
		                }
		                public void onBannerInteraction(IMBanner arg0, Map<String, String> arg1) {
		                	//System.out.println("sssssssss pppp 6666");
		                	banner.setVisibility(View.VISIBLE);
		        }
						public void onBannerRequestFailed(IMBanner arg0,
								IMErrorCode arg1) {
							System.out.println("sssssssss pppp 7777");
							banner.setVisibility(View.GONE);
							// TODO Auto-generated method stub
							
						}
		    });
			
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}

	
	
	public static boolean iratedone = false;
	AppRater mappRater;
	public void refreshList()
	{
		if(lastUser!=currentUser)
		{
			lastUser = currentUser;
			if(currentUser==0)
			{
				categoryStartId = categoryStartId_Mail;
				categoryEndId = categoryEndId_Mail;
				imageButton1.setBackgroundResource(R.drawable.tunein_1);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream);
			}else
			{
				categoryStartId = categoryStartId_Femail;
				categoryEndId = categoryEndId_Femail;
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream_1);
			}
			data.removeAllElements();
			for(int i=categoryStartId;i<=categoryEndId;i++)
			{

				try {
					String ss = getResources().getString((i));

					rd = new RowData(i,ss,"");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				data.add(rd);
				adapter.notifyDataSetChanged();
			}
		}
		mCategoryView.invalidate();
	}
	@Override
	protected void onResume() {
		super.onResume();
		refreshList();
	}
	@Override
	protected void onPause() {
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
		//		setResult(RESULT_CLOSE_ALL);
		setResult(RESULT_OK);
		finish();
	}
	public void performButton(View v)
	{
	}

	public void onClickWhatsApp(View view) {

		Intent waIntent = new Intent(Intent.ACTION_SEND);
		waIntent.setType("text/plain");
		String text = "YOUR TEXT HERE";
		waIntent.setPackage("com.whatsapp");
		if (waIntent != null) {
			waIntent.putExtra(Intent.EXTRA_TEXT, text);//
			startActivity(Intent.createChooser(waIntent, "Share with"));
		} else {
			Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
			.show();
		}

	}

	Vector vectorRateit = new Vector();
	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		MessageListActivity.currentUser = currentUser;
		MessageListActivity.lastUser = currentUser;
		MessageListActivity.currentCategory = position;
		MessageListActivity.categoryId = categoryUniqueId[position][currentUser];

		startActivityForResult(new Intent(getBaseContext(), MessageListActivity.class), RESULT_CLOSE_ALL);



		
		
		
	}
	private class RowData {
		protected int mId;
		protected String mTitle;
		protected String mDetail;
		RowData(int id,String title,String detail){
			mId=id;
			mTitle = title;
			mDetail=detail;
		}
		@Override
		public String toString() {
			return mId+" "+mTitle+" "+mDetail;
		}
	}
	private class CustomAdapter extends ArrayAdapter<RowData>
	{
		private Context mcontext;

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects)
		{
			super(context, resource, textViewResourceId, objects);
			mcontext=context;
		}
		//		      @Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			TextView title = null;
			ImageView i11=null;
			final RowData rowData= getItem(position);
			if(null == convertView){
				convertView = mInflater.inflate(R.layout.message_list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);

			i11=holder.getImage();
			
			i11.setImageResource(categoryIcon[categoryUniqueId[position][currentUser]]);
			

			return convertView;
		}
		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private ImageView i11=null;
			//			private ImageView i22=null;

			public ViewHolder(View row) {
				mRow = row;
			}
			public TextView gettitle() {
				if(null == title){
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public ImageView getImage() {
				if(null == i11){
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}
		}
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
	            	 Utilities.onDrawerItemClick(CategoryListActivity.this,pos);
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