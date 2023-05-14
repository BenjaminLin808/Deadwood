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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", Rank: ");
        sb.append(rank);
        if (!isAvailable()) {
            sb.append(", Player working Role: ");
            sb.append(playerOnRole);
        }
        sb.append("\n");
        return sb.toString();
    }

    public String toStringWithHighlight(int playerRank) {
        String yellowText = "\u001B[33m";
        String resetTextColor = "\u001B[0m";
        boolean roleUnavailable = !isAvailable() || playerRank < rank;
        StringBuilder sb = new StringBuilder();
        if (roleUnavailable) {
            sb.append(yellowText);
        }
        sb.append(this);
        if (roleUnavailable) {
            sb.append(resetTextColor);
        }
        return sb.toString();
    }
}
