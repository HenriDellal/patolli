package ru.henridellal.patolli;

import android.app.Application;

public class PatolliApplication extends Application {
	private static ScreenManager screenManager;
	
	public static void setScreenManager(ScreenManager manager) {
		screenManager = manager;
	}
	
	public static ScreenManager getScreenManager() {
		return screenManager;
	}
	
}
