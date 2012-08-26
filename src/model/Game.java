package model;

public class Game {
	
	private final int TOTAL_HANDS = 1000;
	
	public void startGame(){
		int playedHands = 0;
		while(playedHands < TOTAL_HANDS){
			playNewHand();
		}
	}
	
	public void playNewHand(){
		//todo
	}

}
