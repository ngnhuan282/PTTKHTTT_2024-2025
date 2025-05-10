package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BUS.CTKMBUS;
import BUS.ChiTietHoaDonBUS;
import BUS.HTTTBUS;
import BUS.HoaDonBUS;
import BUS.KMSPBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import DTO.CTKMDTO;
import DTO.ChiTietHDDTO;
import DTO.HTTTDTO;
import DTO.HoaDonDTO;
import DTO.KMSPDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class HoaDonGUI extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int DEFAULT_WIDTH = 1200, DEFAULT_HEIGHT = 800;
    private String color = "#FF5252";
    private JTable table;
    private JTable table_1;
    private JTextField txtMaHD;
    private JTextField txtMaKH;
    private JTextField txtMaNV;
    private JTextField txtMaSP;
    private JTextField txtSoLuong;
    private JButton btnComplete;
    private JButton btnAddProduct;
    private JButton btnCancel;
    private JButton btnXacNhanHD;
    private JButton btnXemCT;
    private JButton btnUpdateBillDetail;
    private JTextField txtMaHD1;
    private JTextField txtMaKH1;
    private JTextField txtMaNV1;
    private JPanel panel_3;


    private HoaDonBUS hoaDonBUS;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private JTextField txtSearch;
    private ArrayList<ChiTietHDDTO> listTemp;
    private SanPhamBUS sanPhamBUS;
    private NhanVienBUS nhanVienBUS;
    private KhachHangBUS khachHangBUS;
    private CTKMBUS ctkmBUS;
    private KMSPBUS kmspBUS;
    private boolean update = false;
    private int selectedRowHoaDon = -1;
    private int selectedRowCTHD = -1;
    private int k = 0;
    private int h = 0;
    private double donGia;
    private double thanhTien;
    private double tongTien = 0;
    private ArrayList<Integer> soLuongTruocKhiUpdate;
    private ArrayList<Integer> soLuongCanTang;
    private ArrayList<String> maSPCanTang;
    private ArrayList<Integer> updateRow;
    private String maSPTruocKhiSua;
    private int soLuongKhiSua;
    private JDateChooser dateChooserTuNgay;
    private JDateChooser dateChooserDenNgay;
    private JComboBox<HTTTDTO> comboBoxHTTT;
    private HTTTBUS htttBUS;
 // === Thêm vào phần đầu class HoaDonGUI ===
    private int startX = 10;
    private int btnWidth = 100;
    private int btnHeight = 42;
    private int btnY = 300;
    private int gap = 10;


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Hóa đơn");
        frame.setTitle("Hệ thống quản lý bán giày");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new HoaDonGUI());
        frame.setVisible(true);
    }

    public HoaDonGUI() throws SQLException {
        hoaDonBUS = new HoaDonBUS();
        try {
            hoaDonBUS.docDSHD(); // ✅ Đọc dữ liệu từ DB khi mở giao diện
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc hóa đơn từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        sanPhamBUS = new SanPhamBUS();
        nhanVienBUS = new NhanVienBUS();
        khachHangBUS = new KhachHangBUS();
        ctkmBUS = new CTKMBUS();
        kmspBUS = new KMSPBUS();
        listTemp = new ArrayList<ChiTietHDDTO>();
        soLuongTruocKhiUpdate = new ArrayList<Integer>();
        soLuongCanTang = new ArrayList<Integer>();
        maSPCanTang = new ArrayList<String>();
        updateRow = new ArrayList<Integer>();
        htttBUS = new HTTTBUS();
        

        initComponents();
        openBillTable();
    }

    public void initComponents() {
        JPanel pHeaderMain = new JPanel();
        pHeaderMain.setBounds(0, 0, 1206, 100);
        pHeaderMain.setBackground(Color.WHITE);
        add(pHeaderMain);

        pHeaderMain.setPreferredSize(new Dimension(1245, 815));
        pHeaderMain.setLayout(null);

        JPanel pLeftHeader = new JPanel();
        pLeftHeader.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pLeftHeader.setBackground(Color.WHITE);
        pLeftHeader.setBounds(0, 0, 430, 100);
        pHeaderMain.add(pLeftHeader);
        pLeftHeader.setLayout(null);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(UIManager.getBorder("Button.border"));
        horizontalBox.setBounds(0, 0, 512, 111);
        pLeftHeader.add(horizontalBox);

        JButton btnThem = new JButton("Thêm");
        horizontalBox.add(btnThem);
        btnThem.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnThem.setActionCommand("Thêm");
        btnThem.addActionListener(this);
        btnThem.setBackground(Color.WHITE);
        btnThem.setBorderPainted(false);
        btnThem.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/add48.png")));
        btnThem.setFont(new Font("Arial", Font.PLAIN, 15));
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setPreferredSize(new Dimension(120, 140));
        btnThem.setVisible(false); // Ẩn button Thêm

        JButton btnSua = new JButton("Sửa");
        horizontalBox.add(btnSua);
        btnSua.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnSua.setActionCommand("Sửa");
        btnSua.addActionListener(this);
        btnSua.setBorderPainted(false);
        btnSua.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSua.setPreferredSize(new Dimension(120, 140));
        btnSua.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit48.png")));
        btnSua.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSua.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSua.setBackground(Color.WHITE);
        btnSua.setVisible(false); // Ẩn button Sửa

        JButton btnXoa = new JButton("Xóa");
        horizontalBox.add(btnXoa);
        btnXoa.setActionCommand("Xóa");
        btnXoa.addActionListener(this);
        btnXoa.setBorderPainted(false);
        btnXoa.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXoa.setPreferredSize(new Dimension(120, 140));
        btnXoa.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/remove48.png")));
        btnXoa.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXoa.setBackground(Color.WHITE);
        btnXoa.setVisible(false); // Ẩn button Xóa

        JButton btnXuatExcel = new JButton("Xuất Excel");
        horizontalBox.add(btnXuatExcel);
        btnXuatExcel.setActionCommand("Xuất Excel");
        btnXuatExcel.addActionListener(this);
        btnXuatExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatExcel.setPreferredSize(new Dimension(120, 140));
        btnXuatExcel.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/excel48.png")));
        btnXuatExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setBackground(Color.WHITE);

        JButton btnXuatPDF = new JButton("Xuất PDF");
        horizontalBox.add(btnXuatPDF);
        btnXuatPDF.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatPDF.addActionListener(this);
        btnXuatPDF.setPreferredSize(new Dimension(120, 140));
        btnXuatPDF.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/pdf48.png")));
        btnXuatPDF.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatPDF.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatPDF.setBorderPainted(false);
        btnXuatPDF.setBackground(Color.WHITE);
        
     // Button Xem chi tiết (thêm vào horizontalBox)
     // Button Xem chi tiết (thêm vào horizontalBox)
        btnXemCT = new JButton("Xem chi tiết");
        horizontalBox.add(btnXemCT);
        btnXemCT.setActionCommand("Xem chi tiết");
        btnXemCT.addActionListener(this);
        btnXemCT.setBackground(Color.WHITE);
        btnXemCT.setBorderPainted(false);
        btnXemCT.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/detailIcon.png")));
        btnXemCT.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXemCT.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXemCT.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXemCT.setPreferredSize(new Dimension(120, 140));
        
     // Button Xác nhận (thêm vào horizontalBox)
        btnXacNhanHD = new JButton("Xác nhận");
        horizontalBox.add(btnXacNhanHD);
        btnXacNhanHD.setActionCommand("Xác nhận");
        btnXacNhanHD.setBackground(Color.WHITE);
        btnXacNhanHD.setBorderPainted(false);
        btnXacNhanHD.setIcon(new ImageIcon(HoaDonGUI.class.getResource("/image/lock_icon.png")));
        btnXacNhanHD.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXacNhanHD.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXacNhanHD.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXacNhanHD.setPreferredSize(new Dimension(120, 140));
        
        btnXacNhanHD.addActionListener(e -> {
            String maHD = txtMaHD.getText().trim();

            if (maHD.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi xác nhận!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn tất hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            boolean success = chiTietHoaDonBUS.capNhatTrangThaiTheoMaHD(maHD, "1");

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtMaHD.setText(maHD);
                try {
                    chiTietHoaDonBUS.docDSCTHD(); // Làm mới dữ liệu từ DB
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                listTemp.clear();
                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                    if (x.getMaHD().equals(maHD)) {
                        listTemp.add(x);
                    }
                }

                btnXacNhanHD.setVisible(false); // Ẩn button Xác nhận sau khi hoàn tất
                openBillDetailTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // === panel tìm kiếm cha ===
        JPanel panelTimKiem = new JPanel();
        panelTimKiem.setBackground(Color.WHITE);
        panelTimKiem.setBounds(540, 10, 700, 100);
        panelTimKiem.setLayout(new BoxLayout(panelTimKiem, BoxLayout.X_AXIS));
        pHeaderMain.add(panelTimKiem);

        // === Panel bên trái: gồm Tìm kiếm nâng cao và theo ngày (dọc) ===
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setBackground(Color.WHITE);

        // --- Tìm kiếm nâng cao ---
        JPanel panelNangCao = new JPanel(null);
        panelNangCao.setPreferredSize(new Dimension(350, 60));
        panelNangCao.setBorder(BorderFactory.createTitledBorder("Tìm kiếm nâng cao"));
        panelNangCao.setBackground(Color.WHITE);

        // Mã HĐ
        JLabel lblMaHDTim = new JLabel("Mã HĐ:");
        lblMaHDTim.setBounds(10, 20, 50, 20);
        panelNangCao.add(lblMaHDTim);

        txtMaHD1 = new JTextField();
        txtMaHD1.setBounds(60, 20, 60, 22);
        panelNangCao.add(txtMaHD1);

        // Mã KH
        JLabel lblMaKHTim = new JLabel("Mã KH:");
        lblMaKHTim.setBounds(130, 20, 50, 20);
        panelNangCao.add(lblMaKHTim);

        txtMaKH1 = new JTextField();
        txtMaKH1.setBounds(180, 20, 60, 22);
        panelNangCao.add(txtMaKH1);

        // Mã NV
        JLabel lblMaNVTim = new JLabel("Mã NV:");
        lblMaNVTim.setBounds(250, 20, 50, 20);
        panelNangCao.add(lblMaNVTim);

        txtMaNV1 = new JTextField();
        txtMaNV1.setBounds(300, 20, 60, 22);
        panelNangCao.add(txtMaNV1);

        // --- Tìm kiếm theo ngày ---
        JPanel panelTheoNgay = new JPanel(null);
        panelTheoNgay.setPreferredSize(new Dimension(350, 60));
        panelTheoNgay.setBorder(BorderFactory.createTitledBorder("Tìm kiếm theo ngày"));
        panelTheoNgay.setBackground(Color.WHITE);

        // Từ ngày
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setBounds(10, 20, 60, 20);
        panelTheoNgay.add(lblTuNgay);

        dateChooserTuNgay = new JDateChooser();
        dateChooserTuNgay.setBounds(70, 20, 90, 22);
        dateChooserTuNgay.setDateFormatString("yyyy-MM-dd");
        panelTheoNgay.add(dateChooserTuNgay);

        // Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setBounds(170, 20, 70, 20);
        panelTheoNgay.add(lblDenNgay);

        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setBounds(240, 20, 90, 22);
        dateChooserDenNgay.setDateFormatString("yyyy-MM-dd");
        panelTheoNgay.add(dateChooserDenNgay);

        // Gộp panel trái
        panelLeft.add(panelNangCao);
        panelLeft.add(panelTheoNgay);

        // === Panel nút bên phải ===
        JPanel panelButtons = new JPanel(null);
        panelButtons.setPreferredSize(new Dimension(180, 100));
        panelButtons.setBackground(Color.WHITE);

        // ComboBox tìm
        JComboBox<String> cboxSearch = new JComboBox<>(new String[]{"Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập"});
        cboxSearch.setBounds(10, 10, 90, 22);
        panelButtons.add(cboxSearch);

        // TextField tìm
        txtSearch = new JTextField();
        txtSearch.setBounds(110, 10, 75, 22);
        panelButtons.add(txtSearch);

        // Button Tìm
        JButton btnSearch = new JButton("", new ImageIcon(SanPhamGUI.class.getResource("/image/search30.png")));
        btnSearch.setBounds(10, 40, 45, 30);
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> {
            String key = cboxSearch.getSelectedItem().toString();
            String keyword = txtSearch.getText().trim();

            String maHD = txtMaHD1.getText().trim();
            String maKH = txtMaKH1.getText().trim();
            String maNV = txtMaNV1.getText().trim();
            java.util.Date tuNgay = dateChooserTuNgay.getDate();
            java.util.Date denNgay = dateChooserDenNgay.getDate();

            ArrayList<HoaDonDTO> result = new ArrayList<>();
            for (HoaDonDTO hd : hoaDonBUS.getListHoaDon()) {
                boolean match = true;

                // Tìm kiếm cơ bản
                if (!keyword.isEmpty()) {
                    String valueToCheck = "";
                    switch (key) {
                        case "Mã hóa đơn":
                            valueToCheck = hd.getMaHD(); break;
                        case "Mã khách hàng":
                            valueToCheck = hd.getMaKH(); break;
                        case "Mã nhân viên":
                            valueToCheck = hd.getMaNV(); break;
                        case "Ngày lập":
                            valueToCheck = hd.getNgayLap().toString(); break;
                    }
                    if (!valueToCheck.toLowerCase().contains(keyword.toLowerCase())) {
                        continue;
                    }
                }

                // Tìm kiếm nâng cao
                if (!maHD.isEmpty() && !hd.getMaHD().toLowerCase().contains(maHD.toLowerCase())) match = false;
                if (!maKH.isEmpty() && !hd.getMaKH().toLowerCase().contains(maKH.toLowerCase())) match = false;
                if (!maNV.isEmpty() && !hd.getMaNV().toLowerCase().contains(maNV.toLowerCase())) match = false;
                if (tuNgay != null && hd.getNgayLap().before(new java.sql.Date(tuNgay.getTime()))) match = false;
                if (denNgay != null && hd.getNgayLap().after(new java.sql.Date(denNgay.getTime()))) match = false;

                if (match) result.add(hd);
            }

            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có kết quả phù hợp", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                openBillTable(result);
            }
        });

        panelButtons.add(btnSearch);

        // Button Làm mới
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(new ImageIcon(HoaDonGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setBounds(65, 40, 120, 30);
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        panelButtons.add(btnLamMoi);

        // === Thêm vào panelTimKiem chính ===
        panelTimKiem.add(panelLeft);
        panelTimKiem.add(Box.createRigidArea(new Dimension(10, 0)));
        panelTimKiem.add(panelButtons);

        JPanel panel = new JPanel();
        panel.setBounds(0, 120, 732, 345);
        pHeaderMain.add(panel);
        panel.setLayout(null);

        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);
        table.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        table.setRowHeight(23);
        table.getTableHeader().setPreferredSize(new Dimension(0, 23));
        table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));

        table.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 0, 732, 345);
        panel.add(jScrollPane);
        table.setBounds(0, 91, 539, 312);
        jScrollPane.setViewportView(table);
        jScrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        jScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 464, 1177, 286);
        pHeaderMain.add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 1177, 286);
        panel_1.add(scrollPane);

        table_1 = new JTable();
        table_1.setBorder(UIManager.getBorder("Table.scrollPaneBorderr"));
        table_1.setBackground(Color.WHITE);
        table_1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table_1.setFillsViewportHeight(true);
        table_1.setFont(new Font("Arial", Font.PLAIN, 13));

        table_1.setFont(new Font("Verdana", Font.PLAIN, 12));
        table_1.setGridColor(new Color(200, 200, 200));
        table_1.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        table_1.setRowHeight(23);
        table_1.getTableHeader().setPreferredSize(new Dimension(0, 23));
        table_1.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        scrollPane.setViewportView(table_1);
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel_3.setBackground(Color.WHITE);
        panel_3.setBounds(732, 120, 445, 345);
        pHeaderMain.add(panel_3);
        panel_3.setLayout(null);
        
        

        JLabel lblNewLabel = new JLabel("Thông tin hóa đơn");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(108, 0, 201, 51);
        panel_3.add(lblNewLabel);

        txtMaHD = new JTextField();
        txtMaHD.setBounds(188, 61, 148, 31);
        panel_3.add(txtMaHD);
        txtMaHD.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Mã hóa đơn");
        lblNewLabel_1.setLabelFor(txtMaHD);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setBounds(80, 60, 83, 31);
        panel_3.add(lblNewLabel_1);
        txtMaHD.setEditable(false); // Khóa chỉnh sửa

        JLabel lbMaKH = new JLabel("Mã khách hàng");
        lbMaKH.setFont(new Font("Tahoma", Font.BOLD, 13));
        lbMaKH.setBounds(80, 142, 103, 31);
        panel_3.add(lbMaKH);

        txtMaKH = new JTextField();
        lbMaKH.setLabelFor(txtMaKH);
        txtMaKH.setColumns(10);
        txtMaKH.setBounds(188, 143, 148, 31);
        txtMaKH.setEditable(false);
        panel_3.add(txtMaKH);

        JButton btnOpenMaKHList = new JButton("...");

        btnOpenMaKHList.setBounds(308, 148, 21, 21);
