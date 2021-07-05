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
public class AuditPolicy {
    String objectSchema;
    String objectName;
    String policyOwner;
    String policyName;
    String policyColumn;
    String sel;
    String ins;
    String upd;
    String del;

    public AuditPolicy(String objectSchema, String objectName, String policyOwner, String policyName, String policyColumn, String sel, String ins, String upd, String del) {
        this.objectSchema = objectSchema;
        this.objectName = objectName;
        this.policyOwner = policyOwner;
        this.policyName = policyName;
        this.policyColumn = policyColumn;
        this.sel = sel;
        this.ins = ins;
        this.upd = upd;
        this.del = del;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public void setPolicyColumn(String policyColumn) {
        this.policyColumn = policyColumn;
    }

    public void setSel(String sel) {
        this.sel = sel;
    }

    @Override
    public String toString() {
        return "AuditPolicy{" + "objectSchema=" + objectSchema + ", objectName=" + objectName + ", policyOwner=" + policyOwner + ", policyName=" + policyName + ", policyColumn=" + policyColumn + ", sel=" + sel + ", ins=" + ins + ", upd=" + upd + ", del=" + del + '}';
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getObjectSchema() {
        return objectSchema;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getPolicyColumn() {
        return policyColumn;
    }

    public String getSel() {
        return sel;
    }

    public String getIns() {
        return ins;
    }

    public String getUpd() {
        return upd;
    }

    public String getDel() {
        return del;
    }
    
    
}
