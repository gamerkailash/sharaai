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





import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MessageListActivity extends ListActivity implements AppConstants  {
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayAdapter<String> adapter1;
	//	connection con;
	int dataCounter=0;

	public static int lastUser = 0;
	public static int currentUser = 0;
	public static int currentCategory = 0;
	public static int messageStartId = 0;
	public static int messageEndId = 0;
	
	public static int categoryId = 0;
	
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;

	public static float width;
	public static float height;

	public ImageView imageButton1;
	public ImageView imageButton2;
	public ImageView imageButton3;
	private MessageView mMessageView;
	
	IMBanner banner;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.message_preview);

		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();

		initDrawer();
		inmobi();
		
		mMessageView = (MessageView) findViewById(R.id.snake);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mMessageView.requestLayout();
		mMessageView.rateActivity = this;

		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);
		if(currentUser==0)
		imageButton1.setBackgroundResource(R.drawable.tunein_1);
		else
			imageButton3.setBackgroundResource(R.drawable.stream_1);
		
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		/*CustomAdapter*/ adapter = new CustomAdapter(this, R.layout.message_list,R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);

		
		messageStartId = categoryMessageStartId[currentCategory][currentUser];
		messageEndId = categoryMessageEndId[currentCategory][currentUser] ;
//		for(int j=0;j<=100;j++)
		for(int i=messageStartId;i<=messageEndId;i++)
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

		//		con.readXml(vectorRateit,getString(R.string.u_rateshowgenre),false);
		
		// when you click this demo button
		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentUser = 0;
				CategoryListActivity.currentUser = 0;
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
//			mMessageView.invalidate();
			startActivityForResult(new Intent(getBaseContext(), FavouriteUserListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
		 }
		});
		imageButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CategoryListActivity.currentUser = 1;
				currentUser = 1;
				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish();
			 }
			});
	}
	
	
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
	public void refreshList()
	{
		if(lastUser!=currentUser)
		{
			lastUser = currentUser;
			data.removeAllElements();
			messageStartId = categoryMessageStartId[currentCategory][currentUser];
			messageEndId = categoryMessageEndId[currentCategory][currentUser] ;
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
		System.out.println(" xxxx mm requestCode "+requestCode+" resultCode "+resultCode+" RESULT_OK "+RESULT_OK);
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
	public void performButton(View v)
	{
	}




	Vector vectorRateit = new Vector();

	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
//		TextActivity.textId = position;
		MessageDetailsActivity.textId = position;
//		startActivity(new Intent(getBaseContext(), TextActivity.class));
		MessageDetailsActivity.currentUser = currentUser;
		MessageDetailsActivity.currentCategory = currentCategory;
		MessageDetailsActivity.messageCurrentId = position;
		MessageDetailsActivity.currentScreen = position;
		MessageDetailsActivity.categoryId = categoryId;
//		startActivity(new Intent(getBaseContext(), MessageDetailsActivity.class));
		startActivityForResult(new Intent(getBaseContext(), MessageDetailsActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
		
		
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
			//		       TextView detail = null;
			ImageView i11=null;
			//			ImageView i22=null;
			final RowData rowData= getItem(position);
			if(null == convertView){
				convertView = mInflater.inflate(R.layout.message_list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);
			//		             detail = holder.getdetail();
			//		             detail.setText(rowData.mDetail);

			//			XmlData xmldata = (XmlData) vectorRateit.elementAt(rowData.mId);
			i11=holder.getImage();
			i11.setImageResource(categoryIcon[categoryId]);




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
	            	 Utilities.onDrawerItemClick(MessageListActivity.this,pos);
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