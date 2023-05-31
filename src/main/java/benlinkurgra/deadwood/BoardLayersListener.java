package benlinkurgra.deadwood;
/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import benlinkurgra.deadwood.location.Coordinates;
import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.RoleData;
import benlinkurgra.deadwood.location.SetLocation;
import benlinkurgra.deadwood.model.Action;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<JButton> locationButtons;

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
        ImageIcon icon = new ImageIcon("src/main/images/board.jpg");
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, Integer.valueOf(0));

        // Set the size of the GUI
        setSize(icon.getIconWidth() + 200, icon.getIconHeight());

    }

    public void createButtons() {
        ImageIcon icon = new ImageIcon("src/main/images/board.jpg");
        // Create the Menu for action buttons
        mLabel = new JLabel("MENU");
        mLabel.setBounds(icon.getIconWidth() + 40, 0, 150, 20);
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

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public void refreshButtons() {
        // Remove existing buttons from the container
        bPane.remove(bAct);
        bPane.remove(bRehearse);
        bPane.remove(bMove);
        bPane.remove(bTakeARole);
        bPane.remove(bUpgrade);
        bPane.remove(bEndTurn);

        // Create and set up new buttons
        Act();
        Rehearse();
        Move();
        TakeARole();
        Upgrade();
        EndTurn();

        // Place the new buttons in the top layer
        bPane.add(bAct, Integer.valueOf(2));
        bPane.add(bRehearse, Integer.valueOf(2));
        bPane.add(bMove, Integer.valueOf(2));
        bPane.add(bTakeARole, Integer.valueOf(2));
        bPane.add(bUpgrade, Integer.valueOf(2));
        bPane.add(bEndTurn, Integer.valueOf(2));

        // Repaint the container to reflect the changes
        bPane.revalidate();
        bPane.repaint();
    }
    public void Act() {
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setEnabled(actionModel.canAct().isValid());
        bAct.setBounds(1210, 30, 150, 60);
//        bAct.addMouseListener(new boardMouseListener());
        bAct.addActionListener(e -> {
            System.out.println("Act is Selected\n");
        });
    }

    public void Rehearse() {
        bRehearse = new JButton("REHEARSE");
        bRehearse.setBackground(Color.white);
        bRehearse.setEnabled(actionModel.canRehearse().isValid());
        bRehearse.setBounds(1210, 100, 150, 60);
//        bRehearse.addMouseListener(new boardMouseListener());
        bRehearse.addActionListener(e -> {
            System.out.println("Rehearse is Selected\n");
        });
    }


    public void Move() {
        System.out.println("called move, active player: " + actionModel.getActivePlayer().getName());
        bMove = new JButton("MOVE");
        locationButtons = new ArrayList<>();
        bMove.setBackground(Color.white);
        bMove.setEnabled(actionModel.canMove().isValid());

        bMove.setBounds(1210, 170, 150, 60);
        bMove.addActionListener(e -> {
            System.out.println("Move is Selected\n");
            Player activePlayerInfo = actionModel.getActivePlayer();
            ArrayList<String> neighbors = actionModel.getBoard().getLocation(activePlayerInfo.getLocation()).getNeighbors();
            for (int i = 0; i < neighbors.size(); i++) {
                String neighbor = neighbors.get(i);
                JButton bLocation = new JButton(neighbor);
                bLocation.setBackground(Color.white);
                bLocation.setBounds(1210 + (i + 1) * 140, 170, 150, 60);
                bPane.add(bLocation);
                locationButtons.add(bLocation);
                bLocation.addActionListener(f -> {
                    boolean sceneReveled = actionModel.move(neighbor);
                    if (sceneReveled) {
                        gui.revealScene(neighbor);
                    }
                    bMove.setEnabled(actionModel.canMove().isValid());
                    JLabel activePlayer = gui.getPlayers().get(activePlayerInfo.getName());
                    Location location = board.getLocation(neighbor);
                    location.freePlayerPosition(activePlayerInfo.getName());
                    try {
                        Coordinates locationCoordinates = location.placePlayerOnLocation(activePlayerInfo.getName());
                        activePlayer.setBounds(
                                locationCoordinates.getX(),
                                locationCoordinates.getY(),
                                locationCoordinates.getWidth(),
                                locationCoordinates.getHeight());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        System.exit(1);
                    }
                    for (JButton button : locationButtons) {
                        button.setVisible(false);
                    }
                    refreshButtons();
                });
            }
        });
    }

    public void TakeARole() {
        bTakeARole = new JButton("Take A Role");
        bTakeARole.setBackground(Color.white);
        bTakeARole.setEnabled(actionModel.canTakeRole().isValid());
        bTakeARole.setBounds(1210, 240, 150, 60);
        bTakeARole.addActionListener(e -> {
            System.out.println("TakeARole is Selected\n");
            Player activePlayerInfo = actionModel.getActivePlayer();
            SetLocation activePlayerLocation = (SetLocation) board.getLocation(actionModel.getActivePlayer().getLocation());
            int playerRank = actionModel.getActivePlayer().getActingRank();
            List<RoleData> roleList = activePlayerLocation.getAllAvailableRoles(playerRank);
            ArrayList<JButton> roleButtons = new ArrayList<>();
            for (int i = 0; i < roleList.size(); i++) {
                String roleName = roleList.get(i).getName();
                Boolean onCard = roleList.get(i).getOnCard();
                RoleData roleData = roleList.get(i);
                JButton bRole = new JButton(roleName);
                bRole.setBackground(Color.white);
                bRole.setBounds(1400, 240 + (i)*70, 150, 60);
                bPane.add(bRole);
                roleButtons.add(bRole);
                bRole.addActionListener(r -> {
                    actionModel.takeRole((SetLocation) board.getLocation(actionModel.getActivePlayer().getLocation()), roleName, onCard);
                    JLabel activePlayer = gui.getPlayers().get(activePlayerInfo.getName());
                    try {
                        Coordinates roleCoordinates = roleData.getCoordinates();
                        activePlayer.setBounds(
                                roleCoordinates.getX(),
                                roleCoordinates.getY(),
                                roleCoordinates.getWidth(),
                                roleCoordinates.getHeight());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        System.exit(1);
                    }
                    for (JButton button : roleButtons) {
                        button.setVisible(false);
                    }
                    refreshButtons();
                });
            }
        });
    }

    public void Upgrade() {
        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setEnabled(actionModel.canUpgrade().isValid());
        bUpgrade.setBounds(1210, 310, 150, 60);
//        bUpgrade.addMouseListener(new boardMouseListener());
        bUpgrade.addActionListener(e -> {
            System.out.println("Upgrade is Selected\n");
        });
    }

    public void EndTurn() {
        bEndTurn = new JButton("End Turn");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setEnabled(actionModel.canEndTurn().isValid());
        bEndTurn.setBounds(1210, 380, 150, 60);
        bEndTurn.addActionListener(e -> {
            actionModel.endTurn();
            resetMoveButton();
        });
    }

    /**
     * Clears neighbour buttons and determines rather Move button should be active
     */
    private void resetMoveButton() {
        System.out.println("Called reset");
        for (JButton button : locationButtons) {
            button.setVisible(false);
            bPane.remove(button);
        }
        locationButtons.clear();
        bMove.setEnabled(actionModel.canMove().isValid());
    }

    public JLayeredPane getbPane() {
        return bPane;
    }


    // This class implements Mouse Events

    class boardMouseListener implements MouseListener {

        // Code for the different button clicks
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == bAct) {
                playerlabel.setVisible(true);
                System.out.println("Acting is Selected\n");
            }
//            else if (e.getSource()== bRehearse){
//                System.out.println("Rehearse is Selected\n");
//            }
            else if (e.getSource() == bMove) {
                System.out.println("TEST Move is Selected\n");
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