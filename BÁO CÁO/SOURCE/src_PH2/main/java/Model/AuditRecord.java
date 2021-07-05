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
public class AuditRecord {
    String OS_USERNAME,USERNAME,OWNER,TIMESTAMP,ACTION_NAME,SQL_TEXT;

    public AuditRecord(String OS_USERNAME, String USERNAME, String OWNER, String TIMESTAMP, String ACTION_NAME, String SQL_TEXT) {
        this.OS_USERNAME = OS_USERNAME;
        this.USERNAME = USERNAME;
        this.OWNER = OWNER;
        this.TIMESTAMP = TIMESTAMP;
        this.ACTION_NAME = ACTION_NAME;
        this.SQL_TEXT = SQL_TEXT;
    }

    public String getOS_USERNAME() {
        return OS_USERNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getOWNER() {
        return OWNER;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public String getACTION_NAME() {
        return ACTION_NAME;
    }

    public String getSQL_TEXT() {
        return SQL_TEXT;
    }

    public void setOS_USERNAME(String OS_USERNAME) {
        this.OS_USERNAME = OS_USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public void setACTION_NAME(String ACTION_NAME) {
        this.ACTION_NAME = ACTION_NAME;
    }

    public void setSQL_TEXT(String SQL_TEXT) {
        this.SQL_TEXT = SQL_TEXT;
    }
    
}
