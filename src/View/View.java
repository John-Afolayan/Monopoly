package View;

import Model.Game;
import Model.ModelUpdateListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View extends JFrame implements ModelUpdateListener {
    Game gameModel;
    JButton newGameButton;
    JButton rollDieButton;
    JButton passTurnButton;
    JButton quitButton;
    ArrayList<JButton> listOfCommandButtons;
    ArrayList<CircleButton> listOfPropertyButtons;
    JTextArea feedbackArea;
    JButton moveButton;
    Map<String, CircleButton> mapOfButtons = new HashMap<>();
    CircleButton carletonUni;

    public void Initialize() {
        //Initialize the View
        JFrame myFrame = new JFrame("Monopoly");
        Container root = getContentPane();
        root.setLayout(new BorderLayout());


        //The layered pane will have multiple layers in-order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(1150, 750);
        monopolyBoard monopolyBoard = new View.View.monopolyBoard();
        jLayeredPane.add(monopolyBoard, JLayeredPane.DEFAULT_LAYER);

        JPanel propertyPanel = new JPanel();
        propertyPanel.setSize(1150, 750);
        propertyPanel.setOpaque(false);
        propertyPanel.setLayout(null);
        propertyCoordinates();
        propertyPanel.add(carletonUni);

        jLayeredPane.add(propertyPanel, JLayeredPane.POPUP_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);
        listOfPropertyButtons = new ArrayList<CircleButton>();
        listOfPropertyButtons.add(carletonUni);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();
        //Creating the buttons and adding actionlistener to them
        newGameButton = new JButton("NewGame");
        rollDieButton = new JButton("Roll Die");
        passTurnButton = new JButton("PassTurn");
        quitButton = new JButton("QuitGame");
        moveButton = new JButton("Move");
        rollDieButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        moveButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(rollDieButton);
        listOfCommandButtons.add(passTurnButton);
        listOfCommandButtons.add(moveButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(newGameButton);


        feedbackArea = new JTextArea("Welcome to Monopoly! Please press New Game in order to start!\n");
        feedbackArea.setRows(4);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(rollDieButton, BorderLayout.CENTER);
        centerPanel.add(moveButton, BorderLayout.EAST);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        menuPanel.add(quitButton, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    /*
     * The JPanel is an image of the Monopoly board that we will be overlaying the components over.
     */
    class monopolyBoard extends JPanel {
        private Image image;

        public monopolyBoard() {
            this.setSize(1150, 760);
            try {
                InputStream iS = this.getClass().getClassLoader().getResourceAsStream("Monopoly.png");
                BufferedImage loadedImage = ImageIO.read(iS);
                image = loadedImage.getScaledInstance(1100, 600, Image.SCALE_DEFAULT);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(image, 0, 0, this);
        }
    }

}