//        panel_3.add(btnOpenMaKHList);
        btnOpenMaKHList.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Khách hàng");

            JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
            searchPanel.setBackground(Color.WHITE);
            JTextField txtSearchMa = new JTextField();
            txtSearchMa.setFont(new Font("Arial", Font.PLAIN, 13));
            searchPanel.add(txtSearchMa, BorderLayout.CENTER);
            dialog.add(searchPanel, BorderLayout.NORTH);

            String[] colunms = {"Mã khách hàng", "Họ", "Tên", "SĐT"};

            JTable tableMaKH = new JTable();
            JScrollPane jScrollPaneMaKH = new JScrollPane(tableMaKH);

            DefaultTableModel modelMaKHList = new DefaultTableModel(colunms, 0);
            for (KhachHangDTO x : khachHangBUS.getListKhachHang()) {
                Object[] row = {
                        x.getMaKH(),
                        x.getHo(),
                        x.getTen(),
                        x.getSdt()
                };
                modelMaKHList.addRow(row);
            }
            tableMaKH.setModel(modelMaKHList);

            dialog.getContentPane().add(jScrollPaneMaKH);
            dialog.setBounds(800, 330, 700, 200);
            dialog.setVisible(true);

            tableMaKH.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = tableMaKH.getSelectedRow();

                    String maKH = (String) tableMaKH.getValueAt(selectedRow, 0);
                    txtMaKH.setText(maKH);
                    dialog.dispose();
                }
            });

            txtSearchMa.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String keyword = txtSearchMa.getText().trim().toLowerCase();
                    modelMaKHList.setRowCount(0);
                    for (KhachHangDTO nv : khachHangBUS.getListKhachHang()) {
                        if (nv.getMaKH().toLowerCase().contains(keyword))
                            modelMaKHList.addRow(new Object[]{nv.getMaKH(), nv.getHo(), nv.getTen(), nv.getSdt()});
                    }
                }
            });
        });

        JLabel lblMaNV = new JLabel("Mã nhân viên");
        lblMaNV.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMaNV.setBounds(80, 101, 93, 31);
        panel_3.add(lblMaNV);

        txtMaNV = new JTextField();
        lblMaNV.setLabelFor(txtMaNV);
        txtMaNV.setColumns(10);
        txtMaNV.setBounds(188, 102, 148, 31);
        txtMaNV.setEditable(false);
        panel_3.add(txtMaNV);

        JButton btnOpenMaNVList = new JButton("...");
        btnOpenMaNVList.setBounds(308, 107, 21, 21);
