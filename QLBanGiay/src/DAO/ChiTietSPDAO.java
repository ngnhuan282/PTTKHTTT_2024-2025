package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.ChiTietSPDTO;

public class ChiTietSPDAO {
    public ChiTietSPDAO() {
    }

    public ArrayList<ChiTietSPDTO> docDSCTSP(String maSP) {
        ArrayList<ChiTietSPDTO> dsCTSP = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "SELECT * FROM ChiTietSP WHERE MaSP = '" + maSP + "'";
            System.out.println("Executing query: " + sql);
            ResultSet rs = mysql.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    String mauSac = rs.getString("MauSac");
                    int kichThuoc = rs.getInt("KichThuoc");
                    String chatLieu = rs.getString("ChatLieu");
                    String kieuDang = rs.getString("KieuDang");
                    ChiTietSPDTO ctsp = new ChiTietSPDTO(maSP, mauSac, kichThuoc, chatLieu, kieuDang);
                    dsCTSP.add(ctsp);
                }
                rs.close();
            }
            mysql.disConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsCTSP;
    }

    public void add(ChiTietSPDTO ctsp) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "INSERT INTO ChiTietSP (MaSP, MauSac, KichThuoc, ChatLieu, KieuDang) VALUES (";
            sql += "'" + ctsp.getMaSP() + "', ";
            sql += "'" + ctsp.getMauSac() + "', ";
            sql += ctsp.getKichThuoc() + ", ";
            sql += "'" + ctsp.getChatLieu() + "', ";
            sql += "'" + ctsp.getKieuDang() + "')";
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.disConnect();
        }
    }

    public void delete(String maSP, String mauSac, int kichThuoc) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "DELETE FROM ChiTietSP WHERE MaSP = '" + maSP + "' AND MauSac = '" + mauSac + "' AND KichThuoc = " + kichThuoc;
            System.out.println("Executing delete: " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.disConnect();
        }
    }

    // Giữ phương thức cũ để xóa toàn bộ chi tiết theo maSP
    public void delete(String maSP) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "DELETE FROM ChiTietSP WHERE MaSP = '" + maSP + "'";
            System.out.println("Executing delete all: " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.disConnect();
        }
    }

    public void update(ChiTietSPDTO ctsp) {
        MySQLConnect mysql = new MySQLConnect();
        try {
            String sql = "UPDATE ChiTietSP SET ";
            sql += "ChatLieu = '" + ctsp.getChatLieu() + "', ";
            sql += "KieuDang = '" + ctsp.getKieuDang() + "' ";
            sql += "WHERE MaSP = '" + ctsp.getMaSP() + "' AND MauSac = '" + ctsp.getMauSac() + "' AND KichThuoc = " + ctsp.getKichThuoc();
            System.out.println("Executing update: " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.disConnect();
        }
    }
}