package edu.ntnu.texasai.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.ntnu.texasai.controller.ControllerModule;
import edu.ntnu.texasai.model.gameproperties.GameProperties;
import edu.ntnu.texasai.persistence.PersistenceModule;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Singleton;

public class TexasModule extends AbstractModule {
    private final LogLevel logLevel;
    private final GamePropertiesParameter gamePropertiesParameter;

    public TexasModule(LogLevel logLevel, GamePropertiesParameter gamePropertiesParameter) {
        this.logLevel = logLevel;
        this.gamePropertiesParameter = gamePropertiesParameter;
    }

    public TexasModule() {
        logLevel = LogLevel.ALL;
        gamePropertiesParameter = GamePropertiesParameter.DEMO;
    }

    @Override
    protected void configure() {
        install(new ControllerModule());
        install(new PersistenceModule());

        bind(LogLevel.class).toInstance(logLevel);
        bind(GamePropertiesParameter.class).toInstance(gamePropertiesParameter);

        bind(GameProperties.class).toProvider(GamePropertiesProvider.class).in(Singleton.class);
        bind(Logger.class).toProvider(LoggerProvider.class).in(Singleton.class);
    }
}
