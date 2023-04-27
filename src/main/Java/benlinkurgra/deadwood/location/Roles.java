package benlinkurgra.deadwood.location;

import benlinkurgra.deadwood.readxml.models.Card;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    // roleData is a map where keys are the rank of roles, values are
    // a map where keys are the scene name and values are role information
    private final List<RoleData> roleList;

    public Roles(Card rawData, boolean onCard) {
        this.roleList = new ArrayList<>();
        //TODO build hashmap from rawData

        // EXAMPLE
        roleList.add(new RoleData(
                2,
                "Falls off Roof",
                "Aaaaiiiigggghh!",
                onCard));
        roleList.add(new RoleData(2,
                "Woman in Black Dress",
                "Aaaaiiiigggghh!",
                onCard));
        // END EXAMPLE
    }

    public List<RoleData> availableRoles(int playerRank) {
        List<RoleData> returnList = new ArrayList<>();
        for (RoleData role: roleList) {
            if (role.getRank() <= playerRank) {
                returnList.add(role);
            }
        }
        return returnList;
    }

    public boolean fillRole(String playerName, int playerRank, String roleName) {
        for (RoleData role : roleList) {
            if (role.getName().equals(playerName) && role.isAvailable()) {
                role.setPlayerOnRole(playerName);
                return true;
            }
        }
        return false;
    }

    public List<String> playersOnRoles() {
        List<String> playerList = new ArrayList<>();
        for(RoleData role : roleList) {
            if (!role.isAvailable()) {
                playerList.add(role.getPlayerOnRole());
            }
        }
        return playerList;
    }
}
