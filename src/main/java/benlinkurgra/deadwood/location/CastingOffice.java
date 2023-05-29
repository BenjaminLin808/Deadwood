package benlinkurgra.deadwood.location;

import benlinkurgra.deadwood.CurrencyType;
import benlinkurgra.deadwood.model.Player;

import java.util.ArrayList;
import java.util.Map;

public class CastingOffice extends Location {
    private final Map<Integer, UpgradeCost> upgrades;

    public CastingOffice(
            String name,
            Map<Integer, UpgradeCost> upgrades,
            ArrayList<String> neighbors,
            Coordinates coordinates) {
        super(name, neighbors, coordinates);
        this.upgrades = upgrades;
    }

    /**
     * determines if a rank upgrade is valid
     *
     * @param player   the player who is requesting the rank increase
     * @param newRank  the acting rank that the player wishes to upgrade to
     * @param currency the currency type the player desires to use
     * @return returns true if the player can upgrade false otherwise
     */
    public boolean isValidUpgrade(Player player, int newRank, CurrencyType currency) {
        UpgradeCost rankCost = upgrades.get(newRank);
        if (player.getActingRank() >= newRank) {
            return false;
        } else if (currency == CurrencyType.CREDITS && player.getCredits() < rankCost.getCreditsCost()) {
            return false;
        } else if (currency == CurrencyType.DOLLARS && player.getDollars() < rankCost.getDollarCost()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determines if a player has any available upgrades
     *
     * @param player player seeking upgrade
     * @return true if the player has an available upgrade, otherwise false
     */
    public boolean hasUpgrades(Player player) {
        for (int key : upgrades.keySet()) {
            if (key > player.getActingRank()) {
                UpgradeCost upgradeCost = upgrades.get(key);
                if (upgradeCost.getCreditsCost() <= player.getCredits() ||
                        upgradeCost.getDollarCost() <= player.getDollars()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * determines the cost of an upgrade
     *
     * @param rank         rank of upgrade
     * @param currencyType type of currency to check
     * @return cost of upgrade
     */
    public int getCost(int rank, CurrencyType currencyType) {
        return upgrades.get(rank).getCostByType(currencyType);
    }

    public Map<Integer, UpgradeCost> getUpgrades() {
        return upgrades;
    }

    @Override
    public String toString() {
        String rankFormat = "%1$-10s";
        String dollarFormat = "%1$-13s";
        String creditFormat = "%1$-11s";
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("Here is a list of all rank upgrades.\n");
        sb.append("+----------+-------------+-----------+\n");
        sb.append("|   RANK   |   DOLLARS   |  CREDITS  |\n");
        sb.append("+----------+-------------+-----------+\n");

        for (Map.Entry<Integer, UpgradeCost> upgrade : upgrades.entrySet()) {
            UpgradeCost upgradeCost = upgrade.getValue();
            int dollarCost = upgradeCost.getDollarCost();
            int creditCost = upgradeCost.getCreditsCost();

            sb.append("|" + String.format(rankFormat, upgrade.getKey()) +
                    "|" + String.format(dollarFormat, dollarCost) +
                    "|" + String.format(creditFormat, creditCost) +
                    "|\n");
        }
        sb.append("+----------+-------------+-----------+");
        return sb.toString();
    }

    /**
     * converts this obejct to a string with invalid upgrades highlighted
     *
     * @param playerActingRank acting rank of player
     * @param playerCredits    players amount of credits
     * @param playerDollars    amount of dollars player has
     * @return string with invalid upgrades highlighted
     */
    public String toStringWithHighlight(int playerActingRank, int playerCredits, int playerDollars) {
        String yellowText = "\u001B[33m";
        String resetTextColor = "\u001B[0m";
        String rankFormat = "%1$-10s";
        String dollarFormat = "%1$-13s";
        String creditFormat = "%1$-11s";

        StringBuilder sb = new StringBuilder();
        sb.append("Here is a list of all rank upgrades. Invalid upgrades highlighted in ");
        sb.append(yellowText);
        sb.append("yellow");
        sb.append(resetTextColor);
        sb.append(":\n");
        sb.append("+----------+-------------+-----------+\n");
        sb.append("|   RANK   |   DOLLARS   |  CREDITS  |\n");
        sb.append("+----------+-------------+-----------+\n");

        for (Map.Entry<Integer, UpgradeCost> upgrade : upgrades.entrySet()) {
            UpgradeCost upgradeCost = upgrade.getValue();
            int dollarCost = upgradeCost.getDollarCost();
            int creditCost = upgradeCost.getCreditsCost();

            if (upgrade.getKey() <= playerActingRank) {
                sb.append("|" + yellowText + String.format(rankFormat, upgrade.getKey()) + resetTextColor +
                        "|" + yellowText + String.format(dollarFormat, dollarCost) + resetTextColor +
                        "|" + yellowText + String.format(creditFormat, creditCost) + resetTextColor +
                        "|\n");
            } else {
                String dollarColor = ((playerDollars < dollarCost)
                        ? yellowText : resetTextColor);
                String creditColor = ((playerCredits < creditCost)
                        ? yellowText : resetTextColor);

                sb.append("|" + String.format(rankFormat, upgrade.getKey()) +
                        "|" + dollarColor + String.format(dollarFormat, dollarCost) + resetTextColor +
                        "|" + creditColor + String.format(creditFormat, creditCost) + resetTextColor +
                        "|\n");
            }
        }
        sb.append("+----------+-------------+-----------+");
        return sb.toString();
    }
}
