package edu.ntnu.texasai.controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.texasai.dependencyinjection.TexasModule;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.cards.EquivalenceClassSuited;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EquivalenceClassControllerTest {

    private EquivalenceClassController equivalenceClassController;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new TexasModule());
        this.equivalenceClassController = injector.getInstance(EquivalenceClassController.class);
    }

    @Test
    public void testGenerateAllEquivalenceClass() {
        this.equivalenceClassController.generateAllEquivalenceClass();
        for (EquivalenceClass eq : this.equivalenceClassController
                .getEquivalenceClasses())
            System.out.println(eq.toString());
        assertEquals(169, this.equivalenceClassController.getEquivalenceClasses().size());

    }

    @Test
    public void testEquivalenceClasses() {
        EquivalenceClass e1 = new EquivalenceClassSuited(CardNumber.ACE,CardNumber.EIGHT);
        EquivalenceClass e2 = new EquivalenceClassSuited(CardNumber.ACE,CardNumber.EIGHT);
        assertEquals(e1, e2);
    }
}
