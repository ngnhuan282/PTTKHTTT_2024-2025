package DTO;

public class NhanVienDTO {
	private String MaNV,ho,ten,sdt;
	private double luong;
	
	public NhanVienDTO() {
		
	}

	public NhanVienDTO(String maNV, String ho, String ten, String sdt, double luong) {
		super();
		MaNV = maNV;
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.luong = luong;
	}

	public String getMaNV() {
		return MaNV;
	}

	public void setMaNV(String maNV) {
		MaNV = maNV;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public double getLuong() {
		return luong;
	}

	public void setLuong(double luong) {
		this.luong = luong;
	}
	
	
}

