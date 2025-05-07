package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietHDDTO;

public class ChiTietHoaDonDAO {
	private MySQLConnect connection;
	
	public ChiTietHoaDonDAO() {
		connection = new MySQLConnect();
	}
	
	public ArrayList<ChiTietHDDTO> getListCTHD() throws SQLException{
		ArrayList<ChiTietHDDTO> listCTHDDTO = new ArrayList<ChiTietHDDTO>();
		connection.getConnection();
		String sql = "SELECT * FROM chitiethoadon";
		ResultSet rs = connection.executeQuery(sql);
		while(rs.next()) {
			String maHD = rs.getString("MaHD");
			String maSP = rs.getString("MaSP");
			int soLuong = Integer.valueOf(rs.getString("SoLuong"));
			Double donGia = Double.valueOf(rs.getString("DonGia"));
			Double thanhTien = Double.valueOf(rs.getString("ThanhTien"));
			ChiTietHDDTO chiTietHDDTO = new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien);
			listCTHDDTO.add(chiTietHDDTO);
		}
		return listCTHDDTO;
	}
	
	public void addCTHD(ChiTietHDDTO x) {
		connection.getConnection();
		String sql = "INSERT INTO chitiethoadon(MaHD, MaSP, SoLuong, DonGia, ThanhTien)"
				+ " VALUES('"+ x.getMaHD()+ "', '"+ x.getMaSP() +"', '"+ x.getSoLuong() +"', '"+ x.getDonGia() +"', '"+ x.getThanhTien() +"')";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void updateCTHD(ChiTietHDDTO x) {
		String sql = "UPDATE chitiethoadon"
				+ " SET"
				+ " MaSP = '"+ x.getMaSP() + "',"
				+ " SoLuong = '"+ x.getSoLuong() + "',"
				+ " DonGia = '"+ x.getDonGia() +"',"
				+ " ThanhTien = '"+ x.getThanhTien() +"'"
				+ " WHERE MaHD = '"+ x.getMaHD() +"'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void deleteCTHD(String maHD) {
		String sql = "DELETE FROM chitiethoadon WHERE MaHD = '"+ maHD +"'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void deleteCTHD(String maHD, String maSP) {
		String sql = "DELETE FROM chitiethoadon WHERE MaHD = '"+ maHD +"' AND MaSP = '" + maSP + "'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void updateSoLuongSP(String maSP, int soLuong) {
		String sql = "UPDATE sanpham SET SoLuong = SoLuong - "+ soLuong + " WHERE MaSP = '" + maSP + "'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void tangSoLuongSP(String maSP, int soLuong) {
		String sql = "UPDATE sanpham SET SoLuong = SoLuong + "+ soLuong + " WHERE MaSP = '" + maSP + "'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
}
