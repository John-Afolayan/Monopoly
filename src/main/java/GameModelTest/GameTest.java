/*
package GameModelTest;


import Model.Game;
import Model.Player;

import Model.Property;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;
    private Player player;

    @Before
    public void setUp(){
        game = new Game();
        player = new Player(1);
    }

    @Test
    public void testCreationOfPLayers(){
        game.createPlayers(3);
        assertEquals(3, game.getPlayers().size());
    }

    @Test
    public void testNumOfInitializedAIPlayers(){
        game.initializePlayers(2, 4);
        assertEquals(6, game.getPlayers().size());
    }

    @Test
    public void testThePassingOfATurn(){
        game.initializePlayers(2,0);
        game.passTurn();
        assertEquals(2, game.getCurrentPlayer().getPlayerNumber());
    }

    @Test
    public void testSettingAPlayerPosition(){
        game.initializePlayers(3, 4);
        game.setCurrentPlayerPosition(5);
        assertEquals(5, game.getCurrentPlayerPosition());
    }

    @Test
    public void testingBuyingOfPlayerProperty(){
        game.initializePlayers(2,0);
        game.getCurrentPlayer().setPosition(1);
        game.getCurrentPlayer().decrementBalance(((Property) game.getBoard().getIndex(game.getCurrentPlayer().getPosition())).getValue());
        assertEquals(1440, game.getCurrentPlayer().getBalance());
    }

    @Test
    public void testingTaxingOfPlayer(){
        game.initializePlayers(2,0);
        game.checkSquare(4);
        game.makePurchase();
        game.getCurrentPlayer().setPosition(4); // set placement of player to income tax placement on board

        assertEquals(1300, game.getCurrentPlayer().getBalance());
    }

    @Test
    public void testingOwnershipOfProperty(){
        Property property = new Property("Property", "gold", 123);
        game.initializePlayers(2,0);
        game.getCurrentPlayer().addProperty(property);

        assertTrue(game.propertyOwned(property));
    }


    @Test
    public void testRemovalOfBankruptPlayer(){
        game.initializePlayers(2,4);
        game.getCurrentPlayer().setBalance(-100);
        game.removeBankruptPlayer();
        assertEquals(5, game.getPlayers().size());
    }

    @Test
    public void testSearchForAWinner(){
        game.initializePlayers(2, 0);
        game.getCurrentPlayer().setBalance(-100);
        game.checkPlayerBalance(game.getCurrentPlayer());
        assertEquals(1, game.getPlayers().size());
    }


}

 */