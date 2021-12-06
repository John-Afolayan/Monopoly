package Model;

import View.BoardOverlay;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * @author John Afolayan
 * <p>
 * This class acts as the model for the game. Important data handling will be done in this class.
 *
 */

public class Game implements Serializable {
    private Player currentPlayer;
    private int currentPlayerInt = 0;
    private List<Player> players;
    private ModelUpdateListener viewer;
    private int numberOfHumanPlayers, numberOfAIPlayers, totalNumberOfPlayers;
    private int initialNumberOfHumanPlayers;
    private Board board = new Board();
    private List<ModelUpdateListener> views;
    private String backgroundFileName = "";
    private static String xmlFileName = "";

    boolean ableToPurchaseRed = false;
    boolean ableToPurchaseBlue = false;
    boolean ableToPurchaseGreen = false;
    boolean ableToPurchaseLightBlue = false;
    boolean ableToPurchasePurple = false;
    boolean ableToPurchaseOrange = false;
    boolean ableToPurchaseBrown = false;
    boolean ableToPurchaseYellow = false;

    public Game() {
        players = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public static void writeToFile(Game game) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gamefile.txt"));
        oos.writeObject(game);
        oos.writeObject(game.getCurrentPlayer());

        oos.writeObject(game.getPlayers());


        oos.writeObject(game.numberOfAIPlayers);
        oos.writeObject(game.numberOfHumanPlayers);
        oos.writeObject(game.initialNumberOfHumanPlayers);
        oos.writeObject(game.totalNumberOfPlayers);
        oos.writeObject(xmlFileName);

    }

    public static List<Object> readFile() throws IOException, ClassNotFoundException {
        Game game;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gamefile.txt"));
        game = (Game) ois.readObject();
        Player player = (Player) ois.readObject();

        List<Player> players = (List<Player>) ois.readObject();


        int numOfAIPlayers = (int) ois.readObject();
        int numOfHumanPlayers = (int) ois.readObject();
        int initialNumOfHumanPlayers = (int) ois.readObject();
        int totalNumOfPlayers = (int) ois.readObject();
        String xmlFile = (String) ois.readObject();
        List<Object> gameStuff = new ArrayList<>();
        gameStuff.add(game);
        gameStuff.add(player);


        gameStuff.add(players);

        gameStuff.add(numOfAIPlayers);
        gameStuff.add(numOfHumanPlayers);
        gameStuff.add(initialNumOfHumanPlayers);
        gameStuff.add(totalNumOfPlayers);
        gameStuff.add(xmlFile);

        return gameStuff;
    }

    public void setInitialNumberOfHumanPlayers(int initialNumberOfHumanPlayers) {
        this.initialNumberOfHumanPlayers = initialNumberOfHumanPlayers;
    }

    public void setNumberOfAIPlayers(int numberOfAIPlayers) {
        this.numberOfAIPlayers = numberOfAIPlayers;
    }

    public void setNumberOfHumanPlayers(int numberOfHumanPlayers) {
        this.numberOfHumanPlayers = numberOfHumanPlayers;
    }

    public void setTotalNumberOfPlayers(int totalNumberOfPlayers) {
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "currentPlayer=" + currentPlayer +
                ", currentPlayerInt=" + currentPlayerInt +
                ", players=" + players +
                ", viewer=" + viewer +
                ", numberOfHumanPlayers=" + numberOfHumanPlayers +
                ", numberOfAIPlayers=" + numberOfAIPlayers +
                ", totalNumberOfPlayers=" + totalNumberOfPlayers +
                ", initialNumberOfHumanPlayers=" + initialNumberOfHumanPlayers +
                ", board=" + board +
                ", views=" + views +
                ", ableToPurchaseRed=" + ableToPurchaseRed +
                ", ableToPurchaseBlue=" + ableToPurchaseBlue +
                ", ableToPurchaseGreen=" + ableToPurchaseGreen +
                ", ableToPurchaseLightBlue=" + ableToPurchaseLightBlue +
                ", ableToPurchasePurple=" + ableToPurchasePurple +
                ", ableToPurchaseOrange=" + ableToPurchaseOrange +
                ", ableToPurchaseBrown=" + ableToPurchaseBrown +
                ", ableToPurchaseYellow=" + ableToPurchaseYellow +
                '}';
    }

    public boolean isAbleToPurchaseBlue() {
        return ableToPurchaseBlue;
    }

    public boolean isAbleToPurchaseBrown() {
        return ableToPurchaseBrown;
    }

    public boolean isAbleToPurchaseGreen() {
        return ableToPurchaseGreen;
    }

    public boolean isAbleToPurchaseLightBlue() {
        return ableToPurchaseLightBlue;
    }

    public boolean isAbleToPurchaseOrange() {
        return ableToPurchaseOrange;
    }

    public boolean isAbleToPurchasePurple() {
        return ableToPurchasePurple;
    }

    public boolean isAbleToPurchaseRed() {
        return ableToPurchaseRed;
    }

    public boolean isAbleToPurchaseYellow() {
        return ableToPurchaseYellow;
    }

