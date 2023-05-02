package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.ActionProvider;
import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;
import benlinkurgra.deadwood.readxml.ParseBoardXML;

import java.util.Queue;

public class Main {

    private static ActionProvider actionProvider;
    private static Board board;
    private static GameState gameState;
    private static Display display;

    private static void getBoardComponents(String boardFilename) {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
//            locations = boardXML.getLocations(boardFilename);
//            board = new Board(locations);
        } catch (Exception e) {
            System.exit(-1);
        }
    }
    private static void getCardComponents(String cardFileName) {
//        try {
//            ParseCardXML cardXML = new ParseCardXML();
//
//        }
    }

    private static void startGame() {
        display = new Display();
        GameInitializer gameInitializer = new GameInitializer(display);
        gameInitializer.startGame();
        int numPlayers = gameInitializer.getNumberPlayers();
        Queue<Player> players = gameInitializer.determinePlayerOrder(numPlayers);
        //GET SCENE ORDER
        //SET gameState
    }

    public static void main(String[] args) {
        startGame();
//        while (gameState.getCurrDay() != gameState.getEndDay()) {
//        }
    }
}