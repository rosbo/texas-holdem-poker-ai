package edu.ntnu.texasai.persistence;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

public class PersistenceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PersistenceManager.class).in(Singleton.class);
        bind(PreFlopPersistence.class).in(Singleton.class);
    }
}
