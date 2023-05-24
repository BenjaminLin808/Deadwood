package benlinkurgra.deadwood;

import benlinkurgra.deadwood.model.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class PlayerInfo extends JFrame {
    private Queue<Player> playerOrder;
    private JLayeredPane bPane;
    private int playerNum;
    private ArrayList<JLabel> players = new ArrayList<>();

    public PlayerInfo(int playerNum, Queue<Player> playerOrder, JLayeredPane bPane){
        this.playerNum = playerNum;
        this.playerOrder = playerOrder;
        this.bPane = bPane;
    }

    public void playPlayerInfo() {
        for(int i = 0; i < playerOrder.size(); i++){
            players.add(new JLabel());
            ImageIcon pIcon = new ImageIcon("src/main/images/dice/b1.png");
            players.get(i).setIcon(pIcon);
            players.get(i).setBounds(114 ,227 - (i * 10),pIcon.getIconWidth(),pIcon.getIconHeight());
            players.get(i).setVisible(true);
            bPane.add(players.get(i),Integer.valueOf(3));
        }
    }
}

