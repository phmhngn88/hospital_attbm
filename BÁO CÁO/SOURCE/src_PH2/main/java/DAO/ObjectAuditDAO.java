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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author anpham
 */
public class ObjectAuditDAO {
    public static List<ObjectAudit> getAllAudits() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<ObjectAudit> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "SELECT OWNER, OBJECT_NAME, OBJECT_TYPE FROM DBA_OBJ_AUDIT_OPTS WHERE OWNER != 'LBACSYS' AND OWNER != 'DVSYS' AND OWNER != 'DVF'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                ObjectAudit line = new ObjectAudit(rs.getString(1), rs.getString(2), rs.getString(3));
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
    public static boolean audit(String tableName) {
        Connection conn = null;
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "audit all on HOSPITAL_DBA."+tableName+" by access";
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.execute();

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
    
    public static boolean noAudit(String tableName) {
        Connection conn = null;
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "noaudit all on HOSPITAL_DBA."+tableName;
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.execute();

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
