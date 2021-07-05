package GUI;

import DAO.OracleConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditPatientFrame extends JFrame {
    public EditPatientFrame(final int[] currPatientID, String newName, String newBirth, String newAddress,
                            String newPhone) {
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

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

        JButton updateBtn = new JButton("Update patient");
        updateBtn.setBackground(Color.WHITE);

        panel.add(nameLb);
        panel.add(nameText);
        panel.add(birthLb);
        panel.add(birthText);
        panel.add(addressLb);
        panel.add(addressText);
        panel.add(phoneLb);
        panel.add(phoneText);
        panel.add(updateBtn);

        add(panel);
        pack();

        layout.putConstraint(SpringLayout.WEST, nameLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLb, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, nameText, 50, SpringLayout.EAST,nameLb);
        layout.putConstraint(SpringLayout.NORTH, nameText, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, birthLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, birthLb, 30, SpringLayout.NORTH, nameText);
        layout.putConstraint(SpringLayout.WEST, birthText, 50, SpringLayout.EAST, nameLb);
        layout.putConstraint(SpringLayout.NORTH, birthText, 30, SpringLayout.NORTH, nameLb);

        layout.putConstraint(SpringLayout.WEST, addressLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addressLb, 30, SpringLayout.NORTH, birthText);
        layout.putConstraint(SpringLayout.WEST, addressText, 50, SpringLayout.EAST, nameLb);
        layout.putConstraint(SpringLayout.NORTH, addressText, 30, SpringLayout.NORTH, birthLb);

        layout.putConstraint(SpringLayout.WEST, phoneLb, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneLb, 30, SpringLayout.NORTH, addressText);
        layout.putConstraint(SpringLayout.WEST, phoneText, 50, SpringLayout.EAST, nameLb);
        layout.putConstraint(SpringLayout.NORTH, phoneText, 30, SpringLayout.NORTH, addressLb);

        layout.putConstraint(SpringLayout.WEST, updateBtn, 110, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, updateBtn, 50, SpringLayout.NORTH, phoneText);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(300, 255);
        setPreferredSize(new Dimension(300, 255));
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPatientID[0] != -1) {
                    String[] inputs = new String[4];
                    if (nameText.getText().equals("")) {
                        inputs[0] = newName;
                    } else {
                        inputs[0] = nameText.getText();
                    }
                    if (birthText.getText().equals("")) {
                        inputs[1] = newBirth;
                    } else {
                        inputs[1] = birthText.getText();
                    }
                    if (addressText.getText().equals("")) {
                        inputs[2] = newAddress;
                    } else {
                        inputs[2] = addressText.getText();
                    }
                    if (phoneText.getText().equals("")) {
                        inputs[3] = newPhone;
                    } else {
                        inputs[3] = phoneText.getText();
                    }
                    for (String item : inputs) {
                        System.out.println(item);
                    }
                        try {
                            if (!OracleConn.updatePatient(currPatientID[0], inputs[0], inputs[1],
                                    inputs[2], inputs[3])) {
                                dispose();
                            } else
                                JOptionPane.showMessageDialog(updateBtn, "Update failed");
                        } catch (SQLException ec) {
                            JOptionPane.showMessageDialog(updateBtn, "Failed to update");
                            ec.printStackTrace();
                        }
                    }
                }
        });
    }
}
