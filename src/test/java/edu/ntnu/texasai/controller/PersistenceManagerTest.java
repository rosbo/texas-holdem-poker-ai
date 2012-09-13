package edu.ntnu.texasai.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.persistence.PersistenceManager;
import org.junit.Before;
import org.junit.Test;

public class PersistenceManagerTest {

    private PersistenceManager persistenceController;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.persistenceController = injector
                .getInstance(PersistenceManager.class);
    }

    @Test
    public void testPrintAll() {
        this.persistenceController.printPreflopEquivalencesProbability();
    }

}
