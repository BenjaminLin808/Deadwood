package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.Player;
import benlinkurgra.deadwood.location.Location;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GameInitializer {
    private final Display display;
    public GameInitializer(Display display) {
        this.display = display;
    }
    public int startGame() {
        return display.startingDisplay();
    }

    public void configureBoard(Map<Location, List<Location>> layout){
        System.out.println("based on the map from Board class, we will set up the board");
    }
    public void determineEndDay(int numPlayers){
        System.out.println("determine the game time based on the player number");
    }
    public void determineSceneOrder(){
        System.out.println("determine what order the scenes would be placed");
    }
    public void createPlayer(String playerName, int numPlayers){
        System.out.println("this should return a player object");
    }
    public void determinePlayerOrder(Queue<Player> player){
        System.out.println("the players would get their turn based on this queue order");
    }
}
