package model;

public class GameHand {

	// just pseudocode
	public void distribuiteCard(){
		for (Player pl : playersInGame){
			PlayerHand ph = pl.getHand();
			List<Card> deck = DeckManager.getDeck();
			ph.getHand.add(DeckManager.removeTopCard());//first card
			ph.getHand.add(DeckManager.removeTopCard());//second card
		}
	}
}
