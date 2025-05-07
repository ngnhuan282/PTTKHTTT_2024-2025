package DTO;

public class KhachHangDTO {
	private String maKH, ho, ten, sdt, diaChi;
	
	public KhachHangDTO() {
		
	}
	
	
	
	public KhachHangDTO(String maKH, String ho, String ten, String sdt, String diaChi) {
		this.maKH = maKH;
		this.ho = ho;
		this.ten = ten;
		this.sdt = sdt;
		this.diaChi = diaChi;
	}



	public String getMaKH() {
		return maKH;
	}
	
	public void setMaKH(String maKH) {
		this.maKH = maKH;
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
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	
	
}
