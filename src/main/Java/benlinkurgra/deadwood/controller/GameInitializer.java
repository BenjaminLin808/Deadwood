package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.Player;
import benlinkurgra.deadwood.location.Location;

import java.util.*;

public class GameInitializer extends DisplayController {

    public GameInitializer(Display display) {
        super(display);
    }

    /**
     * Sends signal to UI to display opening information
     */
    public void startGame() {
        display.startingDisplay();
    }

    /**
     * Signals UI to request number of players.
     *
     * @return number of players, when entry is valid.
     */
    public int getNumberPlayers() {
        display.sendPromptNumPlayers();
        String input = display.getUserInput();
        checkForRequest(input);
        //TODO need to take action if was request
        try {
            int num = Integer.parseInt(input);
            if (num < 2 || num > 8) {
                display.displayInvalidNumPlayers(num);
                return getNumberPlayers();
            } else {
                return num;
            }
        } catch (NumberFormatException e) {
            display.displayNotANumber(input);
            return getNumberPlayers();
        }
    }

    /**
     * Signals display to acquire all player Names. Then determines player order
     *
     * @param numPlayers number of players in game
     * @return a Queue containing the player order
     */
    public Queue<Player> determinePlayerOrder(int numPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            display.sendPromptName();
            String playerName = display.getUserInput();
            //TODO need to take action if was request
            while (containsName(players, playerName)) {
                display.sendInvalidName(players);
                display.sendPromptName();
                playerName = display.getUserInput();
                //TODO need to take action if was request
            }
            players.add(createPlayer(numPlayers, playerName));
        }
        Collections.shuffle(players);
        return new LinkedList<>(players);
    }

    /**
     * checks that a player name is unique
     *
     * @param players list of entered player names
     * @param name name of new player to be added to player list
     * @return true if player name is already in list, otherwise false
     */
    private boolean containsName(final List<Player> players, final String name) {
        return players.stream().anyMatch(player -> name.equals(player.getName()));
    }

    /**
     * creates a new player based on numPlayers
     *
     * @param numPlayers number of players in game
     * @param playerName name of new Player
     * @return a new Player object
     */
    private Player createPlayer(int numPlayers, String playerName) {
        if (numPlayers < 5) {
            return new Player(playerName);
        } else if (numPlayers == 5) {
            return new Player(playerName, 2, 1);
        } else if (numPlayers == 6) {
            return new Player(playerName, 4, 0);
        } else { // numPlayers == 7 || numPlayers == 8
             return new Player(playerName, 0, 2);
        }
    }

    public void configureBoard(Map<Location, List<Location>> layout) {
        //TODO this will either need to be dropped or ignored considering we only have default XML
        System.out.println("based on the map from Board class, we will set up the board");
    }
    public void determineEndDay(int numPlayers) {
        System.out.println("determine the game time based on the player number");
    }
    public void determineSceneOrder() {
        System.out.println("determine what order the scenes would be placed");
    }

}
