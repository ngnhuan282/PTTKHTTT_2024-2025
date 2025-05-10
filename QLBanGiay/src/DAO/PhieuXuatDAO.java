package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PhieuXuatDTO;

public class PhieuXuatDAO {

    public ArrayList<PhieuXuatDTO> xuatDSPX() {
        ArrayList<PhieuXuatDTO> listPX = new ArrayList<>();
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "SELECT * FROM phieuxuat";
            ResultSet rs = mysql.executeQuery(sql);
            while (rs.next()) {
            	 PhieuXuatDTO px = new PhieuXuatDTO();
                 px.setMaPX(rs.getString("MaPX"));
                 px.setMaNV(rs.getString("MaNV"));
                 px.setNgayXuat(rs.getDate("NgayXuat"));
                 px.setGhiChu(rs.getString("GhiChu"));
                 px.setTongTien(rs.getDouble("TongTien"));
                 listPX.add(px);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPX;
    }

    public void them(PhieuXuatDTO px) {
        MySQLConnect mysql = new MySQLConnect();
        String sql = "INSERT INTO phieuxuat VALUES(";
        sql += "'" + px.getMaPX() + "',";
        sql += "'" + px.getMaNV() + "',";
        sql += "'" + px.getNgayXuat() + "',";
        sql += "'" + px.getGhiChu() + "',";
        sql += "'" + px.getTongTien() + "')";
        System.out.println("SQL Insert: " + sql);
        mysql.executeUpdate(sql);
    }

    public void sua(PhieuXuatDTO px, String maPX) {
        MySQLConnect mysql = new MySQLConnect();
        String sql = "UPDATE phieuxuat SET ";
        sql += "MaPX= '" + px.getMaPX() + "',";
        sql += "MaNV= '" + px.getMaNV() + "',";
        sql += "NgayXuat= '" + px.getNgayXuat() + "',";
        sql += "GhiChu= '" + px.getGhiChu() + "',";
        sql += "TongTien= " + px.getTongTien();
        sql += " WHERE MaPX='" + maPX + "'";
        System.out.println("SQL UPDATE: " + sql);
        mysql.executeUpdate(sql);
    }

    public void xoa(String maPX) {
        MySQLConnect mysql = new MySQLConnect();
        String sql = "DELETE FROM phieuxuat WHERE MaPX ='" + maPX + "'";
        System.out.println("SQL DELETE: " + sql);
        mysql.executeUpdate(sql);
    }
}