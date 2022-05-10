package app.presentation.administrator;

import javax.swing.*;

public class ReportUI {
    private JPanel panel1;
    private JTextArea reportTextArea;
    private JButton closeButton;

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextArea getReportTextArea() {
        return reportTextArea;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public ReportUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Administrator Panel");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
