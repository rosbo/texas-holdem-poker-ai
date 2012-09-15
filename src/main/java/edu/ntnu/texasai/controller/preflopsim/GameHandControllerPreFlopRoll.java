package edu.ntnu.texasai.controller.preflopsim;

import com.google.inject.Inject;

import edu.ntnu.texasai.controller.GameHandController;
import edu.ntnu.texasai.controller.HandPowerRanker;
import edu.ntnu.texasai.controller.HandStrengthEvaluator;
import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.model.BettingRoundName;
import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.GameHand;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.preflopsim.GameHandPreFlopRoll;
import edu.ntnu.texasai.model.gameproperties.GameProperties;
import edu.ntnu.texasai.utils.Logger;

public class GameHandControllerPreFlopRoll extends GameHandController {

    @Inject
    public GameHandControllerPreFlopRoll(Logger logger,
            HandPowerRanker handPowerRanker, GameProperties gameProperties,
            StatisticsController statisticsController, HandStrengthEvaluator handStrengthEvaluator, OpponentModeler opponentModeler) {
        super(logger, handPowerRanker, gameProperties, statisticsController, handStrengthEvaluator, opponentModeler);
    }

    public void play(Game game, EquivalenceClass equivalenceClass) {
        logger.log("-----------------------------------------");
        logger.log("Game Hand #" + (game.gameHandsCount() + 1));
        logger.log("-----------------------------------------");
        logger.log("-----------------------------------------");
        logger.log(equivalenceClass.toString());
        logger.log("-----------------------------------------");
        GameHand gameHand = createGameHand(game, equivalenceClass);

        Boolean haveWinner = false;
        while (!gameHand.getBettingRoundName().equals(
                BettingRoundName.POST_RIVER)
                && !haveWinner) {
            haveWinner = playRound(gameHand);
        }

        if (!haveWinner) {
            showDown(gameHand);
        }
    }

    private GameHand createGameHand(Game game, EquivalenceClass equivalenceClass) {
        GameHand gameHand = new GameHandPreFlopRoll(game.getPlayers(),
                equivalenceClass);
        game.addGameHand(gameHand);
        return gameHand;
    }

}
