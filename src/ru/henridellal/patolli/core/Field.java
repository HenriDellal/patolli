package ru.henridellal.patolli.core;

import java.util.ArrayList;
import java.util.Map;

public class Field{
	private char[][] map;
	private char[][] places;
	
	public char getPlace(int x, int y) {
		return places[x][y];
	}
	public void setPlace(int x, int y, char c) {
		places[x][y] = c;
	}
	public char[][] getMap(){
		return map;
	}
	public char[][] getPlaces(){
		return places;
	}
	public Field(){
		places = new char[15][15];
		map = new char[15][15];
		for (int i=0; i<15; i++){
			for (int j=0; j<15; j++){
				places[i][j] = '0';
			}
		}
		places[0][8] = '@';
		places[8][0] = '*';
	}
	public boolean checkBusy(int x, int y, char state){
		//state is for Point's symbol
		//System.out.println("checkBusy works");
		char test = places[x][y];
		if ((test != state) || (x == 0 && y == 7) || (x==7 && y ==0)) {
			return false;
		} else {
			return true;
		}
	}
	public void setMap(ArrayList<Map<String, Integer>> moves){//check
		//System.out.println('setMap works');
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				map[i][j] = places[i][j];
			}
		}
		//change every char in possible position with number of Point
		if (moves.size() != 0){
			for (int i = 0; i < moves.size(); i++){
				int x = moves.get(i).get("x");
				int y = moves.get(i).get("y");
				map[x][y] = (char)(moves.get(i).get("char").intValue());
			}
		}
	}
}
