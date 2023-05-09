package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.Dice;
import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.location.SetLocation;

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

    public Player(String name){
        this.name = name;
    }

    /**
     * Update player rank
     * @param newRank
     * @param currencyType
     * @param amount
     */
    public void upgrade(int newRank, String currencyType, int amount){
        if(currencyType.equals("credits")){
            this.credits = this.credits - amount;
            this.actingRank = newRank;
        } else if (currencyType.equals("dollars")) {
            this.dollars = this.dollars - amount;
            this.actingRank = newRank;
        }
        else{
            System.out.println("try again");
        }
    }

    /**
     * pass in player's move location and board data to check if the new location can be reached
     * @param newLocation
     * @param boardData
     * @return  boolean
     */
    public boolean move(String newLocation, Board boardData){
        ArrayList<String> neighbors = boardData.getLocation(this.location).getNeighbors();
        if(neighbors.contains(newLocation)){
            this.location = newLocation;
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * check whether the player act is successful or not, and payout
     * @param setLocation
     * @return
     */
    public boolean act(SetLocation setLocation){
        Dice dice = new Dice();
        int budget = setLocation.getSceneBudget();
        if(dice.roll() + this.practiceToken >= budget){
            setLocation.removeShotToken();
            // TODO check if the player role is oncard or not
            if(budget == 0){
                this.credits += 2;
            }
            else{
                this.credits += 1;
                this.dollars += 1;
            }
            if(setLocation.getCurrentShotTokens() == 0){
                setLocation.setSceneStatus(SceneStatus.COMPLETED);
                this.practiceToken = 0;
            }
            return true;
        }
        else{
            if(budget != 0){
                this.credits += 1;
            }
            return false;
        }
    }


    /**
     * rehearse
     * @param budget
     * @param setLocation
     * @return
     */
    public boolean rehearse(int budget, SetLocation setLocation){
        // if practice token is equal to budget minus one that means it is guarantee success
        if(practiceToken == budget-1){
            return false;
        }else {
            this.practiceToken += 1;
            return true;
        }
    }

    public void score(){
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

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
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

    public void setPracticeToken(int practiceToken) {
        this.practiceToken = practiceToken;
    }
}
