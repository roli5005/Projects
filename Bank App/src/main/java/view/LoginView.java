package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnEmployeeLogin;
    private JButton btnAdminLogin;

    public LoginView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnEmployeeLogin);
        add(btnAdminLogin);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnEmployeeLogin = new JButton("Login");
        btnAdminLogin = new JButton("Login as admin");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setEmployeeLoginButtonListener(ActionListener loginButtonListener) {
        btnEmployeeLogin.addActionListener(loginButtonListener);
    }

    public void setAdminLoginButtonListener(ActionListener actionListener){
        btnAdminLogin.addActionListener(actionListener);
    }

    public void setRegisterButtonListener(ActionListener LoginAdminButtonListener) {
        btnAdminLogin.addActionListener(LoginAdminButtonListener);
    }

    public void setVisible() {
        this.setVisible(true);
    }

}
