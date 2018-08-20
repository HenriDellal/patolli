package ru.henridellal.patolli;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Map;
import ru.henridellal.patolli.core.Point;
import ru.henridellal.patolli.core.Game;

public class CoveringView extends ImageView {
	private SharedPreferences preferences;
	private ScreenManager screenManager;
	private Paint paint;
	private Bitmap b;
	private Canvas canvas;
	private int[] playerColors, centerCoord;
	private float textSize;
	
	public void initView() {
		screenManager = PatolliApplication.getScreenManager();
		b = Bitmap.createBitmap(screenManager.getWidthPixels(), screenManager.getHeightPixels(), Bitmap.Config.ARGB_8888);//fix
		canvas = new Canvas(b);
		paint = new Paint();
		paint.setAntiAlias(true);
		textSize = screenManager.getDensityDpi()/8;
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);
		centerCoord = new int[] {screenManager.getWidthPixels()/2, screenManager.getHeightPixels()/2};
		playerColors = new int[] {preferences.getInt("playerOneColor", 0), preferences.getInt("playerTwoColor", 0)};
	}
	
	public void drawRoll(int currentPlayer, Integer n) {
		paint.setColor(preferences.getInt("secondaryColor", 0));
		int gap;
		if (currentPlayer == 0) {
			gap = (int)(centerCoord[0]*0.75f);
		}
		else {
			gap = (int)(-centerCoord[0]*0.75f);
		}
		this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1], (float)(screenManager.getWidthPixels()/16), paint);
		paint.setColor(preferences.getInt("textColor", 0));
		switch (n) {
			case 1:
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				break;
			case 2:
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1]-screenManager.getWidthPixels()/32, (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1]+screenManager.getWidthPixels()/32, (float)(screenManager.getWidthPixels()/80), paint);
				break;
			case 3:
				this.canvas.drawCircle(centerCoord[0]+gap-screenManager.getWidthPixels()/32, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap+screenManager.getWidthPixels()/32, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				break;
			default:
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1]-screenManager.getWidthPixels()/32, (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap-screenManager.getWidthPixels()/32, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap, centerCoord[1]+screenManager.getWidthPixels()/32, (float)(screenManager.getWidthPixels()/80), paint);
				this.canvas.drawCircle(centerCoord[0]+gap+screenManager.getWidthPixels()/32, centerCoord[1], (float)(screenManager.getWidthPixels()/80), paint);
				
		}
		//this.canvas.drawText(n.toString(), centerCoord[0]+gap, centerCoord[1], paint);
	}
	public void update(Game game, int currentPlayer, int n) {
		ArrayList<Map<String, Integer>> moves = game.getMoves();
		b.eraseColor(0);
		String text;
		Integer charInt;
		ArrayList<ArrayList<Float>> previousArr = new ArrayList<ArrayList<Float>>();
		previousArr.add(new ArrayList<Float>());
		previousArr.add(new ArrayList<Float>());
		for (int i = 0; i<=1; i++) {
			paint.setColor(playerColors[i]);
			for (Point each: game.getPointList(i)) {
				float[] arr = screenManager.getDrawCoord(each.getX(), each.getY());
				this.canvas.drawCircle(arr[0], arr[1], (float)(screenManager.getWidthPixels()/30), paint);
			}
		}
		paint.setColor(preferences.getInt("textColor", 0));
		for (Map<String, Integer> each: moves) {
			charInt = each.get("char") - 48;
			text = charInt.toString();
			float[] arr = screenManager.getDrawCoord(each.get("x"), each.get("y"));
			if (!(previousArr.get(0).indexOf(arr[0]) == previousArr.get(1).indexOf(arr[1]) && previousArr.get(0).indexOf(arr[0]) != -1)) {
				this.canvas.drawText(text, arr[0], arr[1]+textSize/4, paint);
				previousArr.get(0).add(arr[0]);
				previousArr.get(1).add(arr[1]);
			}
		}
		drawRoll(currentPlayer, n);
	}
	
	//another version for multiplayer
	
	/*public void update(ArrayList<Map<String, Integer>> moves, ArrayList<Map<String, Integer>> pointList, int currentPlayer, int n) {
		//ArrayList<Map<String, Integer>> moves = moves;
		b.eraseColor(0);
		String text;
		Integer charInt;
		ArrayList<ArrayList<Float>> previousArr = new ArrayList<ArrayList<Float>>();
		previousArr.add(new ArrayList<Float>());
		previousArr.add(new ArrayList<Float>());
		
		//draw points
		for (int i=0; i<pointList.size(); i++) {
			for (Map<String, Integer> each: pointList) {
				int j = each.get("char");
				paint.setColor(playerColors[j]);
				float[] arr = gameactivity.getDrawCoord(each.get("x"), each.get("y"));
				this.canvas.drawCircle(arr[0], arr[1], (float)(gameactivity.getWidthPixels()/30), paint);
			}
		}
		
		//draw moves
		paint.setColor(gameactivity.getActivityPreferences().getInt("textColor", 0));
		for (Map<String, Integer> each: moves) {
			charInt = each.get("char");
			text = charInt.toString();
			float[] arr = gameactivity.getDrawCoord(each.get("x"), each.get("y"));
			
			if (!(previousArr.get(0).indexOf(arr[0]) == previousArr.get(1).indexOf(arr[1]) && previousArr.get(0).indexOf(arr[0]) != -1)) {
				this.canvas.drawText(text, arr[0], arr[1]+textSize/4, paint);
				previousArr.get(0).add(arr[0]);
				previousArr.get(1).add(arr[1]);
			}
		}
		drawRoll(currentPlayer, n);
	}*/
	public CoveringView(Context context, AttributeSet attrs) {
		super(context, attrs);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		initView();
	}
	public CoveringView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		initView();
	}
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		//c.drawBitmap(b);
		this.setImageBitmap(b);
	}
}
