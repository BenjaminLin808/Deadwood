package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Board;
import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.GameState;
import benlinkurgra.deadwood.Player;
import benlinkurgra.deadwood.location.CastingOffice;
import benlinkurgra.deadwood.location.SetLocation;

import java.util.ArrayList;
import java.util.List;

public class ActionProvider {
    private final Display display;
    private Player activePlayer;
    private final Board board;
    private final GameState gameState;

    public ActionProvider(Display display, Player activePlayer, Board board, GameState gameState) {
        this.activePlayer = activePlayer;
        this.display = display;
        this.board = board;
        this.gameState = gameState;
    }

    public void provideActions() {
        display.sendActions();
    }

    /**
     * Determines if active player can move.
     *
     * @return true if player can move, false otherwise.
     */
    private boolean canMove() {
        if (activePlayer.isWorkingRole()) {
            //TODO tell display print "currently working role can not move"
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determines if active player can act.
     *
     * @return true if player can act, false otherwise.
     */
    private boolean canAct() {
        if (activePlayer.isWorkingRole()) {
            return true;
        } else {
            //TODO tell display to print "not working a role must be working a role at set location"
            return false;
        }
    }

    /**
     * Determines if the active player can rehearse.
     *
     * @return true if player can rehearse, false otherwise.
     */
    private boolean canRehearse() {
        if (activePlayer.isWorkingRole()) {
            SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
            int budget = playerLocation.getSceneBudget();
            if (activePlayer.getPracticeToken() + 1 >= budget) {
                //TODO tell display to print "success already guaranteed"
                return false;
            } else {
                return true;
            }
        } else {
            //TODO tell display to print "not working a role must be working a role at set location"
            return false;
        }
    }

    /**
     * Determine if active player can upgrade.
     *
     * @return true if active player can upgrade, false otherwise.
     */
    private boolean canUpgrade() {
        if (activePlayer.getLocation().equals("office")) {
            CastingOffice office = (CastingOffice) board.getLocation("office");
            if (office.hasUpgrades(activePlayer)) {
                return true;
            } else {
                //TODO tell display to print "no valid upgrades at casting office"
                return false;
            }
        } else {
            //TODO tell display to print "not at casting office"
            return false;
        }
    }

    /**
     * Determines if active player can end turn.
     * Player can not end role when they are working a role and have not performed an action.
     *
     * @return true if player can end turn, otherwise false.
     */
    private boolean canEndTurn() {
        if (activePlayer.isWorkingRole() && !gameState.isCurrentPlayerDone()) {
            return false;
        } else {
            return true;
        }
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void provideActivePlayer() {
        display.sendActivePlayer(activePlayer.getName());
    }

    public void provideAvailableActions() {
        List<String> actions = new ArrayList<>();
    }

    public void movePlayer(Player player, String newLocation){
        System.out.println("this should invoke some function to update the player location");
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

}
