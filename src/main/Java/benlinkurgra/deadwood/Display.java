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

    /**
     * display opening game message
     */
    public void startingDisplay() {
        System.out.println("""
                Welcome to Deadwood

                <SOME DESCRIPTION HERE>""");
    }

    /**
     * display end of game message
     */
    public void endGame() {
        System.out.println("Ending game, thanks for playing.");
    }

    /**
     * display help options
     */
    public void displayHelp() {
        //TODO add help display
        System.out.println("HELP HERE");
    }

    /**
     * Allows for player input
     *
     * @return player provided input
     */
    public String getUserInput() {
        Scanner inputScanner = new Scanner(System.in);
        return inputScanner.nextLine();
    }

    //region Print statements for player information
    //---------------------------------------------------------------------------------------

    /**
     * Display the names of all currently entered player names
     *
     * @param players list of players
     */
    public void sendPlayers(List<Player> players) {
        StringBuilder buildNames = new StringBuilder();
        buildNames.append("Players: ");
        for (Player playerName : players) {
            buildNames.append(playerName.getName());
            buildNames.append(" ");
        }
        System.out.println(buildNames);
    }

    /**
     * Inform players of currently active player
     *
     * @param name active players name
     */
    public void sendActivePlayer(String name) {
        System.out.printf("The current active player is %s\n", name);
    }

    /**
     * List of actions a player can perform
     */
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

    /**
     * List of actions a player can perform with highlights for invalid actions
     */
    public void sendActions(boolean canMove,
                            boolean canTakeRole,
                            boolean canAct,
                            boolean canRehearse,
                            boolean canUpgrade,
                            boolean canEndTurn) {
        String yellowText = "\u001B[33m";
        String resetTextColor = "\u001B[0m";
        String actions = "Player Actions: \n" +
                "Invalid actions highlighted in " +
                yellowText +
                "yellow" +
                resetTextColor +
                ": \n" +
                (canMove ? resetTextColor : yellowText) +
                "1. Move\n" +
                (canTakeRole ? resetTextColor : yellowText) +
                "2. Take a role\n" +
                (canAct ? resetTextColor : yellowText) +
                "3. Act\n" +
                (canRehearse ? resetTextColor : yellowText) +
                "4. Rehearse\n" +
                (canUpgrade ? resetTextColor : yellowText) +
                "5. Upgrade\n" +
                (canEndTurn ? resetTextColor : yellowText) +
                "6. End Turn" +
                resetTextColor;
        System.out.println(actions);
    }

    /**
     * Displays neighboring location for a given location
     *
     * @param locations location to find neighbors of
     */
    public void displayNeighbors(List<String> locations) {
        StringBuilder outputLocations = new StringBuilder();
        outputLocations.append("You can move to the following locations: \n");
        for (int i = 0; i < locations.size(); i++) {
            outputLocations.append(i);
            outputLocations.append(". ");
            outputLocations.append(locations.get(i));
            outputLocations.append("\n");
        }
        outputLocations.append("Please enter either the location number or name of");
        outputLocations.append(" the location you wish to move to.\n");
        System.out.println(outputLocations);
    }
    //---------------------------------------------------------------------------------------
    //endregion


    //region Print statements for error messages
    //---------------------------------------------------------------------------------------

    /**
     * Informs players entry was not a number
     *
     * @param value invalid input provided
     */
    public void displayNotANumber(String value) {
        System.out.printf("Invalid input, %s is not a number\n", value);
    }

    /**
     * Informs players that entry for number of players is invalid
     *
     * @param num invalid number entered
     */
    public void displayInvalidNumPlayers(int num) {
        System.out.printf("Invalid input, can not have %d players, please select a number from 2 up to 8\n", num);
    }

    /**
     * Informs players that name entry is invalid and must be unique
     *
     * @param players list of currently entered player names
     */
    public void sendInvalidName(List<Player> players) {
        System.out.println("Invalid entry, multiple players can not have the same name.");
        sendPlayers(players);
        System.out.println("Please enter a new unique name.");
    }

    public void sendInvalidActionSelection(int num) {
        System.out.printf("Invalid input, %d is not a valid selection for an action. " +
                "Please enter a number from 1 up to 6.\n", num);
    }

    /**
     * Informs players that action is invalid due to provided reason.
     * <p>
     * FORMAT: "{playerName} can not {action}, {reason}."
     * </p>
     *
     * @param playerName name of player attempting action
     * @param action action attempted
     * @param reason reason can not perform action
     */
    public void displayCanNotPerformAction(String playerName, String action, String reason) {
        System.out.printf("%s can not %s, %s.\n", playerName, action, reason);
    }

    //---------------------------------------------------------------------------------------
    //endregion


    //region Print statements to be attached to user prompts
    //--------------------------------------------------------------------------------------

    /**
     * Used to ask for number of players
     */
    public void sendPromptNumPlayers() {
        System.out.print("Enter number of players: ");
    }

    /**
     * Used to ask for a player name
     */
    public void sendPromptName() {
        System.out.print("Enter player name: ");
    }

    /**
     * Used to ask player to enter a new location
     */
    public void sendPromptEnterLocation() {
        System.out.print("Select new location: ");
    }

    public void sendPromptSelectAction() {
        System.out.println("To select an action, enter the number corresponding to the action.");
        System.out.print("Enter action: ");
    }
    //---------------------------------------------------------------------------------------
    //endregion

}
