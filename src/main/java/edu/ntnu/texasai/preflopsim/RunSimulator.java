package edu.ntnu.texasai.preflopsim;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class RunSimulator {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
		PreFlopSimController  preFlopSimController  = injector.getInstance(PreFlopSimController.class);
		preFlopSimController.play();
	}
}
