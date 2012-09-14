package edu.ntnu.texasai.persistence;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import org.junit.Before;
import org.junit.Test;

public class PreFlopPersistenceTest {
    private PreFlopPersistence preFlopPersistence;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.preFlopPersistence = injector
                .getInstance(PreFlopPersistence.class);
    }

    @Test
    public void testPrintAll() {
        preFlopPersistence.print();
    }
}
