package benlinkurgra.deadwood.location;

public record UpgradeCost(int dollarCost, int creditCost) {

    @Override
    public String toString() {
        return String.format("dollarCost: %d \t creditCost: %d", dollarCost, creditCost);
    }
}
