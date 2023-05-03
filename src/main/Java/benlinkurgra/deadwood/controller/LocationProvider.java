package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.location.Scene;
import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Board;

import java.util.Queue;

public class LocationProvider extends DisplayController {

    Board board;

    public LocationProvider(Display display, Board board) {
        super(display);
        this.board = board;
    }

    public void updateSetLocationStatus(String location){
        System.out.println("update the location status to HIDDEN, COMPLETED, or REVEALED");
        SetLocation setLocation = (SetLocation) board.getLocation(location);
        setLocation.setSceneStatus(SceneStatus.COMPLETED);
    }
    public void updateSceneAtLocation(String location){
        System.out.println("update the scenes at a location");
        SetLocation setLocation = (SetLocation) board.getLocation(location);
    }
    public void provideRolesAtLocation(String location){
        System.out.println("return the roles available at this location");
        SetLocation setLocation = (SetLocation) board.getLocation(location);
        setLocation.getAllAvailableRoles();
    }
}
