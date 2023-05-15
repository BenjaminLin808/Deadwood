package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.GameState;

public class GameStateProvider extends DisplayController {

    public GameStateProvider(Display display) {
        super(display);
    }

    public void updateDay(){
        System.out.println("updating the current day in benlinkurgra.deadwood.GameState");
    }

    public void updateActivePlayer(){
        System.out.println("updating the current player in benlinkurgra.deadwood.GameState");
    }
}
