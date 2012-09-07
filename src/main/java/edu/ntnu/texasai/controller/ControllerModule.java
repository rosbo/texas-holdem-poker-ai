package edu.ntnu.texasai.controller;

import com.google.inject.AbstractModule;

import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.persistence.PersistenceController;

import javax.inject.Singleton;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PokerController.class).in(Singleton.class);
        bind(GameHandController.class).in(Singleton.class);
        bind(PersistenceController.class).in(Singleton.class);
        bind(PlayerControllerPhaseI.class).in(Singleton.class);
        bind(PlayerControllerPhaseII.class).in(Singleton.class);
        bind(EquivalenceClassController.class).in(Singleton.class);
        bind(HandPowerRanker.class).in(Singleton.class);
        bind(StatisticsController.class).in(Singleton.class);
    }
}
