package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.GameState;
import benlinkurgra.deadwood.location.CastingOffice;
import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.location.SetLocation;

public class Action {
    public static class Validity {
        private final boolean isValid;
        private final String reason;

        public Validity(boolean isValid, String reason) {
            this.isValid = isValid;
            this.reason = reason;
        }
        public boolean isValid() {
            return isValid;
        }

        public String getReason() {
            return reason;
        }
    }

    private Player activePlayer;
    private final Board board;
    private final GameState gameState;

    public Action (Player activePlayer, Board board, GameState gameState) {
        this.activePlayer = activePlayer;
        this.board = board;
        this.gameState = gameState;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Determines if active player can move.
     *
     * @return a Validity object that contains if move can be made and if not the reason why
     */
    public Validity canMove() {
        if (gameState.isCurrentPlayerDone()) {
            return new Validity(false, "action already performed");
        } else if (activePlayer.isWorkingRole()) {
            return new Validity(false, "currently acting on role");
        } else {
            return new Validity(true, "");
        }
    }

    /**
     * Determine if active player can take a role.
     *
     * @return a Validity object that contains if a role can be taken and if not the reason why
     */
    public Validity canTakeRole() {
        String locationName = activePlayer.getLocation();
        if (!board.isSetLocation(locationName)) {
            return new Validity(false, "must be at a set location");
        } else if (activePlayer.isWorkingRole()) {
            return new Validity(false,  "already working a role");
        } else {
            SetLocation location = (SetLocation) board.getLocation(locationName);
            boolean hasNoRoles = location.getAllAvailableRoles(activePlayer.getActingRank()).size() == 0;
            if (location.getSceneStatus() == SceneStatus.COMPLETED) {
                return new Validity(false, "the scene at location already completed");
            } else if (hasNoRoles) {
                return new Validity(false, "location has no open roles that player meets requirements for");
            } else {
                return new Validity(true, "");
            }
        }
    }

    /**
     * Determines if active player can act.
     *
     * @return a Validity object that contains if a player can act and if not the reason why.
     */
    public Validity canAct() {
        if (gameState.isCurrentPlayerDone()) {
            return new Validity(false, "action already performed");
        } else if (!activePlayer.isWorkingRole()) {
            return new Validity(false, "must be working a role");
        } else {
            return new Validity(true, "");
        }
    }

    /**
     * Determines if the active player can rehearse.
     *
     * @return a Validity object that contains if a player can rehearse and if not the reason why.
     */
    public Validity canRehearse() {
        if (gameState.isCurrentPlayerDone()) {
            return new Validity(false, "action already performed");
        } else if (!activePlayer.isWorkingRole()) {
            return new Validity(false, "must be working a role");
        } else {
            SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
            int budget = playerLocation.getSceneBudget();
            if (activePlayer.getPracticeToken() + 1 >= budget) {
                return new Validity(false, "success already guaranteed");
            } else {
                return new Validity(true, "");
            }
        }
    }

    /**
     * Determine if active player can upgrade.
     *
     * @return a Validity object that contains if a player can upgrade and if not the reason why.
     */
    public Validity canUpgrade() {
        if (!activePlayer.getLocation().equals("office")) {
            return new Validity(false, "must be at casting office");
        } else {
            CastingOffice office = (CastingOffice) board.getLocation("office");
            if (!office.hasUpgrades(activePlayer)) {
                return new Validity(
                        false,
                        "player either can not afford any higher ranks or is already max rank");
            } else {
                return new Validity(true, "");
            }
        }
    }

    /**
     * Determines if active player can end turn.
     * Player can not end role when they are working a role and have not performed an action.
     *
     * @return a Validity object that contains if a player can end turn and if not the reason why.
     */
    public Validity canEndTurn() {
        if (!activePlayer.isWorkingRole() || gameState.isCurrentPlayerDone()) {
            return new Validity(true, "");
        } else {
            return new Validity(false, "currently acting a role must either act or rehearse");
        }
    }
}
