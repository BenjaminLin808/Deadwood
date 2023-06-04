package benlinkurgra.deadwood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.List;
public class ScoreScreen extends JFrame {
    private JTextArea activityResultLabel;
    public ScoreScreen() {
        setTitle("Game Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        activityResultLabel = new JTextArea();
        activityResultLabel.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(activityResultLabel);
        add(scrollPane, BorderLayout.CENTER);

        JButton quitButton = new JButton("Quit Game");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(quitButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void displayScores(Map<String, Integer> playerScores, List<String> winners) {
        activityResultLabel.setText("End of Game\n\n");
        for (String player : playerScores.keySet()) {
            activityResultLabel.append(player + " scored " +  playerScores.get(player) + "\n");
        }
        String finalWinner = "";
        for (String winner : winners) {
            finalWinner += winner + " ";
        }
        activityResultLabel.append("\n\nThe winner/s: " + finalWinner);
    }
}