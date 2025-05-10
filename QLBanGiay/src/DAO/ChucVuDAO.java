package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChucVuDTO;

public class ChucVuDAO {
	private MySQLConnect connect;
	
	public ChucVuDAO() {
		connect = new MySQLConnect();
	}
	
	public ArrayList<ChucVuDTO> getListChucVuDTO(){
		ArrayList<ChucVuDTO> result = new ArrayList<ChucVuDTO>();
		try {
			String sql = "SELECT * FROM chucvu";
			
			ResultSet rs = connect.executeQuery(sql);
			
			while(rs.next()) {
				int maCV = rs.getInt("MaChucVu");
				String tenCV = rs.getString("TenChucVu");
				
				result.add(new ChucVuDTO(maCV, tenCV));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int getMaChucVu(String tenChucVu) {
			try {
				String sql = "SELECT MaChucVu FROM chucvu WHERE TenChucVu = '"+ tenChucVu +"'";
				
				ResultSet rs = connect.executeQuery(sql);
				
				if(rs.next()) {
					return rs.getInt("MaChucVu");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return 0;
	}
	
	public ChucVuDTO getChucVu(String maNV) {
		
		try {
			String sql = "SELECT * FROM chucvu cv JOIN nhanvien nv ON cv.MaChucVU = nv.MaChucVu AND MaNV = '"+ maNV +"'";
			
			ResultSet rs = connect.executeQuery(sql);
			
			if(rs.next()) {
				return new ChucVuDTO(rs.getInt("MaChucVu"), rs.getString("TenChucVu"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getCurrentMaChucVu(int maTK) {
		try {
			String sql = "SELECT cv.MaChucVu FROM chucvu cv "
					+ "JOIN nhanvien nv ON cv.MaChucVu = nv.MaChucVu "
					+ "JOIN taikhoan tk ON nv.MaTK = tk.ID "
					+ "WHERE tk.ID ="+ maTK +"";
			ResultSet rs = connect.executeQuery(sql);
			if(rs.next()) {
				return rs.getInt("MaChucVu");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
