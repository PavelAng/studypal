import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
package com.edu.ruse.studypal;

public class StudyPalLoginPanel extends JPanel implements ActionListener {
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton loginButton;
  
  public StudyPalLoginPanel() {
    setLayout(new GridLayout(3, 2));
    
    JLabel usernameLabel = new JLabel("Username:");
    add(usernameLabel);
    
    usernameField = new JTextField();
    add(usernameField);
    
    JLabel passwordLabel = new JLabel("Password:");
    add(passwordLabel);
    
    passwordField = new JPasswordField();
    add(passwordField);
    
    loginButton = new JButton("Login");
    loginButton.addActionListener(this);
    add(loginButton);
  }
  
  public void actionPerformed(ActionEvent e) {
    String username = usernameField.getText();
    char[] passwordChars = passwordField.getPassword();
    String password = new String(passwordChars);
    
  }
}
