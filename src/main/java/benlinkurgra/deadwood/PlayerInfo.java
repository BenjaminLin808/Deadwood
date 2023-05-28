package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Scene;
import benlinkurgra.deadwood.model.Board;
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
        Queue<Player> playersOrder = new LinkedList<>(playerOrder);
        for(int i = 0; i < playerOrder.size(); i++){
            Player currPlayer = playersOrder.poll();
            players.add(new JLabel());
            ImageIcon pIcon = new ImageIcon("src/main/images/dice/"+ currPlayer.getName()+currPlayer.getActingRank()+".png");
            players.get(i).setIcon(pIcon);
            players.get(i).setBounds(20+(i * 230) ,900,pIcon.getIconWidth(),pIcon.getIconHeight());
            players.get(i).setVisible(true);
            bPane.add(players.get(i),Integer.valueOf(3));

            JLabel mLabel = new JLabel("Player Name: "+ currPlayer.getName());
            mLabel.setBounds(70+(i*230),880, 180, 60);
            bPane.add(mLabel, Integer.valueOf(2));

            JLabel rank = new JLabel("Player Rank: " + currPlayer.getActingRank());
            rank.setBounds(70+(i*230),900, 180, 60);
            bPane.add(rank, Integer.valueOf(2));

            JLabel dollars = new JLabel("Player Dollars: " + currPlayer.getDollars());
            dollars.setBounds(70+(i*230),920, 180, 60);
            bPane.add(dollars, Integer.valueOf(2));

            JLabel credits = new JLabel("Player Credits: " + currPlayer.getCredits());
            credits.setBounds(70+(i*230),940, 180, 60);
            bPane.add(credits, Integer.valueOf(2));
        }
    }

}

