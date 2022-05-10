package app.presentation.administrator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;

public class Administrator implements Serializable {
    private JFrame frame;
    private JPanel dataPanel;
    private JPanel reportsPanel;
    private JTabbedPane tabbedPanel;
    private JButton importDataButton;
    private JTable productsTable;
    private JButton addButton;
    private JButton removeButton;
    private JButton modifyButton;
    private JButton composeButton;
    private JRadioButton timeIntervalRadioButton;
    private JRadioButton theProductsRadioButton;
    private JRadioButton theClientsRadioButton;
    private JRadioButton theProductsOrderedRadioButton;
    private JTextField startHourTextField;
    private JTextField endHourTextField;
    private JTextField productsOrderedMoreThanTextField;
    private JTextField clientsOrderedMoreThanTextField;
    private JTextField valueHigherTextField;
    private JComboBox dayOfWeekComboBox;
    private JButton viewReportButton;

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getComposeButton() {
        return composeButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getDataPanel() {
        return dataPanel;
    }

    public JPanel getPanel1() {
        return reportsPanel;
    }

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public JButton getImportDataButton() {
        return importDataButton;
    }

    public JTable getProductsTable() {
        return productsTable;
    }


    public Administrator(DefaultTableModel productsTableModel) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Administrator Panel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(tabbedPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
