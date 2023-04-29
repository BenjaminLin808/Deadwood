package benlinkurgra.deadwood.location;

import java.util.ArrayList;

public abstract class Location {
    private final String name;
    private final ArrayList<String> neighbors;
    public Location(String name, ArrayList<String> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }
}
