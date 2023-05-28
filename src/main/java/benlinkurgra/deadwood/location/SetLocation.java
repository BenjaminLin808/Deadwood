package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class SetLocation extends Location {
    private int currentShotTokens;
    private final int maxShotTokens;
    private final Roles roles;
    private Scene scene;
    private SceneStatus sceneStatus;

    private int[] setArea;

    public SetLocation(String name, int maxShotTokens, Roles roles, ArrayList<String> neighbors, int[] setArea) {
        super(name, neighbors);
        this.maxShotTokens = maxShotTokens;
        this.currentShotTokens = maxShotTokens;
        this.roles = roles;
        this.sceneStatus = SceneStatus.HIDDEN;
        this.setArea = setArea;
    }

    public int getCurrentShotTokens() {
        return currentShotTokens;
    }

    /**
     * removes a shot token from this location
     */
    public void removeShotToken() {
        --this.currentShotTokens;
    }

    /**
     * resets the locations shot tokens to the maximum amount
     */
    public void resetShotTokens() {
        this.currentShotTokens = this.maxShotTokens;
    }

    public int getMaxShotTokens() {
        return maxShotTokens;
    }

    public SceneStatus getSceneStatus() {
        return sceneStatus;
    }

    public void setSceneStatus(SceneStatus sceneStatus) {
        this.sceneStatus = sceneStatus;
    }

    /**
     * adds a new scene to the location
     *
     * @param scene new scene at location
     */
    public void setNewScene(Scene scene) {
        resetShotTokens();
        setSceneStatus(SceneStatus.HIDDEN);
        emptyLocationRoles();
        this.scene = scene;
    }

    /**
     * remove all players from roles on location
     */
    public void emptyLocationRoles() {
        roles.emptyRoles();
    }

    /**
     * get the budget of the current scene on location
     *
     * @return scene budget
     */
    public int getSceneBudget() {
        return scene.getBudget();
    }

    /**
     * get the name of the scene at this location
     *
     * @return name of scene at location
     */
    public String getSceneName() {
        return scene.getName();
    }

    public Roles getRolesOnLocation() {
        return roles;
    }

    /**
     * get roles for scene at location
     *
     * @return scene roles
     */
    public Roles getRolesOnScene() {
        return scene.getRoles();
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * get the number of roles on location
     *
     * @return number of roles off card
     */
    public int getNumRolesOnLocation() {
        return roles.getRoleList().size();
    }

    /**
     * get number of roles on scene
     *
     * @return number of roles on card
     */
    public int getNumRolesOnScene() {
        return scene.getRoles().getRoleList().size();
    }

    /**
     * determines if a player can take a role off card
     *
     * @param playerRank rank of player
     *
     * @return true if player can toke role, otherwise false
     */
    public boolean canTakeLocationRole(int playerRank) {
        return roles.availableRoles(playerRank).size() != 0;
    }

    /**
     * determines if a player can take a role on card
     *
     * @param playerRank rank of player
     *
     * @return true if player can toke role, otherwise false
     */
    public boolean canTakeSceneRole(int playerRank) {
        return scene.canTakeRole(playerRank);
    }

    /**
     * attempts to place player on scene role
     *
     * @param playerName name of player
     * @param playerRank rank of player
     * @param roleName name of role to take
     * @return true if player has filled role, otherwise false
     */
    public boolean fillRoleOnScene(String playerName, int playerRank, String roleName) {
        return scene.getRoles().fillRole(playerName, playerRank, roleName);
    }


    /**
     * attempts to place player on location role
     *
     * @param playerName name of player
     * @param playerRank rank of player
     * @param roleName name of role to take
     * @return true if player has filled role, otherwise false
     */
    public boolean fillRoleOnLocation(String playerName, int playerRank, String roleName) {
        return roles.fillRole(playerName, playerRank, roleName);
    }

    /**
     * determines if a player is acting on scene or not
     *
     * @param playerName name of player
     * @return true if acting on scene, otherwise false
     */
    public boolean isActingOnScene(String playerName) {
        return scene.isActingOnScene(playerName);
    }

    /**
     * get the rank of the role a player is working
     *
     * @param playerName name of player working role
     * @return if player is working a role at location return rank of role, otherwise 0
     */
    public int getLocationRoleRank(String playerName) {
        return roles.getRoleRank(playerName);
    }

    /**
     * finds all roles a player of a given rank can take
     *
     * @param playerRank rank of player
     * @return list of role information for available roles
     */
    public List<RoleData> getAllAvailableRoles(int playerRank) {
        List<RoleData> availableRoles = new ArrayList<>(roles.availableRoles(playerRank));
        availableRoles.addAll(scene.getRoles().availableRoles(playerRank));
        return availableRoles;
    }

    /**
     * finds the names of all players working on location
     * @return names of players working on location
     */
    public List<String> playersActingOnLocation() {
        return roles.playersOnRoles();
    }

    /**
     * finds the names of all players working on scene
     * @return names of players working on scene
     */
    public List<String> playersActingOnScene() {
        return scene.playersActingOnScene();
    }

    @Override
    public String toString() {
        return this.getName() +
                ", Shot tokens: " +
                currentShotTokens +
                "/" +
                maxShotTokens +
                "\nRoles on Location:\n" +
                roles +
                "Scene: " +
                getSceneName() +
                ", Budget: " +
                getSceneBudget() +
                "\nRoles on Scene:\n" +
                getRolesOnScene();
    }

    /**
     * converts object to string, highlights roles that can not be taken
     *
     * @param playerRank rank of player
     * @return location info with invalid roles highlighted
     */
    public String toStringWithHighlight(int playerRank) {
        return this.getName() +
                ", Shot tokens: " +
                currentShotTokens +
                "/" +
                maxShotTokens +
                "\nRoles on Location:\n" +
                roles.toStringWithHighlight(playerRank) +
                "Scene: " +
                getSceneName() +
                ", Budget: " +
                getSceneBudget() +
                "\nRoles on Scene:\n" +
                getRolesOnScene().toStringWithHighlight(playerRank);
    }
}
