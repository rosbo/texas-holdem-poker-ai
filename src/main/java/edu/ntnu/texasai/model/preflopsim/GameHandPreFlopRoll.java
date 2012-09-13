package edu.ntnu.texasai.model.preflopsim;

import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.Deck;
import edu.ntnu.texasai.model.cards.EquivalenceClass;

import java.util.List;

public class GameHandPreFlopRoll extends GameHand {

    private final EquivalenceClass equivalenceClass;

    public GameHandPreFlopRoll(final List<Player> players, final EquivalenceClass equivalenceClass) {
        super(players);
        this.equivalenceClass = equivalenceClass;
    }

    /**
     * Deals the hole cards. The prospective of the simulation is player0's one,
     * so players0's hole cards are the same of equivalence cards, while the
     * other players receive random cards form the top of the deck.
     */
    @Override
    protected void dealHoleCards() {
        Deck deck = getDeck();
        Player player1 = getPlayer1();
        getPlayers().remove(player1);

        List<Card> holeCards = this.equivalenceClass.equivalence2cards();
        Card hole1 = holeCards.get(0);
        Card hole2 = holeCards.get(1);
        player1.setHoleCards(hole1, hole2);
        deck.removeCard(hole1);
        deck.removeCard(hole2);

        // other players card are random
        for (Player player : this.getPlayers()) {
            hole1 = deck.removeTopCard();
            hole2 = deck.removeTopCard();
            player.setHoleCards(hole1, hole2);
        }

        getPlayers().add(player1);
    }

    private Player getPlayer1(){
        for (Player player : getPlayers()) {
            if (player.getNumber() == 1) {
                return player;
            }
        }
        throw new IllegalArgumentException("Must have a player #1 during rollout");
    }

}
