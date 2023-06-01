package benlinkurgra.deadwood;

import javax.swing.*;

public class PlayerBottomDisplay {
    private JLabel playerDice;
    private JLabel nameLabel;
    private JLabel rankLabel;
    private JLabel dollarsLabel;
    private JLabel creditsLabel;
    private JLabel practiceLabel;

    public PlayerBottomDisplay(String playerName, int actingRank, int dollars, int credits, ImageIcon icon) {
        playerDice = new JLabel();
        playerDice.setIcon(icon);
        nameLabel = new JLabel("Player Name: " + playerName);
        rankLabel = new JLabel("Player Rank: " + actingRank);
        dollarsLabel = new JLabel("Player Dollars: " + dollars);
        creditsLabel = new JLabel("Player Credits: " + credits);
        practiceLabel = new JLabel("Player Practice Tokens: 0");
    }

    public JLabel getPlayerDice() {
        return playerDice;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getRankLabel() {
        return rankLabel;
    }

    public JLabel getDollarsLabel() {
        return dollarsLabel;
    }

    public JLabel getCreditsLabel() {
        return creditsLabel;
    }

    public JLabel getPracticeLabel() {
        return practiceLabel;
    }

    /**
     * update the dollars label
     *
     * @param dollars amount of dollars player has
     */
    public void updateDollars(int dollars) {
        dollarsLabel.setText("Player Dollars: " + dollars);
    }

    /**
     * update the credits label
     *
     * @param credits amount of credits player has
     */
    public void updateCredits(int credits) {
        creditsLabel.setText("Player Credits: " + credits);
    }

    public void updatePracticeTokens(int tokens) {
        practiceLabel.setText("Player Practice Tokens: " + tokens);
    }
}