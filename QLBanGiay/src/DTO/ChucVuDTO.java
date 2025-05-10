package DTO;

public class ChucVuDTO {
	private int MaChucVu;
	private String tenChucVu;
	
	public ChucVuDTO() {
		
	}

	public ChucVuDTO(int maChucVu, String tenChucVu) {
		super();
		MaChucVu = maChucVu;
		this.tenChucVu = tenChucVu;
	}

	public int getMaChucVu() {
		return MaChucVu;
	}

	public void setMaChucVu(int maChucVu) {
		MaChucVu = maChucVu;
	}

	public String getTenChucVu() {
		return tenChucVu;
	}

	public void setTenChucVu(String tenChucVu) {
		this.tenChucVu = tenChucVu;
	}

	@Override
	public String toString() {
		return "ChucVuDTO [MaChucVu=" + MaChucVu + ", tenChucVu=" + tenChucVu + "]";
	}
	
	
	
}
