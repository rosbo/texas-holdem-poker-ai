package edu.ntnu.texasai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.Player;

public class StatisticsController {
	
	private Map<Integer,Integer> players2wins = new HashMap<Integer,Integer>();
//	private Map<Integer,Integer> players2loses = new HashMap<Integer,Integer>();
	private Map<Integer,Integer> players2ties = new HashMap<Integer,Integer>();
	
	@Inject
	public StatisticsController(){
		this.players2wins = new HashMap<Integer,Integer>();
	}
	
	public void initializeStatistics(){
		for(int i = 0; i<=10; i++){
			this.players2wins.put(i, new Integer(0));
//			this.players2loses.put(i, new Integer(0));
			this.players2ties.put(i, new Integer(0));
		}
	}
	
	public void storeWinners(List<Player> winners){
		if(winners.size()==1){
			Player winner = winners.get(0);
			Integer numberOfWins = players2wins.get(winner.getNumber());
			players2wins.put(winner.getNumber(), new Integer(numberOfWins++));			
		}else{
			for(Player tiePlayer : winners){
				Integer numberOfWins = players2ties.get(tiePlayer.getNumber());
				players2wins.put(tiePlayer.getNumber(), new Integer(numberOfWins++));	
			}
		}
	}
	
	public Map<Integer, Integer> getPlayers2wins() {
		return players2wins;
	}

	public void setPlayers2wins(Map<Integer, Integer> players2wins) {
		this.players2wins = players2wins;
	}

	public Map<Integer, Integer> getPlayers2ties() {
		return players2ties;
	}

	public void setPlayers2ties(Map<Integer, Integer> players2ties) {
		this.players2ties = players2ties;
	}

	public void clearStatistics(){
		this.players2ties.clear();
		this.players2wins.clear();
	}
	
	public Integer getPercentageOfWins(Integer playerNumber, Integer numberOfHands){
		return this.players2wins.get(playerNumber)/numberOfHands;
	}
	
	

}
