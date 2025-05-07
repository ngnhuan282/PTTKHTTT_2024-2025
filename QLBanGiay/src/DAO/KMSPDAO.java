package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.KMSPDTO;

public class KMSPDAO {
	private MySQLConnect connection;
	
	public KMSPDAO() {
		connection = new MySQLConnect();
	}
	
	public ArrayList<KMSPDTO> getListKMSP(Date ngayLapSQL) throws SQLException{
		String ngayLap = String.valueOf(ngayLapSQL);
		String sql = "SELECT ctkm.*, ctkm_sp.MaSP, ctkm_sp.PhanTramGiamGia FROM ctkm"
				+ " JOIN ctkm_sp ON ctkm.MaCTKM = ctkm_sp.MaCTKM"
				+ " WHERE NgayBD <= '"+ ngayLap + "' AND NgayKT >= '"+ ngayLap +"'";
		
		ArrayList<KMSPDTO> result = new ArrayList<KMSPDTO>();
		ResultSet rs = connection.executeQuery(sql);
		
		while(rs.next()) {
			String maCTKM = rs.getString("MaCTKM");
			Date ngayBD = rs.getDate("NgayBD");
			Date ngayKT = rs.getDate("NgayKT");
			String tenCTKM = rs.getString("TenCTKM");
			String maSP = rs.getString("MaSP");
			float phanTram = rs.getFloat("PhanTramGiamGia");
			
			KMSPDTO kmspdto = new KMSPDTO(maCTKM, ngayBD, ngayKT, tenCTKM, maSP, phanTram);
			result.add(kmspdto);
			
		}
		
		return result;
	}
}
