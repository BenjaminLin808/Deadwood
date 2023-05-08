package benlinkurgra.deadwood.model;

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

    public void move(String newLocation){
        this.location = newLocation;
    }

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

    public void takeRole(boolean takingRole){
        this.workingRole = takingRole;
    }

    public void act(){

        System.out.println("Rolling the dice for act");
    }

    public void rehearse(){
        this.practiceToken += 1;
        System.out.println("rehearsing add one practice chip");
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
