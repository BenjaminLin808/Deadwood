package benlinkurgra.deadwood;

public class Dice {
    /**
     * simulates the rolling of six sided dice
     *
     * @return outcome of the dice roll
     */
    public int roll() {
        int num = (int) (Math.random() * 6) + 1;
        return num;
    }

}
