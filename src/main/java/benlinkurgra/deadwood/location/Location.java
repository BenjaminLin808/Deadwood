package benlinkurgra.deadwood.location;

import java.util.ArrayList;

public abstract class Location {
    private final String name;
    private final ArrayList<String> neighbors;
    private final Coordinates coordinates;

    public Location(String name, ArrayList<String> neighbors, Coordinates coordinates) {
        this.name = name;
        this.neighbors = neighbors;
        this.coordinates = coordinates;
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

    @Override
    public String toString() {
        return name + '\n';
    }
}
