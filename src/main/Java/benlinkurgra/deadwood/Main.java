package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.Action;
import benlinkurgra.deadwood.controller.ActionProvider;
import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.controller.LocationProvider;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.Scene;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;
import benlinkurgra.deadwood.readxml.ParseBoardXML;
import benlinkurgra.deadwood.readxml.ParseCardXML;

import java.util.Map;
import java.util.Queue;

public class Main {

    private static ActionProvider actionProvider;
    private static LocationProvider locationProvider;
    private static Board board;
    private static GameState gameState;
    private static Display display;

    private static Map<String, Location> getBoardComponents() {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
            Map<String, Location> locations = boardXML.getLocations("src/main/resources/board.xml");
            return locations;
//            board = new Board(locations);
        } catch (Exception e) {
            System.exit(-1);
            return null;
        }
    }

    private static Queue<Scene> getSceneComponents() {
        try {
            ParseCardXML cardXML = new ParseCardXML();
            return cardXML.getScenes("src/main/resources/cards.xml");
        } catch (Exception e) {
            System.exit(-1);
        }
        return null;
    }

    private static void startGame() {
        // setup components that don't require player interaction
        display = new Display();
        GameInitializer gameInitializer = new GameInitializer(display);
        Map<String, Location> locations = getBoardComponents();
        Queue<Scene> scenes = getSceneComponents();
        board = new Board(locations, scenes);

        // start game and get starting game parameters
        gameInitializer.startGame();
        int numPlayers = gameInitializer.getNumberPlayers();
        Queue<Player> players = gameInitializer.determinePlayerOrder(numPlayers);
        if (numPlayers == 2 || numPlayers == 3) {
            gameState = new GameState(3, scenes, players);
        } else {
            gameState = new GameState(scenes, players);
        }

        Player activePlayer = gameState.getActivePlayer();
        actionProvider = new ActionProvider(display, activePlayer, board, gameState);
//        locationProvider = new LocationProvider(display, board);
    }

    private static Action getAction() {
        actionProvider.provideActivePlayer();
        actionProvider.provideActionsWithHighlighting();
        return actionProvider.parseActionRequest();
    }


    public static void main(String[] args) {
        startGame();
        while (true) {
            Action action = getAction();
            actionProvider.attemptAction(action);
        }
    }
}