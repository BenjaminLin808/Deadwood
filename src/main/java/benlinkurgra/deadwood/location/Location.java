package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public abstract class Location {
    private final String name;
    private final ArrayList<String> neighbors;
    private final Coordinates coordinates;
    private final List<PlayerCoordinates> playerCoordinatesList;

    public Location(String name, ArrayList<String> neighbors, Coordinates coordinates) {
        this.name = name;
        this.neighbors = neighbors;
        this.coordinates = coordinates;
        this.playerCoordinatesList = PlayerCoordinates.locationPlayerCoordinates(name, coordinates);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * finds an open position on location and fills the role with that player.
     * NOTE: each location has 8 positions, if max players is not properly set or
     * players are not removed from a position will throw an error
     *
     * @param playerName name of player filling position
     * @return coordinates of position player is filling
     */
    public Coordinates placePlayerOnLocation(String playerName) {
        for (PlayerCoordinates playerCoordinate : playerCoordinatesList) {
            if (playerCoordinate.isOpen()) {
                playerCoordinate.fillPosition(playerName);
                return playerCoordinate.getCoordinates();
            }
        }
        throw new RuntimeException("Error, no position was available at location, game either setup with" +
                "invalid number of players or players not properly removed from position.");
    }

    /**
     * opens a position on location for a new player to fill
     *
     * @param playerName name of player to remove
     */
    public void freePlayerPosition(String playerName) {
        for (PlayerCoordinates playerCoordinate : playerCoordinatesList) {
            if (playerCoordinate.getNameOfPlayerOnPosition().equals(playerName)) {
                playerCoordinate.openPosition();
            }
        }
    }

    @Override
    public String toString() {
        return name + '\n';
    }
}
