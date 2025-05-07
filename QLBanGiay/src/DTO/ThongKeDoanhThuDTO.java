package DTO;

import java.sql.Date;

public class ThongKeDoanhThuDTO {
	private Date ngay;
	private int thang, nam;
	private double chiPhi, doanhThu, loiNhuan;
	
	public ThongKeDoanhThuDTO(Date ngay, double chiPhi, double doanhThu, double loiNhuan) {
		super();
		this.ngay = ngay;
		this.chiPhi = chiPhi;
		this.doanhThu = doanhThu;
		this.loiNhuan = loiNhuan;
	}
	
	
	public ThongKeDoanhThuDTO(int thang, int nam, double chiPhi, double doanhThu, double loiNhuan) {
		super();
		this.thang = thang;
		this.nam = nam;
		this.chiPhi = chiPhi;
		this.doanhThu = doanhThu;
		this.loiNhuan = loiNhuan;
	}
	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public int getThang() {
		return thang;
	}

	public void setThang(int thang) {
		this.thang = thang;
	}

	public int getNam() {
		return nam;
	}

	public void setNam(int nam) {
		this.nam = nam;
	}

	public double getChiPhi() {
		return chiPhi;
	}

	public void setChiPhi(double chiPhi) {
		this.chiPhi = chiPhi;
	}

	public double getDoanhThu() {
		return doanhThu;
	}

	public void setDoanhThu(double doanhThu) {
		this.doanhThu = doanhThu;
	}

	public double getLoiNhuan() {
		return loiNhuan;
	}

	public void setLoiNhuan(double loiNhuan) {
		this.loiNhuan = loiNhuan;
	}
	
	
}
