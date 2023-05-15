package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.CurrencyType;

import java.util.ArrayList;

public class Player {
    private final String name;
    private String location = "trailer";
    private int credits = 0;
    private int dollars = 0;
    private int actingRank = 1;
    private boolean workingRole = false;
    private int practiceToken = 0;

    public Player(String name, int credits, int actingRank) {
        this.name = name;
        this.credits = credits;
        this.actingRank = actingRank;
    }

    public Player(String name) {
        this.name = name;
    }

    /**
     * Update player rank, assumes player meets requirements for upgrade
     *
     * @param newRank rank to upgrade player to
     * @param currencyType currency to spend on upgrade
     * @param amount cost of upgrade
     */
    public void upgrade(int newRank, CurrencyType currencyType, int amount) {
        if (currencyType == CurrencyType.CREDITS) {
            decreaseCredits(amount);
            this.actingRank = newRank;
        } else if (currencyType == CurrencyType.DOLLARS) {
            decreaseDollars(amount);
            this.actingRank = newRank;
        } else {
            System.out.println("try again");
        }
    }

    /**
     * Pass in player's move location and board data to check if the new location can be reached
     *
     * @param newLocation
     * @param boardData
     * @return boolean
     */
    public boolean move(String newLocation, Board boardData) {
        ArrayList<String> neighbors = boardData.getLocation(this.location).getNeighbors();
        if (neighbors.contains(newLocation)) {
            this.location = newLocation;
            return true;
        } else {
            return false;
        }
    }

    public void score() {
        System.out.println("calculating player score");
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCredits() {
        return credits;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void decreaseCredits(int credits) {
        this.credits -= credits;
    }

    public int getDollars() {
        return dollars;
    }

    public void addDollars(int dollars) {
        this.dollars += dollars;
    }

    public void decreaseDollars(int dollar) {
        this.dollars -= dollar;
    }

    public int getActingRank() {
        return actingRank;
    }

    public void setActingRank(int actingRank) {
        this.actingRank = actingRank;
    }

    public boolean isWorkingRole() {
        return workingRole;
    }

    public void setWorkingRole(boolean workingRole) {
        this.workingRole = workingRole;
    }

    public int getPracticeToken() {
        return practiceToken;
    }

    public void resetPracticeTokens() {
        this.practiceToken = 0;
    }

    public void addPracticeToken() {
        ++this.practiceToken;
    }
}