//        panel_3.add(btnOpenMaNVList);
        btnOpenMaNVList.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Nhân Viên");

            JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
            searchPanel.setBackground(Color.WHITE);
            JTextField txtSearchMa = new JTextField();
            txtSearchMa.setFont(new Font("Arial", Font.PLAIN, 13));
            searchPanel.add(txtSearchMa, BorderLayout.CENTER);
            dialog.add(searchPanel, BorderLayout.NORTH);

            String[] colunms = {"Mã nhân viên", "Họ", "Tên", "SĐT"};

            JTable tableMaNV = new JTable();
            JScrollPane jScrollPaneMaNV = new JScrollPane(tableMaNV);

            DefaultTableModel modelMaNVList = new DefaultTableModel(colunms, 0);
            for (NhanVienDTO x : nhanVienBUS.getListNhanVien()) {
                Object[] row = {
                        x.getMaNV(),
                        x.getHo(),
                        x.getTen(),
                        x.getSdt()
                };
                modelMaNVList.addRow(row);
            }
            tableMaNV.setModel(modelMaNVList);

            dialog.getContentPane().add(jScrollPaneMaNV);
            dialog.setBounds(800, 330, 700, 200);
            dialog.setVisible(true);

            tableMaNV.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = tableMaNV.getSelectedRow();

                    String maNV = (String) tableMaNV.getValueAt(selectedRow, 0);
                    txtMaNV.setText(maNV);
                    dialog.dispose();
                }
            });

            txtSearchMa.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String keyword = txtSearchMa.getText().trim().toLowerCase();
                    modelMaNVList.setRowCount(0);
                    for (NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
                        if (nv.getMaNV().toLowerCase().contains(keyword))
                            modelMaNVList.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
                    }
                }
            });
        });

        JLabel lblMSnPhm = new JLabel("Mã sản phẩm");
        lblMSnPhm.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMSnPhm.setBounds(80, 183, 93, 31);
        panel_3.add(lblMSnPhm);

        txtMaSP = new JTextField();
        lblMSnPhm.setLabelFor(txtMaSP);
        txtMaSP.setColumns(10);
        txtMaSP.setBounds(188, 184, 148, 31);
        txtMaSP.setEditable(false);
        panel_3.add(txtMaSP);

        JButton btnOpenMaSPList = new JButton("...");
        btnOpenMaSPList.addActionListener(e -> {

            JDialog dialog = new JDialog();
            dialog.setTitle("Sản phẩm");
            String[] colunms = {"Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Mã loại SP", "Số lượng"};

            JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
            searchPanel.setBackground(Color.WHITE);
            JTextField txtSearchMa = new JTextField();
            txtSearchMa.setFont(new Font("Arial", Font.PLAIN, 13));
            searchPanel.add(txtSearchMa, BorderLayout.CENTER);
            dialog.add(searchPanel, BorderLayout.NORTH);

            JTable tableMaSP = new JTable();
            JScrollPane jScrollPaneMaSP = new JScrollPane(tableMaSP);
            sanPhamBUS.docDSSP();
            DefaultTableModel modelMaSpList = new DefaultTableModel(colunms, 0);
            for (SanPhamDTO x : sanPhamBUS.getDssp()) {
                Object[] row = {
                        x.getMaSP(),
                        (kmspBUS.checkMaSPKM(x.getMaSP())) ? x.getTenSP() + "(Sale)" : x.getTenSP(),
                        (kmspBUS.checkMaSPKM(x.getMaSP())) ? Math.round((x.getDonGia() - x.getDonGia() * kmspBUS.getPhanTram(x.getMaSP()) / 100.0)) : x.getDonGia(),
                        x.getMaLoaiSP(),
                        (x.getSoLuong() == 0) ? "Hết hàng" : x.getSoLuong()
                };
                modelMaSpList.addRow(row);
            }
            tableMaSP.setModel(modelMaSpList);

            dialog.getContentPane().add(jScrollPaneMaSP);
            dialog.setBounds(800, 330, 700, 200);
            dialog.setVisible(true);

            tableMaSP.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = tableMaSP.getSelectedRow();
                    String tonHang = tableMaSP.getValueAt(selectedRow, 4).toString();
                    if (tonHang.equals("Hết hàng")) {
                        JOptionPane.showMessageDialog(null, "Sản phẩm hiện tại đang hết hàng", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String maSP = (String) tableMaSP.getValueAt(selectedRow, 0);

                    donGia = Double.valueOf(tableMaSP.getValueAt(selectedRow, 2).toString());
                    txtMaSP.setText(maSP);
                    dialog.dispose();
                }
            });

            txtSearchMa.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String keyword = txtSearchMa.getText().trim().toLowerCase();
                    modelMaSpList.setRowCount(0);
                    for (SanPhamDTO x : sanPhamBUS.getDssp()) {
                        if (x.getMaSP().toLowerCase().contains(keyword))
                            modelMaSpList.addRow(new Object[]{x.getMaSP(), (kmspBUS.checkMaSPKM(x.getMaSP())) ? x.getTenSP() + "(Sale)" : x.getTenSP(), (kmspBUS.checkMaSPKM(x.getMaSP())) ? Math.round((x.getDonGia() - x.getDonGia() * kmspBUS.getPhanTram(x.getMaSP()) / 100.0)) : x.getDonGia(), x.getMaLoaiSP(), (x.getSoLuong() == 0) ? "Hết hàng" : x.getSoLuong()});
                    }
                }
            });

        });
        btnOpenMaSPList.setBounds(308, 189, 21, 21);
