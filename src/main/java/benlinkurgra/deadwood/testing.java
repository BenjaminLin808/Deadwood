package benlinkurgra.deadwood;

import benlinkurgra.deadwood.model.Player;

public class testing {
    public static void main(String[] args) {
        int rank = 2;
        int credits = 4;
        Player player = new Player("testingName", credits, rank);
        System.out.println(player.getActingRank());
    }
}
