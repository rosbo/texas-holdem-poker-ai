package edu.ntnu.texasai.preflopsim;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.persistence.PersistenceManager;

public class RunSimulator {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
        StatisticsController statisticsController = injector
                .getInstance(StatisticsController.class);
        statisticsController.initializeStatistics();

        PersistenceManager persistenceController = injector
                .getInstance(PersistenceManager.class);
        persistenceController.createTable();

        PreFlopSimController preFlopSimController = injector
                .getInstance(PreFlopSimController.class);
        preFlopSimController.play();
    }
}
