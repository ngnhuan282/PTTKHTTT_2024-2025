package DTO;

public class LoaiDTO {
	private int maLoaiSP;
	private String tenLoaiSP;
	
	public LoaiDTO()
	{
		
	}
	
	public LoaiDTO(int maLoaiSP, String tenLoaiSP) {
		super();
		this.maLoaiSP = maLoaiSP;
		this.tenLoaiSP = tenLoaiSP;
	}

	public LoaiDTO(String tenLoaiSP) {
		this.tenLoaiSP = tenLoaiSP;
	}

	public int getMaLoaiSP() {
		return maLoaiSP;
	}

	public void setMaLoaiSP(int maLoaiSP) {
		this.maLoaiSP = maLoaiSP;
	}

	public String getTenLoaiSP() {
		return tenLoaiSP;
	}

	public void setTenLoaiSP(String tenLoaiSP) {
		this.tenLoaiSP = tenLoaiSP;
	}
	
	
}
