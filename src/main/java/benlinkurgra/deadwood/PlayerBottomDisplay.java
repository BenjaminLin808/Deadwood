package benlinkurgra.deadwood;

import javax.swing.*;

public class PlayerBottomDisplay {
    private JLabel playerDice;
    private JLabel nameLabel;
    private JLabel rankLabel;
    private JLabel dollarsLabel;
    private JLabel creditsLabel;

    public PlayerBottomDisplay(String playerName, int actingRank, int dollars, int credits, ImageIcon icon) {
        playerDice = new JLabel();
        playerDice.setIcon(icon);
        nameLabel = new JLabel("Player Name: " + playerName);
        rankLabel = new JLabel("Player Rank: " + actingRank);
        dollarsLabel = new JLabel("Player Dollars: " + dollars);
        creditsLabel = new JLabel("Player Credits: " + credits);
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
}