package benlinkurgra.deadwood.location;

import java.util.List;

public class Scene {
    private final String name;
    private final int budget;
    private final String description;
    private final Roles roles;

    private SceneStatus sceneStatus = SceneStatus.HIDDEN;

    public Scene(String name, int budget, String description, Roles roles) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    public String getDescription() {
        return description;
    }

    public Roles getRoles() {
        return roles;
    }

    public List<String> playersActingOnScene() {
        return roles.playersOnRoles();
    }
}
