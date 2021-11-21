package Model;

import Game.Parser;
import View.BoardOverlay; //Only used to get player's colour as a string

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



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
    private Player currentAIPlayer;
    private int currentPlayerInt = 0;
    private List<Player> humanPlayers;
    private List<Player> aiPlayers;
    private List<Player> combinedPlayers;
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
        humanPlayers = new ArrayList<>();
        aiPlayers = new ArrayList<>();
        combinedPlayers = new ArrayList<>();
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

    /**
     * @author John Afolayan
     * @param property Property a player lands on
     * This method checks to see if a property is owned.
     */
    public boolean propertyOwned(Property property){
        for (int i = 0; i < humanPlayers.size(); i++){ //Check for humans
            if (humanPlayers.get(i).getOwnedProperties().contains(property)){
                return true;
            }
        }
        for (int i = 0; i < aiPlayers.size(); i++){ //Check for ai list
            if (aiPlayers.get(i).getOwnedProperties().contains(property)){
                return true;
            }
        }
        return false;
    }

    public boolean utilityOwned(Utility utility){
        for (int i = 0; i < humanPlayers.size(); i++){
            if (humanPlayers.get(i).getOwnedUtility().contains(utility)){
                return true;
            }
        }
        for (int i = 0; i < aiPlayers.size(); i++){
            if (aiPlayers.get(i).getOwnedUtility().contains(utility)){
                return true;
            }
        }
        return false;
    }

    public boolean railroadsOwned(Railroad railroad){
        for (int i = 0; i < humanPlayers.size(); i++){
            if (humanPlayers.get(i).getOwnedRailroads().contains(railroad)){
                return true;
            }
        }
        for (int i = 0; i < aiPlayers.size(); i++){
            if (aiPlayers.get(i).getOwnedRailroads().contains(railroad)){
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
        for (int i = 0; i < humanPlayers.size(); i++){
            if (humanPlayers.get(i).getOwnedProperties().contains(property)){
                return humanPlayers.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsUtility(Utility utility){
        for (int i = 0; i < humanPlayers.size(); i++){
            if (humanPlayers.get(i).getOwnedUtility().contains(utility)){
                return humanPlayers.get(i);
            }
        }
        return null;
    }

    public Player whoOwnsRailroad(Railroad railroad) {
        for (int i = 0; i < humanPlayers.size(); i++) {
            if (humanPlayers.get(i).getOwnedUtility().contains(railroad)) {
                return humanPlayers.get(i);
            }
        }
        return null;
    }

    public int getUtilityRent(int diceValue){
        return utility.getValue() * diceValue;
    }

    public int getRailroadRent(){
        Player ownedBy = whoOwnsRailroad((Railroad) getBoard().getIndex(getCurrentPlayer().getPosition()));
        return ownedBy.totalRailroadsOwned() * railroad.getRent();
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
        return("\nThere are " + humanPlayers.size() + " active players in the game currently and you are player " + (currentPlayerInt + 1) + ".\nYou own the following properties: "
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
        this.currentPlayer = this.combinedPlayers.get(this.currentPlayerInt);
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
        createHumanPlayers(numberOfHumanPlayers);
        createAIPlayers(numberOfAIPlayers);
        combinedPlayers.addAll(getHumanPlayers());
        combinedPlayers.addAll(getAIPlayers());
        this.currentPlayer = combinedPlayers.get(0);
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
    public void createHumanPlayers(int numberOfPlayers) {
        humanPlayers = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            humanPlayers.add(new Player(i));
        }
    }

    /**
     * @author John Afolayan
     * This method creates the specified amount players for a new game
     */
    public void createAIPlayers(int numberOfPlayers) {
        aiPlayers = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            aiPlayers.add(new Player(numberOfHumanPlayers+i));
        }
    }

    public List<Player> getHumanPlayers() {
        return humanPlayers;
    }

    public List<Player> getAIPlayers() {
        return aiPlayers;
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

    public void moveToken() {
        /**
         * @author John Afolayan and Ibrahim Said
         *
         * Checks the syntax of the command passed and moves token n amount of times
         * where n is the value which is rolled on a dice.
         *
         */

        if (board.getIndex(getCurrentPlayer().getPosition()) instanceof Property){
            if(propertyOwned((Property) board.getIndex(getCurrentPlayer().getPosition()))){
                //taxPlayer();
                passTurn();
            }
        }
        else if (board.getIndex(getCurrentPlayer().getPosition())instanceof Square) {
            passTurn();
        }
        update();
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
        for (final Iterator<Player> iterator = humanPlayers.iterator(); iterator.hasNext();) {
            Player temp = iterator.next();
            if (temp.getBalance() <= 0) {
                iterator.remove();
                this.numberOfHumanPlayers -= 1;
                this.currentPlayerInt -= 1;
            }
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