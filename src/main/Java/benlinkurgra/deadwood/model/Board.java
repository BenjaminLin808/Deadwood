package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.location.Location;
import benlinkurgra.deadwood.location.SetLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<String, Location> locations;

    public Board(Map<String, Location> locations) {
        this.locations = locations;
    }

    public Location getLocation(String locationName) {
        return locations.get(locationName);
    }


    /**
     * Determines if a move from a current location to a new location is possible.
     *
     * @param currLocation current location.
     * @param newLocation desired location to move.
     * @return true if move is possible, otherwise false.
     */
    public boolean isValidMove(String currLocation, String newLocation) {
        List<String> neighbors = locations.get(currLocation).getNeighbors();
        return neighbors.contains(newLocation);
    }

    /**
     * Determines if a location is a setLocation or not
     *
     * @param locationName name of location to check
     * @return true of location is a setLocation, false otherwise
     */
    public boolean isSetLocation(String locationName) {
        Location  location = locations.get(locationName);
        return location instanceof SetLocation;
    }
}
