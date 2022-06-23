package app.bll;

import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.bll.model.MenuItem;
import app.dao.LoginInfo;
import app.dao.ProductsDataSerializator;
import app.dao.Serializator;
import app.presentation.Observer;
import app.presentation.administrator.ReportUI;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryService implements IDeliveryServiceProcessing, Serializable, Observable {
    private List<app.presentation.Observer> observersList;
    private HashMap<Order, Map<String, MenuItem>> orders;
    private Serializator orderSerializator;

    private final ProductsDataSerializator productsDataSerializator;
    private final LoginInfo loginInfo;

    public DeliveryService(LoginInfo loginInfo, ProductsDataSerializator productsDataSerializator) {
        orderSerializator = new Serializator();
        this.productsDataSerializator = productsDataSerializator;
        this.loginInfo = loginInfo;
        orders = (HashMap<Order, Map<String, MenuItem>>) orderSerializator.deserialize("orders.ser");
        if (orders == null)
            orders = new HashMap<>();

        observersList = new ArrayList<>();
    }


    @Override
    public void importBaseProductsDataFromCSV(File importFile) {
        try {
            FileReader inputFile = new FileReader(importFile);
            CSVReader csvReader = new CSVReader(inputFile);
            csvReader.readNext();
            List<BaseProduct> baseProducts = new ArrayList<>();
//
//            csvReader.readAll().stream().forEach(baseProductData ->
//                    baseProducts.add(new BaseProduct(baseProductData[0].stripLeading().stripTrailing(), Double.parseDouble(baseProductData[1]), Integer.parseInt(baseProductData[2]), Integer.parseInt(baseProductData[3]),
//                    Integer.parseInt(baseProductData[4]), Integer.parseInt(baseProductData[5]), Integer.parseInt(baseProductData[6]))));

            productsDataSerializator.getProductsData().clear();
            productsDataSerializator.setProductsData(csvReader.readAll().stream().map(strings -> new BaseProduct(strings[0].stripLeading().stripTrailing(), Double.parseDouble(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]),
                Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]))).collect(Collectors.toMap(BaseProduct::getTitle, baseProduct -> baseProduct, (product1, product2) -> product1)));

            //baseProducts.stream().forEach(baseProduct -> productsDataSerializator.getProductsData().put(baseProduct.getTitle().toLowerCase().stripLeading().stripTrailing(), baseProduct));
            productsDataSerializator.serialize();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addProduct(MenuItem baseProduct) {
        if (!productsDataSerializator.getProductsData().containsKey(baseProduct.getTitle().toLowerCase())) {
            productsDataSerializator.getProductsData().put(baseProduct.getTitle().toLowerCase(), baseProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProduct(String baseProduct) {
        if (productsDataSerializator.getProductsData().containsKey(baseProduct.toLowerCase().stripTrailing().stripLeading())) {
            productsDataSerializator.getProductsData().remove(baseProduct.toLowerCase());
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyProduct(MenuItem baseProduct) {
        if (removeProduct(baseProduct.getTitle().toLowerCase())) {
            addProduct(baseProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean addCompositeProduct(CompositeProduct compositeProduct, int customPrice) {
        if (!productsDataSerializator.getProductsData().containsKey(compositeProduct.getTitle().toLowerCase())) {
            compositeProduct.setPrice(compositeProduct.computePrice());
            if (customPrice != -1) {
                compositeProduct.setPrice(customPrice);
            }

            productsDataSerializator.getProductsData().put(compositeProduct.getTitle().toLowerCase(), compositeProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public void placeOrder(Map<String, MenuItem> products) {
        int totalPrice = 0;
        int orderID = 0;
        for (Map.Entry<String, MenuItem> product : products.entrySet()) {
            totalPrice += product.getValue().computePrice();
        }
        for (Map.Entry<Order, Map<String, MenuItem>> order : orders.entrySet()) {
            if (orderID < order.getKey().getOrderID())
                orderID = order.getKey().getOrderID();
        }
        orderID++;


        Order newOrder = new Order(orderID, loginInfo.getClientID());
        notifyObservers(totalPrice, orderID, loginInfo.getClientID());

        orders.put(newOrder, products);
        JOptionPane.showMessageDialog(null, "Order with the id #" + orderID + " from the client with the id #" + loginInfo.getClientID() + " was placed successfully!\n" +
                "Please check the BILL for further details!", "INFO", JOptionPane.INFORMATION_MESSAGE);
        orderID++;
        File bill = new File("billOrder#" + orderID + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(bill);
            StringBuilder billData = new StringBuilder("");
            billData.append("Order ID....................................................................#" + orderID);
            billData.append("\nClient ID...................................................................#" + loginInfo.getClientID());
            billData.append("\nProducts Ordered...............................................................\n");
            products.forEach((title, menuItem) -> {
                if (menuItem instanceof BaseProduct) {
                    billData.append("\t" + menuItem.getTitle() + "\n");
                } else {
                    CompositeProduct compositeProduct = (CompositeProduct) menuItem;
                    billData.append("\t" + compositeProduct.getTitle() + " MADE FROM:" + "\n");
                    compositeProduct.getCompositeProduct().forEach(product -> {
                        billData.append("\t\t" + product.getTitle() + "\n");
                    });
                }
            });
            billData.append("\nTotal Price................................................................." + totalPrice + " EUR");
            fileWriter.write(billData.toString());

            fileWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bill with the order id #" + orderID + " was not created!", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
        orderSerializator.serialize(orders, "orders.ser");
    }

    @Override
    public void registerObserver(Observer observer) {
        observersList.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observersList.remove(observer);
    }

    @Override
    public void notifyObservers(int price, int orderID, int clientID) {

        for (Observer observer : observersList) {
            observer.update(price, orderID, clientID);
        }
    }

    public HashMap<Order, Map<String, MenuItem>> getOrders() {
        return orders;
    }
}
