package benlinkurgra.deadwood.location;

public abstract class Location {
    String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
