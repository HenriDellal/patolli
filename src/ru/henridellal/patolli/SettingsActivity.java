package ru.henridellal.patolli;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements View.OnClickListener
{
	private SharedPreferences preferences;
	private SharedPreferences.Editor prefsEditor;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		prefsEditor = preferences.edit();
		setContentView(R.layout.settingsscreen);
		findViewById(R.id.settingsScreen).setBackgroundColor(preferences.getInt("primaryColor", 0));
		((Button)findViewById(R.id.renameFirstPlayer)).setOnClickListener(this);
		((Button)findViewById(R.id.renameSecondPlayer)).setOnClickListener(this);
		((Button)findViewById(R.id.theme1)).setOnClickListener(this);
		((Button)findViewById(R.id.theme2)).setOnClickListener(this);
		((Button)findViewById(R.id.theme3)).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch(view.getId()) {
			case R.id.renameFirstPlayer:
				showRenameDialog("firstPlayerName");
				break;
			case R.id.renameSecondPlayer:
				showRenameDialog("secondPlayerName");
				break;
			case R.id.theme1:
				ThemeManager.setGameTheme(this, preferences.edit(), R.array.classicTheme);
				findViewById(R.id.settingsScreen).setBackgroundColor(preferences.getInt("primaryColor", 0));
				break;
			case R.id.theme2:
				ThemeManager.setGameTheme(this, preferences.edit(), R.array.blueTheme);
				findViewById(R.id.settingsScreen).setBackgroundColor(preferences.getInt("primaryColor", 0));
				break;
			case R.id.theme3:
				ThemeManager.setGameTheme(this, preferences.edit(), R.array.greenTheme);
				findViewById(R.id.settingsScreen).setBackgroundColor(preferences.getInt("primaryColor", 0));
				break;
		}
	}
	
	public void showRenameDialog(String playerKey) {
		final String key = playerKey;
		LayoutInflater li = LayoutInflater.from(this);
		final View renamer = li.inflate(R.layout.renamedialog, null);
		((EditText) renamer.findViewById(R.id.nameField)).setText(preferences.getString(key, ""));
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(renamer)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface di, int id) {
					prefsEditor.putString(key, ((EditText) renamer.findViewById(R.id.nameField)).getText().toString())
								.commit();
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface di, int id) {
				}
			});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
