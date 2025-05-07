package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.NhaCungCapDTO;

public class NhaCungCapDAO {
	private MySQLConnect mysql = new MySQLConnect();
	
	public ArrayList<NhaCungCapDTO> xuatDSNCC(){
		ArrayList<NhaCungCapDTO> listNCC = new ArrayList<>();
		try {
			String sql = "SELECT * FROM nhacc";
			ResultSet rs = mysql.executeQuery(sql);
			while(rs.next()) {
				NhaCungCapDTO ncc = new NhaCungCapDTO();
				ncc.setMaNCC(rs.getString("MaNCC"));
				ncc.setTenNCC(rs.getString("TenNCC"));
				ncc.setDiaChi(rs.getString("DiaChi"));
				ncc.setSDT(rs.getString("SDT"));
				listNCC.add(ncc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listNCC;
	}
	
	
	public void them(NhaCungCapDTO ncc) {
		try {
			String sql = "INSERT INTO nhacc VALUES(";
			sql += "'" +ncc.getMaNCC()+"',";
			sql += "'" +ncc.getTenNCC()+"',";
			sql += "'" +ncc.getDiaChi()+"',";
			sql += "'" +ncc.getSDT()+"')";
//			System.out.println("SQL INSERT: " + sql); 
			mysql.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sua(NhaCungCapDTO ncc,String maNCC) {
		try {
			String sql = "UPDATE nhacc SET ";
			sql += "MaNCC= '"+ncc.getMaNCC()+"', ";
			sql += "TenNCC= '"+ncc.getTenNCC()+"', ";
			sql += "DiaChi= '"+ncc.getDiaChi()+"', ";
			sql += "SDT= '"+ncc.getSDT()+"'";
			sql += " WHERE MaNCC='"+maNCC+"'";
//			System.out.println("SQL UPDATE: " + sql); 
			mysql.executeUpdate(sql);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void xoa(String maNCC) {
		try {
			String sql = "DELETE FROM nhacc WHERE MaNCC='"+maNCC+"'";
			mysql.executeUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
				String maNCC = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
				String tenNCC = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
				String diaChi = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "";
				String sdt = null;
				if (row.getCell(3) != null) {
					if (row.getCell(3).getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) 
						sdt = String.format("%.0f", row.getCell(3).getNumericCellValue());
					else 
						sdt = row.getCell(3).getStringCellValue();
				} else {
					sdt = "";
				}
				
				// Kiểm tra dữ liệu đầu vào
				if (maNCC.isEmpty() || tenNCC.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
					continue; // Bỏ qua dòng thiếu dữ liệu
				}
				
				String sqlCheck = "SELECT * FROM nhacc WHERE MaNCC = '" + maNCC + "'";
				ResultSet rs = mysql.executeQuery(sqlCheck);
				if(!rs.next()) {
					String sql = "INSERT INTO nhacc (MaNCC, TenNCC, DiaChi, SDT) VALUES (";
					sql += "'" + maNCC + "', ";
					sql += "'" + tenNCC + "', ";
					sql += "'" + diaChi + "', ";
					sql += "'" + sdt + "')";
					System.out.println(sql);
					mysql.executeUpdate(sql);
					addedRows++;
				} else {
					String sql = "UPDATE nhacc SET ";
					sql += "TenNCC = '" + tenNCC + "', ";
					sql += "DiaChi = '" + diaChi + "', ";
					sql += "SDT = '" + sdt + "' ";
					sql += "WHERE MaNCC = '" + maNCC + "'";
					System.out.println(sql);
					mysql.executeUpdate(sql);
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


