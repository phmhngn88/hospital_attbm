/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AuditPolicy;
import Model.LoginInfo;
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
public class AuditPolicyDAO {
    public static List<AuditPolicy> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<AuditPolicy> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());
        
            String sql = "SELECT OBJECT_SCHEMA,OBJECT_NAME,POLICY_OWNER,POLICY_NAME,POLICY_COLUMN,SEL,INS,UPD,DEL "
                    + "from dba_audit_policies";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()) {
                AuditPolicy line = new AuditPolicy(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9));
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
}
