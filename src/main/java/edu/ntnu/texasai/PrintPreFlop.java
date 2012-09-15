package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.GamePropertiesParameter;
import edu.ntnu.texasai.dependencyinjection.LogLevel;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.persistence.PreFlopPersistence;

public class PrintPreFlop {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TexasModule(LogLevel.ALL, GamePropertiesParameter.DEMO));

        PreFlopPersistence preFlopPersistence = injector.getInstance(PreFlopPersistence.class);
        preFlopPersistence.print();
    }
}
