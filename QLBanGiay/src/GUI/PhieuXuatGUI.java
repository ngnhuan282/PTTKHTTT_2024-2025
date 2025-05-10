package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

import BUS.ChiTietPXBUS;
import BUS.LoaiBUS;
import BUS.NhanVienBUS;
import BUS.PhieuXuatBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietPXDTO;
import DTO.LoaiDTO;
import DTO.NhanVienDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import com.toedter.calendar.JDateChooser;

public class PhieuXuatGUI extends JPanel implements ActionListener {
    private JPanel contentPane;
    private String color = "#FF5252";
    public DefaultTableModel modelPhieuXuat, modelCTPX;
    private JTable tbPhieuXuat, tbCTPX;
    private JTextField txtSearch;
    private JPanel inforPanel;
    private JTextField txtMaPX, txtNV, txtSP, txtSoLuong, txtDonGia, txtGhiChu;
    private JButton btnOpenNVList, btnOpenSPList, btnDeleteProduct, btnEditProduct, btnComplete, btnAddProduct;
    private NhanVienBUS nhanVienBUS;
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private PhieuXuatBUS phieuXuatBUS = new PhieuXuatBUS();
    private ChiTietPXBUS chiTietPXBUS = new ChiTietPXBUS();
    private LoaiBUS loaiBUS = new LoaiBUS();
    private ArrayList<ChiTietPXDTO> listTemp = new ArrayList<>();
    private boolean isEditing = false;
    private boolean update = false;
    private boolean add = false;
    private JComboBox<String> cboxSearch;

    public static void main(String[] args) {
        JFrame frame = new JFrame("PhieuXuatGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new PhieuXuatGUI());
        frame.setVisible(true);
    }

    public PhieuXuatGUI() {
        try {
            nhanVienBUS = new NhanVienBUS();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initComponents();
    }

    public void initComponents() {
        setPreferredSize(new Dimension(1248, 757));
        setLayout(null);
        setBackground(Color.WHITE);

        JPanel pHeaderMain = new JPanel();
        pHeaderMain.setBounds(0, 0, 1206, 100);
        pHeaderMain.setBackground(Color.WHITE);
        pHeaderMain.setLayout(null);
        add(pHeaderMain);

        JPanel pLeftHeader = new JPanel();
        pLeftHeader.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pLeftHeader.setBackground(Color.WHITE);
        pLeftHeader.setBounds(2, 0, 489, 100);
        pHeaderMain.add(pLeftHeader);
        pLeftHeader.setLayout(null);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(UIManager.getBorder("Button.border"));
        horizontalBox.setBounds(0, 0, 567, 111);
        pLeftHeader.add(horizontalBox);

        JButton btnThem = new JButton("Thêm", new ImageIcon(PhieuXuatGUI.class.getResource("/image/add48.png")));
        btnThem.setFocusPainted(false);
        btnThem.setActionCommand("Thêm");
        btnThem.addActionListener(this);
        btnThem.setBackground(Color.WHITE);
        btnThem.setBorderPainted(false);
        btnThem.setFont(new Font("Arial", Font.PLAIN, 15));
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnThem);

        JButton btnSua = new JButton("Sửa", new ImageIcon(PhieuXuatGUI.class.getResource("/image/edit48.png")));
        btnSua.setFocusPainted(false);
        btnSua.setActionCommand("Sửa");
        btnSua.addActionListener(this);
        btnSua.setBorderPainted(false);
        btnSua.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSua.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSua.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSua.setBackground(Color.WHITE);
        btnSua.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnSua);

        JButton btnXoa = new JButton("Xóa", new ImageIcon(PhieuXuatGUI.class.getResource("/image/remove48.png")));
        btnXoa.setFocusPainted(false);
        btnXoa.setActionCommand("Xóa");
        btnXoa.addActionListener(this);
        btnXoa.setBorderPainted(false);
        btnXoa.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXoa.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXoa.setBackground(Color.WHITE);
        btnXoa.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnXoa);

