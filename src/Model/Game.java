package Model;

import Game.Command;
import Game.Parser;
import View.BoardOverlay; //Only used to get player's colour as a string

import java.io.InputStream;
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
    private int currentPlayerInt = 0;
    private List<Player> players;
    private Utility utility;
    private Railroad railroad;
    private ModelUpdateListener viewer;
    private int numberOfPlayers;
    private int numberOfAIPlayers;
    private String newPlayerName;
    private InputStream inputStream;
    private Board board = new Board();
    boolean wantToQuit = false;
    boolean ableToPurchaseRed = false;
    boolean ableToPurchaseBlue = false;
    boolean ableToPurchaseGreen = false;
    boolean ableToPurchaseLightBlue = false;
    boolean ableToPurchasePurple = false;
    boolean ableToPurchaseOrange = false;
    boolean ableToPurchaseBrown = false;
    boolean ableToPurchaseYellow = false;
    private List<Player> aiPlayers;
    private Player currentAIPlayer;




    public Game() {
        parser = new Parser();
        players = new ArrayList<>();
        aiPlayers = new ArrayList<>();
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

    private void printCurrentPlayer() {
        System.out.println("\n!*-----------------------------------------------NEW TURN!-------------------------------------------------------*!");
        System.out.println("The current player is " + getCurrentPlayer().getName() + "\n");
    }

    public boolean processCommand(Command command) {

        if (command.isUnknown()) {
            System.out.println("Unknown command");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "move":
                moveToken();
                break;
            case "pass":
                passTurn();
                break;
            case "state":
                printState();
                break;
            case "quit":
                wantToQuit = true;
                break;
        }

        return wantToQuit;
    }

    /**
     * @author John Afolayan
     * @param property Property a player lands on
     * This method checks to see if a property is owned.
     */
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

    public Player whoOwnsRailroad(Railroad railroad) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getOwnedRailroads().contains(railroad)) {
                return players.get(i);
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
        return("\nThere are " + players.size() + " active players in the game currently and you are player " + (currentPlayerInt + 1) + ".\nYou own the following properties: "
                + getCurrentPlayer().getOwnedProperties().toString() + "\nYour current balance is $" + getCurrentPlayer().getBalance() + " and your color on the board is " + BoardOverlay.getPlayerColor(currentPlayerInt + 1));
    }

    public void passTurn() {
        /**
         * @author John Afolayan
         *
         * Passes turn to the next player
         *
         */
        this.currentPlayerInt = (this.currentPlayerInt == (this.numberOfPlayers - 1)) ? 0 : this.currentPlayerInt + 1;
        this.currentPlayer = this.players.get(this.currentPlayerInt);
        //newTurn();
    }

    private void newTurn() {
        printCurrentPlayer();
        //parser.showCommands();
    }

    public void initializePlayers(int numberOfPlayers) {
        /**
         * @author John Afolayan
         *
         * Ask the user for the number of players that will be playing, and then
         * initializes them accordingly.
         *
         */

        this.numberOfPlayers = numberOfPlayers;
        createPlayers(numberOfPlayers);
        this.currentPlayer = players.get(0);
    }

    public void initializeAIPlayers(int numberOfAIPlayers) {
        this.numberOfAIPlayers = numberOfAIPlayers;
        createAIPlayers(numberOfAIPlayers);
        this.currentAIPlayer = aiPlayers.get(0);
    }

    private void update() {
        if (this.viewer != null)
            this.viewer.modelUpdated();
    }

    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }

    public void createAIPlayers(int numberOfAIPlayers){
        aiPlayers = new ArrayList<Player>();
        for (int i = 1; i <= numberOfAIPlayers; i++){
            aiPlayers.add(new Player(i));
        }
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

    public void mergingPlayersList(){
        players.addAll(aiPlayers);
    }

    public List<Player> getAIPlayers(){
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
        if(getCurrentPlayer().getPosition() == 4){
            getCurrentPlayer().decrementBalance(200);
            return true;
        } else if(getCurrentPlayer().getPosition() == 38){
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
        for (final Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
            Player temp = iterator.next();
            if (temp.getBalance() <= 0) {
                iterator.remove();
                this.numberOfPlayers -= 1;
                this.currentPlayerInt -= 1;
            }
        }
    }

    public int getCurrentPlayerInt() {
        return currentPlayerInt;
    }

    /**
     * @author John Afolayan
     * This method returns the current player of the game.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void startGame(int numberOfPlayers) {
        initializePlayers(numberOfPlayers);
        newTurn();
    }

    public void quitGame() {
        System.exit(0);
    }
}