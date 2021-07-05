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
public class ObjectAudit {
    private String owner;
    private String objectName;
    private String objectType;

    public ObjectAudit(String owner, String objectName, String objectType) {
        this.owner = owner;
        this.objectName = objectName;
        this.objectType = objectType;
    }

    public String getOwner() {
        return owner;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "ObjectAudit{" + "owner=" + owner + ", objectName=" + objectName + ", objectType=" + objectType + '}';
    }
    
}
