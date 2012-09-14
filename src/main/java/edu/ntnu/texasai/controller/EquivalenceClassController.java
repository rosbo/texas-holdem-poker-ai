package edu.ntnu.texasai.controller;

import com.google.inject.Inject;
import edu.ntnu.texasai.model.cards.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EquivalenceClassController {
	private final Collection<EquivalenceClass> equivalenceClasses;

	@Inject
	public EquivalenceClassController() {
	    equivalenceClasses = new ArrayList<EquivalenceClass>();
	}
	
	/**
	 * Converts two cards into their corrispondent equivalence class
	 * */
	public EquivalenceClass cards2Equivalence(Card card1, Card card2) {
		EquivalenceClass equivalenceClass;
		if (card1.getSuit().equals(card2.getSuit())) { // suited
			    equivalenceClass = new EquivalenceClassSuited(card1.getNumber(),card2.getNumber());
			} else {// unsuited
			    equivalenceClass = new EquivalenceClassUnsuited(card1.getNumber(),card2.getNumber());
			}
		return equivalenceClass;
	}

	public Collection<EquivalenceClass> getEquivalenceClasses() {
		return equivalenceClasses;
	}
	
	public void generateAllEquivalenceClass()  {
		EquivalenceClass equivalenceClass;
		List<CardNumber> allCardNumbers = new ArrayList<CardNumber>();
		
		//generateThePairs
		for (CardNumber number : CardNumber.values()) {
			equivalenceClass = new EquivalenceClassUnsuited(number,number);	
			equivalenceClasses.add(equivalenceClass);
			allCardNumbers.add(number);
		}
		
		//generate other equivalences 		
		for(int i = 0; i < allCardNumbers.size(); i++){
			for(int j = i+1; j < allCardNumbers.size();j++){				
				equivalenceClass = new EquivalenceClassUnsuited(allCardNumbers.get(i),allCardNumbers.get(j));
				equivalenceClasses.add(equivalenceClass);				
				equivalenceClass = new EquivalenceClassSuited(allCardNumbers.get(i),allCardNumbers.get(j));
				equivalenceClasses.add(equivalenceClass);				
			}			
		}		
	}
}
