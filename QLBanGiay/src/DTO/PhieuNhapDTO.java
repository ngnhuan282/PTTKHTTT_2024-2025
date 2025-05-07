package DTO;

import java.sql.Date;

public class PhieuNhapDTO {
	private String maPhieuNH;
	private String maNV;
	private String maNCC;
	private double tongTien;
	private Date ngayNhap;
	
	
	public PhieuNhapDTO() {
		
	}


	public PhieuNhapDTO(String maPhieuNH, String maNV, String maNCC, double tongTien, Date ngayNhap) {
		this.maPhieuNH = maPhieuNH;
		this.maNV = maNV;
		this.maNCC = maNCC;
		this.tongTien = tongTien;
		this.ngayNhap = ngayNhap;
	}


	public String getMaPhieuNH() {
		return maPhieuNH;
	}


	public void setMaPhieuNH(String maPhieuNH) {
		this.maPhieuNH = maPhieuNH;
	}


	public String getMaNV() {
		return maNV;
	}


	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}


	public String getMaNCC() {
		return maNCC;
	}


	public void setMaNCC(String maNCC) {
		this.maNCC = maNCC;
	}


	public double getTongTien() {
		return tongTien;
	}


	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}


	public Date getNgayNhap() {
		return ngayNhap;
	}


	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}
	
	
	
	
	
}
