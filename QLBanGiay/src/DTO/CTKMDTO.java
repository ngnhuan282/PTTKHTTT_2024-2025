package DTO;

import java.sql.Date;

public class CTKMDTO {
	private String maCTKM;
	private Date ngayBD;
	private Date ngayKT;
	private String tenCTKM;
	private float phanTramGiamGia;
	public CTKMDTO() {
		
	}
	
	
	public CTKMDTO(String maCTKM, Date ngayBD, Date ngayKT, String tenCTKM) {
		this.maCTKM = maCTKM;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.tenCTKM = tenCTKM;
	}


	public CTKMDTO(String maCTKM, Date ngayBD, Date ngayKT, String tenCTKM, float phanTramGiamGia) {
		this.maCTKM = maCTKM;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.tenCTKM = tenCTKM;
		this.phanTramGiamGia = phanTramGiamGia;
	}





	public float getPhanTramGiamGia() {
		return phanTramGiamGia;
	}





	public void setPhanTramGiamGia(float phanTramGiamGia) {
		this.phanTramGiamGia = phanTramGiamGia;
	}





	public String getTenCTKM() {
		return tenCTKM;
	}


	public void setTenCTKM(String tenCTKM) {
		this.tenCTKM = tenCTKM;
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





	@Override
	public String toString() {
		return "CTKMDTO [maCTKM=" + maCTKM + ", ngayBD=" + ngayBD + ", ngayKT=" + ngayKT + ", tenCTKM=" + tenCTKM
				+ ", phanTramGiamGia=" + phanTramGiamGia + "]";
	}







	
}




















