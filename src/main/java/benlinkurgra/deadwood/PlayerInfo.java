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
        for(Player player : playerOrder){
//            players.add(new JLabel());
//            ImageIcon pIcon = new ImageIcon("src/main/images/dice/b1.png");
//            players.setIcon(pIcon);
//            //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
//            playerlabel.setBounds(114,227,46,46);
//            playerlabel.setVisible(false);
//            bPane.add(playerlabel,Integer.valueOf(3));
        }
    }
}

