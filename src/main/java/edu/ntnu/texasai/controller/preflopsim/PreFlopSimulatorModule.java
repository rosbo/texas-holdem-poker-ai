package edu.ntnu.texasai.controller.preflopsim;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class PreFlopSimulatorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlayerControllerPreFlopRoll.class).in(Singleton.class);
        bind(GameHandControllerPreFlopRoll.class).in(Singleton.class);
    }

}
