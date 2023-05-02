package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.readxml.ParseBoardXML;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;
import java.util.Queue;

public class Main {
    private static Board board;
    private static GameState gameState;
    private static Display display;

    private static void getGameComponents(String boardFilename, String cardFilename) {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
//            locations = boardXML.getLocations(boardFilename);
//            board = new Board(locations);
            // TODO add cards
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private static void startGame() {
        display = new Display();
        GameInitializer gameInitializer = new GameInitializer(display);
        gameInitializer.startGame();
        int numPlayers = gameInitializer.getNumberPlayers();
        Queue<Player> players = gameInitializer.determinePlayerOrder(numPlayers);
        //GET SCENE ORDER
        //CREATE gameState object
    }

    public static void main(String[] args) {
        getGameComponents("src/main/resources/board.xml", "src/main/resources/cards.xml");
        startGame();
    }
}