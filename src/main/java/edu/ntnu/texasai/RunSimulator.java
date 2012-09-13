package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.controller.StatisticsController;
import edu.ntnu.texasai.controller.preflopsim.PreFlopSimulatorController;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.persistence.PersistenceManager;

public class RunSimulator {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TexasModule());

        StatisticsController statisticsController = injector
                .getInstance(StatisticsController.class);
        statisticsController.initializeStatistics();

        PersistenceManager persistenceManager = injector
                .getInstance(PersistenceManager.class);
        persistenceManager.createEquivalencesTable();

        PreFlopSimulatorController preFlopSimulatorController = injector
                .getInstance(PreFlopSimulatorController.class);
        preFlopSimulatorController.play();
    }
}
