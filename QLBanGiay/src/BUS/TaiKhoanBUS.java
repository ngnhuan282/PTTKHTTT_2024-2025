package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

public class TaiKhoanBUS {
	
	TaiKhoanDAO taiKhoanDAO;
	
	public TaiKhoanBUS()
	{
		taiKhoanDAO = new TaiKhoanDAO();
	}
	
	public boolean login(String tenDangNhap, String matKhau)
	{	
		TaiKhoanDTO taiKhoan = taiKhoanDAO.checkLogin(tenDangNhap, matKhau);
		if(taiKhoan != null)
			return true;
		return false;
	}
	
	public void addAccount(String tenDangNhap, String matKhau) {
		taiKhoanDAO.addAccount(tenDangNhap, matKhau);
	}
	
	public void updateAccount(int id, String tenDangNhap, String matKhau) {
		taiKhoanDAO.updateAccount(id, tenDangNhap, matKhau);
	}
	
	public void deleteAccount(String tenDangNhap, String matKhau) {
		int id = taiKhoanDAO.getMaTK(tenDangNhap, matKhau);
		taiKhoanDAO.deleteAccount(id);
	}
	
	public int getMaTK(String tenDangNhap, String matKhau) {
		return taiKhoanDAO.getMaTK(tenDangNhap, matKhau);
	}
	
	public TaiKhoanDTO getAccount(String MaNV) {
		return taiKhoanDAO.getAccount(MaNV);
	}
	
	public String getUsername(int maTK) {
		return taiKhoanDAO.getUsername(maTK);
	}
}
