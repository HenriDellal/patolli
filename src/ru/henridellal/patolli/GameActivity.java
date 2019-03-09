package ru.henridellal.patolli;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.henridellal.patolli.core.*;

import java.util.ArrayList;
import java.util.Map;

public class GameActivity extends Activity
{
	private SharedPreferences preferences;
	
	private Game game;
	public static final String SECOND_PLAYER_IS_HUMAN = "ru.henridellal.patolli.second_player_is_human";
	private boolean secondPlayerIsHuman;
	private GameTask gameTask;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamescreen);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        secondPlayerIsHuman = intent.getBooleanExtra(SECOND_PLAYER_IS_HUMAN, false);
        View.OnClickListener onclick = new View.OnClickListener() {
				public void onClick(View v) {
					Button button = (Button) v;
					game.setButtonPressed(Integer.parseInt(button.getText().toString()), true);
				}
			};
		((Button) findViewById(R.id.button_1)).setOnClickListener(onclick);
		((Button) findViewById(R.id.button_2)).setOnClickListener(onclick);
		((Button) findViewById(R.id.button_3)).setOnClickListener(onclick);
		((Button) findViewById(R.id.button_4)).setOnClickListener(onclick);
		gameTask = new GameTask();
        gameTask.execute();
    }
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
		showQuitDialog();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() != KeyEvent.ACTION_DOWN) {
			return super.onKeyDown(keyCode, event);
		}
		int buttonId;
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				buttonId = R.id.button_1;
				break;
			case KeyEvent.KEYCODE_2:
				buttonId = R.id.button_2;
				break;
			case KeyEvent.KEYCODE_3:
				buttonId = R.id.button_3;
				break;
			case KeyEvent.KEYCODE_4:
				buttonId = R.id.button_4;
				break;
			default:
				return super.onKeyDown(keyCode, event);
		}
		findViewById(buttonId).performClick();
		return true;
	}

	public void showQuitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// setting parameters of dialog
        builder.setMessage(getResources().getText(R.string.backToMenu).toString().concat("?"))
        	.setCancelable(true)
        	.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface di, int id) {
        			if (gameTask != null) {
        				gameTask.cancel(true);
        			}
        			game = null;
        			finish();
        		}
        	})
        	.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface di, int id) {
        		
        		}
        	});
        // create the dialog
        AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	public void showRestartDialog(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// setting parameters of dialog
        builder.setMessage(message.concat(" ").concat(getResources().getText(R.string.restartText).toString()))
        	.setCancelable(false)
        	.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface di, int id) {
        			//if (game != null) {
        				gameTask = new GameTask();
        				gameTask.execute();
        			/*} else {
        				launchOnlineGame();
        			}*/
        		}
        	})
        	.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface di, int id) {
        			// return to menu if "No" is pressed
        			finish();
        		}
        	});
        // create the dialog
        AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private class GameTask extends AsyncTask<Void, Void, Integer> {
		CoveringView coverView;
		TextView playerOne, playerTwo;
    	@Override
    	protected void onPreExecute() {
        	coverView = (CoveringView) findViewById(R.id.coverView);
        	game = new Game(secondPlayerIsHuman);
    	    playerOne = (TextView) findViewById(R.id.text1);
    	    playerOne.setText(preferences.getString("firstPlayerName", ""));
   	     playerTwo = (TextView) findViewById(R.id.text2);
    	    playerTwo.setText(preferences.getString("secondPlayerName", ""));
    	}
    	@Override
    	protected Integer doInBackground(Void... unused) {
    		boolean isActive = true;
    		ArrayList<Map<String, Integer>> moves;
    		do {
    			for (int i = 0; i <= 1; i++) {
    				game.setCurrPlayer(i);
    				int n = (int)(Math.random()*4)+1;
    				game.setMoves(n);
    				moves = game.getMoves();
    				coverView.update(game, i, n);
    				if (moves.size() != 0) {
    					if (game.currPlayerIsHuman()) {
    						try {
    							while (true) {
    								SystemClock.sleep(100);
    								if (game.getButtonPressed() != 0 && game.isMoveAvailable()) {
    									throw new Exception("end of wait");
    								}
    							}
    						}
    						catch (Exception e) {
    							//make move
    							game.go();//fix
    							game.setButtonPressed(0);
    						}
    					}
    					else {
    						game.setButtonPressed(moves.get(BotStrategy.getMove(game)).get("char")-48, false);
    						SystemClock.sleep(3000);
    						game.go();
    						game.setButtonPressed(0);
    					}
    				}
    				else {
    					SystemClock.sleep(3000);
    				}
    			}
    			if (game.getState() != Game.UNFINISHED) {
    				isActive = false;
    			}
    		} while (isActive);
    		return game.getState();
    	}
    	@Override
    	protected void onPostExecute(Integer result) {
    		String msg;
    		switch (result) {
    			case Game.FIRST_PLAYER_WON:
    				msg = playerOne.getText().toString().concat(" ").concat(getResources().getText(R.string.playerWon).toString());
    				break;
    			case Game.SECOND_PLAYER_WON:
    				msg = playerTwo.getText().toString().concat(" ").concat(getResources().getText(R.string.playerWon).toString());
    				break;
    			case Game.DRAW:
    				msg = getResources().getText(R.string.draw).toString();
    				break;
    			default:
    				msg = "";
    		}
    		showRestartDialog(msg);
    	}
    }
}
