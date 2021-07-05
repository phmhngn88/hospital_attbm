
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phmhngn
 */
public class RoleModify {
    public static List<Role> findAll() {
        List<Role> roleList = new ArrayList<>();
        // TODO Auto-generated method stub
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
			CallableStatement stmt = connection.prepareCall("begin show_system_role(?); END;");
                        stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
                        stmt.execute();
                        ResultSet resultSet = ((OracleCallableStatement)stmt).getCursor(1);

			while (resultSet.next()) {
				Role line = new Role(resultSet.getString(1),
                                    resultSet.getString(2),resultSet.getString(3));
				
                                roleList.add(line);
				
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
        return roleList;
        }
    
    public static void createRole(String role, String newPassword, boolean isNotIdentified) {
        // TODO Auto-generated method stub
                Connection connection = null;
                PreparedStatement statement = null;
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
                        
//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
                        
			String sql = new String();
                        if (isNotIdentified)
                            sql = "create role "+ role;
                        else
                            sql = "create role "+ role +" identified by " + newPassword;
			statement = connection.prepareCall(sql);
			
//                        statement.setString(1, userName);
//                        statement.setString(2, newPassword);
			
                        statement.execute();
                        String sql1 = "grant create session to " + role;
                        statement = connection.prepareCall(sql1);
			
//                        statement.setString(1, userName);
//                        statement.setString(2, newPassword);
			
                        statement.execute();                        

			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
    }
    
    public static void deleteRole(String role) {
        // TODO Auto-generated method stub
                Connection connection = null;
                PreparedStatement statement = null;
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
                        
//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
                        
			String sql = "drop role "+ role;
			statement = connection.prepareCall(sql);
			
//                        statement.setString(1, userName);
//                        statement.setString(2, newPassword);
			
                        statement.execute();

			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
    }
    
    public static void editRole(String role, String newPassword, boolean isNotIdentified) {
        // TODO Auto-generated method stub
                Connection connection = null;
                PreparedStatement statement = null;
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
                        
//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
                        
			 
                        String sql = new String();
                        if (isNotIdentified) {
                            sql = "alter role "+ role +" not identified";
                        } else {
                            sql = "alter role "+ role +" identified by " + newPassword;
                        }
                            
			statement = connection.prepareCall(sql);
			
//                        statement.setString(1, userName);
//                        statement.setString(2, newPassword);
			
                        statement.execute();

			
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
    }
}
