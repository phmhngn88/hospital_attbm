/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anpham
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try {
            String dbURL = "jdbc:oracle:thin:@172.17.64.1:1521/HOSPITAL";
            String username = "system";
            String password = "021299";
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Connected with connection #2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
