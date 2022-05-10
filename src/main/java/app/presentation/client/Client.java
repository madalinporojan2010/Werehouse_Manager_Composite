package app.presentation.client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Client {
    private JPanel panel1;
    private JTable productsTable;
    private JButton searchButton;
    private JButton orderButton;
    private JFrame frame;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Client(DefaultTableModel productsTableModel) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Client Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


}
