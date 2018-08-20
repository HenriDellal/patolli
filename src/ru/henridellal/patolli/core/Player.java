package ru.henridellal.patolli.core;

import java.util.Map;
import java.util.ArrayList;

public class Player{
	private boolean type;
	private char symbol;
	
	private ArrayList<Map<String, Integer>> moves;
	private ArrayList<Point> pointset;
	private Game game;
	
	public ArrayList<Point> getPointset() {
		return pointset;
	}
	public Player(Game game, char symbol, boolean type, String name){
		this.game = game;
		this.symbol = symbol; //shows player's char
		this.moves = new ArrayList<Map<String, Integer>>();
		this.type = type; //true if human
		this.pointset = createSet(this.game, this.symbol);
	}
	
	public boolean isHuman() {
		return type;
	}
	
	/* forms a list with player's Points */
	private static ArrayList<Point> createSet(Game game, char owner){
		ArrayList<Point> arr = new ArrayList<Point>();
		for (int i = 49; i < 53; i++){
			arr.add(new Point(game, owner, (char)i));
		}
		return arr;
	}
	
	public void go(int c){
		for (int i = 0; i < moves.size(); i++){
			if (c+48 == moves.get(i).get("char")){
				pointset.get(c-1).move(moves.get(i).get("x"), moves.get(i).get("y"));
			}
		}
	}
	
	public void setMoves(int n){
		/* saves all moves in this.moves */
		int s = moves.size();
		for (int i = 0; i < s; i++) {
			moves.remove(0);
		}//remove all elements of moves
		s = pointset.size();
		for (int i = 0; i < s; i++){
			Map<String, Integer> poss_move = pointset.get(i).getNewPos(n);
			if (!(poss_move.get("x")==0 && poss_move.get("y") == 0)){
				if (!(game.getField().checkBusy(poss_move.get("x"), poss_move.get("y"), symbol))){
					moves.add(poss_move);
				}
			}
		}
	}
	
	public ArrayList<Map<String, Integer>> getMoves() {
		return moves;
	}
	
}
