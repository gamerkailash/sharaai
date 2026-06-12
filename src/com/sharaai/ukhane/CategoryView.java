package com.sharaai.ukhane;


import java.io.InputStream;
import java.util.Random;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class CategoryView extends View {

	public static CategoryView mCategoryView;
	public static final Paint mPaint = new Paint();
	public static CategoryListActivity mCategoryActivity;
	public CategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCategoryActivity = (CategoryListActivity) context;
		initSnakeView();
	}  

	public CategoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mCategoryActivity = (CategoryListActivity) context;
		initSnakeView();
	}
	private void initSnakeView() {
		setFocusable(true);
		mCategoryView = this;
	}
	/*public boolean onTouchEvent (MotionEvent event)
	{
		event.getAction();
		float xx = event.getX();
		float yy = event.getY();
		return true;
	}*/
	 @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        float touched_x = event.getX();
	        float touched_y = event.getY();
	        float length = this.getWidth()/4;
	        float height = this.getHeight()/2;
	        
	        int action = event.getAction();
	        switch (action) {
	        case MotionEvent.ACTION_DOWN:
	            break;
	        case MotionEvent.ACTION_MOVE:
	            break;
	        case MotionEvent.ACTION_UP:
	        	if(touched_x<length && touched_y<height){
	        		mCategoryActivity.openDrawer(null);
	        	}
	            break;
	        case MotionEvent.ACTION_CANCEL:
	            break;
	        case MotionEvent.ACTION_OUTSIDE:
	            break;
	        default:
	        }
	        return true;
	    }
	public Bitmap createImage(int ss)
	{
		Bitmap ssss = null;
		InputStream is = getResources().openRawResource(/*R.drawable.powermeterhud*/ss);
		ssss = BitmapFactory.decodeStream(is);
		return ssss;
	}

	Bitmap gamebg;
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(gamebg==null)
		{
			gamebg = createImage(R.drawable.heading);
		}
		
		//canvas.drawBitmap(gamebg,0,0,mPaint);
		Paint paint = new Paint();

		paint.setColor(0xff7a130b);
		paint.setStyle(Paint.Style.STROKE);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(18);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		String heading="";
		if(CategoryListActivity.currentUser==0)
		{
			heading = getResources().getString(R.string.forfemail);
		}else{
			heading = getResources().getString(R.string.formail);
		}
//		heading="ssssss";
		if(this.getHeight()<100)
		{
			paint.setTextSize(18);
			int y = this.getHeight()/2-(this.getHeight()/4-9);
			canvas.drawText(heading,CategoryListActivity.width/2,y, paint);
		}
		if(this.getHeight()>100)
		{
			if(this.getWidth()>580)
			{
				paint.setTextSize(38);
				int y = this.getHeight()/2-(this.getHeight()/4-19);
				canvas.drawText(heading,CategoryListActivity.width/2,y, paint);
			}else
			{
				paint.setTextSize(34);
				int y = this.getHeight()/2-(this.getHeight()/4-17);
				canvas.drawText(heading,CategoryListActivity.width/2,y, paint);
			}
//			canvas.drawText(heading,CategoryListActivity.width/2,40, paint);
		}
	}
}
