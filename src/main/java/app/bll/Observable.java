package app.bll;

import app.presentation.Observer;

public interface Observable {

     void registerObserver(Observer o);
     void unregisterObserver(Observer o);
     void notifyObservers(int price, int orderID, int clientID);
}
