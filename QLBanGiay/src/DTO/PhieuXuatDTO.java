package DTO;

public class PhieuXuatDTO {
    public String maPX;
    public String ngayXuat;
    public String ghiChu;

    public PhieuXuatDTO() {
    }

    public PhieuXuatDTO(String maPX, String ngayXuat, String ghiChu) {
        this.maPX = maPX;
        this.ngayXuat = ngayXuat;
        this.ghiChu = ghiChu;
    }

    public String getMaPX() {
        return this.maPX;
    }

    public String getNgayXuat() {
        return this.ngayXuat;
    }

    public String getGhiChu() {
        return this.ghiChu;
    }

    public void setMaPX(String maPX) {
        this.maPX = maPX;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "PhieuXuatDTO [maPX=" + maPX + ", ngayXuat=" + ngayXuat + ", ghiChu=" + ghiChu + "]";
    }
}