package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.GameState;
import benlinkurgra.deadwood.model.Player;
import benlinkurgra.deadwood.location.CastingOffice;
import benlinkurgra.deadwood.location.SetLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    /**
     * change the current active player
     *
     * @param activePlayer new active player
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
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
            display.displayNotANumber(input);
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
                break;
            case TAKE_ROLE:
                System.out.println("take role not yet implemented");
                break;
            case ACT:
                System.out.println("act not yet implemented");
                break;
            case REHEARSE:
                System.out.println("rehearse yet implemented");
                break;
            case UPGRADE:
                System.out.println("upgrade not yet implemented");
                break;
            case END_TURN:
                System.out.println("end turn not yet implemented");
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
        if (gameState.isCurrentPlayerDone()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "action already performed");
            }
            return false;
        } else if (!board.isSetLocation(locationName)) {
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
            boolean hasRoles = location.getAllAvailableRoles(activePlayer.getActingRank()).size() > 0;
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
        if (gameState.isCurrentPlayerDone()) {
            if (withPrint) {
                display.displayCanNotPerformAction(playerName, action, "action already performed");
            }
            return false;
        } else if (!activePlayer.getLocation().equals("office")) {
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

    public void provideAvailableActions() {
        List<String> actions = new ArrayList<>();
    }

    public void executeMove() {
        List<String> neighbors = locationNeighbors(activePlayer.getLocation());
        display.displayNeighbors(neighbors);
        String input = handleInput(display::sendPromptSelectLocation);
        if (neighbors.stream().anyMatch(input::equalsIgnoreCase)) {
            //TODO change active player location
            // use value from neighbors to ensure correct case
        } else {
            try {
                int locationNumber = Integer.parseInt(input);
                if (locationNumber < 0 || locationNumber > neighbors.size() - 1) {
                    throw new IllegalArgumentException("Input out of range");
                } else {
                    //TODO change active player location
                }
            } catch (Exception e) {
                display.displayInvalidMoveLocation(input);
                executeMove();
            }
        }
    }

    public void takeRole(Player player, String role){
        System.out.println("should update the role of this player");
    }

    public void updatePlayerLocation(Player player){

    }

    public void updatePlayerLocation(Player player, String role){
    }

    public void upgradePlayer(Player player, int newRank){
    }

    public void updatePlayerRank(Player player){
    }

    public void act(Player player){
    }

    public void rehearse(Player player){

    }

    public ArrayList<String> locationNeighbors(String location)  {
        return board.getLocation(location).getNeighbors();
    }

}
