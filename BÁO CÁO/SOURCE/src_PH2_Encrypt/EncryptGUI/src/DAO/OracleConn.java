package DAO;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

public class OracleConn {
    private static Connection conn;
    public static boolean connect(String username, String password) {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",username, password);
            System.out.println(conn);
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public static void close() throws SQLException {
        conn.close();
    }

    public static String[] getPatient(int docID) {
        try {

            String command = "{call HOSPITAL.PROCEDURE_45('NO PARAM', " + docID + ", 'NO PARAM', ?, ?, ?, ?, ?)}";
            CallableStatement cstmt = conn.prepareCall(command);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.NVARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.execute();

            byte[] t = cstmt.getBytes(2);
            byte[] realChars = new byte[t.length - 1];
            int k = 0;
            for (byte i : t) {
                if (i != 0) {
                    realChars[k] = i;
                    k++;
                }
            }
            byte[] nameChars = new byte[k];
            for (int i = 0; i < k;i++) {
                nameChars[i] = realChars[i];
            }
            String[] result = {String.valueOf(cstmt.getInt(1)), new String(nameChars, StandardCharsets.UTF_8),
                    cstmt.getString(3), cstmt.getString(4), cstmt.getString(5)};
            return result;
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
            String[] empty = {};
            return empty;
        }
    }

    public static ArrayList<Object[]> getPatientRecords(int patientID) {
        ArrayList<Object[]> result = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String command ="SELECT * FROM HOSPITAL.HOSOBENHNHAN" +
                    "   WHERE MABN = " + patientID;
            ResultSet rows = stmt.executeQuery(command);
            while (rows.next()) {
                result.add(new Object[]{rows.getString(1), rows.getString(2),
                        rows.getString(4), rows.getString(5), rows.getString(6),
                        rows.getString(7)});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static boolean deletePatient(int maBN) throws SQLException {
        String command ="DELETE FROM HOSPITAL.BENHNHAN" +
                "   WHERE MABN = " + maBN;
        PreparedStatement st = conn.prepareStatement(command);
        int result = st.executeUpdate();
        if(result == 1)
            return true;
            else
                return false;
    }

    public static boolean addPatient(int maBN, String name, String dateOfBirth, String address, String phone) throws SQLException {
        String command = "{call HOSPITAL.INSERT_BENHNHAN(?, ?, ?, ?, ?)}";
        CallableStatement cstmt = conn.prepareCall(command);
        cstmt.setInt(1, maBN);
        cstmt.setNString(2, name);
        cstmt.setString(3, dateOfBirth);
        cstmt.setString(4, address);
        cstmt.setString(5, phone);
        return cstmt.execute();
    }

    public static boolean updatePatient(int maBN, String name, String dateOfBirth, String address, String phone) throws SQLException {
        String command = "{call HOSPITAL.UPDATE_BENHNHAN(?, ?, ?, ?, ?)}";
        CallableStatement cstmt = conn.prepareCall(command);
        cstmt.setInt(1, maBN);
        cstmt.setNString(2, name);
        cstmt.setString(3, dateOfBirth);
        cstmt.setString(4, address);
        cstmt.setString(5, phone);
        return cstmt.execute();
    }

    public static String[] getPatientByID(int patientID) {
        try {
            String command = "{call HOSPITAL.PROCEDURE_46('NO PARAM', " + patientID + ", 'NO PARAM', ?, ?, ?, ?, ?)}";
            CallableStatement cstmt = conn.prepareCall(command);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.registerOutParameter(2, Types.NVARCHAR);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.execute();

            byte[] t = cstmt.getBytes(2);
            byte[] realChars = new byte[t.length - 1];
            int k = 0;
            for (byte i : t) {
                if (i != 0) {
                    realChars[k] = i;
                    k++;
                }
            }
            byte[] nameChars = new byte[k];
            for (int i = 0; i < k;i++) {
                nameChars[i] = realChars[i];
            }
            String[] result = {String.valueOf(cstmt.getInt(1)), new String(nameChars, StandardCharsets.UTF_8),
                    cstmt.getString(3), cstmt.getString(4), cstmt.getString(5)};
            return result;
        } catch (SQLException  throwables) {
            throwables.printStackTrace();
            String[] empty = {};
            return empty;
        }
    }
}
