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
        ArrayList<String> testNeighbors = test.locationNeighbors("Saloon", "D:\\IdeaProjects\\Deadwood\\src\\main\\resources\\board.xml");
        System.out.println(testNeighbors);
    }

    public ArrayList<String> locationNeighbors(String location, String filePath) throws ParserConfigurationException {
        Map<String, Location> neighbors = boardData.getLocations(filePath);
        return neighbors.get(location).getNeighbors();
    }

    public Roles locationRoles(SetLocation setLocation){
        System.out.println("return the roles available at this location");
        return setLocation.getRoles();
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
