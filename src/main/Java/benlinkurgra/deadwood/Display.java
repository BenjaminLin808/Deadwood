package benlinkurgra.deadwood;

import java.util.InputMismatchException;
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

    public int startingDisplay() {
        System.out.println("""
                Welcome to Deadwood

                <SOME DESCRIPTION HERE>

                To start please indicate how many players you have.""");
        return promptNumPlayers();
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
}
