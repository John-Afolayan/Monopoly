package Model;

import java.util.ArrayList;

/**
 * Board Class: sets up the board to be played on
 * Date: October 22, 2021
 * @author Hamza Zafar, 101158275
 * @version 1.0
 */

public class Board {

    private ArrayList<Square> board;

    public Board(){
        board = new ArrayList<>();
        buildBoard();
    }

    public void buildBoard(){
        board.add(0, new Square("Go!"));
        board.add(1, new Property("Carleton University", "brown", 200));
        board.add(2, new Property("123 Street", "brown", 200));
        board.add(3, new Square("empty"));
        board.add(4, new Property("321 Street", "blue", 500));
        board.add(5, new Square("456"));
        board.add(6, new Property("Mainland", "green", 300));
        board.add(7, new Property("New York", "green", 450));
        board.add(8, new Property("Mansion", "purple", 700));
        board.add(9, new Property("Airport", "purple", 700));
        board.add(10, new Property("Hawaii", "blue", 600));
        board.add(11, new Square("empty"));
    }

    public ArrayList<Square> getBoard() {
        return board;
    }
}