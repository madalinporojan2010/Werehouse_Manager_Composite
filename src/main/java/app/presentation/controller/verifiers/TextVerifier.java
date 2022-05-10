package app.presentation.controller.verifiers;
import javax.swing.*;

public class TextVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        if (textField.getText().contains(" ") && !textField.getName().equals("Title")) {
            JOptionPane.showMessageDialog(null, "Invalid characters in " + textField.getName() + " field!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (textField.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "The " + textField.getName() + " field is empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
