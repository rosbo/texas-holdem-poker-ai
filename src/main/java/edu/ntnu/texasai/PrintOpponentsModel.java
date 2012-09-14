package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.persistence.OpponentsModelPersistence;

public class PrintOpponentsModel {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TexasModule());

        OpponentsModelPersistence opponentsModelPersistence = injector.getInstance(OpponentsModelPersistence.class);
        opponentsModelPersistence.print();
    }
}
