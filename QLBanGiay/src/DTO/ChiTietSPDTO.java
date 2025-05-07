package DTO;

public class ChiTietSPDTO {
	private String maSP;
	private String mauSac, chatLieu, kieuDang;
	private int kichThuoc;
	
	public ChiTietSPDTO()
	{
		
	}
	
	public ChiTietSPDTO(String maSP, String mauSac,  int kichThuoc, String chatLieu, String kieuDang) {
		super();
		this.maSP = maSP;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
		this.kieuDang = kieuDang;
		this.kichThuoc = kichThuoc;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getMauSac() {
		return mauSac;
	}

	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
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
