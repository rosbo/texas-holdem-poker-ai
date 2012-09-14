package edu.ntnu.texasai.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import org.junit.Before;
import org.junit.Test;

public class OpponentsModelPersistenceTest {
    private OpponentsModelPersistence opponentsModelPersistence;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.opponentsModelPersistence = injector
                .getInstance(OpponentsModelPersistence.class);
    }

    @Test
    public void testPrintAll() {
        opponentsModelPersistence.print();
    }
}
