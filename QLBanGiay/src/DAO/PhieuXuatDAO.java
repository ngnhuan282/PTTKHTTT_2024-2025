package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PhieuXuatDTO;

public class PhieuXuatDAO {

    public ArrayList<PhieuXuatDTO> xuatDSPhieuXuat() {
        ArrayList<PhieuXuatDTO> listPX = new ArrayList<>();
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "SELECT * FROM phieuxuat";
            ResultSet rs = mysql.executeQuery(sql);
            while (rs.next()) {
                PhieuXuatDTO px = new PhieuXuatDTO();
                px.setMaPX(rs.getString("MaPX"));
                px.setNgayXuat(rs.getString("NgayXuat"));
                px.setGhiChu(rs.getString("GhiChu"));
                listPX.add(px);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPX;
    }

    public void them(PhieuXuatDTO px) {
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "INSERT INTO phieuxuat VALUES(";
            sql += "'" + px.getMaPX() + "',";
            sql += "'" + px.getNgayXuat() + "',";
            sql += "'" + px.getGhiChu() + "')";
            System.out.println("SQL INSERT: " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sua(PhieuXuatDTO px, String maPX) {
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "UPDATE phieuxuat SET ";
            sql += "MaPX= '" + px.getMaPX() + "', ";
            sql += "NgayXuat= '" + px.getNgayXuat() + "', ";
            sql += "GhiChu= '" + px.getGhiChu() + "'";
            sql += " WHERE MaPX='" + maPX + "'";
            System.out.println("SQL UPDATE: " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoa(String maPX) {
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "DELETE FROM phieuxuat WHERE MaPX='" + maPX + "'";
            System.out.println("SQL deleting : " + sql);
            mysql.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}