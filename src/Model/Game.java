package Model;

import Game.Parser;
import View.BoardOverlay; //Only used to get player's colour as a string

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * @author John Afolayan
 * <p>
 * This main class creates and initialises all the others. This class acts the Controller.
 * It creates the parser and starts the game.
 * It also evaluates and executes the commands that the parser returns.
 */

public class Game {
    private Parser parser;
    private Property property;
    private Player currentPlayer;
    private int currentPlayerInt = 0;
    private List<Player> playerList;
    private Utility utility;
    private Railroad railroad;
    private ModelUpdateListener viewer;
    private int numberOfHumanPlayers;
    private int numberOfAIPlayers;
    private int totalNumberOfPlayers;
    private Board board = new Board();
    boolean ableToPurchaseRed = false;
    boolean ableToPurchaseBlue = false;
    boolean ableToPurchaseGreen = false;
    boolean ableToPurchaseLightBlue = false;
    boolean ableToPurchasePurple = false;
    boolean ableToPurchaseOrange = false;
    boolean ableToPurchaseBrown = false;
    boolean ableToPurchaseYellow = false;

    public Game() {
        parser = new Parser();
        playerList = new ArrayList<>();
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

    public Property getProperty(int x){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property){
                return (Property) board.getIndex(playerList.get(i).getPosition());
            }
        }
        return (Property) board.getIndex(playerList.get(x).getPosition());
    }

    public Railroad getRailroad(int x){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad){
                return (Railroad) board.getIndex(playerList.get(i).getPosition());
            }
        }
        return (Railroad) board.getIndex(playerList.get(x).getPosition());
    }

    public Utility getUtility(int x){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getPosition() == x && getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility){
                return (Utility) board.getIndex(playerList.get(i).getPosition());
            }
        }
        return (Utility) board.getIndex(playerList.get(x).getPosition());
    }

    /**
     * @author John Afolayan
     * @param property Property a player lands on
     * This method checks to see if a property is owned.
     */
    public boolean propertyOwned(Property property){
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedProperties().contains(property)){
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentPositionPropertyOwned(){
        Property prop = (Property) getBoard().getIndex(getCurrentPlayer().getPosition());
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedProperties().contains(prop)){
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentPositionRailroadOwned(){
        Railroad r = (Railroad) getRailroad(getCurrentPlayer().getPosition());
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedRailroads().contains(r)){
                return true;
            }
        }
        return false;
    }

    public boolean isCurrentPositionUtilityOwned(){
        Utility u = (Utility) getUtility(getCurrentPlayer().getPosition());
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedUtility().contains(u)){
                return true;
            }
        }
        return false;
    }

    public boolean utilityOwned(Utility utility){
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedUtility().contains(utility)){
                return true;
            }
        }
        return false;
    }

    public boolean railroadsOwned(Railroad railroad){
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedRailroads().contains(railroad)){
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
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedProperties().contains(property)){
                return playerList.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsUtility(Utility utility){
        for (int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).getOwnedUtility().contains(utility)){
                return playerList.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsRailroad(Railroad railroad) {
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getOwnedRailroads().contains(railroad)) {
                return playerList.get(i);
            }
        }
        return null;
    }

    public int getUtilityRent(int diceValue){
        return (int) (((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue()) * diceValue;
    }

    public int getRailroadRent(){
        Player ownedBy = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()));
        return ownedBy.totalRailroadsOwned() * 25;
    }

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
        return("\nThere are " + playerList.size() + " active players in the game currently and you are player " + (currentPlayerInt + 1) + ".\nYou own the following properties: "
                + getCurrentPlayer().getOwnedProperties().toString() + "\nYour current balance is $" + getCurrentPlayer().getBalance() + " and your color on the board is " + BoardOverlay.getPlayerColor(currentPlayerInt + 1));
    }

    public void passTurn() {
        /**
         * @author John Afolayan
         *
         * Passes turn to the next player
         *
         */
        this.currentPlayerInt = (this.currentPlayerInt == (this.totalNumberOfPlayers - 1)) ? 0 : this.currentPlayerInt + 1;
        this.currentPlayer = this.playerList.get(this.currentPlayerInt);
        //newTurn();
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
        this.numberOfAIPlayers = aiPlayers;
        this.totalNumberOfPlayers = (humanPlayers + aiPlayers);
        createPlayers((numberOfHumanPlayers + numberOfAIPlayers));
        this.currentPlayer = playerList.get(0);
    }

    private void update() {
        if (this.viewer != null)
            this.viewer.modelUpdated();
    }

    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }

    /**
     * @author John Afolayan
     * This method creates the specified amount players for a new game
     */
    public void createPlayers(int numberOfPlayers) {
        playerList = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            playerList.add(new Player(i));
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public boolean isPlayerAnAI(){
        if(getCurrentPlayer().getPlayerNumber()>(numberOfHumanPlayers)){
            return true;
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
        if (isPlayerAnAI() && getCurrentPlayer().getBalance() > 0 && getCurrentPlayer().getPosition() != 30) {
            hasPlayerPassedGo();
            int diceroll1 = getCurrentPlayer().rollDice(); //roll first die
            int diceroll2 = 0; //roll second die
            int totalDiceroll = diceroll1 + diceroll2; //add two die together
            getCurrentPlayer().setPosition(totalDiceroll+getCurrentPlayerPosition()); //move AI player to position specified by die

            if(getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Property){ //If player lands on a property
                if (isCurrentPositionPropertyOwned()) { //If AI lands on a position owned by another player then tax them.
                    int taxedAmount = (int) (0.1 * ((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                    Player tempPlayer = whoOwnsProperty((Property) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                    tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by another player " + tempPlayer.getPlayerNumber() +". AI has been taxed $" + taxedAmount;
                } else if (!isCurrentPositionPropertyOwned()) { //If AI lands on an available property, it will decide whether to purchase it or not.
                    if (aiPurchaseDecision()) {
                        getCurrentPlayer().addProperty((Property) getProperty(getCurrentPlayer().getPosition()));
                        getCurrentPlayer().decrementBalance(((Property) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    } else {
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    }
                }
            } else if(getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Railroad){ //If player lands on a railroad
                if (isCurrentPositionRailroadOwned()) { //If AI lands on a position owned by another player then tax them.
                    int taxedAmount = (int) (0.1 * ((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                    Player tempPlayer = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                    tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by player " + tempPlayer.getPlayerNumber() +". AI has been taxed $" + taxedAmount;
                } else if (!isCurrentPositionRailroadOwned()) { //If AI lands on an available property, it will decide whether to purchase it or not.
                    if (aiPurchaseDecision()) {
                        getCurrentPlayer().addRailroad((Railroad) getRailroad(getCurrentPlayer().getPosition()));
                        getCurrentPlayer().decrementBalance(((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    } else {
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    }
                }
            } else if(getBoard().getIndex(getCurrentPlayer().getPosition()) instanceof Utility){ //If player lands on a utility
                if (isCurrentPositionUtilityOwned()) { //If AI lands on a position owned by another player then tax them.
                    int taxedAmount = (int) (0.1 * ((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                    Player tempPlayer = whoOwnsUtility((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())); //Get player who owns property
                    tempPlayer.incrementBalance(taxedAmount); //Increment balance for player who owns property.
                    return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".\nThis square is owned by player " + tempPlayer.getPlayerNumber() + ". AI has been taxed $" + taxedAmount;
                } else if (!isCurrentPositionUtilityOwned()) { //If AI lands on an available utility, it will decide whether to purchase it or not.
                    if (aiPurchaseDecision()) {
                        getCurrentPlayer().addUtility((Utility) getUtility(getCurrentPlayer().getPosition()));
                        getCurrentPlayer().decrementBalance(((Utility) getBoard().getIndex(getCurrentPlayer().getPosition())).getValue());
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    } else {
                        return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". They decided not to purchase " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
                    }
                }
            } else if(getCurrentPlayer().getPosition() == 4){ //AI Player landed on Income Tax square and must pay $200.
                getCurrentPlayer().decrementBalance(200);
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". The AI has been taxed $200";
            } else if(getCurrentPlayer().getPosition() == 38){ //If AI Players landed on Luxury Tax square, they must pay $100.
                getCurrentPlayer().decrementBalance(100);
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ". The AI has been taxed $100";
            }else if(getCurrentPlayer().getPosition() == 30){ //Player rolled die and is in Jail.
                return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now in: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName() + ".";
            }
            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + "(AI): rolled two die, " + diceroll1 + " and " + diceroll2 + " and is now on: " + getBoard().getIndex(getCurrentPlayer().getPosition()).getName();
        }
        else if(isPlayerAnAI() && getCurrentPlayer().getBalance() < 0) { //If AI is bankrupt, remove them from the game
            removeBankruptPlayer();
            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + " is bankrupt and has therefore been eliminated.\n";
        } else if(isPlayerAnAI() && getCurrentPlayer().getPosition() == 30) { //If AI is in jail, they will pay to leave.
            getCurrentPlayer().setPosition(10);
            return "\nPlayer " + getCurrentPlayer().getPlayerNumber() + " has paid $50 to leave jail.";
        }
        return "This player is an AI but the expected output isn't being printed.";
    }

    public int rollDie(){
        return getCurrentPlayer().rollDice();
    }

    public boolean playerIsInJail(){
        if(board.getIndex(getCurrentPlayer().getPosition()).getName().equalsIgnoreCase("Jail")){
            return true;
        }
        return false;
    }

    public void freePlayerFromJail(){
        getCurrentPlayer().setPosition(10); //Sets player position to just visiting jail.
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
            return true;
        } else if(getCurrentPlayer().getPosition() == 38){ //If player lands on Luxury Tax, they must pay $200
            getCurrentPlayer().decrementBalance(100);
            return true;
        }
        return false;
    }

    public int getSpecialPositionFee(){
        if(getCurrentPlayer().getPosition() == 4){
            return 200;
        } else if(getCurrentPlayer().getPosition() == 38){
            getCurrentPlayer().decrementBalance(100);
            return 200;
        }
        return -1;
    }

    /**
     * @author John Afolayan
     * This method sets the new position of a player after a die is rolled
     */
    public void setCurrentPlayerPosition(int pos) {
        getCurrentPlayer().setPosition((getCurrentPlayerPosition() + pos) % board.size());
    }

    /**
     * @author John Afolayan
     * This method gets the position of the current player
     */
    public int getCurrentPlayerPosition() {
        return getCurrentPlayer().getPosition();
    }

    public String getBoardName() {
        return board.getIndex(getCurrentPlayer().getPosition()).getName();
    }

    public Board getBoard() {
        return board;
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

    /**
     * @author John Afolayan
     * This method removes a banrupt player from the game.
     */
    public void removeBankruptPlayer(){
        int index = 0;
        for (final Iterator<Player> iterator = playerList.iterator(); iterator.hasNext();) {
            Player temp = iterator.next();
            if (temp.getBalance() <= 0) {
                iterator.remove();
                if(index > numberOfHumanPlayers){
                    numberOfAIPlayers -= 1;
                } else if(index <= numberOfHumanPlayers){
                    numberOfHumanPlayers -= 1;
                }
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
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void quitGame() {
        System.exit(0);
    }
}