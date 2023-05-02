package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.readxml.ParseBoardXML;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;

public class Main {
    private static Map<String, Location> locations;
    private static GameState gameState;
    private static Display display;

    private static void getGameComponents(String boardFilename, String cardFilename) {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
            locations = boardXML.getLocations(boardFilename);
            //TODO add cards
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private void startGame() {
        display = new Display();

    }

    public static void main(String[] args) {
        getGameComponents("src/main/resources/board.xml", "src/main/resources/cards.xml");
        Display display = new Display();
        GameInitializer gameInitializer = new GameInitializer(display);
        int numPlayers = gameInitializer.startGame();
    }
}