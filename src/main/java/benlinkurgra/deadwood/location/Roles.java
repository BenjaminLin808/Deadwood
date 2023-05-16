package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class Roles {

    // roleData is a map where keys are the rank of roles, values are
    // a map where keys are the scene name and values are role information
    private final List<RoleData> roleList;

    public Roles(List<RoleData> roles) {
        this.roleList = roles;
    }

    protected List<RoleData> availableRoles(int playerRank) {
        List<RoleData> returnList = new ArrayList<>();
        for (RoleData role: roleList) {
            if (role.getRank() <= playerRank && role.isAvailable()) {
                returnList.add(role);
            }
        }
        return returnList;
    }

    /**
     * Attempts to fill a role
     *
     * @param playerName name of player taking role
     * @param playerRank rank of player taking role
     * @param roleName name of the role
     * @return returns true if player can take the role, otherwise returns false
     */
    protected boolean fillRole(String playerName, int playerRank, String roleName) {
        for (RoleData role : roleList) {
            if (role.getName().equals(roleName)) {
                // check if player can take role
                if (role.isAvailable() && role.getRank() <= playerRank) {
                    role.setPlayerOnRole(playerName);
                    return true;
                } else {
                    return false;
                }
            }
        }
        // return false if roleName not a role (this should never happen
        return false;
    }

    protected void emptyRoles() {
        for(RoleData role : roleList) {
            role.setPlayerOnRole("");
        }
    }

    protected List<String> playersOnRoles() {
        List<String> playerList = new ArrayList<>();
        for(RoleData role : roleList) {
            if (!role.isAvailable()) {
                playerList.add(role.getPlayerOnRole());
            }
        }
        return playerList;
    }

    public List<RoleData> getRoleList() {
        return roleList;
    }

    protected boolean isActingOn(String playerName) {
        for(RoleData role : roleList) {
            if (role.getPlayerOnRole().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    protected int getRoleRank(String playerName) {
        for(RoleData role : roleList) {
            if (role.getPlayerOnRole().equals(playerName)) {
                return role.getRank();
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < roleList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(roleList.get(i));
        }
        return sb.toString();
    }

    public String toStringWithHighlight(int playerRank) {
        String yellowText = "\u001B[33m";
        String resetTextColor = "\u001B[0m";
        StringBuilder sb = new StringBuilder();
        sb.append("Unavailable roles highlighted in");
        sb.append(yellowText);
        sb.append(" yellow");
        sb.append(resetTextColor);
        sb.append(".\n");
        for (int i = 0; i < roleList.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(roleList.get(i).toStringWithHighlight(playerRank));
        }
        return sb.toString();
    }
}
