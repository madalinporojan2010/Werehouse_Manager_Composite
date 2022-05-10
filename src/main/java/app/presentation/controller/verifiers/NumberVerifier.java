package app.presentation.controller.verifiers;

import app.presentation.client.SearchProductsUI;

import javax.swing.*;
import java.util.Map;
import java.util.stream.Collectors;

public class NumberVerifier extends InputVerifier {
    private SearchProductsUI searchProductsUI = null;

    public NumberVerifier(SearchProductsUI searchProductsUI) {
        this.searchProductsUI = searchProductsUI;
    }

    public NumberVerifier() {
        super();
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        try {
            if (searchProductsUI != null) {
                boolean invalidRange = false;
                for (Map.Entry<JTextField, JTextField> range : searchProductsUI.getRangeTextFieldMap().entrySet()) {
                    if (Integer.parseInt(range.getKey().getText()) > Integer.parseInt(range.getValue().getText())) {
                        JOptionPane.showMessageDialog(null, "Invalid ranges!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        invalidRange = true;
                    }
                    searchProductsUI.getRangeSliderList().forEach(rangeSlider -> {
                        if (rangeSlider.getName() != null && rangeSlider.getName().contains(range.getKey().getName().substring(4))) {
                            if (Integer.parseInt(range.getKey().getText()) <= 1000) {
                                rangeSlider.setValue(Integer.parseInt(range.getKey().getText()));
                                rangeSlider.setUpperValue(Integer.parseInt(range.getValue().getText()));
                            } else {
                                rangeSlider.setValue(0);
                                rangeSlider.setUpperValue(1000);
                            }
                        }
                    });
                }
                if (invalidRange)
                    return false;
            }
            if (textField.getName() != null && textField.getName().equals("Rating") && Double.parseDouble(textField.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Insert a positive value in " + textField.getName() + " field!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (Integer.parseInt(textField.getText()) < 0) {
                JOptionPane.showMessageDialog(null, "Insert a positive value in " + textField.getName() + " field!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (
                NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Invalid characters in " + textField.getName() + " field!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
