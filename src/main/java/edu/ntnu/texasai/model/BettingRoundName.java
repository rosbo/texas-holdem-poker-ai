package edu.ntnu.texasai.model;

public enum BettingRoundName {
    PRE_FLOP, POST_FLOP, POST_TURN, POST_RIVER;

    public static BettingRoundName fromRoundNumber(int bettingRoundNumber) {
        switch (bettingRoundNumber) {
        case 2:
            return POST_FLOP;
        case 3:
            return POST_TURN;
        case 4:
            return POST_RIVER;
        default:
            return PRE_FLOP;
        }
    }
}
