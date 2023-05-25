package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.BoardLayersListener;
import benlinkurgra.deadwood.Display;
import benlinkurgra.deadwood.model.Player;

import javax.swing.*;
import java.util.*;

public class GuiInitializer {

    public int getNumberPlayers(BoardLayersListener board) {
        int playerNum = 0;
        while (playerNum < 2 || playerNum > 8) {
            playerNum = Integer.parseInt(JOptionPane.showInputDialog(board, "How many players?"));
        }
        return playerNum;
    }
    public Queue<Player> determinePlayerOrder(int numPlayers, String[] dice) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(createPlayer(numPlayers, dice[i]));
        }
        Collections.shuffle(players);
        return new LinkedList<>(players);
    }
    private Player createPlayer(int numPlayers, String playerName) {
        if (numPlayers < 5) {
            return new Player(playerName);
        } else if (numPlayers == 5) {
            return new Player(playerName, 2, 1);
        } else if (numPlayers == 6) {
            return new Player(playerName, 4, 0);
        } else { // numPlayers == 7 || numPlayers == 8
            return new Player(playerName, 0, 2);
        }
    }
}
