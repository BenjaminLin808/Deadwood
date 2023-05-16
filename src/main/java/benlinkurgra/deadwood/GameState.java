package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Scene;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import java.util.*;

public class GameState {
    private int currDay = 1;
    private final int endDay;
    private int activeScenes = 10;
    private boolean currentPlayerDone;
    private Queue<Scene> sceneOrder;
    private Queue<Player> playerOrder;


    public GameState(Queue<Scene> sceneOrder, Queue<Player> playerOrder) {
        this.endDay = 4;
        this.currentPlayerDone = false;
        this.sceneOrder = sceneOrder;
        this.playerOrder = playerOrder;
    }

    public GameState(int endDay, Queue<Scene> sceneOrder, Queue<Player> playerOrder) {
        this.endDay = endDay;
        this.sceneOrder = sceneOrder;
        this.playerOrder = playerOrder;
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

    public int getCurrDay() {
        return currDay;
    }

    public void incrementCurrDay() {
        ++this.currDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getActiveScenes() {
        return activeScenes;
    }

    public void decrementActiveScenes() {
        --this.activeScenes;
    }

    public void resetActiveScenes() {
        this.activeScenes = 10;
    }

    public boolean isCurrentPlayerDone() {
        return currentPlayerDone;
    }

    public void setCurrentPlayerDoneTrue() {
        this.currentPlayerDone = true;
    }

    public Queue<Scene> getSceneOrder() {
        return sceneOrder;
    }

    public Queue<Player> getPlayerOrder() {
        return playerOrder;
    }

    public Player getActivePlayer() {
        return playerOrder.peek();
    }

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

    public Queue<Player> getPlayersInOrder(List<String> playerNames) {
        List<Player> selectedPlayers = getPlayers(playerNames);

        // Sort the selected players by rank using a comparator
        selectedPlayers.sort(Comparator.comparingInt(Player::getActingRank).reversed());

        // Create a new queue with the sorted players and return it
        return new LinkedList<>(selectedPlayers);
    }
}


