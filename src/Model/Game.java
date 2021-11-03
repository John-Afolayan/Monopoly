package Model;

import Game.Command;
import Game.Parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


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
        System.out.println("The current player is " + players.get(currentPlayerInt).getName() + "\n");
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
            case "buy":
                buyProperty();
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

    private void buyProperty(){

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

    private void printState() {
        /**
         * @author John Afolayan
         *
         * Print a representation of the game's state
         *
         */
        System.out.println("You are player " + (currentPlayerInt + 1) + " aka " + players.get(currentPlayerInt).getName() +"\nYou own the following properties:\n"
                + players.get(currentPlayerInt).getOwnedProperties().toString() + "\nYour current balance is " + players.get(currentPlayerInt).getBalance());
    }

    private void passTurn() {
        /**
         * @author John Afolayan
         *
         * Passes turn to the next player
         *
         */
        this.currentPlayerInt = (this.currentPlayerInt == this.numberOfPlayers - 1) ? 0 : this.currentPlayerInt + 1;
        newTurn();
    }

    private void newTurn() {
        printCurrentPlayer();
        parser.showCommands();
    }

    private void initializePlayers() {
        /**
         * @author John Afolayan
         *
         * Ask the user for the number of players that will be playing, and then
         * initializes them accordingly.
         *
         */
        Scanner sc = new Scanner(System.in);
        this.numberOfPlayers = sc.nextInt();
        boolean correctNumberOfPlayers;
        do {
            if (numberOfPlayers <= 8 && numberOfPlayers >= 2) {
                correctNumberOfPlayers = true;

            } else {
                correctNumberOfPlayers = false;
                System.out.println("The number of players allowed is 2,3,4,5,6,7 or 8 players. Please try again.");
                numberOfPlayers = sc.nextInt();
            }
        } while (!correctNumberOfPlayers);
        createPlayers(numberOfPlayers);
    }

    private void update() {
        if (this.viewer != null)
            this.viewer.modelUpdated();
    }

    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }

    private void createPlayers(int numberOfPlayers) {
        Scanner sc = new Scanner(System.in);
        players = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Hi player " + i + "! What would you like to call yourself?");
            this.newPlayerName = sc.next();
            players.add(new Player(newPlayerName, i));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void printListOfCurrentPlayerStats() {
        System.out.println("Player " + (currentPlayerInt + 1) + " currently owns the following properties: ");
        System.out.println(players.get(currentPlayerInt).getOwnedProperties().toString()); //Prints all properties which currentPlayer owns
    }

    private void moveToken() {
        /**
         * @author John Afolayan and Ibrahim Said
         *
         * Checks the syntax of the command passed and moves token n amount of times
         * where n is the value which is rolled on a dice.
         *
         */
        int x, y, z;
        x = players.get(currentPlayerInt).rollDice();
        y = players.get(currentPlayerInt).getPosition() + x;

        if (board.getBoard().size() > y){
            players.get(currentPlayerInt).setPosition(y); // if the size of the board is greater than the position + the roll, then set the current player's position to be the current position + the roll
        }
        else if (board.getBoard().size() <= y) { // if the size of the board is less than the roll + the current position, then set the player's position to be
            z = board.getBoard().size() - players.get(currentPlayerInt).getPosition();
            players.get(currentPlayerInt).setPosition(1 + (x - z));
        }

        if (board.getBoard().get(players.get(currentPlayerInt).getPosition()) instanceof Property){
            System.out.println("You have rolled 2 die that combine to " + x + ". You are currently in position " + players.get(currentPlayerInt).getPosition() + ": " + ((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())).getName());
            if(!propertyOwned((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition()))){
                promptUserToPurchase();
                checkPlayerBalance(players.get(currentPlayerInt));
            } else if(propertyOwned((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition()))){
                taxPlayer();
                passTurn();
            }
        }
        else if (board.getBoard().get(players.get(currentPlayerInt).getPosition())instanceof Square) {
            System.out.println("You have rolled 2 die that combine to " + x + ". You are currently in position " + players.get(currentPlayerInt).getPosition() + ": " + board.getBoard().get(players.get(currentPlayerInt).getPosition()).getName());
            passTurn();
        }
    }

    /**
     * @author John Afolayan
     * A method to prompt a user to purchase a property or not
     */
    public void promptUserToPurchase(){
        int propertyPrice = ((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())).getValue();
        System.out.println("This property is available for purchase! It costs $" + propertyPrice + " Would you like to purchase it?" +
                "\nEnter 'yes' to purchase it or 'no' to skip this purchase.");
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        if(input.equalsIgnoreCase("yes")){
            players.get(currentPlayerInt).addProperty((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition()));
            players.get(currentPlayerInt).decrementBalance(((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())).getValue());
            System.out.println("Congratulations, you now own property: " + (Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())
                    + ". Your new balance is: $" + players.get(currentPlayerInt).getBalance() + "\nSpend wisely!");
            passTurn();
        } else if (input.equalsIgnoreCase("no")){
            passTurn();
        } else {
            System.out.println("That command isn't recognized, please try again.");
        }
        checkPlayerBalance(players.get(currentPlayerInt));
        lookingForWinner();
    }

    /**
     * @author John Afolayan
     * This method taxes a player whenver they land on another player's property
     */
    public void taxPlayer(){
        Player ownedBy = whoOwnsProperty((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())); //player who owns property
        if(!ownedBy.equals(players.get(currentPlayerInt))){ //If current player who lands on property doesn't own that property, tax them.
            int amount = (int) (((Property) board.getBoard().get(players.get(currentPlayerInt).getPosition())).getValue() * 0.1); //amount to decrement by, 10%
            System.out.printf("You've landed on a property owned by another player: %s%n", ownedBy.getName());
            players.get(currentPlayerInt).decrementBalance(amount); //remove $amount from player being taxed
            ownedBy.incrementBalance(amount); //add $amount to player who owns property
            System.out.println("You've been taxed $" + amount + ", your new balance is $" + players.get(currentPlayerInt).getBalance());
            checkPlayerBalance(players.get(currentPlayerInt));
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
            System.out.println("You are now bankrupt! You have been kicked out of the game. Too bad...");
        }
    }

    public void lookingForWinner(){
        if (players.size() == 1){
            System.out.println(players.get(0).getName() + " has won the game! Congratulations");
            System.exit(0);
        }
    }

    public void removeBankruptPlayer(){
        for (final Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
            Player temp = iterator.next();
            ArrayList<Player> toBeRemoved = new ArrayList<>();
            if (temp.getBalance() <= 0) {
                iterator.remove();
                this.numberOfPlayers -= 1;
                this.currentPlayerInt -= 1;
            }
        }
    }

    public void play() {
        /**
         * @author John Afolayan
         *
         * The main loop of the game. Takes user(s) input until the user(s) inputs Quit.
         *
         */
        boolean startedGame = false;
        System.out.println("Welcome to Monopoly! How many players will be playing today? This version of Monopoly can hold up to 8 players.");
        do {
            try {
                startGame();
                startedGame = true;
            } catch (Exception exception) {
                System.err.println("Please enter a valid integer for the number of players. Your options are 2,3,4,5,6,7,8. ");
            }
        }
        while (!startedGame);
        boolean finished = false;
        while (!finished) {
            try {
                Command command = parser.getCommand(this.currentPlayerInt);
                finished = processCommand(command);
            } catch (Exception exception) {
                System.err.println("You have encountered an error :/\n Please report it to the developer");
                exception.printStackTrace();
            }
        }
        System.out.println("Thank you for playing Monopoly!");
    }

    public void startGame() {
        initializePlayers();
        System.out.println("There will be " + numberOfPlayers + " players this game!");
        newTurn();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}