package benlinkurgra.deadwood;

public class LocationProvider {
    public void updateSetLocationStatus(String location){
        System.out.println("update the location status to HIDDEN, COMPLETED, or REVEALED");
    }
    public void updateSceneAtLocation(String location){
        System.out.println("update the scenes at a location");
    }
    public void provideRolesAtLocation(String location){
        System.out.println("return the roles available at this location");
    }
}
