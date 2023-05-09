package benlinkurgra.deadwood.location;

import benlinkurgra.deadwood.CurrencyType;
import benlinkurgra.deadwood.model.Player;

import java.util.ArrayList;
import java.util.Map;

public class CastingOffice extends Location {
    private final Map<Integer, UpgradeCost> upgrades;
    public CastingOffice(String name, Map<Integer, UpgradeCost> upgrades, ArrayList<String> neighbors) {
        super(name, neighbors);
        this.upgrades = upgrades;
    }

    /**
     * determines if a rank upgrade is valid
     *
     * @param player the player who is requesting the rank increase
     * @param newRank the acting rank that the player wishes to upgrade to
     * @param currency the currency type the player desires to use
     * @return returns true if the player can upgrade false otherwise
     */
    public boolean isValidUpgrade(Player player, int newRank, CurrencyType currency) {
        UpgradeCost rankCost = upgrades.get(newRank);
        if (player.getActingRank() <= newRank) {
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

    public String validUpgrades(Player player) {
        StringBuilder upgradesString = new StringBuilder();
        upgradesString.append("Here is a list of all rank upgrades. All ");
        upgradesString.append("\033[31m");
        upgradesString.append("red colored ");
        upgradesString.append("\033[0m");
        upgradesString.append("text are invalid upgrades.\n");

        for (Map.Entry<Integer, UpgradeCost> upgrade : upgrades.entrySet()) {
            if (upgrade.getKey() <= player.getActingRank()) {
                upgradesString.append("\033[31m");
                upgradesString.append("Rank ");
                upgradesString.append(upgrade.getKey());
                upgradesString.append(": ");
                upgradesString.append(upgrade.getValue());
                upgradesString.append("\033[0m");
            } else {
                upgradesString.append("Rank ");
                upgradesString.append(upgrade.getKey());
                upgradesString.append(": ");
                upgradesString.append(upgrade.getValue());
            }
        }
        return upgradesString.toString();
    }

}
