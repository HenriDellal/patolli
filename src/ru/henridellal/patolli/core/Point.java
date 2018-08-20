package ru.henridellal.patolli.core;

import java.util.Map;
import java.util.HashMap;

public class Point{
	private Game game;
	char owner;
	char pointchar;
	int x, y;
	int mp, sp;
	int start_x, start_y;
	
	public Point(Game game, char owner, char pointchar) {
		this.game = game;
		this.pointchar = pointchar;
		if (owner == '@'){
			x = mp = 0;
			y = sp = 8;
			start_x = 0;
			start_y = 8;
		}
		else {
			y = mp = 0;
			x = sp = 8;
			start_x = 8;
			start_y = 0;
		}
		this.owner = owner;
	}
	public int getX(){ return x;}
	public int getY(){ return y;}
	public boolean isOnFinish() {
		if (mp == 0 && sp == 7) {
			return true;
		}
		else {
			return false;
		}
	}
	public Map<String, Integer> getNewPos(int n){
		//returns new coordinates && unique char of Point
		int new_mp, new_sp;
		if (sp == 7){
			n = -n;
		}
		Map<String, Integer> result_dict = new HashMap<String, Integer>();
		result_dict.put("char", (int)(this.pointchar));
		if (((mp + n) >= 2) && ((this.mp + n) <= 13)){
			new_mp = mp + n;
			new_sp = sp;
		} else if ((mp + n) > 15){
			if (mp == 13) {
				new_mp = 16-n;
			} else {
				new_mp = 13;
			}
			new_sp = sp-1;
		} else if ((mp + n) < 1 && mp > 0) {
			new_mp = 0;
			new_sp = sp;
		} else {//elif this.mp + n == 1{
			new_mp = 0;
			new_sp = 0;
		} //no pass then
		
		if (owner == '@') {
			result_dict.put("x", new_mp);
			result_dict.put("y", new_sp);
		} else {
			result_dict.put("x", new_sp);
			result_dict.put("y", new_mp);
		}
		
		return result_dict;
	}
	
	public void move(int x, int y) {
		if (x == 0 && y == 7 || x == 7 && y == 0){
			game.getField().setPlace(x, y, '0');
		}
		//moves character of Point to the new place
		game.getField().setPlace(this.x, this.y, '0');
		//find fi with these coord && return it to start
		int j, k;
		if (owner == '@'){
			j = 1;
			k = 0;
		} else{
			j = 0;
			k = 1;
		}
		this.x = x;
		this.y = y;
		for (int i = 0; i < game.getPointList(j).size(); i++){
			if (game.getField().getPlace(x, y) != '0'){
				Point pointOnPath = game.getPoint(j, i);
				if ((pointOnPath.x == x) && (pointOnPath.y == y)){
					pointOnPath.x = pointOnPath.start_x;
					pointOnPath.y = pointOnPath.start_y;
					if (pointOnPath.owner == '@'){
						pointOnPath.mp = pointOnPath.x;
						pointOnPath.sp = pointOnPath.y;
					} else {
						pointOnPath.mp = pointOnPath.y;
						pointOnPath.sp = pointOnPath.x;
					}
				}
			}
		}
		game.getField().setPlace(x, y, owner);
		if (owner == '@') {
			mp = x;
			sp = y;
		} else {
			mp = y;
			sp = x;
		}
	}
}
