/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phmhngn
 */
public class Role {
    String ROLE, ROLE_ID, PASSWORD_REQUIRED;

    public Role() {
    }

    public Role(String ROLE, String ROLE_ID, String PASSWORD_REQUIRED) {
        this.ROLE = ROLE;
        this.ROLE_ID = ROLE_ID;
        this.PASSWORD_REQUIRED = PASSWORD_REQUIRED;
    }

    public String getROLE() {
        return ROLE;
    }

    public void setROLE(String ROLE) {
        this.ROLE = ROLE;
    }

    public String getROLE_ID() {
        return ROLE_ID;
    }

    public void setROLE_ID(String ROLE_ID) {
        this.ROLE_ID = ROLE_ID;
    }

    public String getPASSWORD_REQUIRED() {
        return PASSWORD_REQUIRED;
    }

    public void setPASSWORD_REQUIRED(String PASSWORD_REQUIRED) {
        this.PASSWORD_REQUIRED = PASSWORD_REQUIRED;
    }
    
}
