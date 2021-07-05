
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class UserModify {
    public static List<User> findAll() {
        List<User> userList = new ArrayList<>();
        // TODO Auto-generated method stub
		String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
		String username = Login.username;
		String password = Login.password;
		
		try {
			Connection connection = DriverManager.getConnection(dbURL, username, password);
			System.out.println("Connected");
//			String sql = "SELECT USERNAME, USER_ID, CREATED FROM all_users ORDER BY created DESC";
//			Statement statement = connection.createStatement();
//			
//			ResultSet resultSet = statement.executeQuery(sql);
                        
                        CallableStatement stmt = connection.prepareCall("begin show_system_user(?); END;");
                        stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
                        stmt.execute();
                        ResultSet resultSet = ((OracleCallableStatement)stmt).getCursor(1);

			while (resultSet.next()) {
				User line = new User(resultSet.getString(1),
                                    resultSet.getString(2),resultSet.getString(3));
				
                                userList.add(line);
				
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("eo dc Biatch");
			e.printStackTrace();
		}
        return userList;
    }
    
    public static void createNewUser(String userName, String newPassword) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
            String sql = "create user " + userName + " identified by " + newPassword;
            statement = connection.prepareCall(sql);
            statement.execute();
            connection.close();
            

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void deleteUser(String userName) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
            String sql = "drop user " + userName;
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

    public static void editUser(String userName, String newPassword) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//                        Statement setMode = connection.createStatement();
//                        setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");
            String sql = "alter user " + userName + " identified by " + newPassword;
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