//        panel_3.add(btnOpenMaSPList);

        JLabel lblSoLuong = new JLabel("Số lượng");
        lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSoLuong.setBounds(80, 224, 93, 31);
        panel_3.add(lblSoLuong);

        txtSoLuong = new JTextField();
        lblSoLuong.setLabelFor(txtSoLuong);
        txtSoLuong.setColumns(10);
        txtSoLuong.setBounds(188, 225, 148, 31);
        panel_3.add(txtSoLuong);
        txtSoLuong.setEditable(false);
        JLabel lblHTTT = new JLabel("Hình thức TT");
        lblHTTT.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblHTTT.setBounds(80, 265, 100, 31);
        panel_3.add(lblHTTT);

        comboBoxHTTT = new JComboBox<>();
        comboBoxHTTT.setBounds(188, 265, 148, 31);
        for (HTTTDTO h : htttBUS.getListHTTT()) {
            comboBoxHTTT.addItem(h);
        }
        panel_3.add(comboBoxHTTT);
        comboBoxHTTT.setEnabled(false);
        // Đặt nút Xác nhận ngay bên cạnh comboBox
        btnXacNhanHD = new JButton("");
        btnXacNhanHD.setBounds(390, 260, 50, 38);
        btnXacNhanHD.setIcon(new ImageIcon(HoaDonGUI.class.getResource("/image/lock_icon.png")));
        btnXacNhanHD.setBackground(Color.WHITE);
