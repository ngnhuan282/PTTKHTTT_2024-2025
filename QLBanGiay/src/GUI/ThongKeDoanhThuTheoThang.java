package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.ThongKeBUS;
import DTO.ThongKeDoanhThuDTO;

public class ThongKeDoanhThuTheoThang extends JPanel {
    private static final long serialVersionUID = 1L;
    private ThongKeBUS thongKeBUS;
    private JComboBox<String> cboxNam;
    private JPanel pChartMonthly;
    private DefaultTableModel tableModelMonthly;

    public ThongKeDoanhThuTheoThang() {
        thongKeBUS = new ThongKeBUS();
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(null);
        setBounds(0, 0, 1188, 697);

        // Panel lọc Theo tháng
        JPanel pFilterMonthly = new RoundedPanel(10);
        pFilterMonthly.setBounds(20, 20, 1148, 80);
        pFilterMonthly.setBackground(new Color(227, 242, 253));
        pFilterMonthly.setLayout(null);
        add(pFilterMonthly);

        JLabel lblTheoNam = new JLabel("Chọn năm:");
        lblTheoNam.setBounds(30, 25, 80, 30);
        lblTheoNam.setFont(new Font("Arial", Font.PLAIN, 16));
        pFilterMonthly.add(lblTheoNam);

        String[] years = {"2025", "2024", "2023", "2022", "2021"};
        cboxNam = new JComboBox<>(years);
        cboxNam.setBounds(110, 25, 100, 30);
        cboxNam.setFont(new Font("Arial", Font.PLAIN, 14));
        cboxNam.addActionListener(e -> capNhatDuLieu());
        pFilterMonthly.add(cboxNam);

        JButton btnXuatExcelMonthly = new JButton("Xuất Excel");
        btnXuatExcelMonthly.setBounds(1018, 25, 100, 30);
        btnXuatExcelMonthly.setFont(new Font("Arial", Font.PLAIN, 14));
        btnXuatExcelMonthly.setBackground(new Color(33, 150, 243));
        btnXuatExcelMonthly.setForeground(Color.WHITE);
        btnXuatExcelMonthly.setBorder(BorderFactory.createEmptyBorder());
        btnXuatExcelMonthly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        pFilterMonthly.add(btnXuatExcelMonthly);

        // Biểu đồ cột
        pChartMonthly = new RoundedPanel(10);
        pChartMonthly.setBounds(20, 120, 1148, 300);
        pChartMonthly.setBackground(Color.WHITE);
        pChartMonthly.setLayout(null);
        add(pChartMonthly);

        // Bảng dữ liệu chi tiết
        String[] columnNamesMonthly = {"Tháng", "Chi phí", "Doanh thu", "Lợi nhuận"};
        tableModelMonthly = new DefaultTableModel(columnNamesMonthly, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableMonthly = new JTable(tableModelMonthly);
        tableMonthly.setRowHeight(30);
        tableMonthly.setFont(new Font("Arial", Font.PLAIN, 14));
        tableMonthly.setBackground(new Color(245, 245, 245));
        tableMonthly.setGridColor(new Color(200, 200, 200));
        tableMonthly.setShowVerticalLines(true);
        JScrollPane scrollPaneMonthly = new JScrollPane(tableMonthly);
        scrollPaneMonthly.setBounds(20, 440, 1148, 220);
        add(scrollPaneMonthly);

        // Điều chỉnh chiều rộng cột
        tableMonthly.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableMonthly.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableMonthly.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableMonthly.getColumnModel().getColumn(3).setPreferredWidth(250);

        // Cập nhật dữ liệu ban đầu
        capNhatDuLieu();
    }

    private void capNhatDuLieu() {
        int nam = Integer.parseInt((String) cboxNam.getSelectedItem());
        ArrayList<ThongKeDoanhThuDTO> listThongKe = thongKeBUS.thongKeDoanhThuTheoThang(nam);

        // Cập nhật biểu đồ
        pChartMonthly.removeAll();
        double[] capital = new double[12];
        double[] revenue = new double[12];
        double[] profit = new double[12];
        String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                          "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        for (ThongKeDoanhThuDTO thongKe : listThongKe) {
            int index = thongKe.getThang() - 1;
            capital[index] = thongKe.getChiPhi();
            revenue[index] = thongKe.getDoanhThu();
            profit[index] = thongKe.getLoiNhuan();
        }
        pChartMonthly.add(new Chart.CustomChartPanelMonthly(capital, revenue, profit, months));
        pChartMonthly.revalidate();
        pChartMonthly.repaint();

        // Cập nhật bảng
        tableModelMonthly.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#,###,###đ");
        for (ThongKeDoanhThuDTO thongKe : listThongKe) {
            tableModelMonthly.addRow(new Object[]{
                "Tháng " + thongKe.getThang(),
                df.format((int)thongKe.getChiPhi()),
                df.format((int)thongKe.getDoanhThu()),
                df.format((int)thongKe.getLoiNhuan())
            });
        }
    }

    private void exportToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Thong Ke Doanh Thu Theo Thang");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Tháng", "Chi phí", "Doanh thu", "Lợi nhuận"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Create data rows
            for (int i = 0; i < tableModelMonthly.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < tableModelMonthly.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(tableModelMonthly.getValueAt(i, j).toString());
                }
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Let user choose file location
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new java.io.File("ThongKeDoanhThu_TheoThang_" + cboxNam.getSelectedItem() + ".xlsx"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }
                workbook.close();
                JOptionPane.showMessageDialog(this, "Xuất file Excel thành công: " + filePath, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                workbook.close();
                return; // User canceled the save operation
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Lớp RoundedPanel để bo góc
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2.dispose();
        }
    }
}