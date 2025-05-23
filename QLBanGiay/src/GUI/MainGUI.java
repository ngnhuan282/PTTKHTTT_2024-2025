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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import BUS.QuyenBUS;

public class MainGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int DEFAULT_WIDTH = 1450, DEFAULT_HEIGHT = 800;
//    private String color = "#FF5252";
    private String color = "#006666";
    private CardLayout cardLayout;
    private JPanel pContent;
    private String username;
    private int maTK;
    private QuyenBUS quyenBUS;
    private int[] y = {49, 85, 121, 157, 193, 229, 265, 301, 337};
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainGUI frame = new MainGUI("Admin", 0);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainGUI(String username, int maTK) throws SQLException {
    	this.username = username;
    	this.maTK = maTK;
    	
    	quyenBUS = new QuyenBUS();
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
        int i = 0;
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
        
        JButton btnBanHang = new JButton("BÁN HÀNG");
        btnBanHang.setIcon(new ImageIcon(MainGUI.class.getResource("/image/shopIcon.png")));
        btnBanHang.setOpaque(true);
        btnBanHang.setHorizontalAlignment(SwingConstants.LEFT);
        btnBanHang.setForeground(Color.WHITE);
        btnBanHang.setFont(new Font("Verdana", Font.BOLD, 14));
        btnBanHang.setBorderPainted(false);
        btnBanHang.setBounds(20, 49, 200, 35);
        btnBanHang.setBackground(Color.decode(color));
        btnBanHang.addActionListener(this);
        pNavItem.add(btnBanHang);
        if(quyenBUS.checkQuyen(maTK, 1)) {
        	btnBanHang.setVisible(true);
        	btnBanHang.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnSanPhamGUI = new JButton("SẢN PHẨM");
        btnSanPhamGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/productIcon.png")));
        btnSanPhamGUI.setOpaque(true);
        btnSanPhamGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnSanPhamGUI.setForeground(Color.WHITE);
        btnSanPhamGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnSanPhamGUI.setBorderPainted(false);
        btnSanPhamGUI.setBackground(Color.decode(color));
        btnSanPhamGUI.setBounds(20, 85, 200, 35);
        btnSanPhamGUI.addActionListener(this);
        pNavItem.add(btnSanPhamGUI);
        btnSanPhamGUI.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 2)) {
        	btnSanPhamGUI.setVisible(true);
        	btnSanPhamGUI.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnNhaCungCapGUI = new JButton("NHÀ CUNG CẤP");
        btnNhaCungCapGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/providerIcon.png")));
        btnNhaCungCapGUI.setOpaque(true);
        btnNhaCungCapGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnNhaCungCapGUI.setForeground(Color.WHITE);
        btnNhaCungCapGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnNhaCungCapGUI.setBorderPainted(false);
        btnNhaCungCapGUI.setBackground(Color.decode(color));
        btnNhaCungCapGUI.setBounds(20, 121, 200, 35);
        btnNhaCungCapGUI.addActionListener(this);
        pNavItem.add(btnNhaCungCapGUI);
        btnNhaCungCapGUI.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 3)) {
        	btnNhaCungCapGUI.setVisible(true);
        	btnNhaCungCapGUI.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnNhanVienGUI = new JButton("NHÂN VIÊN");
        btnNhanVienGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/employeeIcon.png")));
        btnNhanVienGUI.setOpaque(true);
        btnNhanVienGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnNhanVienGUI.setForeground(Color.WHITE);
        btnNhanVienGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnNhanVienGUI.setBorderPainted(false);
        btnNhanVienGUI.setBackground(Color.decode(color));
        btnNhanVienGUI.setBounds(20, 157, 200, 35);
        btnNhanVienGUI.addActionListener(this);
        pNavItem.add(btnNhanVienGUI);
        btnNhanVienGUI.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 4)) {
        	btnNhanVienGUI.setVisible(true);
        	btnNhanVienGUI.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnKhachHangGUI = new JButton("KHÁCH HÀNG");
        btnKhachHangGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/clientIcon.png")));
        btnKhachHangGUI.setOpaque(true);
        btnKhachHangGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnKhachHangGUI.setForeground(Color.WHITE);
        btnKhachHangGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnKhachHangGUI.setBorderPainted(false);
        btnKhachHangGUI.setBackground(Color.decode(color));
        btnKhachHangGUI.setBounds(20, 193, 200, 35);
        btnKhachHangGUI.addActionListener(this);
        pNavItem.add(btnKhachHangGUI);
        btnKhachHangGUI.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 5)) {
        	btnKhachHangGUI.setVisible(true);
        	btnKhachHangGUI.setBounds(20, y[i], 200, 35);
        	i++;
        }


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
        btnPhieuNhap.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 6)) {
        	btnPhieuNhap.setVisible(true);
        	btnPhieuNhap.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnPhieuXuat = new JButton("PHIẾU XUẤT");
        btnPhieuXuat.setOpaque(true);
        btnPhieuXuat.setIcon(new ImageIcon(MainGUI.class.getResource("/image/phieuxuat2.png")));
        btnPhieuXuat.setHorizontalAlignment(SwingConstants.LEFT);
        btnPhieuXuat.setForeground(Color.WHITE);
        btnPhieuXuat.setFont(new Font("Verdana", Font.BOLD, 14));
        btnPhieuXuat.setBorderPainted(false);
        btnPhieuXuat.setBackground(new Color(0, 102, 102));
        btnPhieuXuat.setBounds(20, 265, 200, 35);
        btnPhieuXuat.addActionListener(this);
        pNavItem.add(btnPhieuXuat);
        btnPhieuXuat.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 9)) {
        	btnPhieuXuat.setVisible(true);
        	btnPhieuXuat.setBounds(20, y[i], 200, 35);
        	i++;
        }

        JButton btnKhuyenMaiGUI = new JButton("KHUYẾN MÃI");
        btnKhuyenMaiGUI.setIcon(new ImageIcon(MainGUI.class.getResource("/image/saleIcon.png")));
        btnKhuyenMaiGUI.setOpaque(true);
        btnKhuyenMaiGUI.setHorizontalAlignment(SwingConstants.LEFT);
        btnKhuyenMaiGUI.setForeground(Color.WHITE);
        btnKhuyenMaiGUI.setFont(new Font("Verdana", Font.BOLD, 14));
        btnKhuyenMaiGUI.setBorderPainted(false);
        btnKhuyenMaiGUI.setBackground(Color.decode(color));
        btnKhuyenMaiGUI.setBounds(20, 301, 200, 35);
        btnKhuyenMaiGUI.addActionListener(this);
        pNavItem.add(btnKhuyenMaiGUI);
        btnKhuyenMaiGUI.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 8)) {
        	btnKhuyenMaiGUI.setVisible(true);
        	btnKhuyenMaiGUI.setBounds(20, y[i], 200, 35);
        	i++;
        }
        
        JButton btnThongKe = new JButton("THỐNG KÊ");
        btnThongKe.setOpaque(true);
        btnThongKe.setIcon(new ImageIcon(MainGUI.class.getResource("/image/chart20.png")));
        btnThongKe.setHorizontalAlignment(SwingConstants.LEFT);
        btnThongKe.setForeground(Color.WHITE);
        btnThongKe.setFont(new Font("Verdana", Font.BOLD, 14));
        btnThongKe.setBorderPainted(false);
        btnThongKe.setBackground(Color.decode(color));
        btnThongKe.setBounds(20, 337, 200, 35);
        btnThongKe.addActionListener(this);
        pNavItem.add(btnThongKe);
        btnThongKe.setVisible(false);
        if(quyenBUS.checkQuyen(maTK, 7)) {
        	btnThongKe.setVisible(true);
        	btnThongKe.setBounds(20, y[i], 200, 35);
        	i++;
        }

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
        pContent.add(new NhanVienGUI(maTK, this), "NhanVien");
        pContent.add(new KhachHangGUI(), "KhachHang");
        pContent.add(new PhieuNhapGUI(), "PhieuNhap");
        pContent.add(new PhieuXuatGUI(), "PhieuXuat");
        try {
			pContent.add(new BanHangTabGUI(), "HoaDon");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        pContent.add(new CTKMGUI(), "KhuyenMai");
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
            case "PHIẾU XUẤT":
                cardLayout.show(pContent, "PhieuXuat");
                break;
            case "BÁN HÀNG":
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

//    class PhieuXuatGUI extends JPanel {
//        public PhieuXuatGUI() {
//            setBackground(Color.WHITE);
//            add(new JLabel("Đây là Phiếu Xuất", SwingConstants.CENTER));
//        }
//    }

}
    
 