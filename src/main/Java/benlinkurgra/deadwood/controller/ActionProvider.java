package benlinkurgra.deadwood.controller;

import benlinkurgra.deadwood.Player;

public class ActionProvider {

    public class ActionParser {
        public void movePlayer(Player player, String newLocation){
            System.out.println("this should invoke some function to update the player location");
        }
        public void takeRole(Player player, String role){
            System.out.println("should update the role of this player");
        }
        public void updatePlayerLocation(Player player){

        }
        public void updatePlayerLocation(Player player, String role){

        }
        public void upgradePlayer(Player player, int newRank){

        }
        public void updatePlayerRank(Player player){

        }
        public void act(Player player){

        }
        public void rehearse(Player player){

        }
    }

}
