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
                display.displaySomethingWentWrong();
                return false;
        }
    }

    public void attemptAction(Action action) {
        switch (action) {
            case MOVE:
                executeMove();
                gameState.setCurrentPlayerDoneTrue();
                break;
            case TAKE_ROLE:
                takeRole();
                gameState.setCurrentPlayerDoneTrue();
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
                //TODO how to handle being able to upgrade before a move?
                upgradePlayer();
                break;
            case END_TURN:
                endTurn();
                break;
            default:
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
                    display.displayCanNotPerformAction(playerName, action, "scene at location already completed");
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

    public void executeMove() {
        List<String> neighbors = locationNeighbors(activePlayer.getLocation());
        display.displayNeighbors(neighbors);
        String input = handleInput(display::sendPromptEnterLocation);

        OptionalInt index = IntStream.range(0, neighbors.size())
                .filter(i -> neighbors.get(i).equalsIgnoreCase(input))
                .findFirst();

        if (index.isPresent()) {
            int matchIndex = index.getAsInt();
            String newLocation = neighbors.get(matchIndex);
            display.moveSuccess(activePlayer.getName(), activePlayer.getLocation(), newLocation);
            activePlayer.setLocation(newLocation);
        } else {
            try {
                int locationNumber = Integer.parseInt(input);
                if (locationNumber < 0 || locationNumber > neighbors.size() - 1) {
                    throw new IllegalArgumentException("Input out of range");
                } else {
                    String newLocation = neighbors.get(locationNumber);
                    display.moveSuccess(activePlayer.getName(), activePlayer.getLocation(), newLocation);
                    activePlayer.setLocation(newLocation);
                }
            } catch (Exception e) {
                display.displayInvalidMoveLocation(input);
                executeMove();
            }
        }
    }

    public void takeRole() {
        SetLocation location = (SetLocation) board.getLocation(activePlayer.getLocation());
        display.rolesAtLocation(location.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRoleType);
        if (input.equalsIgnoreCase("extra") || input.equals("1")) {
            //TODO check that can take role as extra
            takeExtraRole(location);
        } else if (input.equalsIgnoreCase("starring") || input.equals("2")) {
            //TODO check that can take starring role
            takeStarringRole(location);
        } else {
            display.invalidRoleType(input);
            takeRole();
        }
    }

    private void takeExtraRole(SetLocation location) {
        Roles roles = location.getRolesOnLocation();
        display.rolesAtLocation(roles.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRole);
        try {
            int roleNum = Integer.parseInt(input) - 1;
            if (roleNum < 0 || roleNum > location.getNumRolesOnLocation() - 1) {
                display.invalidRoleSelection(roleNum + 1, location.getNumRolesOnLocation());
                takeExtraRole(location);
            } else {
                String roleName = roles.getRoleList().get(roleNum).getName();
                boolean roleFilled = location.fillRoleOnLocation(activePlayer.getName(),
                        activePlayer.getActingRank(),
                        roleName);
                if (roleFilled) {
                    activePlayer.setWorkingRole(true);
                    display.roleTaken(activePlayer.getName(), location.getName(), roleName);
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
                    takeExtraRole(location);
                }
            }
        } catch (NumberFormatException e) {
            display.notANumber(input);
            takeExtraRole(location);
        }
    }

    private void takeStarringRole(SetLocation location) {
        Roles roles = location.getRolesOnScene();
        display.rolesAtLocation(roles.toStringWithHighlight(activePlayer.getActingRank()));
        String input = handleInput(display::promptRole);
        try {
            int roleNum = Integer.parseInt(input) - 1;
            if (roleNum < 0 || roleNum > location.getNumRolesOnScene() - 1) {
                display.invalidRoleSelection(roleNum + 1, location.getNumRolesOnScene());
                takeStarringRole(location);
            } else {
                String roleName = roles.getRoleList().get(roleNum).getName();
                boolean roleFilled = location.fillRoleOnScene(activePlayer.getName(),
                        activePlayer.getActingRank(),
                        roleName);
                if (roleFilled) {
                    activePlayer.setWorkingRole(true);
                    display.roleTaken(activePlayer.getName(), location.getName(), roleName);
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
                    takeStarringRole(location);
                }
            }
        } catch (NumberFormatException e) {
            display.notANumber(input);
            takeStarringRole(location);
        }
    }

    public void upgradePlayer() {
        CastingOffice office = (CastingOffice) board.getLocation("office");
        display.displayValidUpgrades(activePlayer, office.getUpgrades());
        String input = handleInput(display::sendPromptSelectUpgrade);
        try {
            int rankNumber = Integer.parseInt(input);
            if (rankNumber < 2 || rankNumber > 6) {
                display.displayInvalidRankSelection(input);
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
        Dice dice = new Dice();
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        List<RoleData> roles = playerLocation.getRolesOnLocation().getRoleList();
        boolean onCard = false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getName().equals(activePlayer.getName())) {
                onCard = roles.get(i).isOnCard();
            }
        }

        if (onCard) {
            if (dice.roll() + activePlayer.getPracticeToken() >= playerLocation.getSceneBudget()) {
                playerLocation.removeShotToken();
                activePlayer.setCredits(activePlayer.getCredits() + 1);
                activePlayer.setDollars(activePlayer.getDollars() + 1);
                display.actSuccess(activePlayer.getName(), activePlayer.getCredits(), activePlayer.getDollars());
            } else {
                activePlayer.setCredits(activePlayer.getCredits() + 1);
                display.actFail(activePlayer.getName(), activePlayer.getCredits(), activePlayer.getDollars());
            }
        } else {
            if (dice.roll() + activePlayer.getPracticeToken() >= playerLocation.getSceneBudget()) {
                playerLocation.removeShotToken();
                activePlayer.setCredits(activePlayer.getCredits() + 2);
                display.actSuccess(activePlayer.getName(), activePlayer.getCredits(), activePlayer.getDollars());
            } else {
                display.actFail(activePlayer.getName(), activePlayer.getCredits(), activePlayer.getDollars());
            }
        }
    }

    // TODO need to revise this method
    private void wrappedScene() {
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        Dice dice = new Dice();
        Queue<Integer> bonus = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < playerLocation.getSceneBudget(); i++) {
            bonus.add(dice.roll());
        }
        List<RoleData> roles = playerLocation.getRolesOnLocation().getRoleList();
        Queue<Player> playerOrder = gameState.getPlayerOrder();
        Queue<Player> playersOnCard = new PriorityQueue<>(Comparator.comparingInt(Player::getActingRank).reversed());
        Queue<Player> playersOffCard = new PriorityQueue<>();
        for (Player player : playerOrder) {
            for (RoleData role : roles) {
                if (player.getName().equals(role.getPlayerOnRole())) {
                    if (role.isOnCard()) {
                        playersOnCard.add(player);
                    } else {
                        playersOffCard.add(player);
                    }
                }
            }
        }

        while (bonus.size() != 0) {
            for (Player playerOnCard : playersOnCard) {
                playerOnCard.setDollars(playerOnCard.getDollars() + bonus.poll());
            }
        }
        if (playersOnCard.size() >= 1) {
            for (Player playerOffCard : playersOffCard) {
                playerOffCard.setDollars(playerOffCard.getDollars() + playerOffCard.getActingRank());
            }
        }
    }

    public void rehearse() {
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        int budget = playerLocation.getSceneBudget();
        if (activePlayer.getPracticeToken() == budget - 1) {
            display.rehearseFail(activePlayer.getName(), activePlayer.getPracticeToken());
        } else {
            activePlayer.setPracticeToken(activePlayer.getPracticeToken() + 1);
            display.rehearseSuccess(activePlayer.getName(), activePlayer.getPracticeToken());
        }
    }

    public void endTurn() {
        display.playerDone(activePlayer.getName());
        activePlayer = gameState.endTurn();
    }

    public ArrayList<String> locationNeighbors(String location) {
        return board.getLocation(location).getNeighbors();
    }

}
