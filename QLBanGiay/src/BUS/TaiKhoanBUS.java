package BUS;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;

public class TaiKhoanBUS {
	
	public TaiKhoanBUS()
	{
		
	}
	
	public boolean login(String tenDangNhap, String matKhau)
	{	
		TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
		TaiKhoanDTO taiKhoan = taiKhoanDAO.checkLogin(tenDangNhap, matKhau);
		if(taiKhoan != null)
			return true;
		return false;
	}
}
