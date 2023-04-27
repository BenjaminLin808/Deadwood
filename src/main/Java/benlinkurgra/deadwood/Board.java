package benlinkurgra.deadwood;

import benlinkurgra.deadwood.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    Map<Location, List<Location>> layout = new HashMap<>();


    public void isValidMove(String currLocation, String newLocation){
        System.out.println("checking for valid move");
    }
}
