package benlinkurgra.deadwood.location;

public class RoleData {
    private final int rank;
    private final String name;
    private final String roleDescription;
    private String playerOnRole;
    private final boolean onCard;


    public RoleData(int rank, String name, String description, boolean onCard) {
        this.rank = rank;
        this.name = name;
        this.roleDescription = description;
        this.onCard = onCard;
        playerOnRole = "";
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
    public String getRoleDescription() {
        return roleDescription;
    }

    public String getPlayerOnRole() {
        return playerOnRole;
    }

    public void setPlayerOnRole(String playerOnRole) {
        this.playerOnRole = playerOnRole;
    }

    public boolean isAvailable() {
        return playerOnRole.isEmpty();
    }

    public boolean isOnCard() {
        return onCard;
    }
}
