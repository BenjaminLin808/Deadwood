package benlinkurgra.deadwood.location;

import java.util.ArrayList;
import java.util.List;

public class PlayerCoordinates {
    private boolean isOpen;
    private String nameOfPlayerOnPosition;
    private Coordinates coordinates;

    private PlayerCoordinates(int x, int y) {
        this.isOpen = true;
        this.coordinates = new Coordinates(x, y, 50, 50);
        nameOfPlayerOnPosition = "";
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getNameOfPlayerOnPosition() {
        return nameOfPlayerOnPosition;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void fillPosition(String playerName) {
        isOpen = false;
        nameOfPlayerOnPosition = playerName;
    }

    public void openPosition() {
        isOpen = true;
        nameOfPlayerOnPosition = "";
    }

    public static List<PlayerCoordinates> locationPlayerCoordinates(String locationName, Coordinates locationCoordinates) {
        List<PlayerCoordinates> playerCoordinatesList = new ArrayList<>();

        int xLocation = locationCoordinates.getX();
        int yLocation = locationCoordinates.getY();

        switch (locationName) {
            case "office":
                // build list of player coordinates for office
                for (int i = 0; i < 3; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation - 10,
                            yLocation + (i * 50) + 55));
                }
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + 180,
                            yLocation + (i * 50) + 30));
                }
                playerCoordinatesList.add(new PlayerCoordinates(
                        xLocation + 125,
                        yLocation + 180));
                break;
            case "trailer":
                // build list of player coordinates for trailer
                for (int i = 0; i < 8; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i%3 * 50) + 25,
                            yLocation - (i/3 * 50) + 125));
                }
                break;
            case "Train Station":
                // build list of player coordinates for train station
                for (int i = 0; i < 5; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation - 10,
                            yLocation + (i * 50) + 150));
                }
                for (int i = 0; i < 3; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + 200,
                            yLocation + (i * 50) - 50));
                }
                break;
            case "Secret Hideout":
                // build list of player coordinates for secret hideout
                for (int i = 0; i < 8; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i%4 * 50) + 215,
                            yLocation + (i/4 * 40) + 75));
                }
                break;
            case "Church":
                // build list of player coordinates for church
                for (int i = 0; i < 8; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i%4 * 50) + 115,
                            yLocation - (i/4 * 45) - 45));
                }
                break;
            case "Hotel":
                // build list of player coordinates for hotel
                for (int i = 0; i < 8; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + 190,
                            yLocation + (i * 50) - 290));
                }
                break;
            case "Main Street":
                // build list of player coordinates for main street
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50) - 200,
                            yLocation + 125));
                }
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50),
                            yLocation + 155));
                }
                break;
            case "Jail":
                // build list of player coordinates for jail
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50) - 25,
                            yLocation + 140));
                }
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50) + 100,
                            yLocation + 185));
                }
                break;
            case "General Store":
                // build list of player coordinates for general store
                for (int i = 0; i < 8; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50) - 160,
                            yLocation + 125 ));
                }
                break;
            case "Ranch":
                // build list of player coordinates for ranch
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 45) - 25,
                            yLocation + 145));
                }
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 45),
                            yLocation + 190));
                }
                break;
            case "Bank":
                // build list of player coordinates for bank
                for (int i = 0; i < 2; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50) + 150,
                            yLocation + 125));
                }
                for (int i = 0; i < 6; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 50),
                            yLocation - 40));
                }
                break;
            case "Saloon":
                // build list of player coordinates for saloon
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 45) + 100,
                            yLocation - 50));
                }
                for (int i = 0; i < 4; i++) {
                    playerCoordinatesList.add(new PlayerCoordinates(
                            xLocation + (i * 45) + 100,
                            yLocation - 100));
                }
                break;
            default:
                throw new IllegalArgumentException("Error, class PlayerCoordinates received invalid location name.");
        }
        return playerCoordinatesList;
    }
}
