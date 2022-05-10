package app.bll;

import java.util.Calendar;
import java.util.Date;

public class Order {
    private final int orderID;
    private final int clientID;
    private final Date orderDate;

    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        orderDate = Calendar.getInstance().getTime();
    }

    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public int hashCode() {
        return orderID + clientID + orderDate.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Order order) {
            return order.orderID == this.orderID && order.clientID == this.clientID && order.orderDate.equals(this.orderDate);
        }
        return false;
    }
}
