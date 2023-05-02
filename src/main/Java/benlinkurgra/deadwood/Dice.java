package benlinkurgra.deadwood;

public class Dice {
    private int outcome;

    public void roll() {
        this.outcome = (int) (Math.random() * 6) + 1;
    }

    public int getOutcome() {
        return outcome;
    }
}
