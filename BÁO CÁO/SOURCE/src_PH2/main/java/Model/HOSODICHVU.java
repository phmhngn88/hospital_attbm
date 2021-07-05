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
public class HOSODICHVU {
    String MAKB,MADV,NGAYGIO,NGUOITHUCHIEN,KETLUAN,GIATIEN;

    public HOSODICHVU(String MAKB, String MADV, String NGAYGIO, String NGUOITHUCHIEN, String KETLUAN, String GIATIEN) {
        this.MAKB = MAKB;
        this.MADV = MADV;
        this.NGAYGIO = NGAYGIO;
        this.NGUOITHUCHIEN = NGUOITHUCHIEN;
        this.KETLUAN = KETLUAN;
        this.GIATIEN = GIATIEN;
    }

    public String getMAKB() {
        return MAKB;
    }

    public String getMADV() {
        return MADV;
    }

    public String getNGAYGIO() {
        return NGAYGIO;
    }

    public String getNGUOITHUCHIEN() {
        return NGUOITHUCHIEN;
    }

    public String getKETLUAN() {
        return KETLUAN;
    }

    public String getGIATIEN() {
        return GIATIEN;
    }

    public void setMAKB(String MAKB) {
        this.MAKB = MAKB;
    }

    public void setMADV(String MADV) {
        this.MADV = MADV;
    }

    public void setNGAYGIO(String NGAYGIO) {
        this.NGAYGIO = NGAYGIO;
    }

    public void setNGUOITHUCHIEN(String NGUOITHUCHIEN) {
        this.NGUOITHUCHIEN = NGUOITHUCHIEN;
    }

    public void setKETLUAN(String KETLUAN) {
        this.KETLUAN = KETLUAN;
    }

    public void setGIATIEN(String GIATIEN) {
        this.GIATIEN = GIATIEN;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HOSODICHVU{MAKB=").append(MAKB);
        sb.append(", MADV=").append(MADV);
        sb.append(", NGAYGIO=").append(NGAYGIO);
        sb.append(", NGUOITHUCHIEN=").append(NGUOITHUCHIEN);
        sb.append(", KETLUAN=").append(KETLUAN);
        sb.append(", GIATIEN=").append(GIATIEN);
        sb.append('}');
        return sb.toString();
    }
    
    
}
