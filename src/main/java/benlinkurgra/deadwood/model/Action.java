package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.CurrencyType;
import benlinkurgra.deadwood.Dice;
import benlinkurgra.deadwood.GameState;
import benlinkurgra.deadwood.location.CastingOffice;
import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.location.SetLocation;

import java.util.*;

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

    public static class ActOutcome {
        private final boolean actSuccess;
        private final int creditsEarned;
        private final int dollarsEarned;
        private final boolean sceneFinished;

        public ActOutcome(boolean actSuccess,
                          int creditsEarned,
                          int dollarsEarned,
                          boolean sceneFinished) {
            this.actSuccess = actSuccess;
            this.creditsEarned = creditsEarned;
            this.dollarsEarned = dollarsEarned;
            this.sceneFinished = sceneFinished;
        }

        public boolean isActSuccess() {
            return actSuccess;
        }

        public int getCreditsEarned() {
            return creditsEarned;
        }

        public int getDollarsEarned() {
            return dollarsEarned;
        }

        public boolean isSceneFinished() {
            return sceneFinished;
        }
    }

    public static class ScenePayout {
        private final boolean bonusPayed;
        private final Map<String, Integer> earnings;

        public ScenePayout(boolean bonusPayed, Map<String, Integer> earnings) {
            this.bonusPayed = bonusPayed;
            this.earnings = earnings;
        }

        public boolean isBonusPayed() {
            return bonusPayed;
        }

        public Map<String, Integer> getEarnings() {
            return earnings;
        }
    }

    private Player activePlayer;
    private final Board board;
    private final GameState gameState;

    public Action(Player activePlayer, Board board, GameState gameState) {
        this.activePlayer = activePlayer;
        this.board = board;
        this.gameState = gameState;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
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
            return new Validity(false, "already working a role");
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

    /**
     * change appropriate states to move active player to a new Location, assumes newLocation is a valid location
     *
     * @param newLocation location player is moving to.
     */
    public void move(String newLocation) {
        activePlayer.setLocation(newLocation);
        gameState.setCurrentPlayerDoneTrue();
    }

    /**
     * change appropriate states to have active player take a role, assumes player can take the role
     *
     * @param location location of role
     * @param roleName name of role
     * @param onScene  true if role is on the scene card, otherwise false
     */
    public void takeRole(SetLocation location, String roleName, boolean onScene) {
        if (onScene) {
            location.fillRoleOnScene(activePlayer.getName(),
                    activePlayer.getActingRank(),
                    roleName);
        } else {
            location.fillRoleOnLocation(activePlayer.getName(),
                    activePlayer.getActingRank(),
                    roleName);
        }
        activePlayer.setWorkingRole(true);
        gameState.setCurrentPlayerDoneTrue();
    }

    /**
     * change appropriate states for active player performing an act action, assumes player can act
     *
     * @param diceOutcome outcome of a die roll for act action
     * @return a ActOutcome object, contains info on success of action, if scene should be finished and currency earned
     */
    public ActOutcome act(int diceOutcome) {
        String playerName = activePlayer.getName();
        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());

        boolean onCard = playerLocation.isActingOnScene(playerName);
        boolean success = diceOutcome + activePlayer.getPracticeToken() >= playerLocation.getSceneBudget();

        int creditsEarned = 0;
        int dollarsEarned = 0;
        if (success) {
            playerLocation.removeShotToken();
            if (onCard) {
                activePlayer.addCredits(2);
                creditsEarned = 2;
            } else {
                activePlayer.addCredits(1);
                activePlayer.addDollars(1);
                creditsEarned = 1;
                dollarsEarned = 1;
            }
        } else {
            if (!onCard) {
                activePlayer.addDollars(1);
                dollarsEarned = 1;
            }
        }

        gameState.setCurrentPlayerDoneTrue();
        boolean sceneFinished = playerLocation.getCurrentShotTokens() == 0;
        return new ActOutcome(success, creditsEarned, dollarsEarned, sceneFinished);
    }

    /**
     * change appropriate states for active player performing a rehearse action, assumes player can rehearse
     */
    public void rehearse() {
        activePlayer.addPracticeToken();
        gameState.setCurrentPlayerDoneTrue();
    }

    /**
     * change appropriate states for active player to upgrade, assumes upgrade is valid
     *
     * @param newActingRank rank player is upgrading to
     * @param currencyType  currency type used for upgrade
     */
    public void upgrade(int newActingRank, CurrencyType currencyType) {
        CastingOffice office = (CastingOffice) board.getLocation("office");
        int cost = office.getCost(newActingRank, currencyType);
        activePlayer.upgrade(newActingRank, currencyType, cost);
    }

    /**
     * change appropriate states for active player ending turn, assumes player can end turn
     *
     * @return the new active player
     */
    public Player endTurn() {
        Player nextPlayer = gameState.changeActivePlayer();
        activePlayer = nextPlayer;
        return nextPlayer;
    }

    /**
     * finishes a scene, distributes bonuses to players, resets player role status, removes players from
     * roles on location, set scene status to completed and decreases total number of active scenes.
     *
     * @return ScenePayout object, contains if bonus was paid and a map of player earnings
     */
    public ScenePayout finishScene() {
        boolean bonusPayed = false;
        Map<String, Integer> earnings = new HashMap<>();

        SetLocation playerLocation = (SetLocation) board.getLocation(activePlayer.getLocation());
        List<String> playersOnCard = playerLocation.playersActingOnScene();
        List<Player> playersOffCard = gameState.getPlayers(playerLocation.playersActingOnLocation());

        // If players are working on scene payout bonus
        if (playersOnCard.size() != 0) {
            bonusPayed = true;
            Dice dice = new Dice();
            int budget = playerLocation.getSceneBudget();
            Queue<Player> orderedPlayersOnCard = gameState.getPlayersInOrder(playersOnCard);
            // determine bonuses for players working starring roles (on scene)
            for (int i = 0; i < budget; i++) {
                int diceOutcome = dice.roll();
                Player player = orderedPlayersOnCard.remove();
                player.addDollars(diceOutcome);
                orderedPlayersOnCard.add(player);

                String playerName = player.getName();
                if (earnings.containsKey(playerName)) {
                    int currentEarnings = earnings.get(playerName);
                    earnings.put(playerName, currentEarnings + diceOutcome);
                } else {
                    earnings.put(playerName, diceOutcome);
                }
            }
            // payout bonus to extras (off scene)
            for (Player player : playersOffCard) {
                int roleRank = playerLocation.getLocationRoleRank(player.getName());
                player.addDollars(roleRank);
                earnings.put(player.getName(), roleRank);
            }
            // rest role status for players on scene
            for (Player player : orderedPlayersOnCard) {
                player.setWorkingRole(false);
                player.resetPracticeTokens();
            }
        }
        // rest role status for players no on scene
        for (Player player : playersOffCard) {
            player.setWorkingRole(false);
            player.resetPracticeTokens();
        }

        playerLocation.emptyLocationRoles();
        playerLocation.setSceneStatus(SceneStatus.COMPLETED);
        gameState.decrementActiveScenes();
        return new ScenePayout(bonusPayed, earnings);
    }

    /**
     * executes commands to end a day
     *
     * @return return true if day was last day, otherwise false
     */
    public boolean endDay() {
        boolean lastDayEnded = gameState.endDay();
        if (!lastDayEnded) {
            board.dealNewScenes(gameState.getSceneOrder());
        }
        return lastDayEnded;
    }
}
