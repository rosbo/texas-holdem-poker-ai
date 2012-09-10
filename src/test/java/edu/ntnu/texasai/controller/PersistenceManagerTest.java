package edu.ntnu.texasai.controller;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.texasai.persistence.PersistenceManager;
import edu.ntnu.texasai.preflopsim.PreFlopSimulatorModule;

public class PersistenceManagerTest {

    private PersistenceManager persistenceController;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
        this.persistenceController = injector
                .getInstance(PersistenceManager.class);
    }

    @Test
    public void testPrintAll() {
        this.persistenceController.printAll();
    }

}
