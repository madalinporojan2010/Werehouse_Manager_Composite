package app.presentation;

import app.dao.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Comparator;

public class Administrator implements Serializable {
    private JFrame frame;
    private JPanel dataPanel;
    private JPanel panel1;
    private JTabbedPane tabbedPanel;
    private JButton importDataButton;
    private JTable productsTable;
    private JButton addButton;
    private JButton removeButton;
    private JButton modifyButton;
    private JButton composeButton;

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
        return panel1;
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
        frame = new JFrame("Administrator Panel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(tabbedPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
