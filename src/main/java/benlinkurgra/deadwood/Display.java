package benlinkurgra.deadwood;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Display {
    /**
     * display opening game message
     */
    public void startingDisplay() {
        System.out.println("""
                Welcome to Deadwood

                Deadwood is a fast-paced board game about actors, acting, and
                the thrill-filled life of a wandering bit player. It’s perfect for 2 to
                6 players, still decent with 7 or 8. Play time is about 60 minutes.
                
                If in need of help enter \"help\" at any time.""");
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
        System.out.println("""
                Available commands:
                help             display this help
                actions          display list of actions
                map              display game map
                quit             immediately ends game
                player info      display information of current player
                location info    display information for current players location
                cancel           used when performing an action will cancel action
                """);
    }

    /**
     * display game map
     */
    public void displayMap() {
        String map = """
            +----------------------------+ +----------------------------+
            | Train Station ═════ Jail ═══════════════════ Main Street  |
            |   ║  ║                ║    | |                ║     ║     |
            |   ║  ║                ║    | |        ╔═══════╝     ║     |
            |   ║  ║                ║    | |        ║             ║     |
            |   ║  ║                ║    | |        ║             ║     |
            |   ║  ╚══════ General Store ═══════ Saloon ══════ Trailers |
            +---║-------------------║----+ +--------║-------------║-----+
            +---║-------------------║----+ +--------║-------------║-----+
            | Casting Office ═════ Ranch ════════ Bank ════╗      ║     |
            |   ║                   ║    | |        ║      ║      ║     |
            |   ║                   ║    | |        ║      ╚════ Hotel  |
            |   ║                   ║    | |        ║             ║     |
            |   ║                   ║    | |        ║             ║     |
            |   ╚════════ Secret Hideout ════════ Church ═════════╝     |
            +----------------------------+ +----------------------------+
            """;
        System.out.println(map);
    }

    /**
     * displays player information
     *
     * @param player player to display
     */
    public void playerInfo(String player) {
        System.out.println(player);
    }

    /**
     * displays location information
     *
     * @param location location to display
     */
    public void locationInfo(String location) {
        System.out.println(location);
    }

    /**
     * Allows for player input
     *
     * @return player provided input
     */
    public String getUserInput() {
        Scanner inputScanner = new Scanner(System.in);
        String input = inputScanner.nextLine();
        while (input.isEmpty()) {
            input = inputScanner.nextLine();
        }
        return input;
    }

    /**
     * colorize a player name green
     *
     * @param playerName name to colorize
     * @return colorized name
     */
    private String colorizeName(String playerName) {
        String greenText = "\u001B[32m";
        String resetText = "\u001B[0m";
        return greenText + playerName + resetText;
    }

    //region Print statements for player information
    //---------------------------------------------------------------------------------------

    /**
     * Display the names of all currently entered player names
     *
     * @param playerNames list of player names
     */
    public void sendPlayers(List<String> playerNames) {
        StringBuilder buildNames = new StringBuilder();
        buildNames.append("Players: ");
        for (int i = 0; i < playerNames.size(); i++) {
            buildNames.append(colorizeName(playerNames.get(i)));
            if (i < playerNames.size() - 1) {
                buildNames.append(", ");
            }
        }
        System.out.println(buildNames);
    }

    /**
     * Inform players of currently active player
     *
     * @param name active players name
     */
    public void sendActivePlayer(String name) {
        System.out.printf("The current active player is %s\n", colorizeName(name));
    }

    /**
     * List of actions a player can perform
     */
    public void sendActions() {
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

    public void displayValidUpgrades(String castingOffice) {
        System.out.println(castingOffice);
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
            outputLocations.append(i + 1);
            outputLocations.append(". ");
            outputLocations.append(locations.get(i));
            outputLocations.append("\n");
        }
        outputLocations.append("Please enter either the location number or name of");
        outputLocations.append(" the location you wish to move to.");
        System.out.println(outputLocations);
    }

    /**
     * displays roles at location
     *
     * @param locationString location with roles
     */
    public void rolesAtLocation(String locationString) {
        System.out.print(locationString);
    }

    /**
     * displays a move was successful
     *
     * @param playerName name of player who moved
     * @param oldLocation previous location of player
     * @param newLocation new location of player
     */
    public void moveSuccess(String playerName, String oldLocation, String newLocation) {
        System.out.printf("Move successful, %s has moved from %s to %s\n",
                colorizeName(playerName),
                oldLocation,
                newLocation);
    }

    public void actOutcome(String message) {
        System.out.println(message);
    }

    /**
     * displays that an upgrade was successful
     *
     * @param playerName name of player who upgraded
     * @param rank new rank of player
     */
    public void upgradeSuccess(String playerName, int rank) {
        System.out.printf("Upgrade successful, %s has upgraded to rank %d\n",
                colorizeName(playerName),
                rank);
    }

    /**
     * displays rehearse was successful
     *
     * @param playerName player who rehearsed
     * @param practiceTokens current practice tokens of player
     */
    public void rehearseSuccess(String playerName, int practiceTokens) {
        System.out.printf("Rehearse successful, %s has %d practice tokens\n",
                colorizeName(playerName),
                practiceTokens);
    }

    /**
     * displays rehearse action failed
     *
     * @param playerName player who attempted to rehearse
     * @param practiceTokens players practice tokens
     */
    public void rehearseFail(String playerName, int practiceTokens) {
        System.out.printf("Rehearse failed, %s has %d \n", colorizeName(playerName), practiceTokens);
    }

    /**
     * displays dice outcome from acting
     *
     * @param playerName name of player who acted
     * @param outcome dice result
     * @param practiceTokens players practice tokens
     * @param budget scene budget
     */
    public void diceOutcome(String playerName, int outcome, int practiceTokens, int budget) {
        System.out.printf("Player %s rolled %d.\n" +
                "They have %d practice token(s) and the scene budget is %d.\n",
                colorizeName(playerName),
                outcome,
                practiceTokens,
                budget);
    }

    /**
     * display scene has finished
     *
     * @param locationName name of location where scene finished
     * @param sceneName name of scene that finished
     */
    public void sceneFinished(String locationName, String sceneName) {
        System.out.printf("Scene %s has finished at location %s.\n", locationName, sceneName);
    }

    /**
     * displays bonus payout
     *
     * @param earnings map where keys are player names and values are bonus earned
     */
    public void earnBonus(Map<String, Integer> earnings) {
        for (Map.Entry<String, Integer> entry : earnings.entrySet()) {
            String playerName = entry.getKey();
            int earningsValue = entry.getValue();
            System.out.printf("Player %s has earned a %d dollar bonus.%n", playerName, earningsValue);
        }
    }

    /**
     * announce no bonus payout
     */
    public void noBonus() {
        System.out.println("No players working on scene, no bonus payout earned.");
    }

    /**
     * announces a player toke a role
     *
     * @param playerName name of player who toke role
     * @param locationName location role was taken at
     * @param roleName name of role taken
     */
    public void roleTaken(String playerName, String locationName, String roleName) {
        System.out.printf("Player %s, at Location %s, has taken the role: %s.\n",
                colorizeName(playerName),
                locationName, roleName);
    }

    /**
     * announce a players turn is over
     *
     * @param playerName name of player
     */
    public void playerDone(String playerName) {
        System.out.printf("Ending player %s's turn.\n", colorizeName(playerName));
    }

    /**
     * announce day has ended
     *
     * @param currDay day that is ending
     * @param lastDay total days in game
     */
    public void endDay(int currDay, int lastDay) {
        System.out.printf("Day %d of %d is ending.\n", currDay, lastDay);
    }

    /**
     * announce a players score
     *
     * @param playerName player name
     * @param score player score
     */
    public void score(String playerName, int score) {
        System.out.printf("Player %s scored %d.\n", colorizeName(playerName), score);
    }

    /**
     * announce a winner
     *
     * @param playerName name of game winner
     * @param score winning score
     */
    public void winners(String playerName, int score) {
        System.out.printf("Player %s has won the game with a score of %d.",
                colorizeName(playerName),
                score);
    }

    /**
     * announce winners, for use in a tie
     *
     * @param winners list of winners names
     * @param score winning score
     */
    public void winners(List<String> winners, int score) {
        StringBuilder sb = new StringBuilder();
        sb.append("Game is a tie. Winners are ");
        for (int i = 0; i < winners.size(); i++) {
            sb.append(colorizeName(winners.get(i)));
            if (i < winners.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" with a score of ");
        sb.append(score);
        System.out.println(sb);
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
    public void notANumber(String value) {
        System.out.printf("Invalid input, %s is not a number\n", value);
    }

    /**
     * Informs players that entry for number of players is invalid
     *
     * @param num invalid number entered
     */
    public void displayInvalidNumPlayers(int num) {
        System.out.printf("Invalid input, can not have %d players, please select a number from 2 to 8\n", num);
    }

    /**
     * Informs players that name entry is invalid and must be unique
     *
     * @param playerNames list of currently entered player names
     */
    public void sendInvalidName(List<String> playerNames) {
        System.out.println("Invalid entry, multiple players can not have the same name.");
        sendPlayers(playerNames);
        System.out.println("Please enter a new unique name.");
    }

    public void sendInvalidActionSelection(int num) {
        System.out.printf("Invalid input, %d is not a valid selection for an action. " +
                "Please enter a number from 1 to 6.\n", num);
    }

    /**
     * Informs players that action is invalid due to provided reason.
     * <p>
     * FORMAT: "{playerName} can not {action}, {reason}."
     * </p>
     *
     * @param playerName name of player attempting action
     * @param action     action attempted
     * @param reason     reason can not perform action
     */
    public void displayCanNotPerformAction(String playerName, String action, String reason) {
        System.out.printf("%s can not %s, %s.\n", colorizeName(playerName), action, reason);
    }

    /**
     * inform players of invalid entry for a move
     *
     * @param input provided invalid input
     */
    public void displayInvalidMoveLocation(String input) {
        System.out.printf("Invalid input, %s is not a valid selection for a move.\n", input);
    }

    /**
     * inform players of invalid selection for new rank
     *
     * @param input provided invalid input
     */
    public void displayInvalidRankSelection(String input) {
        System.out.printf("Invalid input, %s is not a valid rank number, please select a number from 2 to 6.\n", input);
    }

    /**
     * inform players of invalid selection for a role type
     *
     * @param input provided invalid input
     */
    public void invalidRoleType(String input) {
        System.out.printf("Invalid input, %s is not valid role type.\n", input);
    }

    /**
     * informs players of an invalid selection for a currency type
     *
     * @param input provided invalid input
     */
    public void invalidCurrency(String input) {
        System.out.printf("Invalid input, %s is not a valid currency.\n", input);
    }

    /**
     * informs players of invalid selection for a role
     *
     * @param input provided invalid input
     * @param numRoles number of available roles for selection
     */
    public void invalidRoleSelection(int input, int numRoles) {
        System.out.printf("Invalid input, %d is out of range please select a number from 1 to %d.\n", input, numRoles);
    }

    /**
     * error occurred
     */
    public void displaySomethingWentWrong() {
        System.out.println("Sorry, something went wrong.");
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

    /**
     * Used to ask player to choose an action
     */
    public void sendPromptSelectAction() {
        System.out.println("To select an action, enter the number corresponding to the action.");
        System.out.print("Enter action: ");
    }

    /**
     * Used to ask player for a upgrade rank selection
     */
    public void sendPromptSelectUpgrade() {
        System.out.println("To select a new rank enter the number of the rank.");
        System.out.print("Enter rank: ");
    }

    /**
     * Used to ask player for the current to be used in an upgrade
     */
    public void sendPromptSelectCurrency() {
        System.out.println("""
                Select a currency type by typing the currency name or it's corresponding number.
                1. Dollars
                2. Credits""");
        System.out.print("Select Currency: ");
    }

    /**
     * Used to ask player for the type of role they wish to take
     */
    public void promptRoleType() {
        System.out.println("""
                Would you like to take a role as an extra on location, or a starring role on scene?
                Enter the name of your selection or the corresponding number.
                1. Extra
                2. Starring""");
        System.out.print("Select Role Type: ");
    }

    /**
     * Use to ask player to select the number of the role they wish to take
     */
    public void promptRole() {
        System.out.println("Enter the number of the role you would like to select " +
                "or type \"back\" to select another role type.");
        System.out.print("Select Role: ");
    }

    //---------------------------------------------------------------------------------------
    //endregion

}
