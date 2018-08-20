package ru.henridellal.patolli;

import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;

public class TutorialActivity extends Activity {
	private SharedPreferences preferences;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.howtoplay);
		findViewById(R.id.howToPlay).setBackgroundColor(preferences.getInt("primaryColor", 0));
	}
	
}
