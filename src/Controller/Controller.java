package Controller;

import Game.Command;
import Model.Game;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener {
    View gameView;
    Game gameModel;

    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "New Game":
                int numberOfPlayers = gameView.numberOfPlayersRequest();
                gameModel.initializePlayers(numberOfPlayers);
                gameView.unlockButtons();
                gameView.setFeedbackArea("A new game has begun with " + numberOfPlayers + " players. " + "\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + "\n");
                gameView.getNewGameButton().setEnabled(false);
                break;
            case "Roll Die":


                break;
            case "Pass Turn":
                gameView.setFeedbackArea("Player # " + gameModel.getCurrentPlayer().getPlayerNumber() + " has passed their turn");
                gameModel.passTurn();
                gameView.setFeedbackArea("\n" + gameModel.getCurrentPlayer().getPlayerNumber() + " it is now your turn");
                goToTheBottomOfTextField();


                break;
            case "State":
                gameView.setFeedbackArea("");
                goToTheBottomOfTextField();
                break;
            case "Quit Game":
                gameView.setFeedbackArea("Quit game has been called!\n");
                goToTheBottomOfTextField();
                gameModel.quitGame();
                break;
        }
    }

    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}
