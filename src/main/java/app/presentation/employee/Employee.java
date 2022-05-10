package app.presentation.employee;

import app.presentation.Observer;

import javax.swing.*;

public class Employee {
    private JPanel panel1;
    private JList notifyList;
    private JButton closeButton;
    private JFrame frame;

    public JPanel getPanel1() {
        return panel1;
    }

    public JList getNotifyList() {
        return notifyList;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Employee() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Employee notifications");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
