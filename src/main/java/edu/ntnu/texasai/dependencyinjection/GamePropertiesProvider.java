package edu.ntnu.texasai.dependencyinjection;

import edu.ntnu.texasai.model.gameproperties.*;

import javax.inject.Inject;
import javax.inject.Provider;

public class GamePropertiesProvider implements Provider<GameProperties> {
    private final GamePropertiesParameter gamePropertiesParameter;
    private final DemoGameProperties demoGameProperties;
    private final PhaseIGameProperties phaseIGameProperties;
    private final PhaseIIGameProperties phaseIIGameProperties;
    private final PhaseIIIGameProperties phaseIIIGameProperties;

    @Inject
    public GamePropertiesProvider(final GamePropertiesParameter gamePropertiesParameter, final DemoGameProperties demoGameProperties,
                                  final PhaseIGameProperties phaseIGameProperties,
                                  final PhaseIIGameProperties phaseIIGameProperties,
                                  final PhaseIIIGameProperties phaseIIIGameProperties) {
        this.gamePropertiesParameter = gamePropertiesParameter;
        this.demoGameProperties = demoGameProperties;
        this.phaseIGameProperties = phaseIGameProperties;
        this.phaseIIGameProperties = phaseIIGameProperties;
        this.phaseIIIGameProperties = phaseIIIGameProperties;
    }

    @Override
    public GameProperties get() {
        switch (gamePropertiesParameter){
            case PHASE1:
                return phaseIGameProperties;
            case PHASE2:
                return phaseIIGameProperties;
            case PHASE3:
                return phaseIIIGameProperties;
            default:
                return demoGameProperties;
        }
    }
}
