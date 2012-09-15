package edu.ntnu.texasai.model.opponentmodeling;

public enum ContextRaises {
    FEW,
    MANY;

    public static ContextRaises valueFor(int numberOfRaises) {
        if(numberOfRaises < 3){
            return FEW;
        }else{
            return MANY;
        }
    }
}
