package com.sharaai.ukhane;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerViewAdapter extends BaseAdapter {
	Context con;

	ArrayList<String> mDrawerList=null;
	ArrayList<Integer> mDrawerIconList=null;
	
	boolean selectedStatus ;
	
	public DrawerViewAdapter(Context mContext , ArrayList<String> mStatusList, ArrayList<Integer> mIconList ) {
		con= mContext;
		this.mDrawerList = mStatusList;
		this.mDrawerIconList = mIconList;
//		this.selectedStatus = selectedStatus;
	}


	public int getCount() {
		return mDrawerList.size();
	}

	int mCurrentPosition;

	public Object getItem(int position) {
		mCurrentPosition = position;
		return mDrawerList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		private TextView tvStatus;
		private ImageView ivIcon;
		private int position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) 
		{  
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status_list_row, null);
			holder=new ViewHolder();
			holder.position=position;
			holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_title);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvStatus.setText(mDrawerList.get(holder.position));
		holder.ivIcon.setBackgroundResource(mDrawerIconList.get(holder.position));
		return convertView;
	}

}