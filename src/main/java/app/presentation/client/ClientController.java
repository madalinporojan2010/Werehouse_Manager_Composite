package app.presentation.client;

import app.bll.DeliveryService;
import app.bll.model.MenuItem;
import app.dao.ProductsDataSerializator;
import app.presentation.controller.MainController;
import app.presentation.controller.verifiers.NumberVerifier;
import org.apache.commons.lang3.Range;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ClientController {
    private MainController mainController;
    private Client client;
    private SearchProductsUI searchProductsUI;
    private DefaultTableModel productsTableModel;
    private ProductsDataSerializator productsDataSerializator;
    private DeliveryService deliveryService;

    private DefaultTableModel searchResultsTableModel;

    public Client getClient() {
        return client;
    }

    public void setProductsTableModel(DefaultTableModel productsTableModel) {
        this.productsTableModel = productsTableModel;
    }

    public ClientController(MainController mainController, DefaultTableModel productsTableModel, ProductsDataSerializator productsDataSerializator, DeliveryService deliveryService) {
        this.mainController = mainController;
        this.productsTableModel = productsTableModel;
        client = new Client(productsTableModel);
        setClientUIProprieties();
        mainController.importData(client.getProductsTable());
        this.productsDataSerializator = productsDataSerializator;
        this.deliveryService = deliveryService;
    }

    private void setClientUIProprieties() {
        mainController.setToolTipText(client.getProductsTable());
        setClientUIActionListeners();
    }

    private void setSearchProductsUIProprieties() {
        setSearchProductsVerifiers();
        setSearchProductsActionListeners();
    }


    private void setSearchProductsVerifiers() {
        searchProductsUI.getRangeTextFieldMap().forEach((minTextField, maxTextField) -> {
            minTextField.setInputVerifier(new NumberVerifier(searchProductsUI));
            maxTextField.setInputVerifier(new NumberVerifier(searchProductsUI));
        });
    }

    private void setClientUIActionListeners() {
        ActionListener searchButtonListener = e -> {
            searchProductsUI = new SearchProductsUI();
            setSearchProductsUIProprieties();
        };
        ActionListener placeOrderButtonListener = e -> {
            Map<String, MenuItem> selectedProducts = new HashMap<>();
            if (client.getProductsTable().getSelectedRow() != -1) {
                for (int product : client.getProductsTable().getSelectedRows()) {
                    selectedProducts.put(client.getProductsTable().getValueAt(product, 0).toString().toLowerCase(), productsDataSerializator.getProductsData().get(client.getProductsTable().getValueAt(product, 0).toString().toLowerCase()));
                }
                deliveryService.placeOrder(selectedProducts);
            } else {
                JOptionPane.showMessageDialog(null, "Select at least one product!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        client.getSearchButton().addActionListener(searchButtonListener);
        client.getOrderButton().addActionListener(placeOrderButtonListener);
    }

    private void setRangeSlidersActionListeners() {
        ChangeListener ratingRangeSliderChangeListener = e -> {
            searchProductsUI.getMinRatingLabel().setText(String.valueOf(searchProductsUI.getRatingRangeSlider().getValue()));
            searchProductsUI.getMaxRatingLabel().setText(String.valueOf(searchProductsUI.getRatingRangeSlider().getUpperValue()));
        };
        ChangeListener caloriesRangeSliderChangeListener = e -> {
            searchProductsUI.getMinCaloriesTextField().setText(String.valueOf(searchProductsUI.getCaloriesRangeSlider().getValue()));
            searchProductsUI.getMaxCaloriesTextField().setText(String.valueOf(searchProductsUI.getCaloriesRangeSlider().getUpperValue()));
        };
        ChangeListener proteinRangeSliderChangeListener = e -> {
            searchProductsUI.getMinProteinTextField().setText(String.valueOf(searchProductsUI.getProteinRangeSlider().getValue()));
            searchProductsUI.getMaxProteinTextField().setText(String.valueOf(searchProductsUI.getProteinRangeSlider().getUpperValue()));
        };
        ChangeListener fatRangeSliderChangeListener = e -> {
            searchProductsUI.getMinFatTextField().setText(String.valueOf(searchProductsUI.getFatRangeSlider().getValue()));
            searchProductsUI.getMaxFatTextField().setText(String.valueOf(searchProductsUI.getFatRangeSlider().getUpperValue()));
        };
        ChangeListener sodiumRangeSliderChangeListener = e -> {
            searchProductsUI.getMinSodiumTextField().setText(String.valueOf(searchProductsUI.getSodiumRangeSlider().getValue()));
            searchProductsUI.getMaxSodiumTextField().setText(String.valueOf(searchProductsUI.getSodiumRangeSlider().getUpperValue()));
        };
        ChangeListener priceRangeSliderChangeListener = e -> {
            searchProductsUI.getMinPriceTextField().setText(String.valueOf(searchProductsUI.getPriceRangeSlider().getValue()));
            searchProductsUI.getMaxPriceTextField().setText(String.valueOf(searchProductsUI.getPriceRangeSlider().getUpperValue()));
        };
        searchProductsUI.getRatingRangeSlider().addChangeListener(ratingRangeSliderChangeListener);
        searchProductsUI.getCaloriesRangeSlider().addChangeListener(caloriesRangeSliderChangeListener);
        searchProductsUI.getProteinRangeSlider().addChangeListener(proteinRangeSliderChangeListener);
        searchProductsUI.getFatRangeSlider().addChangeListener(fatRangeSliderChangeListener);
        searchProductsUI.getSodiumRangeSlider().addChangeListener(sodiumRangeSliderChangeListener);
        searchProductsUI.getPriceRangeSlider().addChangeListener(priceRangeSliderChangeListener);
    }

    private void setSearchProductsActionListeners() {
        setRangeSlidersActionListeners();
        ActionListener searchExecuteListener = e -> {
            importFoundData();
            searchProductsUI.getFrame().dispose();
            JOptionPane.showMessageDialog(null, "Searching...", "INFO", JOptionPane.INFORMATION_MESSAGE);
        };
        searchProductsUI.getSearchExecuteButton().addActionListener(searchExecuteListener);
    }

    private boolean checkRanges(Vector row) {
        double rating = Double.parseDouble((String) row.elementAt(1));
        int calories = (int) row.elementAt(2);
        int protein = (int) row.elementAt(3);
        int fat = (int) row.elementAt(4);
        int sodium = (int) row.elementAt(5);
        int price = (int) row.elementAt(6);

        if (rating < Integer.parseInt(searchProductsUI.getMinRatingLabel().getText()) || rating > Integer.parseInt(searchProductsUI.getMaxRatingLabel().getText())) {
            return false;
        }

        for (Map.Entry<JTextField, JTextField> range : searchProductsUI.getRangeTextFieldMap().entrySet()) {
            int minValue = Integer.parseInt(range.getKey().getText());
            int maxValue = Integer.parseInt(range.getValue().getText());
            if (range.getKey().getName().contains("calories") && (calories < minValue || calories > maxValue))
                return false;
            else if (range.getKey().getName().contains("protein") && (protein < minValue || protein > maxValue)) {
                return false;
            } else if (range.getKey().getName().contains("fat") && (fat < minValue || fat > maxValue)) {
                return false;
            } else if (range.getKey().getName().contains("sodium") && (sodium < minValue || sodium > maxValue)) {
                return false;
            } else if (range.getKey().getName().contains("price") && (price < minValue || price > maxValue)) {
                return false;
            }
        }
        return true;
    }

    private void importFoundData() {
        searchResultsTableModel = new DefaultTableModel();
        searchResultsTableModel.setColumnIdentifiers(new Object[]{"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"});
        productsTableModel.getDataVector().forEach(row -> {
            if (((String) row.elementAt(0)).contains(searchProductsUI.getTitleTextField().getText())) {
                if (checkRanges(row)) {
                    searchResultsTableModel.addRow(row);
                }
            }
        });
        client.getProductsTable().setModel(searchResultsTableModel);
    }


}
