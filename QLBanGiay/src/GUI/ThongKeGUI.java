package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import javax.swing.*;

public class ThongKeGUI extends JPanel {
    private static final long serialVersionUID = 1L;

    public ThongKeGUI() {
        initComponents();
    }

    private void initComponents() {
        setPreferredSize(new Dimension(1248, 757));
        setLayout(null);
        setBackground(Color.WHITE);

        // TabbedPane để chuyển đổi giữa các trang
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 10, 1228, 737);
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));
        add(tabbedPane);

        // Tab 1: Tổng quan
        JPanel pTongQuan = new JPanel();
        pTongQuan.setBackground(Color.WHITE);
        pTongQuan.setLayout(null);
        tabbedPane.addTab("Tổng quan", pTongQuan);

        // Tab 2: Doanh thu (Gọi từ ThongKeDoanhThuTuNgayDenNgay)
        ThongKeDoanhThuTuNgayDenNgay pDoanhThuTab = new ThongKeDoanhThuTuNgayDenNgay();
        tabbedPane.addTab("Doanh thu", pDoanhThuTab);

        // Tab 3: Hóa đơn (Gọi từ ThongKeHoaDonGUI)
        ThongKeHoaDonGUI pHoaDon = new ThongKeHoaDonGUI();
        tabbedPane.addTab("Hóa đơn", pHoaDon);

        // --- Thiết kế Tab Tổng quan ---
        // Header: TỔNG QUAN
        JLabel lblHeaderTongQuan = new JLabel("TỔNG QUAN", SwingConstants.CENTER);
        lblHeaderTongQuan.setBounds(0, 10, 1228, 50);
        lblHeaderTongQuan.setFont(new Font("Arial", Font.BOLD, 30));
        lblHeaderTongQuan.setForeground(new Color(255, 82, 82));
        pTongQuan.add(lblHeaderTongQuan);

        // 4 Panel trang trí trong Tab Tổng quan
        JPanel pSanPham = createDecorativePanel("Sản phẩm trong kho", "14", "/image/productIcon.png", Color.decode("#E3F2FD"));
        pSanPham.setBounds(20, 70, 580, 280);
        pTongQuan.add(pSanPham);

        JPanel pKhachHang = createDecorativePanel("Khách hàng trực tiếp", "18", "/image/clientIcon.png", Color.decode("#FFEBEE"));
        pKhachHang.setBounds(620, 70, 580, 280);
        pTongQuan.add(pKhachHang);

        JPanel pNhanVien = createDecorativePanel("Nhân viên đang hoạt động", "5", "/image/employeeIcon.png", Color.decode("#FFFDE7"));
        pNhanVien.setBounds(20, 370, 580, 280);
        pTongQuan.add(pNhanVien);

        JPanel pDoanhThu = createDecorativePanel("Doanh thu tháng này", "26.580.000đ", "/image/chart20.png", Color.decode("#E8F5E9"));
        pDoanhThu.setBounds(620, 370, 580, 280);
        pTongQuan.add(pDoanhThu);
    }

    // Phương thức tạo panel trang trí
    private JPanel createDecorativePanel(String title, String value, String iconPath, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setLayout(null);

        // Icon (ẩn phía dưới nội dung)
        ImageIcon originalIcon = new ImageIcon(ThongKeGUI.class.getResource(iconPath));
        ImageIcon fadedIcon = createFadedIcon(originalIcon, 0.5f);
        JLabel lblIcon = new JLabel(fadedIcon);
        lblIcon.setBounds(400, 120, 150, 150);
        panel.add(lblIcon);

        // Tiêu đề
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(20, 20, 540, 40);
        panel.add(lblTitle);

        // Giá trị
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 36));
        lblValue.setForeground(new Color(255, 82, 82));
        lblValue.setBounds(20, 70, 540, 50);
        panel.add(lblValue);

        return panel;
    }

    // Phương thức làm mờ icon
    private ImageIcon createFadedIcon(ImageIcon originalIcon, float opacity) {
        BufferedImage image = new BufferedImage(
            originalIcon.getIconWidth(),
            originalIcon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = image.createGraphics();
        originalIcon.paintIcon(null, g2d, 0, 0);

        BufferedImage fadedImage = new BufferedImage(
            originalIcon.getIconWidth(),
            originalIcon.getIconHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        g2d = fadedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(fadedImage);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test ThongKeGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new ThongKeGUI());
        frame.setVisible(true);
    }
}