//        panel_3.add(btnXacNhanHD);

        btnXacNhanHD.addActionListener(e -> {
            String maHD = txtMaHD.getText().trim();

            if (maHD.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trước khi xác nhận!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận hoàn tất hóa đơn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            boolean success = chiTietHoaDonBUS.capNhatTrangThaiTheoMaHD(maHD, "1");

            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtMaHD.setText(maHD);
                try {
                    chiTietHoaDonBUS.docDSCTHD(); // Làm mới dữ liệu từ DB
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
                listTemp.clear();
                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                    if (x.getMaHD().equals(maHD)) {
                        listTemp.add(x);
                    }
                }

                btnAddProduct.setVisible(false);
                btnUpdateBillDetail.setVisible(false);
                btnComplete.setVisible(false);
                btnCancel.setVisible(false);
                btnXacNhanHD.setVisible(false);

                openBillDetailTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });


     // Button Hoàn tất (ẩn, không thêm vào panel_3)
        btnComplete = new JButton("Hoàn tất");
        btnComplete.setBounds(startX, btnY, btnWidth, btnHeight);
        btnComplete.setBackground(Color.WHITE);
        btnComplete.setActionCommand("Hoàn tất hóa đơn");
        btnComplete.setVisible(false); // Ẩn button Hoàn tất
        // panel_3.add(btnComplete);

        // Button Thêm SP (ẩn, không thêm vào panel_3)
        btnAddProduct = new JButton("Thêm SP");
        btnAddProduct.setBounds(startX + (btnWidth + gap), btnY, btnWidth, btnHeight);
        btnAddProduct.setBackground(Color.WHITE);
        btnAddProduct.setActionCommand("Thêm sản phẩm");
        btnAddProduct.setVisible(false);
        // panel_3.add(btnAddProduct);

        // Button Xem chi tiết (căn giữa)
        btnXemCT = new JButton("Xem chi tiết");
        int panelWidth = 445; // Chiều rộng của panel_3 theo mã gốc
        int centerX = (panelWidth - btnWidth) / 2; // Tính vị trí x để căn giữa
        btnXemCT.setBounds(centerX, btnY, btnWidth, btnHeight);
        btnXemCT.setBackground(Color.WHITE);
//        panel_3.add(btnXemCT);

        // Button Hủy (ẩn, không thêm vào panel_3)
        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(startX + 3 * (btnWidth + gap), btnY, btnWidth, btnHeight);
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setActionCommand("Hủy"); // Sửa lại ActionCommand đúng (không phải "Thêm sản phẩm")
        btnCancel.setVisible(false);
        // panel_3.add(btnCancel);

        for (JButton btn : new JButton[]{btnComplete, btnAddProduct, btnXemCT, btnCancel}) {
            btn.setFocusPainted(false);
            btn.setMargin(new Insets(0, 0, 0, 0));
        }

        btnUpdateBillDetail = new JButton("Sửa hóa đơn");
        btnUpdateBillDetail.setBackground(Color.WHITE);
        btnUpdateBillDetail.setBounds(150, 300, 154, 42);
        btnUpdateBillDetail.addActionListener(e -> {
            if (txtMaHD.getText().equals("") || txtMaKH.getText().equals("") || txtMaNV.getText().equals("") || txtMaSP.getText().equals("") || txtSoLuong.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (chiTietHoaDonBUS.checkDulicate(txtMaHD.getText(), txtMaSP.getText()) && !txtMaSP.getText().equals(listTemp.get(h).getMaSP())) {
                JOptionPane.showMessageDialog(this, "Vui lòng không thêm sản phẩm giống nhau vào một hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtSoLuong.setText("");
                txtMaSP.setText("");
                return;
            }

            String maHD = txtMaHD.getText();
            String maKH = txtMaKH.getText();
            String maNV = txtMaNV.getText();
            String maSP = txtMaSP.getText();
            int soLuong = Integer.valueOf(txtSoLuong.getText());

            ChiTietHDDTO chiTietHDDTO = listTemp.get(h);

            chiTietHDDTO.setMaHD(maHD);
            chiTietHDDTO.setMaSP(maSP);
            chiTietHDDTO.setSoLuong(soLuong);
            thanhTien = soLuong * donGia;
            chiTietHDDTO.setDonGia(donGia);
            chiTietHDDTO.setThanhTien(thanhTien);

            HTTTDTO selectedHTTT = (HTTTDTO) comboBoxHTTT.getSelectedItem();
            if (selectedHTTT != null) {
                chiTietHDDTO.setMaHTTT(selectedHTTT.getMaHTTT());
            }

            chiTietHoaDonBUS.deleteCTHDByIndex(k);
            chiTietHoaDonBUS.addCTHD(
                    chiTietHDDTO.getMaHD(),
                    chiTietHDDTO.getMaSP(),
                    chiTietHDDTO.getSoLuong(),
                    chiTietHDDTO.getDonGia(),
                    chiTietHDDTO.getThanhTien(),
                    chiTietHDDTO.getTrangThai(),
                    chiTietHDDTO.getMaTTT()
            );

            selectedRowCTHD = table_1.getSelectedRow();
            k = 0;
            donGia = 0;
            thanhTien = 0;
            btnAddProduct.setVisible(true);
            btnUpdateBillDetail.setVisible(false);
            btnXemCT.setVisible(true);
            txtMaNV.setEditable(false);
            txtMaSP.setText("");
            txtSoLuong.setText("");
            JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            openBillDetailTable();
            if (!maSP.equals(maSPTruocKhiSua)) {
                int index = maSPCanTang.indexOf(maSPTruocKhiSua);
                int index2 = updateRow.indexOf(h);
                soLuongTruocKhiUpdate.set(h, 0);

                if (index != -1) {
                    maSPCanTang.remove(index);
                    soLuongCanTang.remove(index);
                }
                if (index2 != -1) {
                    return;
                }
                maSPCanTang.add(maSPTruocKhiSua);
                soLuongCanTang.add(soLuongKhiSua);
                updateRow.add(h);
            }
            maSPTruocKhiSua = "";
            soLuongKhiSua = 0;

        });
        btnUpdateBillDetail.setVisible(false);
        panel_3.add(btnUpdateBillDetail);

        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        openBillTable();
        openBillDetailTable();

        btnAddProduct.addActionListener(e -> {
            if (txtMaHD.getText().equals("") || txtMaKH.getText().equals("") || txtMaNV.getText().equals("") || txtMaSP.getText().equals("") || txtSoLuong.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (chiTietHoaDonBUS.checkDulicate(txtMaHD.getText(), txtMaSP.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng không thêm sản phẩm giống nhau vào một hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtSoLuong.setText("");
                txtMaSP.setText("");
                return;
            }
            String maHD = txtMaHD.getText();
            String maKH = txtMaKH.getText();
            String maNV = txtMaNV.getText();
            String maSP = txtMaSP.getText();
            int soLuong = Integer.valueOf(txtSoLuong.getText());

            soLuongTruocKhiUpdate.add(0);
            thanhTien = donGia * soLuong;

            HTTTDTO selectedHTTT = (HTTTDTO) comboBoxHTTT.getSelectedItem();
            String maHTTT = selectedHTTT != null ? selectedHTTT.getMaHTTT() : "";
            listTemp.add(new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien, "0", maHTTT));

            donGia = 0;
            thanhTien = 0;
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            txtSoLuong.setText("");
            txtMaSP.setText("");
            openBillDetailTable();
        });

        btnComplete.addActionListener(e -> {
            if (listTemp.size() == 0) {
                JOptionPane.showMessageDialog(this, "Bạn cần thêm sản phẩm vào hóa đơn trước khi hoàn tất", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            LocalDate now = LocalDate.now();
            Date ngayLap = Date.valueOf(now);
            if (!update) {
                int i = 0;
                for (ChiTietHDDTO x : listTemp) {
                    tongTien += x.getThanhTien();
                }

                try {
                    if (ctkmBUS.getCTKM_HD(ngayLap) != null) {
                        CTKMDTO ctkmDTO = ctkmBUS.getCTKM_HD(ngayLap);
                        tongTien = tongTien - tongTien * (ctkmDTO.getPhanTramGiamGia() / 100.0);
                        JOptionPane.showMessageDialog(this, "Nhân dịp khuyến mãi " + ctkmDTO.getTenCTKM() + " hóa đơn sẽ được giảm " + ctkmDTO.getPhanTramGiamGia() + "% trên tổng giá trị hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                hoaDonBUS.addHoaDon(txtMaHD.getText(), txtMaKH.getText(), txtMaNV.getText(), ngayLap, Math.round(tongTien * 100.0) / 100.0);
                for (ChiTietHDDTO x : listTemp) {
                    chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong() - soLuongTruocKhiUpdate.get(i));
                    if (chiTietHoaDonBUS.checkDulicateMaSP(x.getMaSP(), x.getMaHD())) {
                        i++;
                        continue;
                    }
                    chiTietHoaDonBUS.addCTHD(x.getMaHD(), x.getMaSP(), x.getSoLuong(), x.getDonGia(), x.getThanhTien(), x.getTrangThai(),x.getMaTTT());
                    chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong());
                    i++;
                }
            } else {
                int selectedRow = table.getSelectedRow();
                int i = 0;

                for (int index = 0; index < soLuongCanTang.size(); index++) {
                    String maSP = maSPCanTang.get(index);
                    chiTietHoaDonBUS.updateSoLuongSP(maSP, -soLuongCanTang.get(index));
                }

                for (ChiTietHDDTO x : listTemp) {
                    tongTien += x.getThanhTien();
                    chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong() - soLuongTruocKhiUpdate.get(i));
                    soLuongTruocKhiUpdate.set(i, x.getSoLuong());
                    if (chiTietHoaDonBUS.checkDulicateMaSP(x.getMaSP(), x.getMaHD())) {
                        i++;
                        continue;
                    }
                    chiTietHoaDonBUS.addCTHD(x.getMaHD(), x.getMaSP(), x.getSoLuong(), x.getDonGia(), x.getThanhTien(),x.getTrangThai(),x.getMaTTT());
                    i++;
                }

                try {
                    if (ctkmBUS.getCTKM_HD(ngayLap) != null) {
                        CTKMDTO ctkmDTO = ctkmBUS.getCTKM_HD(ngayLap);
                        tongTien = tongTien - tongTien * (ctkmDTO.getPhanTramGiamGia() / 100.0);
                        JOptionPane.showMessageDialog(this, "Nhân dịp khuyến mãi " + ctkmDTO.getTenCTKM() + " hóa đơn sẽ được giảm " + ctkmDTO.getPhanTramGiamGia() + "% trên tổng giá trị hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                hoaDonBUS.updateHoaDon(txtMaHD.getText(), txtMaKH.getText(), txtMaNV.getText(), ngayLap, Math.round(tongTien * 100.0) / 100.0, selectedRow);
                update = false;
                soLuongTruocKhiUpdate.clear();
                soLuongCanTang.clear();
                maSPCanTang.clear();
                updateRow.clear();
            }

            printBill(txtMaHD.getText(), txtMaKH.getText(), txtMaNV.getText(), ngayLap, tongTien);

            tongTien = 0;
            txtMaHD.setText("");
            txtMaSP.setText("");
            txtMaNV.setText("");
            txtMaKH.setText("");
            txtSoLuong.setText("");
            txtMaSP.setText("");
            listTemp.clear();

            openBillDetailTable();
            openBillTable();
            JOptionPane.showMessageDialog(this, "Nhập hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCancel.addActionListener(e -> {
            if (listTemp.size() == 0) {
                txtMaHD.setText("");
                txtMaSP.setText("");
                txtMaNV.setText("");
                txtMaKH.setText("");
                txtSoLuong.setText("");
                txtMaSP.setText("");
            } else {
                int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy hóa đơn này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    listTemp.clear();
                    txtMaHD.setText("");
                    txtMaSP.setText("");
                    txtMaNV.setText("");
                    txtMaKH.setText("");
                    txtSoLuong.setText("");
                    txtMaSP.setText("");
                    txtMaHD.setEditable(true);
                    txtMaKH.setEditable(true);
                    txtMaNV.setEditable(true);
                    btnXemCT.setVisible(true);
                    btnAddProduct.setVisible(true);
                    btnUpdateBillDetail.setVisible(false);
                    update = false;
                    openBillDetailTable();
                }
            }
        });

        btnXemCT.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maHD = (String) table.getValueAt(selectedRow, 0);
            String maKH = (String) table.getValueAt(selectedRow, 1);
            String maNV = (String) table.getValueAt(selectedRow, 2);
            Date ngayLap = (Date) table.getValueAt(selectedRow, 3);
            
            double tongTien = (double) table.getValueAt(selectedRow, 4);

            listTemp.clear();
            try {
                chiTietHoaDonBUS.docDSCTHD();
                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                    if (x.getMaHD().equals(maHD)) {
                        listTemp.add(x);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            try {
                ArrayList<ChiTietHDDTO> dsCTHD = new ArrayList<>();
                for (ChiTietHDDTO ct : chiTietHoaDonBUS.getListCTHD()) {
                    if (ct.getMaHD().equals(maHD)) {
                        dsCTHD.add(ct);
                    }
                }
                String maHTTT = dsCTHD.size() > 0 ? dsCTHD.get(0).getMaTTT() : "";

                XemCTHDialog dialog = new XemCTHDialog(maHD, maKH, maNV, maHTTT, dsCTHD, tongTien);

                dialog.setLocationRelativeTo(null);
                dialog.setModal(true);
                dialog.setVisible(true);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi mở chi tiết hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        });

        table_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                k = 0;
                h = 0;
                selectedRowCTHD = table_1.getSelectedRow();

                if (selectedRowCTHD != -1) {
                    String maHTTT = listTemp.get(selectedRowCTHD).getMaTTT();
                    for (int i = 0; i < comboBoxHTTT.getItemCount(); i++) {
                        HTTTDTO item = comboBoxHTTT.getItemAt(i);
                        if (item.getMaHTTT().equals(maHTTT)) {
                            comboBoxHTTT.setSelectedIndex(i);
                            break;
                        }
                    }

                    if (hoaDonBUS.getListHoaDon().size() != 0)
                        txtMaNV.setText(table.getValueAt(selectedRowHoaDon, 2).toString());

                    txtMaSP.setText(table_1.getValueAt(selectedRowCTHD, 1).toString());
                    txtSoLuong.setText(table_1.getValueAt(selectedRowCTHD, 2).toString());

                    ChiTietHDDTO chiTietHDDTO = new ChiTietHDDTO(
                        txtMaHD.getText(),
                        txtMaSP.getText(),
                        Integer.parseInt(txtSoLuong.getText()),
                        Double.parseDouble(table_1.getValueAt(selectedRowCTHD, 3).toString()),
                        Double.parseDouble(table_1.getValueAt(selectedRowCTHD, 4).toString()),
                        listTemp.get(selectedRowCTHD).getTrangThai(),
                        listTemp.get(selectedRowCTHD).getMaTTT()
                    );

                    maSPTruocKhiSua = txtMaSP.getText();
                    soLuongKhiSua = Integer.parseInt(txtSoLuong.getText());

                    for (ChiTietHDDTO x : listTemp) {
                        if (x.equals(chiTietHDDTO)) break;
                        h++;
                    }

                    for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                        if (x.equals(chiTietHDDTO)) break;
                        k++;
                    }

                    donGia = Double.parseDouble(table_1.getValueAt(selectedRowCTHD, 3).toString());
                    String trangThai = listTemp.get(selectedRowCTHD).getTrangThai();
                    // Đảm bảo các button ẩn và btnXemCT luôn hiển thị
                    btnAddProduct.setVisible(false);
                    btnUpdateBillDetail.setVisible(false);
                    btnXemCT.setVisible(true);
                }
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRowHoaDon = table.getSelectedRow();
                if (selectedRowHoaDon != -1) {
                    txtMaNV.setText(table.getValueAt(selectedRowHoaDon, 2).toString());
                }

                update = false;
                openUpdateBill();
            }
        });
    }

    private void printBill(String maHD, String maKH, String maNV, Date ngayLap, double tongTien) {
        KhachHangDTO khachHang = null;
        for (KhachHangDTO kh : khachHangBUS.getListKhachHang()) {
            if (kh.getMaKH().equals(maKH)) {
                khachHang = kh;
                break;
            }
        }

        NhanVienDTO nhanVien = null;
        for (NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
            if (nv.getMaNV().equals(maNV)) {
                nhanVien = nv;
                break;
            }
        }

        StringBuilder billContent = new StringBuilder();
        billContent.append("------------ HÓA ĐƠN BÁN HÀNG ------------\n");
        billContent.append("Mã hóa đơn: ").append(maHD).append("\n");
        billContent.append("Ngày lập: ").append(ngayLap).append("\n\n");

        billContent.append("Thông tin khách hàng:\n");
        if (khachHang != null) {
            billContent.append("Tên: ").append(khachHang.getHo()).append(" ").append(khachHang.getTen()).append("\n");
            billContent.append("SĐT: ").append(khachHang.getSdt()).append("\n");
        } else {
            billContent.append("Không tìm thấy thông tin khách hàng.\n");
        }

        billContent.append("\nThông tin người bán:\n");
        if (nhanVien != null) {
            billContent.append("Mã NV: ").append(nhanVien.getMaNV()).append("\n");
            billContent.append("Tên: ").append(nhanVien.getHo()).append(" ").append(nhanVien.getTen()).append("\n");
        } else {
            billContent.append("Không tìm thấy thông tin nhân viên.\n");
        }

        billContent.append("\nChi tiết sản phẩm:\n");
        
        for (ChiTietHDDTO ct : listTemp) {
            if (ct.getMaHD().equals(maHD)) {
                String tenSP = sanPhamBUS.getTenSP(ct.getMaSP());
                String tenHTTT = getTenHTTT(ct.getMaTTT()); // Lấy tên HTTT từng dòng
                billContent.append(" - Tên SP: ").append(tenSP)
                           .append(", SL: ").append(ct.getSoLuong())
                           .append(", Đơn giá: ").append(ct.getDonGia())
                           .append(", Thành tiền: ").append(ct.getThanhTien())
                           .append(", HTTT: ").append(tenHTTT).append("\n");
            }
        }




        billContent.append("\nTổng tiền: ").append(Math.round(tongTien * 100.0) / 100.0).append("\n");
        billContent.append("-------------------------------------------\n");

        JTextArea billTextArea = new JTextArea(billContent.toString());
        billTextArea.setEditable(false);
        billTextArea.setLineWrap(true);
        billTextArea.setWrapStyleWord(true);
        billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(billTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Thông tin hóa đơn", JOptionPane.INFORMATION_MESSAGE);

        int result = JOptionPane.showConfirmDialog(this, "Bạn có muốn xuất hóa đơn ra PDF không?", "Xuất PDF", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            xuatPDF(maHD);
        }
    }

    private void filterBillsByDate() {
        java.util.Date tuNgay = dateChooserTuNgay.getDate();
        java.util.Date denNgay = dateChooserDenNgay.getDate();

        if (tuNgay == null || denNgay == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả 'Từ ngày' và 'Đến ngày'!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date startDate = new Date(tuNgay.getTime());
        Date endDate = new Date(denNgay.getTime());

        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được sau ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<HoaDonDTO> filteredList = new ArrayList<>();
        for (HoaDonDTO hd : hoaDonBUS.getListHoaDon()) {
            if (!hd.getNgayLap().before(startDate) && !hd.getNgayLap().after(endDate)) {
                filteredList.add(hd);
            }
        }

        if (filteredList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn nào trong khoảng thời gian này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        openBillTable(filteredList);
    }

    private String getTenHTTT(String maHTTT) {
        if (maHTTT == null || maHTTT.trim().isEmpty()) {
            return "Chưa xác định";
        }
        for (HTTTDTO h : htttBUS.getListHTTT()) {
            if (h.getMaHTTT().equals(maHTTT)) {
                return h.getTenHTTT();
            }
        }
        return "Chưa xác định";
    }

//    private void openBillDetailTable() {
//        try {
//            chiTietHoaDonBUS.docDSCTHD();
//            listTemp.clear();
//            String maHD = txtMaHD.getText();
//            if (maHD != null && !maHD.isEmpty()) {
//                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
//                    if (x.getMaHD().equals(maHD)) {
//                        listTemp.add(x);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//            return;
//        }
//
//        String[] columnNameBillDetail = { "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Trạng thái", "HT thanh toán" };
//        DefaultTableModel model1 = new DefaultTableModel(columnNameBillDetail, 0);
//
//        boolean isConfirmed = false;
//
//        for (ChiTietHDDTO x : listTemp) {
//            String trangThai = x.getTrangThai();
//            String trangThaiHienThi;
//            if ("0".equals(trangThai)) {
//                trangThaiHienThi = "Chờ xử lý";
//            } else if ("1".equals(trangThai)) {
//                trangThaiHienThi = "Hoàn tất";
//                isConfirmed = true;
//            } else {
//                trangThaiHienThi = "Không xác định";
//            }
//
//            Object[] row = {
//                x.getMaHD(),
//                x.getMaSP(),
//                x.getSoLuong(),
//                x.getDonGia(),
//                x.getThanhTien(),
//                trangThaiHienThi,
//                getTenHTTT(x.getMaTTT())
//            };
//            model1.addRow(row);
//        }
//
//        table_1.setModel(model1);
//        if (isConfirmed) {
//            // Căn giữa nút "Xem chi tiết"
//            int panelWidth = panel_3.getWidth();
//            int buttonWidth = btnXemCT.getWidth();
//            int centeredX = (panelWidth - buttonWidth) / 2;
//            btnXemCT.setBounds(centeredX, btnXemCT.getY(), buttonWidth, btnXemCT.getHeight());
//        } else {
//            // Trả lại vị trí gốc
//            btnXemCT.setBounds(startX + 2 * (btnWidth + gap), btnY, btnWidth, btnHeight);
//        }
//
//        btnAddProduct.setVisible(!isConfirmed);
//        btnUpdateBillDetail.setVisible(false);
//        btnComplete.setVisible(!isConfirmed);
//        btnCancel.setVisible(!isConfirmed);
//        btnXacNhanHD.setVisible(!isConfirmed);
//    }
    
    private void openBillDetailTable() {
        try {
            chiTietHoaDonBUS.docDSCTHD();
            listTemp.clear();
            String maHD = txtMaHD.getText();
            if (maHD != null && !maHD.isEmpty()) {
                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                    if (x.getMaHD().equals(maHD)) {
                        listTemp.add(x);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        String[] columnNameBillDetail = { "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Trạng thái", "HT thanh toán" };
        DefaultTableModel model1 = new DefaultTableModel(columnNameBillDetail, 0);

        boolean isConfirmed = false;

        for (ChiTietHDDTO x : listTemp) {
            String trangThai = x.getTrangThai();
            String trangThaiHienThi;
            if ("0".equals(trangThai)) {
                trangThaiHienThi = "Chờ xử lý";
            } else if ("1".equals(trangThai)) {
                trangThaiHienThi = "Hoàn tất";
                isConfirmed = true;
            } else {
                trangThaiHienThi = "Không xác định";
            }

            Object[] row = {
                x.getMaHD(),
                x.getMaSP(),
                x.getSoLuong(),
                x.getDonGia(),
                x.getThanhTien(),
                trangThaiHienThi,
                getTenHTTT(x.getMaTTT())
            };
            model1.addRow(row);
        }

        table_1.setModel(model1);
        // Chỉ điều chỉnh hiển thị btnXacNhanHD (các button khác đã ẩn)
        btnXacNhanHD.setVisible(!isConfirmed);
    }

    private void openBillTable(ArrayList<HoaDonDTO> result) {
        String[] columnNamesBill = { "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Tổng tiền" };
        DefaultTableModel model = new DefaultTableModel(columnNamesBill, 0);
        for (HoaDonDTO x : result) {
            Object[] row = {
                    x.getMaHD(),
                    x.getMaKH(),
                    x.getMaNV(),
                    x.getNgayLap(),
                    x.getTongTien()
            };
            model.addRow(row);
        }

        table.setModel(model);
        Font font = new Font("Verdana", Font.PLAIN, 14);
        table.setFont(font);
    }

    private void openBillTable() {
        String[] columnNamesBill = { "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Tổng tiền" };
        DefaultTableModel model = new DefaultTableModel(columnNamesBill, 0);
        for (HoaDonDTO x : hoaDonBUS.getListHoaDon()) {
            Object[] row = {
                    x.getMaHD(),
                    x.getMaKH(),
                    x.getMaNV(),
                    x.getNgayLap(),
                    x.getTongTien()
            };
            model.addRow(row);
        }

        table.setModel(model);
        Font font = new Font("Verdana", Font.PLAIN, 14);
        table.setFont(font);
    }

    public void openAddBill() {
        if (listTemp.size() != 0) {
            JOptionPane.showMessageDialog(this, "Bạn cần hoàn tất hóa đơn này trước khi thêm hóa đơn mới", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        txtMaKH.setText("");
        txtMaNV.setText("");
        txtMaSP.setText("");
        txtSoLuong.setText("");
        txtMaHD.setText(hoaDonBUS.getMaHD());
    }

    public void openUpdateBill() {
        listTemp.clear();
        soLuongTruocKhiUpdate.clear();
        selectedRowHoaDon = table.getSelectedRow();

        if (hoaDonBUS.getListHoaDon().size() == 0) {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedRowHoaDon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = (String) table.getValueAt(selectedRowHoaDon, 0);
        String maKH = (String) table.getValueAt(selectedRowHoaDon, 1);
        String maNV = (String) table.getValueAt(selectedRowHoaDon, 2);

        try {
            chiTietHoaDonBUS.docDSCTHD();
            for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                if (x.getMaHD().equals(maHD)) {
                    listTemp.add(x);
                    soLuongTruocKhiUpdate.add(x.getSoLuong());
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        txtMaHD.setText(maHD);
        txtMaKH.setText(maKH);
        txtMaNV.setText(maNV);
        update = true;
        btnXemCT.setVisible(true);
        openBillDetailTable();
    }

    public void openDeleteBill() {
        if (hoaDonBUS.getListHoaDon().size() == 0) {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedRowHoaDon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        btnAddProduct.setVisible(true);
        btnUpdateBillDetail.setVisible(false);
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            selectedRowHoaDon = table.getSelectedRow();
            selectedRowCTHD = table_1.getSelectedRow();
            if (selectedRowHoaDon != -1 && selectedRowCTHD == -1) {
                String maHD = (String) table.getValueAt(selectedRowHoaDon, 0);
                chiTietHoaDonBUS.deleteCTHD(maHD);
                hoaDonBUS.deleteHoaDon(selectedRowHoaDon);
                listTemp.clear();
                openBillDetailTable();
                openBillTable();
                update = false;
                txtMaHD.setText("");
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else if (selectedRowCTHD != -1) {
                ChiTietHDDTO chiTietHDDTO = listTemp.get(h);
                chiTietHoaDonBUS.deleteCTHDByIndex(k, chiTietHDDTO.getMaSP());
                listTemp.remove(h);
                tongTien = 0;
                for (ChiTietHDDTO x : listTemp) {
                    tongTien += x.getThanhTien();
                }
                hoaDonBUS.updateTongTien((String) table_1.getValueAt(selectedRowCTHD, 0), tongTien);
                openBillDetailTable();
                openBillTable();
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            txtMaSP.setText("");
            txtMaNV.setText("");
            txtMaKH.setText("");
            txtSoLuong.setText("");
            txtMaSP.setText("");
        }
    }

    public static  void xuatPDF(String maHD) {
        try {
            PDFReporter pdfReporter = new PDFReporter();
            pdfReporter.writeHoaDon(maHD);
            JOptionPane.showMessageDialog(null, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();

        if (str.equals("Thêm")) {
            openAddBill();
        } else if (str.equals("Sửa")) {
            if (!update) {
                openUpdateBill();
            } else {
                return;
            }
        } else if (str.equals("Xóa")) {
            openDeleteBill();
        } else if (str.equals("Xuất Excel")) {
            xuatExcel();
        } else if (str.equals("Xuất PDF")) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xuất PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String maHD = (String) table.getValueAt(selectedRow, 0);
            xuatPDF(maHD);
        } else if (str.equals("Reload")) {
            try {
                hoaDonBUS.docDSHD();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }
            openBillTable();
            dateChooserTuNgay.setDate(null);
            dateChooserDenNgay.setDate(null);
            txtSearch.setText("");
        } else if (str.equals("Xem chi tiết")) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maHD = (String) table.getValueAt(selectedRow, 0);
            String maKH = (String) table.getValueAt(selectedRow, 1);
            String maNV = (String) table.getValueAt(selectedRow, 2);
            Date ngayLap = (Date) table.getValueAt(selectedRow, 3);
            double tongTien = (double) table.getValueAt(selectedRow, 4);

            listTemp.clear();
            try {
                chiTietHoaDonBUS.docDSCTHD();
                for (ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
                    if (x.getMaHD().equals(maHD)) {
                        listTemp.add(x);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu từ database: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            try {
                ArrayList<ChiTietHDDTO> dsCTHD = new ArrayList<>();
                for (ChiTietHDDTO ct : chiTietHoaDonBUS.getListCTHD()) {
                    if (ct.getMaHD().equals(maHD)) {
                        dsCTHD.add(ct);
                    }
                }
                String maHTTT = dsCTHD.size() > 0 ? dsCTHD.get(0).getMaTTT() : "";
                XemCTHDialog dialog = new XemCTHDialog(maHD, maKH, maNV, maHTTT, dsCTHD, tongTien);

                dialog.setLocationRelativeTo(null);
                dialog.setModal(true);
                dialog.setVisible(true);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi mở chi tiết hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    public void setBanHangData(String maNV, ArrayList<ChiTietHDDTO> danhSachSP) {
        txtMaHD.setText(hoaDonBUS.getMaHD());
        txtMaNV.setText(maNV);
        listTemp.clear();
        soLuongTruocKhiUpdate.clear();

        for (ChiTietHDDTO dto : danhSachSP) {
            listTemp.add(dto);
            soLuongTruocKhiUpdate.add(0); // vì là mới thêm từ giỏ hàng
        }

        openBillDetailTable();
    }

    public HoaDonBUS getHoaDonBUS() {
        return this.hoaDonBUS;
    }

    public ChiTietHoaDonBUS getChiTietHoaDonBUS() {
        return this.chiTietHoaDonBUS;
    }

    public void xuatExcel() {
        try {
            ExcelExporter.exportJTableToExcel(table);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public void refreshData() {
        try {
            hoaDonBUS.docDSHD();
            openBillTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi làm mới dữ liệu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}