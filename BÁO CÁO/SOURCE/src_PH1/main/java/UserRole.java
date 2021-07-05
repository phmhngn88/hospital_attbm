/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phmhngn
 */
public class UserRole {
    String GRANTEE, GRANTED_ROLE;

    public UserRole() {
    }

    public UserRole(String GRANTEE, String GRANTED_ROLE) {
        this.GRANTEE = GRANTEE;
        this.GRANTED_ROLE = GRANTED_ROLE;
    }

    public String getGRANTEE() {
        return GRANTEE;
    }

    public void setGRANTEE(String GRANTEE) {
        this.GRANTEE = GRANTEE;
    }

    public String getGRANTED_ROLE() {
        return GRANTED_ROLE;
    }

    public void setGRANTED_ROLE(String GRANTED_ROLE) {
        this.GRANTED_ROLE = GRANTED_ROLE;
    }
    
    
}
