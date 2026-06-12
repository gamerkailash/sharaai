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
import androidx.drawerlayout.widget.DrawerLayout;
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

public class FavouriteMessageListActivity extends ListActivity implements AppConstants  {
	ArrayList<String> listItems=new ArrayList<String>();
	ArrayAdapter<String> adapter1;
	int dataCounter=0;
	public static int currentUser = 0;
	public static int currentCategory = 0;
	
	
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	CustomAdapter adapter;

	public static float width;
	public static float height;

	public ImageView imageButton1;
	public ImageView imageButton2;
	public ImageView imageButton3;
	private FavouriteMessageView mFavouriteMessageView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.favouritemessage_preview);

		Display display = getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();

		initDrawer();
		
		mFavouriteMessageView = (FavouriteMessageView) findViewById(R.id.snake);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mFavouriteMessageView.requestLayout();
		mFavouriteMessageView.FavouriteMessageActivity = this;

		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);
		imageButton2.setBackgroundResource(R.drawable.rateit_1);
		
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		/*CustomAdapter*/ adapter = new CustomAdapter(this, R.layout.message_list,R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);

		
		
		if(DatabaseReadWrite.CategoryID!=null)
		{
			for(int i=ID1,k=0; i<=ID10; i++)
			{
				for(int j=0;j<=DatabaseReadWrite.CategoryID.size()-1;j++)
				{
				}		
			}
		
		}

		
		

		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			//	currentUser = 0;
				CategoryListActivity.currentUser = 0;
				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish();
			 }
			});
		imageButton2.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			startActivityForResult(new Intent(getBaseContext(), FavouriteUserListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
			finish();
		 }
		});
		imageButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CategoryListActivity.currentUser = 1;
				//currentUser = 1;
				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish();
			 }
			});
	}
	
	

	
	public void refreshList()
	{
		data.removeAllElements();
		adapter.notifyDataSetChanged();
		int k=0;
		if(DatabaseReadWrite.CategoryID!=null)
		{
//			for(int i=ID1,k=0; i<=ID10; i++)
			{
				for(int j=0;j<=DatabaseReadWrite.CategoryID.size()-1;j++)
				{
					if(DatabaseReadWrite.CategoryID.get(j)==currentCategory && DatabaseReadWrite.CurrentStatus.get(j)==1 && DatabaseReadWrite.CurrentUserId.get(j)==currentUser)
					{
						try {
							String ss = getResources().getString(DatabaseReadWrite.ShotMessageID.get(j));
							rd = new RowData(k,ss,"");
						} catch (ParseException e) {
							e.printStackTrace();
						}
						k++;
						data.add(rd);
						adapter.notifyDataSetChanged();
					}
				}		
			}
		
		}
		if(k==0)
		{
//			finish();
			changeLayoutForNoData();
		}

	}
	public void changeLayoutForNoData()
	{
		setContentView(R.layout.favouritemessagelistnodata);
		initDrawer();
		mFavouriteMessageView = (FavouriteMessageView) findViewById(R.id.snake);
		mFavouriteMessageView.requestLayout();

		imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
		imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
		imageButton3 = (ImageView) findViewById(R.id.opt_stream);
		
		imageButton2.setBackgroundResource(R.drawable.rateit_1);
		
		imageButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CategoryListActivity.currentUser = 0;
				CategoryListActivity.categoryStartId = categoryStartId_Mail;
				CategoryListActivity.categoryEndId = categoryEndId_Mail;
//				refreshList();
				imageButton1.setBackgroundResource(R.drawable.tunein_1);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream);
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
//				mCategoryView.invalidate();
			}
		});
		imageButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CategoryListActivity.currentUser = 1;
				CategoryListActivity.categoryStartId = categoryStartId_Femail;
				CategoryListActivity.categoryEndId = categoryEndId_Femail;
//				refreshList();
				imageButton1.setBackgroundResource(R.drawable.tunein);
				imageButton2.setBackgroundResource(R.drawable.rateit);
				imageButton3.setBackgroundResource(R.drawable.stream_1);
//				mCategoryView.invalidate();
				startActivityForResult(new Intent(getBaseContext(), CategoryListActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
				setResult(RESULT_OK);
				finish();

			}
		});
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
		if(resultCode==RESULT_OK)
		{
			setResult(RESULT_OK);
	        finish();
		}
	}
		@Override
	public void onBackPressed() {
		//super.onBackPressed();
		finish();
	}
	public void performButton(View v)
	{
	}


	public void onListItemClick(ListView parent, View v, int position,
			long id) {
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
//		MessageDetailsActivity.textId = position;
		FavouriteMessageDetailsActivity.currentUser = currentUser;
		FavouriteMessageDetailsActivity.currentCategory = currentCategory;
//		MessageDetailsActivity.messageCurrentId = position;
		FavouriteMessageDetailsActivity.currentScreen = position;
		FavouriteMessageDetailsActivity.messageCurrentId = position;
		
		FavouriteMessageDetailsActivity.totalMessage = data.size();
//		MessageDetailsActivity.categoryId = categoryId;

		startActivityForResult(new Intent(getBaseContext(), FavouriteMessageDetailsActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
		
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

			i11=holder.getImage();
			i11.setImageResource(categoryIcon[currentCategory]);




			return convertView;
		}
		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private ImageView i11=null;

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
	            	 Utilities.onDrawerItemClick(FavouriteMessageListActivity.this,pos);
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