package ru.henridellal.patolli;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;

public class ThemeManager {

	public static void setGameTheme(Context context, SharedPreferences.Editor prefsEditor, int id) {
		TypedArray themeArray = context.getResources().obtainTypedArray(id);
		prefsEditor.putInt("primaryColor", themeArray.getColor(0,0))
					.putInt("secondaryColor", themeArray.getColor(1,0))
					.putInt("additionalColor", themeArray.getColor(2,0))
					.putInt("playerOneColor", themeArray.getColor(3,0))
					.putInt("playerTwoColor", themeArray.getColor(4,0))
					.putInt("textColor", themeArray.getColor(5,0))
					.commit();
	}
	
}
