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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    private JTextField txtTuNgay, txtDenNgay;
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

        txtTuNgay = new JTextField();
        txtTuNgay.setBounds(100, 15, 120, 30);
        pFilter.add(txtTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setBounds(240, 15, 80, 30);
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblDenNgay);

        txtDenNgay = new JTextField();
        txtDenNgay.setBounds(320, 15, 120, 30);
        pFilter.add(txtDenNgay);

        // Lọc Theo năm
        JLabel lblTheoNam = new JLabel("Chọn năm:");
        lblTheoNam.setBounds(460, 15, 80, 30);
        lblTheoNam.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblTheoNam);

        String[] years = {"2025", "2024", "2023", "2022", "2021"};
        cboxNam = new JComboBox<>(years);
        cboxNam.setBounds(540, 15, 80, 30);
        pFilter.add(cboxNam);

        // Chọn loại thống kê
        JLabel lblLoaiThongKe = new JLabel("Loại thống kê:");
        lblLoaiThongKe.setBounds(640, 15, 100, 30);
        lblLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(lblLoaiThongKe);

        String[] loaiThongKe = {"Theo ngày", "Theo tháng", "Theo khách hàng", "Theo nhân viên", "Theo sản phẩm"};
        cboxLoaiThongKe = new JComboBox<>(loaiThongKe);
        cboxLoaiThongKe.setSelectedIndex(1); // Mặc định chọn "Theo tháng" (index 1)
        cboxLoaiThongKe.setBounds(740, 15, 150, 30);
        pFilter.add(cboxLoaiThongKe);

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBounds(960, 15, 105, 30);
        btnFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFilter.setBackground(new Color(33, 150, 243)); // Màu xanh lá cây #1E7E34
        btnFilter.setForeground(Color.WHITE); // Chữ màu trắng
        pFilter.add(btnFilter);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setBounds(1068, 15, 105, 30);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 14));
        btnXuatExcel.setBackground(new Color(33, 150, 243)); // Màu xanh lá cây #1E7E34
        btnXuatExcel.setForeground(Color.WHITE); // Chữ màu trắng
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

        // Mặc định hiển thị thống kê theo tháng của năm đầu tiên
        String selectedYear = cboxNam.getItemAt(0).toString(); // Lấy năm đầu tiên (2025)
        int year = Integer.parseInt(selectedYear);
        loadThongKeTheoThang(year);
        updateChart(null, null, year, "Theo tháng");
    }

    private void filterStatistics() {
        String tuNgay = txtTuNgay.getText().trim();
        String denNgay = txtDenNgay.getText().trim();
        String selectedYear = cboxNam.getSelectedItem().toString();
        String loaiThongKe = cboxLoaiThongKe.getSelectedItem().toString();

        // Kiểm tra nếu cả hai trường ngày đều rỗng và loại thống kê yêu cầu ngày
        if ((tuNgay.isEmpty() && denNgay.isEmpty()) && loaiThongKe.equals("Theo ngày")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cả 'Từ ngày' và 'Đến ngày' để lọc theo ngày!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate và ghép năm từ cboxNam với định dạng MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        java.sql.Date startDate = null, endDate = null;

        try {
            if (!tuNgay.isEmpty()) {
                String fullTuNgay = selectedYear + "-" + tuNgay; // Ghép năm với MM-dd
                Date parsedDate = dateFormat.parse(fullTuNgay);
                startDate = new java.sql.Date(parsedDate.getTime());
            }
            if (!denNgay.isEmpty()) {
                String fullDenNgay = selectedYear + "-" + denNgay; // Ghép năm với MM-dd
                Date parsedDate = dateFormat.parse(fullDenNgay);
                endDate = new java.sql.Date(parsedDate.getTime());
            }
            if (startDate != null && endDate != null && startDate.after(endDate)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (MM-dd) hoặc dữ liệu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo biến tạm thời để sử dụng trong stream
        java.sql.Date finalStartDate = startDate;
        java.sql.Date finalEndDate = endDate;

        // Cập nhật tổng số hóa đơn, tổng doanh thu, số lượng sản phẩm
        List<HoaDonDTO> danhSachHoaDon = thongKeBUS.getDanhSachHoaDon();
        List<HoaDonDTO> filteredHoaDonList = danhSachHoaDon.stream()
                .filter(hd -> (finalStartDate == null || !hd.getNgayLap().before(finalStartDate)))
                .filter(hd -> (finalEndDate == null || !hd.getNgayLap().after(finalEndDate)))
                .collect(Collectors.toList());

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
        try {
            year = Integer.parseInt(selectedYear);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (loaiThongKe) {
            case "Theo ngày":
                if (startDate != null && endDate != null) {
                    loadThongKeTheoNgay(startDate, endDate);
                    updateChart(startDate, endDate, null, loaiThongKe);
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập cả ngày bắt đầu và ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Theo tháng":
                loadThongKeTheoThang(year);
                updateChart(null, null, year, loaiThongKe);
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
    }

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
        String[] labels = null;

        if (loaiThongKe.equals("Theo ngày") && startDate != null && endDate != null) {
            ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTuNgayDenNgay(startDate, endDate);
            revenue = new double[thongKeList.size()];
            labels = new String[thongKeList.size()];
            for (int i = 0; i < thongKeList.size(); i++) {
                ThongKeDoanhThuDTO tk = thongKeList.get(i);
                revenue[i] = tk.getDoanhThu();
                labels[i] = new SimpleDateFormat("dd/MM").format(tk.getNgay());
            }
        } else if (loaiThongKe.equals("Theo tháng") && nam != null) {
            ArrayList<ThongKeDoanhThuDTO> thongKeList = thongKeDAO.thongKeDoanhThuTheoThang(nam);
            revenue = new double[thongKeList.size()];
            labels = new String[thongKeList.size()];
            for (int i = 0; i < thongKeList.size(); i++) {
                ThongKeDoanhThuDTO tk = thongKeList.get(i);
                revenue[i] = tk.getDoanhThu();
                labels[i] = "Tháng " + tk.getThang();
            }
        } else if (loaiThongKe.equals("Theo khách hàng")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoKhachHang();
            revenue = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
                labels[index] = entry.getKey();
                index++;
            }
        } else if (loaiThongKe.equals("Theo nhân viên")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoNhanVien();
            revenue = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
                labels[index] = entry.getKey();
                index++;
            }
        } else if (loaiThongKe.equals("Theo sản phẩm")) {
            Map<String, Double> thongKe = thongKeBUS.thongKeTheoSanPham();
            revenue = new double[thongKe.size()];
            labels = new String[thongKe.size()];
            int index = 0;
            for (Map.Entry<String, Double> entry : thongKe.entrySet()) {
                revenue[index] = entry.getValue();
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
        CustomChartPanel chartPanel = new CustomChartPanel(revenue, labels);
        chartPanel.setBounds(0, 0, 1188, 200);
        pChart.add(chartPanel);
        pChart.revalidate();
        pChart.repaint();
    }

    // Lớp CustomChartPanel để vẽ biểu đồ cột
    class CustomChartPanel extends JPanel {
        private double[] values;
        private String[] labels;
        private DecimalFormat df = new DecimalFormat("#,###");

        public CustomChartPanel(double[] values, String[] labels) {
            this.values = values;
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
            int barWidth = 40;

            // Tìm giá trị lớn nhất để tính tỷ lệ chiều cao
            double maxValue = 0;
            for (double value : values) {
                maxValue = Math.max(maxValue, value);
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

            // Tính khoảng cách giữa các cột
            int totalBars = values.length;
            int barSpacing = totalBars > 0 ? (width - 2 * padding - totalBars * barWidth) / (totalBars + 1) : 0;
            if (barSpacing < 10) barSpacing = 10; // Đảm bảo có khoảng cách tối thiểu

            // Vẽ các cột
            for (int i = 0; i < values.length; i++) {
                int x = padding + (i + 1) * barSpacing + i * barWidth;
                double barHeight = (values[i] / maxValue) * (height - 2 * padding - labelPadding);
                int y = (int) (height - padding - barHeight);

                // Vẽ cột với màu gradient
                GradientPaint gradient = new GradientPaint(x, y, new Color(33, 150, 243), x, (int) (height - padding), new Color(100, 181, 246));
                g2.setPaint(gradient);
                g2.fillRoundRect(x, y, barWidth, (int) barHeight, 10, 10);

                // Vẽ viền cột
                g2.setColor(new Color(0, 120, 215));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, barWidth, (int) barHeight, 10, 10);

                // Vẽ giá trị trên cột
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 12));
                String valueStr = df.format(values[i]);
                int strWidth = g2.getFontMetrics().stringWidth(valueStr);
                g2.drawString(valueStr, x + (barWidth - strWidth) / 2, y - 5);

                // Vẽ nhãn dưới cột
                String label = labels[i];
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                g2.drawString(label, x + (barWidth - labelWidth) / 2, height - padding + labelPadding);
            }

            // Vẽ nhãn trục X
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Thời gian/Mã", width / 2 - 50, height - 10);

            g2.dispose();
        }
    }
    
    public void refreshThongKe() {
        loadDefaultStatistics();
    }

}