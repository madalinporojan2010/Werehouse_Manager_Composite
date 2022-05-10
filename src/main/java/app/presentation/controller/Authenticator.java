package app.presentation.controller;

import javax.swing.*;

public class Authenticator {
    private JPanel panel1;
    private JButton logInButton;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JCheckBox showPasswordCheckBox;
    private JButton registerButton;
    private final JFrame frame;

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public JPasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public JCheckBox getShowPasswordCheckBox() {
        return showPasswordCheckBox;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public Authenticator() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Log In");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
