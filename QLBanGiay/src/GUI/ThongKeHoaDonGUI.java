package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import DAO.ThongKeDAO;

public class ThongKeHoaDonGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private ThongKeDAO thongKeDAO;
    private JLabel lblTongSoHoaDon, lblTongGiaTri, lblSoLuongSanPham;
    private JTextField txtTuNgay, txtDenNgay;
    private JComboBox<String> cboxNam, cboxLoaiThongKe;
    private DefaultTableModel tableModel;
    private JTable table;

    public ThongKeHoaDonGUI() 
    {
        initComponents();
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

        lblTongGiaTri = new JLabel("Tổng giá trị: 0đ");
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

        String[] loaiThongKe = {"Theo tháng", "Theo khách hàng", "Theo nhân viên", "Theo sản phẩm"};
        cboxLoaiThongKe = new JComboBox<>(loaiThongKe);
        cboxLoaiThongKe.setBounds(740, 15, 150, 30);
        pFilter.add(cboxLoaiThongKe);

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBounds(960, 15, 100, 30);
        btnFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(btnFilter);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setBounds(1068, 15, 100, 30);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 14));
        pFilter.add(btnXuatExcel);

        // Placeholder cho biểu đồ cột
        JPanel pChart = new JPanel();
        pChart.setBounds(20, 250, 1188, 200);
        pChart.setBackground(Color.WHITE);
        pChart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pChart.setLayout(null);
        add(pChart);

        JLabel lblChartPlaceholder = new JLabel("Biểu đồ cột (Thống kê hóa đơn theo tháng)", SwingConstants.CENTER);
        lblChartPlaceholder.setBounds(0, 0, 1188, 200);
        lblChartPlaceholder.setFont(new Font("Arial", Font.PLAIN, 14));
        pChart.add(lblChartPlaceholder);

        // Bảng dữ liệu chi tiết
        String[] columnNames = {"Tháng/Mã", "Số lượng hóa đơn/Tên", "Tổng giá trị", "Số lượng sản phẩm"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 460, 1188, 200);
        add(scrollPane);

     

    }
}