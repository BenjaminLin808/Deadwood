public class Player {
    String name = "";
    String location = "";
    int credits;
    int dollars;
    int actingRank;
    boolean workingRole;
    int practiceToken;

    public Player(String name, int credits, int dollars, int actingRank) {
        this.name = name;
        this.credits = credits;
        this.dollars = dollars;
        this.actingRank = actingRank;
    }

    public void move(String newLocation){
        this.location = newLocation;
    }

    public void upgrade(int newRank, String currencyType){
        this.actingRank = newRank;
    }

    public void takeRole(boolean roleName){
        this.workingRole = roleName;
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

    public void setName(String name) {
        this.name = name;
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
