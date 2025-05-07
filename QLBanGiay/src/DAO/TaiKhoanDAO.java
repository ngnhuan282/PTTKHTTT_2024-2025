package DAO;

import java.sql.ResultSet;

import DTO.TaiKhoanDTO;

public class TaiKhoanDAO {
	private MySQLConnect mysql = new MySQLConnect();
	
	public TaiKhoanDAO()
	{
		
	}
	
	public TaiKhoanDTO checkLogin(String tenDangNhap, String matKhau)
	{	
		TaiKhoanDTO taiKhoan = null;
		try {
			String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ";
			sql += "'" + tenDangNhap + "'";
			sql += "AND matKhau= '" + matKhau + "'";
			ResultSet rs = mysql.executeQuery(sql);
			if(rs.next())
			{	
				taiKhoan = new TaiKhoanDTO();
				String username = rs.getString("TenDangNhap");
				String password = rs.getString("MatKhau");
				taiKhoan.setTenDangNhap(username);
				taiKhoan.setMatKhau(password);
			}
			rs.close();
			mysql.disConnect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return taiKhoan;
	}
}
