package edu.ntnu.texasai.dependencyinjection;

import edu.ntnu.texasai.utils.ConsoleLogger;
import edu.ntnu.texasai.utils.ImportantLogger;
import edu.ntnu.texasai.utils.Logger;

import javax.inject.Inject;
import javax.inject.Provider;

public class LoggerProvider implements Provider<Logger> {
    private final LogLevel logLevel;

    @Inject
    public LoggerProvider(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public Logger get() {
        switch (logLevel){
            case IMPORTANT:
                return new ImportantLogger();
            default:
                return new ConsoleLogger();
        }
    }
}
