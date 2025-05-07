package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import BUS.ThongKeBUS;
import DTO.ThongKeDoanhThuDTO;

public class ThongKeDoanhThuTuNgayDenNgay extends JPanel {
    private static final long serialVersionUID = 1L;
    private ThongKeBUS thongKeBUS;
    private JDateChooser dateChooserTuNgay;
    private JDateChooser dateChooserDenNgay;
    private JPanel pChartDateRange;
    private DefaultTableModel tableModelDateRange;
    private JTable tableDateRange;

    public ThongKeDoanhThuTuNgayDenNgay() {
        thongKeBUS = new ThongKeBUS();
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(null);
        setBounds(0, 0, 1228, 737);

        // Sub-TabbedPane để chuyển đổi giữa các tab con
        JTabbedPane subTabbedPane = new JTabbedPane();
        subTabbedPane.setBounds(0, 0, 1228, 737);
        subTabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));
        add(subTabbedPane);

        // Sub-Tab 1: Thống kê từ ngày đến ngày
        JPanel pDateRange = new JPanel();
        pDateRange.setBackground(Color.WHITE);
        pDateRange.setLayout(null);
        subTabbedPane.addTab("Thống kê từ ngày đến ngày", pDateRange);

        // Sub-Tab 2: Thống kê theo tháng trong năm
        ThongKeDoanhThuTheoThang pMonthly = new ThongKeDoanhThuTheoThang();
        subTabbedPane.addTab("Thống kê theo tháng trong năm", pMonthly);

        // --- Thiết kế Sub-Tab: Thống kê từ ngày đến ngày ---
        // Panel lọc Từ ngày → Đến ngày
        JPanel pFilterDateRange = new RoundedPanel(10);
        pFilterDateRange.setBounds(20, 20, 1188, 80);
        pFilterDateRange.setBackground(new Color(227, 242, 253));
        pFilterDateRange.setLayout(null);
        pDateRange.add(pFilterDateRange);

        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setBounds(30, 25, 100, 30);
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        pFilterDateRange.add(lblTuNgay);

        dateChooserTuNgay = new JDateChooser();
        dateChooserTuNgay.setBounds(110, 25, 120, 30);
        dateChooserTuNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        Calendar calTuNgay = Calendar.getInstance();
        calTuNgay.set(2024, Calendar.APRIL, 1); // Đặt mặc định 2024-04-01
        dateChooserTuNgay.setDate(calTuNgay.getTime());
        pFilterDateRange.add(dateChooserTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setBounds(250, 25, 100, 30);
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        pFilterDateRange.add(lblDenNgay);

        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setBounds(330, 25, 120, 30);
        dateChooserDenNgay.setFont(new Font("Arial", Font.PLAIN, 14));
        Calendar calDenNgay = Calendar.getInstance();
        calDenNgay.set(2024, Calendar.APRIL, 5); // Đặt mặc định 2024-04-05
        dateChooserDenNgay.setDate(calDenNgay.getTime());
        pFilterDateRange.add(dateChooserDenNgay);

        JButton btnLoc = new JButton("Lọc");
        btnLoc.setBounds(470, 25, 100, 30);
        btnLoc.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLoc.setBackground(new Color(33, 150, 243));
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setBorder(BorderFactory.createEmptyBorder());
        btnLoc.addActionListener(e -> capNhatDuLieu());
        pFilterDateRange.add(btnLoc);

        JButton btnXuatExcelDateRange = new JButton("Xuất Excel");
        btnXuatExcelDateRange.setBounds(1018, 25, 100, 30);
        btnXuatExcelDateRange.setFont(new Font("Arial", Font.PLAIN, 14));
        btnXuatExcelDateRange.setBackground(new Color(33, 150, 243));
        btnXuatExcelDateRange.setForeground(Color.WHITE);
        btnXuatExcelDateRange.setBorder(BorderFactory.createEmptyBorder());
        btnXuatExcelDateRange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ExcelExporter.exportJTableToExcel(tableDateRange);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        pFilterDateRange.add(btnXuatExcelDateRange);

        // Biểu đồ cột
        pChartDateRange = new RoundedPanel(10);
        pChartDateRange.setBounds(20, 120, 1188, 300);
        pChartDateRange.setBackground(Color.WHITE);
        pChartDateRange.setLayout(null);
        pDateRange.add(pChartDateRange);

        // Bảng dữ liệu chi tiết
        String[] columnNamesDateRange = {"Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"};
        tableModelDateRange = new DefaultTableModel(columnNamesDateRange, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDateRange = new JTable(tableModelDateRange);
        tableDateRange.setRowHeight(30);
        tableDateRange.setFont(new Font("Arial", Font.PLAIN, 14));
        tableDateRange.setBackground(new Color(245, 245, 245));
        tableDateRange.setGridColor(new Color(200, 200, 200));
        tableDateRange.setShowVerticalLines(true);
        tableDateRange.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPaneDateRange = new JScrollPane(tableDateRange);
        scrollPaneDateRange.setBounds(20, 440, 1188, 220);
        scrollPaneDateRange.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pDateRange.add(scrollPaneDateRange);

        // Điều chỉnh chiều rộng cột
        tableDateRange.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableDateRange.getColumnModel().getColumn(1).setPreferredWidth(250);
        tableDateRange.getColumnModel().getColumn(2).setPreferredWidth(250);
        tableDateRange.getColumnModel().getColumn(3).setPreferredWidth(250);

        // Cập nhật dữ liệu ban đầu
        capNhatDuLieu();
    }

    private void capNhatDuLieu() {
        try {
            // Lấy ngày từ JDateChooser
            java.util.Date tuNgayUtil = dateChooserTuNgay.getDate();
            java.util.Date denNgayUtil = dateChooserDenNgay.getDate();

            if (tuNgayUtil == null || denNgayUtil == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date tuNgay = new Date(tuNgayUtil.getTime());
            Date denNgay = new Date(denNgayUtil.getTime());

            if (tuNgay.after(denNgay)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lấy dữ liệu từ ThongKeBUS
            ArrayList<ThongKeDoanhThuDTO> listThongKe = thongKeBUS.thongKeDoanhThuTuNgayDenNgay(tuNgay, denNgay);

            // Cập nhật biểu đồ
            pChartDateRange.removeAll();
            double[] revenue = new double[listThongKe.size()];
            String[] days = new String[listThongKe.size()];
            for (int i = 0; i < listThongKe.size(); i++) {
            	ThongKeDoanhThuDTO thongKe = listThongKe.get(i);
                revenue[i] = thongKe.getDoanhThu();
                days[i] = new SimpleDateFormat("dd/MM").format(thongKe.getNgay());
            }
            pChartDateRange.add(new Chart.CustomChartPanelDateRange(revenue, days));
            pChartDateRange.revalidate();
            pChartDateRange.repaint();

            // Cập nhật bảng
            tableModelDateRange.setRowCount(0);
            DecimalFormat df = new DecimalFormat("#,###,###đ");
            for (ThongKeDoanhThuDTO thongKe : listThongKe) {
                tableModelDateRange.addRow(new Object[]{
                    new SimpleDateFormat("dd/MM").format(thongKe.getNgay()),
                    df.format((int)thongKe.getChiPhi()),
                    df.format((int)thongKe.getDoanhThu()),
                    df.format((int)thongKe.getLoiNhuan())
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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