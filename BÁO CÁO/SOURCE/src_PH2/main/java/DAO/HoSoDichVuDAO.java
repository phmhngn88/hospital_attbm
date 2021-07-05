/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.HOSOBENHNHAN;
import Model.HOSODICHVU;
import Model.LoginInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anpham
 */
public class HoSoDichVuDAO {

    public static List<HOSODICHVU> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<HOSODICHVU> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());

            try {
                String sql = "SELECT * FROM HOSPITAL_DBA.HOSODICHVU";
                st = conn.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    HOSODICHVU line = new HOSODICHVU(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6));
                    listOA.add(line);
                }

                return listOA;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateKetLuan(String maKB,String maDV, String ketLuan) {
        String jdbcUrl = LoginInfo.getConnectionString();
        String username = LoginInfo.getUserName();
        String password = LoginInfo.getPassWord();
        String sql = "update hospital_dba.hosodichvu set ketluan=? where makb=? and madv=?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, ketLuan);
            stmt.setString(2, maKB);
            stmt.setString(3, maDV);
            stmt.executeUpdate();

            System.out.println("Database updated successfully ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
