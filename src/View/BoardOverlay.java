package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @author John Afolayan
 * A class which represents a player's coordinate on the Monopoly board.
 */
public class BoardOverlay extends JPanel {
    Game game;
    HashMap<String, Point> boardLocations = new HashMap<String, Point>();

    public BoardOverlay(Game gameModel) {
        setSize(950, 550);
        setOpaque(false);
        setLayout(null);
        this.game = gameModel;
        storeHashmapLocations();
    }

    /**
     * Stores the coordinates of every square on the monopoly board into a hashmap
     */
    public void storeHashmapLocations(){
        Point coord;
        for(int i = 0; i < game.getBoard().size(); i++){
            String boardName = game.getBoard().getIndex(i).getName();
            if(boardName.equalsIgnoreCase("Go!")){
                coord = new Point(857, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Mediterranean Ave")){
                coord = new Point(757, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 1")){
                coord = new Point(680, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Baltic Ave")){
                coord = new Point(600, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Income Tax")){
                coord = new Point(525, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Reading Railroad")){
                coord = new Point(445, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Oriental Ave")){
                coord = new Point(380, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 2")){
                coord = new Point(295, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Vermont Ave")){
                coord = new Point(220, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Connecticut Ave")){
                coord = new Point(145, 395);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Jail")){
                coord = new Point(70, 385);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Just Visiting Jail")){
                coord = new Point(5, 410);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("St. Charles Place")){
                coord = new Point(30, 345);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Electric Company")){
                coord = new Point(30, 310);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("States Ave")){
                coord = new Point(30, 275);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Virginia Ave")){
                coord = new Point(30, 240);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Pennsylvania Railroad")){
                coord = new Point(30, 205);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("St. James Place")){
                coord = new Point(30, 170);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 3")){
                coord = new Point(30, 135);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Tennessee Ave")){
                coord = new Point(30, 98);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("New York Ave")){
                coord = new Point(30, 65);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Free Parking")){
                coord = new Point(50, 25);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Kentucky Ave")){
                coord = new Point(145, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 4")){
                coord = new Point(220, 20);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Indiana Ave")){
                coord = new Point(300, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Illinois Ave")){
                coord = new Point(375, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("B. & O. Railroad")){
                coord = new Point(450, 30);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Atlantic Ave")){
                coord = new Point(528, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Ventnor Ave")){
                coord = new Point(605, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Water Works")){
                coord = new Point(680, 15);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Marvin Gardens")){
                coord = new Point(755, 10);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Go to jail")){
                coord = new Point(70, 385);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Pacific Ave")){
                coord = new Point(875, 67);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 5")){
                coord = new Point(860, 135);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Pennsylvania Ave")){
                coord = new Point(875, 170);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Short Line")){
                coord = new Point(865, 205);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("empty 6")){
                coord = new Point(860, 240);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Park Place")){
                coord = new Point(875, 275);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Luxury Tax")){
                coord = new Point(860, 313);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            } else if(boardName.equalsIgnoreCase("Boardwalk")){
                coord = new Point(875, 348);
                boardLocations.put(game.getBoard().getIndex(i).getName(), coord);
            }
        }
    }

    public Color setPlayerColor(int num){
        if(num == 1){
            return Color.WHITE;
        } else if(num == 2){
            return Color.CYAN;
        } else if(num == 3){
            return Color.YELLOW;
        } else if(num == 4){
            return Color.MAGENTA;
        } else if(num == 5){
            return Color.GREEN;
        } else if(num == 6){
            return Color.ORANGE;
        } else if(num == 7){
            return Color.RED;
        } else if(num == 8){
            return Color.BLUE;
        }
        return Color.WHITE;
    }

    public static String getPlayerColor(int num){
        if(num == 1){
            return "WHITE";
        } else if(num == 2){
            return "CYAN";
        } else if(num == 3){
            return "YELLOW";
        } else if(num == 4){
            return "MAGENTA";
        } else if(num == 5){
            return "GREEN";
        } else if(num == 6){
            return "ORANGE";
        } else if(num == 7){
            return "RED";
        } else if(num == 8){
            return "BLUE";
        }
        return "WHITE";
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //Draw a circle for the amount of players in the game
        for (int i = 0; i < game.getHumanPlayers().size(); i++) {
            int intPos = game.getHumanPlayers().get(i).getPosition(); //Get the integer position of the current player
            String positionName = game.getBoard().getIndex(intPos).getName(); //Get the name of the square which player is currently on
            if (boardLocations.containsKey(positionName)) {
                graphics.drawOval(boardLocations.get(positionName).x, boardLocations.get(positionName).y, getWidth() / 30, getHeight() / 30);
                graphics.fillOval(boardLocations.get(positionName).x, boardLocations.get(positionName).y, getWidth() / 30, getHeight() / 30);
                graphics.setColor(setPlayerColor(game.getHumanPlayers().get(i).getPlayerNumber())); //Passes player's number as parameter for getPlayerColor() which has a player's assigned color
            }
        }
    }
}