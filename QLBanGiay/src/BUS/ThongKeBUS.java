package BUS;

import DAO.ThongKeDAO;
import DTO.ThongKeDoanhThuDTO;
import java.sql.Date;
import java.util.ArrayList;

public class ThongKeBUS {
    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeDoanhThuTuNgayDenNgay(Date tuNgay, Date denNgay) {
        return thongKeDAO.thongKeDoanhThuTuNgayDenNgay(tuNgay, denNgay);
    }

    public ArrayList<ThongKeDoanhThuDTO> thongKeDoanhThuTheoThang(int nam) {
        return thongKeDAO.thongKeDoanhThuTheoThang(nam);
    }
}