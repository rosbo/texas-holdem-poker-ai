package edu.ntnu.texasai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import edu.ntnu.texasai.model.Player;

public class StatisticsController {

	private Map<Integer, Integer> players2wins;
	// private Map<Integer,Integer> players2loses = new
	// HashMap<Integer,Integer>();
	private Map<Integer, Integer> players2ties = new HashMap<Integer, Integer>();
	private Integer player0winnings;
	private Integer currentNumberOfPlayers;

	@Inject
	public StatisticsController() {
		this.players2wins = new HashMap<Integer, Integer>();
		player0winnings = new Integer(0);
		this.currentNumberOfPlayers = new Integer(0);

	}

	public void initializeStatistics() {
		for (int i = 0; i <= 10; i++) {
			this.players2wins.put(i, 0);
			// this.players2loses.put(i, new Integer(0));
			this.players2ties.put(i, 0);
		}
		player0winnings = new Integer(0);

	}

	public void storeWinners(List<Player> winners) {
		// if(winners.size()==1){
		// System.out.println("WINNER "+winners.get(0).getNumber());
		// Player winner = winners.get(0);
		// System.out.println(winner.toString());
		// int numberOfWins = players2wins.get(winner.getNumber());
		// this.players2wins.put(winner.getNumber(), new
		// Integer(numberOfWins++));
		// System.out.println("NUmber of wins " + numberOfWins);
		// }else{
		// for(Player tiePlayer : winners){
		// TODO: tiePlayers if really needed
		// int numberOfTies = players2ties.get(tiePlayer.getNumber());
		// players2ties.put(tiePlayer.getNumber(), numberOfTies++);
		// }
		// }
		// I don't know why storing the wins of all players was giving me
		// problems
		// At the moment we need only player0 wins in order to store it into the
		// table
		// I'll fix it later if we need it
		for (Player winner : winners) {
			if (winner.getNumber() == 0) {
				player0winnings++;
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

	public void clearStatistics() {
		this.players2ties.clear();
		this.players2wins.clear();

	}

	public Double getPercentageOfWinsPlayer0(Integer numberOfHands) {
		// return this.players2wins.get(0)/numberOfHands;
		Double d = new Double(10);

		return new Double(player0winnings / new Double(numberOfHands));

	}

	public Integer getPlayer0Wins() {
		// TODO Auto-generated method stub
		return player0winnings;
	}

//	public Integer getCurrentNumberOfPlayers() {
//		return this.currentNumberOfPlayers;
//	}
//
//	public void setCurrentNumberOfPlayers(Integer numberOfPlayers) {
//		this.currentNumberOfPlayers = numberOfPlayers;
//	}

}
