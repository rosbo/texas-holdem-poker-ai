package edu.ntnu.texasai.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.ntnu.texasai.utils.Logger;
import edu.ntnu.texasai.utils.NopLogger;

public class ComputingModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new TexasModule());

        bind(Logger.class).to(NopLogger.class);
    }
}
