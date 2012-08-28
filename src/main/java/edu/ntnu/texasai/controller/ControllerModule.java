package edu.ntnu.texasai.controller;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PokerController.class).in(Singleton.class);
        bind(PlayerController.class).to(PlayerControllerPhaseI.class).in(Singleton.class);

        bind(HandPowerRanker.class).in(Singleton.class);
    }
}
