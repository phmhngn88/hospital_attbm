/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.AuditPolicy;
import Model.AuditRecord;
import Model.LoginInfo;
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
public class AuditRecordDAO {
    public static List<AuditRecord> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<AuditRecord> listOA = new ArrayList<>();
        try {
            String dbURL = LoginInfo.getConnectionString();
            conn = DriverManager.getConnection(dbURL, LoginInfo.getUserName(), LoginInfo.getPassWord());

            String sql = "select os_username, username, owner, timestamp, action_name, sql_text"
                    + " from dba_audit_trail"
                    + " order by  TIMESTAMP desc";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                AuditRecord line = new AuditRecord(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6));
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
