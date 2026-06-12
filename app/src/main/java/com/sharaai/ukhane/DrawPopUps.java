package com.sharaai.ukhane;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

public class DrawPopUps
{

	public AlertDialog.Builder builder;
	public AlertDialog alertDialog;
	public TextPaint mTextPaint;
	public TextView dialog_box;

	public ScrollView scrollView;
	public MessageDetailsActivity hg;
	public Context _context;

	public DrawPopUps(Context context/* ,Game g */) {
		this._context = (Activity) context;
		hg = (MessageDetailsActivity) context;
		initTextViewOutline();
	}

	public void initTextViewOutline() {
		mTextPaint = new TextPaint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(16);
		mTextPaint.setColor(0xFF000000);
		mTextPaint.setStyle(Paint.Style.FILL);
	}

	Button b1;

	public void drawPopups(int strId, int iconId) {

		builder = new AlertDialog.Builder(_context);
		
		dialog_box = new TextView(_context);
		RelativeLayout r1 = new RelativeLayout(_context);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		

		LinearLayout l2 = new LinearLayout(_context);
		l2.setOrientation(LinearLayout.VERTICAL);
		l2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		l2.setWeightSum(1);



		scrollView = new ScrollView(_context);
		scrollView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		scrollView.setPadding(5, 5, 5, 50);

		dialog_box.setText(strId);
		dialog_box.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
		
		dialog_box.setPadding(10, 10, 10, 50);
		dialog_box.setWidth((int) (MessageDetailsActivity.width - 100));

		dialog_box.setTextColor(Color.WHITE);
		dialog_box.setTextSize(15);

		dialog_box.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));

		l2.addView(dialog_box);
		scrollView.addView(l2);
		// l4.addView(scrollView);

		b1 = new Button(_context);
		 b1.setBackgroundResource(R.drawable.close1);
		b1.setText("");
		b1.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 4));
		
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

//		l3.setId(11);
		scrollView.setId(22);
		dialog_box.setId(33);
		r1.addView(scrollView);

		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		r1.addView(b1, lp);

		if (iconId != -1) {
			ImageView imgIcon = new ImageView(_context);
			imgIcon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			imgIcon.setImageResource(iconId);
			imgIcon.setPadding(0, 10, 0, 0);
			RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			lay.addRule(RelativeLayout.CENTER_HORIZONTAL);
			r1.addView(imgIcon, lay);
			scrollView.setPadding(5, 105, 5, 50);
		}

		r1.setBackgroundColor(Color.TRANSPARENT);
//		r1.setBackgroundResource(R.drawable.back);

		alertDialog = builder.create();
		alertDialog.setView(r1, 0, 0, 0, 0);
		WindowManager.LayoutParams wlp = alertDialog.getWindow()
				.getAttributes();

		alertDialog.show();

		alertDialog.getWindow().setAttributes(wlp);

		alertDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				alertDialog.dismiss();
			}
		});
	}

}
