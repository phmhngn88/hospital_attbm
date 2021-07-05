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
public class HOSOBENHNHAN {
    String MAKB,NGAYKB,MATT,MABS,MABN,TINHTRANGBANDAU,KETLUANCUABS;

    public void setMAKB(String MAKB) {
        this.MAKB = MAKB;
    }

    public void setNGAYKB(String NGAYKB) {
        this.NGAYKB = NGAYKB;
    }

    public void setMATT(String MATT) {
        this.MATT = MATT;
    }

    public void setMABS(String MABS) {
        this.MABS = MABS;
    }

    public void setMABN(String MABN) {
        this.MABN = MABN;
    }

    public void setTINHTRANGBANDAU(String TINHTRANGBANDAU) {
        this.TINHTRANGBANDAU = TINHTRANGBANDAU;
    }

    public void setKETLUANCUABS(String KETLUANCUABS) {
        this.KETLUANCUABS = KETLUANCUABS;
    }

    public HOSOBENHNHAN(String MAKB, String NGAYKB, String MATT, String MABS, String MABN, String TINHTRANGBANDAU, String KETLUANCUABS) {
        this.MAKB = MAKB;
        this.NGAYKB = NGAYKB;
        this.MATT = MATT;
        this.MABS = MABS;
        this.MABN = MABN;
        this.TINHTRANGBANDAU = TINHTRANGBANDAU;
        this.KETLUANCUABS = KETLUANCUABS;
    }

    public String getMAKB() {
        return MAKB;
    }

    public String getNGAYKB() {
        return NGAYKB;
    }

    public String getMATT() {
        return MATT;
    }

    public String getMABS() {
        return MABS;
    }

    public String getMABN() {
        return MABN;
    }

    public String getTINHTRANGBANDAU() {
        return TINHTRANGBANDAU;
    }

    public String getKETLUANCUABS() {
        return KETLUANCUABS;
    }
    
}
