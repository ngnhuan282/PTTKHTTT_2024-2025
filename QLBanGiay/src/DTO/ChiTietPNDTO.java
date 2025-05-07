package DTO;

public class ChiTietPNDTO {
	private String maPhieuNH;
	private String maSP;
	private int soLuong;
	private double donGia;
	private double thanhTien;
	
	
	public ChiTietPNDTO() {
		
	}


	public ChiTietPNDTO(String maPhieuNH, String maSP, int soLuong, double donGia, double thanhTien) {
		this.maPhieuNH = maPhieuNH;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
	}


	public String getMaPhieuNH() {
		return maPhieuNH;
	}


	public void setMaPhieuNH(String maPhieuNH) {
		this.maPhieuNH = maPhieuNH;
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
		return "ChiTietPNDTO [maPhieuNH=" + maPhieuNH + ", maSP=" + maSP + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ ", thanhTien=" + thanhTien + "]";
	}
	
	
	
	

	
	

}