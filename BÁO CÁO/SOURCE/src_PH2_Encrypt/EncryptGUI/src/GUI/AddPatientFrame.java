package GUI;

import DAO.OracleConn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddPatientFrame extends JFrame {
    public AddPatientFrame() {
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        JLabel maBNLb = new JLabel("MABN: ", JLabel.TRAILING);
        JTextField maBNText = new JTextField(15);
        maBNLb.setLabelFor(maBNText);

        JLabel nameLb = new JLabel("HO TEN: ", JLabel.TRAILING);
        JTextField nameText = new JTextField(15);
        nameLb.setLabelFor(nameText);

        JLabel birthLb = new JLabel("NGAY SINH: ", JLabel.TRAILING);
        JTextField birthText = new JTextField(15);
        birthLb.setLabelFor(birthText);

        JLabel addressLb = new JLabel("DIA CHI: ", JLabel.TRAILING);
        JTextField addressText = new JTextField(15);
        addressLb.setLabelFor(addressText);

        JLabel phoneLb = new JLabel("SO DIEN THOAI: ", JLabel.TRAILING);
        JTextField phoneText = new JTextField(15);
        phoneLb.setLabelFor(phoneText);

        JButton addBtn = new JButton("Add patient");
        addBtn.setBackground(Color.WHITE);

        panel.add(maBNLb); panel.add(maBNText); panel.add(nameLb); panel.add(nameText);
        panel.add(birthLb); panel.add(birthText); panel.add(addressLb); panel.add(addressText);
        panel.add(phoneLb); panel.add(phoneText); panel.add(addBtn);

        add(panel);
        pack();

        layout.putConstraint(SpringLayout.WEST, maBNLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, maBNLb, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, maBNText, 50, SpringLayout.EAST, maBNLb);
        layout.putConstraint(SpringLayout.NORTH, maBNText, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, nameLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLb, 30, SpringLayout.NORTH, maBNLb);
        layout.putConstraint(SpringLayout.WEST, nameText, 50, SpringLayout.EAST, maBNLb);
        layout.putConstraint(SpringLayout.NORTH, nameText, 30, SpringLayout.NORTH, maBNLb);

        layout.putConstraint(SpringLayout.WEST, birthLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, birthLb, 30, SpringLayout.NORTH, nameText);
        layout.putConstraint(SpringLayout.WEST, birthText, 50, SpringLayout.EAST, maBNLb);
        layout.putConstraint(SpringLayout.NORTH, birthText, 30, SpringLayout.NORTH, nameLb);

        layout.putConstraint(SpringLayout.WEST, addressLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addressLb, 30, SpringLayout.NORTH, birthText);
        layout.putConstraint(SpringLayout.WEST, addressText, 50, SpringLayout.EAST, maBNLb);
        layout.putConstraint(SpringLayout.NORTH, addressText, 30, SpringLayout.NORTH, birthLb);

        layout.putConstraint(SpringLayout.WEST, phoneLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneLb, 30, SpringLayout.NORTH, addressText);
        layout.putConstraint(SpringLayout.WEST, phoneText, 50, SpringLayout.EAST, maBNLb);
        layout.putConstraint(SpringLayout.NORTH, phoneText, 30, SpringLayout.NORTH, addressLb);

        layout.putConstraint(SpringLayout.WEST, addBtn, 110, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addBtn, 50, SpringLayout.NORTH, phoneText);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(300, 255);
        setPreferredSize(new Dimension(300, 255));
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maBNText.getText().equals("") || nameText.getText().equals("") || birthText.getText().equals("") ||
                    addressText.getText().equals("") || phoneText.getText().equals("")) {
                    JOptionPane.showMessageDialog(addBtn, "There's an empty field.");
                } else {
                    try {
                        int maBN = Integer.parseInt(maBNText.getText());
                        System.out.println(maBN);
                        try {
                            if (!OracleConn.addPatient(maBN, nameText.getText(), birthText.getText(),
                                    addressText.getText(), phoneText.getText())) {
                                dispose();
                            } else
                                JOptionPane.showMessageDialog(addBtn, "Update failed");
                        } catch (SQLException ec) {
                            JOptionPane.showMessageDialog(addBtn, "Failed to update");
                            ec.printStackTrace();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addBtn, "Invalid ID format");
                    }
                }
            }
        });
    }
}
