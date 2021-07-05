package GUI;

import DAO.OracleConn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffFrame extends JFrame {
    public StaffFrame(String staffname) {
        final int[] currPatientID = {-1};
        setTitle(staffname);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //panel for searchbar
        JPanel searPane = new JPanel();
        SpringLayout layout = new SpringLayout();
        searPane.setLayout(layout);
        searPane.setBackground(Color.WHITE);

        JLabel maKBLabel= new JLabel("Ma kham benh:", JLabel.TRAILING);
        JTextField maKBText = new JTextField(15);
        maKBLabel.setLabelFor(maKBText);
        JLabel maBNLabel = new JLabel("Ma benh nhan:", JLabel.TRAILING);
        JTextField maBNText = new JTextField(15);
        maBNLabel.setLabelFor(maBNText);
        JButton findByMaKBBtn = new JButton("Tim benh nhan");
        JButton findByMaBNBtn = new JButton("Tim benh nhan");

        findByMaKBBtn.setBackground(Color.WHITE);
        findByMaBNBtn.setBackground(Color.WHITE);

        searPane.add(maKBLabel);
        searPane.add(maKBText);
        searPane.add(maBNLabel);
        searPane.add(maBNText);
        searPane.add(findByMaKBBtn);
        searPane.add(findByMaBNBtn);

        //add
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 80;      //make this component tall
        c.weightx = 150;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(searPane, c);

        //info pane
        SpringLayout infoLayout = new SpringLayout();
        JPanel infoPane = new JPanel();
        infoPane.setLayout(infoLayout);
        JTextArea patientInfo = new JTextArea();
        patientInfo.setText("ID: \n\n" + "Ho ten: \n\n" + "Ngay sinh: \n\n" + "Dia chi: \n\n" + "So dien thoai:" );
        patientInfo.setPreferredSize(new Dimension(250, 220));
        patientInfo.setEditable(false);
        Font font = patientInfo.getFont();
        float size = font.getSize() + 5.0f;
        patientInfo.setFont( font.deriveFont(size) );
        JButton editBtn = new JButton("Edit");
        editBtn.setBackground(Color.WHITE);
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(Color.WHITE);
        JButton addBtn = new JButton("New patient");
        addBtn.setBackground(Color.WHITE);

        String column1[] = {"MAKB", "NGAYKB", "MABS", "MABN", "TINHTRANGBANDAU", "KETLUANCUABACSI"};
        DefaultTableModel model1 = new DefaultTableModel(column1, 0);
        JTable patientDoc = new JTable(model1);

        JScrollPane spane = new JScrollPane(patientDoc);
        spane.setBackground(Color.WHITE);
        spane.setPreferredSize(new Dimension(520, 200));

        infoPane.add(patientInfo);
        infoPane.add(spane);
        infoPane.add(editBtn);
        infoPane.add(deleteBtn);
        infoPane.add(addBtn);
        //layout info pane
        infoLayout.putConstraint(SpringLayout.WEST, patientInfo, 25, SpringLayout.WEST, infoPane);
        infoLayout.putConstraint(SpringLayout.NORTH, patientInfo, 5, SpringLayout.NORTH, infoPane);

        infoLayout.putConstraint(SpringLayout.WEST, spane, 50, SpringLayout.EAST, patientInfo);
        infoLayout.putConstraint(SpringLayout.NORTH, spane, 5, SpringLayout.NORTH, infoPane);

        infoLayout.putConstraint(SpringLayout.EAST, infoPane, 5, SpringLayout.EAST, spane);
        infoLayout.putConstraint(SpringLayout.SOUTH, infoPane, 5, SpringLayout.SOUTH, spane);

        infoLayout.putConstraint(SpringLayout.WEST, editBtn, -60, SpringLayout.EAST, patientInfo);
        infoLayout.putConstraint(SpringLayout.NORTH, editBtn, 250, SpringLayout.NORTH, infoPane);

        infoLayout.putConstraint(SpringLayout.WEST, deleteBtn, -140, SpringLayout.EAST, editBtn);
        infoLayout.putConstraint(SpringLayout.NORTH, deleteBtn, 250, SpringLayout.NORTH, infoPane);

        infoLayout.putConstraint(SpringLayout.WEST, addBtn, -180, SpringLayout.EAST, deleteBtn);
        infoLayout.putConstraint(SpringLayout.NORTH, addBtn, 250, SpringLayout.NORTH, infoPane);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 300;      //make this component tall
        c.weightx = 1.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(infoPane, c);

        //
        add(mainPanel);
        pack();

        //Lay out the search panel.
        layout.putConstraint(SpringLayout.EAST, findByMaKBBtn, -15, SpringLayout.EAST, searPane);
        layout.putConstraint(SpringLayout.NORTH, findByMaKBBtn, 5, SpringLayout.NORTH, searPane);

        layout.putConstraint(SpringLayout.EAST, findByMaBNBtn, -15, SpringLayout.EAST, searPane);
        layout.putConstraint(SpringLayout.NORTH, findByMaBNBtn, 35, SpringLayout.NORTH, findByMaKBBtn);

        layout.putConstraint(SpringLayout.EAST, maKBText, -15, SpringLayout.WEST, findByMaKBBtn);
        layout.putConstraint(SpringLayout.NORTH, maKBText, 5, SpringLayout.NORTH, searPane);
        layout.putConstraint(SpringLayout.EAST, maKBLabel, -10, SpringLayout.WEST, maKBText);
        layout.putConstraint(SpringLayout.NORTH, maKBLabel, 5, SpringLayout.NORTH, searPane);

        layout.putConstraint(SpringLayout.EAST, maBNText, -15, SpringLayout.WEST, findByMaKBBtn);
        layout.putConstraint(SpringLayout.NORTH, maBNText, 45, SpringLayout.NORTH, searPane);
        layout.putConstraint(SpringLayout.EAST, maBNLabel, -10, SpringLayout.WEST, maKBText);
        layout.putConstraint(SpringLayout.NORTH, maBNLabel, 45, SpringLayout.NORTH, searPane);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1200, 680);
        setPreferredSize(new Dimension(1200, 680));
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //event handle
        findByMaKBBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maKBText.getText().equals(""))
                    JOptionPane.showMessageDialog(findByMaKBBtn, "Invalid ID");
                else {
                    try {
                        int maKB = Integer.parseInt(maKBText.getText());
                        System.out.println(maKB);

                        String[] infos = OracleConn.getPatient(maKB);
                        if (infos.length > 0) {
                            patientInfo.setText("ID: " + infos[0] + "\n\n" + "Ho ten: " + infos[1] + "\n\n" +
                                    "Ngay sinh: " + infos[2] + "\n\n" + "Dia chi: " + infos[3] + "\n\n" +
                                    "So dien thoai: " + infos[4]);
                            ArrayList<Object[]> patientRecords = OracleConn.getPatientRecords(Integer.valueOf(infos[0]));
                            if (!patientRecords.isEmpty()) {
                                patientDoc.setModel(new DefaultTableModel(column1, 0));
                                DefaultTableModel t = (DefaultTableModel) patientDoc.getModel();
                                for (Object[] item : patientRecords) {
                                    t.addRow(item);
                                }
                            }
                            currPatientID[0] = Integer.parseInt(infos[0]);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(findByMaKBBtn, "Invalid ID format");
                    }
                }
            }
        });

        findByMaBNBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maBNText.getText().equals(""))
                    JOptionPane.showMessageDialog(findByMaBNBtn, "Invalid ID");
                else {
                    try {
                        int maKB = Integer.parseInt(maBNText.getText());
                        System.out.println(maKB);

                        String[] infos = OracleConn.getPatientByID(maKB);
                        if (infos.length > 0) {
                            patientInfo.setText("ID: " + infos[0] + "\n\n" + "Ho ten: " + infos[1] + "\n\n" +
                                    "Ngay sinh: " + infos[2] + "\n\n" + "Dia chi: " + infos[3] + "\n\n" +
                                    "So dien thoai: " + infos[4]);
                            ArrayList<Object[]> patientRecords = OracleConn.getPatientRecords(Integer.valueOf(infos[0]));
                            patientDoc.setModel(new DefaultTableModel(column1, 0));
                            if (!patientRecords.isEmpty()) {
                                DefaultTableModel t = (DefaultTableModel) patientDoc.getModel();
                                for (Object[] item : patientRecords) {
                                    t.addRow(item);
                                }
                            }
                            currPatientID[0] = Integer.parseInt(infos[0]);
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(findByMaBNBtn, "Invalid ID format");
                    }
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currPatientID[0] != -1) {
                    try {
                        boolean isSucceed = OracleConn.deletePatient(currPatientID[0]);
                        if(!isSucceed) {
                            JOptionPane.showMessageDialog(deleteBtn, "Can't delete patient.");
                        } else {
                            patientInfo.setText("ID: \n\n" + "Ho ten: \n\n" + "Ngay sinh: \n\n" + "Dia chi: \n\n" + "So dien thoai:" );
                            patientDoc.setModel(new DefaultTableModel(column1, 0));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(deleteBtn, "Failed to delete patient.");
                    }
                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addFrame = new AddPatientFrame();
                addFrame.setVisible(true);
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] patientInfos = patientInfo.getText().split("\n\n", -2);
                for(int i = 1; i < patientInfos.length; i++) {
                    patientInfos[i] = patientInfos[i].split(": ", 2)[1];
                    System.out.println(patientInfos[i]);
                }
                JFrame updateFrame = new EditPatientFrame(currPatientID, patientInfos[1],
                        patientInfos[2], patientInfos[3], patientInfos[4]);
                updateFrame.setVisible(true);
            }
        });
    }
}
