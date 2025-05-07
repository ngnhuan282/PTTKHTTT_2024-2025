package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietPNDTO;

public class ChiTietPNDAO {
	
	public ArrayList<ChiTietPNDTO> xuatDSCTPN(){
		ArrayList<ChiTietPNDTO> listCTPN = new ArrayList<>();
		try {
			MySQLConnect mysql = new MySQLConnect();
			String sql = "SELECT * FROM ctpnh";
			ResultSet rs = mysql.executeQuery(sql);
			while(rs.next()) {
				ChiTietPNDTO ctpn = new ChiTietPNDTO();
				ctpn.setMaPhieuNH(rs.getString("MaPhieuNH"));
				ctpn.setMaSP(rs.getString("MaSP"));
				ctpn.setSoLuong(rs.getInt("SoLuong"));
				ctpn.setDonGia(rs.getDouble("DonGia"));
				ctpn.setThanhTien(rs.getDouble("ThanhTien"));
				listCTPN.add(ctpn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCTPN;
	}
	
	
	public void them(ChiTietPNDTO ctpn) {
		
		MySQLConnect mysql = new MySQLConnect();
		String sql = "INSERT INTO ctpnh VALUES(";
		sql+= "'" +ctpn.getMaPhieuNH()+"',";
		sql+= "'" +ctpn.getMaSP()+"',";
		sql+= "'" +ctpn.getSoLuong()+"',";
		sql+= "'" +ctpn.getDonGia()+"',";
		sql+= "'" +ctpn.getThanhTien()+"')";
		System.out.println("SQL Insert : "+sql);
		mysql.executeUpdate(sql);
		
	}
	
	
	public void xoa(String maPN) {
	
			MySQLConnect mysql = new MySQLConnect();
			String sql = "DELETE FROM ctpnh WHERE MaPhieuNH ='"+maPN+"'";
			System.out.println("SQL DELETE: " + sql);
			mysql.executeUpdate(sql);
		
	}
}
