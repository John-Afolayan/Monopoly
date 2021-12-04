package Model;

import java.io.Serializable;

public interface ModelUpdateListener extends Serializable {
    void modelUpdated();

    void dieCount(int dieRoll1, int dieRoll2, int position);

    void unlockPropertyBuy();

    void unlockUtilityBuy();

    void unlockRailroadBuy();

    void lockPassTurnButton();

    void unlockPassTurnButton();

    void passTurn(int playerNumber);

    void taxProperty(int tax, Player ownedBy, int playerNumber, int balance);

    void confirmPurchase(int playerNumber, String name, int balance);

    void printState(int i, int balance, String toString, int balance1);

    void initializeGame(int numberOfPlayers, int playerNumber);

    void manualPassUpdate(int playerNumber);

    void returnWinner(int playerNumber);

    void displayBankruptPlayer(int playerNumber);

    void displayPlayerHasPassedGo();

    void displaySpecialPosition();

    void AIRepaint();

    void purchasingHouse();

    void notPurchasingAHouse();

    void cannotPurchase();

    void confirmHouseTransaction();

    void confirmHouseSold();

    void cannotSell();

    void purchasingHotel();

    void notPurchasingAHotel();

    void confirmHotelTransaction();

    void confirmHotelSold();

    void cannotPurchaseHotel();

    void cannotSellHotel();

    void payToLeaveJail();




    void sellingHouse();
}