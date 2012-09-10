package edu.ntnu.texasai.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.EquivalenceClass;
import edu.ntnu.texasai.model.cards.EquivalenceClassSuited;
import edu.ntnu.texasai.model.cards.EquivalenceClassUnsuited;

public class EquivalenceClassController {

	private Collection<EquivalenceClass> equivalenceClasses; 
	private List<Card> cards1;
	private List<EquivalenceClass>cards2;
	
	@Inject
	public EquivalenceClassController() {
	    this.equivalenceClasses = new ArrayList<EquivalenceClass>();
		this.cards1 = new ArrayList<Card>();
		this.cards2 = new ArrayList<EquivalenceClass>();
	}
	
	/**
	 * Converts two cards into their corrispondent equivalence class
	 * */
	public EquivalenceClass cards2Equivalence(Card card1, Card card2) {
		EquivalenceClass equivalenceClass = null;
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

	public List<Card> getCards1() {
		return cards1;
	}
	
	public List<EquivalenceClass> getCard2(){
		return this.cards2;
	}
	
	public void generateAllEquivalenceClass()  {
		EquivalenceClass equivalenceClass = null;
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
