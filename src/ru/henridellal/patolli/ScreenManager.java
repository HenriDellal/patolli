package ru.henridellal.patolli;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenManager {
	private float density, densityDpi;
	private int widthPixels, heightPixels;
	
	private Activity activity;
	
	public int getWidthPixels() {
		return widthPixels;
	}
	
	public int getHeightPixels() {
		return heightPixels;
	}
	
	public float getDensityDpi() {
		return densityDpi;
	}
	
	public float[] getDrawCoord(int x, int y){
		float[] arr;
		arr = new float[2];
		arr[0] = (float)(widthPixels/2-(x-y)*widthPixels/16);
		arr[1] = (float)((x+y)*widthPixels/16);
		return arr;
	}
	
	public void getScreenInfo(){
    	DisplayMetrics metrics = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	this.density = metrics.density;
    	this.densityDpi = metrics.densityDpi;
	}
	
	public ScreenManager(Activity activity, int widthPixels, int heightPixels) {
		this.activity = activity;
		getScreenInfo();
		this.widthPixels = widthPixels;
		this.heightPixels = heightPixels;
	}
}
