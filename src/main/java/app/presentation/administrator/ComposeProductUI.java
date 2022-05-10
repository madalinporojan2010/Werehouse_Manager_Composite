package app.presentation.administrator;

import javax.swing.*;

public class ComposeProductUI {
    private JTextField priceTextField;
    private JPanel panel1;
    private JTextField titleTextField;
    private JCheckBox customPriceCheckBox;
    private JButton composeButton;
    private JFrame frame;

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JCheckBox getCustomPriceCheckBox() {
        return customPriceCheckBox;
    }

    public JButton getComposeButton() {
        return composeButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public ComposeProductUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Compose product");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
