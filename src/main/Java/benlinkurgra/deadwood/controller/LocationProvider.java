package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.location.*;
import benlinkurgra.deadwood.readxml.ParseBoardXML;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Map;

public class LocationProvider extends DisplayController {

    ParseBoardXML boardData = new ParseBoardXML();

    public LocationProvider(Display display, ParseBoardXML boardData) {
        super(display);
        this.boardData = boardData;
    }

    public static void main(String[] args) throws ParserConfigurationException {
        LocationProvider test = new LocationProvider(new Display(), new ParseBoardXML());
        ArrayList<String> testNeighbors = test.neighborLocations("Saloon", "D:\\IdeaProjects\\Deadwood\\src\\main\\resources\\board.xml");
        System.out.println(testNeighbors);
    }

    /**
     * Using hashmap to find the neighbors of the player's current location
     * @param location
     * @param filePath
     * @return an array of current location's neighbors
     * @throws ParserConfigurationException
     */
    public ArrayList<String> neighborLocations(String location, String filePath) throws ParserConfigurationException {
        Map<String, Location> neighbors = boardData.getLocations(filePath);
        return neighbors.get(location).getNeighbors();
    }

    /**
     * Updating the scene status of the location to REVEALED or COMPLETED or HIDDEN
     * @param sceneStatus
     * @param setLocation
     */
    public void updateSetLocationStatus(SceneStatus sceneStatus, SetLocation setLocation){
        System.out.println("update the location status to HIDDEN, COMPLETED, or REVEALED");
        setLocation.setSceneStatus(sceneStatus);
    }

    /**
     * Updating the scenes at a location
     * @param scene
     * @param setLocation
     */
    public void updateSceneAtLocation(Scene scene, SetLocation setLocation){
        System.out.println("update the scenes at a location");
        setLocation.setScene(scene);
    }

    /**
     * Get current locations available roles
     * @param setLocation
     * @return
     */
    public Roles provideRolesAtLocation(SetLocation setLocation){
        System.out.println("return the roles available at this location");
        return setLocation.getRoles();
    }
}
