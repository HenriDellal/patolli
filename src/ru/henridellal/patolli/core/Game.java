package ru.henridellal.patolli.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Game {

	public static final int FIRST_PLAYER_WON = 1;
	public static final int SECOND_PLAYER_WON = 2;
	public static final int DRAW = 3;
	public static final int UNFINISHED = 0;
	
    private int buttonPressed;
    private int curr_player;
    
    private ArrayList<ArrayList<Point>> p;
    private ArrayList<Player> players;
    private Field field;
    
    public int getButtonPressed() {
    	return buttonPressed;
    }
    public void setButtonPressed(int button, boolean pressedByHuman) {
    	if (currPlayerIsHuman() == pressedByHuman) {
    		buttonPressed = button;
    	}
    }
    public void setButtonPressed(int button) {
    	buttonPressed = button;
    }
    public Field getField() {
    	return field;
    }
    
    public Point getPoint(int i, int j) {
    	return p.get(i).get(j);
    }
    
    public ArrayList<Point> getPointList(int i) {
    	return p.get(i);
    }
    
    private boolean isPlayerFinished(int i) {
    	for (Point each: getPointList(i)) {
    		if (!each.isOnFinish()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public int getState() {
    	if (!isPlayerFinished(0)) {
    		if (!isPlayerFinished(1)) {
    			return UNFINISHED;
    		} else {
    			return SECOND_PLAYER_WON;
    		}
    	} else if (!isPlayerFinished(1)) {
    		return FIRST_PLAYER_WON;
    	} else {
    		return DRAW;
    	}
    }
    
    public Game(boolean secondPlayerIsHuman){
    	setButtonPressed(0);
        p = new ArrayList<ArrayList<Point>>();
        players = new ArrayList<Player>();
        players.add(new Player(this, '@', true, "A"));
        players.add(new Player(this, '*', secondPlayerIsHuman, "B"));
        for (Player each: this.players){
        	p.add(each.getPointset());
        }
        //System.out.println(this.field);
        curr_player = 0;
    	field = new Field();
    }
    
    public void setCurrPlayer(int c){
    	curr_player = c;
    }
    public int getCurrPlayer(){
        return curr_player;
    }
    
    public boolean currPlayerIsHuman() {
    	return players.get(curr_player).isHuman();
    }
    
    public void setMoves(int n) {
    	players.get(curr_player).setMoves(n);
    }
    public ArrayList<Map<String, Integer>> getMoves() {
    	return players.get(curr_player).getMoves();
    }
    public boolean isMoveAvailable() {
    	for (Map<String, Integer> each: getMoves()) {
    		if (buttonPressed == each.get("char")-48) {
    			return true;
    		}
    	}
    	return false;
    }
    public void go() {
    	players.get(curr_player).go(buttonPressed);
    }
    /*
    Online game functions
    Should be moved to OnlineGame class
    */
    public int getN() {
    	return (int)(Math.random()*4)+1;
    }
    public String movesToString(ArrayList<Map<String, Integer>> moves) {
    	String result = "";
    	Integer charInt;
    	for (Map<String, Integer> each: moves){
    		charInt = each.get("char")-48;
    		result = result.concat(charInt.toString())
    			.concat(":")
    			.concat(each.get("x").toString())
    			.concat(":")
    			.concat(each.get("y").toString())
    			.concat(" ");
    	}
    	return result;//"\d:\d+:\d+ "
    }
    public String pointSetToString() {
    	String result = "";
    	for (int i=0; i<=1; i++) {
    		for (Point each: getPointList(i)) {
    			Integer x = each.getX();
    			Integer y = each.getY();
    			result = result.concat(x.toString())
    				.concat(i == 0 ? "a" : "b")
    				.concat(y.toString())
    				.concat(" ");
    		}
    	}
    	return result;//"\d+\D\d+ "
    }
}