    public void setAbleToPurchaseBlue(boolean ableToPurchaseBlue) {
        this.ableToPurchaseBlue = ableToPurchaseBlue;
    }

    public void setAbleToPurchaseBrown(boolean ableToPurchaseBrown) {
        this.ableToPurchaseBrown = ableToPurchaseBrown;
    }

    public void setAbleToPurchaseGreen(boolean ableToPurchaseGreen) {
        this.ableToPurchaseGreen = ableToPurchaseGreen;
    }

    public void setAbleToPurchaseLightBlue(boolean ableToPurchaseLightBlue) {
        this.ableToPurchaseLightBlue = ableToPurchaseLightBlue;
    }

    public void setAbleToPurchaseOrange(boolean ableToPurchaseOrange) {
        this.ableToPurchaseOrange = ableToPurchaseOrange;
    }

    public void setAbleToPurchasePurple(boolean ableToPurchasePurple) {
        this.ableToPurchasePurple = ableToPurchasePurple;
    }

    public void setAbleToPurchaseRed(boolean ableToPurchaseRed) {
        this.ableToPurchaseRed = ableToPurchaseRed;
    }

    public void setAbleToPurchaseYellow(boolean ableToPurchaseYellow) {
        this.ableToPurchaseYellow = ableToPurchaseYellow;
    }

    public void checkingForHouseEligibility(){
        if (getCurrentPlayer().getBrownProperties() == 2) {
            setAbleToPurchaseBrown(true);
        }
        if (getCurrentPlayer().getPurpleProperties() == 3){
            setAbleToPurchasePurple(true);
        }
        if (getCurrentPlayer().getGreenProperties() == 3){
            setAbleToPurchaseGreen(true);
        }
        if (getCurrentPlayer().getBlueProperties() == 2){
            setAbleToPurchaseBlue(true);
        }
        if (getCurrentPlayer().getLightBlueProperties() == 3){
            setAbleToPurchaseLightBlue(true);
        }
        if (getCurrentPlayer().getYellowProperties() == 3){
            setAbleToPurchaseYellow(true);
        }
        if (getCurrentPlayer().getRedProperties() == 3){
            setAbleToPurchaseRed(true);
        }
        if (getCurrentPlayer().getOrangeProperties() == 3){
            setAbleToPurchaseOrange(true);
        }
    }

    public void addView(ModelUpdateListener view){
        views.add(view);
    }

    /**
     * @author John Afolayan
     * @param property Property a player lands on
     * This method checks to see if a property is owned.
     */

    /**
     * @author John Afolayan
     * This method prints the state of the current player.
     */
    public String printState() {
        /**
         * @author John Afolayan
         *
         * Print a representation of the game's state
         *
         */
        return("\nThere are " + players.size() + " active players in the game currently and you are player " + getCurrentPlayer().getPlayerNumber() + ".\nYou own the following properties: "
                + getCurrentPlayer().getOwnedProperties().toString() +  "You own the following houses: " + getCurrentPlayer().getOwnedHouses().toString() +
                "\nYou are on position: " + board.getIndex(getCurrentPlayer().getPosition()).getName() + ", your current balance is $" + getCurrentPlayer().getBalance() + " and your color on the board is " + BoardOverlay.getPlayerColor(currentPlayerInt));
    }

    public void passTurn() {
        /**
         * @author John Afolayan
         *
         * Passes turn to the next player
         *
         */
        this.currentPlayerInt = (this.currentPlayerInt == (this.totalNumberOfPlayers - 1)) ? 0 : this.currentPlayerInt + 1;
        this.currentPlayer = this.players.get(this.currentPlayerInt);
        for(ModelUpdateListener v: this.views) {
            v.passTurn(getCurrentPlayer().getPlayerNumber());
            while (isPlayerAnAI()){
                for (ModelUpdateListener vw: views){
                    vw.AIRepaint();
                }
                passTurn();
            }
            v.modelUpdated();
            v.lockPassTurnButton();
        }
    }

    public void manualPass(){
        checkPlayerBalance(getCurrentPlayer());
        lookingForWinner();
        for(ModelUpdateListener v: this.views) {
            v.manualPassUpdate(getCurrentPlayer().getPlayerNumber());
        }
        passTurn();
    }

