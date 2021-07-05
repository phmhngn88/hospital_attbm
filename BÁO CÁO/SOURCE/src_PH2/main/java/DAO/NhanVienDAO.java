/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.LoginInfo;
import Model.NhanVien;
import Model.ObjectAudit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anpham
 */
public class NhanVienDAO {
    public static List<NhanVien> getAll () {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<NhanVien> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "SELECT * FROM HOSPITAL_DBA.NHANVIEN";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                NhanVien line = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                listOA.add(line);
                System.out.println(line.toString());
            }
            
            return listOA;

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
    
    public static boolean isBacSi (String maNV) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<NhanVien> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "SELECT * FROM HOSPITAL_DBA.NHANVIEN WHERE MANV= '"+maNV + "' AND VAITRO='BacSi'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                NhanVien line = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
                listOA.add(line);
                System.out.println(line.toString());
            }
            
            if (listOA.size() == 0) {
                return false;
            } else {
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
    }
}
