package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.QuyenDTO;

public class QuyenDAO {
	private MySQLConnect connect;
	
	public QuyenDAO() {
		connect = new MySQLConnect();
		
	}
	
	public ArrayList<QuyenDTO> getListQuyen(){
		ArrayList<QuyenDTO> result = new ArrayList<QuyenDTO>();
		try {
			String sql = "SELECT * FROM quyen";
			
			ResultSet rs = connect.executeQuery(sql);
			
			while(rs.next()) {
				int maQuyen = rs.getInt("MaQuyen");
				String tenQUyen = rs.getString("TenQuyen");
				
				result.add(new QuyenDTO(maQuyen, tenQUyen));
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		connect.disConnect();
		return result;
	}
	
	public void phanQuyenNV(int maTK, int maQuyen) {
		String sql = "INSERT INTO chitietphanquyen (MaTK, MaQuyen) VALUES ("+ maTK +", "+ maQuyen + " )";
		connect.executeUpdate(sql);
		connect.disConnect();
	}
	
	public void clearPhanQuyenNV(int maTK) {
		String sql = "DELETE FROM chitietphanquyen WHERE MaTK = "+ maTK +"";
		connect.executeUpdate(sql);
		connect.disConnect();
	}
	
	public boolean checkQuyen(int maTK, int maQuyen) {
		try {
			String sql = "SELECT 1 FROM chitietphanquyen WHERE MaTK = "+ maTK +" AND MaQuyen = "+ maQuyen;
			
			ResultSet rs = connect.executeQuery(sql);
			if(rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

}
