package DAO;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DTO.CTKMDTO;

public class CTKMDAO {
	
	private MySQLConnect connection;
	
	public CTKMDAO() {
		connection = new MySQLConnect();
	}
	
	public static CTKMDAO getkhuyenMaiDAO() {
		return new CTKMDAO();
	}
	
	public ArrayList<CTKMDTO> getListkhuyenMai() throws SQLException {
		ArrayList<CTKMDTO> listkhuyenMaiDTO = new ArrayList<CTKMDTO>();
		connection.getConnection();
		
		String sql = "SELECT * FROM ctkm";
		ResultSet rs = connection.executeQuery(sql);
		
		while(rs.next()) {
			String maCTKM = rs.getString("MaCTKM");
			Date ngayBD = rs.getDate("NgayBD");
			Date ngayKT = rs.getDate("NgayKT");
			String tenCTKM =rs.getString("TenCTKM");
			
			CTKMDTO khuyenMai = new CTKMDTO(maCTKM, ngayBD, ngayKT,tenCTKM);
			listkhuyenMaiDTO.add(khuyenMai);
		}
		
		connection.disConnect();
		return listkhuyenMaiDTO;
	}
	
	public CTKMDTO getCTKM_HD(Date ngayLapSQL) throws SQLException {
		String ngayLap = String.valueOf(ngayLapSQL);
		String sql = "SELECT ctkm.*, ctkm_hd.PhanTramGiamGia FROM ctkm"
				+ " JOIN ctkm_hd ON ctkm.MaCTKM = ctkm_hd.MaCTKM"
				+ " WHERE NgayBD <= '"+ ngayLap +"' AND NgayKT >= '"+ ngayLap +"'";
		ResultSet rs = connection.executeQuery(sql);
		
		if(rs.next()) {
			String maCTKM = rs.getString("MaCTKM");
			Date ngayBD = rs.getDate("NgayBD");
			Date ngayKT = rs.getDate("NgayKT");
			String tenCTKM =rs.getString("TenCTKM");
			float phanTramGiamGia = rs.getFloat("PhanTramGiamGia");
			
			return new CTKMDTO(maCTKM, ngayBD, ngayKT,tenCTKM, phanTramGiamGia);
		}
		
		return null;
		
	}
	
	
		
	
	
	
	public void addkhuyenMaiDAO(CTKMDTO x) {
		String sql = "INSERT INTO ctkm(MaCTKM, NgayBD, NgayKT, TenCTKM)"
				+ "VALUES('"+ x.getMaCTKM()+"', '"+ x.getNgayBD() +"', '"+ x.getNgayKT()+ "', '"+ x.getTenCTKM()+"')";
		
		connection.executeUpdate(sql);
	}
	public void addCTKM_SP(String maCTKM, String maSP, double phanTram) {
	    String sql = "INSERT INTO ctkm_sp VALUES('" + maCTKM + "', '" + maSP + "', " + phanTram + ")";
	    connection.executeUpdate(sql);
	}

	public void addCTKM_HD(String maCTKM, String maHD, double phanTram) {
	    String sql = "INSERT INTO ctkm_hd VALUES('" + maCTKM + "', '" + maHD + "', " + phanTram + ")";
	    connection.executeUpdate(sql);
	}

