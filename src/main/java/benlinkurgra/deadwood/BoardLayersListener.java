package benlinkurgra.deadwood;
/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.controller.GuiInitializer;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Action;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.lang.*;
import java.util.ArrayList;
import java.util.Queue;

public class BoardLayersListener extends JFrame {

    private Action actionModel;
    private Gui gui;
    private Board board;
    private GameState gameState;

    // JLabels
    JLabel boardlabel;
    JLabel cardlabel;
    JLabel playerlabel;
    JLabel mLabel;

    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bUpgrade;
    JButton bTakeARole;
    JButton bEndTurn;

    // JLayered Pane
    JLayeredPane bPane;


    // Constructor

    public BoardLayersListener() {

        // Set the title of the JFrame
        super("Deadwood");

        // Set the exit option for the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create the JLayeredPane to hold the display, cards, dice and buttons
        bPane = getLayeredPane();

        // Create the deadwood board
        boardlabel = new JLabel();
        ImageIcon icon =  new ImageIcon("src/main/images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, Integer.valueOf(0));

        // Set the size of the GUI
        setSize(icon.getIconWidth()+200,icon.getIconHeight());

    }

    public void createButtons() {
        ImageIcon icon =  new ImageIcon("src/main/images/board.jpg");
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth()+40,0,150,20);
        bPane.add(mLabel, Integer.valueOf(2));

        // Create Action buttons
        Act();
        Rehearse();
        Move();
        TakeARole();
        Upgrade();
        EndTurn();

        // Place the action buttons in the top layer
        bPane.add(bAct, Integer.valueOf(2));
        bPane.add(bRehearse, Integer.valueOf(2));
        bPane.add(bMove, Integer.valueOf(2));
        bPane.add(bTakeARole, Integer.valueOf(2));
        bPane.add(bUpgrade, Integer.valueOf(2));
        bPane.add(bEndTurn, Integer.valueOf(2));
    }

    public void setActionModel(Action actionModel) {
        this.actionModel = actionModel;
    }
    public void setGui(Gui gui){this.gui = gui;}
    public void setBoard(Board board) {this.board = board;}
    public void setGameState(GameState gameState) {this.gameState = gameState;}
    public void Act(){
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setEnabled(actionModel.canAct().isValid());
        bAct.setBounds(1210, 30,150, 60);
//        bAct.addMouseListener(new boardMouseListener());
        bAct.addActionListener(e -> {
            System.out.println("Act is Selected\n");
        });
    }

    public void Rehearse(){
        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setEnabled(actionModel.canRehearse().isValid());
        bRehearse.setBounds(1210,100,150, 60);
//        bRehearse.addMouseListener(new boardMouseListener());
        bRehearse.addActionListener(e -> {
            System.out.println("Rehearse is Selected\n");
        });
    }


    public void Move(){
        bMove = new JButton("MOVE");
        bMove.setBackground(Color.white);
        bMove.setEnabled(actionModel.canMove().isValid());
        bMove.setBounds(1210,170,150, 60);
//        bMove.addMouseListener(new boardMouseListener());
        bMove.addActionListener(e -> {
            System.out.println("Move is Selected\n");
            ArrayList<String> neighbors = actionModel.getBoard().getLocation(actionModel.getActivePlayer().getLocation()).getNeighbors();
            ArrayList<JButton> locationButtons = new ArrayList<>();
            for (int i = 0; i < neighbors.size(); i++) {
                String neighbor = neighbors.get(i);
                JButton bLocation = new JButton(neighbor);
                bLocation.setBackground(Color.white);
                bLocation.setBounds(1210+(i+1)*140, 170, 150, 60);
                bPane.add(bLocation);
                locationButtons.add(bLocation);
                bLocation.addActionListener(location -> {
                    actionModel.getActivePlayer().setLocation(neighbor);
                    JLabel activePlayer = gui.getPlayers().get(actionModel.getActivePlayer().getName());
                    SetLocation setLocation = (SetLocation) board.getLocation(neighbor);
                    int setLocationX = setLocation.getCoordinates().getX();
                    int setLocationY = setLocation.getCoordinates().getY();
                    int setLocationW = setLocation.getCoordinates().getWidth();
                    int setLocationH = setLocation.getCoordinates().getHeight();
                    activePlayer.setBounds(setLocationX, setLocationY, setLocationW, setLocationH);
                    for(JButton button : locationButtons){
                        button.setVisible(false);
                    }
                    bMove.setEnabled(false);
                });
            }
        });
    }

    public void TakeARole(){
        bTakeARole = new JButton("Take A Role");
        bTakeARole.setBackground(Color.white);
        bTakeARole.setEnabled(actionModel.canTakeRole().isValid());
        bTakeARole.setBounds(1210,240,150, 60);
//        bTakeARole.addMouseListener(new boardMouseListener());
        bTakeARole.addActionListener(e -> {
            System.out.println("TakeARole is Selected\n");

        });
    }

    public void Upgrade(){
        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setEnabled(actionModel.canUpgrade().isValid());
        bUpgrade.setBounds(1210,310,150, 60);
//        bUpgrade.addMouseListener(new boardMouseListener());
        bUpgrade.addActionListener(e -> {
            System.out.println("Upgrade is Selected\n");
        });
    }

    public void EndTurn(){
        bEndTurn = new JButton("End Turn");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setEnabled(actionModel.canEndTurn().isValid());
        bEndTurn.setBounds(1210,380,150, 60);
//        bEndTurn.addMouseListener(new boardMouseListener());
        bEndTurn.addActionListener(e -> {
            actionModel.endTurn();
        });
    }


    public JLayeredPane getbPane() {
        return bPane;
    }



    // This class implements Mouse Events

    class boardMouseListener implements MouseListener{

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {

            if (e.getSource()== bAct){
                playerlabel.setVisible(true);
                System.out.println("Acting is Selected\n");
            }
//            else if (e.getSource()== bRehearse){
//                System.out.println("Rehearse is Selected\n");
//            }
            else if (e.getSource()== bMove){
                System.out.println("Move is Selected\n");
            }
        }
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
    }


    public static void main(String[] args) {


    }
}