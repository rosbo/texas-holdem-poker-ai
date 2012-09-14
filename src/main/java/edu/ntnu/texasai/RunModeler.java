package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.controller.PokerController;
import edu.ntnu.texasai.controller.opponentmodeling.OpponentModeler;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.model.opponentmodeling.ContextAggregate;
import edu.ntnu.texasai.persistence.OpponentsModelPersistence;
import edu.ntnu.texasai.utils.GameProperties;

import java.util.List;

public class RunModeler {
    private static final int NUMBER_OF_HANDS = 1000; // TODO: Run with 10k and AI final

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TexasModule());
        OpponentsModelPersistence opponentsModelPersistence = injector.getInstance(OpponentsModelPersistence.class);

        GameProperties gameProperties = injector.getInstance(GameProperties.class);
        gameProperties.setNumberOfHands(NUMBER_OF_HANDS);

        opponentsModelPersistence.clear();

        PokerController pokerController = injector.getInstance(PokerController.class);
        pokerController.play();

        OpponentModeler opponentModeler = injector.getInstance(OpponentModeler.class);
        persistOpponentModelingData(opponentModeler, opponentsModelPersistence);
    }

    private static void persistOpponentModelingData(OpponentModeler opponentModeler, OpponentsModelPersistence
            opponentsModelPersistence) {
        for (List<ContextAggregate> playerModel : opponentModeler.getPlayerModels().values()) {
            for (ContextAggregate contextAggregate : playerModel) {
                opponentsModelPersistence.persist(contextAggregate);
            }
        }
    }
}
