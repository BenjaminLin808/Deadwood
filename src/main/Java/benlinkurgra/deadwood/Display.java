package benlinkurgra.deadwood;

import benlinkurgra.deadwood.model.Player;

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

    public void endGame() {
        System.out.println("Ending game, thanks for playing.");
    }

    public void displayHelp() {
        //TODO add help display
        System.out.println("HELP HERE");
    }

    public String getUserInput() {
        Scanner inputScanner = new Scanner(System.in);
        return inputScanner.nextLine();
    }

    public void sendPromptNumPlayers() {
        System.out.print("Enter number of players: ");
    }

    public void displayNotANumber(String value) {
        System.out.printf("Invalid input, %s is not a number\n", value);
    }

    public void displayInvalidNumPlayers(int num) {
        System.out.printf("Invalid input, can not have %d players, please select a number from 2 up to 8\n", num);
    }

    public void sendPromptName() {
        System.out.print("Enter player name: ");
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

    public void sendInvalidName(List<Player> players) {
        System.out.println("Invalid entry, multiple players can not have the same name.");
        sendPlayers(players);
        System.out.println("Please enter a new unique name.");
    }

    public void sendActivePlayer(String name) {
        System.out.printf("The current active player is %s\n", name);
    }

    public void sendActions() {
        //TODO maybe add in a prompt to type help for more information
        System.out.println("""
                1. Move
                2. Take a role
                3. Act
                4. Rehearse
                5. Upgrade
                6. End Turn""");
    }
}
