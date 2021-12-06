package Model;

import java.io.Serializable;

public interface ModelUpdateListener extends Serializable {
    void modelUpdated();

    void dieCount(int dieRoll1, int dieRoll2, int position);

    void initializeLoadedGame(int numberOfPlayers, int playerNumber);

    void unlockPropertyBuy();

    void unlockUtilityBuy();

    void unlockRailroadBuy();

    void lockPassTurnButton();

    void unlockPassTurnButton();

    void passTurn(int playerNumber);

    void taxProperty(int tax, Player ownedBy, int playerNumber, int balance, String currency);

    void confirmPurchase(int playerNumber, String name, int balance, String currency);

    void loadingSavedGame(int playerNumber);

    void printState(int i, int balance, String toString, int balance1);

    void initializeGame(int numberOfPlayers, int playerNumber);

    void manualPassUpdate(int playerNumber);

    void returnWinner(int playerNumber);

    void displayBankruptPlayer(int playerNumber);

    void displayPlayerHasPassedGo(String currency);

    void displaySpecialPosition(String boardName, int specialPositionFee, String currency);

    void AIRepaint();

    void purchasingHouse();

    void notPurchasingAHouse();

    void cannotPurchase();

    void confirmHouseTransaction(String currency);

    void confirmHouseSold(String currency);

    void cannotSell();

    void purchasingHotel();

    void notPurchasingAHotel();

    void confirmHotelTransaction(String currency);

    void confirmHotelSold(String currency);

    void cannotPurchaseHotel();

    void cannotSellHotel();





    void sellingHouse();

    void goingToJail(int dieRoll1, int dieRoll2, int currentPlayerPosition);

    void freeFromJail(int dieRoll1, int dieRoll2, int currentPlayerPosition);

    void stayInJail(int currentPlayer);
}