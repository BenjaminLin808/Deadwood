package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class SetLocation extends Location{
    private int currentShotTokens;
    private final int maxShotTokens;
    private final Roles roles;
    private Scene scene;
    private SceneStatus sceneStatus;
    private ArrayList<String> neighbors = new ArrayList<>();

    public SetLocation(String name, int maxShotTokens, Roles roles, ArrayList<String> neighbors) {
        super(name);
        this.maxShotTokens = maxShotTokens;
        this.currentShotTokens = maxShotTokens;
        this.roles = roles;
        this.sceneStatus = SceneStatus.HIDDEN;
        this.neighbors = neighbors;
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

    public List<Roles> getAllAvailableRoles() {
        List<Roles> allRoles = new ArrayList<>();
        allRoles.add(roles);
        allRoles.add(scene.getRoles());
        return allRoles;
    }

    public void finishScene() {
        //TODO
    }
}
