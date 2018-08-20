package ru.henridellal.patolli;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.preference.PreferenceManager;
//import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.*;

public class BackgroundView extends ImageView {
	private Paint paint;
	private Bitmap b;
	private Canvas canvas;
	private int primaryColor, secondaryColor, additionalColor;
	public void initView(Activity activity) {
		ScreenManager screenManager = PatolliApplication.getScreenManager();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		primaryColor = preferences.getInt("primaryColor", activity.getResources().getColor(R.color.yellow));
		secondaryColor = preferences.getInt("secondaryColor", activity.getResources().getColor(R.color.white));
		additionalColor = preferences.getInt("additionalColor", activity.getResources().getColor(R.color.red));
		b = Bitmap.createBitmap(screenManager.getWidthPixels(), screenManager.getHeightPixels(), Bitmap.Config.ARGB_8888);//fix
		canvas = new Canvas(b);
		b.eraseColor(primaryColor);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(secondaryColor);
		paint.setStyle(Paint.Style.FILL);
		char[][] pointbases = new char[15][15];
		for (int i=1; i<14; i++) {
			pointbases[7][i] = pointbases[8][i] = pointbases[i][7] = pointbases[i][8] = '5';
		}
		for (int i=7; i<=8; i++){
			pointbases[1][i] = pointbases[14][i] = pointbases[i][1] = pointbases[i][14] = '8';
		}
		for (int i=0; i < 15; i++) {
			for (int j=0; j < 15; j++) {
				if (pointbases[i][j] == '5') {
					float[] arr = screenManager.getDrawCoord(i, j);
					this.canvas.drawCircle(arr[0], arr[1], (float)(screenManager.getWidthPixels()/24), paint);
				}
			}
		}
		paint.setColor(additionalColor);
		for (int i=0; i < 15; i++) {
			for (int j=0; j < 15; j++) {
				if (pointbases[i][j] == '8') {
					float[] arr = screenManager.getDrawCoord(i, j);
					this.canvas.drawCircle(arr[0], arr[1], (float)(screenManager.getWidthPixels()/24), paint);
				}
			}
		}
		//generate bitmap
	}
	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			initView((Activity)context);
		} catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	public BackgroundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//initView((Activity)context);
		try {
			initView((Activity)context);
		} catch (Exception e) {
			Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		this.setImageBitmap(b);
	}
}
