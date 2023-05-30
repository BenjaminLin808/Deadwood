package benlinkurgra.deadwood;
import benlinkurgra.deadwood.controller.ActionsEnum;
import benlinkurgra.deadwood.controller.ActionProvider;
import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.controller.GuiInitializer;
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
    private static Board board;
    private static GameState gameState;
    /**
     * Get XML board componenets
     *
     * @return Map of locations where keys are location name and values are location
     */
    private static Map<String, Location> getBoardComponents() {
        try {
            ParseBoardXML boardXML = new ParseBoardXML();
            Map<String, Location> locations = boardXML.getLocations(Main.class.getResource("/board.xml"));
            return locations;
        } catch (Exception e) {
            System.exit(-1);
            return null;
        }
    }
    /**
     * Get XML scene components
     *
     * @return shuffled Queue of scenes
     */
    private static Queue<Scene> getSceneComponents() {
        try {
            ParseCardXML cardXML = new ParseCardXML();
            return cardXML.getScenes(Main.class.getResource("/cards.xml"));
        } catch (Exception e) {
            System.exit(-1);
        }
        return null;
    }
    /**
     * executes commands for starting game
     */
    private static void startGame() {
        // setup components that don't require player interaction
        Display display = new Display();
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
    }

    private static void startGameGui(){
        BoardLayersListener boardLayersListener = new BoardLayersListener();
        boardLayersListener.setVisible(true);
        GuiInitializer guiInitializer = new GuiInitializer();

        Map<String, Location> locations = getBoardComponents();
        Queue<Scene> scenes = getSceneComponents();
        board = new Board(locations, scenes);

        // start game and get starting game parameters
        int numPlayers = guiInitializer.getNumberPlayers(boardLayersListener);
        String[] playerNames = new String[] { "b", "c", "g", "o", "p", "r", "v", "w", "y" };
        Queue<Player> players = guiInitializer.determinePlayerOrder(numPlayers, playerNames);
        if (numPlayers == 2 || numPlayers == 3) {
            gameState = new GameState(3, scenes, players);
        } else {
            gameState = new GameState(scenes, players);
        }

        Gui gui = new Gui(numPlayers, players, boardLayersListener.getbPane(), board);
        gui.playPlayerInfo();
        gui.setUp(locations);
    }
    /**
     * executes commands to get an action from a player
     *
     * @return action performed
     */
    private static ActionsEnum getAction() {
        actionProvider.provideActivePlayer();
        actionProvider.provideActionsWithHighlighting();
        return actionProvider.parseActionRequest();
    }
    /**
     * executes commands needed for player to take a turn
     */
    private static void takeTurn() {
        boolean turnNoOver = true;
        while (turnNoOver) {
            ActionsEnum actionType = getAction();
            actionProvider.performAction(actionType);
            if (actionType == ActionsEnum.END_TURN) {
                turnNoOver = false;
            }
        }
    }
    /**
     * executes commands to end a day
     *
     * @return return true if day was last day, otherwise false
     */
    private static boolean endDay() {
        actionProvider.endDay();
        boolean lastDayEnded = gameState.endDay();
        if (!lastDayEnded) {
            board.dealNewScenes(gameState.getSceneOrder());
        }
        return lastDayEnded;
    }

    /**
     * play a game of deadwood
     *
     * @param args arguments, NONE EXPECTED
     */
    public static void main(String[] args) {
        startGameGui();
        if (args.length != 1) {
            System.out.println("Error, unexpected number of arguments.");
        } else if (args[0].equals("term")) {
        startGame(); // new for gui
        boolean gameNotOver = true;
        while (gameNotOver) {
            takeTurn();
            if (gameState.getActiveScenes() == 1) {
                boolean lastDay = endDay();
                if (lastDay) {
                    gameNotOver = false;
                }
            }
        }
        actionProvider.endGame();
        } else if (args[0].equals("gui")) {
            // run gui application
            startGameGui();

        } else {
            System.out.println("Error, invalid input.");
        }
    }
}