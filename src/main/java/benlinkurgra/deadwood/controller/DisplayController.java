package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;

import java.util.function.Function;

public abstract class DisplayController {
    protected final Display display;

    public DisplayController(Display display) {
        this.display = display;
    }

    /**
     * checks if input was a request and performs request action
     *
     * @param input user input
     * @return if was request true, otherwise false
     */
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
            case "map" -> {
                display.displayMap();
                return true;
            }
        }
        return false;
    }

    /**
     * handles user input, continues to prompt user until input is not a request
     *
     * @param userPrompt prompt to display to user
     * @return input given by user
     */
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
