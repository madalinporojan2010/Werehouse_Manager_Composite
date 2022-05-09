package app.presentation;

import app.bll.DeliveryService;
import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.bll.model.MenuItem;
import app.bll.model.User;
import app.dao.*;
import app.presentation.verifiers.TextVerifier;
import app.presentation.verifiers.NumberVerifier;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class Controller implements Serializable {
    private final static Controller controller = new Controller();
    private final Authenticator authenticator;
    private Administrator administrator;
    private Client client;
    private Employee employee;
    private final LoginSerializator loginSerializator;
    private final ProductsDataSerializator productsDataSerializator;
    private final DeliveryService deliveryService;
    private final LoginInfo loginInfo;

    private AddProductUI addBaseProductUI;
    private ModifyProductUI modifyProductUI;

    private DefaultTableModel productsTableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private static File importFile = new File("products.csv");

    private Controller() {
        authenticator = new Authenticator();

        loginSerializator = new LoginSerializator();
        loginInfo = new LoginInfo(loginSerializator);
        productsDataSerializator = new ProductsDataSerializator();
        deliveryService = new DeliveryService(productsDataSerializator);
        productsDataSerializator.deserialize();
        loginSerializator.deserialize();
        setAuthenticatorProprieties();
    }

    private void setModifyProductProprieties() {
        modifyProductUI.getTitleTextField().setEditable(false);
        setModifyProductVerifiers();
        setModifyProductActionListeners();
    }

    private void setAddBaseProductProprieties() {
        setAddBaseProductVerifiers();
        setAddBaseProductActionListeners();
    }

    private void setAdministratorProprieties() {
        setToolTipText(administrator.getProductsTable());
        setAdministratorActionListeners();
    }

    private void setAuthenticatorProprieties() {
        setAuthenticatorActionListeners();
        setAuthenticatorLogInVerifiers();
    }

    private void setModifyProductActionListeners() {
        ActionListener addListener = e -> {
            BaseProduct product = new BaseProduct(modifyProductUI.getTitleTextField().getText().stripTrailing().stripLeading(), Double.parseDouble(modifyProductUI.getRatingTextField().getText()), Integer.parseInt(modifyProductUI.getCaloriesTextField().getText()), Integer.parseInt(modifyProductUI.getProteinTextField().getText()),
                    Integer.parseInt(modifyProductUI.getFatTextField().getText()), Integer.parseInt(modifyProductUI.getSodiumTextField().getText()), Integer.parseInt(modifyProductUI.getPriceTextField().getText()));
            if (deliveryService.modifyProduct(product)) {
                sortTableDataModel();
                importDataToTableModel();
                JOptionPane.showMessageDialog(null, "Product modified successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                modifyProductUI.getFrame().dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Product not modified, check fields/name conflict!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        modifyProductUI.getModifyProductTextField().addActionListener(addListener);
    }

    private void setAddBaseProductActionListeners() {
        ActionListener addListener = e -> {
            BaseProduct product = new BaseProduct(addBaseProductUI.getTitleTextField().getText().stripTrailing().stripLeading(), Double.parseDouble(addBaseProductUI.getRatingTextField().getText()), Integer.parseInt(addBaseProductUI.getCaloriesTextField().getText()), Integer.parseInt(addBaseProductUI.getProteinTextField().getText()),
                    Integer.parseInt(addBaseProductUI.getFatTextField().getText()), Integer.parseInt(addBaseProductUI.getSodiumTextField().getText()), Integer.parseInt(addBaseProductUI.getPriceTextField().getText()));
            if (deliveryService.addProduct(product)) {
                sortTableDataModel();
                sortTableDataModel();
                importDataToTableModel();
                JOptionPane.showMessageDialog(null, "Product added successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                addBaseProductUI.getFrame().dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Product not added, check fields/name conflict!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        addBaseProductUI.getAddBaseProductButton().addActionListener(addListener);
    }

    private void setAuthenticatorActionListeners() {
        ActionListener showPasswordCheckBoxActionListener = e -> {
            if (authenticator.getShowPasswordCheckBox().isSelected()) {
                authenticator.getPasswordTextField().setEchoChar((char) 0);
            } else {
                authenticator.getPasswordTextField().setEchoChar('•');
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
                            client = new Client();
                            authenticator.getFrame().dispose();
                        }
                        case "administrator" -> {
                            JOptionPane.showMessageDialog(null, "Logged in successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            administrator = new Administrator(productsTableModel);
                            importDataToTableModel();
                            setAdministratorProprieties();
                            authenticator.getFrame().dispose();
                        }
                        case "employee" -> {
                            JOptionPane.showMessageDialog(null, "Logged in successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            employee = new Employee();
                            authenticator.getFrame().dispose();
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

    private void setAdministratorActionListeners() {
        ActionListener importDataButtonListener = e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("./"));
            jFileChooser.showOpenDialog(null);
            if (jFileChooser.getSelectedFile() != null) {
                if (jFileChooser.getSelectedFile().getName().endsWith(".csv"))
                    importFile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                else
                    JOptionPane.showMessageDialog(null, "Please select a CSV file!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            adminImportData();
        };
        ActionListener addBaseProductListener = e -> {
            addBaseProductUI = new AddProductUI();
            setAddBaseProductProprieties();
        };
        ActionListener removeProductListener = e -> {
            if (administrator.getProductsTable().getSelectedRow() != -1) {
                if (deliveryService.removeProduct((String) administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 0))) {
                    String removedProductTitle = (String) administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 0);
                    sortTableDataModel();
                    importDataToTableModel();
                    JOptionPane.showMessageDialog(null, "The '" + removedProductTitle + "' was removed successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "The '" + productsTableModel.getValueAt(administrator.getProductsTable().getSelectedRow(), 0) + "' was NOT removed", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        ActionListener modifyProductListener = e -> {
            if (administrator.getProductsTable().getSelectedRow() != -1) {
                modifyProductUI = new ModifyProductUI();
                modifyProductUI.getTitleTextField().setText((String) administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 0));
                modifyProductUI.getRatingTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 1)));
                modifyProductUI.getCaloriesTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 2)));
                modifyProductUI.getProteinTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 3)));
                modifyProductUI.getFatTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 4)));
                modifyProductUI.getSodiumTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 5)));
                modifyProductUI.getPriceTextField().setText(String.valueOf(administrator.getProductsTable().getValueAt(administrator.getProductsTable().getSelectedRow(), 6)));
                setModifyProductProprieties();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };

        ActionListener composeCompositeProductListener = e -> {
            CompositeProduct compositeProduct = new CompositeProduct("\"\"");
            compositeProduct.addBaseProduct(new BaseProduct("honey", 2,1,1,1,1,1));
            compositeProduct.addBaseProduct(new BaseProduct("milk", 1,1,1,1,1,1));
            productsDataSerializator.getProductsData().put("\"\"", compositeProduct);
            productsDataSerializator.serialize();
            importDataToTableModel();
        };

        administrator.getComposeButton().addActionListener(composeCompositeProductListener);
        administrator.getModifyButton().addActionListener(modifyProductListener);
        administrator.getRemoveButton().addActionListener(removeProductListener);
        administrator.getImportDataButton().addActionListener(importDataButtonListener);
        administrator.getAddButton().addActionListener(addBaseProductListener);
    }

    private void setAuthenticatorLogInVerifiers() {
        authenticator.getPasswordTextField().setInputVerifier(new TextVerifier());
        authenticator.getUsernameTextField().setInputVerifier(new TextVerifier());
    }

    private void setModifyProductVerifiers() {
        modifyProductUI.getRatingTextField().setInputVerifier(new NumberVerifier());
        modifyProductUI.getTitleTextField().setInputVerifier(new TextVerifier());
        modifyProductUI.getCaloriesTextField().setInputVerifier(new NumberVerifier());
        modifyProductUI.getProteinTextField().setInputVerifier(new NumberVerifier());
        modifyProductUI.getFatTextField().setInputVerifier(new NumberVerifier());
        modifyProductUI.getSodiumTextField().setInputVerifier(new NumberVerifier());
        modifyProductUI.getPriceTextField().setInputVerifier(new NumberVerifier());

    }

    private void setAddBaseProductVerifiers() {
        addBaseProductUI.getTitleTextField().setInputVerifier(new TextVerifier());
        addBaseProductUI.getRatingTextField().setInputVerifier(new NumberVerifier());
        addBaseProductUI.getCaloriesTextField().setInputVerifier(new NumberVerifier());
        addBaseProductUI.getProteinTextField().setInputVerifier(new NumberVerifier());
        addBaseProductUI.getFatTextField().setInputVerifier(new NumberVerifier());
        addBaseProductUI.getSodiumTextField().setInputVerifier(new NumberVerifier());
        addBaseProductUI.getPriceTextField().setInputVerifier(new NumberVerifier());
    }

    private void sortTableDataModel() {
        productsTableModel.getDataVector().sort(Comparator.comparing(o -> ((String) o.elementAt(0))));
    }


    private void importDataToTableModel() {
        productsTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTableModel.setColumnIdentifiers(new Object[]{"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"});
        productsDataSerializator.getProductsData().forEach((productTitle, product) -> productsTableModel.addRow(new Object[]{
                product.getTitle(), product.computeRating(), product.computeCalories(), product.computeProtein(),
                product.computeFat(), product.computeSodium(), product.computePrice()}));
        sortTableDataModel();
        administrator.getProductsTable().setModel(productsTableModel);
    }

    private void adminImportData() {
        deliveryService.importBaseProductsDataFromCSV(importFile);
        importDataToTableModel();
    }

    private void setToolTipText(JTable table) {
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