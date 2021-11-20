package Controller;

import Game.Command;
import Model.Game;
import Model.Property;
import Model.Railroad;
import Model.Utility;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener {
    View gameView;
    Game gameModel;
    int numberOfPlayers, numberOfAIPlayers, initialNumberOfPlayers;;

    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New Game":
                initialNumberOfPlayers = gameView.numberOfPlayersRequest();
                numberOfPlayers = gameView.numberOfPlayersRequest();
                numberOfAIPlayers= gameView.numberOfAIPlayersRequest(numberOfPlayers);
                gameModel.initializePlayers(numberOfPlayers);
                gameView.unlockButtons();
                gameView.setFeedbackArea("A new game has begun with " + numberOfPlayers + " players\n" + "\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                gameView.getNewGameButton().setEnabled(false);
                break;
            case "Roll Die":
                int diceRoll1 = gameModel.rollDie();
                int diceRoll2 = gameModel.rollDie();
                if(!gameModel.playerIsInJail()) { //If player is not in jail, then roll die is allowed.
                    gameModel.setCurrentPlayerPosition(diceRoll1 + diceRoll2);
                    gameView.repaint();
                    int pos = gameModel.getCurrentPlayerPosition();
                    gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have rolled two die that have added up to " + diceRoll1 + diceRoll2);
                    gameView.setFeedbackArea("\nYour new position is now " + pos + ": " + gameModel.getBoardName());
                    if(gameModel.hasPlayerPassedGo()){
                        gameView.setFeedbackArea("\nCongratulations, you've passed GO! Your balance has increased by $200.");
                    }
                    if(gameModel.hasPlayerLandedOnSpecialPosition()){
                        gameView.setFeedbackArea("\nSince you landed on " + gameModel.getBoardName() + ", a fee of $" + gameModel.getSpecialPositionFee() + " has been deducted from your balance.");
                    }
                    if(gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Property) {
                        if (!gameModel.propertyOwned((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If property landed on isn't owned
                            gameView.unlockBuyButton(); //Unlock the 'Buy' button.
                            gameView.promptUserToPurchase();
                            gameView.lockRollDieButton();
                            goToTheBottomOfTextField();
                            break;
                        } else if (gameModel.propertyOwned((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If property landed on is owned by someone else
                            gameView.taxPlayer();
                            gameModel.passTurn();
                            gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                            break;
                        }
                    }
                    else if(gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Utility){
                        if (!gameModel.utilityOwned((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If utility landed on isn't owned
                            gameView.unlockBuyButton(); //Unlock the 'Buy' button.
                            gameView.promptUtilityPurchase();
                            goToTheBottomOfTextField();
                            break;
                        }
                        else if (gameModel.utilityOwned((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If utility landed on is owned by someone else
                            int tax = gameModel.getUtilityRent(diceRoll1 + diceRoll2);
                            gameView.taxUtility(tax);
                            gameModel.passTurn();
                            gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");

                            break;
                        }
                    }
                    else if(gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Railroad) {
                        if (!gameModel.railroadsOwned((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If RailRoad landed on isn't owned
                            gameView.unlockBuyButton(); //Unlock the 'Buy' button.
                            gameView.promptRailroadPurchase();
                            goToTheBottomOfTextField();
                            break;
                        }
                        else if (gameModel.railroadsOwned((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()))) { //If Railroad landed on is owned by someone else
                            int tax = gameModel.getRailroadRent();
                            gameView.taxRailroad(tax);
                            gameModel.passTurn();
                            gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");

                            break;
                        }
                    }

                }
                gameView.unlockRollDieButton();
                gameModel.passTurn();
                gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                goToTheBottomOfTextField();
                //gameModel.moveToken();
                break;
            case "Buy":
                gameView.lockBuyButton();
                if (gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Property) {
                    String propertyColor = ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getColor();
                    switch (propertyColor) {
                        case "green": {
                            int x = gameModel.getCurrentPlayer().getGreenProperties();
                            gameModel.getCurrentPlayer().setGreenProperties(x + 1);
                            break;
                        }
                        case "red": {
                            int x = gameModel.getCurrentPlayer().getRedProperties();
                            gameModel.getCurrentPlayer().setRedProperties(x + 1);
                            break;
                        }
                        case "blue": {
                            int x = gameModel.getCurrentPlayer().getBlueProperties();
                            gameModel.getCurrentPlayer().setBlueProperties(x + 1);
                            break;
                        }
                        case "light blue": {
                            int x = gameModel.getCurrentPlayer().getLightBlueProperties();
                            gameModel.getCurrentPlayer().setLightBlueProperties(x + 1);
                            break;
                        }
                        case "yellow": {
                            int x = gameModel.getCurrentPlayer().getYellowProperties();
                            gameModel.getCurrentPlayer().setYellowProperties(x + 1);
                            break;
                        }
                        case "purple": {
                            int x = gameModel.getCurrentPlayer().getPurpleProperties();
                            gameModel.getCurrentPlayer().setPurpleProperties(x + 1);
                            break;
                        }
                        case "orange": {
                            int x = gameModel.getCurrentPlayer().getOrangeProperties();
                            gameModel.getCurrentPlayer().setOrangeProperties(x + 1);
                            break;
                        }
                        case "brown": {
                            int x = gameModel.getCurrentPlayer().getBrownProperties();
                            gameModel.getCurrentPlayer().setBrownProperties(x + 1);
                            break;
                        }
                    }
                    gameModel.getCurrentPlayer().addProperty((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
                    gameModel.getCurrentPlayer().decrementBalance(((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue());
                    gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Congratulations, you now own property: " + (Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) +
                            "\nYour new balance is: $" + gameModel.getCurrentPlayer().getBalance() + "\nSpend wisely!");
                    gameView.unlockRollDieButton();
                    gameModel.passTurn();
                    gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                }
                else if (gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Utility) {
                    gameModel.getCurrentPlayer().addUtility((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
                    gameModel.getCurrentPlayer().decrementBalance(((Utility) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue());
                    gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Congratulations, you now own Utility: " + gameModel.getBoardName() +
                            "\nYour new balance is: $" + gameModel.getCurrentPlayer().getBalance() + "\nSpend wisely!");
                    gameModel.passTurn();
                    gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                }
                else if (gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Railroad) {
                    gameModel.getCurrentPlayer().addRailroad((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()));
                    gameModel.getCurrentPlayer().decrementBalance(((Railroad) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getValue());
                    gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + ": Congratulations, you now own RailRoad: " + gameModel.getBoardName() +
                            "\nYour new balance is: $" + gameModel.getCurrentPlayer().getBalance() + "\nSpend wisely!");
                    gameModel.passTurn();
                    gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                }

                gameView.checkPlayerBalance(gameModel.getCurrentPlayer());
                gameView.lookingForWinner();
                break;
            case "Pass Turn":
                gameView.lockBuyButton();
                gameView.unlockRollDieButton();
                gameView.checkPlayerBalance(gameModel.getCurrentPlayer());
                gameView.lookingForWinner();
                gameView.setFeedbackArea("\nPlayer # " + gameModel.getCurrentPlayer().getPlayerNumber() + " has passed their turn\n");
                gameModel.passTurn();
                gameView.setFeedbackArea("\n!*-----------------------------------------------NEW TURN!-------------------------------------------------------*!");
                gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + " it is now your turn");
                goToTheBottomOfTextField();
                break;
            case "State":
                gameView.setFeedbackArea(gameModel.printState()+"\n");
                goToTheBottomOfTextField();
                break;
            case "Buy/Sell House":
                if (!(gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Property)) {
                    JOptionPane.showMessageDialog(gameView, "Sorry, this position is not a property square. You cannot buy or sell houses here");
                }
                else if (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().size() == 4){
                    JOptionPane.showMessageDialog(gameView, "Sorry, you can only add up to 4 houses to a property.");
                }
                else {
                    gameModel.checkingForHouseEligibility();
                    String input = JOptionPane.showInputDialog(null, "Are you here to buy or sell?");
                    if (input.equals("buy")){
                        gameView.purchaseAHouse();
                    }
                    else if (input.equals("sell") && !((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().isEmpty()){
                        gameView.sellAHouse();
                    }
                    else if (input.equals("sell") && ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHouses().isEmpty()) {
                        JOptionPane.showMessageDialog(gameView, "There are no houses for you to sell");
                    }
                    else {
                        JOptionPane.showMessageDialog(gameView, "This is not a valid entry. You just lost your turn...");
                    }

                }
                gameModel.clear();
                gameModel.passTurn();
                gameView.unlockRollDieButton();
                gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                break;
            case "Buy/Sell Hotel":
                if (!(gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition()) instanceof Property)) {
                    JOptionPane.showMessageDialog(gameView, "Sorry, this position is not a property square. You cannot buy or sell hotels here");
                }
                else {
                    gameModel.checkingForHouseEligibility();
                    String input = JOptionPane.showInputDialog(null, "Are you here to buy or sell?");
                    if (input.equals("buy")){
                        gameView.purchaseAHotel();
                    }
                    else if (((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().size() == 1){
                        JOptionPane.showMessageDialog(gameView, "Sorry, you can only add 1 hotel to a property at a time");
                    }
                    else if(input.equals("sell") && !((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().isEmpty()){
                        gameView.sellAHotel();
                    }
                    else if(input.equals("sell") && ((Property) gameModel.getBoard().getIndex(gameModel.getCurrentPlayer().getPosition())).getHotels().isEmpty()){
                        JOptionPane.showMessageDialog(gameView, "There are no hotels for you to sell");
                    }
                    else {
                        JOptionPane.showMessageDialog(gameView, "This is not a valid entry. You just lost your turn...");
                    }

                }
                gameModel.clear();
                gameModel.passTurn();
                gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                break;
            case "Quit Game":
                gameView.setFeedbackArea("Quitting game...\n");
                goToTheBottomOfTextField();
                gameModel.quitGame();
                break;
        }
    }

    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}
