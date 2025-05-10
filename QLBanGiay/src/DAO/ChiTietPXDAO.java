package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietPXDTO;

public class ChiTietPXDAO {

    public ArrayList<ChiTietPXDTO> xuatDSCTPX() {
        ArrayList<ChiTietPXDTO> listCTPX = new ArrayList<>();
        try {
            MySQLConnect mysql = new MySQLConnect();
            String sql = "SELECT * FROM ctpx";
            ResultSet rs = mysql.executeQuery(sql);
            while (rs.next()) {
                ChiTietPXDTO ctpx = new ChiTietPXDTO();
                ctpx.setMaPX(rs.getString("MaPX"));
                ctpx.setMaSP(rs.getString("MaSP"));
                ctpx.setSoLuong(rs.getInt("SoLuong"));
                ctpx.setDonGia(rs.getDouble("DonGia"));
                ctpx.setThanhTien(rs.getDouble("ThanhTien"));
                listCTPX.add(ctpx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCTPX;
    }

    public void them(ChiTietPXDTO ctpx) {
        MySQLConnect mysql = new MySQLConnect();
        String sql = "INSERT INTO ctpx VALUES(";
        sql += "'" + ctpx.getMaPX() + "',";
        sql += "'" + ctpx.getMaSP() + "',";
        sql += "'" + ctpx.getSoLuong() + "',";
        sql += "'" + ctpx.getDonGia() + "',";
        sql += "'" + ctpx.getThanhTien() + "')";
        System.out.println("SQL Insert: " + sql);
        mysql.executeUpdate(sql);
    }

    public void xoa(String maPX) {
        MySQLConnect mysql = new MySQLConnect();
        String sql = "DELETE FROM ctpx WHERE MaPX ='" + maPX + "'";
        System.out.println("SQL DELETE: " + sql);
        mysql.executeUpdate(sql);
    }
}