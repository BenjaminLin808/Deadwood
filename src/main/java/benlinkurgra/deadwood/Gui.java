package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Coordinates;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class Gui extends JFrame {
    private Queue<Player> playerOrder;
    private JLayeredPane bPane;
    private int playerNum;
    private Map<String, JLabel> players = new HashMap<>();
    private Board board;
    private Map<String, JLabel> cardsLocations = new HashMap<>();

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
            JLabel playerDice = new JLabel();
            ImageIcon pIcon = new ImageIcon("src/main/images/dice/" + currPlayer.getName() + currPlayer.getActingRank() + ".png");
            playerDice.setIcon(pIcon);
            playerDice.setBounds(20 + (i * 230), 900, pIcon.getIconWidth(), pIcon.getIconHeight());
            playerDice.setVisible(true);
            bPane.add(playerDice, Integer.valueOf(3));

            JLabel mLabel = new JLabel("Player Name: " + currPlayer.getName());
            mLabel.setBounds(70 + (i * 230), 880, 180, 60);
            bPane.add(mLabel, Integer.valueOf(2));

            JLabel rank = new JLabel("Player Rank: " + currPlayer.getActingRank());
            rank.setBounds(70 + (i * 230), 900, 180, 60);
            bPane.add(rank, Integer.valueOf(2));

            JLabel dollars = new JLabel("Player Dollars: " + currPlayer.getDollars());
            dollars.setBounds(70 + (i * 230), 920, 180, 60);
            bPane.add(dollars, Integer.valueOf(2));

            JLabel credits = new JLabel("Player Credits: " + currPlayer.getCredits());
            credits.setBounds(70 + (i * 230), 940, 180, 60);
            bPane.add(credits, Integer.valueOf(2));
        }
    }

    public void setUp(Map<String, Location> locations) {
        Set<String> keys = locations.keySet();

        for (String key : keys) {
            JLabel value = new JLabel();
            cardsLocations.put(key, value);
            if (!key.equals("office") && !key.equals("trailer")) {
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
}

