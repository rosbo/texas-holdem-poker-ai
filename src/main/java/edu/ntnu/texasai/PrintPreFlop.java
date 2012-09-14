package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.DefaultModule;
import edu.ntnu.texasai.persistence.PreFlopPersistence;

public class PrintPreFlop {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DefaultModule());

        PreFlopPersistence preFlopPersistence = injector.getInstance(PreFlopPersistence.class);
        preFlopPersistence.print();
    }
}
