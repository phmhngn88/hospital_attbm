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
public class NhanVien {
    String MANV,HOTEN,GIOITINH,NGAYSINH,DIACHI,LUONG,VAITRO,DONVI;

    public String getMANV() {
        return MANV;
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public String getLUONG() {
        return LUONG;
    }

    public String getVAITRO() {
        return VAITRO;
    }

    public String getDONVI() {
        return DONVI;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public void setLUONG(String LUONG) {
        this.LUONG = LUONG;
    }

    public void setVAITRO(String VAITRO) {
        this.VAITRO = VAITRO;
    }

    public void setDONVI(String DONVI) {
        this.DONVI = DONVI;
    }

    public NhanVien(String MANV, String HOTEN, String GIOITINH, String NGAYSINH, String DIACHI, String LUONG, String VAITRO, String DONVI) {
        this.MANV = MANV;
        this.HOTEN = HOTEN;
        this.GIOITINH = GIOITINH;
        this.NGAYSINH = NGAYSINH;
        this.DIACHI = DIACHI;
        this.LUONG = LUONG;
        this.VAITRO = VAITRO;
        this.DONVI = DONVI;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "MANV=" + MANV + ", HOTEN=" + HOTEN + ", GIOITINH=" + GIOITINH + ", NGAYSINH=" + NGAYSINH + ", DIACHI=" + DIACHI + ", LUONG=" + LUONG + ", VAITRO=" + VAITRO + ", DONVI=" + DONVI + '}';
    }
    

}
