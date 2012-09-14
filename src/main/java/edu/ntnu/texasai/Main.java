package edu.ntnu.texasai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.controller.PokerController;
import edu.ntnu.texasai.dependencyinjection.TexasModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TexasModule());

        PokerController pokerController = injector.getInstance(PokerController.class);
        pokerController.play();
    }
}
