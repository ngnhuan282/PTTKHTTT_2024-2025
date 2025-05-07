package DTO;

import java.sql.Date;

public class HoaDonDTO {
	private String maHD;
	private String maKH;
	private String maNV;
	private Date ngayLap;
	private double tongTien;
	
	public HoaDonDTO() {
		
	}

	public HoaDonDTO(String maHD, String maKH, String maNV, Date ngayLap, Double tongTien) {
		this.maHD = maHD;
		this.maKH = maKH;
		this.maNV = maNV;
		this.ngayLap = ngayLap;
		this.tongTien = tongTien;
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public Date getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(Date ngayLap) {
		this.ngayLap = ngayLap;
	}

	public Double getTongTien() {
		return tongTien;
	}

	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}
	
	
	
	
}
