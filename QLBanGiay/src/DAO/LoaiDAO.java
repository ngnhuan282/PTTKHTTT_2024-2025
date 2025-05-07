package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.LoaiDTO;

public class LoaiDAO {
	MySQLConnect mysql = new MySQLConnect();
	
	public LoaiDAO()
	{
		
	}
	
	public ArrayList<LoaiDTO> docDSLoai()
	{	
		ArrayList<LoaiDTO> dsloai = new ArrayList<LoaiDTO>();
		try {
			String sql = "SELECT * FROM PhanLoai";
			ResultSet rs = mysql.executeQuery(sql);
			while(rs.next())
			{
				int maLoai = rs.getInt("MaLoaiSP");
				String tenLoai = rs.getString("TenLoaiSP");
				LoaiDTO loai = new LoaiDTO(maLoai, tenLoai);
				dsloai.add(loai);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsloai;
	}
	
	public void add(LoaiDTO loai)
	{
		try {
			String sql = "INSERT INTO PhanLoai (TenLoaiSP) VALUES(";
			sql += "'" + loai.getTenLoaiSP() + "')";
			mysql.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update(LoaiDTO loai)
	{
		try {
			String sql = "UPDATE PhanLoai SET TenLoaiSP= ";
			sql += "'" + loai.getTenLoaiSP() + "'";
			sql += "WHERE MaLoaiSP= ";
			sql += "'" + loai.getMaLoaiSP() + "'";
			mysql.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void delete(int maLoaiSP)
	{
		try {
			String sql = "DELETE FROM PhanLoai WHERE MaLoaiSP= "; 
			sql +="'"+ maLoaiSP +"'";
			mysql.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
