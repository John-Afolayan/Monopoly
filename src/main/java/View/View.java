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
    JButton resumeGameButton;
    JButton saveGameButton;
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
                "How many human players are playing today?",
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
        monopolyBoard = new MonopolyBoard(this.gameModel.getBoard().getBoardBackgroundFileName());
        jLayeredPane.add(monopolyBoard, JLayeredPane.DEFAULT_LAYER);

        gameModel.addView(this);

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
        resumeGameButton = new JButton("Load Game");
        saveGameButton = new JButton("Save Current Game");
        quitButton = new JButton("Quit Game");
        stateButton = new JButton("State");
        saveGameButton.setEnabled(false);
        addHotelButton.setEnabled(false);
        rollDieButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        buyButton.setEnabled(false);
        stateButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        quitButton.setEnabled(false);
        addHouseButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(rollDieButton);
        listOfCommandButtons.add(saveGameButton);
        listOfCommandButtons.add(resumeGameButton);
        listOfCommandButtons.add(buyButton);
        listOfCommandButtons.add(passTurnButton);
        listOfCommandButtons.add(stateButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(newGameButton);
        listOfCommandButtons.add(addHotelButton);
        listOfCommandButtons.add(addHouseButton);


        feedbackArea = new JTextArea("Welcome to Monopoly! Please press New Game in order to start!\n");
        feedbackArea.setRows(8);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(rollDieButton, BorderLayout.CENTER);
        centerPanel.add(buyButton, BorderLayout.WEST);
        centerPanel.add(stateButton, BorderLayout.EAST);
        bottomPanel.add(addHouseButton);
        bottomPanel.add(addHotelButton);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        bottomPanel.add(resumeGameButton);
        bottomPanel.add(saveGameButton);
        bottomPanel.add(quitButton);
        menuPanel.add(bottomPanel, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(950, 650);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setBackground(){
        this.monopolyBoard.setBackgroundFromFileName(this.gameModel.getBoard().getBoardBackgroundFileName());
    }


    public void promptPropertyPurchase(){
        rollDieButton.setEnabled(false);
        int propertyPrice = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + propertyPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
        gameModel.lookingForWinner();

    }

    public void promptUtilityPurchase(){
        rollDieButton.setEnabled(false);
        int utilityPrice = ((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + utilityPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
        gameModel.lookingForWinner();

    }

    public void promptRailroadPurchase() {
        rollDieButton.setEnabled(false);
        int railroadPrice = ((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue();
        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Would you like to purchase " + gameModel.getBoardName() +
                "? It costs $" + railroadPrice + " and you currently have $" + gameModel.getCurrentPlayer().getBalance() + ".\nClick the 'Buy' button to purchase or 'Pass Turn' to move on.\n");
        gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
        gameModel.lookingForWinner();

    }

    /**
     * @author John Afolayan
     * This method taxes a player whenver they land on another player's property
     */
    public void taxProperty(){
        Player ownedBy = gameModel.whoOwnsProperty((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())); //player who owns property
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){ //If current player who lands on property doesn't own that property, tax them.
            int amount = (int) (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue() * 0.1); //amount to decrement by, 10%
            gameModel.getCurrentPlayer().decrementBalance(amount); //remove $amount from player being taxed
            ownedBy.incrementBalance(amount); //add $amount to player who owns property
            setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a property owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + amount + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
            gameModel.lookingForWinner();
        }
    }

    /**
     * @author Hamza
     * This method taxes a player whenever they land of another players utility
     */

    public void taxUtility(int tax){
        Player ownedBy = gameModel.whoOwnsUtility((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){
            gameModel.getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);
            setFeedbackArea("Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a utility owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + tax + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
            gameModel.lookingForWinner();
        }
    }

    public void taxRailroad(int tax) {
        Player ownedBy = gameModel.whoOwnsRailroad((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
        if(!ownedBy.equals(gameModel.getCurrentPlayer())){
            gameModel.getCurrentPlayer().decrementBalance(tax);
            ownedBy.incrementBalance(tax);
            setFeedbackArea("Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You've landed on a railroad owned by player "+  ownedBy.getPlayerNumber() + ". You've been taxed $" + tax + ", your new balance is $" + gameModel.getCurrentPlayer().getBalance());
            gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
            gameModel.lookingForWinner();
        }
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


    private void initialize(Controller gameController) {
        for (JButton button : listOfCommandButtons) {
            button.addActionListener(gameController);
        }
    }


    /**
     * @author John Afolayan
     * This method unlocks GUI buttons after a player specifies amount of players
     */
    public void unlockButtons() {
        resumeGameButton.setEnabled(false);
        for (JButton button : listOfCommandButtons) {
            if(!button.getText().equalsIgnoreCase("Buy") && !button.getText().equalsIgnoreCase("Load Game"))
                button.setEnabled(true);
        }
    }


    public void lockRollButton(){
        rollDieButton.setEnabled(false);
    }
    public void unlockRollDieButton(){
        rollDieButton.setEnabled(true);
    }

    public void lockBuyButton(){
        buyButton.setEnabled(false);
    }
    public void unlockBuyButton(){
        buyButton.setEnabled(true);
    }

    @Override
    public void lockPassTurnButton(){
        passTurnButton.setEnabled(false);
    }

    @Override
    public void unlockPassTurnButton(){
        passTurnButton.setEnabled(true);
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

    private void goToTheBottomOfTextField() {
        getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
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
            Integer[] choices = new Integer[9 - numberOfPlayers];
            choices[0] = 0;
            int index = 7 - numberOfPlayers;
            for (int i = numberOfPlayers; i < 8; i++) {
                choices[index+1] = 8 - i;
                index--;
            }
            int choice = askUserAboutAI(choices);
            return choice;
        }
        return 0;
    }

    public String customBoardRequest() {
        String[] choices = new String[]{"Original Board", "Tour De France", "Custom Board", };
        String choice = askUserChoiceOfBoard(choices);
        return choice;
    }

    static String askUserChoiceOfBoard(String[] choices) {
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Which Board would you like to play on?",
                "Select the Board!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

    /*
     * This method updates the model
     */
    @Override
    public void modelUpdated() {
        repaint();
    }

    @Override
    public void dieCount(int dieRoll1, int dieRoll2, int position) {
        setFeedbackArea("Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have rolled two die " + dieRoll1 + " and " + dieRoll2 + " which add up to " + (dieRoll1 + dieRoll2));
        setFeedbackArea("\nYour new position is now " + position + ": " + gameModel.getBoardName());
    }

    @Override
    public void unlockPropertyBuy() {
        unlockBuyButton();
        promptPropertyPurchase();
        lockRollButton();

    }

    @Override
    public void unlockUtilityBuy() {
        unlockBuyButton();
        promptUtilityPurchase();
        lockRollButton();

    }

    @Override
    public void unlockRailroadBuy() {
        unlockBuyButton();
        promptRailroadPurchase();
        lockRollButton();

    }

    @Override
    public void passTurn(int playerNumber) {
        setFeedbackArea("\nCurrently turn of: Player " + playerNumber + "\n");
        lockPassTurnButton();
    }

    @Override
    public void manualPassUpdate(int playerNumber) {
        setFeedbackArea("\nPlayer # " + playerNumber + " has passed their turn\n");
        goToTheBottomOfTextField();
        lockPassTurnButton();
    }

    @Override
    public void taxProperty(int tax, Player ownedBy, int playerNumber, int balance) {
        if(!ownedBy.equals(gameModel.getCurrentPlayer())) { //If current player who lands on property doesn't own that property, tax them.
            setFeedbackArea("\nPlayer " + playerNumber + ": You've landed on a property owned by player " + ownedBy.getPlayerNumber() + ". You've been taxed $" + tax + ", your new balance is $" + balance);
            gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
            gameModel.lookingForWinner();
        }
    }

    @Override
    public void confirmPurchase(int playerNumber, String name, int balance) {
        lockBuyButton();
        setFeedbackArea("\nPlayer " + playerNumber + ": Congratulations, you now own: " + name +
        "\nYour new balance is: $" + balance + "\nSpend wisely!");
    }

    @Override
    public void printState(int i, int balance, String toString, int balance1) {
        setFeedbackArea("You are player " + (i) + "\nYou own the following properties:\n"
                + toString + "\nYour current balance is " + balance);
        goToTheBottomOfTextField();
    }

    @Override
    public void initializeGame(int numberOfPlayers, int playerNumber) {
        unlockButtons();
        setFeedbackArea("A new game has begun with " + numberOfPlayers + " players\n" + "\nCurrently turn of: Player " + playerNumber + "\n");
        getNewGameButton().setEnabled(false);
    }

    @Override
    public void returnWinner(int playerNumber) {
        JOptionPane.showMessageDialog(null, "Player " + playerNumber + " has won the game! Congratulations");
    }

    @Override
    public void displayBankruptPlayer(int playerNumber) {
        JOptionPane.showMessageDialog(null, "Player " + playerNumber + ": You are now bankrupt! You have been kicked out of the game. Too bad...");
    }

    @Override
    public void displayPlayerHasPassedGo() {
        setFeedbackArea("\nCongratulations, you've passed GO! Your balance has increased by $200.");
    }

    @Override
    public void displaySpecialPosition() {
        setFeedbackArea("\nSince you landed on " + gameModel.getBoardName() + ", a fee of $" + gameModel.getSpecialPositionFee() + " has been deducted from your balance.");
    }

    @Override
    public void AIRepaint() {
        setFeedbackArea(gameModel.aiAlgorithm());
        repaint();
        lockPassTurnButton();
    }

    @Override
    public void purchasingHouse() {
        JOptionPane.showMessageDialog(this, "You are able to purchase houses!" );
    }

    @Override
    public void notPurchasingAHouse() {
        JOptionPane.showMessageDialog(this, "You cannot buy a house for " + gameModel.getBoardName() + " as it is not a property");
    }

    @Override
    public void cannotPurchase() {
        setFeedbackArea("Sorry, you cannot buy a house at the moment. Please try again later...");
    }

    @Override
    public void confirmHouseTransaction() {
        String propertyColor = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor();

        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have bought a new " + propertyColor + " house for " + gameModel.getBoardName() +
                ". Your current balance $" + gameModel.getCurrentPlayer().getBalance() + ".\n");
        gameModel.passTurn();
        checkPlayerBalance(gameModel.getCurrentPlayer());
        lookingForWinner();
    }

    @Override
    public void confirmHouseSold() {
        String propertyColor = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor();

        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have sold a  " + propertyColor + " house from " + gameModel.getBoardName() +
                ". Your current balance $" + gameModel.getCurrentPlayer().getBalance() + ".\n");
        gameModel.passTurn();
        checkPlayerBalance(gameModel.getCurrentPlayer());
        lookingForWinner();
    }

    @Override
    public void cannotSell() {
        setFeedbackArea("Sorry, you cannot sell a house at the moment. Please try again later...\n");
    }

    @Override
    public void purchasingHotel() {
        JOptionPane.showMessageDialog(this, "You are able to purchase hotels!" );
    }

    @Override
    public void cannotPurchaseHotel(){
        setFeedbackArea("Sorry, you cannot buy a hotel at the moment. Please try again later...\n");
    }

    @Override
    public void notPurchasingAHotel() {
        JOptionPane.showMessageDialog(this, "You cannot buy a hotel for " + gameModel.getBoardName() + " as it is not a property\n");
    }

    @Override
    public void confirmHotelTransaction() {
        String propertyColor = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor();

        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have bought a new " + propertyColor + " hotel for " + gameModel.getBoardName() +
                ". Your current balance $" + gameModel.getCurrentPlayer().getBalance() + ".\n");
        gameModel.passTurn();
        gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
        gameModel.lookingForWinner();
    }

    @Override
    public void confirmHotelSold() {
        String propertyColor = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor();

        setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have sold a  " + propertyColor + " hotel from " + gameModel.getBoardName() +
                ". Your current balance $" + gameModel.getCurrentPlayer().getBalance() + ".\n");
        gameModel.passTurn();
        gameModel.checkPlayerBalance(gameModel.getCurrentPlayer());
        gameModel.lookingForWinner();
    }

    @Override
    public void cannotSellHotel() {
        setFeedbackArea("Sorry, you cannot sell a hotel at the moment. Please try again later...\n");
    }

    @Override
    public void sellingHouse() {

    }

    @Override
    public void payToLeaveJail(){
            if (gameModel.playerIsInJail()) {
                lockRollButton();
                int input = JOptionPane.showConfirmDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You are in Jail. Would you like to pay $50 bail to leave?" + "\nClick yes to pay bail or no to stay in jail.", "Pay bail?", JOptionPane.YES_NO_OPTION);
                if (input == JOptionPane.YES_OPTION) {
                    gameModel.playerIsLeavingJail();
                } else if (input == JOptionPane.NO_OPTION) {
                    setFeedbackArea("\nYikes :/ another night in jail doesn't sound fun. Good luck.");
                } else {
                    setFeedbackArea("Seems like there might have been an error. Please report it to the developer.");
                }
            }
            repaint();
    }

    public String requestingHouseStatus(){
        String input = JOptionPane.showInputDialog(this, "Are you here to buy or sell a house?");
        return input;
    }

    public String requestingHotelStatus(){
        String input = JOptionPane.showInputDialog(this, "Are you here to buy or sell a hotel?");
        return input;
    }

    public void lockNewGameButton() {
        newGameButton.setEnabled(false);
    }
}