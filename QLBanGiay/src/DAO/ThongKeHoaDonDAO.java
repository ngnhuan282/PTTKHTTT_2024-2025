package DAO;

import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeHoaDonDAO {
    private MySQLConnect mysql;

    public ThongKeHoaDonDAO() {
        mysql = new MySQLConnect();
    }

    // Lấy danh sách tất cả hóa đơn từ cơ sở dữ liệu
    public List<HoaDonDTO> layDanhSachHoaDon() {
        List<HoaDonDTO> danhSachHoaDon = new ArrayList<>();
        String query = "SELECT maHD, maKH, maNV, ngayLap, tongTien FROM HoaDon";
        
        try {
            ResultSet rs = mysql.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    HoaDonDTO hoaDon = new HoaDonDTO();
                    hoaDon.setMaHD(rs.getString("maHD"));
                    hoaDon.setMaKH(rs.getString("maKH"));
                    hoaDon.setMaNV(rs.getString("maNV"));
                    hoaDon.setNgayLap(rs.getDate("ngayLap"));
                    hoaDon.setTongTien(rs.getDouble("tongTien"));
                    danhSachHoaDon.add(hoaDon);
                }
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeHoaDonDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách hóa đơn: " + query, e);
        } finally {
            mysql.disConnect();
        }
        
        return danhSachHoaDon;
    }

    // Lấy danh sách tất cả chi tiết hóa đơn từ cơ sở dữ liệu
    public List<ChiTietHDDTO> layDanhSachChiTietHoaDon() {
        List<ChiTietHDDTO> danhSachChiTiet = new ArrayList<>();
        String query = "SELECT maHD, maSP, soLuong, donGia, thanhTien FROM chitiethoadon"; // Sửa tên bảng thành chitiethoadon
        
        try {
            ResultSet rs = mysql.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    ChiTietHDDTO chiTiet = new ChiTietHDDTO();
                    chiTiet.setMaHD(rs.getString("maHD")); // Đổi kiểu về String để khớp với int(11)
                    chiTiet.setMaSP(rs.getString("maSP"));
                    chiTiet.setSoLuong(rs.getInt("soLuong"));
                    chiTiet.setDonGia(rs.getDouble("donGia"));
                    chiTiet.setThanhTien(rs.getDouble("thanhTien"));
                    danhSachChiTiet.add(chiTiet);
                }
                rs.close();
            } else {
                Logger.getLogger(ThongKeHoaDonDAO.class.getName()).log(Level.WARNING, "ResultSet null khi truy vấn: " + query);
            }
        } catch (SQLException e) {
            Logger.getLogger(ThongKeHoaDonDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách chi tiết hóa đơn: " + query, e);
        } finally {
            mysql.disConnect();
        }
        
        return danhSachChiTiet;
    }
}