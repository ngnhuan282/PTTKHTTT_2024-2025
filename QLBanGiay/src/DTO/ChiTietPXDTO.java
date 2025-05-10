package DTO;

public class ChiTietPXDTO {
    private String maPX;
    private String maSP;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietPXDTO() {
    }

    public ChiTietPXDTO(String maPX, String maSP, int soLuong, double donGia, double thanhTien) {
        this.maPX = maPX;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getMaPX() {
        return maPX;
    }

    public void setMaPX(String maPX) {
        this.maPX = maPX;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public String toString() {
        return "ChiTietPXDTO [maPX=" + maPX + ", maSP=" + maSP + ", soLuong=" + soLuong + ", donGia=" + donGia
                + ", thanhTien=" + thanhTien + "]";
    }
}