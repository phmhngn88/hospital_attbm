/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.*;

/**
 *
 * @author phmhngn
 */
public class TabPrivModify {

    public static List<DbaTabPrivs> findAll() {
        List<DbaTabPrivs> resultTab = new ArrayList<>();
        // TODO Auto-generated method stub
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
            //String sql = "SELECT * FROM USER_TAB_PRIVS";

//			Statement statement = connection.createStatement();
//			
//			ResultSet resultSet = statement.executeQuery(sql);
//			statement.setString(1, "myrole");
//			statement.setString(2, "NV001");
            String sql = "exec view_user_priviledge";
            CallableStatement stmt = connection.prepareCall("begin view_user_priviledge(?); END;");
            stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
            stmt.execute();
            ResultSet resultSet = ((OracleCallableStatement) stmt).getCursor(1);

            while (resultSet.next()) {
                DbaTabPrivs line = new DbaTabPrivs(resultSet.getString(1),
                        resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(10));

                resultTab.add(line);

            }
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
        return resultTab;
    }

    public static boolean isConnected() {
        // TODO Auto-generated method stub
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static List<DbaTabPrivs> find(String objName) {
        List<DbaTabPrivs> resultTab = new ArrayList<>();
        // TODO Auto-generated method stub
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");
            CallableStatement stmt = connection.prepareCall("begin view_user_priviledge(?); END;");
            stmt.registerOutParameter(1, OracleTypes.CURSOR); //REF CURSOR
            stmt.execute();
            ResultSet resultSet = ((OracleCallableStatement) stmt).getCursor(1);
//			statement.setString(1, "myrole");
//			statement.setString(2, "NV001");
            while (resultSet.next()) {
                if (objName.toUpperCase().equals(resultSet.getString(1).toUpperCase())) {
                    DbaTabPrivs line = new DbaTabPrivs(resultSet.getString(1),
                            resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                            resultSet.getString(8), resultSet.getString(9), resultSet.getString(10));

                    resultTab.add(line);
                }

            }
            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
        return resultTab;
    }

    public static void grantSelect(String obj, String table, boolean grantOption) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin grant_priviledge(?, ?, ?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "SELECT");
            stmt.setString(4, grantOption ? "TRUE" : "FALSE");
            stmt.setString(5, "ALL COLUMNS");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void grantDelete(String obj, String table, boolean grantOption) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin grant_priviledge(?, ?, ?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "DELETE");
            stmt.setString(4, grantOption ? "TRUE" : "FALSE");
            stmt.setString(5, "ALL COLUMNS");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void grantInsert(String obj, String table, String column, boolean grantOption) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin grant_priviledge(?, ?, ?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "INSERT");
            stmt.setString(4, grantOption ? "TRUE" : "FALSE");
            stmt.setString(5, column);
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void grantUpdate(String obj, String table, String column, boolean grantOption) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin grant_priviledge(?, ?, ?, ? , ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "UPDATE");
            stmt.setString(4, grantOption ? "TRUE" : "FALSE");
            stmt.setString(5, column);
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void revokeSelect(String obj, String table) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin revoke_priviledge(?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "SELECT");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void revokeDelete(String obj, String table) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin revoke_priviledge(?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "DELETE");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void revokeInsert(String obj, String table) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin revoke_priviledge(?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "INSERT");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void revokeUpdate(String obj, String table) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin revoke_priviledge(?, ?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, table);
            stmt.setString(3, "UPDATE");
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void revokeRole(String obj, String role) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

            Statement setMode = connection.createStatement();
            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            CallableStatement stmt = connection.prepareCall("begin revoke_role(?, ?); END;");
            stmt.setString(1, obj);
            stmt.setString(2, role);
            stmt.execute();

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }

    public static void grantRole(String obj, String role) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        String dbURL = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
        String username = Login.username;
        String password = Login.password;

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected");

//            Statement setMode = connection.createStatement();
//            setMode.executeQuery("alter session set \"_ORACLE_SCRIPT\"=true");

            String sql = "GRANT " + role + " TO " + obj;

            statement = connection.prepareCall(sql);

//                        statement.setString(1, userName);
//                        statement.setString(2, newPassword);
            statement.execute();
            connection = DriverManager.getConnection(dbURL, "LBACSYS", "12345");
            System.out.println("Connected");
            String esql = null;
            CallableStatement stmt;
            switch (role.toLowerCase()) {
         
                case "bacsi":
                    esql = " begin SA_USER_ADMIN.SET_USER_LABELS ("
                            + "policy_name       => 'BV_OLS',"
                            + "user_name         => '" + obj + "',"
                            + "max_read_label    => 'EMP:BS',"
                            + "max_write_label   => 'EMP:BS',"
                            + "def_label         => 'EMP:BS',"
                            + "row_label         => 'EMP:BS'); end;";
                    System.out.println("bacsi");
                    stmt = connection.prepareCall(esql);
                    stmt.execute();
                    break;
                case "nhanvien_ql_tn_ns":
                    esql = " begin SA_USER_ADMIN.SET_USER_LABELS ("
                            + "policy_name       => 'BV_OLS',"
                            + "user_name         => '" + obj + "',"
                            + "max_read_label    => 'MGR:QLNS,BS,TT,TV',"
                            + "max_write_label   => 'MGR:QLNS,BS,TT,TV',"
                            + "def_label         => 'MGR:QLNS,BS,TT,TV',"
                            + "row_label         => 'MGR:QLNS,BS,TT,TV'); end;";
                    System.out.println("nhanvien_ql_tn_ns");
                    stmt = connection.prepareCall(esql);
                    stmt.execute();
                    break;
                case "nv_tieptan_dieuphoi":
                    esql = " begin SA_USER_ADMIN.SET_USER_LABELS ("
                            + "policy_name       => 'BV_OLS',"
                            + "user_name         => '" + obj + "',"
                            + "max_read_label    => 'EMP:TT',"
                            + "max_write_label   => 'EMP:TT',"
                            + "def_label         => 'EMP:TT',"
                            + "row_label         => 'EMP:TT'); end;";
                    System.out.println("nv_tieptan_dieuphoi");
                    stmt = connection.prepareCall(esql);
                    stmt.execute();
                    break;
                case "nhanvien_ql_tv":
                    esql = " begin SA_USER_ADMIN.SET_USER_LABELS ("
                            + "policy_name       => 'BV_OLS',"
                            + "user_name         => '" + obj + "',"
                            + "max_read_label    => 'EMP:TV',"
                            + "max_write_label   => 'EMP:TV',"
                            + "def_label         => 'EMP:TV',"
                            + "row_label         => 'EMP:TV'); end;";
                    System.out.println("nhanvien_ql_tv");
                    stmt = connection.prepareCall(esql);
                    stmt.execute();
                    break;
            }


            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("eo dc Biatch");
            e.printStackTrace();
        }
    }
}
