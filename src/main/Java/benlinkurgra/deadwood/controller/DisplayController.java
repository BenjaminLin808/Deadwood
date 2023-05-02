package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;

public abstract class DisplayController {
    protected final Display display;

    public DisplayController(Display display) {
        this.display = display;
    }
    protected void checkForRequest(String input) {
        switch (input) {
            case "quit" -> {
                display.endGame();
                System.exit(0); // placeholder, I think this should be moved
            }
            case "help" -> display.displayHelp();
            case "actions" -> display.sendActions();
        }
    }
}
