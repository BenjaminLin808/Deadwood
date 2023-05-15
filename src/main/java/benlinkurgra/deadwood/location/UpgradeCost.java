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

    public int getCostByType(CurrencyType currencyType) {
        if (currencyType == CurrencyType.DOLLARS) {
            return dollarCost;
        } else {
            return creditsCost;
        }
    }
}