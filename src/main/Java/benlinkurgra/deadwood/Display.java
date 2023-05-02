package benlinkurgra.deadwood;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Display {
    /*
     * Mockup of game
     * <game is started>
     * console: Welcome to Deadwood
     * console: Please enter the number of users
     * user: <playerNum>
     * (loop)
     * console: enter name of nth player
     * user: playerName
     * (end)
     * console: <playerName> is active player
     * console: available moves are as follows <1. <action 1> \n <2. <action 2>>...
     * user: <input number of name of move>... assume selection is move
     * console: <you have selected move>
     * console: available locations are <list locations>
     * user: selects location
     * console list available moves... assume end turn
     * console: <playerName> has completed turn
     * (repeat from) console: <playerName> is active player
     * ...
     */

    public void startingDisplay() {
        System.out.println("""
                Welcome to Deadwood

                <SOME DESCRIPTION HERE>""");
    }

    public int promptNumPlayers() {
        int numPlayers = 0;
        Scanner playerNumScanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        try {
            numPlayers = playerNumScanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e);
            return promptNumPlayers();
        }
        if (numPlayers < 2 || numPlayers > 8) {
            System.out.printf("Invalid entry, %d is not valid, please enter a number from 2 to 8\n", numPlayers);
            return promptNumPlayers();
        } else {
            return numPlayers;
        }
    }

    public String promptName() {
        Scanner nameScanner = new Scanner(System.in);
        System.out.print("Enter player name: ");
        String name = nameScanner.next();
        return name;
    }

    public void sendPlayers(List<Player> players) {
        StringBuilder buildNames = new StringBuilder();
        buildNames.append("Players: ");
        for (Player playerName : players) {
            buildNames.append(playerName.getName());
            buildNames.append(" ");
        }
        System.out.println(buildNames);
    }

    public String sendInvalidName(List<Player> players) {
        System.out.println("""
                Invalid entry, multiple players can not have the same name.
                
                Please enter a new name.
                """);
        sendPlayers(players);
        return promptName();
    }
}
