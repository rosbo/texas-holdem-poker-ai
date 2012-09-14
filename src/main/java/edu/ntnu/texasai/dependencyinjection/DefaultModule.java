package edu.ntnu.texasai.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.ntnu.texasai.utils.ConsoleLogger;
import edu.ntnu.texasai.utils.Logger;

public class DefaultModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new TexasModule());

        bind(Logger.class).to(ConsoleLogger.class);
    }
}
