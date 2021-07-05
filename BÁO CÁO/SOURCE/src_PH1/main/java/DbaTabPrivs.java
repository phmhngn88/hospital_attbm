/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phmhngn
 */
public class DbaTabPrivs {
    String GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEDGE, GRANTABLE;
    String HIERACHY, COMMON, TYPE, INHERITED;

    public DbaTabPrivs() {
    }

    public DbaTabPrivs(String GRANTEE, String OWNER, String TABLE_NAME, String GRANTOR, String PRIVILEDGE, String GRANTABLE, String HIERACHY, String COMMON, String TYPE, String INHERITED) {
        this.GRANTEE = GRANTEE;
        this.OWNER = OWNER;
        this.TABLE_NAME = TABLE_NAME;
        this.GRANTOR = GRANTOR;
        this.PRIVILEDGE = PRIVILEDGE;
        this.GRANTABLE = GRANTABLE;
        this.HIERACHY = HIERACHY;
        this.COMMON = COMMON;
        this.TYPE = TYPE;
        this.INHERITED = INHERITED;
    }

    public String getGRANTEE() {
        return GRANTEE;
    }

    public void setGRANTEE(String GRANTEE) {
        this.GRANTEE = GRANTEE;
    }

    public String getOWNER() {
        return OWNER;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getGRANTOR() {
        return GRANTOR;
    }

    public void setGRANTOR(String GRANTOR) {
        this.GRANTOR = GRANTOR;
    }

    public String getPRIVILEDGE() {
        return PRIVILEDGE;
    }

    public void setPRIVILEDGE(String PRIVILEDGE) {
        this.PRIVILEDGE = PRIVILEDGE;
    }

    public String getGRANTABLE() {
        return GRANTABLE;
    }

    public void setGRANTABLE(String GRANTABLE) {
        this.GRANTABLE = GRANTABLE;
    }

    public String getHIERACHY() {
        return HIERACHY;
    }

    public void setHIERACHY(String HIERACHY) {
        this.HIERACHY = HIERACHY;
    }

    public String getCOMMON() {
        return COMMON;
    }

    public void setCOMMON(String COMMON) {
        this.COMMON = COMMON;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getINHERITED() {
        return INHERITED;
    }

    public void setINHERITED(String INHERITED) {
        this.INHERITED = INHERITED;
    }
}
