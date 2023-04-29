package benlinkurgra.deadwood.location;

import java.util.ArrayList;

public class Trailers extends Location {
    ArrayList<String> neighbors = new ArrayList<>();

    public Trailers(String name, ArrayList<String> neighbors) {
        super(name);
        this.neighbors = neighbors;
    }

}
