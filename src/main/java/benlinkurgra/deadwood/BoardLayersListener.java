package benlinkurgra.deadwood;
/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import benlinkurgra.deadwood.controller.GameInitializer;
import benlinkurgra.deadwood.controller.GuiInitializer;
import benlinkurgra.deadwood.model.Action;
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


        // Create the Menu for action buttons
//        mLabel = new JLabel("MENU");
//        mLabel.setBounds(icon.getIconWidth()+40,0,150,20);
//        bPane.add(mLabel, Integer.valueOf(2));
//
//        // Create Action buttons
//        Act();
//        Rehearse();
//        Move();
//        TakeARole();
//        Upgrade();
//        EndTurn();
//
//        // Place the action buttons in the top layer
//        bPane.add(bAct, Integer.valueOf(2));
//        bPane.add(bRehearse, Integer.valueOf(2));
//        bPane.add(bMove, Integer.valueOf(2));
//        bPane.add(bTakeARole, Integer.valueOf(2));
//        bPane.add(bUpgrade, Integer.valueOf(2));
//        bPane.add(bEndTurn, Integer.valueOf(2));
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

    public void Act(){
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setBounds(1210, 30,150, 60);
        bAct.addMouseListener(new boardMouseListener());
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
        bMove.setBounds(1210,170,150, 60);
        bMove.setEnabled(actionModel.canMove().isValid());
        bMove.addMouseListener(new boardMouseListener());
    }

    public void TakeARole(){
        bTakeARole = new JButton("Take A Role");
        bTakeARole.setBackground(Color.white);
        bTakeARole.setBounds(1210,240,150, 60);
        bTakeARole.addMouseListener(new boardMouseListener());
    }

    public void Upgrade(){
        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setBounds(1210,310,150, 60);
        bUpgrade.addMouseListener(new boardMouseListener());
    }

    public void EndTurn(){
        bEndTurn = new JButton("End Turn");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setBounds(1210,380,150, 60);
        bEndTurn.addMouseListener(new boardMouseListener());
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