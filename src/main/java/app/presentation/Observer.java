package app.presentation;

import app.bll.Order;
import app.bll.model.MenuItem;

import java.util.HashMap;
import java.util.Map;

public interface Observer {
    void update(int price, int orderID, int clientID);
}
