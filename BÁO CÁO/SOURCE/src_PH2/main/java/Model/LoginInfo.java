/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author anpham
 */
public class LoginInfo {
    static String userName;
    static String passWord;
    static String ConnectionString = "jdbc:oracle:thin:@localhost:1521/HOSPITAL";
    
    static String role;

    public static void setRole(String role) {
        LoginInfo.role = role;
    }

    public static String getRole() {
        return role;
    }

    public LoginInfo() {
    }

    public static void setUserName(String userName) {
        LoginInfo.userName = userName;
    }

    public static void setPassWord(String passWord) {
        LoginInfo.passWord = passWord;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static String getConnectionString() {
        return ConnectionString;
    }
    
}
