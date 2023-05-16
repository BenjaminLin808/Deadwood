package benlinkurgra.deadwood;

import java.util.List;
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

    public void playerInfo(String player) {
        System.out.println(player);
    }

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

    public String colorizeName(String playerName) {
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
//        String yellowText = "\u001B[33m";
//        String resetTextColor = "\u001B[0m";
//        String rankFormat = "%1$-10s";
//        String dollarFormat = "%1$-13s";
//        String creditFormat = "%1$-11s";
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("Here is a list of all rank upgrades. Invalid upgrades highlighted in ");
//        sb.append(yellowText);
//        sb.append("yellow");
//        sb.append(resetTextColor);
//        sb.append(":\n");
//        sb.append("+----------+-------------+-----------+\n");
//        sb.append("|   RANK   |   DOLLARS   |  CREDITS  |\n");
//        sb.append("+----------+-------------+-----------+\n");
//
//        for (Map.Entry<Integer, UpgradeCost> upgrade : upgrades.entrySet()) {
//            UpgradeCost upgradeCost = upgrade.getValue();
//            int dollarCost = upgradeCost.getDollarCost();
//            int creditCost = upgradeCost.getCreditsCost();
//
//            if (upgrade.getKey() <= player.getActingRank()) {
//                sb.append("|" + yellowText + String.format(rankFormat, upgrade.getKey()) + resetTextColor +
//                        "|" + yellowText + String.format(dollarFormat, dollarCost) + resetTextColor +
//                        "|" + yellowText + String.format(creditFormat, creditCost) + resetTextColor +
//                        "|\n");
//            } else {
//                String dollarColor = ((player.getDollars() < dollarCost)
//                        ? yellowText : resetTextColor);
//                String creditColor = ((player.getCredits() < creditCost)
//                        ? yellowText : resetTextColor);
//
//                sb.append("|" + String.format(rankFormat, upgrade.getKey()) +
//                        "|" + dollarColor + String.format(dollarFormat, dollarCost) + resetTextColor +
//                        "|" + creditColor + String.format(creditFormat, creditCost) + resetTextColor +
//                        "|\n");
//            }
//        }
//        sb.append("+----------+-------------+-----------+");
//        System.out.println(sb);
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

    public void rolesAtLocation(String locationString) {
        System.out.print(locationString);
    }

    public void moveSuccess(String playerName, String oldLocation, String newLocation) {
        System.out.printf("Move successful, %s has moved from %s to %s\n",
                colorizeName(playerName),
                oldLocation,
                newLocation);
    }

    public void upgradeSuccess(String playerName, int rank) {
        System.out.printf("Upgrade successful, %s has upgraded to rank %d\n",
                colorizeName(playerName),
                rank);
    }

    public void actSuccess(String playerName) {
        System.out.printf("Act successful, %s has earned 1 credit and 1 dollar.\n",
                colorizeName(playerName));
    }

    public void actSuccess(String playerName, String currencyType, int currencyIncrease) {
        System.out.printf("Act successful, %s has earned %d %s.\n",
                colorizeName(playerName),
                currencyIncrease,
                currencyType);
    }

    public void rehearseSuccess(String playerName, int practiceTokens) {
        System.out.printf("Rehearse successful, %s has %d practice tokens\n",
                colorizeName(playerName),
                practiceTokens);
    }

    public void actFail(String playerName, int dollars) {
        System.out.printf("Act failed, %s has earned %d dollar\n",
                colorizeName(playerName),
                dollars);
    }

    public void actFail(String playerName) {
        System.out.printf("Act failed, %s receives nothing.\n", colorizeName(playerName));

    }

    public void rehearseFail(String playerName, int practiceTokens) {
        System.out.printf("Rehearse failed, %s has %d \n", colorizeName(playerName), practiceTokens);
    }

    public void diceOutcome(String playerName, int outcome, int practiceTokens, int budget) {
        System.out.printf("Player %s rolled %d.\n" +
                "They have %d practice token(s) and the scene budget is %d.\n",
                colorizeName(playerName),
                outcome,
                practiceTokens,
                budget);
    }

    public void sceneFinished(String locationName, String sceneName) {
        System.out.printf("Scene %s has finished at location %s.\n", locationName, sceneName);
    }

    public void earnBonus(String playerName, int bonusAmount) {
        System.out.printf("Player %s has earned a %d dollar bonus.\n",
                colorizeName(playerName),
                bonusAmount);
    }

    public void roleTaken(String playerName, String locationName, String roleName) {
        System.out.printf("Player %s, at Location %s, has taken the role: %s.\n",
                colorizeName(playerName),
                locationName, roleName);
    }

    public void playerDone(String playerName) {
        System.out.printf("Ending player %s's turn.\n", colorizeName(playerName));
    }

    public void endDay(int currDay, int lastDay) {
        System.out.printf("Day %d of %d is ending.\n", currDay, lastDay);
    }

    public void score(String playerName, int score) {
        System.out.printf("Player %s scored %d.\n", colorizeName(playerName), score);
    }

    public void winners(String playerName, int score) {
        System.out.printf("Player %s has won the game with a score of %d.",
                colorizeName(playerName),
                score);
    }

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

    public void displayInvalidMoveLocation(String input) {
        System.out.printf("Invalid input, %s is not a valid selection for a move.\n", input);
    }

    public void displayInvalidRankSelection(String input) {
        System.out.printf("Invalid input, %s is not a valid rank number, please select a number from 2 to 6.\n", input);
    }

    public void invalidRoleType(String input) {
        System.out.printf("Invalid input, %s is not valid role type.\n", input);
    }

    public void invalidCurrency(String input) {
        System.out.printf("Invalid input, %s is not a valid currency.\n", input);
    }

    public void invalidRoleSelection(int input, int numRoles) {
        System.out.printf("Invalid input, %d is out of range please select a number from 1 to %d.\n", input, numRoles);
    }

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

    public void sendPromptSelectAction() {
        System.out.println("To select an action, enter the number corresponding to the action.");
        System.out.print("Enter action: ");
    }

    public void sendPromptSelectUpgrade() {
        System.out.println("To select a new rank enter the number of the rank.");
        System.out.print("Enter rank: ");
    }

    public void sendPromptSelectCurrency() {
        System.out.println("""
                Select a currency type by typing the currency name or it's corresponding number.
                1. Dollars
                2. Credits""");
        System.out.print("Select Currency: ");
    }

    public void promptRoleType() {
        System.out.println("""
                Would you like to take a role as an extra on location, or a starring role on scene?
                Enter the name of your selection or the corresponding number.
                1. Extra
                2. Starring""");
        System.out.print("Select Role Type: ");
    }

    public void promptRole() {
        System.out.println("Enter the number of the role you would like to select " +
                "or type \"back\" to select another role type.");
        System.out.print("Select Role: ");
    }

    //---------------------------------------------------------------------------------------
    //endregion

}
