package View;

import Controller.Controller;
import Model.Game;
import Model.ModelUpdateListener;
import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * View class that displays the contents of the game
 * @author John Afolayon, Ibrahim Said, Hamza Jafar
 * @version 1.0
 */

public class View extends JFrame implements ModelUpdateListener {
    Game gameModel;
    JButton newGameButton;
    JButton rollDieButton;
    JButton passTurnButton;
    JButton quitButton;
    ArrayList<JButton> listOfCommandButtons;
    JTextArea feedbackArea;
    JButton stateButton;
    //MyPanel panel;

    public View(Game gameModel) {
        super("Monopoly");
        Initialize();
        this.gameModel = gameModel;
    }

    public static void main(String[] args) {
        Game gameModel = new Game();
        View gameView = new View(gameModel);
        gameModel.setViewer(gameView);
        Controller gameController = new Controller(gameModel, gameView);
        gameView.initialize(gameController);
    }

    /**
     * Prompts the number of players from the user
     * @param choices
     * @return number of players playing Monopoly
     */
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

    /**
     * Initializes all the Swing components of the GUI
     * @author John Afolayon
     */

    public void Initialize() {
        /*MyPanel a functionality I (John)
        was working on to show each player
        on the GUI board using a color pixel
        but did not have success with
        I may try again later.
        panel = new MyPanel();
        this.add(panel);
        this.pack();*/

        //Initialize the View
        JFrame myFrame = new JFrame("Monopoly");
        Container root = getContentPane();
        root.setLayout(new BorderLayout());


        //The layered pane will have multiple layers in-order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(950, 550);
        monopolyBoard monopolyBoard = new monopolyBoard();
        jLayeredPane.add(monopolyBoard, JLayeredPane.DEFAULT_LAYER);

        JPanel boardPanel = new JPanel();
        boardPanel.setSize(950, 550);
        boardPanel.setOpaque(false);
        boardPanel.setLayout(null);
        propertyCoordinates();

        jLayeredPane.add(boardPanel, JLayeredPane.POPUP_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();

        //Creating JButtons and adding actionlisteners for them
        newGameButton = new JButton("New Game");
        rollDieButton = new JButton("Roll Die");
        passTurnButton = new JButton("Pass Turn");
        quitButton = new JButton("Quit Game");
        stateButton = new JButton("State");
        rollDieButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        stateButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        quitButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(rollDieButton);
        listOfCommandButtons.add(passTurnButton);
        listOfCommandButtons.add(stateButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(newGameButton);


        feedbackArea = new JTextArea("Welcome to Monopoly! Please press New Game in order to start!\n");
        feedbackArea.setRows(8);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(rollDieButton, BorderLayout.CENTER);
        centerPanel.add(stateButton, BorderLayout.EAST);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        menuPanel.add(quitButton, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(950, 650);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initialize(Controller gameController) {
        for (JButton button : listOfCommandButtons) {
            button.addActionListener(gameController);
        }
    }

    /*
     * This method contains the x,y coordinates of the properties on the monopoly board
     */
    private void propertyCoordinates() {
    }

    /*
     *
     * This method updates the model
     */
    @Override
    public void modelUpdated() {

    }

    /**
     Unlocks the buttons for use after a new game is initialized
     @author John Afolayon
     */
    public void unlockButtons() {
        for (JButton button : listOfCommandButtons) {
            button.setEnabled(true);
        }
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

    /*
     * The JPanel is an image of the Monopoly board that we will be overlaying the components over.
     */
    class monopolyBoard extends JPanel {
        private Image image;

        public monopolyBoard() {
            this.setSize(950, 560);
            try {
                InputStream iS = this.getClass().getClassLoader().getResourceAsStream("Monopoly.png");
                BufferedImage loadedImage = ImageIO.read(iS);
                image = loadedImage.getScaledInstance(935, 430, Image.SCALE_AREA_AVERAGING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(image, 0, 0, this);
            graphics.drawRect(0,0,getWidth()-1, getHeight()-1);
        }
    }

}