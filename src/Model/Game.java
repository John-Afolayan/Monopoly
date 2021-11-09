package Model;

import Game.Command;
import Game.Parser;

import javax.swing.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 * @author John Afolayan, Hamza Zafar, Ibrahim Said
 * @version 1.1
 * This class acts as the Model of the game. Contains the contents of the user interface.
 * It also evaluates and executes the commands that the parser returns.
 */

public class Game {
    private Parser parser;
    private Property property;
    private Player currentPlayer;
    private int currentPlayerInt = 0;
    private List<Player> players;
    private ModelUpdateListener viewer;
    private int numberOfPlayers;
    private String newPlayerName;
    private InputStream inputStream;
    private Board board = new Board();
    boolean wantToQuit = false;
    public Game() {
        parser = new Parser();
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

    private boolean propertyOwned(Property property){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedProperties().contains(property)){
                return true;
            }
        }
        return false;
    }

    private Player whoOwnsProperty(Property property){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getOwnedProperties().contains(property)){
                return players.get(i);
            }
        }
        return null;
    }

    public String printState() {
        /**
         * @author John Afolayan
         *
         * Print a representation of the game's state
         *
         */
        return("You are player " + (currentPlayerInt + 1) + "\nYou own the following properties:\n"
                + getCurrentPlayer().getOwnedProperties().toString() + "\nYour current balance is " + getCurrentPlayer().getBalance());
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
        update();
    }

    private void update() {
        if (this.viewer != null)
            this.viewer.modelUpdated();
    }

    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }

    public void createPlayers(int numberOfPlayers) {
        players = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player(i));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void printListOfCurrentPlayerStats() {
        System.out.println("Player " + (currentPlayerInt + 1) + " currently owns the following properties: ");
        System.out.println(getCurrentPlayer().getOwnedProperties().toString()); //Prints all properties which currentPlayer owns
    }

    public int rollDie(){
        return getCurrentPlayer().rollDice();
    }

    public void setCurrentPlayerPosition(int pos) {
        getCurrentPlayer().setPosition((getCurrentPlayerPosition() + pos) % board.size());
    }

    public int getCurrentPlayerPosition() {
        return getCurrentPlayer().getPosition();
    }

    public String getBoardName() {
        return board.getIndex(getCurrentPlayer().getPosition()).getName();
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
            if(!propertyOwned((Property) board.getIndex(getCurrentPlayer().getPosition()))){
                promptUserToPurchase();
            } else if(propertyOwned((Property) board.getIndex(getCurrentPlayer().getPosition()))){
                taxPlayer();
                passTurn();
            }
        }
        else if (board.getIndex(getCurrentPlayer().getPosition())instanceof Square) {
            passTurn();
        }
    }

    /**
     * @author John Afolayan
     * A method to prompt a user to purchase a property or not
     */
    public void promptUserToPurchase(){
        int propertyPrice = ((Property) board.getIndex(getCurrentPlayer().getPosition())).getValue();
        int input = JOptionPane.showConfirmDialog(null, "Player " + getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + getBoardName() + "? It costs $" + propertyPrice + " and you currently have $" + getCurrentPlayer().getBalance() + ". Click yes to purchase or no to move on.", "Purchase " + getBoardName() + "?", JOptionPane.YES_NO_OPTION);
        if(input == JOptionPane.YES_OPTION){
            getCurrentPlayer().addProperty((Property) board.getIndex(getCurrentPlayer().getPosition()));
            getCurrentPlayer().decrementBalance(((Property) board.getIndex(getCurrentPlayer().getPosition())).getValue());
            JOptionPane.showMessageDialog(null, "Player " + getCurrentPlayer().getPlayerNumber() + ": Congratulations, you now own property: " + (Property) board.getIndex(getCurrentPlayer().getPosition())
                    + ". Your new balance is: $" + getCurrentPlayer().getBalance() + "\nSpend wisely!");
            checkPlayerBalance(players.get(currentPlayerInt));
            lookingForWinner();
            passTurn();
        } else if (input == JOptionPane.NO_OPTION){
            checkPlayerBalance(players.get(currentPlayerInt));
            lookingForWinner();
            passTurn();
        }
        checkPlayerBalance(players.get(currentPlayerInt));
        lookingForWinner();
    }

    /**
     * @author John Afolayan
     * This method taxes a player whenver they land on another player's property
     */
    public void taxPlayer(){
        Player ownedBy = whoOwnsProperty((Property) board.getIndex(getCurrentPlayer().getPosition())); //player who owns property
        if(!ownedBy.equals(players.get(currentPlayerInt))){ //If current player who lands on property doesn't own that property, tax them.
            int amount = (int) (((Property) board.getIndex(getCurrentPlayer().getPosition())).getValue() * 0.1); //amount to decrement by, 10%
            getCurrentPlayer().decrementBalance(amount); //remove $amount from player being taxed
            ownedBy.incrementBalance(amount); //add $amount to player who owns property
            JOptionPane.showMessageDialog(null, "Player " + getCurrentPlayer().getPlayerNumber() + ": You've landed on a property owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + amount + ", your new balance is $" + getCurrentPlayer().getBalance());
            checkPlayerBalance(getCurrentPlayer());
            lookingForWinner();
        }
    }

    /**
     * @author Ibrahim Said
     * This method checks the balance of a player and determines if they are eliminated or not.
     */
    public void checkPlayerBalance(Player player){
        int balance = player.getBalance();
        if (balance <= 0){
            removeBankruptPlayer();
            JOptionPane.showMessageDialog(null, "Player " + getCurrentPlayer().getPlayerNumber() + ": You are now bankrupt! You have been kicked out of the game. Too bad...");
        }
    }

    public void lookingForWinner(){
        if (players.size() == 1){
            JOptionPane.showMessageDialog(null, "Player " + players.get(0).getPlayerNumber() + " has won the game! Congratulations");
            System.exit(0);
        }
    }

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void startGame(int numberOfPlayers) {
        initializePlayers(numberOfPlayers);
        //System.out.println("There will be " + numberOfPlayers + " players this game!");
        newTurn();
    }

    public void quitGame() {
        System.exit(0);
    }

    /*public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }*/
}