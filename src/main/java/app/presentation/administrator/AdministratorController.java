package app.presentation.administrator;

import app.bll.DeliveryService;
import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.dao.ProductsDataSerializator;
import app.presentation.controller.MainController;
import app.presentation.controller.verifiers.NumberVerifier;
import app.presentation.controller.verifiers.TextVerifier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.File;

public class AdministratorController {
    private Administrator administrator;
    private File importFile = new File("products.csv");
    private DeliveryService deliveryService;
    private MainController mainController;

    private AddProductUI addBaseProductUI;
    private ModifyProductUI modifyProductUI;
    private ComposeProductUI composeProductUI;

    private DefaultTableModel productsTableModel;

    private ProductsDataSerializator productsDataSerializator;

    private ReportUI reportUI;

    public Administrator getAdministrator() {
        return administrator;
    }

    public File getImportFile() {
        return importFile;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public MainController getMainController() {
        return mainController;
    }

    public AddProductUI getAddBaseProductUI() {
        return addBaseProductUI;
    }

    public ModifyProductUI getModifyProductUI() {
        return modifyProductUI;
    }

    public ComposeProductUI getComposeProductUI() {
        return composeProductUI;
    }

    public DefaultTableModel getProductsTableModel() {
        return productsTableModel;
    }

    public ProductsDataSerializator getProductsDataSerializator() {
        return productsDataSerializator;
    }

    public void setProductsTableModel(DefaultTableModel productsTableModel) {
        this.productsTableModel = productsTableModel;
    }

    public AdministratorController(MainController mainController, DefaultTableModel productsTableModel, ProductsDataSerializator productsDataSerializator, DeliveryService deliveryService) {
        this.mainController = mainController;
        this.deliveryService = deliveryService;
        this.productsDataSerializator = productsDataSerializator;
        this.productsTableModel = productsTableModel;
        this.administrator = new Administrator(productsTableModel);
        setAdministratorProprieties();
        mainController.importData(administrator.getProductsTable());
    }

    private void setAdministratorProprieties() {
        mainController.setToolTipText(administrator.getProductsTable());
        setAdministratorActionListeners();
        setReportActionListener();
        setReportVerifiers();
    }

    private void setReportData() {
        //reportUI.getReportTextArea().setText(deliveryService.getReportData());
        StringBuilder reportString = new StringBuilder();
        System.out.println(deliveryService.getOrders());
        deliveryService.getOrders().forEach((order, menuItem) -> {
            if (administrator.getTimeIntervalRadioButton().isSelected()) {
                if(order.getOrderDate().getHours() >= Integer.parseInt(administrator.getStartHourTextField().getText()) && order.getOrderDate().getHours() <= Integer.parseInt(administrator.getEndHourTextField().getText())) {
                    reportString.append("Order with ID #" + order.getOrderID() + " from client ID" + order.getClientID() + " at: " + order.getOrderDate() + "\n");
                }
            } else if (administrator.getTheProductsRadioButton().isSelected()) {

            } else if (administrator.getTheClientsRadioButton().isSelected()) {

            } else if (administrator.getTheProductsOrderedRadioButton().isSelected()) {

            }
        });
        reportUI.getReportTextArea().setText(reportString.toString());
    }

    private void adminImportData() {
        deliveryService.importBaseProductsDataFromCSV(importFile);
        mainController.importData(administrator.getProductsTable());
    }


    private void setComposeProductProprieties() {
        setComposeProductVerifiers();
        setComposeProductActionListeners();
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

    private void setReportVerifiers() {
        administrator.getStartHourTextField().setInputVerifier(new NumberVerifier());
        administrator.getEndHourTextField().setInputVerifier(new NumberVerifier());
        administrator.getClientsOrderedMoreThanTextField().setInputVerifier(new NumberVerifier());
        administrator.getProductsOrderedMoreThanTextField().setInputVerifier(new NumberVerifier());
        administrator.getValueHigherTextField().setInputVerifier(new NumberVerifier());
    }

    private void setComposeProductVerifiers() {
        composeProductUI.getTitleTextField().setInputVerifier(new TextVerifier());
        composeProductUI.getPriceTextField().setInputVerifier(new NumberVerifier());
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

    private void setComposeProductActionListeners() {
        CompositeProduct compositeProduct = new CompositeProduct();
        ActionListener customPriceCheckBoxListener = e -> {
            composeProductUI.getPriceTextField().setEditable(false);
            if (composeProductUI.getCustomPriceCheckBox().isSelected()) {
                composeProductUI.getPriceTextField().setEditable(true);
            }
        };
        ActionListener composeProductListener = e -> {
            compositeProduct.setTitle(composeProductUI.getTitleTextField().getText());
            boolean onlyBaseProductsSelected = true;
            int customPrice = composeProductUI.getPriceTextField().isEditable() ? Integer.parseInt(composeProductUI.getPriceTextField().getText()) : -1;
            if (customPrice != -1)
                compositeProduct.setCustomPrice(true);
            for (int selectedProduct : administrator.getProductsTable().getSelectionModel().getSelectedIndices()) {
                if (productsDataSerializator.getProductsData().get(((String) administrator.getProductsTable().getValueAt(selectedProduct, 0)).toLowerCase()) instanceof BaseProduct) {
                    compositeProduct.addBaseProduct((BaseProduct) productsDataSerializator.getProductsData().get(((String) administrator.getProductsTable().getValueAt(selectedProduct, 0)).toLowerCase()));
                } else {
                    JOptionPane.showMessageDialog(null, "Please select only normal products!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    composeProductUI.getFrame().dispose();
                    onlyBaseProductsSelected = false;
                    break;
                }
            }
            if (onlyBaseProductsSelected) {
                if (deliveryService.addCompositeProduct(compositeProduct, customPrice)) {
                    JOptionPane.showMessageDialog(null, "Composite product added successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                    mainController.sortTableDataModel();
                    mainController.importData(administrator.getProductsTable());
                    composeProductUI.getFrame().dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "The composite product was not added!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        composeProductUI.getCustomPriceCheckBox().addActionListener(customPriceCheckBoxListener);
        composeProductUI.getComposeButton().addActionListener(composeProductListener);
    }

    private void setReportActionListener() {
        ActionListener reportActionListener = e -> {
            reportUI = new ReportUI();
            setReportData();
            ActionListener closeReportActionListener = f -> {
                reportUI.getFrame().dispose();
            };
            reportUI.getCloseButton().addActionListener(closeReportActionListener);
        };
        administrator.getViewReportButton().addActionListener(reportActionListener);
    }

    private void setModifyProductActionListeners() {
        ActionListener addListener = e -> {
            BaseProduct product = new BaseProduct(modifyProductUI.getTitleTextField().getText().stripTrailing().stripLeading(), Double.parseDouble(modifyProductUI.getRatingTextField().getText()), Integer.parseInt(modifyProductUI.getCaloriesTextField().getText()), Integer.parseInt(modifyProductUI.getProteinTextField().getText()),
                    Integer.parseInt(modifyProductUI.getFatTextField().getText()), Integer.parseInt(modifyProductUI.getSodiumTextField().getText()), Integer.parseInt(modifyProductUI.getPriceTextField().getText()));
            if (deliveryService.modifyProduct(product)) {
                mainController.sortTableDataModel();
                mainController.importData(administrator.getProductsTable());
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
                mainController.sortTableDataModel();
                mainController.sortTableDataModel();
                mainController.importData(administrator.getProductsTable());
                JOptionPane.showMessageDialog(null, "Product added successfully!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                addBaseProductUI.getFrame().dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Product not added, check fields/name conflict!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        addBaseProductUI.getAddBaseProductButton().addActionListener(addListener);
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
                    mainController.sortTableDataModel();
                    mainController.importData(administrator.getProductsTable());
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
            if (administrator.getProductsTable().getSelectedRow() != -1) {
                composeProductUI = new ComposeProductUI();
                setComposeProductProprieties();
            } else {
                JOptionPane.showMessageDialog(null, "Please select at least one row!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };

        administrator.getComposeButton().addActionListener(composeCompositeProductListener);
        administrator.getModifyButton().addActionListener(modifyProductListener);
        administrator.getRemoveButton().addActionListener(removeProductListener);
        administrator.getImportDataButton().addActionListener(importDataButtonListener);
        administrator.getAddButton().addActionListener(addBaseProductListener);
    }
}
