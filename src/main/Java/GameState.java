import benlinkurgra.deadwood.location.Scene;

import java.util.PriorityQueue;
import java.util.Queue;

public class GameState {
    int currDay;
    int endDay;
    int activeScenes;
    int numPlayers;
    boolean currentPlayerDone;
    Queue<Scene> sceneOrder = new PriorityQueue<Scene>();
    Queue<Player> playerOrder = new PriorityQueue<Player>();


    public GameState(int numPlayers, Queue<Scene> sceneOrder, Queue<Player> playerOrder) {
        this.numPlayers = numPlayers;
        this.sceneOrder = sceneOrder;
        this.playerOrder = playerOrder;
    }

    public void distributedScenes(){
        System.out.println("Distrubuting scenes");
    }

    public void endTurn(){
        System.out.println("Current player's turn is over");
    }


    public void endDay(){
        System.out.println("Current day is over, resetting the board");
    }

    public void scoreGame(){
        System.out.println("get players scores");
    }


    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getCurrDay() {
        return currDay;
    }

    public void setCurrDay(int currDay) {
        this.currDay = currDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getActiveScenes() {
        return activeScenes;
    }

    public void setActiveScenes(int activeScenes) {
        this.activeScenes = activeScenes;
    }

    public boolean isCurrentPlayerDone() {
        return currentPlayerDone;
    }

    public void setCurrentPlayerDone(boolean currentPlayerDone) {
        this.currentPlayerDone = currentPlayerDone;
    }

    public Queue<Scene> getSceneOrder() {
        return sceneOrder;
    }

    public void setSceneOrder(Queue<Scene> sceneOrder) {
        this.sceneOrder = sceneOrder;
    }

    public Queue<Player> getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(Queue<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }






}


