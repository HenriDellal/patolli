package ru.henridellal.patolli;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class PatolliActivity extends Activity implements View.OnClickListener
{
	private SharedPreferences preferences;
	private SharedPreferences.Editor prefsEditor;
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		prefsEditor = preferences.edit();
		if (!(preferences.contains("firstPlayerName"))) {
			prefsEditor.putString("firstPlayerName", getResources().getString(R.string.firstPlayer))
						.putString("secondPlayerName", getResources().getString(R.string.secondPlayer)).commit();
			ThemeManager.setGameTheme(this, prefsEditor, R.array.classicTheme);
		}
		setContentView(R.layout.main);
		((Button)findViewById(R.id.onePlayerButton)).setOnClickListener(this);
		((Button)findViewById(R.id.twoPlayersButton)).setOnClickListener(this);
		((Button)findViewById(R.id.settingsButton)).setOnClickListener(this);
		((Button)findViewById(R.id.howToPlayButton)).setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		findViewById(R.id.mainView).setBackgroundColor(preferences.getInt("primaryColor", 0));
	}
	
	public void setScreenManager() {
		View mainView = findViewById(R.id.mainView);
		ScreenManager screenManager = new ScreenManager(this, mainView.getWidth(), mainView.getHeight());
		PatolliApplication.setScreenManager(screenManager);
	}
	
	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch(id) {
			case R.id.onePlayerButton:
				Intent singlePlayerIntent = new Intent(this, GameActivity.class);
				singlePlayerIntent.putExtra(GameActivity.SECOND_PLAYER_IS_HUMAN, false);
				setScreenManager();
				startActivity(singlePlayerIntent);
				break;
			case R.id.twoPlayersButton:
				Intent multiplayerIntent = new Intent(this, GameActivity.class);
				multiplayerIntent.putExtra(GameActivity.SECOND_PLAYER_IS_HUMAN, true);
				setScreenManager();
				startActivity(multiplayerIntent);
				break;
			case R.id.settingsButton:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			case R.id.howToPlayButton:
				startActivity(new Intent(this, TutorialActivity.class));
				break;
		}
	}
}
