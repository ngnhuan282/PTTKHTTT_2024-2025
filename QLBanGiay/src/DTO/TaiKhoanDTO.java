package DTO;

public class TaiKhoanDTO {
	private String tenDangNhap;
	private String matKhau;
	
	
	
	public TaiKhoanDTO(String tenDangNhap, String matKhau) 
	{
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
	}

	public TaiKhoanDTO()
	{
		
	}
	
	public String getTenDangNhap() {
		return tenDangNhap;
	}
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	
}
