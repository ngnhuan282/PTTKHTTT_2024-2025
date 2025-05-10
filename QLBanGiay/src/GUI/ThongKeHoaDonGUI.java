package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;

import BUS.ThongKeHoaDonBUS;
import DAO.ThongKeDAO;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;
import DTO.ThongKeDoanhThuDTO;

public class ThongKeHoaDonGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private ThongKeDAO thongKeDAO;
    private ThongKeHoaDonBUS thongKeBUS;
    private JLabel lblTongSoHoaDon, lblTongGiaTri, lblSoLuongSanPham;
    private JDateChooser dateChooserTuNgay, dateChooserDenNgay; // Replaced JTextField with JDateChooser
    private JComboBox<String> cboxNam, cboxLoaiThongKe;
    private DefaultTableModel tableModel;
    private JTable table;
    private DecimalFormat formatter = new DecimalFormat("#,###");
    private JPanel pChart;

    public ThongKeHoaDonGUI() {
        thongKeDAO = new ThongKeDAO();
        thongKeBUS = new ThongKeHoaDonBUS();
        initComponents();
        loadDefaultStatistics();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(null);

        // Header: THỐNG KÊ HÓA ĐƠN
        JLabel lblHeader = new JLabel("THỐNG KÊ HÓA ĐƠN", SwingConstants.CENTER);
        lblHeader.setBounds(0, 10, 1228, 50);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 30));
        lblHeader.setForeground(new Color(255, 82, 82));
        add(lblHeader);

        // Panel hiển thị Tổng số hóa đơn, Tổng giá trị, Số lượng sản phẩm
        JPanel pSummary = new JPanel();
        pSummary.setBounds(20, 70, 1188, 100);
        pSummary.setBackground(Color.WHITE);
        pSummary.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pSummary.setLayout(null);
        add(pSummary);

        lblTongSoHoaDon = new JLabel("Tổng số hóa đơn: 0");
        lblTongSoHoaDon.setBounds(20, 30, 300, 40);
        lblTongSoHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
        pSummary.add(lblTongSoHoaDon);

        lblTongGiaTri = new JLabel("Tổng doanh thu: 0đ");
        lblTongGiaTri.setBounds(420, 30, 300, 40);
        lblTongGiaTri.setFont(new Font("Arial", Font.PLAIN, 16));
        pSummary.add(lblTongGiaTri);

        lblSoLuongSanPham = new JLabel("Số lượng sản phẩm: 0");
        lblSoLuongSanPham.setBounds(820, 30, 300, 40);
        lblSoLuongSanPham.setFont(new Font("Arial", Font.PLAIN, 16));
        pSummary.add(lblSoLuongSanPham);

        // Panel lọc
        JPanel pFilter = new JPanel();
        pFilter.setBounds(20, 180, 1188, 60);
        pFilter.setBackground(Color.WHITE);
        pFilter.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pFilter.setLayout(null);
        add(pFilter);

        // Lọc Từ ngày → Đến ngày
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setBounds(20, 15, 80, 30);
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblTuNgay);

        dateChooserTuNgay = new JDateChooser();
        dateChooserTuNgay.setBounds(100, 15, 120, 30);
        dateChooserTuNgay.setDateFormatString("yyyy-MM-dd");
        pFilter.add(dateChooserTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setBounds(240, 15, 80, 30);
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblDenNgay);

        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setBounds(320, 15, 120, 30);
        dateChooserDenNgay.setDateFormatString("yyyy-MM-dd");
        pFilter.add(dateChooserDenNgay);

        // Lọc Theo năm
        JLabel lblTheoNam = new JLabel("Chọn năm:");
        lblTheoNam.setBounds(460, 15, 80, 30);
        lblTheoNam.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblTheoNam);

        String[] years = {"Chọn năm", "2025", "2024", "2023", "2022", "2021"}; // Added "Chọn năm" as default
        cboxNam = new JComboBox<>(years);
        cboxNam.setBounds(540, 15, 100, 30); // Slightly wider to accommodate text
        cboxNam.setSelectedIndex(0); // Default to "Chọn năm"
        pFilter.add(cboxNam);

        // Chọn loại thống kê
        JLabel lblLoaiThongKe = new JLabel("Loại thống kê:");
        lblLoaiThongKe.setBounds(660, 15, 100, 30);
        lblLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblLoaiThongKe);

        String[] loaiThongKe = {"Theo ngày", "Theo tháng", "Theo khách hàng", "Theo nhân viên", "Theo sản phẩm"};
        cboxLoaiThongKe = new JComboBox<>(loaiThongKe);
        cboxLoaiThongKe.setSelectedIndex(1); // Mặc định chọn "Theo tháng"
        cboxLoaiThongKe.setBounds(760, 15, 150, 30);
        pFilter.add(cboxLoaiThongKe);

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBounds(960, 15, 105, 30);
        btnFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFilter.setBackground(new Color(33, 150, 243));
        btnFilter.setForeground(Color.WHITE);
        pFilter.add(btnFilter);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setBounds(1068, 15, 105, 30);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 14));
        btnXuatExcel.setBackground(new Color(33, 150, 243));
        btnXuatExcel.setForeground(Color.WHITE);
        pFilter.add(btnXuatExcel);

        // Panel cho biểu đồ cột
        pChart = new JPanel();
        pChart.setBounds(20, 250, 1188, 200);
        pChart.setBackground(Color.WHITE);
        pChart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pChart.setLayout(null);
        add(pChart);

        // Bảng dữ liệu chi tiết
        String[] columnNames = {"Ngày/Tháng/Mã", "Số lượng hóa đơn/Tên", "Doanh thu", "Chi phí", "Lợi nhuận"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 460, 1188, 200);
        add(scrollPane);

        // Sự kiện nút Lọc
        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterStatistics();
            }
        });

        // Sự kiện nút Xuất Excel
        btnXuatExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
    }

    private void loadDefaultStatistics() {
        // Cập nhật tổng số hóa đơn, tổng doanh thu, số lượng sản phẩm
        int tongSoHoaDon = thongKeBUS.tinhTongSoHoaDon();
        double tongDoanhThu = thongKeBUS.tinhTongDoanhThu();
        int soLuongSanPham = thongKeBUS.tinhTongSoLuongSanPhamBanRa();

        lblTongSoHoaDon.setText("Tổng số hóa đơn: " + tongSoHoaDon);
        lblTongGiaTri.setText("Tổng doanh thu: " + formatter.format(tongDoanhThu) + "đ");
        lblSoLuongSanPham.setText("Số lượng sản phẩm: " + soLuongSanPham);

        // Mặc định hiển thị thống kê theo tháng của năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        cboxNam.setSelectedItem(String.valueOf(currentYear)); // Set to current year
        loadThongKeTheoThang(currentYear);
        updateChart(null, null, currentYear, "Theo tháng");
    }
    
    private void filterStatistics() {
        Date tuNgay = dateChooserTuNgay.getDate();
        Date denNgay = dateChooserDenNgay.getDate();
        String selectedYear = cboxNam.getSelectedItem().toString();
        String loaiThongKe = cboxLoaiThongKe.getSelectedItem().toString();

        // Validate year selection
        if (selectedYear.equals("Chọn năm") && !loaiThongKe.equals("Theo ngày")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm để lọc!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate dates for "Theo ngày"
        java.sql.Date startDate = null, endDate = null;
        if (loaiThongKe.equals("Theo ngày")) {
            if (tuNgay == null || denNgay == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn cả 'Từ ngày' và 'Đến ngày' để lọc theo ngày!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (tuNgay.after(denNgay)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            startDate = new java.sql.Date(tuNgay.getTime());
            endDate = new java.sql.Date(denNgay.getTime());
        }

        // Filter danhSachHoaDon
        List<HoaDonDTO> danhSachHoaDon = thongKeBUS.getDanhSachHoaDon();
        final List<HoaDonDTO> filteredHoaDonList; // Declare as final to ensure it's effectively final

        if (loaiThongKe.equals("Theo ngày")) {
            java.sql.Date finalStartDate = startDate;
            java.sql.Date finalEndDate = endDate;
            filteredHoaDonList = danhSachHoaDon.stream()
                    .filter(hd -> !hd.getNgayLap().before(finalStartDate) && !hd.getNgayLap().after(finalEndDate))
                    .collect(Collectors.toList());
        } else if (!selectedYear.equals("Chọn năm")) {
            try {
                int year = Integer.parseInt(selectedYear);
                filteredHoaDonList = danhSachHoaDon.stream()
                        .filter(hd -> hd.getNgayLap().toLocalDate().getYear() == year)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // For cases where no year filtering is needed (e.g., Theo khách hàng, nhân viên, sản phẩm)
            filteredHoaDonList = new ArrayList<>(danhSachHoaDon);
        }

        // Calculate statistics
        int tongSoHoaDon = filteredHoaDonList.size();
        double tongDoanhThu = filteredHoaDonList.stream().mapToDouble(HoaDonDTO::getTongTien).sum();
        int soLuongSanPham = thongKeBUS.getDanhSachChiTietHoaDon().stream()
                .filter(ct -> filteredHoaDonList.stream().anyMatch(hd -> hd.getMaHD().equals(ct.getMaHD())))
                .mapToInt(ChiTietHDDTO::getSoLuong)
                .sum();

        lblTongSoHoaDon.setText("Tổng số hóa đơn: " + tongSoHoaDon);
        lblTongGiaTri.setText("Tổng doanh thu: " + formatter.format(tongDoanhThu) + "đ");
        lblSoLuongSanPham.setText("Số lượng sản phẩm: " + soLuongSanPham);

        // Cập nhật bảng theo loại thống kê
        tableModel.setRowCount(0);
        int year = 0;
        if (!selectedYear.equals("Chọn năm")) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        switch (loaiThongKe) {
            case "Theo ngày":
                if (startDate != null && endDate != null) {
                    loadThongKeTheoNgay(startDate, endDate);
                    updateChart(startDate, endDate, null, loaiThongKe);
                }
                break;
            case "Theo tháng":
                if (!selectedYear.equals("Chọn năm")) {
                    loadThongKeTheoThang(year);
                    updateChart(null, null, year, loaiThongKe);
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn năm để lọc theo tháng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Theo khách hàng":
                loadThongKeTheoKhachHang();
                updateChart(null, null, null, loaiThongKe);
                break;
            case "Theo nhân viên":
                loadThongKeTheoNhanVien();
                updateChart(null, null, null, loaiThongKe);
                break;
            case "Theo sản phẩm":
                loadThongKeTheoSanPham();
                updateChart(null, null, null, loaiThongKe);
                break;
        }
        dateChooserTuNgay.setDate(null);
        dateChooserDenNgay.setDate(null);
    }

//    private void filterStatistics() {
//        Date tuNgay = dateChooserTuNgay.getDate();
//        Date denNgay = dateChooserDenNgay.getDate();
//        String selectedYear = cboxNam.getSelectedItem().toString();
//        String loaiThongKe = cboxLoaiThongKe.getSelectedItem().toString();
//
//        // Validate year selection
//        if (selectedYear.equals("Chọn năm") && !loaiThongKe.equals("Theo ngày")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm để lọc!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // Validate dates for "Theo ngày"
//        java.sql.Date startDate = null, endDate = null;
//        if (loaiThongKe.equals("Theo ngày")) {
//            if (tuNgay == null || denNgay == null) {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn cả 'Từ ngày' và 'Đến ngày' để lọc theo ngày!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//            if (tuNgay.after(denNgay)) {
//                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            startDate = new java.sql.Date(tuNgay.getTime());
//            endDate = new java.sql.Date(denNgay.getTime());
//        }
//
//        // Cập nhật tổng số hóa đơn, tổng doanh thu, số lượng sản phẩm
//        List<HoaDonDTO> danhSachHoaDon = thongKeBUS.getDanhSachHoaDon();
//        List<HoaDonDTO> filteredHoaDonList = danhSachHoaDon;
//        
//        if (loaiThongKe.equals("Theo ngày")) {
//            filteredHoaDonList = danhSachHoaDon.stream()
//                    .filter(hd -> !hd.getNgayLap().before(startDate) && !hd.getNgayLap().after(endDate))
//                    .collect(Collectors.toList());
//        } else if (!selectedYear.equals("Chọn năm")) {
//            try {
//                int year = Integer.parseInt(selectedYear);
//                filteredHoaDonList = danhSachHoaDon.stream()
//                        .filter(hd -> hd.getNgayLap().toLocalDate().getYear() == year)
//                        .collect(Collectors.toList());
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//
//        int tongSoHoaDon = filteredHoaDonList.size();
//        double tongDoanhThu = filteredHoaDonList.stream().mapToDouble(HoaDonDTO::getTongTien).sum();
//        int soLuongSanPham = thongKeBUS.getDanhSachChiTietHoaDon().stream()
//                .filter(ct -> filteredHoaDonList.stream().anyMatch(hd -> hd.getMaHD().equals(ct.getMaHD())))
//                .mapToInt(ChiTietHDDTO::getSoLuong)
//                .sum();
//
//        lblTongSoHoaDon.setText("Tổng số hóa đơn: " + tongSoHoaDon);
//        lblTongGiaTri.setText("Tổng doanh thu: " + formatter.format(tongDoanhThu) + "đ");
//        lblSoLuongSanPham.setText("Số lượng sản phẩm: " + soLuongSanPham);
//
//        // Cập nhật bảng theo loại thống kê
//        tableModel.setRowCount(0);
//        int year = 0;
//        if (!selectedYear.equals("Chọn năm")) {
//            try {
//                year = Integer.parseInt(selectedYear);
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//
//        switch (loaiThongKe) {
//            case "Theo ngày":
//                if (startDate != null && endDate != null) {
//                    loadThongKeTheoNgay(startDate, endDate);
//                    updateChart(startDate, endDate, null, loaiThongKe);
//                }
//                break;
//            case "Theo tháng":
//                if (!selectedYear.equals("Chọn năm")) {
//                    loadThongKeTheoThang(year);
//                    updateChart(null, null, year, loaiThongKe);
//                } else {
//                    JOptionPane.showMessageDialog(this, "Vui lòng chọn năm để lọc theo tháng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//                break;
//            case "Theo khách hàng":
//                loadThongKeTheoKhachHang();
//                updateChart(null, null, null, loaiThongKe);
//                break;
//            case "Theo nhân viên":
//                loadThongKeTheoNhanVien();
//                updateChart(null, null, null, loaiThongKe);
//                break;
//            case "Theo sản phẩm":
//                loadThongKeTheoSanPham();
//                updateChart(null, null, null, loaiThongKe);
//                break;
//        }
//    }

    private void loadThongKeTheoNgay(java.sql.Date tuNgay, java.sql.Date denNgay) {
        ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTuNgayDenNgay(tuNgay, denNgay);
        for (ThongKeDoanhThuDTO tk : thongKeList) {
            int soHoaDon = thongKeBUS.getDanhSachHoaDon().stream()
                    .filter(hd -> hd.getNgayLap().toLocalDate().equals(tk.getNgay().toLocalDate()))
                    .collect(Collectors.toList()).size();
            tableModel.addRow(new Object[]{
                    tk.getNgay(),
                    soHoaDon,
                    formatter.format(tk.getDoanhThu()),
                    formatter.format(tk.getChiPhi()),
                    formatter.format(tk.getLoiNhuan())
            });
        }
    }

    private void loadThongKeTheoThang(int nam) {
        ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTheoThang(nam);
        for (ThongKeDoanhThuDTO tk : thongKeList) {
            int soHoaDon = thongKeBUS.getDanhSachHoaDon().stream()
                    .filter(hd -> hd.getNgayLap().toLocalDate().getYear() == nam && hd.getNgayLap().toLocalDate().getMonthValue() == tk.getThang())
                    .collect(Collectors.toList()).size();
            tableModel.addRow(new Object[]{
                    "Tháng " + tk.getThang(),
                    soHoaDon,
                    formatter.format(tk.getDoanhThu()),
                    formatter.format(tk.getChiPhi()),
                    formatter.format(tk.getLoiNhuan())
            });
        }
    }

    private void loadThongKeTheoKhachHang() {
        Map<String, Double> thongKe = thongKeBUS.thongKeTheoKhachHang();
        for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
            String maKH = entry.getKey();
            double doanhThu = entry.getValue();
            int soHoaDon = thongKeBUS.getDanhSachHoaDon().stream()
                    .filter(hd -> hd.getMaKH().equals(maKH))
                    .collect(Collectors.toList()).size();
            tableModel.addRow(new Object[]{
                    maKH,
                    soHoaDon,
                    formatter.format(doanhThu),
                    "", // Không có chi phí
                    ""  // Không có lợi nhuận
            });
        }
    }

    private void loadThongKeTheoNhanVien() {
        Map<String, Double> thongKe = thongKeBUS.thongKeTheoNhanVien();
        for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
            String maNV = entry.getKey();
            double doanhThu = entry.getValue();
            int soHoaDon = thongKeBUS.getDanhSachHoaDon().stream()
                    .filter(hd -> hd.getMaNV().equals(maNV))
                    .collect(Collectors.toList()).size();
            tableModel.addRow(new Object[]{
                    maNV,
                    soHoaDon,
                    formatter.format(doanhThu),
                    "", // Không có chi phí
                    ""  // Không có lợi nhuận
            });
        }
    }

    private void loadThongKeTheoSanPham() {
        Map<String, Double> thongKe = thongKeBUS.thongKeTheoSanPham();
        for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
            String maSP = entry.getKey();
            double doanhThu = entry.getValue();
            int soLuongSanPham = thongKeBUS.getDanhSachChiTietHoaDon().stream()
                    .filter(ct -> ct.getMaSP().equals(maSP))
                    .mapToInt(ChiTietHDDTO::getSoLuong)
                    .sum();
            tableModel.addRow(new Object[]{
                    maSP,
                    soLuongSanPham,
                    formatter.format(doanhThu),
                    "", // Không có chi phí
                    ""  // Không có lợi nhuận
            });
        }
    }

    private void exportToExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");

            // Đặt tên mặc định
            fileChooser.setSelectedFile(new File("ThongKeHoaDon.xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("ThongKeHoaDon");

                // Header
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableModel.getColumnName(i));
                }

                // Dữ liệu
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        Cell cell = row.createCell(j);
                        Object value = tableModel.getValueAt(i, j);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }

                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }
                workbook.close();
                JOptionPane.showMessageDialog(this, "Xuất Excel thành công: " + filePath);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChart(java.sql.Date startDate, java.sql.Date endDate, Integer nam, String loaiThongKe) {
        double[] revenue = null;
        double[] cost = null;
        double[] profit = null;
        String[] labels = null;

        if (loaiThongKe.equals("Theo ngày") && startDate != null && endDate != null) {
            ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTuNgayDenNgay(startDate, endDate);
            revenue = new double[thongKeList.size()];
            cost = new double[thongKeList.size()];
            profit = new double[thongKeList.size()];
            labels = new String[thongKeList.size()];
            for (int i = 0; i < thongKeList.size(); i++) {
                ThongKeDoanhThuDTO tk = thongKeList.get(i);
                revenue[i] = tk.getDoanhThu();
                cost[i] = tk.getChiPhi();
                profit[i] = tk.getLoiNhuan();
                labels[i] = new SimpleDateFormat("dd/MM").format(tk.getNgay());
            }
        } else if (loaiThongKe.equals("Theo tháng") && nam != null) {
            ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTheoThang(nam);
            revenue = new double[thongKeList.size()];
            cost = new double[thongKeList.size()];
            profit = new double[thongKeList.size()];
            labels = new String[thongKeList.size()];
            for (int i = 0; i < thongKeList.size(); i++) {
                ThongKeDoanhThuDTO tk = thongKeList.get(i);
                revenue[i] = tk.getDoanhThu();
                cost[i] = tk.getChiPhi();
                profit[i] = tk.getLoiNhuan();
                labels[i] = "Tháng " + tk.getThang();
            }
        } else if (loaiThongKe.equals("Theo khách hàng")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoKhachHang();
            revenue = new double[thongKe.size()];
            cost = new double[thongKe.size()];
            profit = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
                cost[index] = 0; // Không có chi phí
                profit[index] = 0; // Không có lợi nhuận
                labels[index] = entry.getKey();
                index++;
            }
        } else if (loaiThongKe.equals("Theo nhân viên")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoNhanVien();
            revenue = new double[thongKe.size()];
            cost = new double[thongKe.size()];
            profit = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
                cost[index] = 0; // Không có chi phí
                profit[index] = 0; // Không có lợi nhuận
                labels[index] = entry.getKey();
                index++;
            }
        } else if (loaiThongKe.equals("Theo sản phẩm")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoSanPham();
            revenue = new double[thongKe.size()];
            cost = new double[thongKe.size()];
            profit = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
                cost[index] = 0; // Không có chi phí
                profit[index] = 0; // Không có lợi nhuận
                labels[index] = entry.getKey();
                index++;
            }
        } else {
            // Không có dữ liệu để vẽ biểu đồ, giữ nguyên panel trống
            pChart.removeAll();
            pChart.revalidate();
            pChart.repaint();
            return;
        }

        // Cập nhật biểu đồ
        pChart.removeAll();
        CustomChartPanel chartPanel = new CustomChartPanel(revenue, cost, profit, labels);
        chartPanel.setBounds(0, 0, 1188, 200);
        pChart.add(chartPanel);
        pChart.revalidate();
        pChart.repaint();
    }

    // Lớp CustomChartPanel để vẽ biểu đồJL cột
    class CustomChartPanel extends JPanel {
        private double[] revenue;
        private double[] cost;
        private double[] profit;
        private String[] labels;
        private DecimalFormat df = new DecimalFormat("#,###");

        public CustomChartPanel(double[] revenue, double[] cost, double[] profit, String[] labels) {
            this.revenue = revenue;
            this.cost = cost;
            this.profit = profit;
            this.labels = labels;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 50;
            int labelPadding = 20;
            int barWidth = 20; // Giảm chiều rộng cột để chứa 3 cột mỗi nhóm
            int barGap = 5; // Khoảng cách giữa các cột trong một nhóm

            // Tìm giá trị lớn nhất để tính tỷ lệ chiều cao
            double maxValue = 0;
            for (int i = 0; i < revenue.length; i++) {
                maxValue = Math.max(maxValue, revenue[i]);
                maxValue = Math.max(maxValue, cost[i]);
                maxValue = Math.max(maxValue, profit[i]);
            }
            if (maxValue == 0) maxValue = 1; // Tránh chia cho 0

            // Vẽ nền trắng
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            // Vẽ trục X
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(padding, height - padding, width - padding, height - padding); // Trục X

            // Vẽ các mức tiền bên trái và đường kẻ ngang liền
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            int numTicks = 5; // Số mức tiền hiển thị
            double tickValueStep = maxValue / (numTicks - 1);
            double tickHeightStep = (height - 2 * padding - labelPadding) / (numTicks - 1);
            for (int i = 0; i < numTicks; i++) {
                double tickValue = tickValueStep * i;
                int y = (int) (height - padding - tickHeightStep * i);
                String tickLabel;
                if (tickValue >= 1000000) {
                    tickLabel = (int)(tickValue / 1000000) + "M";
                } else if (tickValue >= 1000) {
                    tickLabel = (int)(tickValue / 1000) + "K";
                } else {
                    tickLabel = (int)tickValue + "đ";
                }
                g2.drawString(tickLabel, padding - 40, y + 5);

                // Vẽ đường kẻ ngang liền
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1)); // Nét liền
                g2.drawLine(padding, y, width - padding, y);
            }

            // Tính khoảng cách giữa các nhóm cột
            int totalGroups = labels.length;
            int groupWidth = barWidth * 3 + barGap * 2; // Chiều rộng của một nhóm (3 cột + 2 khoảng cách)
            int barSpacing = totalGroups > 0 ? (width - 2 * padding - totalGroups * groupWidth) / (totalGroups + 1) : 0;
            if (barSpacing < 10) barSpacing = 10; // Đảm bảo có khoảng cách tối thiểu

            // Vẽ các cột
            for (int i = 0; i < labels.length; i++) {
                int groupX = padding + (i + 1) * barSpacing + i * groupWidth;

                // Vẽ cột Doanh thu
                int xRevenue = groupX;
                double barHeightRevenue = (revenue[i] / maxValue) * (height - 2 * padding - labelPadding);
                int yRevenue = (int) (height - padding - barHeightRevenue);
                GradientPaint gradientRevenue = new GradientPaint(xRevenue, yRevenue, new Color(33, 150, 243), xRevenue, (int) (height - padding), new Color(100, 181, 246));
                g2.setPaint(gradientRevenue);
                g2.fillRoundRect(xRevenue, yRevenue, barWidth, (int) barHeightRevenue, 10, 10);
                g2.setColor(new Color(0, 120, 215));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(xRevenue, yRevenue, barWidth, (int) barHeightRevenue, 10, 10);
                if (revenue[i] > 0) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.PLAIN, 10));
                    String valueStr = df.format(revenue[i]);
                    int strWidth = g2.getFontMetrics().stringWidth(valueStr);
                    g2.drawString(valueStr, xRevenue + (barWidth - strWidth) / 2, yRevenue - 5);
                }

                // Vẽ cột Chi phí
                int xCost = groupX + barWidth + barGap;
                double barHeightCost = (cost[i] / maxValue) * (height - 2 * padding - labelPadding);
                int yCost = (int) (height - padding - barHeightCost);
                GradientPaint gradientCost = new GradientPaint(xCost, yCost, new Color(255, 87, 34), xCost, (int) (height - padding), new Color(255, 138, 101));
                g2.setPaint(gradientCost);
                g2.fillRoundRect(xCost, yCost, barWidth, (int) barHeightCost, 10, 10);
                g2.setColor(new Color(200, 60, 20));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(xCost, yCost, barWidth, (int) barHeightCost, 10, 10);
                if (cost[i] > 0) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.PLAIN, 10));
                    String valueStr = df.format(cost[i]);
                    int strWidth = g2.getFontMetrics().stringWidth(valueStr);
                    g2.drawString(valueStr, xCost + (barWidth - strWidth) / 2, yCost - 5);
                }

                // Vẽ cột Lợi nhuận
                int xProfit = groupX + (barWidth + barGap) * 2;
                double barHeightProfit = (profit[i] / maxValue) * (height - 2 * padding - labelPadding);
                int yProfit = (int) (height - padding - barHeightProfit);
                GradientPaint gradientProfit = new GradientPaint(xProfit, yProfit, new Color(76, 175, 80), xProfit, (int) (height - padding), new Color(129, 199, 132));
                g2.setPaint(gradientProfit);
                g2.fillRoundRect(xProfit, yProfit, barWidth, (int) barHeightProfit, 10, 10);
                g2.setColor(new Color(46, 125, 50));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(xProfit, yProfit, barWidth, (int) barHeightProfit, 10, 10);
                if (profit[i] > 0) {
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.PLAIN, 10));
                    String valueStr = df.format(profit[i]);
                    int strWidth = g2.getFontMetrics().stringWidth(valueStr);
                    g2.drawString(valueStr, xProfit + (barWidth - strWidth) / 2, yProfit - 5);
                }

                // Vẽ nhãn dưới nhóm cột
                String label = labels[i];
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                int labelX = groupX + (groupWidth - labelWidth) / 2;
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                g2.drawString(label, labelX, height - padding + labelPadding);
            }

            // Vẽ nhãn trục X
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Thời gian/Mã", width / 2 - 50, height - 10);

            // Vẽ chú thích (legend)
            int legendX = padding;
            int legendY = padding - 40;
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            // Doanh thu
            g2.setColor(new Color(33, 150, 243));
            g2.fillRect(legendX, legendY, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawRect(legendX, legendY, 15, 15);
            g2.drawString("Doanh thu", legendX + 20, legendY + 12);
            // Chi phí
            g2.setColor(new Color(255, 87, 34));
            g2.fillRect(legendX + 80, legendY, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawRect(legendX + 80, legendY, 15, 15);
            g2.drawString("Chi phí", legendX + 100, legendY + 12);
            // Lợi nhuận
            g2.setColor(new Color(76, 175, 80));
            g2.fillRect(legendX + 160, legendY, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawRect(legendX + 160, legendY, 15, 15);
            g2.drawString("Lợi nhuận", legendX + 180, legendY + 12);

            g2.dispose();
        }
    }
    
    public void refreshThongKe() {
        loadDefaultStatistics();
    }
}