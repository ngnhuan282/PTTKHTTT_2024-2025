package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PhieuNhapDTO;

public class PhieuNhapDAO {
	
	public ArrayList<PhieuNhapDTO> xuatDSPN(){
		ArrayList<PhieuNhapDTO> listPN = new ArrayList<>();
		try {
			MySQLConnect mysql = new MySQLConnect();
			String sql ="SELECT * FROM phieunhaphang";
			ResultSet rs = mysql.executeQuery(sql);
			while(rs.next()) {
				PhieuNhapDTO pn = new PhieuNhapDTO();
				pn.setMaPhieuNH(rs.getString("MaPhieuNH"));
				pn.setMaNV(rs.getString("MaNV"));
				pn.setMaNCC(rs.getString("MaNCC"));
				pn.setTongTien(rs.getDouble("TongTien"));
				pn.setNgayNhap(rs.getDate("NgayNhap"));
				listPN.add(pn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listPN;
	}
	
	
	public void them(PhieuNhapDTO pn) {
		
		MySQLConnect mysql = new MySQLConnect();
		String sql = "INSERT INTO phieunhaphang VALUES(";
		sql+= "'" +pn.getMaPhieuNH()+"',";
		sql+= "'" +pn.getMaNV()+"',";
		sql+= "'" +pn.getMaNCC()+"',";
		sql+= "'" +pn.getTongTien()+"',";
		sql+= "'" +pn.getNgayNhap()+"')";
		System.out.println("SQL Insert : "+sql);
		mysql.executeUpdate(sql);
		
	}
	
	public void sua(PhieuNhapDTO pn, String maPN) {

		MySQLConnect mysql = new MySQLConnect();
		String sql = "UPDATE phieunhaphang SET ";
		sql+= "MaPhieuNH= '"+pn.getMaPhieuNH()+"',";
		sql+= "MaNV= '"+pn.getMaNV()+"',";
		sql+= "MaNCC= '"+pn.getMaNCC()+"',";
		sql+= "TongTien= '"+pn.getTongTien()+"',";
		sql+= "NgayNhap= '"+pn.getNgayNhap()+"'";
		sql+= "WHERE MaPhieuNH='"+maPN+"'";
		System.out.println("SQL UPDATE: " + sql); 
		mysql.executeUpdate(sql);
		
	}
	
	
	public void xoa(String maPN) {
	
			MySQLConnect mysql = new MySQLConnect();
			String sql ="DELETE FROM phieunhaphang WHERE MaPhieuNH ='"+maPN+"'";
			System.out.println("SQL DELETE: " + sql);
	        mysql.executeUpdate(sql);
		
	}
}
