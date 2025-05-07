package DTO;

import java.sql.Date;

public class KMSPDTO {
	private String maCTKM;
	private Date ngayBD;
	private Date ngayKT;
	private String tenCTKM;
	private String maSP;
	private float phanTramGiamGia;
	
	public KMSPDTO(String maCTKM, Date ngayBD, Date ngayKT, String tenCTKM, String maSP, float phanTramGiamGia) {
		this.maCTKM = maCTKM;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.tenCTKM = tenCTKM;
		this.maSP = maSP;
		this.phanTramGiamGia = phanTramGiamGia;
	}

	public String getMaCTKM() {
		return maCTKM;
	}

	public void setMaCTKM(String maCTKM) {
		this.maCTKM = maCTKM;
	}

	public Date getNgayBD() {
		return ngayBD;
	}

	public void setNgayBD(Date ngayBD) {
		this.ngayBD = ngayBD;
	}

	public Date getNgayKT() {
		return ngayKT;
	}

	public void setNgayKT(Date ngayKT) {
		this.ngayKT = ngayKT;
	}

	public String getTenCTKM() {
		return tenCTKM;
	}

	public void setTenCTKM(String tenCTKM) {
		this.tenCTKM = tenCTKM;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public float getPhanTramGiamGia() {
		return phanTramGiamGia;
	}

	public void setPhanTramGiamGia(float phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
	}

	@Override
	public String toString() {
		return "KMSPDTO [maCTKM=" + maCTKM + ", ngayBD=" + ngayBD + ", ngayKT=" + ngayKT + ", tenCTKM=" + tenCTKM
				+ ", maSP=" + maSP + ", phanTramGiamGia=" + phanTramGiamGia + "]";
	}
	
	
	
	
	
	
}
