package edu.ntnu.texasai.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.cards.EquivalenceClassSuited;
import edu.ntnu.texasai.preflopsim.PreFlopSimulatorModule;

public class EquivalenceClassControllerTest {

	private EquivalenceClassController equivalenceClassController;

	@Before
	public void setUp() throws Exception {
		Injector injector = Guice.createInjector(new PreFlopSimulatorModule());
		this.equivalenceClassController = injector
				.getInstance(EquivalenceClassController.class);

	}

	@Test
	public void testEquivalence2Cards() {
		// fail("Not yet implemented");

	}

	@Test
	public void testGenerateAllEquivalenceClass() {
		this.equivalenceClassController.generateAllEquivalenceClass();

		for (EquivalenceClass eq : this.equivalenceClassController.getCard2())
			System.out.println(eq.toString());
		//
		assertEquals(169, this.equivalenceClassController
				.getEquivalenceClasses().size());
		// assertEquals(169,this.equivalenceClassController.getCard2().size());
	}

	@Test
	public void testEquivalenceClasses() {
		EquivalenceClass e1 = new EquivalenceClassSuited(CardNumber.ACE,
				CardNumber.EIGHT);
		EquivalenceClass e2 = new EquivalenceClassSuited(CardNumber.EIGHT,
				CardNumber.ACE);
		EquivalenceClass e3 = new EquivalenceClassSuited(CardNumber.ACE,
				CardNumber.EIGHT);
		assertEquals(e1, e3);
//		assertEquals(e2, e3);
	}
}
