package benlinkurgra.deadwood.location;

import java.util.List;

public class Scene {
    private final String name;
    private final int budget;
    private final String description;
    private final Roles roles;

    private final int sceneNum;

    public Scene(String name, int budget, String description, Roles roles, int sceneNum) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        this.roles = roles;
        this.sceneNum = sceneNum;
    }

    public String getName() {
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

    /**
     * get list of all player acting on the scene
     *
     * @return list player names who are acting on scene
     */
    protected List<String> playersActingOnScene() {
        return roles.playersOnRoles();
    }

    /**
     * determines of a player can take a role
     *
     * @param playerRank rank of player
     * @return true if player can take role, otherwise false
     */
    protected boolean canTakeRole(int playerRank) {
        return roles.availableRoles(playerRank).size() != 0;
    }

    /**
     * determines if a player is acting on the scene
     *
     * @param playerName name of player
     * @return true if player is acting on scene, otherwise false
     */
    protected boolean isActingOnScene(String playerName) {
        return roles.isActingOn(playerName);
    }

}
