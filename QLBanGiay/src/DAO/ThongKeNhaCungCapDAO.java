package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import DTO.ThongKeNhaCungCapDTO;
import DTO.NhaCungCapDTO;

public class ThongKeNhaCungCapDAO {
    private MySQLConnect mysql = new MySQLConnect();

    public ArrayList<ThongKeNhaCungCapDTO> getThongKeTheoNgay(Date from, Date to, String keyword) {
        ArrayList<ThongKeNhaCungCapDTO> result = new ArrayList<>();
        try {
            keyword = keyword == null ? "" : keyword.trim().toLowerCase();

            String sql = "SELECT pn.MaNCC, ncc.TenNCC, " +
                    "SUM(ct.SoLuong) AS TongSL, SUM(ct.ThanhTien) AS TongTien " +
                    "FROM phieunhap pn " +
                    "JOIN chitietphieunhap ct ON pn.MaPhieuNH = ct.MaPhieuNH " +
                    "JOIN nhacungcap ncc ON pn.MaNCC = ncc.MaNCC " +
                    "WHERE pn.NgayLap BETWEEN '" + from + "' AND '" + to + "' " +
                    "AND (LOWER(ncc.MaNCC) LIKE '%" + keyword + "%' OR LOWER(ncc.TenNCC) LIKE '%" + keyword + "%') " +
                    "GROUP BY pn.MaNCC, ncc.TenNCC";

//          String sql = "SELECT pn.MaNCC, ncc.TenNCC, " +
//          "SUM(ct.SoLuong) AS TongSL, SUM(ct.ThanhTien) AS TongTien " +
//          "FROM phieunhap pn " +
//          "JOIN chitietphieunhap ct ON pn.MaPhieuNH = ct.MaPhieuNH " +
//          "JOIN nhacc ncc ON pn.MaNCC = ncc.MaNCC " +
//          "WHERE pn.NgayLap BETWEEN '" + from + "' AND '" + to + "' " +
//          "AND (LOWER(ncc.MaNCC) LIKE '%" + keyword + "%' OR LOWER(ncc.TenNCC) LIKE '%" + keyword + "%') " +
//          "GROUP BY pn.MaNCC, ncc.TenNCC";

            ResultSet rs = mysql.executeQuery(sql);

            while (rs.next()) {
                String maNCC = rs.getString("MaNCC");
                int sl = rs.getInt("TongSL");
                double tongTien = rs.getDouble("TongTien");

                // Gọi tên NCC từ câu lệnh SQL JOIN sẵn luôn
                String tenNCC = rs.getString("TenNCC");

                result.add(new ThongKeNhaCungCapDTO(maNCC, tenNCC, sl, tongTien));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ThongKeNhaCungCapDTO> getToanBoThongKe() {
        ArrayList<ThongKeNhaCungCapDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT pn.MaNCC, SUM(ct.SoLuong) AS TongSL, SUM(ct.ThanhTien) AS TongTien " +
                         "FROM phieunhap pn " +
                         "JOIN chitietphieunhap ct ON pn.MaPhieuNH = ct.MaPhieuNH " +
                         "GROUP BY pn.MaNCC";

            ResultSet rs = mysql.executeQuery(sql);

            NhaCungCapDAO nccDAO = new NhaCungCapDAO();
            ArrayList<NhaCungCapDTO> allNCC = nccDAO.xuatDSNCC();

            while (rs.next()) {
                String maNCC = rs.getString("MaNCC");
                int soLuong = rs.getInt("TongSL");
                double tongTien = rs.getDouble("TongTien");

                // Tìm tên NCC từ danh sách
                String tenNCC = allNCC.stream()
                    .filter(ncc -> ncc.getMaNCC().equals(maNCC))
                    .map(ncc -> ncc.getTenNCC())
                    .findFirst()
                    .orElse("(Không tìm thấy)");

                result.add(new ThongKeNhaCungCapDTO(maNCC, tenNCC, soLuong, tongTien));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
 // DAO
    public ArrayList<ThongKeNhaCungCapDTO> getThongKeTheoNgay(Date from, Date to) {
        return getThongKeTheoNgay(from, to, ""); // Gọi lại phương thức chính và truyền keyword rỗng
    }


} 