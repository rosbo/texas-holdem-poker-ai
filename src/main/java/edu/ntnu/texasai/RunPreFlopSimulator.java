package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.controller.preflopsim.PreFlopSimulatorController;
import edu.ntnu.texasai.dependencyinjection.ComputingModule;

public class RunPreFlopSimulator {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ComputingModule());

        PreFlopSimulatorController preFlopSimulatorController = injector
                .getInstance(PreFlopSimulatorController.class);
        preFlopSimulatorController.play();
    }
}
