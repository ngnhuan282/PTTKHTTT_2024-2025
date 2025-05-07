package BUS;

import DAO.ThongKeHoaDonDAO;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThongKeHoaDonBUS {
    private ThongKeHoaDonDAO thongKeDAO;

    public ThongKeHoaDonBUS() {
        thongKeDAO = new ThongKeHoaDonDAO();
    }

    // Lấy toàn bộ hóa đơn
    public List<HoaDonDTO> getDanhSachHoaDon() {
        return thongKeDAO.layDanhSachHoaDon();
    }

    // Lấy toàn bộ chi tiết hóa đơn
    public List<ChiTietHDDTO> getDanhSachChiTietHoaDon() {
        return thongKeDAO.layDanhSachChiTietHoaDon();
    }

    // Tổng doanh thu tất cả
    public double tinhTongDoanhThu() {
        return getDanhSachHoaDon().stream()
                .mapToDouble(HoaDonDTO::getTongTien)
                .sum();
    }

    // Tổng số hóa đơn
    public int tinhTongSoHoaDon() {
        return getDanhSachHoaDon().size();
    }

    // Tổng số sản phẩm đã bán
    public int tinhTongSoLuongSanPhamBanRa() {
        return getDanhSachChiTietHoaDon().stream()
                .mapToInt(ChiTietHDDTO::getSoLuong)
                .sum();
    }

    // Doanh thu theo tháng/năm
    public double tinhDoanhThuTheoThang(int thang, int nam) {
        return getDanhSachHoaDon().stream()
                .filter(hd -> {
                    LocalDate ngay = hd.getNgayLap().toLocalDate();
                    return ngay.getYear() == nam && ngay.getMonthValue() == thang;
                })
                .mapToDouble(HoaDonDTO::getTongTien)
                .sum();
    }

    // Doanh thu theo khách hàng (mã KH)
    public Map<String, Double> thongKeTheoKhachHang() {
        return getDanhSachHoaDon().stream()
                .collect(Collectors.groupingBy(
                        HoaDonDTO::getMaKH,
                        Collectors.summingDouble(HoaDonDTO::getTongTien)
                ));
    }

    // Doanh thu theo nhân viên (mã NV)
    public Map<String, Double> thongKeTheoNhanVien() {
        return getDanhSachHoaDon().stream()
                .collect(Collectors.groupingBy(
                        HoaDonDTO::getMaNV,
                        Collectors.summingDouble(HoaDonDTO::getTongTien)
                ));
    }

    // Doanh thu theo sản phẩm (mã SP)
    public Map<String, Double> thongKeTheoSanPham() {
        return getDanhSachChiTietHoaDon().stream()
                .collect(Collectors.groupingBy(
                        ChiTietHDDTO::getMaSP,
                        Collectors.summingDouble(ChiTietHDDTO::getThanhTien)
                ));
    }
}
