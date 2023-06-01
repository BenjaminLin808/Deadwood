package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Scene;
import benlinkurgra.deadwood.model.Player;

import java.util.*;

public class GameState {
    private final int endDay;
    private int currDay = 1;
    private int activeScenes = 10;
    private boolean currentPlayerDone;
    private Queue<Scene> sceneOrder;
    private Queue<Player> playerOrder;


    /**
     * GameState constructor for default starting days,to be used when player number is greater than 3
     *
     * @param sceneOrder  Queue of scenes
     * @param playerOrder Queue of players, represents turn order
     */
    public GameState(Queue<Scene> sceneOrder, Queue<Player> playerOrder) {
        this.endDay = 4;
        this.currentPlayerDone = false;
        this.sceneOrder = sceneOrder;
        this.playerOrder = playerOrder;
    }

    /**
     * GameState constructor for unique number of days
     *
     * @param endDay      ending day
     * @param sceneOrder  Queue of scenes
     * @param playerOrder Queue of players, represents turn order
     */
    public GameState(int endDay, Queue<Scene> sceneOrder, Queue<Player> playerOrder) {
        this.endDay = endDay;
        this.sceneOrder = sceneOrder;
        this.playerOrder = playerOrder;
    }

    public int getCurrDay() {
        return currDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getActiveScenes() {
        return activeScenes;
    }

    public boolean isCurrentPlayerDone() {
        return currentPlayerDone;
    }

    public Queue<Scene> getSceneOrder() {
        return sceneOrder;
    }

    public Queue<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * Transitions to next active player
     *
     * @return new active player
     */
    public Player changeActivePlayer() {
        playerOrder.add(playerOrder.remove());
        currentPlayerDone = false;
        return playerOrder.peek();
    }

    /**
     * Attempts to end the current day
     *
     * @return if day was last day returns true, otherwise returns false
     */
    public boolean endDay() {
        if (currDay == endDay) {
            return true;
        } else {
            incrementCurrDay();
            resetActiveScenes();
            for (Player player : playerOrder) {
                player.startNewDay();
            }
            return false;
        }
    }

    /**
     * increase current day by 1
     */
    public void incrementCurrDay() {
        ++this.currDay;
    }

    /**
     * decrease number of active scenes by 1
     */
    public void decrementActiveScenes() {
        --this.activeScenes;
    }
    /**
     * resets active scenes
     */
    private void resetActiveScenes() {
        this.activeScenes = 10;
    }

    /**
     * change current player state to done
     */
    public void setCurrentPlayerDoneTrue() {
        this.currentPlayerDone = true;
    }

    /**
     * find the player whose turn it is
     *
     * @return current active player
     */
    public Player getActivePlayer() {
        return playerOrder.peek();
    }

    /**
     * get a list of player objects related to list of names provided
     *
     * @param playerNames list of player names to find
     * @return Players related to provided names
     */
    public List<Player> getPlayers(List<String> playerNames) {
        List<Player> selectedPlayers = new ArrayList<>();

        // Iterate over the playerOrder queue to find players with names in the provided list
        for (Player player : playerOrder) {
            if (playerNames.contains(player.getName())) {
                selectedPlayers.add(player);
            }
        }
        return selectedPlayers;
    }

    /**
     * get a list of players in order of player rank
     *
     * @param playerNames list of player names to find
     * @return Players related to provided names, ordered by rank
     */
    public Queue<Player> getPlayersInOrder(List<String> playerNames) {
        List<Player> selectedPlayers = getPlayers(playerNames);

        // Sort the selected players by rank using a comparator
        selectedPlayers.sort(Comparator.comparingInt(Player::getActingRank).reversed());

        // Create a new queue with the sorted players and return it
        return new LinkedList<>(selectedPlayers);
    }

    public Player getPlayer(String name) {
        for (Player player : playerOrder) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        throw new IllegalArgumentException("Error, method getPlayer received illegal argument, " +
                name +
                " is not a player name");
    }
}


