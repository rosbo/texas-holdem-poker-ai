package edu.ntnu.texasai.controller;

import com.google.inject.AbstractModule;
import edu.ntnu.texasai.persistence.PersistenceManager;
import javax.inject.Singleton;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PokerController.class).in(Singleton.class);
        bind(GameHandController.class).in(Singleton.class);
        bind(PersistenceManager.class).in(Singleton.class);
        bind(PlayerControllerPhaseI.class).in(Singleton.class);
        bind(PlayerControllerPhaseII.class).in(Singleton.class);
        bind(EquivalenceClassController.class).in(Singleton.class);
        bind(HandPowerRanker.class).in(Singleton.class);
        bind(StatisticsController.class).in(Singleton.class);
        bind(HandStrengthEvaluator.class).in(Singleton.class);
    }
}
