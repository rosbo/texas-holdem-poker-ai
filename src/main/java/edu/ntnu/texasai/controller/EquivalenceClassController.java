package edu.ntnu.texasai.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.cards.Card;
import edu.ntnu.texasai.model.cards.CardNumber;
import edu.ntnu.texasai.model.cards.CardSuit;
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
			equivalenceClass = new EquivalenceClassSuited(card1.getNumber(),
					card2.getNumber());
			System.out.println("Suited");
		} else {// unsuited
			equivalenceClass = new EquivalenceClassUnsuited(card1.getNumber(),
					card2.getNumber());
			System.out.println("Unsuited");
		}
		return equivalenceClass;
	}

//	public List<Card> equivalence2Cards(EquivalenceClass equivalenceClass) {
//		// TODO: from equivalence 2 cards
//		return equivalenceClass.equivalence2cards();
//	}

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
	
//	public boolean containsEquivalenceClass(EquivalenceClass equivalenceClass, Collection<EquivalenceClass> list){
//		for (EquivalenceClass eq : list){
//			if(areTheSame(equivalenceClass,eq)){
//				return true;
//			}			
//		}
//		return false;
//	}
//	
//	public boolean areTheSame(EquivalenceClass eq1, EquivalenceClass eq2){
//		if(eq1 == null || eq2 == null)
//			return false;
//		boolean left,right,middle;
//		left = eq1.equals(eq2);
//		middle = eq1.getNumber1().getPower().equals(eq2.getNumber2()) && eq1.getNumber2().getPower().equals(eq2.getNumber1());
////		right =  eq1.getNumber1().getPower().equals(eq2.getNumber2())).equals(eq1.getNumber2());
//		return left || middle;
//	}
}
