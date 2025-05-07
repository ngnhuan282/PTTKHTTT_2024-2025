package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.SanPhamDTO;

public class SanPhamDAO {
	private MySQLConnect mysql = new MySQLConnect();
	
	public SanPhamDAO()
	{
		
	}
	
	public ArrayList<SanPhamDTO> docDSSP()
	{	
		ArrayList<SanPhamDTO> dssp = new ArrayList<>();
		try {
			
			MySQLConnect mysql = new MySQLConnect();
			String sql = "SELECT * FROM SanPham";
			ResultSet rs = mysql.executeQuery(sql);
			while(rs.next())
			{
				String maSP = rs.getString("MaSP");
				String tenSP = rs.getString("TenSP");
				int maLoaiSP = rs.getInt("MaLoaiSP");
				int soLuong = rs.getInt("SoLuong");
				double donGia = rs.getDouble("DonGia");
				String donViTinh = rs.getString("DonViTinh");
				String chatLieu = rs.getString("ChatLieu");
				String kieuDang = rs.getString("KieuDang");
				String mauSac = rs.getString("MauSac");
				int kichThuoc = rs.getInt("KichThuoc");
				
				SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, maLoaiSP, soLuong, donGia, donViTinh,
											mauSac, kichThuoc, chatLieu, kieuDang);
				dssp.add(sp);
			}
			rs.close();
			mysql.disConnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dssp;
	}
	
	public void add(SanPhamDTO sp)
	{
		String sql = "INSERT INTO SanPham VALUES(";
		sql += "'" + sp.getMaSP() + "', ";
        sql += "'" + sp.getTenSP() + "', ";
        sql += "'" + sp.getMaLoaiSP() + "', ";
        sql += "'" + sp.getSoLuong() + "', ";
        sql += "'" + sp.getDonGia() + "', ";  
        sql += "'" + sp.getDonViTinh() + "', ";
        sql += "'" + sp.getMauSac() + "', ";
        sql += "'" + sp.getKichThuoc() + "', "; 
        sql += "'" + sp.getChatLieu() + "', ";
        sql += "'" + sp.getKieuDang() + "')";
		
		mysql.executeUpdate(sql);
		mysql.disConnect();
	}
	
	public void update(SanPhamDTO sp)
	{
		String sql = "UPDATE SanPham SET ";
		sql += "MaSP='" + sp.getMaSP() + "', ";
		sql += "TenSP='" + sp.getTenSP() + "', ";
		sql += "MaLoaiSP='" + sp.getMaLoaiSP() + "', ";
		sql += "SoLuong='" + sp.getSoLuong() + "', ";
		sql += "DonGia='" + sp.getDonGia() + "', ";
		sql += "DonViTinh='" + sp.getDonViTinh() + "', ";
		sql += "ChatLieu='" + sp.getChatLieu() + "', ";
		sql += "MauSac='" + sp.getMauSac() + "', ";
		sql += "KieuDang='" + sp.getKieuDang() + "', ";
		sql += "KichThuoc='" + sp.getKichThuoc() + "',";
		sql += "WHERE MaSP= '" + sp.getMaSP() + "'";
		mysql.executeUpdate(sql);
		mysql.disConnect();
	}
	
	public void delete(String MaSP)
	{
		String sql = "DELETE FROM SanPham WHERE MaSP = '" +MaSP + "'";
		mysql.executeUpdate(sql);
		mysql.disConnect();
	}
	
	//Phieu nhapppppppppp
	public void capNhapSoLuong(String maSP, int soLuong) {
		String sql = "UPDATE sanpham SET SoLuong ="+soLuong+" WHERE MaSP = '"+maSP+"'";
		mysql.executeUpdate(sql);
		mysql.disConnect();
	}
	
	
	public int[] ImportExcel(File file) {
	    int addedRows = 0;
	    int updatedRows = 0;
	    try {
	        FileInputStream in = new FileInputStream(file);
	        XSSFWorkbook workbook = new XSSFWorkbook(in);
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        Row row;
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            row = sheet.getRow(i);
	            String maSP = row.getCell(0).getStringCellValue();
	            String tenSP = row.getCell(1).getStringCellValue();
	            String loai = row.getCell(2).getStringCellValue();
	            int soLuong = (int) row.getCell(4).getNumericCellValue();
	            int gia = (int) row.getCell(3).getNumericCellValue();
	            String DVT = row.getCell(5).getStringCellValue();
	            String mauSac = row.getCell(6).getStringCellValue();
	            int kichThuoc = (int) row.getCell(7).getNumericCellValue();
	            String chatLieu = row.getCell(8).getStringCellValue();
	            String kieuDang = row.getCell(9).getStringCellValue();

	            String sqlCheckLoai = "SELECT MaLoaiSP FROM PhanLoai WHERE TenLoaiSP= '" + loai + "'";
	            ResultSet rsLoai = mysql.executeQuery(sqlCheckLoai);
	            int maLoaiSP;
	            if (rsLoai.next()) {
	                maLoaiSP = rsLoai.getInt("MaLoaiSP");
	            } else {
	                String sql = "INSERT INTO PhanLoai (TenLoaiSP) VALUES ('" + loai + "')";
	                mysql.executeUpdate(sql);
	                rsLoai = mysql.executeQuery("SELECT LAST_INSERT_ID() as MaLoaiSP");
	                rsLoai.next();
	                maLoaiSP = rsLoai.getInt("MaLoaiSP");
	            }

	            String sqlCheckSP = "SELECT * FROM SANPHAM WHERE MaSP = '" + maSP + "'";
	            ResultSet rsSP = mysql.executeQuery(sqlCheckSP);

	            if (!rsSP.next()) { // Sản phẩm chưa tồn tại
	                String sql = "INSERT INTO sanpham (MaSP, TenSP, SoLuong, DonGia, DonViTinh, MaLoaiSP, MauSac, KichThuoc, ChatLieu, KieuDang) VALUES (";
	                sql += "'" + maSP + "', ";
	                sql += "'" + tenSP + "', ";
	                sql += "'" + soLuong + "', ";
	                sql += "'" + gia + "', ";
	                sql += "'" + DVT + "', ";
	                sql += "'" + maLoaiSP + "', ";
	                sql += "'" + mauSac + "', ";
	                sql += "'" + kichThuoc + "', ";
	                sql += "'" + chatLieu + "', ";
	                sql += "'" + kieuDang + "')";
	                mysql.executeUpdate(sql);
	                addedRows++;
	            } else { // Sản phẩm đã tồn tại
	                String sql = "UPDATE sanpham SET ";
	                sql += "TenSP= '" + tenSP + "', ";
	                sql += "MaLoaiSP= '" + maLoaiSP + "', ";
	                sql += "SoLuong= '" + soLuong + "', ";
	                sql += "DonGia= '" + gia + "', ";
	                sql += "DonViTinh= '" + DVT + "', ";
	                sql += "MauSac= '" + mauSac + "', ";
	                sql += "KichThuoc= '" + kichThuoc + "', ";
	                sql += "ChatLieu= '" + chatLieu + "', ";
	                sql += "KieuDang= '" + kieuDang + "' ";
	                sql += "WHERE MaSP= '" + maSP + "'";
	                mysql.executeUpdate(sql);
	                updatedRows++;
	            }
	        }
	        in.close();
	        workbook.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Lỗi khi nhập Excel: " + e.getMessage());
	    }
	    return new int[]{addedRows, updatedRows};
	}
}
