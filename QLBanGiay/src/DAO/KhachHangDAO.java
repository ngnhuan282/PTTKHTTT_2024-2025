package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.KhachHangDTO;

public class KhachHangDAO {
	
	private MySQLConnect connection;
	
	public KhachHangDAO() {
		connection = new MySQLConnect();
	}
	
	public static KhachHangDAO getKhachHangDAO() {
		return new KhachHangDAO();
	}
	
	public ArrayList<KhachHangDTO> getListKhachHang() throws SQLException {
		ArrayList<KhachHangDTO> listKhachHangDTO = new ArrayList<KhachHangDTO>();
		connection.getConnection();
		
		String sql = "SELECT * FROM khachhang";
		ResultSet rs = connection.executeQuery(sql);
		
		while(rs.next()) {
			String maKH = rs.getString("MaKH");
			String ho = rs.getString("Ho");
			String ten = rs.getString("Ten");
			String sdt = rs.getString("SDT");
			String diaChi = rs.getString("DiaChi");
			
			KhachHangDTO khachHang = new KhachHangDTO(maKH, ho, ten, sdt, diaChi);
			listKhachHangDTO.add(khachHang);
		}
		
		connection.disConnect();
		return listKhachHangDTO;
	}
	
	public void addKhachHangDAO(KhachHangDTO x) {
		String sql = "INSERT INTO khachhang(MaKH, Ho, Ten, SDT, DiaChi)"
				+ "VALUES('"+ x.getMaKH()+"', '"+ x.getHo() +"', '"+ x.getTen() +"', '" + x.getSdt() + "', '" + x.getDiaChi() + "')";
		connection.executeUpdate(sql);
	}
	
	public void updateKhachHangDAO(KhachHangDTO x) {
		String sql = "UPDATE khachhang"
				+ " SET"
				+ " Ho = '" + x.getHo() + "'"
				+ ", Ten = '" + x.getTen() + "'"
				+ ", DiaChi = '" + x.getDiaChi() + "'"
				+ ", SDT = '" + x.getSdt() + "'"
				+ " WHERE MaKH = '" + x.getMaKH() + "'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public void deleteKhachHangDAO(KhachHangDTO x) {
		String sql  = "DELETE FROM khachhang WHERE MaKH ='" + x.getMaKH() + "'";
		connection.executeUpdate(sql);
		connection.disConnect();
	}
	
	public int[] ImportExcel(File file) {
		int addedRows = 0;
		int updatedRows = 0;
		try {
			FileInputStream in = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(in);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Row row;
			for(int i=1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row == null) continue; // Bỏ qua hàng rỗng
				String maKH = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
				String ho = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
				String ten = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
				String sdt = null;
				if (row.getCell(3) != null) {
					if (row.getCell(3).getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) 
						sdt = String.format("%.0f", row.getCell(3).getNumericCellValue());
					else 
						sdt = row.getCell(3).getStringCellValue();
				} else {
					sdt = "";
				}
				String diaChi = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : "";
				
				// Kiểm tra dữ liệu đầu vào
				if (maKH.isEmpty() || ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
					continue; // Bỏ qua dòng thiếu dữ liệu
				}
				
				String sqlCheck = "SELECT * FROM khachhang WHERE MaKH = '" + maKH + "'";
				ResultSet rs = connection.executeQuery(sqlCheck);
				if(!rs.next()) {
					String sql = "INSERT INTO khachhang (MaKH, Ho, Ten, SDT, DiaChi) VALUES (";
					sql += "'" + maKH + "', ";
					sql += "'" + ho + "', ";
					sql += "'" + ten + "', ";
					sql += "'" + sdt + "', ";
					sql += "'" + diaChi + "')";
					System.out.println(sql);
					connection.executeUpdate(sql);
					addedRows++;
				} else {
					String sql = "UPDATE khachhang SET ";
					sql += "Ho = '" + ho + "', ";
					sql += "Ten = '" + ten + "', ";
					sql += "SDT = '" + sdt + "', ";
					sql += "DiaChi = '" + diaChi + "' ";
					sql += "WHERE MaKH = '" + maKH + "'";
					System.out.println(sql);
					connection.executeUpdate(sql);
					updatedRows++;
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Lỗi khi nhập Excel: " + e.getMessage());
		}
		return new int[]{addedRows, updatedRows};
	}
}
