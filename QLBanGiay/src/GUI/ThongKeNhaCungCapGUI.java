package GUI;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.toedter.calendar.JDateChooser;
import BUS.ThongKeNhaCungCapBUS;
import DTO.ThongKeNhaCungCapDTO;

public class ThongKeNhaCungCapGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtSearch;
    private DefaultTableModel tableModel;
    private JTable table;
    private DecimalFormat formatter = new DecimalFormat("#,###");
    private ThongKeNhaCungCapBUS thongKeBUS = new ThongKeNhaCungCapBUS();

    public ThongKeNhaCungCapGUI() {
        initComponents();
        // Load dữ liệu toàn bộ khi mở giao diện
        ArrayList<ThongKeNhaCungCapDTO> list = thongKeBUS.getToanBoThongKe();
        loadTableThongKe(list);
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(null);

        JPanel pLeft = new JPanel(null);
        pLeft.setBounds(20, 20, 260, 660);
        pLeft.setBackground(Color.decode("#F8F9FA"));
        pLeft.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(pLeft);

        JLabel lblSearch = new JLabel("Tìm kiếm nhà cung cấp");
        lblSearch.setBounds(10, 10, 240, 25);
        pLeft.add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(10, 40, 240, 30);
        pLeft.add(txtSearch);

        JLabel lblTuNgay = new JLabel("Từ ngày");
        lblTuNgay.setBounds(10, 90, 100, 25);
        pLeft.add(lblTuNgay);

        JDateChooser dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("yyyy-MM-dd");
        dcTuNgay.setBounds(10, 120, 240, 30);
        pLeft.add(dcTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày");
        lblDenNgay.setBounds(10, 170, 100, 25);
        pLeft.add(lblDenNgay);

        JDateChooser dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("yyyy-MM-dd");
        dcDenNgay.setBounds(10, 200, 240, 30);
        pLeft.add(dcDenNgay);

        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setBounds(10, 260, 115, 35);
        btnExcel.setBackground(new Color(76, 175, 80));
        btnExcel.setForeground(Color.WHITE);
        pLeft.add(btnExcel);

        JButton btnReset = new JButton("Làm mới");
        btnReset.setBounds(135, 260, 115, 35);
        btnReset.setBackground(new Color(244, 67, 54));
        btnReset.setForeground(Color.WHITE);
        pLeft.add(btnReset);

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBounds(10, 310, 240, 35);
        btnFilter.setBackground(new Color(33, 150, 243));
        btnFilter.setForeground(Color.WHITE);
        pLeft.add(btnFilter);

        String[] columnNames = {"STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Số lượng nhập", "Tổng số tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 20, 900, 660);
        add(scrollPane);
     // Tìm kiếm theo từ khóa (không giới hạn ngày)
        txtSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            ArrayList<ThongKeNhaCungCapDTO> list = thongKeBUS.getToanBoThongKe();
            list = thongKeBUS.locTheoKeyword(list, keyword);  // Lọc lại theo từ khóa
            loadTableThongKe(list);
        });

        // Lọc theo ngày và keyword
        btnFilter.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            java.util.Date fromUtil = dcTuNgay.getDate();
            java.util.Date toUtil = dcDenNgay.getDate();

            ArrayList<ThongKeNhaCungCapDTO> list;

            // Nếu có khoảng ngày → lọc theo ngày
            if (fromUtil != null && toUtil != null) {
                if (fromUtil.after(toUtil)) {
                    JOptionPane.showMessageDialog(this, "Từ ngày không được sau Đến ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Date from = new Date(fromUtil.getTime());
                Date to = new Date(toUtil.getTime());
                list = thongKeBUS.thongKeTheoNgay(from, to);
            } else {
                list = thongKeBUS.getToanBoThongKe();
            }

            // Nếu có keyword → lọc theo từ khóa
            if (!keyword.isEmpty()) {
                list = thongKeBUS.locTheoKeyword(list, keyword);
            }

            // Kiểm tra danh sách kết quả
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

            loadTableThongKe(list);
        });





        btnReset.addActionListener(e -> {
            txtSearch.setText("");        // Xóa ô tìm kiếm
            dcTuNgay.setDate(null);       // Xóa ngày bắt đầu
            dcDenNgay.setDate(null);      // Xóa ngày kết thúc

            ArrayList<ThongKeNhaCungCapDTO> list = thongKeBUS.getToanBoThongKe();
            loadTableThongKe(list);
        });


        btnExcel.addActionListener(e -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Chọn nơi lưu file Excel");
                // Đặt sẵn tên mặc định cho file
                chooser.setSelectedFile(new File("ThongKeNCC.xlsx"));
                int result = chooser.showSaveDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    if (!file.getName().endsWith(".xlsx")) {
                        file = new File(file.getAbsolutePath() + ".xlsx");
                    }

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("ThongKeNCC");

                    Row header = sheet.createRow(0);
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        header.createCell(i).setCellValue(table.getColumnName(i));
                    }

                    for (int i = 0; i < table.getRowCount(); i++) {
                        Row row = sheet.createRow(i + 1);
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            row.createCell(j).setCellValue(table.getValueAt(i, j).toString());
                        }
                    }

                    FileOutputStream out = new FileOutputStream(file);
                    workbook.write(out);
                    out.close();
                    workbook.close();

                    JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất Excel: " + ex.getMessage());
            }
        });
    }

    private void loadTableThongKe(ArrayList<ThongKeNhaCungCapDTO> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        for (ThongKeNhaCungCapDTO ncc : list) {
            tableModel.addRow(new Object[]{
                stt++,
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getTongSoLuong(),
                formatter.format(ncc.getTongTien())
            });
        }
    }
}