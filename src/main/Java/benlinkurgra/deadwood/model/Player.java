package benlinkurgra.deadwood.model;

import benlinkurgra.deadwood.Dice;
import benlinkurgra.deadwood.location.SceneStatus;
import benlinkurgra.deadwood.location.SetLocation;

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
     * Update player location
     * @param newLocation
     */
    public void move(String newLocation) {
        this.location = newLocation;
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
     * Update player takingRole
     * @param takingRole
     */
    public void takeRole(boolean takingRole){
        this.workingRole = takingRole;
    }

    /**
     * Acting on the role
     * @param budget
     * @param setLocation
     */
    public void act(int budget, SetLocation setLocation){
        Dice dice = new Dice();
        if(dice.roll() + this.practiceToken >= budget){
            setLocation.removeShotToken();
            if(setLocation.getCurrentShotTokens() == 0){
                setLocation.setSceneStatus(SceneStatus.COMPLETED);
                this.practiceToken = 0;
            }
        }
    }

    /**
     * Increase practice tokens
     * @param budget
     * @param setLocation
     */
    public void rehearse(int budget, SetLocation setLocation){
        // if practice token is equal to budget minus one that means it is guarantee success
        if(practiceToken == budget-1){
            act(budget, setLocation);
        }else {
            this.practiceToken += 1;
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
