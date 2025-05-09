package DTO;

public class ThongKeNhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private int tongSoLuong;
    private double tongTien;

    public ThongKeNhaCungCapDTO() {}

    public ThongKeNhaCungCapDTO(String maNCC, String tenNCC, int tongSoLuong, double tongTien) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
    }

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }

    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}