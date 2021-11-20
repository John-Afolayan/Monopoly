package Model;

/**
 * Player class: Creates players, name, position and balance
 * Date: October 22, 2021
 * @author Hamza Zafar, 101158275
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    //private String piece (not implementing for now)
    private int playerNumber; // ie. player1, player2 etc
    private int position, previousPosition;
    private int balance;
    int greenProperties = 0;
    int yellowProperties = 0;
    int blueProperties = 0;
    int brownProperties = 0;
    int purpleProperties = 0;
    int lightBlueProperties = 0;
    int orangeProperties = 0;
    int redProperties = 0;

    private ArrayList<Property> ownedProperties = new ArrayList<>();
    private ArrayList<House> ownedHouses = new ArrayList<>();
    private ArrayList<Hotel> ownedHotels = new ArrayList<>();
    private ArrayList<Railroad> ownedRailroads = new ArrayList<>();
    private ArrayList<Utility> ownedUtility = new ArrayList<>();

    //constructor
    public Player(int playerNumber){
        //this.name = name;
        this.playerNumber = playerNumber;
        this.balance = 1500; //default starting balance
        this.position = 0; //start at the first square
        this.previousPosition = 0;
    }

    public ArrayList<House> getOwnedHouses() {
        return ownedHouses;
    }

    public ArrayList<Hotel> getOwnedHotels() {
        return ownedHotels;
    }

    public int getYellowProperties() {
        return yellowProperties;
    }

    public int getRedProperties() {
        return redProperties;
    }

    public int getOrangeProperties() {
        return orangeProperties;
    }

    public int getPurpleProperties() {
        return purpleProperties;
    }

    public int getLightBlueProperties() {
        return lightBlueProperties;
    }

    public int getGreenProperties() {
        return greenProperties;
    }

    public int getBrownProperties() {
        return brownProperties;
    }

    public int getBlueProperties() {
        return blueProperties;
    }

    public void setYellowProperties(int yellowProperties) {
        this.yellowProperties = yellowProperties;
    }

    public void setRedProperties(int redProperties) {
        this.redProperties = redProperties;
    }

    public void setPurpleProperties(int purpleProperties) {
        this.purpleProperties = purpleProperties;
    }

    public void setOrangeProperties(int orangeProperties) {
        this.orangeProperties = orangeProperties;
    }

    public void setLightBlueProperties(int lightBlueProperties) {
        this.lightBlueProperties = lightBlueProperties;
    }

    public void setGreenProperties(int greenProperties) {
        this.greenProperties = greenProperties;
    }

    public void setBrownProperties(int brownProperties) {
        this.brownProperties = brownProperties;
    }

    public void setBlueProperties(int blueProperties) {
        this.blueProperties = blueProperties;
    }

    /**
     * returns Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * returns Player's balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @param balance specified balance
     * sets a Player's balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @param amount to be decremented
     * decrements a Player's balance
     */
    public void decrementBalance(float amount) {
        this.balance -= amount;
    }

    /**
     * @param amount to be incremented
     * increments a Player's balance
     */
    public void incrementBalance(float amount) {
        this.balance += amount;
    }

    /**
     * @param position new position
     * sets a player's new position
     */
    public void setPosition(int position) {
        previousPosition = this.position;
        this.position = position;
    }

    /**
     * gets a player's new position
     */
    public int getPosition() { return this.position; }

    /**
     * gets a player's previous position
     */
    public int getPreviousPosition() { return this.previousPosition; }

    /**
     * adds a new property a player's collected
     */
    public void addProperty(Property property){
        ownedProperties.add(property);
    }

    public void addUtility(Utility utility){
        ownedUtility.add(utility);
    }

    public void addRailroad(Railroad railroad){
        ownedRailroads.add(railroad);
    }

    /**
     * returns a player's property collection
     */
    public ArrayList getOwnedProperties(){
        return ownedProperties;
    }

    public ArrayList<Utility> getOwnedUtility() {
        return ownedUtility;
    }

    public ArrayList<Railroad> getOwnedRailroads() {
        return ownedRailroads;
    }

    public int totalRailroadsOwned(){
        return ownedRailroads.size();
    }
    /**
     * returns a player's number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Calculates a random die roll
     */
    public Integer rollDice() {
        Integer numberOfDiceToRoll = 1; //rolling one die
        List<Integer> diceRolls = new ArrayList<>();
        int dieRoll = 0;
        Random random = new Random();
        for (int i = 0; i < numberOfDiceToRoll; i++) {
            int diceRoll = random.nextInt(6) + 1;
            diceRolls.add(diceRoll);
            dieRoll += diceRoll;
        }
        if(dieRoll >=0){
            return dieRoll;
        } else {
            rollDice();
        }
        return 2;
    }

}
