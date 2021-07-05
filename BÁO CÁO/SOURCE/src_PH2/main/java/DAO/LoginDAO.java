/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.LoginInfo;
import Model.ObjectAudit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author anpham
 */
public class LoginDAO {
    public static boolean login(String username, String password) {
        Connection conn = null;
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Connected to database");
                LoginInfo.setUserName(username);
                LoginInfo.setPassWord(password);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static boolean loginNhanVien(String username, String password) {
        Connection conn = null;
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                System.out.println("Connected to database");
                LoginInfo.setUserName(username);
                LoginInfo.setPassWord(password);
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
