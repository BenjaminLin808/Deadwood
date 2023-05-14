package benlinkurgra.deadwood.location;

import java.util.List;

public class Scene {
    private final String name;
    private final int budget;
    private final String description;
    private final Roles roles;

    public Scene(String name, int budget, String description, Roles roles) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.roles = roles;
    }

    protected String getName() {
        return name;
    }

    protected int getBudget() {
        return budget;
    }

    protected String getDescription() {
        return description;
    }

    protected Roles getRoles() {
        return roles;
    }

    protected List<String> playersActingOnScene() {
        return roles.playersOnRoles();
    }

}