	public void updateCTKMDAO(CTKMDTO ctkm, String loaiCTKMCu, String loaiCTKM, String maSPorHD, String maSPorHDCu, double phanTramValue) {
	    // Cập nhật thông tin chương trình khuyến mãi vào bảng ctkm
		String sqlUpdateCTKM = "UPDATE ctkm SET NgayBD = '" + new java.sql.Date(ctkm.getNgayBD().getTime()) + "', " +
		                       "NgayKT = '" + new java.sql.Date(ctkm.getNgayKT().getTime()) + "', " +
		                       "TenCTKM = '" + ctkm.getTenCTKM() + "' " +
		                       "WHERE MaCTKM = '" + ctkm.getMaCTKM() + "'";
		connection.executeUpdate(sqlUpdateCTKM);

		// Kiểm tra xem loại khuyến mãi có thay đổi hay không
		if (!loaiCTKMCu.equals(loaiCTKM)) {
		    // Nếu loại khuyến mãi thay đổi, thực hiện xóa bản ghi cũ và thêm bản ghi mới vào bảng tương ứng
		    if (loaiCTKMCu.equals("Sản Phẩm") && loaiCTKM.equals("Hóa Đơn")) {
		        // Xóa bản ghi cũ trong bảng ctkm_sp
		        String sqlDeleteSP = "DELETE FROM ctkm_sp WHERE MaCTKM = '" + ctkm.getMaCTKM() + "' AND MaSP = '" + maSPorHDCu + "'";
		        connection.executeUpdate(sqlDeleteSP);

		        // Thêm bản ghi mới vào bảng ctkm_hd
		        String sqlInsertHD = "INSERT INTO ctkm_hd (MaCTKM, MaHD, PhanTramGiamGia) VALUES ('" + ctkm.getMaCTKM() + "', '" + maSPorHD + "', " + phanTramValue + ")";
		        connection.executeUpdate(sqlInsertHD);
		    } else if (loaiCTKMCu.equals("Hóa Đơn") && loaiCTKM.equals("Sản Phẩm")) {
		        // Xóa bản ghi cũ trong bảng ctkm_hd
		        String sqlDeleteHD = "DELETE FROM ctkm_hd WHERE MaCTKM = '" + ctkm.getMaCTKM() + "' AND MaHD = '" + maSPorHDCu + "'";
		        connection.executeUpdate(sqlDeleteHD);

		        // Thêm bản ghi mới vào bảng ctkm_sp
		        String sqlInsertSP = "INSERT INTO ctkm_sp (MaCTKM, MaSP, PhanTramGiamGia) VALUES ('" + ctkm.getMaCTKM() + "', '" + maSPorHD + "', " + phanTramValue + ")";
		        connection.executeUpdate(sqlInsertSP);
		    }
		} else {
		    // Nếu loại khuyến mãi không thay đổi, kiểm tra mã sản phẩm/hoá đơn
		    if (loaiCTKM.equals("Sản Phẩm")) {
		        // Nếu mã SP thay đổi, cần xóa bản ghi cũ và thêm bản ghi mới
		        String sqlDeleteSP = "DELETE FROM ctkm_sp WHERE MaCTKM = '" + ctkm.getMaCTKM() + "'";
		        connection.executeUpdate(sqlDeleteSP);

		        String sqlInsertSP = "INSERT INTO ctkm_sp (MaCTKM, MaSP, PhanTramGiamGia) VALUES ('" + ctkm.getMaCTKM() + "', '" + maSPorHD + "', " + phanTramValue + ")";
		        connection.executeUpdate(sqlInsertSP);
		    } else if (loaiCTKM.equals("Hóa Đơn")) {
		        // Cập nhật phần trăm giảm giá cho hóa đơn
		        String sqlUpdateHD = "UPDATE ctkm_hd SET PhanTramGiamGia = " + phanTramValue + 
		                              " WHERE MaCTKM = '" + ctkm.getMaCTKM() + "' AND MaHD = '" + maSPorHD + "'";
		        connection.executeUpdate(sqlUpdateHD);
		    }
		}
	}




//	    connection.disConnect();
	


	
	
	public void deletekhuyenMaiDAO(CTKMDTO x) {
		String sql  = "DELETE FROM ctkm WHERE MaCTKM ='" + x.getMaCTKM() + "'";
		connection.executeUpdate(sql);
	}
	
	// Thêm phương thức để lấy danh sách MaSP
		public ArrayList<String> getListMaSP() throws SQLException {
			ArrayList<String> listMaSP = new ArrayList<>();
			connection.getConnection();
			
			String sql = "SELECT MaSP FROM sanpham";
			ResultSet rs = connection.executeQuery(sql);
			
			while (rs.next()) {
				listMaSP.add(rs.getString("MaSP"));
			}
			
			rs.close();
			connection.disConnect();
			return listMaSP;
		}
		
		// Thêm phương thức để lấy danh sách MaHD
		public ArrayList<String> getListMaHD() throws SQLException {
			ArrayList<String> listMaHD = new ArrayList<>();
			connection.getConnection();
			
			String sql = "SELECT MaHD FROM hoadon";
			ResultSet rs = connection.executeQuery(sql);
			
			while (rs.next()) {
				listMaHD.add(rs.getString("MaHD"));
			}
			
			rs.close();
			connection.disConnect();
			return listMaHD;
		}
		
		public static void main(String[] args) throws SQLException {
			LocalDate now = LocalDate.now();
			Date ngayLap = Date.valueOf(now);
			CTKMDAO ctkmdao = new CTKMDAO();
			CTKMDTO ctkm = ctkmdao.getCTKM_HD(ngayLap);
			System.out.println(ctkm);
		}
}
