package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.location.*;
import benlinkurgra.deadwood.model.Board;

public class LocationProvider extends DisplayController {
    Board boardData;

    public LocationProvider(Display display, Board boardData) {
        super(display);
        this.boardData = boardData;
    }

//    public ArrayList<String> locationNeighbors(String location)  {
//        return boardData.getLocation(location).getNeighbors();
//    }

    public Roles locationRoles(SetLocation setLocation){
        System.out.println("return the roles available at this location");
        return setLocation.getRolesOnLocation();
    }

    public Scene locationScene(SetLocation setLocation){
        return setLocation.getScene();
    }

    public SceneStatus locationSceneStatus(SetLocation setLocation){
        return setLocation.getSceneStatus();
    }

    public int locationCurrShotTokens(SetLocation setLocation){
        return setLocation.getCurrentShotTokens();
    }

    public int locationMaxShotTokens(SetLocation setLocation){
        return setLocation.getMaxShotTokens();
    }


}
