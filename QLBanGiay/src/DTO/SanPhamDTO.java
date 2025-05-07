package DTO;

public class SanPhamDTO {
	private String maSP, tenSP;
	private int maLoaiSP;
	private int soLuong;
	private double donGia;
	private String donViTinh;
	private String chatLieu, mauSac, kieuDang;
	private int kichThuoc;

	
	public SanPhamDTO()
	{
		
	}
	
	

	public SanPhamDTO(String maSP, String tenSP, int maLoaiSP, int soLuong, double donGia, String donViTinh,
			String mauSac, int kichThuoc,  String chatLieu, String kieuDang) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.maLoaiSP = maLoaiSP;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.donViTinh = donViTinh;
		this.chatLieu = chatLieu;
		this.mauSac = mauSac;
		this.kieuDang = kieuDang;
		this.kichThuoc = kichThuoc;
	}



	public String getMaSP() {
		return maSP;
	}


	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}


	public String getTenSP() {
		return tenSP;
	}


	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}


	public int getMaLoaiSP() {
		return maLoaiSP;
	}


	public void setMaLoaiSP(int maLoaiSP) {
		this.maLoaiSP = maLoaiSP;
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


	public String getDonViTinh() {
		return donViTinh;
	}


	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}


	public String getChatLieu() {
		return chatLieu;
	}


	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}


	public String getMauSac() {
		return mauSac;
	}


	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}


	public String getKieuDang() {
		return kieuDang;
	}


	public void setKieuDang(String kieuDang) {
		this.kieuDang = kieuDang;
	}


	public int getKichThuoc() {
		return kichThuoc;
	}


	public void setKichThuoc(int kichThuoc) {
		this.kichThuoc = kichThuoc;
	}
	
	
	
}
	
	
