package DTO;

public class ChiTietHDDTO {
	private String maHD;
	private String maSP;
	private int soLuong;
	private double donGia;
	private double thanhTien;
	
	public ChiTietHDDTO() {
	}

	public ChiTietHDDTO(String maHD, String maSP, int soLuong, double donGia, double thanhTien) {
		this.maHD = maHD;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
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
	public boolean equals(Object obj) {
	    if (this == obj) return true; 
	    if (obj == null || getClass() != obj.getClass()) return false; 
	    ChiTietHDDTO other = (ChiTietHDDTO) obj; 

	    return this.maHD.equals(other.maHD) && this.maSP.equals(other.maSP) && this.soLuong == other.soLuong && this.donGia == other.donGia && this.thanhTien == other.thanhTien;
	}

	@Override
	public String toString() {
		return "ChiTietHDDTO [maHD=" + maHD + ", maSP=" + maSP + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ ", thanhTien=" + thanhTien + "]";
	}
	
	

	
	
	
	
}
