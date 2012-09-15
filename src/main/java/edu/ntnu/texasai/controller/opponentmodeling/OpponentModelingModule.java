package edu.ntnu.texasai.controller.opponentmodeling;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class OpponentModelingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OpponentModeler.class).in(Singleton.class);
    }
}
