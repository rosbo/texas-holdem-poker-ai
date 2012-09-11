package edu.ntnu.texasai.controller.preflopsim;

import edu.ntnu.texasai.controller.EquivalenceClassController;
import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.model.Game;
import edu.ntnu.texasai.model.Player;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PersistenceManager;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

public class PreFlopSimulatorController {
    private final Game game;
    private final Logger logger;
    private final GameProperties gameProperties;
    private final PlayerControllerPreFlopRoll playerControllerPreFlopRoll;
    private final EquivalenceClassController equivalenceClassController;
    private final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll;
    private final StatisticsController statisticsController;
    private final PersistenceManager persistenceController;

    @Inject
    public PreFlopSimulatorController(final Logger logger, final GameProperties gameProperties,
                                      final PlayerControllerPreFlopRoll playerControllerPreFlopRoll,
                                      final EquivalenceClassController equivalenceClassController,
                                      final GameHandControllerPreFlopRoll gameHandControllerPreFlopRoll,
                                      final StatisticsController statisticsController,
                                      final PersistenceManager persistenceController) {
        this.logger = logger;
        this.gameProperties = gameProperties;
        this.playerControllerPreFlopRoll = playerControllerPreFlopRoll;
        this.equivalenceClassController = equivalenceClassController;
        this.gameHandControllerPreFlopRoll = gameHandControllerPreFlopRoll;
        this.statisticsController = statisticsController;
        this.persistenceController = persistenceController;
        game = new Game(new ArrayList<Player>());
    }

    public void play() {

        int idDatabase = 0;

        this.equivalenceClassController.generateAllEquivalenceClass();

        game.addPlayer(new Player(0, gameProperties.getInitialMoney(),
                playerControllerPreFlopRoll));
        Collection<EquivalenceClass> equivalenceClasses = equivalenceClassController
                .getEquivalenceClasses();
        for (int numberOfPlayers = 2; numberOfPlayers <= 4; numberOfPlayers++) {
            // gameProperties.getPlayers().size()
            game.addPlayer(new Player(numberOfPlayers, gameProperties
                    .getInitialMoney(), playerControllerPreFlopRoll));
            for (EquivalenceClass equivalenceClass : equivalenceClasses) {
                this.statisticsController.initializeStatistics();
                for (int i = 0; i < gameProperties.getNumberOfHands(); i++) {
                    // this.statisticsController.setCurrentNumberOfPlayers(new
                    // Integer(numberOfPlayers));
                    gameHandControllerPreFlopRoll.play(game, equivalenceClass);
                    game.setNextDealer();
                }

                Double percentageOfWinsPlayer0 = this.statisticsController
                        .getPercentageOfWinsPlayer0(gameProperties
                                .getNumberOfHands());
                logger.log("=================");
                logger.log("STATISTICS FOR EQUIVALENCE CLASS "
                        + equivalenceClass.toString());
                logger.log("Number of hands played: "
                        + gameProperties.getNumberOfHands());
                logger.log("Number players: " + numberOfPlayers);
                logger.log("Percentage of wins is " + percentageOfWinsPlayer0);
                idDatabase++;
                persistenceController.persistResult(idDatabase, numberOfPlayers, equivalenceClass,
                        percentageOfWinsPlayer0);
            }
        }
    }
}
