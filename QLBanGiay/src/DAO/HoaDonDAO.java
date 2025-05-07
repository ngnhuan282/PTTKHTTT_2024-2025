package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;

public class HoaDonDAO {
	private MySQLConnect connection;
	
	public HoaDonDAO() {
		connection = new MySQLConnect();
	}
	
	public ArrayList<HoaDonDTO> getListHoaDon() throws SQLException {
		ArrayList<HoaDonDTO> listHoaDonDTO = new ArrayList<HoaDonDTO>();
		connection.getConnection();
		String sql = "SELECT * FROM hoadon";
		ResultSet rs = connection.executeQuery(sql);
		while(rs.next()) {
			String maHD = rs.getString("MaHD");
			String maKH = rs.getString("MaKH");
			String maNV = rs.getString("MaNV");
			Date ngayLap = Date.valueOf(rs.getString("NgayLap"));
			Double tongTien = Double.valueOf(rs.getString("TongTien"));
			
			HoaDonDTO hoaDon = new HoaDonDTO(maHD, maKH, maNV, ngayLap, tongTien);
			listHoaDonDTO.add(hoaDon);
			
		}
		connection.disConnect();
		return listHoaDonDTO;
		
	}
	
	public void addHoaDon(HoaDonDTO x) {
		String sql = "INSERT INTO hoadon (MaHD, MaKH, MaNV, NgayLap, TongTien)"
				+ " VALUES ('"+ x.getMaHD() + "', '" + x.getMaKH() + "', '" + x.getMaNV() + "', '" + x.getNgayLap() + "', '" + x.getTongTien() + "')";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void updateHoaDon(HoaDonDTO x) {
		String sql = "UPDATE hoadon"
				+ " SET"
				+ " MaKH = '"+ x.getMaKH() + "',"
				+ " MaNV = '"+ x.getMaNV() + "',"
				+ " NgayLap = '"+ x.getNgayLap() +"',"
				+ " TongTien = '"+ x.getTongTien() +"'"
				+ " WHERE MaHD = '"+ x.getMaHD() +"'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void deleteHoaDon(HoaDonDTO x) {
		String sql = "DELETE FROM hoadon WHERE MaHD = '"+ x.getMaHD() +"'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void updateTongTien(HoaDonDTO x) {
		String sql = "UPDATE hoadon"
				+ " SET"
				+ " TongTien = '"+ x.getTongTien() +"'"
				+ " WHERE MaHD = '"+ x.getMaHD() +"'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	
}
