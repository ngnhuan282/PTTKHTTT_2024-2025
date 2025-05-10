// File: ChiTietHoaDonDAO.java
package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ChiTietHDDTO;

public class ChiTietHoaDonDAO {
    private MySQLConnect connection;

    public ChiTietHoaDonDAO() {
        connection = new MySQLConnect();
    }

    public ArrayList<ChiTietHDDTO> getListCTHD() throws SQLException {
        ArrayList<ChiTietHDDTO> listCTHDDTO = new ArrayList<>();
        connection.getConnection();
        String sql = "SELECT * FROM chitiethoadon";
        ResultSet rs = connection.executeQuery(sql);
        while (rs.next()) {
            String maHD = rs.getString("MaHD");
            String maSP = rs.getString("MaSP");
            int soLuong = rs.getInt("SoLuong");
            double donGia = rs.getDouble("DonGia");
            double thanhTien = rs.getDouble("ThanhTien");
            String trangThai = rs.getString("TrangThai");
            String maHTTT = rs.getString("MaHTTT");
            ChiTietHDDTO dto = new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien, trangThai, maHTTT);
            listCTHDDTO.add(dto);
        }
        connection.disConnect();
        return listCTHDDTO;
    }

    public void addCTHD(ChiTietHDDTO x) {
        connection.getConnection();
        String sql = "INSERT INTO chitiethoadon(MaHD, MaSP, SoLuong, DonGia, ThanhTien, TrangThai, MaHTTT) " +
                     "VALUES('" + x.getMaHD() + "', '" + x.getMaSP() + "', " + x.getSoLuong() + ", " +
                     x.getDonGia() + ", " + x.getThanhTien() + ", '" + x.getTrangThai() + "', '" +
                     x.getMaTTT() + "')";
        connection.executeUpdate(sql);
        connection.disConnect();
    }

    public void updateCTHD(ChiTietHDDTO x) {
        connection.getConnection();
        String sql = "UPDATE chitiethoadon SET " +
                     "SoLuong = " + x.getSoLuong() + ", " +
                     "DonGia = " + x.getDonGia() + ", " +
                     "ThanhTien = " + x.getThanhTien() + ", " +
                     "TrangThai = '" + x.getTrangThai() + "', " +
                     "MaHTTT = '" + x.getMaTTT() + "' " +
                     "WHERE MaHD = '" + x.getMaHD() + "' AND MaSP = '" + x.getMaSP() + "'";
        connection.executeUpdate(sql);
        connection.disConnect();
    }

    public void deleteCTHD(String maHD) {
        connection.getConnection();
        String sql = "DELETE FROM chitiethoadon WHERE MaHD = '" + maHD + "' AND TrangThai = '0'";
        connection.executeUpdate(sql);
        connection.disConnect();
    }

    public void deleteCTHD(String maHD, String maSP) {
        connection.getConnection();
        String sql = "DELETE FROM chitiethoadon WHERE MaHD = '" + maHD + "' AND MaSP = '" + maSP + "' AND TrangThai = '0'";
        connection.executeUpdate(sql);
        connection.disConnect();
    }

    public void updateSoLuongSP(String maSP, int soLuong) {
        connection.getConnection();
        String sql = "UPDATE sanpham SET SoLuong = SoLuong - " + soLuong + " WHERE MaSP = '" + maSP + "'";
        connection.executeUpdate(sql);
        connection.disConnect();
    }

    public void tangSoLuongSP(String maSP, int soLuong) {
        connection.getConnection();
        String sql = "UPDATE sanpham SET SoLuong = SoLuong + " + soLuong + " WHERE MaSP = '" + maSP + "'";
        connection.executeUpdate(sql);
        connection.disConnect();
    }
    
    public boolean capNhatTrangThaiTheoMaHD(String maHD, String trangThaiMoi) {
        connection.getConnection();
        String sql = "UPDATE chitiethoadon SET TrangThai = '" + trangThaiMoi + "' WHERE MaHD = '" + maHD + "'";
        try {
            System.out.println("SQL đang chạy: " + sql);
            int affectedRows = connection.executeUpdateReturnRowCount(sql); // ✅ dùng hàm mới
            System.out.println("Số dòng bị ảnh hưởng: " + affectedRows);
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disConnect();
        }
        return false;
    }


    public void deleteByMaHD(String maHD) {
        connection.getConnection(); // mở kết nối
        String sql = "DELETE FROM chitiethoadon WHERE MaHD = '" + maHD + "'";
        connection.executeUpdate(sql); // thực thi lệnh
        connection.disConnect(); // đóng kết nối
    }



}