package edu.ntnu.texasai.controller;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.texasai.persistence.PersistenceController;
import edu.ntnu.texasai.preflopsim.PreFlopSimulatorModule;

public class PersistenceControllerTest {

    private PersistenceController persistenceController;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
        this.persistenceController = injector
                .getInstance(PersistenceController.class);
    }

    @Test
    public void testPrintAll() {
        this.persistenceController.printAll();
    }

}
