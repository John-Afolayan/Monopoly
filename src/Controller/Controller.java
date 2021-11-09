package Controller;

import Game.Command;
import Model.Game;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class: Controls the flow of data between the actor, View, and Model
 * @author Ibrahim Said, Hamza Zafar, John Afolayon
 * @Version 1.0
 */

public class Controller implements ActionListener {
    View gameView;
    Game gameModel;

    // class constructor
    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    /**
     * Determines the action to be performed after the event of a button is triggered
     * @author John Afolayon, Ibrahim Said, Hamza Zafar
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New Game":
                int numberOfPlayers = gameView.numberOfPlayersRequest();
                gameModel.initializePlayers(numberOfPlayers);
                gameView.unlockButtons();
                gameView.setFeedbackArea("A new game has begun with " + numberOfPlayers + " players\n" + "\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                gameView.getNewGameButton().setEnabled(false);
                break;
            case "Roll Die":
                int diceRoll = gameModel.rollDie();
                gameModel.setCurrentPlayerPosition(diceRoll);
                JOptionPane.showMessageDialog(null, "Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ": You have rolled two die that added up to " + diceRoll);
                int pos = gameModel.getCurrentPlayerPosition();
                gameView.setFeedbackArea("\nYour new position is now " + pos + ": " + gameModel.getBoardName());
                gameModel.moveToken();
                gameView.setFeedbackArea("\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                goToTheBottomOfTextField();
                break;
            case "Pass Turn":
                gameView.setFeedbackArea("\nPlayer # " + gameModel.getCurrentPlayer().getPlayerNumber() + " has passed their turn\n");
                gameModel.passTurn();
                gameView.setFeedbackArea("\n!*-----------------------------------------------NEW TURN!-------------------------------------------------------*!");
                gameView.setFeedbackArea("\nPlayer " + gameModel.getCurrentPlayer().getPlayerNumber() + " it is now your turn");
                goToTheBottomOfTextField();
                break;
            case "State":
                //gameView.setFeedbackArea("\nCurrent Player: " + gameModel.getCurrentPlayer().getPlayerNumber() + ". Properties owned: " + gameModel.getCurrentPlayer().getOwnedProperties().toString());
                gameView.setFeedbackArea(gameModel.printState()+"\n");
                goToTheBottomOfTextField();
                break;
            case "Quit Game":
                gameView.setFeedbackArea("Quit game has been called!\n");
                goToTheBottomOfTextField();
                gameModel.quitGame();
                break;
        }
    }

    /**
     * Displays the text at the bottom of the textfield of the view
     * @author John Afolayon
     */
    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}