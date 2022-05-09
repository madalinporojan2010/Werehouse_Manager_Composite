package app.presentation.verifiers;

import javax.swing.*;

public class NumberVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        try {
            if (textField.getName().equals("Rating") && Double.parseDouble(textField.getText()) < 0) {
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
