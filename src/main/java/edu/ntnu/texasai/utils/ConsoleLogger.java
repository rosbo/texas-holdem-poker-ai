package edu.ntnu.texasai.utils;

public class ConsoleLogger implements Logger {
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void logImportant(String message) {
        System.out.println(message);
    }
}
