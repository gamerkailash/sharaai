package com.sharaai.ukhane;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewMedium extends TextView {
	public static Typeface tf;
	public static Typeface tf_normal;
	public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomTextViewMedium(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomTextViewMedium(Context context) {
		super(context);
		init();
	}
	public void init() {
		if(tf == null){
			tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
		}
		setTypeface(tf );

	}

}