        JButton btnXuatExcel = new JButton("Xuất Excel", new ImageIcon(PhieuXuatGUI.class.getResource("/image/xuatexcel48.png")));
        btnXuatExcel.setFocusPainted(false);
        btnXuatExcel.setActionCommand("Xuất Excel");
        btnXuatExcel.addActionListener(this);
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatExcel.setBackground(Color.WHITE);
        btnXuatExcel.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnXuatExcel);

        JButton btnXuatPDF = new JButton("Xuất PDF");
        btnXuatPDF.setFocusPainted(false);
        btnXuatPDF.setActionCommand("Xuất PDF");
        btnXuatPDF.addActionListener(this);
        btnXuatPDF.setBorderPainted(false);
        btnXuatPDF.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatPDF.setIcon(new ImageIcon(PhieuXuatGUI.class.getResource("/image/pdf48.png")));
        btnXuatPDF.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatPDF.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatPDF.setBackground(Color.WHITE);
        btnXuatPDF.setPreferredSize(new Dimension(120, 140));
        horizontalBox.add(btnXuatPDF);

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(PhieuXuatGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1004, 26, 128, 30);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        pHeaderMain.add(btnLamMoi);

        String[] listKeyWord = {"Mã PX", "Mã NV"};
        JButton btnSearch = new JButton("", new ImageIcon(PhieuXuatGUI.class.getResource("/image/search30.png")));
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setActionCommand("Tìm kiếm");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(931, 26, 64, 30);
        pHeaderMain.add(btnSearch);
        
                cboxSearch = new JComboBox<>(listKeyWord);
                cboxSearch.setBounds(623, 26, 79, 30);
                pHeaderMain.add(cboxSearch);
                cboxSearch.setForeground(Color.BLACK);
                cboxSearch.setFont(new Font("Arial", Font.PLAIN, 13));
                cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                cboxSearch.setBackground(Color.WHITE);
                
                        txtSearch = new JTextField();
                        txtSearch.setBounds(712, 26, 209, 30);
                        pHeaderMain.add(txtSearch);
                        txtSearch.setColumns(10);

        JPanel pContent = new JPanel();
        pContent.setBackground(SystemColor.control);
        pContent.setBounds(0, 103, 1248, 654);
        pContent.setLayout(null);
        add(pContent);

        inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setBounds(10, 11, 387, 328);
        pContent.add(inforPanel);
        inforPanel.setLayout(null);

        JLabel lbThongTin = new JLabel("Thông tin phiếu xuất");
        lbThongTin.setBounds(83, 11, 225, 25);
        lbThongTin.setFont(new Font("Tahoma", Font.BOLD, 20));
        inforPanel.add(lbThongTin);

        JLabel lbMaPX = new JLabel("Mã phiếu xuất");
        lbMaPX.setBounds(10, 55, 114, 25);
        lbMaPX.setFont(new Font("Tahoma", Font.PLAIN, 13));
        inforPanel.add(lbMaPX);

        txtMaPX = new JTextField();
        txtMaPX.setBounds(124, 55, 184, 25);
        inforPanel.add(txtMaPX);
        txtMaPX.setColumns(10);

        JLabel lbNV = new JLabel("Nhân viên xuất");
        lbNV.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbNV.setBounds(10, 91, 114, 25);
        inforPanel.add(lbNV);

        txtNV = new JTextField();
        txtNV.setColumns(10);
        txtNV.setBounds(124, 91, 184, 25);
        inforPanel.add(txtNV);
        txtNV.setEditable(false);
        txtNV.setFocusable(false);

        JLabel lbSP = new JLabel("Sản phẩm");
        lbSP.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbSP.setBounds(10, 127, 114, 25);
        inforPanel.add(lbSP);

        txtSP = new JTextField();
        txtSP.setColumns(10);
        txtSP.setBounds(124, 127, 184, 25);
        inforPanel.add(txtSP);
        txtSP.setEditable(false);
        txtSP.setFocusable(false);

        JLabel lbSoLuong = new JLabel("Số lượng");
        lbSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbSoLuong.setBounds(10, 163, 114, 25);
        inforPanel.add(lbSoLuong);

        txtSoLuong = new JTextField();
        txtSoLuong.setColumns(10);
        txtSoLuong.setBounds(124, 163, 184, 25);
        inforPanel.add(txtSoLuong);

        JLabel lbDonGia = new JLabel("Đơn giá xuất");
        lbDonGia.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbDonGia.setBounds(10, 199, 114, 25);
        inforPanel.add(lbDonGia);

        txtDonGia = new JTextField();
        txtDonGia.setColumns(10);
        txtDonGia.setBounds(124, 199, 184, 25);
        inforPanel.add(txtDonGia);

        JLabel lbGhiChu = new JLabel("Ghi chú");
        lbGhiChu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbGhiChu.setBounds(10, 235, 114, 25);
        inforPanel.add(lbGhiChu);

        txtGhiChu = new JTextField();
        txtGhiChu.setColumns(10);
        txtGhiChu.setBounds(124, 235, 184, 25);
        inforPanel.add(txtGhiChu);

        JButton btnCancel = new JButton("");
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setIcon(new ImageIcon(PhieuXuatGUI.class.getResource("/image/icons8-cancel-20.png")));
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(20, 11, 32, 23);
        inforPanel.add(btnCancel);

        btnComplete = new JButton("Hoàn tất");
        btnComplete.setBounds(292, 271, 85, 33);
        inforPanel.add(btnComplete);
        btnComplete.addActionListener(e -> complete());
        btnComplete.setVisible(false);

        btnOpenNVList = new JButton("...");
        btnOpenNVList.setBounds(318, 92, 32, 23);
        inforPanel.add(btnOpenNVList);
        btnOpenNVList.addActionListener(e -> openNVList());

        btnOpenSPList = new JButton("...");
        btnOpenSPList.setBounds(318, 129, 32, 23);
        inforPanel.add(btnOpenSPList);
        btnOpenSPList.addActionListener(e -> openSPList());

        btnEditProduct = new JButton("Sửa SP");
        btnEditProduct.setBounds(102, 271, 85, 33);
        inforPanel.add(btnEditProduct);
        btnEditProduct.addActionListener(e -> editProduct());
        btnEditProduct.setVisible(false);

        btnDeleteProduct = new JButton("Xóa SP");
        btnDeleteProduct.setBounds(7, 271, 85, 33);
        inforPanel.add(btnDeleteProduct);
        btnDeleteProduct.addActionListener(e -> deleteProduct());
        btnDeleteProduct.setVisible(false);

        btnAddProduct = new JButton("Thêm SP");
        btnAddProduct.setBounds(197, 271, 85, 33);
        inforPanel.add(btnAddProduct);
        btnAddProduct.addActionListener(e -> addProduct());
        btnAddProduct.setVisible(false);

        JPanel phieuXuatPanel = new JPanel();
        phieuXuatPanel.setBackground(Color.WHITE);
        phieuXuatPanel.setBounds(407, 11, 831, 328);
        pContent.add(phieuXuatPanel);
        phieuXuatPanel.setLayout(null);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBounds(10, 351, 1228, 292);
        pContent.add(detailsPanel);
        detailsPanel.setLayout(null);

        String[] columns = {"Mã phiếu xuất", "Mã nhân viên", "Tổng tiền", "Ngày xuất", "Ghi chú"};
        modelPhieuXuat = new DefaultTableModel(columns, 0);

        tbPhieuXuat = new JTable(modelPhieuXuat);
        tbPhieuXuat.setFont(new Font("Verdana", Font.PLAIN, 12));
        tbPhieuXuat.setGridColor(new Color(200, 200, 200));
        tbPhieuXuat.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tbPhieuXuat.setRowHeight(23);
        tbPhieuXuat.getTableHeader().setPreferredSize(new Dimension(0, 25));
        tbPhieuXuat.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));

        tbPhieuXuat.getSelectionModel().addListSelectionListener(e -> getSelectedRowTbPhieuXuat());

        JScrollPane scrollPanePhieuXuat = new JScrollPane(tbPhieuXuat);
        scrollPanePhieuXuat.setBounds(0, 0, 831, 328);
        scrollPanePhieuXuat.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220)));
        scrollPanePhieuXuat.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPanePhieuXuat.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        phieuXuatPanel.add(scrollPanePhieuXuat);

        phieuXuatBUS.docDSPX();
        updateTablePX();

        String[] columns2 = {"Mã phiếu xuất", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        modelCTPX = new DefaultTableModel(columns2, 0);

        tbCTPX = new JTable(modelCTPX);
        tbCTPX.setFont(new Font("Verdana", Font.PLAIN, 12));
        tbCTPX.setGridColor(new Color(200, 200, 200));
        tbCTPX.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tbCTPX.setRowHeight(23);
        tbCTPX.getTableHeader().setPreferredSize(new Dimension(0, 23));
        tbCTPX.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));

        tbCTPX.getSelectionModel().addListSelectionListener(e -> getInforFromTbCTPX());

        JScrollPane scrollPaneCTPX = new JScrollPane(tbCTPX);
        scrollPaneCTPX.setBounds(0, 0, 1228, 292);
        scrollPaneCTPX.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220)));
        scrollPaneCTPX.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPaneCTPX.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        detailsPanel.add(scrollPaneCTPX);

        chiTietPXBUS.docDSCTPX();
        sanPhamBUS.docDSSP();
    }

    public void openNVList() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Nhân viên");
        dialog.setSize(650, 300);
        dialog.getContentPane().setLayout(new BorderLayout(5, 5));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        JTextField txtSearchNV = new JTextField();
        txtSearchNV.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(txtSearchNV, BorderLayout.CENTER);
        dialog.getContentPane().add(searchPanel, BorderLayout.NORTH);

        Object[] columns = {"Mã NV", "Họ NV", "Tên NV", "Số Điện Thoại"};
        JTable tbNV = new JTable();
        tbNV.setFont(new Font("Arial", Font.PLAIN, 12));
        tbNV.setRowHeight(23);
        tbNV.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane jScrollPaneNV = new JScrollPane(tbNV);
        DefaultTableModel modelNV = new DefaultTableModel(columns, 0);
        for (NhanVienDTO nv : nhanVienBUS.getListNhanVien())
            modelNV.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
        tbNV.setModel(modelNV);

        dialog.getContentPane().add(jScrollPaneNV);
        dialog.setBounds(0, 300, 650, 250);
        dialog.setVisible(true);

        txtSearchNV.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchNV.getText().trim().toLowerCase();
                modelNV.setRowCount(0);
                for (NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
                    if (nv.getMaNV().toLowerCase().contains(keyword))
                        modelNV.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
                }
            }
        });

        tbNV.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tbNV.getSelectedRow();
            if (selectedRow != -1) {
                String maNV = (String) tbNV.getValueAt(selectedRow, 0);
                txtNV.setText(maNV);
                dialog.dispose();
            }
        });
    }

    public void openSPList() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Sản Phẩm");
        dialog.setSize(750, 300);
        dialog.getContentPane().setLayout(new BorderLayout(5, 5));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        JTextField txtSearchSP = new JTextField();
        txtSearchSP.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(txtSearchSP, BorderLayout.CENTER);
        dialog.getContentPane().add(searchPanel, BorderLayout.NORTH);

        Object[] columns = {"Mã SP", "Tên SP", "Loại", "Giá", "Số lượng", "ĐVT", "Màu sắc", "Kích thước", "Chất liệu", "Kiểu dáng"};
        JTable tbSP = new JTable();
        tbSP.setFont(new Font("Arial", Font.PLAIN, 12));
        tbSP.setRowHeight(23);
        tbSP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane jScrollPaneSP = new JScrollPane(tbSP);

        DefaultTableModel modelSP = new DefaultTableModel(columns, 0);
        for (SanPhamDTO sp : sanPhamBUS.getDssp()) {
            String tenLoaiSP = "";
            for (LoaiDTO loai : loaiBUS.getDsloai()) {
                if (loai.getMaLoaiSP() == sp.getMaLoaiSP()) {
                    tenLoaiSP = loai.getTenLoaiSP();
                    break;
                }
            }
            modelSP.addRow(new Object[]{
                    sp.getMaSP(), sp.getTenSP(), tenLoaiSP, sp.getDonGia(), sp.getSoLuong(),
                    sp.getDonViTinh(), sp.getMauSac(), sp.getKichThuoc(), sp.getChatLieu(),
                    sp.getKieuDang()
            });
        }

        tbSP.setModel(modelSP);
        dialog.getContentPane().add(jScrollPaneSP);
        dialog.setBounds(0, 330, 750, 250);
        dialog.setVisible(true);

        txtSearchSP.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtSearchSP.getText().trim().toLowerCase();
                modelSP.setRowCount(0);
                for (SanPhamDTO sp : sanPhamBUS.getDssp()) {
                    String tenLoaiSP = "";
                    for (LoaiDTO loai : loaiBUS.getDsloai()) {
                        if (loai.getMaLoaiSP() == sp.getMaLoaiSP()) {
                            tenLoaiSP = loai.getTenLoaiSP();
                            break;
                        }
                    }
                    if (sp.getMaSP().toLowerCase().contains(keyword)) {
                        modelSP.addRow(new Object[]{
                                sp.getMaSP(), sp.getTenSP(), tenLoaiSP, sp.getDonGia(), sp.getSoLuong(),
                                sp.getDonViTinh(), sp.getMauSac(), sp.getKichThuoc(), sp.getChatLieu(),
                                sp.getKieuDang()
                        });
                    }
                }
            }
        });

        tbSP.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tbSP.getSelectedRow();
            if (selectedRow != -1) {
                String maSP = (String) tbSP.getValueAt(selectedRow, 0);
                txtSP.setText(maSP);
                dialog.dispose();
            }
        });
    }

    public void addCTPX() {
        clear();
        txtMaPX.setText(phieuXuatBUS.generateMaPX());
        btnAddProduct.setVisible(true);
        btnEditProduct.setVisible(true);
        btnDeleteProduct.setVisible(true);
        btnComplete.setVisible(true);
        add = true;
    }

    public void editProduct() {
        int selectedRow = tbCTPX.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maPX = txtMaPX.getText().trim();
        String SP = txtSP.getText().trim();
        int soLuong;
        double donGia;

        if (SP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSP.requestFocus();
            return;
        }
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            donGia = Double.parseDouble(txtDonGia.getText().trim());
            if (soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSoLuong.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }

        for (int i = 0; i < listTemp.size(); i++) {
            if (i != selectedRow && listTemp.get(i).getMaSP().equals(SP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm " + SP + " đã tồn tại! Vui lòng chọn mã sản phẩm khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtSP.requestFocus();
                return;
            }
        }

        double thanhTien = soLuong * donGia;
        listTemp.set(selectedRow, new ChiTietPXDTO(maPX, SP, soLuong, donGia, thanhTien));

        modelCTPX.setRowCount(0);
        for (ChiTietPXDTO ctpx : listTemp) {
            modelCTPX.addRow(new Object[]{ctpx.getMaPX(), ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien()});
        }

        JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        txtSP.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }

    public void deleteProduct() {
        int selectedRow = tbCTPX.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa chi tiết này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        listTemp.remove(selectedRow);

        modelCTPX.setRowCount(0);
        for (ChiTietPXDTO ctpx : listTemp) {
            modelCTPX.addRow(new Object[]{ctpx.getMaPX(), ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien()});
        }

        txtSP.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
    }

    public void editPX() {
        btnOpenNVList.setVisible(true);
        btnOpenSPList.setVisible(true);
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtSoLuong.setEditable(true);
        txtSoLuong.setFocusable(true);
        txtDonGia.setEditable(true);
        txtDonGia.setFocusable(true);
        txtGhiChu.setEditable(true);
        txtGhiChu.setFocusable(true);

        update = true;
        int selectedRowPX = tbPhieuXuat.getSelectedRow();
        if (selectedRowPX == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất ở bảng phiếu xuất để tiến hành sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maPX = (String) modelPhieuXuat.getValueAt(selectedRowPX, 0);
        String NV = (String) modelPhieuXuat.getValueAt(selectedRowPX, 1);
        String ghiChu = (String) modelPhieuXuat.getValueAt(selectedRowPX, 4);

        txtMaPX.setText(maPX);
        txtMaPX.setForeground(new Color(155, 155, 155));
        txtNV.setText(NV);
        txtGhiChu.setText(ghiChu);

        txtMaPX.setEditable(false);
        txtMaPX.setFocusable(false);

        listTemp.clear();
        modelCTPX.setRowCount(0);
        listTemp.addAll(chiTietPXBUS.getChiTietTheoMaPhieu(maPX));
        for (ChiTietPXDTO ctpx : listTemp) {
            modelCTPX.addRow(new Object[]{ctpx.getMaPX(), ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien()});
        }

        btnAddProduct.setVisible(true);
        btnEditProduct.setVisible(true);
        btnDeleteProduct.setVisible(true);
        btnComplete.setVisible(true);
    }

    public void addProduct() {
        String maPX = txtMaPX.getText().trim();
        String NV = txtNV.getText().trim();
        String SP = txtSP.getText().trim();

        if (maPX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPX.requestFocus();
            return;
        }
        if (!update) {
            if (phieuXuatBUS.checkMaPX(maPX)) {
                JOptionPane.showMessageDialog(this, "Mã phiếu xuất " + maPX + " đã tồn tại! Vui lòng chọn mã khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMaPX.requestFocus();
                return;
            }
        }

        if (NV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNV.requestFocus();
            return;
        }
        if (SP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSP.requestFocus();
            return;
        }

        int soLuong;
        double donGia;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            donGia = Double.parseDouble(txtDonGia.getText().trim());
            if (soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (ChiTietPXDTO ctpx : listTemp) {
            if (ctpx.getMaSP().equals(SP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm " + SP + " đã tồn tại! Vui lòng chọn dòng và nhấn Sửa để cập nhật số lượng hoặc đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        double thanhTien = soLuong * donGia;
        listTemp.add(new ChiTietPXDTO(maPX, SP, soLuong, donGia, thanhTien));
        modelCTPX.setRowCount(0);
        for (ChiTietPXDTO ctpx : listTemp) {
            modelCTPX.addRow(new Object[]{ctpx.getMaPX(), ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien()});
        }

        txtMaPX.setEditable(false);
        txtMaPX.setFocusable(false);
        txtMaPX.setForeground(new Color(155, 155, 155));
        txtSP.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");

        JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào chi tiết phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void complete() {
        String maPX = txtMaPX.getText().trim();
        String NV = txtNV.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();

        if (maPX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPX.requestFocus();
            return;
        }
        if (NV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNV.requestFocus();
            return;
        }
        if (listTemp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết phiếu xuất!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSP.requestFocus();
            return;
        }

        // Kiểm tra số lượng tồn kho trước khi xuất
        ArrayList<ChiTietPXDTO> oldListCTPX = update ? chiTietPXBUS.getChiTietTheoMaPhieu(maPX) : new ArrayList<>();
        for (ChiTietPXDTO ctpx : listTemp) {
            SanPhamDTO sp = sanPhamBUS.getSanPhamByMaSP(ctpx.getMaSP());
            int soLuongTon = sp.getSoLuong();
            // Nếu sửa phiếu xuất, cộng lại số lượng cũ để tính tồn kho thực tế
            if (update) {
                for (ChiTietPXDTO oldCTPX : oldListCTPX) {
                    if (oldCTPX.getMaSP().equals(ctpx.getMaSP())) {
                        soLuongTon += oldCTPX.getSoLuong();
                        break;
                    }
                }
            }
            if (soLuongTon < ctpx.getSoLuong()) {
                JOptionPane.showMessageDialog(this, "Sản phẩm " + ctpx.getMaSP() + " không đủ số lượng tồn kho! Tồn kho: " + soLuongTon + ", Yêu cầu: " + ctpx.getSoLuong(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

       
        double tongTien = 0;
        for (ChiTietPXDTO ctpx : listTemp) {
            tongTien += ctpx.getThanhTien();
        }

        LocalDate now = LocalDate.now();
        Date ngayXuat = Date.valueOf(now);

        try {
            if (update) {
                
                for (ChiTietPXDTO oldCTPX : oldListCTPX) {
                    sanPhamBUS.updateSoLuongPX(oldCTPX.getMaSP(), oldCTPX.getSoLuong());
                }
             
                for (ChiTietPXDTO newCTPX : listTemp) {
                    sanPhamBUS.updateSoLuongPX(newCTPX.getMaSP(), -newCTPX.getSoLuong());
                }

               
                phieuXuatBUS.updatePX(maPX, NV, tongTien, ngayXuat, ghiChu);
                chiTietPXBUS.updateCTPX(maPX, listTemp);

              
                int selectedRow = tbPhieuXuat.getSelectedRow();
                if (selectedRow != -1) {
                    modelPhieuXuat.setValueAt(NV, selectedRow, 1);
                    modelPhieuXuat.setValueAt(tongTien, selectedRow, 2);
                    modelPhieuXuat.setValueAt(ngayXuat, selectedRow, 3);
                    modelPhieuXuat.setValueAt(ghiChu, selectedRow, 4);
                }

                JOptionPane.showMessageDialog(this, "Sửa phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
              
                if (phieuXuatBUS.checkMaPX(maPX)) {
                    JOptionPane.showMessageDialog(this, "Mã phiếu xuất " + maPX + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    txtMaPX.requestFocus();
                    return;
                }
                phieuXuatBUS.addPX(maPX, NV, tongTien, ngayXuat, ghiChu);
                for (ChiTietPXDTO ctpx : listTemp) {
                    chiTietPXBUS.addCTPX(maPX, ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien());
                }
               
                for (ChiTietPXDTO ctpx : listTemp) {
                    sanPhamBUS.updateSoLuongPX(ctpx.getMaSP(), -ctpx.getSoLuong());
                }

                modelPhieuXuat.addRow(new Object[]{maPX, NV, tongTien, ngayXuat, ghiChu});

                JOptionPane.showMessageDialog(this, "Thêm phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

            clear();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }	
    }

    public void deletePX() {
        int selectedRow = tbPhieuXuat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tbPhieuXuat.requestFocus();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu xuất này? Tất cả chi tiết phiếu xuất cũng sẽ bị xóa.", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        String maPX = (String) modelPhieuXuat.getValueAt(selectedRow, 0);
        ArrayList<ChiTietPXDTO> list = chiTietPXBUS.getChiTietTheoMaPhieu(maPX);
        for (ChiTietPXDTO ctpx : list) {
            sanPhamBUS.updateSoLuong(ctpx.getMaSP(), ctpx.getSoLuong());
        }
        chiTietPXBUS.deleteCTPX(maPX);
        phieuXuatBUS.deletePX(maPX);
        modelPhieuXuat.removeRow(selectedRow);
        clear();
        JOptionPane.showMessageDialog(this, "Xóa phiếu xuất thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public DefaultTableModel updateTablePX() {
        txtSearch.setText("");

        modelPhieuXuat.setRowCount(0);
        for (PhieuXuatDTO px : phieuXuatBUS.getListPX()) {
            modelPhieuXuat.addRow(new Object[]{px.getMaPX(), px.getMaNV(), px.getTongTien(), px.getNgayXuat(), px.getGhiChu()});
        }
        return modelPhieuXuat;
    }

    public void loadChiTietPX(String maPX) {
        ArrayList<ChiTietPXDTO> dsCTPX = chiTietPXBUS.getChiTietTheoMaPhieu(maPX);
        modelCTPX.setRowCount(0);
        for (ChiTietPXDTO ctpx : dsCTPX) {
            modelCTPX.addRow(new Object[]{ctpx.getMaPX(), ctpx.getMaSP(), ctpx.getSoLuong(), ctpx.getDonGia(), ctpx.getThanhTien()});
        }
    }

    public void getSelectedRowTbPhieuXuat() {
        int selectedRow = tbPhieuXuat.getSelectedRow();
        if (selectedRow != -1 && !update && !add) {
            loadChiTietPX((String) modelPhieuXuat.getValueAt(selectedRow, 0));
        }
    }

    public void getInforFromTbCTPX() {
        int selectedRowPX = tbPhieuXuat.getSelectedRow();
        int selectedRowCTPX = tbCTPX.getSelectedRow();
        if (selectedRowCTPX != -1 && selectedRowPX != -1 && listTemp.isEmpty() && !update && !add) {
            String maPX = (String) tbPhieuXuat.getValueAt(selectedRowPX, 0);
            String NV = (String) tbPhieuXuat.getValueAt(selectedRowPX, 1);
            String ghiChu = (String) tbPhieuXuat.getValueAt(selectedRowPX, 4);
            String SP = (String) tbCTPX.getValueAt(selectedRowCTPX, 1);
            int soLuong = (int) tbCTPX.getValueAt(selectedRowCTPX, 2);
            double donGia = (double) tbCTPX.getValueAt(selectedRowCTPX, 3);

            txtMaPX.setText(maPX);
            txtNV.setText(NV);
            txtGhiChu.setText(ghiChu);
            txtSP.setText(SP);
            txtSoLuong.setText(String.valueOf(soLuong));
            txtDonGia.setText(String.valueOf(donGia));

            txtMaPX.setEditable(false);
            txtMaPX.setFocusable(false);
            txtSoLuong.setEditable(false);
            txtSoLuong.setFocusable(false);
            txtDonGia.setEditable(false);
            txtDonGia.setFocusable(false);
            txtGhiChu.setEditable(false);
            txtGhiChu.setFocusable(false);

            btnOpenNVList.setVisible(false);
            btnOpenSPList.setVisible(false);
            btnComplete.setVisible(false);
        }
    }

    public void timKiem() {
        String tieuChi = cboxSearch.getSelectedItem().toString();
        String tuKhoa = txtSearch.getText().trim();

        ArrayList<PhieuXuatDTO> result;


        boolean isTimKiemCoBan = !tuKhoa.isEmpty();



        int count = 0;
        if (isTimKiemCoBan) count++;


        if (count > 1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chỉ chọn một loại tìm kiếm: cơ bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isTimKiemCoBan) {
            result = phieuXuatBUS.search(tuKhoa, tieuChi);
        } 
        else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin để tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelPhieuXuat.setRowCount(0);
        for (PhieuXuatDTO px : result) {
            modelPhieuXuat.addRow(new Object[]{px.getMaPX(), px.getMaNV(), px.getTongTien(), px.getNgayXuat(), px.getGhiChu()});
        }
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void clear() {
        txtMaPX.setText("");
        txtNV.setText("");
        txtSP.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtGhiChu.setText("");

        tbPhieuXuat.clearSelection();
        tbCTPX.clearSelection();

        modelCTPX.setRowCount(0);

        txtMaPX.setEditable(true);
        txtMaPX.setFocusable(true);
        txtSoLuong.setEditable(true);
        txtSoLuong.setFocusable(true);
        txtDonGia.setEditable(true);
        txtDonGia.setFocusable(true);
        txtGhiChu.setEditable(true);
        txtGhiChu.setFocusable(true);

        btnOpenNVList.setVisible(true);
        btnOpenSPList.setVisible(true);

        btnAddProduct.setVisible(false);
        btnEditProduct.setVisible(false);
        btnDeleteProduct.setVisible(false);
        btnComplete.setVisible(false);
        update = false;
        add = false;

        txtMaPX.setForeground(new Color(60, 60, 60));

        listTemp.clear();
    }

    public void xuatExcel() {
        try {
            ExcelExporter.exportJTableToExcel(tbPhieuXuat);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void xuatPDF() {
        int selectedRow = tbPhieuXuat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xuất PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            tbPhieuXuat.requestFocus();
            return;
        }

        String maPX = (String) modelPhieuXuat.getValueAt(selectedRow, 0);
        try {
            PDFReporter pdfReporter = new PDFReporter();
            pdfReporter.writePhieuXuat(maPX);
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Cancel":
                clear();
                break;
            case "Thêm":
                addCTPX();
                break;
            case "Sửa":
                editPX();
                break;
            case "Xóa":
                deletePX();
                break;
            case "Reload":
                updateTablePX();
                break;
            case "Tìm kiếm":
                timKiem();
                break;
            case "Xuất Excel":
                xuatExcel();
                break;
            case "Xuất PDF":
                xuatPDF();
                break;
        }
    }
}