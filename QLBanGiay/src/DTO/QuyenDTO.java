package DTO;

public class QuyenDTO {
	private int maQuyen;
	private String tenQuyen;
	
	public QuyenDTO() {
		
	}

	public QuyenDTO(int maQuyen, String tenQuyen) {
		this.maQuyen = maQuyen;
		this.tenQuyen = tenQuyen;
	}

	public int getMaQuyen() {
		return maQuyen;
	}

	public void setMaQuyen(int maQuyen) {
		this.maQuyen = maQuyen;
	}

	public String getTenQuyen() {
		return tenQuyen;
	}

	public void setTenQuyen(String tenQuyen) {
		this.tenQuyen = tenQuyen;
	}

	@Override
	public String toString() {
		return "QuyenDTO [maQuyen=" + maQuyen + ", tenQuyen=" + tenQuyen + "]";
	}
	
	
	
}
