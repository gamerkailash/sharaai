package com.sharaai.ukhane;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.drawerlayout.widget.DrawerLayout;

public class MessageListActivity extends ListActivity implements AppConstants {
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

        mMessageView = (MessageView) findViewById(R.id.snake);
        mMessageView.requestLayout();
        mMessageView.rateActivity = this;

        imageButton1 = (ImageView) findViewById(R.id.opt_checkin);
        imageButton2 = (ImageView) findViewById(R.id.opt_rateit);
        imageButton3 = (ImageView) findViewById(R.id.opt_stream);

        if (currentUser == 0)
            imageButton1.setBackgroundResource(R.drawable.tunein_1);
        else
            imageButton3.setBackgroundResource(R.drawable.stream_1);

        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        data = new Vector<RowData>();
        adapter = new CustomAdapter(this, R.layout.message_list, R.id.title, data);
        setListAdapter(adapter);
        getListView().setTextFilterEnabled(true);

        messageStartId = categoryMessageStartId[currentCategory][currentUser];
        messageEndId = categoryMessageEndId[currentCategory][currentUser];
        for (int i = messageStartId; i <= messageEndId; i++) {
            try {
                String ss = getResources().getString(i);
                rd = new RowData(i, ss, "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            data.add(rd);
            adapter.notifyDataSetChanged();
        }

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

    public void refreshList() {
        if (lastUser != currentUser) {
            lastUser = currentUser;
            data.removeAllElements();
            messageStartId = categoryMessageStartId[currentCategory][currentUser];
            messageEndId = categoryMessageEndId[currentCategory][currentUser];
            if (currentUser == 0) {
                imageButton1.setBackgroundResource(R.drawable.tunein_1);
                imageButton2.setBackgroundResource(R.drawable.rateit);
                imageButton3.setBackgroundResource(R.drawable.stream);
            } else {
                imageButton1.setBackgroundResource(R.drawable.tunein);
                imageButton2.setBackgroundResource(R.drawable.rateit);
                imageButton3.setBackgroundResource(R.drawable.stream_1);
            }
            for (int i = messageStartId; i <= messageEndId; i++) {
                try {
                    String ss = getResources().getString(i);
                    rd = new RowData(i, ss, "");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        MessageDetailsActivity.currentUser = currentUser;
        MessageDetailsActivity.currentCategory = currentCategory;
        MessageDetailsActivity.messageCurrentId = position;
        MessageDetailsActivity.currentScreen = position;
        MessageDetailsActivity.categoryId = categoryId;
        startActivityForResult(new Intent(getBaseContext(), MessageDetailsActivity.class), CategoryListActivity.RESULT_CLOSE_ALL);
    }

    private class RowData {
        protected int mId;
        protected String mTitle;
        protected String mDetail;

        RowData(int id, String title, String detail) {
            mId = id;
            mTitle = title;
            mDetail = detail;
        }

        @Override
        public String toString() {
            return mId + " " + mTitle + " " + mDetail;
        }
    }

    private class CustomAdapter extends ArrayAdapter<RowData> {
        public CustomAdapter(Context context, int resource, int textViewResourceId, List<RowData> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final RowData rowData = getItem(position);
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.message_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            holder.gettitle().setText(rowData.mTitle);
            holder.getImage().setImageResource(categoryIcon[categoryId]);
            return convertView;
        }

        private class ViewHolder {
            private View mRow;
            private TextView title = null;
            private ImageView i11 = null;

            public ViewHolder(View row) {
                mRow = row;
            }

            public TextView gettitle() {
                if (null == title) title = (TextView) mRow.findViewById(R.id.title);
                return title;
            }

            public ImageView getImage() {
                if (null == i11) i11 = (ImageView) mRow.findViewById(R.id.img);
                return i11;
            }
        }
    }

    DrawerLayout drawer;
    ListView navList;
    private DrawerViewAdapter mStatusAdapter;

    public void initDrawer() {
        mStatusAdapter = new DrawerViewAdapter(this, Utilities.getDrawerData(), Utilities.getDrawerIconData());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (ListView) findViewById(R.id.drawer);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup) myinflater.inflate(R.layout.header_drawer, navList, false);
        navList.addHeaderView(myHeader, null, false);
        navList.setAdapter(mStatusAdapter);

        navList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
                Utilities.onDrawerItemClick(MessageListActivity.this, pos);
                drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                });
                drawer.closeDrawer(navList);
            }
        });
    }

    public void openDrawer(View v) {
        drawer.openDrawer(navList);
    }
}
