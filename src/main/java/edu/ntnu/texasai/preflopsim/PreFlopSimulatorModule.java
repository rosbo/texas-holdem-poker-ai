package edu.ntnu.texasai.preflopsim;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import edu.ntnu.texasai.controller.ControllerModule;
import edu.ntnu.texasai.controller.PlayerControllerPreFlopRoll;
import edu.ntnu.texasai.utils.ConsoleLogger;
import edu.ntnu.texasai.utils.GameProperties;
import edu.ntnu.texasai.utils.Logger;

public class PreFlopSimulatorModule extends AbstractModule {

    @Override
    protected void configure() {
        // TODO Auto-generated method stub
        install(new ControllerModule());

        bind(GameProperties.class).in(Singleton.class);
        bind(PlayerControllerPreFlopRoll.class).in(Singleton.class);

        bind(Logger.class).to(ConsoleLogger.class);
        bind(GameHandControllerPreFlopRoll.class).in(Singleton.class);

    }

}