    public void initializePlayers(int humanPlayers, int aiPlayers) {
        /**
         * @author John Afolayan
         *
         * Ask the user for the number of players that will be playing, and then
         * initializes them accordingly.
         *
         */

        this.numberOfHumanPlayers = humanPlayers;
        this.initialNumberOfHumanPlayers = numberOfHumanPlayers; //Make sure you don't change this value
        this.numberOfAIPlayers = aiPlayers;
        this.totalNumberOfPlayers = numberOfHumanPlayers + numberOfAIPlayers;
        createPlayers((numberOfHumanPlayers + numberOfAIPlayers));
        this.currentPlayer = players.get(0);

        for(ModelUpdateListener v: this.views) {
            v.initializeGame(totalNumberOfPlayers, getCurrentPlayer().getPlayerNumber());
        }
    }


    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }

    public void reloadPlayers(List<Player> players){
        this.players.addAll(players);

    }

    /**
     * @author John Afolayan
     * This method creates the specified amount players for a new game
     */
    public void createPlayers(int numberOfPlayers) {
        players = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player(i));
        }
    }


    public List<Player> getPlayers() {
        return players;
    }

    public int rollDie(){
        int dieRoll1 = getCurrentPlayer().rollDice();
        int dieRoll2 = getCurrentPlayer().rollDice();
        boolean doubleCheck = getCurrentPlayer().isDouble();
        int count = getCurrentPlayer().getDoubleCount();
        if (count == 3){
            goToJail();
            for(ModelUpdateListener v: this.views) {
                v.goingToJail(dieRoll1, dieRoll2, getCurrentPlayerPosition());
            }
        }
        else if(playerIsInJail() && doubleCheck){
            freePlayerFromJail();
            getCurrentPlayer().clearDoublesCount();
            for(ModelUpdateListener v: this.views) {
                v.freeFromJail(dieRoll1, dieRoll2, getCurrentPlayerPosition());
            }
        }
        else{
            setCurrentPlayerPosition(dieRoll1 + dieRoll2);
            for(ModelUpdateListener v: this.views) {
                v.dieCount(dieRoll1, dieRoll2, getCurrentPlayerPosition());
            }
            return (dieRoll1 + dieRoll2);
        }
        return 0;
    }

    public boolean playerIsInJail(){
        if(getCurrentPlayer().getPosition() == 30){
            return true;
        }
        return false;
    }

    public void freePlayerFromJail(){
        getCurrentPlayer().setPosition(10); //Sets player position to just visiting jail.
    }

    public void goToJail(){
        getCurrentPlayer().setPosition(30);
        getCurrentPlayer().clearDoublesCount();
    }

    public boolean hasPlayerPassedGo(){
        if((getCurrentPlayer().getPreviousPosition() != 30) && getCurrentPlayer().getPreviousPosition() > getCurrentPlayer().getPosition()){
            getCurrentPlayer().incrementBalance(200);
            return true;
        }
        return false;
    }

    public boolean hasPlayerLandedOnSpecialPosition(){
        if(getCurrentPlayer().getPosition() == 4){ //If player lands on Income Tax, they must pay $200
            getCurrentPlayer().decrementBalance(200);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            return true;
        } else if(getCurrentPlayer().getPosition() == 38){ //If player lands on Luxury Tax, they must pay $200
            getCurrentPlayer().decrementBalance(100);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            return true;
        }
        return false;
    }

    public int getSpecialPositionFee(){
        if(getCurrentPlayer().getPosition() == 4){ //If player lands on Income Tax deduct $200
            getCurrentPlayer().decrementBalance(200);
            return 200;
        } else if(getCurrentPlayer().getPosition() == 38){
            getCurrentPlayer().decrementBalance(100); //If player lands on Luxury Tax deduct 100
            return 200;
        }
        return 0;
    }

    /**
     * @author John Afolayan
     * This method sets the new position of a player after a die is rolled
     */
    public void setCurrentPlayerPosition(int pos) {
        getCurrentPlayer().setPosition((getCurrentPlayerPosition() + pos) % board.size());
        int position = getCurrentPlayerPosition();
    }

    /**
     * @author John Afolayan
     * This method gets the position of the current player
     */
    public int getCurrentPlayerPosition() {
        return getCurrentPlayer().getPosition();
    }

    public boolean propertyOwned(Property property){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedProperties().contains(property)){
                return true;
            }
        }
        return false;
    }

    public boolean utilityOwned(Utility utility){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedUtility().contains(utility)){
                return true;
            }
        }
        return false;
    }

    public boolean railroadsOwned(Railroad railroad){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedRailroads().contains(railroad)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author John Afolayan
     * @param property Property a player lands on
     * This method checks who owns the property landed on.
     */
    public Player whoOwnsProperty(Property property){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedProperties().contains(property)){
                return players.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsUtility(Utility utility){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedUtility().contains(utility)){
                return players.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsRailroad(Railroad railroad){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedRailroads().contains(railroad)){
                return players.get(i);
            }
        }
        return null;
    }

    public int getUtilityRent(int diceValue){
        if(getCurrentPlayer().getOwnedUtility().size() == 2){
            int amount = diceValue * 10;
            return amount;
        } else {
            int amount = diceValue * 4;
            return amount;
        }
    }

    public int getRailroadRent(){
        Player ownedBy = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()));
        return ownedBy.totalRailroadsOwned() * 25;
    }

    public boolean isPlayerAnAI(){
        if(getCurrentPlayer().getPlayerNumber()>(initialNumberOfHumanPlayers)){
            return true;
        }
        return false;
    }

    public boolean isCurrentPositionPropertyOwned(){
        Property prop = (Property) getBoard().getIndex(getCurrentPlayer().getPosition());
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedProperties().contains(prop)){
                return true;
            }
        }
        return false;
    }

    public Property getProperty(int x){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property){
                return (Property) board.getIndex(players.get(i).getPosition());
            }
        }
        return (Property) board.getIndex(players.get(x).getPosition());
    }
    public Railroad getRailroad(int x){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad){
                return (Railroad) board.getIndex(players.get(i).getPosition());
            }
        }
        return (Railroad) board.getIndex(players.get(x).getPosition());
    }

    public Utility getUtility(int x){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility){
                return (Utility) board.getIndex(players.get(i).getPosition());
            }
        }
        return (Utility) board.getIndex(players.get(x).getPosition());
    }

    public boolean isCurrentPositionRailroadOwned(){
        Railroad r = (Railroad) getRailroad(getCurrentPlayer().getPosition());
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedRailroads().contains(r)){
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentPositionUtilityOwned(){
        Utility u = (Utility) getUtility(getCurrentPlayer().getPosition());
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedUtility().contains(u)){
                return true;
            }
        }
        return false;
    }

    public boolean aiPurchaseDecision(){
        Random bool = new Random();
        if(getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property){
            Property p = (Property) getBoard().getIndex(getCurrentPlayer().getPosition());
            if(getCurrentPlayer().getBalance() > p.getValue()){
                return true; //If AI has enough money to purchase a property then it will choose to do so
            }
        } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad) {
            Railroad r = (Railroad) getBoard().getIndex(getCurrentPlayer().getPosition());
            if(getCurrentPlayer().getBalance() > r.getValue()){
                return true; //If AI has enough money to purchase a property then it will choose to do so
            }
        } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility) {
            Utility u = (Utility) getBoard().getIndex(getCurrentPlayer().getPosition());
            if(getCurrentPlayer().getBalance() > u.getValue()){
                return true; //If AI has enough money to purchase a property then it will choose to do so
            }
        }
        return bool.nextBoolean(); //Otherwise, the decision will randomly be decided.
    }

    public String aiAlgorithm() {
        checkPlayerBalance(getCurrentPlayer());
        lookingForWinner();
        hasPlayerPassedGo();
        int diceroll1 = getCurrentPlayer().rollDice(); //roll first die
        int diceroll2 = getCurrentPlayer().rollDice(); //roll second die
        int totalDiceroll = diceroll1 + diceroll2; //add two die together
        if (isPlayerAnAI() && getCurrentPlayer().getBalance() > 0 && getCurrentPlayer().getPosition() != 30) {
            int count = getCurrentPlayer().getDoubleCount();
            if (count == 3) {
                goToJail();
            } else {
                getCurrentPlayer().setPosition((totalDiceroll + getCurrentPlayerPosition()) % board.size()); //move AI player to position specified by die

                if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property) { //If player lands on a property
                    if (isCurrentPositionPropertyOwned()) { //If AI lands on a position owned by another player then tax them.
                        int taxedAmount = (int) (0.1 * ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        Player tempPlayer = whoOwnsProperty((Property) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                        tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                        checkPlayerBalance(getCurrentPlayer());
                        lookingForWinner();
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by another player " + tempPlayer.getPlayerNumber() + ". AI has been taxed $" + taxedAmount;
                    } else if (!isCurrentPositionPropertyOwned()) { //If AI lands on an available property, it will decide whether to purchase it or not.
                        if (aiPurchaseDecision()) {
                            getCurrentPlayer().addProperty((Property) getProperty(getCurrentPlayer().getPosition()));
                            getCurrentPlayer().decrementBalance(((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                            checkPlayerBalance(getCurrentPlayer());
                            lookingForWinner();
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        } else {
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        }
                    }
                } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad) { //If player lands on a railroad
                    if (isCurrentPositionRailroadOwned()) { //If AI lands on a position owned by another player then tax them.
                        int taxedAmount = (int) (0.1 * ((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        Player tempPlayer = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                        tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                        checkPlayerBalance(getCurrentPlayer());
                        lookingForWinner();
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by player " + tempPlayer.getPlayerNumber() + ". AI has been taxed $" + taxedAmount;
                    } else if (!isCurrentPositionRailroadOwned()) { //If AI lands on an available property, it will decide whether to purchase it or not.
                        if (aiPurchaseDecision()) {
                            getCurrentPlayer().addRailroad((Railroad) getRailroad(getCurrentPlayer().getPosition()));
                            getCurrentPlayer().decrementBalance(((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                            checkPlayerBalance(getCurrentPlayer());
                            lookingForWinner();
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        } else {
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        }
                    }
                } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility) { //If player lands on a utility
                    if (isCurrentPositionUtilityOwned()) { //If AI lands on a position owned by another player then tax them.
                        int taxedAmount = (int) (0.1 * ((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        Player tempPlayer = whoOwnsUtility((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                        tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                        checkPlayerBalance(getCurrentPlayer());
                        lookingForWinner();
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by player " + tempPlayer.getPlayerNumber() + ". AI has been taxed $" + taxedAmount;
                    } else if (!isCurrentPositionUtilityOwned()) { //If AI lands on an available utility, it will decide whether to purchase it or not.
                        if (aiPurchaseDecision()) {
                            getCurrentPlayer().addUtility((Utility) getUtility(getCurrentPlayer().getPosition()));
                            getCurrentPlayer().decrementBalance(((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                            checkPlayerBalance(getCurrentPlayer());
                            lookingForWinner();
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        } else {
                            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                        }
                    }
                } else if (getCurrentPlayer().getPosition() == 4) { //AI Player landed on Income Tax square and must pay $200.
                    getCurrentPlayer().decrementBalance(200);
                    checkPlayerBalance(getCurrentPlayer());
                    lookingForWinner();
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". The AI has been taxed $200";
                } else if (getCurrentPlayer().getPosition() == 38) { //If AI Players landed on Luxury Tax square, they must pay $100.
                    getCurrentPlayer().decrementBalance(100);
                    checkPlayerBalance(getCurrentPlayer());
                    lookingForWinner();
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". The AI has been taxed $100";
                } else if (getCurrentPlayer().getPosition() == 30) { //Player rolled die and is in Jail.
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now in: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".";
                }
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
            }
        } else if (isPlayerAnAI() && getCurrentPlayer().getBalance() < 0) { //If AI is bankrupt, remove them from the game
                checkPlayerBalance(getCurrentPlayer());
                lookingForWinner();
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + " is bankrupt and has therefore been eliminated.\n";
            } else if (isPlayerAnAI() && playerIsInJail()) {
            boolean doubleCheck = getCurrentPlayer().isDouble();
            if(doubleCheck){
                freePlayerFromJail();
                getCurrentPlayer().clearDoublesCount();
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() +" is free from jail!";
            }
        }
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            return "This player is an AI but the expected output isn't being printed.";
    }

    public void buyingHouseEligibility() {
        if (!(board.getIndex(getCurrentPlayer().getPosition()) instanceof Property)) {
            for (ModelUpdateListener v: views){
                v.notPurchasingAHouse();
            }
        }
    }

    public void buyingHotelEligibility(){
        if (!(board.getIndex(getCurrentPlayer().getPosition()) instanceof Property)){
            for (ModelUpdateListener v: views){
                v.notPurchasingAHotel();
            }
        }
    }

    private void blueHouseTransaction(boolean status){
        if (status) {
            for (ModelUpdateListener v : views) {
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("blue house", 200, "blue"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("blue house", 200, "blue"));
            getCurrentPlayer().decrementBalance(200);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v : views) {
                v.confirmHouseTransaction();
            }
        }
        if (!status) {
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) board.getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(100);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    private void brownHouseTransaction(boolean status){
        if (status) {
            for (ModelUpdateListener v : views) {
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("brown house", 50, "brown"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("brown house", 50, "brown"));
            getCurrentPlayer().decrementBalance(50);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v : views) {
                v.confirmHouseTransaction();
            }
        }
        if (!status) {
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) board.getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(25);
            for (ModelUpdateListener v : views) {
                v.confirmHouseSold();
            }
        }
    }

    private void purpleHouseTransaction(boolean status){
        if (status) {
            for (ModelUpdateListener v : views) {
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("purple house", 50, "purple"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("purple house", 50, "purple"));
            getCurrentPlayer().decrementBalance(50);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v : views) {
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(25);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }
    }

    private void orangeHouseTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("orange house", 100, "orange"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("orange house", 100, "orange"));
            getCurrentPlayer().decrementBalance(100);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(50);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    private void redHouseTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("red house", 150, "red"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("red house", 150, "red"));
            getCurrentPlayer().decrementBalance(150);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(75);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    private void lightBlueHouseTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("light blue house", 50, "light blue"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("light blue house", 50, "light blue"));
            getCurrentPlayer().decrementBalance(50);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(25);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    private void yellowHouseTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("yellow house", 150, "yellow"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("yellow house", 150, "yellow"));
            getCurrentPlayer().decrementBalance(150);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(75);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    private void greenHouseTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHouse();
            }
            getCurrentPlayer().getOwnedHouses().add(new House("green house", 200, "green"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().add(new House("green house", 200, "green"));
            getCurrentPlayer().decrementBalance(200);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHouseTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHouses().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().remove(0);
            getCurrentPlayer().incrementBalance(100);
            for (ModelUpdateListener v: views){
                v.confirmHouseSold();
            }
        }

    }

    public void sellAHouse(){
        checkingForHouseEligibility();
        String currentColor = (((Property) board.getIndex(getCurrentPlayer().getPosition())).getColor());
        if (isAbleToPurchaseBlue() && currentColor.equals("blue")) {
            blueHouseTransaction(false);
        }
        else if (isAbleToPurchaseBrown() && currentColor.equals("brown")) {
            brownHouseTransaction(false);
        }
        else if (isAbleToPurchaseGreen() && currentColor.equals("green")) {
            greenHouseTransaction(false);
        }
        else if (isAbleToPurchaseLightBlue() && currentColor.equals("light blue")) {
            lightBlueHouseTransaction(false);
        }
        else if (isAbleToPurchaseYellow() && currentColor.equals("yellow")) {
            yellowHouseTransaction(false);
        }
        else if (isAbleToPurchaseOrange() && currentColor.equals("orange")) {
            orangeHouseTransaction(false);
        }
        else if (isAbleToPurchaseRed() && currentColor.equals("red")) {
            redHouseTransaction(false);
        }
        else if (isAbleToPurchasePurple() && currentColor.equals("purple")) {
            purpleHouseTransaction(false);
        }
        else {
            for (ModelUpdateListener v: views){
                v.cannotSell();
            }
        }
    }


    public void purchaseAHouse(){
        checkingForHouseEligibility();

        String currentColor = (((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getColor());
        if (isAbleToPurchaseBlue() && currentColor.equals("blue")){
            blueHouseTransaction(true);
        }
        else if (isAbleToPurchaseBrown() && currentColor.equals("brown")){
            brownHouseTransaction(true);
        }
        else if (isAbleToPurchasePurple() && currentColor.equals("purple")){
            purpleHouseTransaction(true);
        }
        else if (isAbleToPurchaseOrange() && currentColor.equals("orange")){
           orangeHouseTransaction(true);
        }
        else if (isAbleToPurchaseRed() && currentColor.equals("red")){
            redHouseTransaction(true);
        }
        else if (isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
            lightBlueHouseTransaction(true);
        }
        else if (isAbleToPurchaseYellow() && currentColor.equals("yellow")){
            yellowHouseTransaction(true);
        }
        else if (isAbleToPurchaseGreen() && currentColor.equals("green")){
            greenHouseTransaction(true);
        }
        else {
            for (ModelUpdateListener v: views){
                v.cannotPurchase();
            }
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
        }

    }

    private void blueHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("blue hotel", 200, "blue"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("blue hotel", 200, "blue"));
            getCurrentPlayer().decrementBalance(200);

            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(100);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void brownHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("brown hotel", 50, "brown"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("brown hotel", 200, "brown"));
            getCurrentPlayer().decrementBalance(50);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(25);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void purpleHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("purple hotel", 50, "purple"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("purple hotel", 50, "purple"));
            getCurrentPlayer().decrementBalance(50);

            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(25);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void orangeHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("orange hotel", 100, "orange"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("orange hotel", 100, "orange"));
            getCurrentPlayer().decrementBalance(100);

            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(50);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void redHotelTransaction(boolean status) {
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("red hotel", 150, "red"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("red hotel", 150, "red"));
            getCurrentPlayer().decrementBalance(150);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(75);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void yellowHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("yellow hotel", 150, "yellow"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("yellow hotel", 150, "yellow"));
            getCurrentPlayer().decrementBalance(150);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(75);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void lightBlueHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("light blue hotel", 50, "light blue"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("light blue house", 50, "light blue"));
            getCurrentPlayer().decrementBalance(50);

            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(25);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    private void greenHotelTransaction(boolean status){
        if (status){
            for (ModelUpdateListener v: views){
                v.purchasingHotel();
            }
            getCurrentPlayer().getOwnedHotels().add(new Hotel("green hotel", 200, "green"));
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().add(new Hotel("red hotel", 200, "red"));
            getCurrentPlayer().decrementBalance(200);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelTransaction();
            }
        }
        if (!status){
            getCurrentPlayer().getOwnedHotels().remove(0);
            ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().remove(0);
            getCurrentPlayer().incrementBalance(100);
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
            for (ModelUpdateListener v: views){
                v.confirmHotelSold();
            }
        }
    }

    public void sellAHotel(){
        String currentColor = (((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getColor());
        if (isAbleToPurchaseBlue() && currentColor.equals("blue")){
            blueHotelTransaction(false);
        }
        else if (isAbleToPurchaseBrown() && currentColor.equals("brown")){
            brownHotelTransaction(false);
        }
        else if (isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
            lightBlueHotelTransaction(false);
        }
        else if (isAbleToPurchaseGreen() && currentColor.equals("green")){
            greenHotelTransaction(false);
        }
        else if (isAbleToPurchaseOrange() && currentColor.equals("orange")){
            orangeHotelTransaction(false);
        }
        else if (isAbleToPurchasePurple() && currentColor.equals("purple")){
            purpleHotelTransaction(false);
        }
        else if (isAbleToPurchaseYellow() && currentColor.equals("yellow")){
            yellowHotelTransaction(false);
        }
        else if (isAbleToPurchaseRed() && currentColor.equals("red")){
            redHotelTransaction(false);
        }

        else {
            for (ModelUpdateListener v: views){
                v.cannotSellHotel();
            }
        }
    }

    public void purchaseAHotel(){
        if (((Property) board.getIndex(getCurrentPlayer().getPosition())).getHouses().size() == 4) {
            ((Property) board.getIndex(getCurrentPlayer().getPosition())).getHouses().clear();
            String currentColor = (((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getColor());
            if (isAbleToPurchaseBlue() && currentColor.equals("blue")){
                blueHotelTransaction(true);
            }
            else if (isAbleToPurchaseBrown() && currentColor.equals("brown")){
                brownHotelTransaction(true);
            }
            else if (isAbleToPurchasePurple() && currentColor.equals("purple")){
                purpleHotelTransaction(true);
            }
            else if (isAbleToPurchaseOrange() && currentColor.equals("orange")){
                orangeHotelTransaction(true);
            }
            else if (isAbleToPurchaseRed() && currentColor.equals("red")){
                redHotelTransaction(true);
            }
            else if (isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
                lightBlueHotelTransaction(true);
            }
            else if (isAbleToPurchaseYellow() && currentColor.equals("yellow")){
                yellowHotelTransaction(true);
            }
            else if (isAbleToPurchaseGreen() && currentColor.equals("green")){
                greenHotelTransaction(true);
            }
        }
        else {
            for (ModelUpdateListener v: views){
                v.cannotPurchaseHotel();
            }

        }

    }

    public void clear(){
        setAbleToPurchaseOrange(false);
        setAbleToPurchaseBlue(false);
        setAbleToPurchaseRed(false);
        setAbleToPurchaseYellow(false);
        setAbleToPurchasePurple(false);
        setAbleToPurchaseBrown(false);
        setAbleToPurchaseGreen(false);
        setAbleToPurchaseLightBlue(false);
    }




    public String getBoardName() {
        return board.getIndex(getCurrentPlayer().getPosition()).getName();
    }

    public Board getBoard() {
        return board;
    }

    /**
     * @author John Afolayan
     * This method removes a banrupt player from the game.
     */
    public void removeBankruptPlayer(){
        int index = 1;
        for (final Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
            Player temp = iterator.next();
            if (temp.getBalance() <= 0) {
                if(index > numberOfHumanPlayers){
                    numberOfAIPlayers -= 1;
                } else if(index <= numberOfHumanPlayers){
                    numberOfHumanPlayers -= 1;
                }
                iterator.remove();
                this.totalNumberOfPlayers -= 1;
                this.currentPlayerInt -= 1;
            }
            index += 1;
        }
    }

    /**
     * @author John Afolayan
     * This method returns the current player of the game.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void quitGame() {
        System.exit(0);
    }

    /**
     * @author Ibrahim Said
     * This method checks the balance of a player and determines if they are eliminated or not.
     */
    public void checkPlayerBalance(Player player){
        int balance = player.getBalance();
        if (balance <= 0){
            removeBankruptPlayer();
            for(ModelUpdateListener v: this.views) {
                v.displayBankruptPlayer(getCurrentPlayer().getPlayerNumber());
            }
        }
    }

    /**
     * @author Ibrahim Said
     * This method checks if a player has won the game.
     */
    public void lookingForWinner(){
        if (getPlayers().size() == 1){
            for(ModelUpdateListener v: this.views) {
                v.returnWinner(getPlayers().get(0).getPlayerNumber());
            }
            System.exit(0);
        }
    }

    /**
     * @author John Afolayan
     * This method taxes a player whenver they land on another player's property
     */
    public void taxProperty() {
        Player ownedBy = whoOwnsProperty((Property) getBoard().getIndex(getCurrentPlayer().getPosition())); //player who owns property
        int amount = (int) (((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getTax()); //amount to decrement by, 10%
        int houseAmount = 0, hotelAmount= 0;
        if (!((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses().isEmpty()){
            for (House h: ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHouses()){
                houseAmount += h.getPrice();
            }
        }
        else if (!((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels().isEmpty()){
            for (Hotel h: ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getHotels()){
                hotelAmount += (h.getPrice() * 4);
            }
        }
        int total = houseAmount + amount + hotelAmount;
        if (!ownedBy.equals(getCurrentPlayer())){
            getCurrentPlayer().decrementBalance(total); //remove $amount from player being taxed
            ownedBy.incrementBalance(total); //add $amount to player who owns property
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
        }

        for(ModelUpdateListener v: this.views) {
            v.taxProperty(total, ownedBy, getCurrentPlayer().getPlayerNumber(), getCurrentPlayer().getBalance());
        }
    }

    /**
     * @author Hamza
     * This method taxes a player whenever they land of another players utility
     */

    public void taxUtility(int tax){
        Player ownedBy = whoOwnsUtility((Utility) getBoard().getIndex(getCurrentPlayer().getPosition()));
        if (!ownedBy.equals(getCurrentPlayer())){
            getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);

            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
        }

        for(ModelUpdateListener v: this.views) {
            v.taxProperty(tax, ownedBy, getCurrentPlayer().getPlayerNumber(), getCurrentPlayer().getBalance());
        }

    }

    /**
     * @author Hamza
     * This method taxes a player whenever they land of another players railroad
     */
    public void taxRailroad(int tax) {
        Player ownedBy = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()));
        if(!ownedBy.equals(getCurrentPlayer())){
            getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);

            for(ModelUpdateListener v: this.views) {
                v.taxProperty(tax, ownedBy, getCurrentPlayer().getPlayerNumber(), getCurrentPlayer().getBalance());
            }
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
        }
    }



    public void checkSquare(int diceRoll) {

        checkPlayerBalance(getCurrentPlayer());
        lookingForWinner();

        if(!playerIsInJail() && !isPlayerAnAI()) {
            if (hasPlayerPassedGo()) {
                for (ModelUpdateListener v : views) {
                    v.displayPlayerHasPassedGo();
                }
                passTurn();
            }
            else if (hasPlayerLandedOnSpecialPosition()) {
                for (ModelUpdateListener v : views) {
                    v.displaySpecialPosition();
                }
                passTurn();
            }
            else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property) {
                if (!propertyOwned((Property) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If property landed on isn't owned

                    for (ModelUpdateListener v : this.views) {
                        v.unlockPropertyBuy();
                    }
                } else if (propertyOwned((Property) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If property landed on is owned by someone else
                    this.taxProperty();
                    passTurn();
                }
            } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility) {
                if (!utilityOwned((Utility) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If utility landed on isn't owned
                    for (ModelUpdateListener v : this.views) {
                        v.unlockUtilityBuy();
                    }
                } else if (utilityOwned((Utility) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If utility landed on is owned by someone else
                    int tax = getUtilityRent(diceRoll);
                    this.taxUtility(tax);
                    passTurn();
                }
            } else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad) {
                if (!railroadsOwned((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If RailRoad landed on isn't owned
                    for (ModelUpdateListener v : this.views) {
                        v.unlockRailroadBuy();
                    }
                } else if (railroadsOwned((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()))) { //If Railroad landed on is owned by someone else
                    int rent = getRailroadRent();
                    this.taxRailroad(rent);
                    passTurn();
                }

            }else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Square){
                checkPlayerBalance(getCurrentPlayer());
                lookingForWinner();
                passTurn();
            }
        }
        else if(playerIsInJail() && !isPlayerAnAI()){
            passTurn();
        }
        while (isPlayerAnAI()){
            try {
                while (isPlayerAnAI()){
                    for (ModelUpdateListener v: views){
                        v.AIRepaint();
                    }
                    passTurn();
                    Thread.sleep(1);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        checkPlayerBalance(getCurrentPlayer());
        lookingForWinner();

    }

    public void makePurchase(){
        checkPlayerBalance(getCurrentPlayer());
        lookingForWinner();
        if (board.getIndex(getCurrentPlayer().getPosition()) instanceof Property) {
            String propertyColor = ((Property) board.getIndex(getCurrentPlayer().getPosition())).getColor();
            switch (propertyColor) {
                case "green": {
                    int x = getCurrentPlayer().getGreenProperties();
                    getCurrentPlayer().setGreenProperties(x + 1);
                    break;
                }
                case "red": {
                    int x = getCurrentPlayer().getRedProperties();
                    getCurrentPlayer().setRedProperties(x + 1);
                    break;
                }
                case "blue": {
                    int x = getCurrentPlayer().getBlueProperties();
                    getCurrentPlayer().setBlueProperties(x + 1);
                    break;
                }
                case "light blue": {
                    int x = getCurrentPlayer().getLightBlueProperties();
                    getCurrentPlayer().setLightBlueProperties(x + 1);
                    break;
                }
                case "yellow": {
                    int x = getCurrentPlayer().getYellowProperties();
                    getCurrentPlayer().setYellowProperties(x + 1);
                    break;
                }
                case "purple": {
                    int x = getCurrentPlayer().getPurpleProperties();
                    getCurrentPlayer().setPurpleProperties(x + 1);
                    break;
                }
                case "orange": {
                    int x = getCurrentPlayer().getOrangeProperties();
                    getCurrentPlayer().setOrangeProperties(x + 1);
                    break;
                }
                case "brown": {
                    int x = getCurrentPlayer().getBrownProperties();
                    getCurrentPlayer().setBrownProperties(x + 1);
                    break;
                }
            }
            getCurrentPlayer().addProperty((Property) getBoard().getIndex(getCurrentPlayer().getPosition()));
            getCurrentPlayer().decrementBalance(((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();

            for(ModelUpdateListener v: this.views) {
                v.confirmPurchase(getCurrentPlayer().getPlayerNumber(), getBoardName(), getCurrentPlayer().getBalance());
            }
            passTurn();

        }
        else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility) {
            getCurrentPlayer().addUtility((Utility) getBoard().getIndex(getCurrentPlayer().getPosition()));
            getCurrentPlayer().decrementBalance(((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();

            for(ModelUpdateListener v: this.views) {
                v.confirmPurchase(getCurrentPlayer().getPlayerNumber(), getBoardName(), getCurrentPlayer().getBalance());
            }

            passTurn();
        }
        else if (getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad) {
            getCurrentPlayer().addRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()));
            getCurrentPlayer().decrementBalance(((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();

            for(ModelUpdateListener v: this.views) {
                v.confirmPurchase(getCurrentPlayer().getPlayerNumber(), getBoardName(), getCurrentPlayer().getBalance());
            }
            passTurn();
        }

    }

    /**
     * Since the corresponding png background is the same name as the xml document, we just need to change xml to png
     * @param fileName
     */
    public void setBackgroundFileName(String fileName){
        backgroundFileName = fileName.replaceAll("xml", "png");
    }

    public String getBackgroundFileName(){
        return backgroundFileName;
    }

    public void setCustomBoard(String customBoardChoice) {
        xmlFileName = customBoardChoice;
        try {
            this.getBoard().importFromXmlFile(customBoardChoice);
            setBackgroundFileName(customBoardChoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}