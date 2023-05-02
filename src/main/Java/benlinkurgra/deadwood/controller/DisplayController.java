package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;

import java.util.function.Function;

public abstract class DisplayController {
    protected final Display display;

    public DisplayController(Display display) {
        this.display = display;
    }
    protected boolean checkForRequest(String input) {
        switch (input) {
            case "quit" -> {
                display.endGame();
                System.exit(0); // placeholder, I think this should be moved
            }
            case "help" -> {
                display.displayHelp();
                return true;
            }
            case "actions" -> {
                display.sendActions();
                return true;
            }
        }
        return false;
    }

    protected String handleInput(Runnable userPrompt) {
        userPrompt.run();
        String input = display.getUserInput();
        boolean processing = checkForRequest(input);
        while (processing) {
            userPrompt.run();
            input = display.getUserInput();
            processing = checkForRequest(input);
        }
        return input;
    }
}
