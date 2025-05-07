package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int DEFAULT_WIDTH = 1450, DEFAULT_HEIGHT = 800;
//    private String color = "#FF5252";
    private String color = "#006666";
    private CardLayout cardLayout;
    private JPanel pContent;
    private String username;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainGUI frame = new MainGUI("Admin");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainGUI(String username) throws SQLException {
    	this.username = username;
        initComponents();
    }

    private void initComponents() throws SQLException {
        setTitle("Hệ thống quản lý bán giày");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Navbar
        JPanel pNavbar = new JPanel();
        pNavbar.setBounds(5, 6, 230, 800);
        pNavbar.setPreferredSize(new Dimension(200, 800));
        pNavbar.setBackground(Color.decode(color));
        contentPane.add(pNavbar);
        pNavbar.setLayout(null);

        JPanel pAccount = new JPanel();
        pAccount.setBounds(0, 0, 230, 88);
        pAccount.setBackground(Color.decode(color));
        pNavbar.add(pAccount);
        pAccount.setLayout(null);
 
        JLabel lbInfo1 = new JLabel("Xin chào");
        lbInfo1.setIcon(new ImageIcon(MainGUI.class.getResource("/image/userIcon.png")));
        lbInfo1.setVerticalTextPosition(SwingConstants.BOTTOM);
        lbInfo1.setHorizontalTextPosition(SwingConstants.CENTER);
        lbInfo1.setForeground(Color.WHITE);
        lbInfo1.setBounds(16, 0, 200, 54);
        lbInfo1.setFont(new Font("Verdana", Font.BOLD, 14));
        lbInfo1.setHorizontalAlignment(SwingConstants.CENTER);
        pAccount.add(lbInfo1);

        JLabel lbInfo2 = new JLabel(username); // Hiển thị tên đăng nhập
        lbInfo2.setBounds(13, 49, 200, 33);
        lbInfo2.setForeground(Color.WHITE);
        lbInfo2.setFont(new Font("Verdana", Font.BOLD, 14));
        lbInfo2.setHorizontalAlignment(SwingConstants.CENTER);
        pAccount.add(lbInfo2);

        JPanel pNavItem = new JPanel();
        pNavItem.setBounds(0, 86, 230, 672);
        pNavItem.setBackground(Color.decode(color));
        pNavbar.add(pNavItem);
        pNavItem.setLayout(null);

        JButton btnDangXuat = new JButton("ĐĂNG XUẤT");
        btnDangXuat.setIcon(new ImageIcon(MainGUI.class.getResource("/image/logoutIcon.png")));
        btnDangXuat.setOpaque(true);
        btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setFont(new Font("Verdana", Font.BOLD, 14));
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setBackground(Color.decode(color));
        btnDangXuat.setBounds(20, 600, 200, 35);
        btnDangXuat.addActionListener(this);
        pNavItem.add(btnDangXuat);

        JButton btnTrangChuGUI = new JButton("TRANG CHỦ");
        btnTrangChuGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/homeIcon.png")));
        btnTrangChuGUI.setOpaque(true);
        btnTrangChuGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnTrangChuGUI.setForeground(Color.WHITE);
        btnTrangChuGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnTrangChuGUI.setBorderPainted(false);
        btnTrangChuGUI.setBackground(Color.decode(color));
        btnTrangChuGUI.setBounds(20, 13, 200, 35);
        btnTrangChuGUI.addActionListener(this);
        pNavItem.add(btnTrangChuGUI);

        JButton btnSanPhamGUI = new JButton("SẢN PHẨM");
        btnSanPhamGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/productIcon.png")));
        btnSanPhamGUI.setOpaque(true);
        btnSanPhamGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnSanPhamGUI.setForeground(Color.WHITE);
        btnSanPhamGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnSanPhamGUI.setBorderPainted(false);
        btnSanPhamGUI.setBackground(Color.decode(color));
        btnSanPhamGUI.setBounds(20, 49, 200, 35);
        btnSanPhamGUI.addActionListener(this);
        pNavItem.add(btnSanPhamGUI);

        JButton btnNhaCungCapGUI = new JButton("NHÀ CUNG CẤP");
        btnNhaCungCapGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/providerIcon.png")));
        btnNhaCungCapGUI.setOpaque(true);
        btnNhaCungCapGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnNhaCungCapGUI.setForeground(Color.WHITE);
        btnNhaCungCapGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnNhaCungCapGUI.setBorderPainted(false);
        btnNhaCungCapGUI.setBackground(Color.decode(color));
        btnNhaCungCapGUI.setBounds(20, 85, 200, 35);
        btnNhaCungCapGUI.addActionListener(this);
        pNavItem.add(btnNhaCungCapGUI);

        JButton btnNhanVienGUI = new JButton("NHÂN VIÊN");
        btnNhanVienGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/employeeIcon.png")));
        btnNhanVienGUI.setOpaque(true);
        btnNhanVienGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnNhanVienGUI.setForeground(Color.WHITE);
        btnNhanVienGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnNhanVienGUI.setBorderPainted(false);
        btnNhanVienGUI.setBackground(Color.decode(color));
        btnNhanVienGUI.setBounds(20, 121, 200, 35);
        btnNhanVienGUI.addActionListener(this);
        pNavItem.add(btnNhanVienGUI);

        JButton btnKhachHangGUI = new JButton("KHÁCH HÀNG");
        btnKhachHangGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/clientIcon.png")));
        btnKhachHangGUI.setOpaque(true);
        btnKhachHangGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnKhachHangGUI.setForeground(Color.WHITE);
        btnKhachHangGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnKhachHangGUI.setBorderPainted(false);
        btnKhachHangGUI.setBackground(Color.decode(color));
        btnKhachHangGUI.setBounds(20, 157, 200, 35);
        btnKhachHangGUI.addActionListener(this);
        pNavItem.add(btnKhachHangGUI);

        JButton btnHoaDon = new JButton("HÓA ĐƠN");
        btnHoaDon.setIcon(new ImageIcon(MainGUI.class.getResource("/image/phieuNhap.png")));
        btnHoaDon.setOpaque(true);
        btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
        btnHoaDon.setForeground(Color.WHITE);
        btnHoaDon.setFont(new Font("Verdana", Font.BOLD, 14));
        btnHoaDon.setBorderPainted(false);
        btnHoaDon.setBackground(Color.decode(color));
        btnHoaDon.setBounds(20, 193, 200, 35);
        btnHoaDon.addActionListener(this);
        pNavItem.add(btnHoaDon);

        JButton btnPhieuNhap = new JButton("PHIẾU NHẬP");
        btnPhieuNhap.setIcon(new ImageIcon(MainGUI.class.getResource("/image/phieuXuat.png")));
        btnPhieuNhap.setOpaque(true);
        btnPhieuNhap.setHorizontalAlignment(SwingConstants.LEFT);
        btnPhieuNhap.setForeground(Color.WHITE);
        btnPhieuNhap.setFont(new Font("Verdana", Font.BOLD, 14));
        btnPhieuNhap.setBorderPainted(false);
        btnPhieuNhap.setBackground(Color.decode(color));
        btnPhieuNhap.setBounds(20, 229, 200, 35);
        btnPhieuNhap.addActionListener(this);
        pNavItem.add(btnPhieuNhap);

        JButton btnKhuyenMaiGUI = new JButton("KHUYẾN MÃI");
        btnKhuyenMaiGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/saleIcon.png")));
        btnKhuyenMaiGUI.setOpaque(true);
        btnKhuyenMaiGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnKhuyenMaiGUI.setForeground(Color.WHITE);
        btnKhuyenMaiGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnKhuyenMaiGUI.setBorderPainted(false);
        btnKhuyenMaiGUI.setBackground(Color.decode(color));
        btnKhuyenMaiGUI.setBounds(20, 265, 200, 35);
        btnKhuyenMaiGUI.addActionListener(this);
        pNavItem.add(btnKhuyenMaiGUI);
        
        JButton btnThongKe = new JButton("THỐNG KÊ");
        btnThongKe.setOpaque(true);
        btnThongKe.setIcon(new ImageIcon(MainGUI.class.getResource("/image/chart20.png")));
        btnThongKe.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFont(new Font("Verdana", Font.BOLD, 14));
        btnThongKe.setBorderPainted(false);
        btnThongKe.setBackground(Color.decode(color));
        btnThongKe.setBounds(20, 301, 200, 35);
        btnThongKe.addActionListener(this);
        pNavItem.add(btnThongKe);

        // Main content area
        JPanel pMain = new JPanel();
        pMain.setBackground(SystemColor.control);
        pMain.setBounds(233, 6, 1272, 757);
        contentPane.add(pMain);
        pMain.setLayout(null);

        // CardLayout for content
        cardLayout = new CardLayout();
        pContent = new JPanel(cardLayout);
        pContent.setBackground(Color.WHITE);
        pContent.setBounds(0, 0, 1248, 757); // Điều chỉnh để chiếm toàn bộ không gian
        pMain.add(pContent);

        // Add panels to CardLayout
        pContent.add(new TrangChuGUI(), "TrangChu");
        pContent.add(new SanPhamGUI(), "SanPham");
        pContent.add(new NhaCungCapGUI(), "NhaCungCap");
        pContent.add(new NhanVienGUI(), "NhanVien");
        pContent.add(new KhachHangGUI(), "KhachHang");
        pContent.add(new PhieuNhapGUI(), "PhieuNhap");
        pContent.add(new HoaDonGUI(), "HoaDon");
        pContent.add(new CTKMGUI(), "KhuyenMai");
        pContent.add(new ThongKeGUI(), "ThongKe");
        // Default panel
        cardLayout.show(pContent, "TrangChu");
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "TRANG CHỦ":
                cardLayout.show(pContent, "TrangChu");
                break;
            case "SẢN PHẨM":
                cardLayout.show(pContent, "SanPham");
                break;
            case "NHÀ CUNG CẤP":
                cardLayout.show(pContent, "NhaCungCap");
                break;
            case "NHÂN VIÊN":
                cardLayout.show(pContent, "NhanVien");
                break;
            case "KHÁCH HÀNG":
                cardLayout.show(pContent, "KhachHang");
                break;
            case "PHIẾU NHẬP":
                cardLayout.show(pContent, "PhieuNhap");
                break;
            case "HÓA ĐƠN":
                cardLayout.show(pContent, "HoaDon");
                break;
            case "KHUYẾN MÃI":
                cardLayout.show(pContent, "KhuyenMai");
                break;
            case "THỐNG KÊ":
                cardLayout.show(pContent, "ThongKe");
                break;
            case "ĐĂNG XUẤT":
                Login login = new Login();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
                dispose(); // Đóng MainGUI
                break;
        }
    }
    
 // Placeholder panels

//    class NhanVienGUI extends JPanel {
//        public NhanVienGUI() {
//            setBackground(Color.WHITE);
//            add(new JLabel("Đây là Nhân Viên", SwingConstants.CENTER));
//        }
//    }
    
//    class NVGUI extends JPanel {
//        public NVGUI() {
//            setBackground(Color.WHITE);
//            add(new JLabel("Đây là Nhân Viên", SwingConstants.CENTER));
//        }
//    }

    
//    class PhieuNhapGUI extends JPanel {
//        public PhieuNhapGUI() {
//            setBackground(Color.WHITE);
//            add(new JLabel("Đây là Phiếu Nhập", SwingConstants.CENTER));
//        }
//    }

    class PhieuXuatGUI extends JPanel {
        public PhieuXuatGUI() {
            setBackground(Color.WHITE);
            add(new JLabel("Đây là Phiếu Xuất", SwingConstants.CENTER));
        }
    }

}
    
 