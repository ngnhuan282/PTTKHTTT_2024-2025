package BUS;

import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

import DAO.ThongKeNhaCungCapDAO;
import DTO.ThongKeNhaCungCapDTO;

public class ThongKeNhaCungCapBUS {
    private ThongKeNhaCungCapDAO dao = new ThongKeNhaCungCapDAO();

    // Trả về toàn bộ thống kê (không lọc)
    public ArrayList<ThongKeNhaCungCapDTO> getToanBoThongKe() {
        return dao.getToanBoThongKe();
    }

    // Trả về thống kê trong khoảng thời gian
    public ArrayList<ThongKeNhaCungCapDTO> thongKeTheoNgay(Date from, Date to) {
        return dao.getThongKeTheoNgay(from, to);
    }

    // Lọc từ danh sách theo keyword
    public ArrayList<ThongKeNhaCungCapDTO> locTheoKeyword(ArrayList<ThongKeNhaCungCapDTO> list, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return list;

        String kw = keyword.trim().toLowerCase();
        return list.stream()
                .filter(ncc -> ncc.getMaNCC().toLowerCase().contains(kw) || 
                               ncc.getTenNCC().toLowerCase().contains(kw))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
