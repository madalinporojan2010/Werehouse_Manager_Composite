package app.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddProductUI {
    private JTextField titleTextField;
    private JButton addBaseProductButton;
    private JTextField ratingTextField;
    private JTextField caloriesTextField;
    private JTextField proteinTextField;
    private JTextField fatTextField;
    private JTextField sodiumTextField;
    private JTextField priceTextField;
    private JPanel panel1;
    private JFrame frame;

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JButton getAddBaseProductButton() {
        return addBaseProductButton;
    }

    public JTextField getRatingTextField() {
        return ratingTextField;
    }

    public JTextField getCaloriesTextField() {
        return caloriesTextField;
    }

    public JTextField getProteinTextField() {
        return proteinTextField;
    }

    public JTextField getFatTextField() {
        return fatTextField;
    }

    public JTextField getSodiumTextField() {
        return sodiumTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JFrame getFrame() {
        return frame;
    }

    public AddProductUI() {
        frame = new JFrame("Add base product");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
