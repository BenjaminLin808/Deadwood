package benlinkurgra.deadwood;
/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import benlinkurgra.deadwood.location.*;
import benlinkurgra.deadwood.model.Action;
import benlinkurgra.deadwood.model.Board;
import benlinkurgra.deadwood.model.Player;

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.lang.*;
import java.util.*;
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
    JTextArea activityResultLabel;

    //JButtons
    JButton bAct;
    JButton bRehearse;
    JButton bMove;
    JButton bUpgrade;
    JButton bTakeARole;
    JButton bEndTurn;
    private ArrayList<JButton> locationButtons;
    private ArrayList<JButton> roleButtons = new ArrayList<>();
    private ArrayList<JButton> upgradeButtons = new ArrayList<>();

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
        ImageIcon icon = new ImageIcon(Deadwood.class.getResource("/images/board.jpg"));
        boardlabel.setIcon(icon);
        boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

        // Add the board to the lowest layer
        bPane.add(boardlabel, Integer.valueOf(0));
        activityResultLabel = new JTextArea();
        activityResultLabel.setBounds(icon.getIconWidth() + 10, 600, 220, 300);
        activityResultLabel.setFont(new Font(activityResultLabel.getFont().getFontName(), Font.PLAIN, 20));
        activityResultLabel.setLineWrap(true);
        activityResultLabel.setWrapStyleWord(true);
        activityResultLabel.setEditable(false);
        bPane.add(activityResultLabel);
        // Set the size of the GUI
        setSize(icon.getIconWidth() + 200, icon.getIconHeight());

    }

    public void resetDisplay() {
        activityResultLabel.setText("Active Player is " + actionModel.getActivePlayer().getName() + "\n");
    }

    public void createButtons() {
        ImageIcon icon = new ImageIcon(Deadwood.class.getResource("/images/board.jpg"));
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

    public void setEnabledAll() {
        bAct.setEnabled(actionModel.canAct().isValid());
        bRehearse.setEnabled(actionModel.canRehearse().isValid());
        resetMoveButton();
        resetTakeARoleButton();
        bUpgrade.setEnabled(actionModel.canUpgrade().isValid());
        bEndTurn.setEnabled(actionModel.canEndTurn().isValid());
    }

    public void Act() {
        bAct = new JButton("ACT");
        bAct.setBackground(Color.white);
        bAct.setEnabled(actionModel.canAct().isValid());
        bAct.setBounds(1210, 30, 150, 60);
        bAct.addActionListener(e -> {
            System.out.println("Act is Selected\n");
            int roll = new Dice().roll();
            Action.ActOutcome actOutcome = actionModel.act(roll);
            activityResultLabel.append("Acting roll result: " + roll);

            Player activePlayerInfo = actionModel.getActivePlayer();
            if (actOutcome.isActSuccess()) {
                activityResultLabel.append("\nsuccessful performed role\n");
                ArrayList<JLabel> shots = gui.getShots().get(activePlayerInfo.getLocation());
                for (JLabel shot : shots) {
                    if (shot.isVisible()) {
                        shot.setVisible(false);
                        break;
                    }
                }
            } else {
                activityResultLabel.append("\nfailed to perform role\n");
            }

            if (actOutcome.getCreditsEarned() != 0) {
                activityResultLabel.append("Earned " + actOutcome.getCreditsEarned() + " credit(s)\n");
                gui.updateCreditsLabel(activePlayerInfo.getName(), activePlayerInfo.getCredits());
            }
            if (actOutcome.getDollarsEarned() != 0) {
                activityResultLabel.append("Earned " + actOutcome.getDollarsEarned() + " dollar(s)\n");
                gui.updateDollarsLabel(activePlayerInfo.getName(), activePlayerInfo.getDollars());
            }

            if (actOutcome.isSceneFinished()) {
                SetLocation location = (SetLocation) actionModel.getBoard().getLocation(activePlayerInfo.getLocation());
                List<String> playersActing = location.playersActingOnLocation();

                Action.ScenePayout scenePayout = actionModel.finishScene();

                if (scenePayout.isBonusPayed()) {
                    activityResultLabel.append("Scene has finished, paying out bonuses\n");
                    Map<String, Integer> earnings = scenePayout.getEarnings();
                    for (String name : earnings.keySet()) {
                        activityResultLabel.append("Player " + name + " earns " + earnings.get(name) + " dollars\n");
                        gui.updateDollarsLabel(name, gameState.getPlayer(name).getDollars());
                    }
                } else {
                    activityResultLabel.append("Scene has finished, no bonus received\n");
                }

                playersActing.addAll(location.playersActingOnScene());
                for (String playerName : playersActing) {
                    // move players off roles
                    Coordinates locationCoordinates = location.placePlayerOnLocation(playerName);
                    gui.getPlayers().get(playerName).setBounds(
                            locationCoordinates.getX(),
                            locationCoordinates.getY(),
                            locationCoordinates.getWidth(),
                            locationCoordinates.getHeight());
                    // display 0 practice token for players
                    gui.updatePracticeLabel(playerName, 0);
                }
                // display scene completed
                gui.completeScene(location.getName());
            }
            setEnabledAll();
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
            actionModel.rehearse();
            Player activePlayerInfo = actionModel.getActivePlayer();
            gui.updatePracticeLabel(activePlayerInfo.getName(), activePlayerInfo.getPracticeToken());
            setEnabledAll();
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
                    JLabel activePlayer = gui.getPlayers().get(activePlayerInfo.getName());
                    Location location = board.getLocation(neighbor);
                    board.getLocation(activePlayerInfo.getLocation()).freePlayerPosition(activePlayerInfo.getName());
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
                    setEnabledAll();
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
                    actionModel.takeRole(activePlayerLocation, roleName, onCard);
                    activePlayerLocation.freePlayerPosition(activePlayerInfo.getName());
                    JLabel activePlayer = gui.getPlayers().get(activePlayerInfo.getName());
                    try {
                        Coordinates roleCoordinates = roleData.getCoordinates();
                        int roleX = roleCoordinates.getX();
                        int roleY = roleCoordinates.getY();
                        int roleW = roleCoordinates.getWidth();
                        int roleH = roleCoordinates.getHeight();
                        System.out.println("roleData: " + roleData.getOnCard());
                        if(roleData.getOnCard()){
                            roleX += activePlayerLocation.getCoordinates().getX();
                            roleY += activePlayerLocation.getCoordinates().getY() - 55;
                            roleW += activePlayerLocation.getCoordinates().getWidth();
                            roleH += activePlayerLocation.getCoordinates().getHeight();
                        }
                        activePlayer.setBounds(roleX, roleY, roleW, roleH);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        System.exit(1);
                    }
                    for (JButton button : roleButtons) {
                        button.setVisible(false);
                    }
                    setEnabledAll();
                });
            }
        });
    }

    public void Upgrade() {
        bUpgrade = new JButton("Upgrade");
        bUpgrade.setBackground(Color.white);
        bUpgrade.setEnabled(actionModel.canUpgrade().isValid());
        bUpgrade.setBounds(1210, 310, 150, 60);
        bUpgrade.addActionListener(e -> {
            System.out.println("Upgrade is Selected\n");
            Player activePlayerInfo = actionModel.getActivePlayer();
            int playerRank = activePlayerInfo.getActingRank();
            CastingOffice castingOffice = (CastingOffice) board.getLocation(activePlayerInfo.getLocation());
            for(int i = playerRank+1; i < 7; i++){
                JButton bUp = new JButton("" + i);
                int rank = i;
                bUp.setBackground(Color.white);
                bUp.setBounds(1400, 240 + (i)*70, 150, 60);
                bPane.add(bUp);
                upgradeButtons.add(bUp);
                bUp.addActionListener(f -> {
                    for (JButton button : upgradeButtons) {
                        button.setVisible(false);
                    }
                    upgradeMethods(activePlayerInfo, rank, castingOffice);
                });
            }
            setEnabledAll();
        });
    }

    private void upgradeMethods(Player activePlayer, int newRank, CastingOffice castingOffice){
        UpgradeCost upgradeCost = castingOffice.getUpgrades().get(newRank);

        JButton upgradeDollars = new JButton("Upgrade With Dollars");
        JButton upgradeCredits = new JButton("Upgrade With Credits");

        upgradeDollars.setBackground(Color.white);
        upgradeDollars.setBounds(1400, 380, 220, 60);
        bPane.add(upgradeDollars);
        int dollars = upgradeCost.getDollarCost();
        upgradeDollars.addActionListener(f -> {
            if(activePlayer.getDollars() >= dollars){
                activePlayer.upgrade(newRank, CurrencyType.DOLLARS, dollars);
                System.out.println("Dollars: "+dollars);
            }
            resetUpgradeButton();
            upgradeCredits.setVisible(false);
            upgradeDollars.setVisible(false);
            gui.updateRankLabel(activePlayer.getName(), activePlayer.getActingRank());
            gui.updatePlayerDice(activePlayer.getName(), activePlayer.getActingRank());
            gui.updateDollarsLabel(activePlayer.getName(), activePlayer.getDollars());
        });

        upgradeCredits.setBackground(Color.white);
        upgradeCredits.setBounds(1400, 460, 220, 60);
        bPane.add(upgradeCredits);
        int credits = upgradeCost.getCreditsCost();
        upgradeCredits.addActionListener(e -> {
            if(activePlayer.getCredits() >= credits){
                activePlayer.upgrade(newRank, CurrencyType.CREDITS, credits);
            }
            resetUpgradeButton();
            upgradeCredits.setVisible(false);
            upgradeDollars.setVisible(false);
            gui.updateRankLabel(activePlayer.getName(), activePlayer.getActingRank());
            gui.updatePlayerDice(activePlayer.getName(), activePlayer.getActingRank());
            gui.updateCreditsLabel(activePlayer.getName(), activePlayer.getCredits());
        });

    }

    public void EndTurn() {
        bEndTurn = new JButton("End Turn");
        bEndTurn.setBackground(Color.white);
        bEndTurn.setEnabled(actionModel.canEndTurn().isValid());
        bEndTurn.setBounds(1210, 380, 150, 60);
        bEndTurn.addActionListener(e -> {
            actionModel.endTurn();
            // Check if day should be ended
            if (gameState.getActiveScenes() == 1) {
                if (actionModel.endDay()) {
                    // day was last day score and end game
                    EndGame();
                } else {
                    // day not last day, start new day
                    startNewDay();
                }
            }
            setEnabledAll();
            resetDisplay();            
        });
    }

    /**
     * changes appropriate gui elements to start new day
     */
    private void startNewDay() {
        Queue<Player> players = gameState.getPlayerOrder();

        for (Player player :players) {
            Location playerLocation = board.getLocation(player.getLocation());
            String playerName = player.getName();
            playerLocation.freePlayerPosition(playerName);
            Coordinates trailerCoordinates = board.getLocation("trailer")
                    .placePlayerOnLocation(playerName);
            gui.getPlayers().get(playerName).setBounds(
                    trailerCoordinates.getX(),
                    trailerCoordinates.getY(),
                    trailerCoordinates.getWidth(),
                    trailerCoordinates.getHeight());

            gui.updatePracticeLabel(playerName, player.getPracticeToken());
        }

        // changes scenes on board to hidden and reset shot counters
        gui.resetScenes();
    }

    public void EndGame() {
        Queue<Player> players = gameState.getPlayerOrder();
        Map<String, Integer> playerScores = new HashMap<>();
        List<String> winners = new ArrayList<>();
        int highScore = 0;
        for (Player player : players) {
            int score = player.score();
            playerScores.put(player.getName(), score);
            if (score > highScore) {
                winners.clear();
                winners.add(player.getName());
                highScore = score;
            } else if (score == highScore) {
                winners.add(player.getName());
            }
        }
        new ScoreScreen().displayScores(playerScores, winners);
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

    private void resetTakeARoleButton() {
        System.out.println("Called reset");
        for (JButton button : roleButtons) {
            button.setVisible(false);
            bPane.remove(button);
        }
        roleButtons.clear();
        bTakeARole.setEnabled(actionModel.canTakeRole().isValid());
    }
    private void resetUpgradeButton() {
        System.out.println("Called reset");
        for (JButton button : upgradeButtons) {
            button.setVisible(false);
            bPane.remove(button);
        }
        upgradeButtons.clear();
        bUpgrade.setEnabled(actionModel.canUpgrade().isValid());
    }

    public JLayeredPane getbPane() {
        return bPane;
    }

}