package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class SetLocation extends Location {
    private int currentShotTokens;
    private final int maxShotTokens;
    private final Roles roles;
    private Scene scene;
    private SceneStatus sceneStatus;

    public SetLocation(String name, int maxShotTokens, Roles roles, ArrayList<String> neighbors) {
        super(name, neighbors);
        this.maxShotTokens = maxShotTokens;
        this.currentShotTokens = maxShotTokens;
        this.roles = roles;
        this.sceneStatus = SceneStatus.HIDDEN;
    }

    public int getCurrentShotTokens() {
        return currentShotTokens;
    }

    public void removeShotToken() {
        --this.currentShotTokens;
    }

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

    public void setNewScene(Scene scene) {
        resetShotTokens();
        setSceneStatus(SceneStatus.HIDDEN);
        emptyLocationRoles();
        this.scene = scene;
    }

    public void emptyLocationRoles() {
        roles.emptyRoles();
    }

    public int getSceneBudget() {
        return scene.getBudget();
    }

    public String getSceneName() {
        return scene.getName();
    }

    public Roles getRolesOnLocation() {
        return roles;
    }

    public Roles getRolesOnScene() {
        return scene.getRoles();
    }

    public Scene getScene() {
        return scene;
    }

    public int getNumRolesOnLocation() {
        return roles.getRoleList().size();
    }

    public int getNumRolesOnScene() {
        return scene.getRoles().getRoleList().size();
    }

    public boolean canTakeLocationRole(int playerRank) {
        return roles.availableRoles(playerRank).size() != 0;
    }

    public boolean canTakeSceneRole(int playerRank) {
        return scene.canTakeRole(playerRank);
    }

    public boolean fillRoleOnScene(String playerName, int playerRank, String roleName) {
        return scene.getRoles().fillRole(playerName, playerRank, roleName);
    }

    public boolean fillRoleOnLocation(String playerName, int playerRank, String roleName) {
        return roles.fillRole(playerName, playerRank, roleName);
    }

    public boolean isActingOnScene(String playerName) {
        return scene.isActingOnScene(playerName);
    }

    public int getLocationRoleRank(String playerName) {
        return roles.getRoleRank(playerName);
    }

    public List<RoleData> getAllAvailableRoles(int playerRank) {
        List<RoleData> availableRoles = new ArrayList<>(roles.availableRoles(playerRank));
        availableRoles.addAll(scene.getRoles().availableRoles(playerRank));
        return availableRoles;
    }

    public List<String> playersActingOnLocation() {
        return roles.playersOnRoles();
    }

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
