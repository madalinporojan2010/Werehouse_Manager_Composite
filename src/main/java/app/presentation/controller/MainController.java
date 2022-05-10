package app.presentation.controller;

import app.bll.DeliveryService;
import app.bll.Order;
import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.bll.model.MenuItem;
import app.bll.model.User;
import app.dao.*;
import app.presentation.Observer;
import app.presentation.administrator.*;
import app.presentation.client.ClientController;
import app.presentation.client.SearchProductsUI;
import app.presentation.controller.verifiers.TextVerifier;
import app.presentation.employee.EmployeeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class MainController implements Serializable {
    private final static MainController controller = new MainController();
    private final Authenticator authenticator;
    private AdministratorController administratorController;
    private ClientController clientController;
    private EmployeeController employeeController;
    private final LoginSerializator loginSerializator;
    private final ProductsDataSerializator productsDataSerializator;
    private final DeliveryService deliveryService;
    private final LoginInfo loginInfo;

    private SearchProductsUI searchProductsUI;

    private DefaultTableModel productsTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private DefaultListModel observerListData;
    private Serializator observerListDataSerializator;

    private static File importFile = new File("products.csv");

    private MainController() {
        authenticator = new Authenticator();

        loginSerializator = new LoginSerializator();
        loginInfo = new LoginInfo(loginSerializator);
        productsDataSerializator = new ProductsDataSerializator();
        deliveryService = new DeliveryService(loginInfo, productsDataSerializator);
        productsDataSerializator.deserialize();
        loginSerializator.deserialize();
        setAuthenticatorProprieties();
        importData(null);

        observerListDataSerializator = new Serializator();

        observerListData = (DefaultListModel) observerListDataSerializator.deserialize("obsListData.ser");
        if (observerListData == null)
            observerListData = new DefaultListModel();
    }


    private void setAuthenticatorProprieties() {
        setAuthenticatorActionListeners();
        setAuthenticatorLogInVerifiers();
    }


    private void setAuthenticatorActionListeners() {
        ActionListener showPasswordCheckBoxActionListener = e -> {
            if (authenticator.getShowPasswordCheckBox().isSelected()) {
                authenticator.getPasswordTextField().setEchoChar((char) 0);
            } else {
                authenticator.getPasswordTextField().setEchoChar('*');
            }
        };
        ActionListener registerActionListener = e -> {
            if (authenticator.getPasswordTextField().getText().isBlank() || authenticator.getUsernameTextField().getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Invalid Username/Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User();
                if (loginInfo.isAlreadyRegistered(authenticator.getUsernameTextField().getText())) {
                    JOptionPane.showMessageDialog(null, "Username already exists.\nPlease Log In!", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    user.setId(loginInfo.getNextID());
                    user.setUsername(authenticator.getUsernameTextField().getText());
                    user.setPassword(authenticator.getPasswordTextField().getText());

                    loginInfo.registerUser(user);
                    JOptionPane.showMessageDialog(null, "Registration was successful.\nPlease Log In!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            loginSerializator.serialize();
        };
        ActionListener logInActionListener = e -> {
            if (authenticator.getPasswordTextField().getText().isBlank() || authenticator.getUsernameTextField().getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Invalid Username/Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (loginInfo.isAlreadyRegistered(authenticator.getUsernameTextField().getText()) &&
                        loginInfo.isPasswordGood(authenticator.getUsernameTextField().getText(), authenticator.getPasswordTextField().getText())) {
                    loginSerializator.serialize();
                    productsDataSerializator.serialize();
                    switch (loginInfo.getRights()) {
                        case "client" -> {
                            JOptionPane.showMessageDialog(null, "Logged in successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            ClientController clientController = new ClientController(this, productsTableModel, productsDataSerializator, deliveryService);
                            EmployeeController employeeController = new EmployeeController(this);
                            deliveryService.registerObserver((price, orderID, clientID) -> {
                                observerListData.addElement("The order with ID #" + orderID + " from the client with ID #" + orderID + " costing " + price + " EUR was placed!");
                                observerListDataSerializator.serialize(observerListData, "obsListData.ser");
                                employeeController.getEmployee().getNotifyList().setModel(observerListData);
                            });
                            employeeController.getEmployee().getFrame().setVisible(false);
                            //authenticator.getFrame().dispose();
                        }
                        case "administrator" -> {
                            JOptionPane.showMessageDialog(null, "Logged in successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            AdministratorController administratorController = new AdministratorController(this, productsTableModel, productsDataSerializator, deliveryService);
                            importData(administratorController.getAdministrator().getProductsTable());
                            authenticator.getFrame().dispose();
                        }
                        case "employee" -> {
                            JOptionPane.showMessageDialog(null, "Logged in successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            EmployeeController employeeController = new EmployeeController(this);
                            observerListDataSerializator.deserialize("obsListData.ser");
                            employeeController.getEmployee().getNotifyList().setModel(observerListData);
                            System.out.println(observerListData);
                            employeeController.getEmployee().getFrame().setVisible(true);
                            //authenticator.getFrame().dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username/Password!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        authenticator.getRegisterButton().addActionListener(registerActionListener);
        authenticator.getShowPasswordCheckBox().addActionListener(showPasswordCheckBoxActionListener);
        authenticator.getLogInButton().addActionListener(logInActionListener);
    }


    private void setAuthenticatorLogInVerifiers() {
        authenticator.getPasswordTextField().setInputVerifier(new TextVerifier());
        authenticator.getUsernameTextField().setInputVerifier(new TextVerifier());
    }


    public void sortTableDataModel() {
        productsTableModel.getDataVector().sort(Comparator.comparing(o -> ((String) o.elementAt(0))));
    }


    public void importData(JTable productsTable) {
        productsTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTableModel.setColumnIdentifiers(new Object[]{"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"});
        productsDataSerializator.getProductsData().forEach((productTitle, product) -> productsTableModel.addRow(new Object[]{
                product.getTitle(), String.format("%.2f", product.computeRating()), product.computeCalories(), product.computeProtein(),
                product.computeFat(), product.computeSodium(), product.computePrice()}));
        sortTableDataModel();
        if (administratorController != null) {
            administratorController.setProductsTableModel(productsTableModel);
        }
        if (clientController != null) {
            clientController.setProductsTableModel(productsTableModel);
        }
        if (productsTable != null)
            productsTable.setModel(productsTableModel);
    }


    public void setToolTipText(JTable table) {
        table.setToolTipText(null);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() != -1) {
                for (Entry<String, MenuItem> product : productsDataSerializator.getProductsData().entrySet()) {

                    if (product.getKey().equals(table.getValueAt(table.getSelectedRow(), 0).toString().toLowerCase())) {
                        if (product.getValue() instanceof BaseProduct) {
                            table.setToolTipText(product.getValue().getTitle());
                        } else if (product.getValue() instanceof CompositeProduct) {
                            table.setToolTipText(product.getValue().toString());
                        }
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
    }
}