package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ";
			sql += "'" + tenDangNhap + "'";
			sql += "AND Matkhau= '" + matKhau + "'";
			ResultSet rs = mysql.executeQuery(sql);
			if(rs.next())
			{	
				taiKhoan = new TaiKhoanDTO();
				String username = rs.getString("TenDangNhap");
				String password = rs.getString("Matkhau");
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
	
	public void addAccount(String tenDangNhap, String matKhau) {
		String sql = "INSERT INTO taikhoan (TenDangNhap, Matkhau) "
				+ "VALUES ('"+ tenDangNhap +"', '"+ matKhau +"')";
		mysql.executeUpdate(sql);
	}
	
	public void updateAccount(int id, String tenDangNhap, String matKhau) {
		String sql = "UPDATE taikhoan "
				+ "SET "
				+ "TenDangNhap = '"+ tenDangNhap +"' "
				+ ", Matkhau = '"+ matKhau +"' "
				+ "WHERE ID = '"+ id + "' ";
		System.out.println(sql);
		mysql.executeUpdate(sql);
	}
	
	public void deleteAccount(int id) {
		String sql = "DELETE FROM taikhoan WHERE ID = "+ id +"";
		mysql.executeUpdate(sql);
	}
	
	public int getMaTK(String tenDangNhap, String matKhau) {
		try {
			String sql = "SELECT ID FROM taikhoan WHERE TenDangNhap = '"+ tenDangNhap +"' AND Matkhau = '"+ matKhau +"'";
			ResultSet rs = mysql.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public TaiKhoanDTO getAccount(String MaNV) {
		try {
			String sql = "SELECT TenDangNhap, Matkhau FROM taikhoan JOIN nhanvien ON taikhoan.ID = nhanvien.MaTK WHERE nhanvien.MaNV = '"+ MaNV + "'";
			
			ResultSet rs = mysql.executeQuery(sql);
			if(rs.next()) {
				String username = rs.getString("TenDangNhap");
				String pass = rs.getString("Matkhau");
				
				TaiKhoanDTO tk = new TaiKhoanDTO(username, pass);
				
				return tk;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUsername(int maTK) {
		String sql = "SELECT TenDangNhap FROM taikhoan WHERE ID = "+ maTK +"";
		ResultSet rs = mysql.executeQuery(sql);
		
		try {
			if(rs.next())
				return rs.getString("TenDangNhap");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
