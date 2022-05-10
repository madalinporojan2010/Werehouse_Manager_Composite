package app.bll;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Order implements Serializable {
    private final int orderID;
    private final int clientID;
    private Date orderDate;

    public Order(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        orderDate = Calendar.getInstance().getTime();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
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
