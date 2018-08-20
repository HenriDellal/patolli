package ru.henridellal.patolli.core;

public class BotStrategy {

	private static int opponentIsOnWay(Game game) {
		for (int i = 0; i < game.getMoves().size(); i++) {
			for (Point point: game.getPointList(game.getCurrPlayer() == 0? 1: 0)) {
				if (point.getX() == game.getMoves().get(i).get("x") && point.getY() == game.getMoves().get(i).get("y")) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static int getMove(Game game) {
		int move = -1;
		if (game.getMoves().size() == 1) {
			return 0;
		}
		else if ((move = opponentIsOnWay(game)) != -1) {
			return move;
		}
		else {
			move = (int)(Math.random()*game.getMoves().size());
		}
		return move;
	}
	
}
