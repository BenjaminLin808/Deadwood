package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.CurrencyType;
import benlinkurgra.deadwood.Dice;
import benlinkurgra.deadwood.location.*;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.GameState;
import benlinkurgra.deadwood.model.Player;

import java.util.*;
import java.util.stream.IntStream;

public class ActionProvider extends DisplayController {
    private Player activePlayer;
    private final Board board;
    private final GameState gameState;

    public ActionProvider(Display display, Player activePlayer, Board board, GameState gameState) {
        super(display);
        this.activePlayer = activePlayer;
        this.board = board;
        this.gameState = gameState;
    }

    @Override
    protected String handleInput(Runnable userPrompt) {
        boolean processing = true;
        String input = "";
        while (processing) {
            userPrompt.run();
            input = display.getUserInput();
            processing = checkForRequest(input);
            if (input.equals("player info")) {
                display.playerInfo(activePlayer.toString());
                processing = true;
            } else if (input.equals("location info")) {
                display.locationInfo(board.getLocation(activePlayer.getLocation()).toString());
                processing = true;
            }
        }
        return input;
    }

    public Action parseActionRequest() {
        String input = handleInput(display::sendPromptSelectAction);
        try {
            int actionNumber = Integer.parseInt(input);
            if (actionNumber < 1 || actionNumber > 6) {
                display.sendInvalidActionSelection(actionNumber);
                return parseActionRequest();
            }
            Action action = Action.valueOf(actionNumber);
            if (validateActionSelection(action)) {
                return action;
            } else {
                return parseActionRequest();
            }
        } catch (NumberFormatException e) {
            display.notANumber(input);
            return parseActionRequest();
        }
    }

    private boolean validateActionSelection(Action action) {
        switch (action) {
            case MOVE:
                return canMove(true);
            case TAKE_ROLE:
                return canTakeRole(true);
            case ACT:
                return canAct(true);
            case REHEARSE:
                return canRehearse(true);
            case UPGRADE:
                return canUpgrade(true);
            case END_TURN:
                return canEndTurn(true);
            default:
                System.out.println("with validate,,,, action: " + action);
                display.displaySomethingWentWrong();
                return false;
        }
    }

    public void performAction(Action action) {
        switch (action) {
            case MOVE:
                if (executeMove()) {
                    gameState.setCurrentPlayerDoneTrue();
                }
                break;
            case TAKE_ROLE:
                if (takeRole()) {
                    gameState.setCurrentPlayerDoneTrue();
                }
                break;
            case ACT:
                act();
                gameState.setCurrentPlayerDoneTrue();
                break;
            case REHEARSE:
                rehearse();
                gameState.setCurrentPlayerDoneTrue();
                break;
            case UPGRADE:
                upgradePlayer();
                break;
            case END_TURN:
                endTurn();
                break;
            default:
                System.out.println("with perform,,,, action: " + action);
                display.displaySomethingWentWrong();
                parseActionRequest();
        }
    }

    /**
     * Signal display to provide a list of actions and highlight actions based on availability.
     */
    public void provideActionsWithHighlighting() {
        display.sendActions(canMove(false),
                canTakeRole(false),
                canAct(false),
                canRehearse(false),
                canUpgrade(false),
                canEndTurn(false));
    }

