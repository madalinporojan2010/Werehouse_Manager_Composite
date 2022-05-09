package app.presentation;

import javax.swing.*;

public class ModifyProductUI {
    private JPanel panel1;
    private JTextField titleTextField;
    private JButton modifyProductTextField;
    private JTextField ratingTextField;
    private JTextField caloriesTextField;
    private JTextField proteinTextField;
    private JTextField fatTextField;
    private JTextField sodiumTextField;
    private JTextField priceTextField;
    private JFrame frame;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JButton getModifyProductTextField() {
        return modifyProductTextField;
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

    public JFrame getFrame() {
        return frame;
    }

    public ModifyProductUI() {
        frame = new JFrame("Modify product");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
