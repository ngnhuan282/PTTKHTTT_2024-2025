package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.ChucVuBUS;
import BUS.NhanVienBUS;
import BUS.QuyenBUS;
import BUS.TaiKhoanBUS;
import DAO.SanPhamDAO;
import DTO.ChucVuDTO;
import DTO.NhanVienDTO;
import DTO.QuyenDTO;
import DTO.TaiKhoanDTO;
import GUI.ExcelExporter;

public class NhanVienGUI extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JButton btnLamMoi;
    private JButton btnPhanQuyen;
    private JTable tblDSNV;
    private JTextField txtMaNV, txtHoNV, txtTenNV, txtSDT, txtLuong, txtSearch;
    private JComboBox<String> cboxSearch;
    JComboBox<String> cboxChucVu;
    private JButton btnEditMode, btnNhapExcel, btnXuatExcel;
    private NhanVienBUS nvBUS;
    private TaiKhoanBUS tkBUS;
    private ChucVuBUS chucVuBUS;
    private QuyenBUS quyenBUS;
    private String[] chucVu;
    private boolean isEditMode = false;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private int currentMaTK;
    private int currentMaChucVu;
    private MainGUI mainGUI;

    public NhanVienGUI(int currentMaTK, MainGUI mainGUI) throws SQLException {
    	
    	this.mainGUI = mainGUI;
        nvBUS = new NhanVienBUS();
        tkBUS = new TaiKhoanBUS();
        chucVuBUS = new ChucVuBUS();
        quyenBUS = new QuyenBUS();
        this.currentMaTK = currentMaTK; 
        currentMaChucVu = chucVuBUS.getCurrentMaChucVu(this.currentMaTK);
        Object[] header = {"Mã NV", "Họ NV", "Tên NV", "Số Điện Thoại", "Lương Tháng", "Chức vụ","Username", "Password"};
        
        model = new DefaultTableModel(header, 0);
        tblDSNV = new JTable(model);
        
        cboxChucVu = new JComboBox<String>();
        cboxChucVu.addItem("Chọn chức vụ");
        for(ChucVuDTO x : chucVuBUS.getListChucVu()) {
        	cboxChucVu.addItem(x.getTenChucVu());
        }
        
        initComponents();
        fillTableWithSampleData();
    }

    private void fillTableWithSampleData() {
    	btnLamMoi.setVisible(false);
        model.setRowCount(0);
        ArrayList<NhanVienDTO> dsNV = nvBUS.getListNhanVien();
        for (NhanVienDTO nv : dsNV) {
        	TaiKhoanDTO tk = tkBUS.getAccount(nv.getMaNV());
        	ChucVuDTO cv = chucVuBUS.getChucVu(nv.getMaNV());
            model.addRow(new Object[]{
                nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt(), nv.getLuong(), cv.getTenChucVu(), tk.getTenDangNhap(), tk.getMatKhau()
            });
        }
        tblDSNV.setModel(model);
        tblDSNV.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblDSNV.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblDSNV.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblDSNV.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblDSNV.getColumnModel().getColumn(4).setPreferredWidth(200);
    }

    public void initComponents() {
        setPreferredSize(new Dimension(1248, 757));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel pMain = new JPanel();
        pMain.setPreferredSize(new Dimension(1248, 757));
        pMain.setLayout(null);
        add(pMain);

        // Header
        JPanel pHeaderMain = new JPanel();
        pHeaderMain.setBackground(Color.WHITE);
        pHeaderMain.setBounds(0, 0, 1248, 100);
        pMain.add(pHeaderMain);
        pHeaderMain.setLayout(null);

        JPanel pLeftHeader = new JPanel();
        pLeftHeader.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pLeftHeader.setBackground(Color.WHITE);
        pLeftHeader.setBounds(2, 0, 512, 100);
        pHeaderMain.add(pLeftHeader);
        pLeftHeader.setLayout(null);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(UIManager.getBorder("Button.border"));
        horizontalBox.setBounds(0, 0, 512, 111);
        pLeftHeader.add(horizontalBox);

        JButton btnThem = new JButton("Thêm");
        btnThem.setFocusPainted(false);
        btnThem.setActionCommand("Thêm");
        btnThem.addActionListener(this);
        btnThem.setBackground(Color.WHITE);
        btnThem.setBorderPainted(false);
        btnThem.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/add48.png")));
        btnThem.setFont(new Font("Arial", Font.PLAIN, 15));
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setFocusPainted(false);
        btnSua.setActionCommand("Sửa");
        btnSua.addActionListener(this);
        btnSua.setBorderPainted(false);
        btnSua.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSua.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit48.png")));
        btnSua.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSua.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSua.setBackground(Color.WHITE);
        btnSua.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFocusPainted(false);
        btnXoa.setActionCommand("Xóa");
        btnXoa.addActionListener(this);
        btnXoa.setBorderPainted(false);
        btnXoa.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXoa.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/remove48.png")));
        btnXoa.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXoa.setBackground(Color.WHITE);
        btnXoa.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnXoa);

        btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.setFocusPainted(false);
        btnNhapExcel.setActionCommand("Nhập Excel");
        btnNhapExcel.addActionListener(this);
        btnNhapExcel.setBorderPainted(false);
        btnNhapExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNhapExcel.setIcon(new ImageIcon(NhanVienGUI.class.getResource("/image/excel48.png")));
        btnNhapExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNhapExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNhapExcel.setBackground(Color.WHITE);
        btnNhapExcel.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnNhapExcel);

        btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFocusPainted(false);
        btnXuatExcel.setActionCommand("Xuất Excel");
        btnXuatExcel.addActionListener(this);
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatExcel.setIcon(new ImageIcon(NhanVienGUI.class.getResource("/image/xuatexcel48.png")));
        btnXuatExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatExcel.setBackground(Color.WHITE);
        btnXuatExcel.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnXuatExcel);
        
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1045, 31, 126, 28);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(e -> fillTableWithSampleData());
        btnLamMoi.setVisible(false);
        pHeaderMain.add(btnLamMoi);
        
        
        btnPhanQuyen = new JButton("Phân quyền");
        btnPhanQuyen.setBackground(Color.WHITE);
        btnPhanQuyen.setFont(new Font("Arial", Font.PLAIN, 13));
        btnPhanQuyen.setBounds(1045, 31, 126, 28);
        btnPhanQuyen.setActionCommand("Reload");
        btnPhanQuyen.addActionListener(e -> phanQuyenDialog());
        btnPhanQuyen.setVisible(false);
        pHeaderMain.add(btnPhanQuyen);
        
        String[] listKeyWord = {"Mã NV", "Tên NV"};
        cboxSearch = new JComboBox<String>(listKeyWord);
        cboxSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        cboxSearch.setBounds(569, 31, 79, 28);
        cboxSearch.setBackground(Color.WHITE);
        cboxSearch.setForeground(Color.BLACK);
        cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pHeaderMain.add(cboxSearch);

        txtSearch = new JTextField();
        txtSearch.setColumns(10);
        txtSearch.setBounds(658, 32, 290, 27);
        pHeaderMain.add(txtSearch);

        JButton btnSearch = new JButton("", new ImageIcon(SanPhamGUI.class.getResource("/image/search30.png")));
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setActionCommand("Tìm kiếm");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(958, 29, 66, 30);
        pHeaderMain.add(btnSearch);

        // Input Form
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 110, 265, 655);
        pMain.add(panel);
        panel.setLayout(null);

        JLabel lbMaNV = new JLabel("Mã nhân viên");
        lbMaNV.setFont(new Font("Verdana", Font.BOLD, 12));
        lbMaNV.setBounds(10, 30, 100, 23);
        panel.add(lbMaNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(10, 52, 245, 32);
        txtMaNV.setColumns(10);
        txtMaNV.setEditable(false);
        txtMaNV.setFocusable(false);
        panel.add(txtMaNV);

        JLabel lbHoNV = new JLabel("Họ nhân viên");
        lbHoNV.setFont(new Font("Verdana", Font.BOLD, 12));
        lbHoNV.setBounds(10, 94, 100, 23);
        panel.add(lbHoNV);

        txtHoNV = new JTextField();
        txtHoNV.setColumns(10);
        txtHoNV.setBounds(10, 120, 245, 32);
        txtHoNV.setEditable(false);
        txtHoNV.setFocusable(false);
        panel.add(txtHoNV);

        JLabel lbTenNV = new JLabel("Tên nhân viên");
        lbTenNV.setFont(new Font("Verdana", Font.BOLD, 12));
        lbTenNV.setBounds(10, 162, 100, 23);
        panel.add(lbTenNV);

        txtTenNV = new JTextField();
        txtTenNV.setColumns(10);
        txtTenNV.setBounds(10, 184, 245, 32);
        txtTenNV.setEditable(false);
        txtTenNV.setFocusable(false);
        panel.add(txtTenNV);

        JLabel lbSDT = new JLabel("Số điện thoại");
        lbSDT.setFont(new Font("Verdana", Font.BOLD, 12));
        lbSDT.setBounds(10, 226, 100, 23);
        panel.add(lbSDT);

        txtSDT = new JTextField();
        txtSDT.setColumns(10);
        txtSDT.setBounds(10, 259, 245, 32);
        txtSDT.setEditable(false);
        txtSDT.setFocusable(false);
        panel.add(txtSDT);

        JLabel lbLuong = new JLabel("Lương tháng");
        lbLuong.setFont(new Font("Verdana", Font.BOLD, 12));
        lbLuong.setBounds(10, 301, 122, 23);
        panel.add(lbLuong);

        txtLuong = new JTextField();
        txtLuong.setColumns(10);
        txtLuong.setBounds(10, 334, 245, 32);
        txtLuong.setEditable(false);
        txtLuong.setFocusable(false);
        panel.add(txtLuong);

        btnEditMode = new JButton("");
        btnEditMode.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit20.png")));
        btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.setBounds(215, 15, 37, 20);
        btnEditMode.addActionListener(e -> toggleEditMode());
        panel.add(btnEditMode);
        
        txtUsername = new JTextField();
        txtUsername.setFocusable(false);
        txtUsername.setEditable(false);
        txtUsername.setColumns(10);
        txtUsername.setBounds(10, 479, 245, 32);
        panel.add(txtUsername);
        
        JLabel lbUsername = new JLabel("Tên đăng nhập");
        lbUsername.setFont(new Font("Verdana", Font.BOLD, 12));
        lbUsername.setBounds(10, 446, 122, 23);
        panel.add(lbUsername);
        
        txtPassword = new JTextField();
        txtPassword.setFocusable(false);
        txtPassword.setEditable(false);
        txtPassword.setColumns(10);
        txtPassword.setBounds(10, 546, 245, 32);
        panel.add(txtPassword);
        
        JLabel lbPassword = new JLabel("Mật khẩu");
        lbPassword.setFont(new Font("Verdana", Font.BOLD, 12));
        lbPassword.setBounds(10, 521, 122, 23);
        panel.add(lbPassword);
        
        JLabel lbUsername_1 = new JLabel("Chức Vụ");
        lbUsername_1.setFont(new Font("Verdana", Font.BOLD, 12));
        lbUsername_1.setBounds(10, 376, 122, 23);
        panel.add(lbUsername_1);
        
        cboxChucVu.setForeground(Color.BLACK);
        cboxChucVu.setFont(new Font("Arial", Font.PLAIN, 14));
        cboxChucVu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cboxChucVu.setBackground(Color.WHITE);
        cboxChucVu.setBounds(10, 405, 245, 28);
        panel.add(cboxChucVu);

        // Table
        tblDSNV.setFont(new Font("Verdana", Font.PLAIN, 12));
        tblDSNV.setGridColor(new Color(200, 200, 200));
        tblDSNV.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        tblDSNV.setRowHeight(23);
        tblDSNV.getTableHeader().setPreferredSize(new Dimension(0, 23));
        tblDSNV.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblDSNV.setDefaultRenderer(Object.class, centerRenderer);
        JScrollPane scrollPane = new JScrollPane(tblDSNV);
        scrollPane.setBounds(265, 110, 965, 655);
        pMain.add(scrollPane);

        tblDSNV.getSelectionModel().addListSelectionListener(e -> getInforFromTable());
    }
    
    public void phanQuyenDialog() {
    	 		int selectedRow = tblDSNV.getSelectedRow();
    	 		String tenDangNhap = tblDSNV.getValueAt(selectedRow, 6).toString();
    	 		String matKhau = tblDSNV.getValueAt(selectedRow, 7).toString();
    	 		int maTK = tkBUS.getMaTK(tenDangNhap, matKhau);
    			
    	        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "🔐 Phân Quyền Nhân Viên", Dialog.ModalityType.APPLICATION_MODAL);
    	        dialog.setSize(400, 350);
    	        dialog.setLocationRelativeTo(this);
    	        dialog.getContentPane().setLayout(new BorderLayout(10, 10));

    	        JLabel lblTitle = new JLabel("Chọn các quyền cho nhân viên", SwingConstants.CENTER);
    	        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    	        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	        dialog.getContentPane().add(lblTitle, BorderLayout.NORTH);

    	        JPanel panelCheck = new JPanel(new GridLayout(0, 1, 5, 5));
    	        panelCheck.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    	        Map<Integer, JCheckBox> mapCheckBox = new LinkedHashMap<>();

    	        for (QuyenDTO x : quyenBUS.getListQuyen()) {
    	            JCheckBox cb = new JCheckBox(x.getTenQuyen());
    	            cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    	            cb.setBackground(new Color(245, 245, 245));
    	            mapCheckBox.put(x.getMaQuyen(), cb);
    	            panelCheck.add(cb);
    	            if(quyenBUS.checkQuyen(maTK, x.getMaQuyen()))
    	            	cb.setSelected(true);
    	        }

    	        JScrollPane scrollPane = new JScrollPane(panelCheck);
    	        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    	        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

    	        // Panel chứa nút
    	        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
    	        panelButtons.setBackground(Color.WHITE);
    	        JButton btnLuu = new JButton("💾 Lưu");
    	        btnLuu.setActionCommand("Lưu");
    	        JButton btnHuy = new JButton("❌ Hủy");

    	        btnLuu.setFocusPainted(false);
    	        btnHuy.setFocusPainted(false);
    	        btnLuu.setBackground(new Color(59, 130, 246));
    	        btnLuu.setForeground(Color.WHITE);
    	        btnHuy.setBackground(new Color(220, 53, 69));
    	        btnHuy.setForeground(Color.WHITE);

    	        btnLuu.setPreferredSize(new Dimension(80, 30));
    	        btnHuy.setPreferredSize(new Dimension(80, 30));

    	        List<Integer> quyenDaChon = new ArrayList<>();
    	        

    	    

    	        btnLuu.addActionListener(e -> {
    	            for (Map.Entry<Integer, JCheckBox> entry : mapCheckBox.entrySet()) {
    	                if (entry.getValue().isSelected()) {
    	                    quyenDaChon.add(entry.getKey());
    	                }
    	            }
    	            
    	            quyenBUS.phanQuyenNV(maTK, quyenDaChon);
    	            dialog.dispose();
    	            
    	            if(maTK == currentMaTK) {

        	            if(mainGUI != null) {
        	            	mainGUI.dispose();
        	            	MainGUI main = null;
        	            	try {
    							main = new MainGUI(tkBUS.getUsername(maTK), maTK);
    						} catch (SQLException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
        	            	main.setVisible(true);
        	            	main.setLocationRelativeTo(null);
        	            }
    	            }
    	        });

    	        btnHuy.addActionListener(e -> {
    	            quyenDaChon.clear();
    	            
    	            dialog.dispose();
    	        });
 
    	        panelButtons.add(btnHuy);
    	        panelButtons.add(btnLuu);

    	        dialog.getContentPane().add(panelButtons, BorderLayout.SOUTH);

    	        // Tô nền dialog đẹp hơn
    	        dialog.getContentPane().setBackground(Color.WHITE);
    	        dialog.setVisible(true);
    	        
    }

    public void toggleEditMode() {
        isEditMode = !isEditMode;
        txtMaNV.setEditable(isEditMode);
        txtMaNV.setFocusable(isEditMode);
        txtHoNV.setEditable(isEditMode);
        txtHoNV.setFocusable(isEditMode);
        txtTenNV.setEditable(isEditMode);
        txtTenNV.setFocusable(isEditMode);
        txtSDT.setEditable(isEditMode);
        txtSDT.setFocusable(isEditMode);
        txtLuong.setEditable(isEditMode);
        txtLuong.setFocusable(isEditMode);
        txtUsername.setEditable(isEditMode);
        txtUsername.setFocusable(isEditMode);
        txtPassword.setEditable(isEditMode);
        txtPassword.setFocusable(isEditMode);
    }

    public void getInforFromTable() {
    	if(this.currentMaChucVu == 1) {
    		btnLamMoi.setVisible(false);
        	btnPhanQuyen.setVisible(true);
    	}
        int selectedRow = tblDSNV.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaNV.setEditable(false);
            txtMaNV.setText(tblDSNV.getValueAt(selectedRow, 0).toString());
            txtHoNV.setText(tblDSNV.getValueAt(selectedRow, 1).toString());
            txtTenNV.setText(tblDSNV.getValueAt(selectedRow, 2).toString());
            txtSDT.setText(tblDSNV.getValueAt(selectedRow, 3).toString());
            txtLuong.setText(tblDSNV.getValueAt(selectedRow, 4).toString());
            cboxChucVu.setSelectedItem(tblDSNV.getValueAt(selectedRow, 5).toString());
            txtUsername.setText(tblDSNV.getValueAt(selectedRow, 6).toString());
            txtPassword.setText(tblDSNV.getValueAt(selectedRow, 7).toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Thêm")) {
            addStaff();
        } else if (str.equals("Sửa")) {
            updateStaff();
        } else if (str.equals("Xóa")) {
            deleteStaff();
        } else if (str.equals("Tìm kiếm")) {
            timKiem();
            btnLamMoi.setVisible(true);
        } else if (str.equals("Nhập Excel")) {
            nhapExcel();
        } else if (str.equals("Xuất Excel")) {
            xuatExcel();
        } else if (str.equals("Nhập Excel")) {
            nhapExcel();
        }
    }

    public void timKiem() {
        String tuKhoa = txtSearch.getText().trim();
        String tieuChi = cboxSearch.getSelectedItem().toString();
        ArrayList<NhanVienDTO> result = new ArrayList<>();
        for (NhanVienDTO nv : nvBUS.getListNhanVien()) {
            if (tieuChi.equals("Mã NV") && nv.getMaNV().toLowerCase().contains(tuKhoa.toLowerCase())) {
                result.add(nv);
            } else if (tieuChi.equals("Tên NV") && nv.getTen().toLowerCase().contains(tuKhoa.toLowerCase())) {
                result.add(nv);
            }
        }

        model.setRowCount(0);
        for (NhanVienDTO nv : result) {
            model.addRow(new Object[]{
                nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt(), nv.getLuong()
            });
        }
        tblDSNV.setModel(model);
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void addStaff() {
        if (!isEditMode) {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = txtMaNV.getText().trim();
        String hoNV = txtHoNV.getText().trim();
        String tenNV = txtTenNV.getText().trim();
        String sdt = txtSDT.getText().trim();
        String luongStr = txtLuong.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String chucVu = cboxChucVu.getSelectedItem().toString().trim();

        if (maNV.isEmpty() || hoNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() || luongStr.isEmpty() || username.isEmpty() || password.isEmpty() || chucVu.equals("Chọn chức vụ")
        		) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!hoNV.matches("^[a-zA-ZÀ-ỹ ]+$")) {
            JOptionPane.showMessageDialog(this, "Họ sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!tenNV.matches("^[a-zA-ZÀ-ỹ ]+$")) {
            JOptionPane.showMessageDialog(this, "Tên sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double luong = Double.parseDouble(luongStr);
            if (isDuplicateMaNV(maNV)) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (isDuplicateSDT(sdt)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            NhanVienDTO nv = new NhanVienDTO(maNV, hoNV, tenNV, sdt, luong);
            int maChucVu = chucVuBUS.getMaChucVu(chucVu);
            tkBUS.addAccount(username, password);
            
            int maTK = tkBUS.getMaTK(username, password);
            nvBUS.addStaff(maNV, hoNV, tenNV, sdt, luong, maTK, maChucVu);
            model.addRow(new Object[]{maNV, hoNV, tenNV, sdt, luong, chucVu, username, password});
            tblDSNV.setModel(model);
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clearField();
            toggleEditInTheEnd();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lương phải là số hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updateStaff() {
        if (!isEditMode) {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = tblDSNV.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maNV = tblDSNV.getValueAt(selectedRow, 0).toString();
        String hoNV = txtHoNV.getText().trim();
        String tenNV = txtTenNV.getText().trim();
        String sdt = txtSDT.getText().trim();
        String luongStr = txtLuong.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String chucVu = cboxChucVu.getSelectedItem().toString().trim();
        int maChucVu = cboxChucVu.getSelectedIndex();

        if (!txtMaNV.getText().trim().equals(maNV)) {
            JOptionPane.showMessageDialog(this, "Bạn không thể sửa mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaNV.setText(maNV);
            return;
        }

        if (maNV.isEmpty() || hoNV.isEmpty() || tenNV.isEmpty() || sdt.isEmpty() || luongStr.isEmpty() || username.isEmpty() || password.isEmpty() || cboxChucVu.getSelectedItem().toString().equals("Chọn chức vụ")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!sdt.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!hoNV.matches("^[a-zA-ZÀ-ỹ ]+$")) {
            JOptionPane.showMessageDialog(this, "Họ sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!tenNV.matches("^[a-zA-ZÀ-ỹ ]+$")) {
            JOptionPane.showMessageDialog(this, "Tên sai định dạng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double luong = Double.parseDouble(luongStr);
            String oldSdt = tblDSNV.getValueAt(selectedRow, 3).toString();
            if (!sdt.equals(oldSdt) && isDuplicateSDT(sdt)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            nvBUS.fixStaff(hoNV, tenNV, sdt, luong, maChucVu ,selectedRow);
            
            int maTK = tkBUS.getMaTK(tblDSNV.getValueAt(selectedRow, 6).toString(), tblDSNV.getValueAt(selectedRow, 7).toString());
            tkBUS.updateAccount(maTK, username, password);
            model.setValueAt(maNV, selectedRow, 0);
            model.setValueAt(hoNV, selectedRow, 1);
            model.setValueAt(tenNV, selectedRow, 2);
            model.setValueAt(sdt, selectedRow, 3);
            model.setValueAt(luong, selectedRow, 4);
            model.setValueAt(chucVu, selectedRow, 5);
            model.setValueAt(username, selectedRow, 6);
            model.setValueAt(password, selectedRow, 7);
            tblDSNV.setModel(model);
            JOptionPane.showMessageDialog(this, "Sửa thông tin thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            btnPhanQuyen.setVisible(false);
            clearField();
            toggleEditInTheEnd();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lương phải là số hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void deleteStaff() {
        int selectedRow = tblDSNV.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            nvBUS.deleteStaff(selectedRow);
            tkBUS.deleteAccount(tblDSNV.getValueAt(selectedRow, 6).toString(), tblDSNV.getValueAt(selectedRow, 7).toString());
            model.removeRow(selectedRow);
            tblDSNV.setModel(model);
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clearField();
            toggleEditInTheEnd();
        }
    }

    public void nhapExcel() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có muốn nạp dữ liệu mới từ file Excel này không?\nDữ liệu hiện có sẽ được kiểm tra và cập nhật nếu cần.",
                "Xác nhận nhập Excel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    NhanVienBUS nvBUS = new NhanVienBUS();
                    int[] importResult = nvBUS.ImportExcel(selectedFile);
                    int addedRows = importResult[0];
                    int updatedRows = importResult[1];
                    JOptionPane.showMessageDialog(
                        this,
                        "Nhập dữ liệu từ Excel thành công!\n" +
                        " - Số dòng được thêm mới: " + addedRows + "\n" +
                        " - Số dòng được cập nhật: " + updatedRows,
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    // Cập nhật lại bảng sau khi nhập
                    fillTableWithSampleData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Lỗi khi nhập Excel: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                    );
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Đã hủy nhập dữ liệu từ Excel.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    public void xuatExcel() {
    	System.out.println("Exporting Excel for NhanVienGUI...");
        try {
            ExcelExporter.exportJTableToExcel(tblDSNV);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean isDuplicateMaNV(String maNV) {
        for (int i = 0; i < tblDSNV.getRowCount(); i++) {
            String existingMaNV = tblDSNV.getValueAt(i, 0).toString();
            if (maNV.equals(existingMaNV)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateSDT(String sdt) {
        for (int i = 0; i < tblDSNV.getRowCount(); i++) {
            String existingSDT = tblDSNV.getValueAt(i, 3).toString();
            if (sdt.equals(existingSDT)) {
                return true;
            }
        }
        return false;
    }

    public void clearField() {
        txtMaNV.setText("");
        txtHoNV.setText("");
        txtTenNV.setText("");
        txtSDT.setText("");
        txtLuong.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cboxChucVu.setSelectedIndex(0);
    }

    public void toggleEditInTheEnd() {
        isEditMode = false;
        txtMaNV.setEditable(false);
        txtMaNV.setFocusable(false);
        txtHoNV.setEditable(false);
        txtHoNV.setFocusable(false);
        txtTenNV.setEditable(false);
        txtTenNV.setFocusable(false);
        txtSDT.setEditable(false);
        txtSDT.setFocusable(false);
        txtLuong.setEditable(false);
        txtLuong.setFocusable(false);
        txtUsername.setEditable(false);
        txtUsername.setFocusable(false);
        txtPassword.setEditable(false);
        txtPassword.setFocusable(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test NhanVienGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        try {
            frame.getContentPane().add(new NhanVienGUI(1, null));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
}