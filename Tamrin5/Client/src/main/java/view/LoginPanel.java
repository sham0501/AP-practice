package view;

import controller.ClientManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends MyPanel {
    private JButton loginButton, signUpButton;
    private JTextField usernameField, passwordField;

    public LoginPanel(ClientManager clientManager) {
        super(clientManager);
    }

    @Override
    protected void setContent() {
        setLoginButton();
        setSignUpButton();
        setUsernameField();
        setPasswordField();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("", Font.PLAIN, 20));
        g.drawString("Username", 390, 140);
        g.drawString("Password", 390, 240);
    }

    public void setLoginButton() {
        loginButton = new JButton();
        loginButton.addActionListener(e -> {
            clientManager.logIn(usernameField.getText(), passwordField.getText());
        });
        loginButton.setFont(new Font("", Font.PLAIN, 18));
        loginButton.setText("Log In");
        loginButton.setLayout(null);
        loginButton.setLocation(280, 320);
        loginButton.setSize(160, 60);
        add(loginButton);
    }

    public void setSignUpButton() {
        signUpButton = new JButton();
        signUpButton.addActionListener(e -> {
            clientManager.signUp(usernameField.getText(), passwordField.getText());
        });
        signUpButton.setFont(new Font("", Font.PLAIN, 18));
        signUpButton.setText("Sign Up");
        signUpButton.setLayout(null);
        signUpButton.setLocation(450, 320);
        signUpButton.setSize(160, 60);
        add(signUpButton);
    }

    public void setUsernameField() {
        usernameField = new JTextField();
        usernameField.setLayout(null);
        usernameField.setFont(new Font("", Font.PLAIN, 18));
        usernameField.setSelectionColor(Color.CYAN);
        usernameField.setCaretColor(Color.BLUE);
        usernameField.setLocation(280, 150);
        usernameField.setSize(330, 50);
        add(usernameField);
    }

    public void setPasswordField() {
        passwordField = new JPasswordField();
        passwordField.setLayout(null);
        passwordField.setFont(new Font("", Font.PLAIN, 18));
        passwordField.setSelectionColor(Color.CYAN);
        passwordField.setCaretColor(Color.BLUE);
        passwordField.setLocation(280, 250);
        passwordField.setSize(330, 50);
        add(passwordField);
    }
}