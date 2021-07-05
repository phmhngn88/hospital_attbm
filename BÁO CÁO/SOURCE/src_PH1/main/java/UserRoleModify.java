
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phmhngn
 */
public class UserRoleModify {
    public static List<UserRole> findAll() {
        List<UserRole> roleList = new ArrayList<>();
        // TODO Auto-generated method stub
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
			CallableStatement stmt = connection.prepareCall("begin view_user_roles(?); END;");
                        stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
                        stmt.execute();
                        ResultSet resultSet = ((OracleCallableStatement)stmt).getCursor(1);

			while (resultSet.next()) {
				UserRole line = new UserRole(resultSet.getString(1),
                                    resultSet.getString(2));
				
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
    
    public static List<UserRole> find(String obj) {
        List<UserRole> roleList = new ArrayList<>();
        // TODO Auto-generated method stub
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username =Login.username;
		String password = Login.password;
		
		try {
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
			CallableStatement stmt = connection.prepareCall("begin view_user_roles(?); END;");
                        stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
                        stmt.execute();
                        ResultSet resultSet = ((OracleCallableStatement)stmt).getCursor(1);

			while (resultSet.next()) {
                            if (obj.toUpperCase().equals(resultSet.getString(1).toUpperCase())) {
				UserRole line = new UserRole(resultSet.getString(1),
                                    resultSet.getString(2));
				
                                roleList.add(line);
                            }
				
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
        return roleList;
        }
}
