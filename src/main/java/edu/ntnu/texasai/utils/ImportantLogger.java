package edu.ntnu.texasai.utils;

public class ImportantLogger implements Logger {
    public void log(String message) {
        // Don't print not important information
    }

    @Override
    public void logImportant(String message) {
        System.out.println(message);
    }
}
