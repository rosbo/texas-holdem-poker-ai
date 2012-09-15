package edu.ntnu.texasai.dependencyinjection;

public enum GamePropertiesParameter {
    DEMO, PHASE1, PHASE2, PHASE3;

    public static GamePropertiesParameter fromString(String s) {
        if (s.equals("phase1")) {
            return PHASE1;
        } else if (s.equals("phase2")) {
            return PHASE2;
        } else if (s.equals("phase3")) {
            return PHASE3;
        }
        return DEMO;
    }
}
