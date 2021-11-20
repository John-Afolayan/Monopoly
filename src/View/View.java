package View;

import Controller.Controller;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame implements ModelUpdateListener {
    Game gameModel;
    JButton newGameButton;
    JButton rollDieButton;
    JButton passTurnButton;
    JButton buyButton;
    JButton quitButton;
    JButton addHouseButton;
    ArrayList<JButton> listOfCommandButtons;
    JTextArea feedbackArea;
    JButton stateButton;
    JButton addHotelButton;
    BoardOverlay boardOverlay;
    MonopolyBoard monopolyBoard;
    //MyPanel panel;

    public View(Game gameModel) {
        super("Monopoly");
        this.gameModel = gameModel;
        initialize();
    }

    public static void main(String[] args) {
        Game gameModel = new Game();
        View gameView = new View(gameModel);
        gameModel.setViewer(gameView);
        Controller gameController = new Controller(gameModel, gameView);
        gameView.initialize(gameController);
    }

    static int askUser(Integer[] choices) {
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "How many players are playing today?",
                "Select the number of players!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

    public void initialize() {
        //Initialize the View
        JFrame myFrame = new JFrame("Monopoly");
        Container root = getContentPane();
        root.setLayout(new BorderLayout());


        //The layered pane will have multiple layers in order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(950, 550);
        monopolyBoard = new MonopolyBoard();
        jLayeredPane.add(monopolyBoard, JLayeredPane.DEFAULT_LAYER);

        boardOverlay = new BoardOverlay(gameModel);
        jLayeredPane.add(boardOverlay, JLayeredPane.POPUP_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();

        //Creating JButtons and adding actionlisteners for them
        newGameButton = new JButton("New Game");
        rollDieButton = new JButton("Roll Die");
        buyButton = new JButton("Buy");
        addHouseButton = new JButton("Buy/Sell House");
        addHotelButton = new JButton("Buy/Sell Hotel");
        passTurnButton = new JButton("Pass Turn");
        quitButton = new JButton("Quit Game");
        stateButton = new JButton("State");
        rollDieButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        buyButton.setEnabled(false);
        addHouseButton.setEnabled(false);
        stateButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        addHotelButton.setEnabled(false);
        quitButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(rollDieButton);
        listOfCommandButtons.add(buyButton);
        listOfCommandButtons.add(passTurnButton);
        listOfCommandButtons.add(stateButton);
        listOfCommandButtons.add(addHouseButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(addHotelButton);
        listOfCommandButtons.add(newGameButton);


        feedbackArea = new JTextArea("Welcome to Monopoly! Please press New Game in order to start!\n");
        feedbackArea.setRows(8);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(rollDieButton, BorderLayout.CENTER);
        centerPanel.add(buyButton, BorderLayout.WEST);
        centerPanel.add(stateButton, BorderLayout.EAST);
        bottomPanel.add(addHouseButton, BorderLayout.EAST);
        bottomPanel.add(addHotelButton, BorderLayout.CENTER);
        menuPanel.add(passTurnButton, BorderLayout.EAST);

        bottomPanel.add(quitButton, BorderLayout.WEST);
        menuPanel.add(bottomPanel, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(950, 650);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void promptUserToPurchase(){
        lockRollDieButton(); //This doesn't work for some reason but I called it in Controller class and it worked
        int propertyPrice = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + propertyPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        checkPlayerBalance(gameModel.getCurrentPlayer());
        lookingForWinner();
        rollDieButton.setEnabled(true);
    }

    public void promptUtilityPurchase(){
        rollDieButton.setEnabled(false);
        int utilityPrice = ((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + utilityPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        checkPlayerBalance(gameModel.getCurrentPlayer());
        lookingForWinner();
        rollDieButton.setEnabled(true);
    }

    public void promptRailroadPurchase() {
        rollDieButton.setEnabled(false);
        int railroadPrice = ((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + railroadPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        checkPlayerBalance(gameModel.getCurrentPlayer());
        lookingForWinner();
        rollDieButton.setEnabled(true);
    }

    public void taxUtility(int tax){
        Player ownedBy = gameModel.whoOwnsUtility((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){
            gameModel.getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);
            JOptionPane.showMessageDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a utility owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + tax + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
        }
    }

    public void taxRailroad(int tax) {
        Player ownedBy = gameModel.whoOwnsProperty((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){
            gameModel.getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);
            JOptionPane.showMessageDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a railroad owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + tax + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
        }
    }



    /**
     * @author John Afolayan
     * This method taxes a player whenver they land on another player's property
     */
    public void taxPlayer(){
        Player ownedBy = gameModel.whoOwnsProperty((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())); //player who owns property
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){ //If current player who lands on property doesn't own that property, tax them.
            int amount = (int) (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue() * 0.1); //amount to decrement by, 10%
            int houseAmount = 0;
            int hotelAmount = 0;
            if (!((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().isEmpty()){
                for (House h: ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses()){
                    houseAmount += h.getPrice();
                }
            }
            else if (!((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().isEmpty()){
                for (Hotel h: ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels()){
                    hotelAmount += (h.getPrice() * 4);
                }
            }
            int totalAmount = amount + houseAmount + hotelAmount;
            gameModel.getCurrentPlayer().decrementBalance(totalAmount ); //remove $amount from player being taxed
            ownedBy.incrementBalance(amount); //add $amount to player who owns property
            setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a property owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + amount + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            //JOptionPane.showMessageDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a property owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + totalAmount + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
        }
    }

    public void sellAHouse(){
        String currentColor = (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor());
        if (gameModel.isAbleToPurchaseBlue() && currentColor.equals("blue")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(100);
                JOptionPane.showMessageDialog(this,"You have sold a house from this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseBrown() && currentColor.equals("brown")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a house from this brown property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseGreen() && currentColor.equals("green")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(100);
                JOptionPane.showMessageDialog(this,"You have sold a house from this green property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseLightBlue() && currentColor.equals("light blue")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a house from this light blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseYellow() && currentColor.equals("yellow")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(75);
                JOptionPane.showMessageDialog(this,"You have sold a house from this yellow property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseOrange() && currentColor.equals("orange")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(50);
                JOptionPane.showMessageDialog(this,"You have sold a house from this orange property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchaseRed() && currentColor.equals("red")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(75);
                JOptionPane.showMessageDialog(this,"You have sold a house from this red property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else if (gameModel.isAbleToPurchasePurple() && currentColor.equals("purple")) {
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a house from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a house from this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a house from " + gameModel.getBoardName());
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "You cannot purchase a house at the moment. Please try again later.");
        }
    }

    public void purchaseAHouse(){
        String currentColor = (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor());
        if (gameModel.isAbleToPurchaseBlue() && currentColor.equals("blue")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase blue houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S200");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("blue house", 200, "blue"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("blue house", 200, "blue"));
                gameModel.getCurrentPlayer().decrementBalance(200);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseBrown() && currentColor.equals("brown")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase brown houses!");
            rollDieButton.setEnabled(false);
            int input = (JOptionPane.showConfirmDialog( null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S50"));
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("brown house", 50, "brown"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("brown house", 50, "brown"));
                gameModel.getCurrentPlayer().decrementBalance(50);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this brown property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchasePurple() && currentColor.equals("purple")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase purple houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S50");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("purple house", 50, "purple"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("purple house", 50, "purple"));
                gameModel.getCurrentPlayer().decrementBalance(50);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this purple property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseOrange() && currentColor.equals("orange")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase orange houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S100");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("orange house", 100, "orange"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("orange house", 100, "orange"));
                gameModel.getCurrentPlayer().decrementBalance(100);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this orange property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseRed() && currentColor.equals("red")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase red houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S150");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("red house", 150, "red"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("red house", 150, "red"));
                gameModel.getCurrentPlayer().decrementBalance(150);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this red property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase light blue houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S50");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("light blue house", 50, "light blue"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("light blue house", 50, "light blue"));
                gameModel.getCurrentPlayer().decrementBalance(50);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this light blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());

            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseYellow() && currentColor.equals("yellow")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase yellow houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you $150");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("yellow house", 150, "yellow"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("yellow house", 150, "yellow"));
                gameModel.getCurrentPlayer().decrementBalance(150);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this yellow property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseGreen() && currentColor.equals("green")){
            JOptionPane.showMessageDialog(this, "You are now able to purchase green houses!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S200");
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHouses().add(new House("green house", 50, "green"));
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().add(new House("green house", 50, "green"));
                gameModel.getCurrentPlayer().decrementBalance(200);
                JOptionPane.showMessageDialog(this,"You have purchased a house for this green property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be buying a house for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }

        else {
            JOptionPane.showMessageDialog(this, "You cannot purchase a house at the moment. Please try again later.");
        }
    }

    public void sellAHotel(){
        String currentColor = (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor());
        if (gameModel.isAbleToPurchaseBlue() && currentColor.equals("blue")){
            JOptionPane.showMessageDialog(this, "You are now able to sell blue hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(100);
                JOptionPane.showMessageDialog(this,"You have sold a house from this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseBrown() && currentColor.equals("brown")){
            JOptionPane.showMessageDialog(this, "You are now able to sell brown hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a house from this brown property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
            JOptionPane.showMessageDialog(this, "You are now able to sell light blue hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a hotel from this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseBlue() && currentColor.equals("green")){
            JOptionPane.showMessageDialog(this, "You are now able to sell green hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(100);
                JOptionPane.showMessageDialog(this,"You have sold a hotel from this green property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseOrange() && currentColor.equals("orange")){
            JOptionPane.showMessageDialog(this, "You are now able to sell orange hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(50);
                JOptionPane.showMessageDialog(this,"You have sold a hotel from this orange property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchasePurple() && currentColor.equals("purple")){
            JOptionPane.showMessageDialog(this, "You are now able to sell purple hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel from " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(25);
                JOptionPane.showMessageDialog(this,"You have sold a hotel from this purple property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseYellow() && currentColor.equals("yellow")){
            JOptionPane.showMessageDialog(this, "You are now able to sell yellow hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel to " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(75);
                JOptionPane.showMessageDialog(this,"You have sold a hotel from this yellow property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }
        else if (gameModel.isAbleToPurchaseRed() && currentColor.equals("red")){
            JOptionPane.showMessageDialog(this, "You are now able to sell red hotels!");
            rollDieButton.setEnabled(false);
            int input = JOptionPane.showConfirmDialog(null, "Would you like to sell a hotel to " + gameModel.getBoardName());
            if (input == JOptionPane.YES_OPTION) {
                gameModel.getCurrentPlayer().getOwnedHotels().remove(0);
                ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().remove(0);
                gameModel.getCurrentPlayer().incrementBalance(75);
                JOptionPane.showMessageDialog(this,"You have sold a hotel for this red property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
            }
            if (input == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(this, "You will not be selling a hotel for " + gameModel.getBoardName());
            }
            checkPlayerBalance(gameModel.getCurrentPlayer());
            lookingForWinner();
            rollDieButton.setEnabled(true);
        }

        else {
            JOptionPane.showMessageDialog(this, "You cannot sell a hotel at the moment. Please try again later.");
        }
    }


    public void purchaseAHotel(){
        if (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().size() == 4) {
            ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().clear();
            String currentColor = (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor());
            if (gameModel.isAbleToPurchaseBlue() && currentColor.equals("blue")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase blue hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S200");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("blue hotel", 200, "blue"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("blue house", 200, "blue"));
                    gameModel.getCurrentPlayer().decrementBalance(200);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseBrown() && currentColor.equals("brown")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase brown hotels!");
                rollDieButton.setEnabled(false);
                int input = (JOptionPane.showConfirmDialog( null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S50"));
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("brown hotel", 50, "brown"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("brown house", 50, "brown"));
                    gameModel.getCurrentPlayer().decrementBalance(50);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this brown property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchasePurple() && currentColor.equals("purple")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase purple hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S50");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("purple hotel", 50, "purple"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("purple house", 50, "purple"));
                    gameModel.getCurrentPlayer().decrementBalance(50);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this purple property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseOrange() && currentColor.equals("orange")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase orange hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a house to " + gameModel.getBoardName() + "? It will cost you S100");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("orange hotel", 100, "orange"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("orange hotel", 100, "orange"));
                    gameModel.getCurrentPlayer().decrementBalance(100);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this orange property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseRed() && currentColor.equals("red")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase red hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S150");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("red house", 150, "red"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("red hotel", 150, "red"));
                    gameModel.getCurrentPlayer().decrementBalance(150);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this red property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseLightBlue() && currentColor.equals("light blue")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase light blue hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S50");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("light blue hotel", 50, "light blue"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("light blue hotel", 50, "light blue"));
                    gameModel.getCurrentPlayer().decrementBalance(50);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this light blue property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseYellow() && currentColor.equals("yellow")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase yellow hotel!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you $150");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("yellow hotel", 150, "yellow"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("yellow house", 150, "yellow"));
                    gameModel.getCurrentPlayer().decrementBalance(150);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this yellow property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
            else if (gameModel.isAbleToPurchaseGreen() && currentColor.equals("green")){
                JOptionPane.showMessageDialog(this, "You are now able to purchase green hotels!");
                rollDieButton.setEnabled(false);
                int input = JOptionPane.showConfirmDialog(null, "Would you like to add a hotel to " + gameModel.getBoardName() + "? It will cost you S200");
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.getCurrentPlayer().getOwnedHotels().add(new Hotel("green hotel", 50, "green"));
                    ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().add(new Hotel("green hotel", 50, "green"));
                    gameModel.getCurrentPlayer().decrementBalance(200);
                    JOptionPane.showMessageDialog(this,"You have purchased a hotel for this green property. Your new balance is: $" + gameModel.getCurrentPlayer().getBalance());
                }
                if (input == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(this, "You will not be buying a hotel for " + gameModel.getBoardName());
                }
                checkPlayerBalance(gameModel.getCurrentPlayer());
                lookingForWinner();
                rollDieButton.setEnabled(true);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "You cannot purchase a hotel at the moment. Please try again later.");
        }

    }

    public void lockAddHotelButton(){
        addHotelButton.setEnabled(false);
    }

    public void unlockAddHotelButton(){
        addHotelButton.setEnabled(true);
    }


    /**
     * @author Ibrahim Said
     * This method checks the balance of a player and determines if they are eliminated or not.
     */
    public void checkPlayerBalance(Player player){
        int balance = player.getBalance();
        if (balance <= 0){
            gameModel.removeBankruptPlayer();
            JOptionPane.showMessageDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You are now bankrupt! You have been kicked out of the game. Too bad...");
        }
    }

    /**
     * @author Ibrahim Said
     * This method checks if a player has won the game.
     */
    public void lookingForWinner(){
        if (gameModel.getPlayers().size() == 1){
            JOptionPane.showMessageDialog(null, "Player " + gameModel.getPlayers().get(0).getPlayerNumber() + " has won the game! Congratulations");
            System.exit(0);
        }
    }

    public void payToLeaveJail(){
        if(gameModel.playerIsInJail()){
            int input = JOptionPane.showConfirmDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You are in Jail. Would you like to pay $50 bail to leave?" + "\nClick yes to pay bail or no to stay in jail.", "Pay bail?", JOptionPane.YES_NO_OPTION);
            if(input == JOptionPane.YES_OPTION){
                gameModel.getCurrentPlayer().decrementBalance(50);
                gameModel.freePlayerFromJail();
            } else if(input == JOptionPane.NO_OPTION){
                setFeedbackArea("\nYikes :/ another night in jail doesn't sound fun. Good luck.");
            } else {
                setFeedbackArea("Seems like there might have been an error. Please report it to the developer.");
            }
        }
        repaint();
    }


    private void initialize(Controller gameController) {
        for (JButton button : listOfCommandButtons) {
            button.addActionListener(gameController);
        }
    }

    /*
     * This method updates the model
     */
    @Override
    public void modelUpdated() {
        repaint();
    }

    /**
     * @author John Afolayan
     * This method unlocks GUI buttons after a player specifies amount of players
     */
    public void unlockButtons() {
        for (JButton button : listOfCommandButtons) {
            if(!button.getText().equalsIgnoreCase("Buy"))
                button.setEnabled(true);
        }
    }

    public void lockBuyButton(){
        buyButton.setEnabled(false);
    }

    public void unlockBuyButton(){
        buyButton.setEnabled(true);
    }

    public void unlockRollDieButton(){
        rollDieButton.setEnabled(true);
    }
    public void lockRollDieButton(){
        rollDieButton.setEnabled(false);
    }

    public JTextArea getFeedbackArea() {
        return feedbackArea;
    }

    public void setFeedbackArea(String feedbackAreaText) {
        this.feedbackArea.append(feedbackAreaText);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public int numberOfPlayersRequest() {
        Integer[] choices = new Integer[]{2, 3, 4, 5, 6, 7, 8};
        int choice = askUser(choices);
        return choice;
    }

    static int askUserAboutAI(Integer[] choices) {
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "How many AI controlled players would you like to set?",
                "Select the number of AI players!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

    public int numberOfAIPlayersRequest(int numberOfPlayers) {
        if(numberOfPlayers != 8) {
            Integer[] choices = new Integer[8 - numberOfPlayers];
            int index = 7 - numberOfPlayers;
            for (int i = numberOfPlayers; i < 8; i++) {
                choices[index] = 8 - i;
                index--;
            }
            int choice = askUserAboutAI(choices);
            return choice;
        }
        return 0;
    }

}