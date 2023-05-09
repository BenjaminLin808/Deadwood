package benlinkurgra.deadwood;

import benlinkurgra.deadwood.controller.Action;
import benlinkurgra.deadwood.controller.ActionProvider;
import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.controller.LocationProvider;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.Scene;
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

    private static void getBoardComponents(String boardFilename) {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
            Map<String, Location>  locations = boardXML.getLocations(boardFilename);
            board = new Board(locations);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private static Queue<Scene> getSceneComponents(String cardFilename){
        try {
            ParseCardXML cardXML = new ParseCardXML();
            return cardXML.getScenes(cardFilename);
        }catch (Exception e){
            System.exit(-1);
        }
        return null;
    }

    private static void startGame() {
        display = new Display();
        GameInitializer gameInitializer = new GameInitializer(display);
        gameInitializer.startGame();
        int numPlayers = gameInitializer.getNumberPlayers();
        Queue<Player> players = gameInitializer.determinePlayerOrder(numPlayers);
        Queue<Scene> scenes = getSceneComponents("src/main/resources/cards.xml");
        gameState = new GameState(numPlayers, scenes, players);
        getBoardComponents("src/main/resources/board.xml");
        Player activePlayer = gameState.getActivePlayer();
        actionProvider = new ActionProvider(display, activePlayer, board, gameState);
        locationProvider = new LocationProvider(display, board);
    }

    private static Action getAction() {
        actionProvider.provideActivePlayer();
        actionProvider.provideActionsWithHighlighting();
        return actionProvider.parseActionRequest();
    }



    public static void main(String[] args) {
        startGame();
        Action action = getAction();
        actionProvider.attemptAction(action);
    }
}