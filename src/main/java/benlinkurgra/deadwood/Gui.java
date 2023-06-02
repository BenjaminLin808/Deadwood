package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Coordinates;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class Gui extends JFrame {
    private Queue<Player> playerOrder;
    private JLayeredPane bPane;
    private int playerNum;
    private Map<String, JLabel> players = new HashMap<>();
    private Board board;
    private Map<String, JLabel> cardsLocations = new HashMap<>();

    private Map<String, PlayerBottomDisplay> playerBottomDisplayMap = new HashMap<>();

    private Map<String, ArrayList<JLabel>> shots = new HashMap<>();

    public Gui(int playerNum, Queue<Player> playerOrder, JLayeredPane bPane, Board board) {
        this.playerNum = playerNum;
        this.playerOrder = playerOrder;
        this.bPane = bPane;
        this.board = board;
    }

    public Map<String, JLabel> getPlayers() {
        return players;
    }

    public Map<String, JLabel> getCardsLocations() {
        return cardsLocations;
    }

    public Map<String, ArrayList<JLabel>> getShots() {
        return shots;
    }

    public void playPlayerInfo() {
        Queue<Player> playersOrder = new LinkedList<>(playerOrder);
        for (int i = 0; i < playerOrder.size(); i++) {
            Player currPlayer = playersOrder.poll();
            ImageIcon pIcon = null;
            try {
                assert currPlayer != null;
                URL iconURL = Gui.class.getResource("/images/dice/" + currPlayer.getName() + currPlayer.getActingRank() + ".png");
                assert iconURL != null;
                pIcon = new ImageIcon(iconURL);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

//            ImageIcon pIcon = new ImageIcon("src/main/images/dice/" + currPlayer.getName() + currPlayer.getActingRank() + ".png");

            PlayerBottomDisplay playerBottomDisplay = new PlayerBottomDisplay(
                    currPlayer.getName(),
                    currPlayer.getActingRank(),
                    currPlayer.getDollars(),
                    currPlayer.getCredits(),
                    pIcon
            );

            playerBottomDisplay.getPlayerDice().setBounds(
                    20 + (i * 230),
                    900,
                    pIcon.getIconWidth(),
                    pIcon.getIconHeight());
            playerBottomDisplay.getPlayerDice().setVisible(true);
            bPane.add(playerBottomDisplay.getPlayerDice(), Integer.valueOf(3));


            playerBottomDisplay.getNameLabel().setBounds(70 + (i * 230), 880, 180, 60);
            bPane.add(playerBottomDisplay.getNameLabel(), Integer.valueOf(2));

            playerBottomDisplay.getRankLabel().setBounds(70 + (i * 230), 900, 180, 60);
            bPane.add(playerBottomDisplay.getRankLabel(), Integer.valueOf(2));

            playerBottomDisplay.getDollarsLabel().setBounds(70 + (i * 230), 920, 180, 60);
            bPane.add(playerBottomDisplay.getDollarsLabel(), Integer.valueOf(2));

            playerBottomDisplay.getCreditsLabel().setBounds(70 + (i * 230), 940, 180, 60);
            bPane.add(playerBottomDisplay.getCreditsLabel(), Integer.valueOf(2));

            playerBottomDisplay.getPracticeLabel().setBounds(70 + (i * 230), 960, 180, 60);
            bPane.add(playerBottomDisplay.getPracticeLabel(), Integer.valueOf(2));

            playerBottomDisplayMap.put(currPlayer.getName(), playerBottomDisplay);
        }
    }

    /**
     * Updates the player dollar amount on bottom display
     *
     * @param playerName name of player for update
     * @param dollars amount of dollars player has
     */
    public void updateDollarsLabel(String playerName, int dollars) {
        PlayerBottomDisplay playerDisplay = playerBottomDisplayMap.get(playerName);
        if (playerDisplay != null) {
            playerDisplay.updateDollars(dollars);
        }
    }

    /**
     * Updates the player credit amount on bottom display
     *
     * @param playerName name of player for update
     * @param credits amount of credits player has
     */
    public void updateCreditsLabel(String playerName, int credits) {
        PlayerBottomDisplay playerDisplay = playerBottomDisplayMap.get(playerName);
        if (playerDisplay != null) {
            playerDisplay.updateCredits(credits);
        }
    }

    /**
     * Updates the player practice token amount on bottom display
     *
     * @param playerName name of player for update
     * @param tokens amount of practice tokens player has
     */
    public void updatePracticeLabel(String playerName, int tokens) {
        PlayerBottomDisplay playerDisplay = playerBottomDisplayMap.get(playerName);
        if (playerDisplay != null) {
            playerDisplay.updatePracticeTokens(tokens);
        }
    }

    public void updateRankLabel(String playerName, int rank){
        PlayerBottomDisplay playerDisplay = playerBottomDisplayMap.get(playerName);
        if(playerDisplay != null){
            playerDisplay.updateRank(rank);
        }
    }

    public void updatePlayerDice(String playerName, int rank){
        PlayerBottomDisplay playerDisplay = playerBottomDisplayMap.get(playerName);
        if(playerDisplay != null){
            playerDisplay.updatePlayerDice(playerName, rank);
            players.get(playerName).setIcon(new ImageIcon("src/main/images/dice/" + playerName + rank + ".png"));
        }
    }

    public void resetScenes() {
        ImageIcon cardback = new ImageIcon("src/main/images/cardback-small.jpg");
        for (JLabel cardLocation : cardsLocations.values()) {
            cardLocation.setVisible(true);
            cardLocation.setIcon(cardback);
        }

        for (ArrayList<JLabel> shots : shots.values()) {
            for (JLabel shot : shots) {
                shot.setVisible(true);
            }
        }
    }

    public void setUp(Map<String, Location> locations) {
        Set<String> keys = locations.keySet();

        for (String key : keys) {
            if (!key.equals("office") && !key.equals("trailer")) {
                JLabel value = new JLabel();
                cardsLocations.put(key, value);
                Coordinates locationCoordinates = board.getLocation(key).getCoordinates();
                int x = locationCoordinates.getX();
                int y = locationCoordinates.getY();
                int h = locationCoordinates.getHeight();
                int w = locationCoordinates.getWidth();
                ImageIcon cardback = new ImageIcon("src/main/images/cardback-small.jpg");
                cardsLocations.get(key).setIcon(cardback);
                cardsLocations.get(key).setBounds(x, y, w, h);
                bPane.add(cardsLocations.get(key), Integer.valueOf(2));

                SetLocation setLocation = (SetLocation) board.getLocation(key);
                shots.put(key, new ArrayList<>());

                for (int i = 0; i < setLocation.getMaxShotTokens(); i++) {
                    int shotX = setLocation.getTakeCoordinates().get(i).getX();
                    int shotY = setLocation.getTakeCoordinates().get(i).getY();
                    int shotH = setLocation.getTakeCoordinates().get(i).getHeight();
                    int shotW = setLocation.getTakeCoordinates().get(i).getWidth();
                    ImageIcon shot = new ImageIcon("src/main/images/shot.png");
                    JLabel shotValue = new JLabel();
                    shots.get(key).add(shotValue);
                    shots.get(key).get(i).setIcon(shot);
                    shots.get(key).get(i).setBounds(shotX, shotY, shotW, shotH);
                    bPane.add(shots.get(key).get(i), Integer.valueOf(2));
                }
            }
        }
        Queue<Player> playersOrder = new LinkedList<>(playerOrder);

        for (int i = 0; i < playerNum; i++) {
            Player currPlayer = playersOrder.poll();
            JLabel playerDice = new JLabel();
            players.put(currPlayer.getName(), playerDice);
            ImageIcon pIcon = new ImageIcon("src/main/images/dice/" + currPlayer.getName() + currPlayer.getActingRank() + ".png");
            players.get(currPlayer.getName()).setIcon(pIcon);
            try {
                Coordinates openCoordinate = board.getLocation("trailer").placePlayerOnLocation(currPlayer.getName());
                players.get(currPlayer.getName()).setBounds(
                        openCoordinate.getX(),
                        openCoordinate.getY(),
                        openCoordinate.getWidth(),
                        openCoordinate.getHeight());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            bPane.add(players.get(currPlayer.getName()), Integer.valueOf(3));
        }
    }

    public void revealScene(String locationName) {
        SetLocation location = (SetLocation) board.getLocation(locationName);
        String sceneFileName = location.getSceneFileName();
        ImageIcon sceneCard = new ImageIcon("src/main/images/cards/" + sceneFileName);
        cardsLocations.get(locationName).setIcon(sceneCard);
    }

    public void completeScene(String locationName) {
        cardsLocations.get(locationName).setVisible(false);
    }
}