    /**
     * Determines if active player can move.
     *
     * @param withPrint signal to display why move can not be performed
     * @return true if player can move, false otherwise.
     */
    private boolean canMove(boolean withPrint) {
        String playerName = activePlayer.getName();
        String action = "move";
        if (gameState.isCurrentPlayerDone()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "action already performed");
            }
            return false;
        } else if (activePlayer.isWorkingRole()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "currently acting on role");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determine if active player can take a role.
     *
     * @param withPrint signal to display why role can not be taken
     * @return true if player can take a role, false otherwise.
     */
    private boolean canTakeRole(boolean withPrint) {
        String playerName = activePlayer.getName();
        String action = "take a role";
        String locationName = activePlayer.getLocation();
        if (!board.isSetLocation(locationName)) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "must be at a set location");
            }
            return false;
        } else if (activePlayer.isWorkingRole()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "already working a role");
            }
            return false;
        } else {
            SetLocation location = (SetLocation) board.getLocation(locationName);
            boolean hasRoles = !(location.getAllAvailableRoles(activePlayer.getActingRank()).size() > 0);
            if (location.getSceneStatus() == SceneStatus.COMPLETED) {
                if (withPrint) {
                    display.displayCanNotPerformAction(playerName, action, "the scene at location already completed");
                }
                return false;
            } else if (hasRoles) {
                if (withPrint) {
                    display.displayCanNotPerformAction(playerName, action, "location has no open roles that player meets requirements for");
                }
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Determines if active player can act.
     *
     * @param withPrint signal to display why player can not act
     * @return true if player can act, false otherwise.
     */
    private boolean canAct(boolean withPrint) {
        String playerName = activePlayer.getName();
        String action = "act";
        if (gameState.isCurrentPlayerDone()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "action already performed");
            }
            return false;
        } else if (!activePlayer.isWorkingRole()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "must be working a role");
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determines if the active player can rehearse.
     *
     * @param withPrint signal to display why player can not rehearse
     * @return true if player can rehearse, false otherwise.
     */
    private boolean canRehearse(boolean withPrint) {
        String playerName = activePlayer.getName();
        String action = "rehearse";
        if (gameState.isCurrentPlayerDone()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "action already performed");
            }
            return false;
        } else if (!activePlayer.isWorkingRole()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "must be working a role");
            }
            return false;
        } else {
            SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
            int budget = playerLocation.getSceneBudget();
            if (activePlayer.getPracticeToken() + 1 >= budget) {
                if (withPrint) {
                    display.displayCanNotPerformAction(playerName, action, "success already guaranteed");
                }
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Determine if active player can upgrade.
     *
     * @param withPrint signal to display why player can not upgrade
     * @return true if active player can upgrade, false otherwise.
     */
    private boolean canUpgrade(boolean withPrint) {
        String playerName = activePlayer.getName();
        String action = "upgrade";
        if (!activePlayer.getLocation().equals("office")) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "must be at casting office");
            }
            return false;
        } else {
            CastingOffice office = (CastingOffice) board.getLocation("office");
            if (!office.hasUpgrades(activePlayer)) {
                if (withPrint) {
                    display.displayCanNotPerformAction(playerName,
                            action,
                            "player either can not afford any higher ranks or is already max rank");
                }
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Determines if active player can end turn.
     * Player can not end role when they are working a role and have not performed an action.
     *
     * @param withPrint signal to display why player can not end turn
     * @return true if player can end turn, otherwise false.
     */
    private boolean canEndTurn(boolean withPrint) {
        if (!activePlayer.isWorkingRole() || gameState.isCurrentPlayerDone()) {
            return true;
        } else {
            if (withPrint) {
                display.displayCanNotPerformAction(activePlayer.getName(),
                        "end turn",
                        "currently acting a role must either act or rehearse");
            }
            return false;
        }
    }

    public void provideActivePlayer() {
        display.sendActivePlayer(activePlayer.getName());
    }

    public boolean executeMove() {
        List<String> neighbors = locationNeighbors(activePlayer.getLocation());
        display.displayNeighbors(neighbors);
        String input = handleInput(display::sendPromptEnterLocation);
        if (input.equals("cancel")) {
            return false;
        } else {
            // Attempt to find index of matching location name
            OptionalInt index = IntStream.range(0, neighbors.size())
                    .filter(i -> neighbors.get(i).equalsIgnoreCase(input))
                    .findFirst();

            if (index.isPresent()) {
                String newLocation = neighbors.get(index.getAsInt());
                display.moveSuccess(activePlayer.getName(), activePlayer.getLocation(), newLocation);
                activePlayer.setLocation(newLocation);
                return true;
            } else {
                try {
                    int locationNumber = Integer.parseInt(input);
                    if (locationNumber < 1 || locationNumber > neighbors.size()) {
                        throw new IllegalArgumentException("Input out of range");
                    } else {
                        String newLocation = neighbors.get(locationNumber - 1);
                        display.moveSuccess(activePlayer.getName(), activePlayer.getLocation(), newLocation);
                        activePlayer.setLocation(newLocation);
                        return true;
                    }
                } catch (Exception e) {
                    display.displayInvalidMoveLocation(input);
                    return executeMove();
                }
            }
        }
    }

    public boolean takeRole() {
        SetLocation location = (SetLocation) board.getLocation(activePlayer.getLocation());
        display.rolesAtLocation(location.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRoleType);
        if (input.equals("cancel")) {
            return false;
        } else {
            if (input.equalsIgnoreCase("extra") || input.equals("1")) {
                if (location.canTakeLocationRole(activePlayer.getActingRank())) {
                    return takeExtraRole(location);
                } else {
                    display.displayCanNotPerformAction(activePlayer.getName(),
                            "take role as extra",
                            "location has no open roles that player meets requirements for");
                    return takeRole();
                }
            } else if (input.equalsIgnoreCase("starring") || input.equals("2")) {
                if (location.canTakeSceneRole(activePlayer.getActingRank())) {
                    return takeStarringRole(location);
                } else {
                    display.displayCanNotPerformAction(activePlayer.getName(),
                            "take starring role",
                            "scene has no open roles that player meets requirements for");
                    return takeRole();
                }
            } else {
                display.invalidRoleType(input);
                return takeRole();
            }
        }
    }

    private boolean takeExtraRole(SetLocation location) {
        Roles roles = location.getRolesOnLocation();
        display.rolesAtLocation(roles.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRole);
        if (input.equals("back")) {
            return takeRole();
        } else if (input.equals("cancel")) {
            return false;
        } else {
            try {
                int roleNum = Integer.parseInt(input) - 1;
                if (roleNum < 0 || roleNum > location.getNumRolesOnLocation() - 1) {
                    display.invalidRoleSelection(roleNum + 1, location.getNumRolesOnLocation());
                    return takeExtraRole(location);
                } else {
                    String roleName = roles.getRoleList().get(roleNum).getName();
                    boolean roleFilled = location.fillRoleOnLocation(activePlayer.getName(),
                            activePlayer.getActingRank(),
                            roleName);
                    if (roleFilled) {
                        activePlayer.setWorkingRole(true);
                        display.roleTaken(activePlayer.getName(), location.getName(), roleName);
                        return true;
                    } else {
                        String reason = "";
                        if (!roles.getRoleList().get(roleNum).isAvailable()) {
                            reason = "role already filled by " + roles.getRoleList().get(roleNum).getPlayerOnRole();
                        } else if (roles.getRoleList().get(roleNum).getRank() > activePlayer.getActingRank()) {
                            reason = "player must be rank " + roles.getRoleList().get(roleNum).getRank() + " or higher";
                        } else {
                            reason = "could not find role";
                        }
                        display.displayCanNotPerformAction(activePlayer.getName(),
                                "take role" + roleName,
                                reason);
                        return takeExtraRole(location);
                    }
                }
            } catch (NumberFormatException e) {
                display.notANumber(input);
                return takeExtraRole(location);
            }
        }
    }

    private boolean takeStarringRole(SetLocation location) {
        Roles roles = location.getRolesOnScene();
        display.rolesAtLocation(roles.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRole);
        if (input.equals("back")) {
            return takeRole();
        } else if (input.equals("cancel")) {
            return false;
        } else {
            try {
                int roleNum = Integer.parseInt(input) - 1;
                if (roleNum < 0 || roleNum > location.getNumRolesOnScene() - 1) {
                    display.invalidRoleSelection(roleNum + 1, location.getNumRolesOnScene());
                    return takeStarringRole(location);
                } else {
                    String roleName = roles.getRoleList().get(roleNum).getName();
                    boolean roleFilled = location.fillRoleOnScene(activePlayer.getName(),
                            activePlayer.getActingRank(),
                            roleName);
                    if (roleFilled) {
                        activePlayer.setWorkingRole(true);
                        display.roleTaken(activePlayer.getName(), location.getName(), roleName);
                        return true;
                    } else {
                        String reason = "";
                        if (!roles.getRoleList().get(roleNum).isAvailable()) {
                            reason = "role alrady filled by " + roles.getRoleList().get(roleNum).getPlayerOnRole();
                        } else if (roles.getRoleList().get(roleNum).getRank() > activePlayer.getActingRank()) {
                            reason = "player must be rank " + roles.getRoleList().get(roleNum).getRank() + " or higher";
                        } else {
                            reason = "could not find role";
                        }
                        display.displayCanNotPerformAction(activePlayer.getName(),
                                "take role" + roleName,
                                reason);
                        return takeStarringRole(location);
                    }
                }
            } catch (NumberFormatException e) {
                display.notANumber(input);
                return takeStarringRole(location);
            }
        }

    }

    public void upgradePlayer() {
        CastingOffice office = (CastingOffice) board.getLocation("office");
        display.displayValidUpgrades(office
                .toStringWithHighlight(activePlayer.getActingRank(),
                        activePlayer.getCredits(),
                        activePlayer.getDollars()));
        String input = handleInput(display::sendPromptSelectUpgrade);
        if (input.equals("cancel")) {
            return;
        } else {
            try {
                int rankNumber = Integer.parseInt(input);
                if (rankNumber < 2 || rankNumber > 6) {
                    display.displayInvalidRankSelection(input);
                    upgradePlayer();
                } else if (rankNumber <= activePlayer.getActingRank()) {
                    display.displayCanNotPerformAction(activePlayer.getName(),
                            "upgrade to rank " +
                                    rankNumber,
                            "current rank is higher or equal");
                    upgradePlayer();
                } else {
                    CurrencyType currencyType = getCurrencyType();
                    if (office.isValidUpgrade(activePlayer, rankNumber, currencyType)) {
                        int cost = office.getCost(rankNumber, currencyType);
                        activePlayer.upgrade(rankNumber, currencyType, cost);
                        display.upgradeSuccess(activePlayer.getName(), rankNumber);
                    } else {
                        display.displayCanNotPerformAction(activePlayer.getName(),
                                "upgrade to rank " +
                                        rankNumber + " with " +
                                        ((currencyType == CurrencyType.DOLLARS) ? "dollars" : "credits"),
                                "they do not meet the requirements");
                        upgradePlayer();
                    }
                }
            } catch (NumberFormatException e) {
                display.notANumber(input);
                upgradePlayer();
            }
        }
    }

    private CurrencyType getCurrencyType() {
        String input = handleInput(display::sendPromptSelectCurrency);
        if (input.equalsIgnoreCase("credits")) {
            return CurrencyType.CREDITS;
        } else if (input.equalsIgnoreCase("dollars")) {
            return CurrencyType.DOLLARS;
        } else {
            try {
                int type = Integer.parseInt(input);
                if (type == 1) {
                    return CurrencyType.DOLLARS;
                } else if (type == 2) {
                    return CurrencyType.CREDITS;
                } else {
                    throw new IllegalArgumentException("Not a valid currency input");
                }
            } catch (Exception e) {
                display.invalidCurrency(input);
                return getCurrencyType();
            }
        }
    }

    public void act() {
        String playerName = activePlayer.getName();
        Dice dice = new Dice();
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        boolean onCard = playerLocation.isActingOnScene(playerName);
        int outcome = dice.roll();
        int practiceToken = activePlayer.getPracticeToken();
        int budget = playerLocation.getSceneBudget();
        display.diceOutcome(playerName, outcome, practiceToken, budget);
        boolean success = outcome + practiceToken >= budget;
        if (success) {
            playerLocation.removeShotToken();
            if (onCard) {
                activePlayer.addCredits(2);
                display.actSuccess(playerName, "credits", 2);
            } else {
                activePlayer.addCredits(1);
                activePlayer.addDollars(1);
                display.actSuccess(playerName);
            }
        } else {
            if (onCard) {
                display.actFail(playerName);
            } else {
                activePlayer.addDollars(1);
                display.actFail(playerName, 1);
            }
        }
        if (playerLocation.getCurrentShotTokens() == 0) {
            wrappedScene();
        }
    }

    // TODO need to revise this method
    private void wrappedScene() {
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        display.sceneFinished(playerLocation.getName(), playerLocation.getSceneName());
        List<String> playersOnCard = playerLocation.playersActingOnScene();
        List<Player> playersOffCard = gameState.getPlayers(playerLocation.playersActingOnLocation());

        // If players are working on scene payout bonus
        if (playersOnCard.size() != 0) {
            Dice dice = new Dice();
            int budget = playerLocation.getSceneBudget();
            Queue<Player> orderedPlayersOnCard = gameState.getPlayersInOrder(playersOnCard);
            for (int i = 0; i < budget; i++) {
                int diceOutcome = dice.roll();
                Player player = orderedPlayersOnCard.remove();
                player.addDollars(diceOutcome);
                display.earnBonus(player.getName(), diceOutcome);
                orderedPlayersOnCard.add(player);
            }
            for (Player player : playersOffCard) {
                int roleRank = playerLocation.getLocationRoleRank(player.getName());
                player.addDollars(roleRank);
                display.earnBonus(player.getName(), roleRank);
            }

            for (Player player : orderedPlayersOnCard) {
                player.setWorkingRole(false);
                player.resetPracticeTokens();
            }

        }

        for (Player player : playersOffCard) {
            player.setWorkingRole(false);
            player.resetPracticeTokens();
        }

        playerLocation.emptyLocationRoles();
        playerLocation.setSceneStatus(SceneStatus.COMPLETED);
        gameState.decrementActiveScenes();
    }

    public void rehearse() {
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        int budget = playerLocation.getSceneBudget();
        if (activePlayer.getPracticeToken() == budget - 1) {
            display.rehearseFail(activePlayer.getName(), activePlayer.getPracticeToken());
        } else {
            activePlayer.addPracticeToken();
            display.rehearseSuccess(activePlayer.getName(), activePlayer.getPracticeToken());
        }
    }

    private void endTurn() {
        display.playerDone(activePlayer.getName());
        activePlayer = gameState.changeActivePlayer();
    }

    public void endDay() {
        display.endDay(gameState.getCurrDay(), gameState.getEndDay());
    }

    public void endGame() {
        Queue<Player> players = gameState.getPlayerOrder();
        List<String> winners = new ArrayList<>();
        int highScore = 0;
        for (Player player : players) {
            int score = player.score();
            display.score(player.getName(), score);
            if (score > highScore) {
                winners.clear();
                winners.add(player.getName());
                highScore = score;
            } else if (score == highScore) {
                winners.add(player.getName());
            }
        }

        if (winners.size() == 1) {
            display.winners(winners.get(0), highScore);
        } else {
            display.winners(winners, highScore);
        }
    }

    public ArrayList<String> locationNeighbors(String location) {
        return board.getLocation(location).getNeighbors();
    }

}
