package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ThongKeDoanhThuDTO;

public class ThongKeDAO {
    private MySQLConnect mysql = new MySQLConnect();

    public ThongKeDAO() {
      
    }

    // Thống kê từ ngày đến ngày
    public ArrayList<ThongKeDoanhThuDTO> thongKeDoanhThuTuNgayDenNgay(Date tuNgay, Date denNgay) {
        ArrayList<ThongKeDoanhThuDTO> listThongKe = new ArrayList<>();
        try {

            // Truy vấn tất cả các ngày trong khoảng từ ngày đến ngày
            String sql = "SELECT DATE_FORMAT(d.ngay, '%Y-%m-%d') AS ngay, " +
                         "COALESCE(SUM(pn.TongTien), 0) AS chiPhi, " +
                         "COALESCE(SUM(hd.TongTien), 0) AS doanhThu " +
                         "FROM (SELECT DATE_ADD('" + tuNgay + "', INTERVAL n DAY) AS ngay " +
                         "      FROM (SELECT a.N + b.N * 10 + c.N * 100 AS n " +
                         "            FROM (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a, " +
                         "                 (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b, " +
                         "                 (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) c) numbers " +
                         "      WHERE DATE_ADD('" + tuNgay + "', INTERVAL n DAY) <= '" + denNgay + "') d " +
                         "LEFT JOIN PHIEUNHAPHANG pn ON DATE(pn.NgayNhap) = d.ngay " +
                         "LEFT JOIN HOADON hd ON DATE(hd.NgayLap) = d.ngay " +
                         "GROUP BY d.ngay " +
                         "ORDER BY d.ngay";

            ResultSet rs = mysql.executeQuery(sql);
            while (rs.next()) {
                Date ngay = Date.valueOf(rs.getString("ngay"));
                double chiPhi = rs.getDouble("chiPhi");
                double doanhThu = rs.getDouble("doanhThu");
                double loiNhuan = doanhThu - chiPhi;
                ThongKeDoanhThuDTO thongKe = new ThongKeDoanhThuDTO(ngay, chiPhi, doanhThu, loiNhuan);
                listThongKe.add(thongKe);
            }
            mysql.disConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listThongKe;
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeDoanhThuTheoThang(int nam) {
        ArrayList<ThongKeDoanhThuDTO> listThongKe = new ArrayList<>();
        try {

            // Truy vấn tất cả các tháng trong năm
            String sql = "SELECT m.thang, " +
                         "COALESCE(SUM(pn.TongTien), 0) AS chiPhi, " +
                         "COALESCE(SUM(hd.TongTien), 0) AS doanhThu " +
                         "FROM (SELECT 1 AS thang UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 " +
                         "      UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) m " +
                         "LEFT JOIN PHIEUNHAPHANG pn ON MONTH(pn.NgayNhap) = m.thang AND YEAR(pn.NgayNhap) = " + nam + " " +
                         "LEFT JOIN HOADON hd ON MONTH(hd.NgayLap) = m.thang AND YEAR(hd.NgayLap) = " + nam + " " +
                         "GROUP BY m.thang " +
                         "ORDER BY m.thang";

            ResultSet rs = mysql.executeQuery(sql);
            while (rs.next()) {
                int thang = rs.getInt("thang");
                double chiPhi = rs.getDouble("chiPhi");
                double doanhThu = rs.getDouble("doanhThu");
                double loiNhuan = doanhThu - chiPhi;
                ThongKeDoanhThuDTO thongKe = new ThongKeDoanhThuDTO(thang, nam, chiPhi, doanhThu, loiNhuan);
                listThongKe.add(thongKe);
            }
            mysql.disConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listThongKe;
    }
}