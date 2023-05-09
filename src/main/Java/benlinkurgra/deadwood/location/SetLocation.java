package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class SetLocation extends Location {
    private int currentShotTokens;
    private final int maxShotTokens;
    private Roles roles;
    private Scene scene;
    private SceneStatus sceneStatus;

    public SetLocation(String name, int maxShotTokens, Roles roles, ArrayList<String> neighbors) {
        super(name, neighbors);
        this.maxShotTokens = maxShotTokens;
        this.currentShotTokens = maxShotTokens;
        this.roles = roles;
        this.sceneStatus = SceneStatus.HIDDEN;
    }

    public List<String> playersActingOnScene() {
        return scene.playersActingOnScene();
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
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
        this.scene = scene;
    }

    public int getSceneBudget() {
        return scene.getBudget();
    }

    public List<RoleData> getAllAvailableRoles(int playerRank) {
        List<RoleData> availableRoles = new ArrayList<>(roles.availableRoles(playerRank));
        availableRoles.addAll(scene.getRoles().availableRoles(playerRank));
        return availableRoles;
    }
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void finishScene() {
        //TODO
    }
}
