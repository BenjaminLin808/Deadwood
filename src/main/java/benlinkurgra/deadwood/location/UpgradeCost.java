package benlinkurgra.deadwood.location;

import benlinkurgra.deadwood.CurrencyType;

public class UpgradeCost {
    private final int dollarCost;
    private final int creditsCost;

    public UpgradeCost(int dollarCost, int creditCost) {
        this.dollarCost = dollarCost;
        this.creditsCost = creditCost;
    }

    public int getDollarCost() {
        return dollarCost;
    }

    public int getCreditsCost() {
        return creditsCost;
    }

    /**
     * get the cost of an upgrade based on desired currency type
     *
     * @param currencyType type of currency to check
     * @return cost of upgrade
     */
    public int getCostByType(CurrencyType currencyType) {
        if (currencyType == CurrencyType.DOLLARS) {
            return dollarCost;
        } else {
            return creditsCost;
        }
    }
}