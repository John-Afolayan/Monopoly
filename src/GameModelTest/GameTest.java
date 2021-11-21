/*
package GameModelTest;

import Model.Game;
import Model.Player;

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
        assertEquals(3, game.getHumanPlayers().size());
    }

    @Test
    public void testNumOfInitializedPlayers(){
        game.initializePlayers(2);
        assertEquals(2, game.getHumanPlayers().size());
    }

    @Test
    public void testThePassingOfATurn(){
        game.startGame(3);
        game.passTurn();
        assertEquals(2, game.getCurrentPlayer().getPlayerNumber());
    }

    @Test
    public void testSettingAPlayerPosition(){
        game.startGame(3);
        game.setCurrentPlayerPosition(5);
        assertEquals(5, game.getCurrentPlayerPosition());
    }

    */
/*
    @Test
    public void testPromptingPlayerPurchase(){
        game.startGame(3);
        game.moveToken();
        assertEquals(1, game.getCurrentPlayer().getOwnedProperties().size());
    }
     *//*


    @Test
    public void testRemovalOfBankruptPlayer(){
        game.startGame(3);
        game.getCurrentPlayer().setBalance(-100);
        game.removeBankruptPlayer();
        assertEquals(2, game.getHumanPlayers().size());
    }
*/
/*
    @Test
    public void testSearchForAWinner(){
        game.startGame(2);
        game.getCurrentPlayer().setBalance(-100);
        game.checkPlayerBalance(game.getCurrentPlayer());
        assertEquals(1, game.getPlayers().size());
    }*//*



}
*/
