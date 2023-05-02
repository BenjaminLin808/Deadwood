package benlinkurgra.deadwood.location;

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
}