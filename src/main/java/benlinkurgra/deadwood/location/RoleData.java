package benlinkurgra.deadwood.location;

public class RoleData {
    private final int rank;
    private final String name;
    private final String roleDescription;
    private final Coordinates coordinates;
    private String playerOnRole;
    private Boolean onCard;


    public RoleData(int rank, String name, String description, Coordinates coordinates, Boolean onCard) {
        this.rank = rank;
        this.name = name;
        this.roleDescription = description;
        this.coordinates = coordinates;
        playerOnRole = "";
        this.onCard = onCard;
    }

    public Boolean getOnCard() {
        return onCard;
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

    public Coordinates getCoordinates() {
        return coordinates;
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

    /**
     * converts this object to a string, invalid roles are highlighted in yellow
     *
     * @param playerRank rank of player
     * @return string with invalid role highlighted in yellow
     */
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
