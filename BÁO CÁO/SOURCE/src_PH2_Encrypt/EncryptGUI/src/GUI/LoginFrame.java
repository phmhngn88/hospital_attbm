package GUI;

import DAO.OracleConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public LoginFrame() {

        setDefaultLookAndFeelDecorated(true);
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        JLabel usernameL = new JLabel("Username", JLabel.TRAILING);
        JTextField usernameText = new JTextField(15);
        usernameL.setLabelFor(usernameText);

        JLabel passwordL = new JLabel("Password", JLabel.TRAILING);
        JTextField passwordText = new JTextField(15);
        passwordL.setLabelFor(passwordText);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.WHITE);


        panel.add(usernameL);
        panel.add(usernameText);
        panel.add(passwordL);
        panel.add(passwordText);
        panel.add(loginBtn);

        // Set the window to be visible as the default to be false
        add(panel);
        pack();

        //Lay out the panel.
        layout.putConstraint(SpringLayout.EAST, loginBtn, -15, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.NORTH, loginBtn, 15, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.EAST, usernameText, -15, SpringLayout.WEST, loginBtn);
        layout.putConstraint(SpringLayout.NORTH, usernameText, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, usernameL, -10, SpringLayout.WEST, usernameText);
        layout.putConstraint(SpringLayout.NORTH, usernameL, 5, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.EAST, passwordText, -15, SpringLayout.WEST, loginBtn);
        layout.putConstraint(SpringLayout.NORTH, passwordText, 35, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, passwordL, -10, SpringLayout.WEST, usernameText);
        layout.putConstraint(SpringLayout.NORTH, passwordL, 35, SpringLayout.NORTH, panel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(360, 100);
        setPreferredSize(new Dimension(360, 100));
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //event handle
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usernameText.getText().equals("") || passwordText.getText().equals(""))
                    JOptionPane.showMessageDialog(loginBtn, "Please give a pair of username and password");
                else {
                    if(OracleConn.connect(usernameText.getText(), passwordText.getText())) {
                        JFrame t = new StaffFrame(usernameText.getText());
                        t.setVisible(true);
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(loginBtn, "invalid username/password; logon denied");
                }
            }
        });
    }
}
