package DTO;

import java.sql.Date;

public class PhieuXuatDTO {
    private String maPX;
    private String maNV;
    private double tongTien;
    private Date ngayXuat;
    private String ghiChu;

    public PhieuXuatDTO() {
    }

    public PhieuXuatDTO(String maPX, String maNV, double tongTien, Date ngayXuat, String ghiChu) {
        this.maPX = maPX;
        this.maNV = maNV;
        this.tongTien = tongTien;
        this.ngayXuat = ngayXuat;
        this.ghiChu = ghiChu;
    }

    public String getMaPX() {
        return maPX;
    }

    public void setMaPX(String maPX) {
        this.maPX = maPX;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}