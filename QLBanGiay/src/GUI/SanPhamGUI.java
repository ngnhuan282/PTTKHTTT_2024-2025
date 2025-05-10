package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;



import BUS.LoaiBUS;
import BUS.SanPhamBUS;
import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamGUI extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable tblDSSP;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuong, txtDonViTinh, txtChatLieu, txtKieuDang;
    private JSpinner spinnerKichThuoc;
    private JRadioButton rbDen, rbTrang, rbXam;
    private ButtonGroup mauSacGroup;
    private JComboBox<String> cbLoaiSP, cboxSearch;
    private JButton btnEditMode, btnNhapExcel, btnXuatExcel;
    private LoaiBUS loaiBUS = new LoaiBUS();
    private SanPhamBUS spBUS = new SanPhamBUS();
    private boolean isEditMode = false;
    private JTextField txtSearch;
    private JLabel lblAnhSanPham;
    private JButton btnChonAnh;
    private String duongDanAnh = ""; // để lưu đường dẫn ảnh


    public SanPhamGUI() 
    {
        Object[] header = {"Mã SP", "Tên SP", "Loại", "Số lượng", "Giá", "ĐVT", "Màu sắc", "Kích thước", "Chất liệu", "Kiểu dáng"};
        model = new DefaultTableModel(header, 0);
        tblDSSP = new JTable(model);

        initComponents();
        loadDataToTable();  
        loadLoaiSPToComboBox();
    }

    private void loadDataToTable() 
    {
        model.setRowCount(0);
        
        // Đọc danh sách sản phẩm và loại
        spBUS.docDSSP();
        loaiBUS.docDSLoai();
        for (SanPhamDTO sp : spBUS.getDssp()) {
            String tenLoaiSP = "";
            for (DTO.LoaiDTO loai : loaiBUS.getDsloai()) {
                if (loai.getMaLoaiSP() == sp.getMaLoaiSP()) {
                    tenLoaiSP = loai.getTenLoaiSP();
                    break;
                }
            }
            model.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), tenLoaiSP, sp.getSoLuong(), sp.getDonGia(),
                sp.getDonViTinh(), sp.getMauSac(), sp.getKichThuoc(), sp.getChatLieu(), 
                sp.getKieuDang()
            });
        }
    }

    private void loadLoaiSPToComboBox() 
    {
        cbLoaiSP.removeAllItems();
        LoaiBUS loaiBUS = new LoaiBUS();
        if (loaiBUS.getDsloai() != null) {
            for (DTO.LoaiDTO loai : loaiBUS.getDsloai()) {
                cbLoaiSP.addItem(loai.getTenLoaiSP());
            }
        }
    }

    public void initComponents() 
    {
        setPreferredSize(new Dimension(1248, 757));
        setLayout(null);
        setBackground(Color.WHITE);

        // Header
        JPanel pHeaderMain = new JPanel();
        pHeaderMain.setBounds(0, 0, 1206, 100);
        pHeaderMain.setBackground(Color.WHITE);
        pHeaderMain.setLayout(null);
        add(pHeaderMain);

        JPanel pLeftHeader = new JPanel();
        pLeftHeader.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pLeftHeader.setBackground(Color.WHITE);
        pLeftHeader.setBounds(2, 0, 512, 100);
        pHeaderMain.add(pLeftHeader);
        pLeftHeader.setLayout(null);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(UIManager.getBorder("Button.border"));
        horizontalBox.setBounds(0, 0, 532, 111);
        pLeftHeader.add(horizontalBox);

        JButton btnThem = new JButton("Thêm", new ImageIcon(SanPhamGUI.class.getResource("/image/add48.png")));
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

        JButton btnSua = new JButton("Sửa", new ImageIcon(SanPhamGUI.class.getResource("/image/edit48.png")));
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

        JButton btnXoa = new JButton("Xóa", new ImageIcon(SanPhamGUI.class.getResource("/image/remove48.png")));
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

        btnNhapExcel = new JButton("Nhập Excel", new ImageIcon(SanPhamGUI.class.getResource("/image/excel48.png")));
        btnNhapExcel.setFocusPainted(false);
        btnNhapExcel.setActionCommand("Nhập Excel");
        btnNhapExcel.addActionListener(this);
        btnNhapExcel.setBorderPainted(false);
        btnNhapExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNhapExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNhapExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNhapExcel.setBackground(Color.WHITE);
        btnNhapExcel.setPreferredSize(new Dimension(100, 140));
        horizontalBox.add(btnNhapExcel);

        btnXuatExcel = new JButton("Xuất Excel", new ImageIcon(SanPhamGUI.class.getResource("/image/xuatexcel48.png")));
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
        
//        JButton btnXuatPDF = new JButton("Xuất PDF");
//        btnXuatPDF.setFocusPainted(false);
//        btnXuatPDF.setActionCommand("Xuất PDF");
//        btnXuatPDF.addActionListener(this);
//        btnXuatPDF.setBorderPainted(false);
//        btnXuatPDF.setVerticalTextPosition(SwingConstants.BOTTOM);
//        btnXuatPDF.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/pdf48.png"))); // Thay bằng icon PDF
//        btnXuatPDF.setHorizontalTextPosition(SwingConstants.CENTER);
//        btnXuatPDF.setFont(new Font("Arial", Font.PLAIN, 15));
//        btnXuatPDF.setBackground(Color.WHITE);
//        btnXuatPDF.setPreferredSize(new Dimension(120, 140));
//        horizontalBox.add(btnXuatPDF);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1045, 31, 126, 28);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        pHeaderMain.add(btnLamMoi);
        
        String [] listKeyWord = {"Mã SP", "Tên SP"};
        cboxSearch = new JComboBox<String>(listKeyWord);
        cboxSearch.setForeground(Color.BLACK);
        cboxSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cboxSearch.setBackground(Color.WHITE);
        cboxSearch.setBounds(569, 31, 79, 28);
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

        // Form
        JPanel pInput = new JPanel();
        pInput.setBorder(UIManager.getBorder("TextField.border"));
        pInput.setBackground(Color.WHITE);
        pInput.setBounds(150, 110, 497, 408);
        add(pInput);
        pInput.setLayout(null);

        JLabel lbHeaderSP = new JLabel("THÔNG TIN SẢN PHẨM");
        lbHeaderSP.setForeground(Color.WHITE);
        lbHeaderSP.setHorizontalAlignment(SwingConstants.CENTER);
        lbHeaderSP.setFont(new Font("Arial", Font.BOLD, 15));
        lbHeaderSP.setBackground(Color.decode("#006666"));
        lbHeaderSP.setOpaque(true);
        lbHeaderSP.setBounds(0, 0, 497, 45);
        pInput.add(lbHeaderSP);

        JLabel lbMaSP = new JLabel("Mã sản phẩm");
        lbMaSP.setFont(new Font("Arial", Font.PLAIN, 14));
        lbMaSP.setBounds(10, 65, 112, 17);
        pInput.add(lbMaSP);

        txtMaSP = new JTextField();
        txtMaSP.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaSP.setBounds(10, 91, 168, 19);
        txtMaSP.setEditable(false);
        pInput.add(txtMaSP);

        JLabel lbLoaiSP = new JLabel("Loại sản phẩm");
        lbLoaiSP.setFont(new Font("Arial", Font.PLAIN, 14));
        lbLoaiSP.setBounds(233, 65, 112, 17);
        pInput.add(lbLoaiSP);

        cbLoaiSP = new JComboBox<>();
        cbLoaiSP.setFont(new Font("Arial", Font.PLAIN, 13));
        cbLoaiSP.setBounds(233, 91, 133, 21);
        cbLoaiSP.setBackground(Color.WHITE);
        cbLoaiSP.setForeground(Color.BLACK);
        cbLoaiSP.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cbLoaiSP.setEnabled(false);
        pInput.add(cbLoaiSP);

        JButton btnLoaiSP = new JButton("", new ImageIcon(SanPhamGUI.class.getResource("/image/icons8-information-20.png")));
        btnLoaiSP.setBorder(null);
        btnLoaiSP.setBackground(null);
        btnLoaiSP.setFocusPainted(false);
        btnLoaiSP.setBounds(376, 91, 47, 21);
        btnLoaiSP.addActionListener(e -> openLoaiSPDialog());
        pInput.add(btnLoaiSP);

        JLabel lbTenSP = new JLabel("Tên sản phẩm");
        lbTenSP.setFont(new Font("Arial", Font.PLAIN, 14));
        lbTenSP.setBounds(10, 133, 112, 17);
        pInput.add(lbTenSP);

        txtTenSP = new JTextField();
        txtTenSP.setFont(new Font("Arial", Font.PLAIN, 13));
        txtTenSP.setBounds(10, 159, 168, 19);
        txtTenSP.setEditable(false);
        pInput.add(txtTenSP);

        JLabel lbDonGia = new JLabel("Đơn giá");
        lbDonGia.setFont(new Font("Arial", Font.PLAIN, 14));
        lbDonGia.setBounds(233, 133, 112, 17);
        pInput.add(lbDonGia);

        txtDonGia = new JTextField();
        txtDonGia.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDonGia.setBounds(233, 159, 168, 19);
        txtDonGia.setEditable(false);
        pInput.add(txtDonGia);

        JLabel lbSoLuong = new JLabel("Số lượng");
        lbSoLuong.setFont(new Font("Arial", Font.PLAIN, 14));
        lbSoLuong.setBounds(10, 198, 112, 17);
        pInput.add(lbSoLuong);

        txtSoLuong = new JTextField();
        txtSoLuong.setFont(new Font("Arial", Font.PLAIN, 13));
        txtSoLuong.setBounds(10, 224, 168, 19);
        txtSoLuong.setEditable(false);
        pInput.add(txtSoLuong);

        JLabel lbDonViTinh = new JLabel("Đơn vị tính");
        lbDonViTinh.setFont(new Font("Arial", Font.PLAIN, 14));
        lbDonViTinh.setBounds(233, 198, 112, 17);
        pInput.add(lbDonViTinh);

        txtDonViTinh = new JTextField();
        txtDonViTinh.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDonViTinh.setBounds(233, 224, 168, 19);
        txtDonViTinh.setEditable(false);
        pInput.add(txtDonViTinh);

        JLabel lbMauSac = new JLabel("Màu sắc");
        lbMauSac.setFont(new Font("Arial", Font.PLAIN, 14));
        lbMauSac.setBounds(10, 263, 112, 17);
        pInput.add(lbMauSac);

        rbDen = new JRadioButton("Đen");
        rbDen.setFont(new Font("Arial", Font.PLAIN, 13));
        rbDen.setBounds(10, 289, 60, 19);
        rbDen.setEnabled(false);
        rbDen.setBorderPainted(false);
        rbDen.setBackground(null);
        pInput.add(rbDen);

        rbTrang = new JRadioButton("Trắng");
        rbTrang.setFont(new Font("Arial", Font.PLAIN, 13));
        rbTrang.setBounds(80, 289, 70, 19);
        rbTrang.setBackground(null);
        rbTrang.setEnabled(false);
        pInput.add(rbTrang);

        rbXam = new JRadioButton("Xám");
        rbXam.setFont(new Font("Arial", Font.PLAIN, 13));
        rbXam.setBounds(160, 289, 60, 19);
        rbXam.setBackground(null);
        rbXam.setEnabled(false);
        pInput.add(rbXam);

        mauSacGroup = new ButtonGroup();
        mauSacGroup.add(rbDen);
        mauSacGroup.add(rbTrang);
        mauSacGroup.add(rbXam);

        JLabel lbKichThuoc = new JLabel("Kích thước");
        lbKichThuoc.setFont(new Font("Arial", Font.PLAIN, 14));
        lbKichThuoc.setBounds(233, 263, 112, 17);
        pInput.add(lbKichThuoc);

        spinnerKichThuoc = new JSpinner(new SpinnerNumberModel(38, 38, 43, 1));
        spinnerKichThuoc.setFont(new Font("Arial", Font.PLAIN, 13));
        spinnerKichThuoc.setBounds(233, 289, 76, 19);
        spinnerKichThuoc.setEnabled(false);
        pInput.add(spinnerKichThuoc);

        JLabel lbChatLieu = new JLabel("Chất liệu");
        lbChatLieu.setFont(new Font("Arial", Font.PLAIN, 14));
        lbChatLieu.setBounds(10, 328, 112, 17);
        pInput.add(lbChatLieu);

        txtChatLieu = new JTextField();
        txtChatLieu.setFont(new Font("Arial", Font.PLAIN, 13));
        txtChatLieu.setBounds(10, 354, 168, 19);
        txtChatLieu.setEditable(false);
        pInput.add(txtChatLieu);

        JLabel lbKieuDang = new JLabel("Kiểu dáng");
        lbKieuDang.setFont(new Font("Arial", Font.PLAIN, 14));
        lbKieuDang.setBounds(233, 328, 112, 17);
        pInput.add(lbKieuDang);

        txtKieuDang = new JTextField();
        txtKieuDang.setFont(new Font("Arial", Font.PLAIN, 13));
        txtKieuDang.setBounds(233, 354, 168, 19);
        txtKieuDang.setEditable(false);
        pInput.add(txtKieuDang);

        btnEditMode = new JButton("", new ImageIcon(SanPhamGUI.class.getResource("/image/edit20.png")));
        btnEditMode.setFont(new Font("Arial", Font.PLAIN, 13));
        btnEditMode.setBounds(445, 55, 47, 21);
        btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.addActionListener(e -> toggleEditMode());
        pInput.add(btnEditMode);
        
        //Anh
        JPanel panelAnh = new JPanel();
        panelAnh.setLayout(null);
        panelAnh.setBounds(670, 110, 320, 320);  // bên phải pInput
        panelAnh.setOpaque(true); 
       // pInput.setBounds(150, 110, 497, 408);

        // Tạo JLabel để hiển thị ảnh sản phẩm
        lblAnhSanPham = new JLabel();
        lblAnhSanPham.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblAnhSanPham.setBounds(0, 0, 320, 320); // Vị trí trong panel
        panelAnh.add(lblAnhSanPham); // Thêm JLabel vào panelAnh

        // Tạo nút "Chọn ảnh"
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setFont(new Font("Arial", Font.PLAIN, 13));
        btnChonAnh.setBounds(670, 440, 320, 80); // Vị trí dưới ảnh trong panel
        btnChonAnh.setEnabled(true); // Tuỳ trạng thái
        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    duongDanAnh = selectedFile.getAbsolutePath();
                    hienThiAnh(duongDanAnh);
                }
            }
        });
        add(btnChonAnh); // Thêm nút vào panelAnh

        // Thêm panelAnh vào pInput
        add(panelAnh);

        // Table sản phẩm chính
        tblDSSP.setBackground(Color.WHITE);
        tblDSSP.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
        tblDSSP.setFillsViewportHeight(true);
        tblDSSP.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDSSP.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(tblDSSP);
        scrollPane.setBounds(146, 544, 964, 167);
        add(scrollPane);

        tblDSSP.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblDSSPMouseClicked();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    
    private void hienThiAnh(String duongDan) {
        try {
            ImageIcon icon = new ImageIcon(duongDan);
            Image img = icon.getImage();

            // Tính toán giữ nguyên tỷ lệ
            int labelWidth = lblAnhSanPham.getWidth();
            int labelHeight = lblAnhSanPham.getHeight();
            double imgWidth = img.getWidth(null);
            double imgHeight = img.getHeight(null);
            double imgAspect = imgWidth / imgHeight;
            double labelAspect = (double) labelWidth / labelHeight;

            int scaledWidth, scaledHeight;

            if (imgAspect > labelAspect) {
                // Ảnh rộng hơn label
                scaledWidth = labelWidth;
                scaledHeight = (int) (labelWidth / imgAspect);
            } else {
                // Ảnh cao hơn label
                scaledHeight = labelHeight;
                scaledWidth = (int) (labelHeight * imgAspect);
            }

            Image scaledImage = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            lblAnhSanPham.setIcon(new ImageIcon(scaledImage));
            lblAnhSanPham.setHorizontalAlignment(SwingConstants.CENTER);
            lblAnhSanPham.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tblDSSPMouseClicked() 
    {
        int i = tblDSSP.getSelectedRow();
        if (i >= 0) {
            txtMaSP.setText(tblDSSP.getValueAt(i, 0).toString());
            txtTenSP.setText(tblDSSP.getValueAt(i, 1).toString());
            cbLoaiSP.setSelectedItem(tblDSSP.getValueAt(i, 2).toString());
            txtDonGia.setText(tblDSSP.getValueAt(i, 4).toString());
            txtSoLuong.setText(tblDSSP.getValueAt(i, 3).toString());
            txtDonViTinh.setText(tblDSSP.getValueAt(i, 5).toString());
            String mauSac = tblDSSP.getValueAt(i, 6).toString();
            if (mauSac.equals("Đen")) rbDen.setSelected(true);
            else if (mauSac.equals("Trắng")) rbTrang.setSelected(true);
            else if (mauSac.equals("Xám")) rbXam.setSelected(true);
            spinnerKichThuoc.setValue(Integer.parseInt(tblDSSP.getValueAt(i, 7).toString()));
            txtChatLieu.setText(tblDSSP.getValueAt(i, 8).toString());
            txtKieuDang.setText(tblDSSP.getValueAt(i, 9).toString());
        }
    }

    public void toggleEditMode() 
    {
        isEditMode = !isEditMode;
        txtMaSP.setEditable(isEditMode);
        txtTenSP.setEditable(isEditMode);
        txtDonGia.setEditable(isEditMode);
        txtSoLuong.setEditable(isEditMode);
        txtDonViTinh.setEditable(isEditMode);
        cbLoaiSP.setEnabled(isEditMode);
        rbDen.setEnabled(isEditMode);
        rbTrang.setEnabled(isEditMode);
        rbXam.setEnabled(isEditMode);
        spinnerKichThuoc.setEnabled(isEditMode);
        txtChatLieu.setEditable(isEditMode);
        txtKieuDang.setEditable(isEditMode);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();
        switch (command) 
        {
            case "Thêm": addProduct(); break;
            case "Sửa": updateProduct(); break;
            case "Xóa": deleteProduct(); break;
            case "Reload": loadDataToTable(); break;
            case "Tìm kiếm": timKiem(); break;
            case "Nhập Excel": nhapExcel(); break;
            case "Xuất Excel": xuatExcel(); break;
            case "Xuất PDF":  break;
        }
    }
    
    public void timKiem()
    {
    	String tuKhoa = txtSearch.getText().trim();
    	String tieuChi = cboxSearch.getSelectedItem().toString();
    	SanPhamBUS spBUS = new SanPhamBUS();
    	LoaiBUS loaiBUS = new LoaiBUS();
    	ArrayList<SanPhamDTO> result = spBUS.searchSP(tuKhoa, tieuChi);
    
    	model.setRowCount(0);
    	for(SanPhamDTO sp : result)
    	{
    		String tenLoaiSP = "";
    		for(DTO.LoaiDTO loai : loaiBUS.getDsloai())
    		{
    			if(loai.getMaLoaiSP() == sp.getMaLoaiSP())
    			{
    				tenLoaiSP = loai.getTenLoaiSP();
    				break;
    			}
    		}
    		Vector row = new Vector();
        	row.add(sp.getMaSP()); row.add(sp.getTenSP()); 	row.add(tenLoaiSP);
        	row.add(sp.getDonGia()); row.add(sp.getSoLuong()); row.add(sp.getDonViTinh());
        	row.add(sp.getMauSac()); row.add(sp.getKichThuoc());
        	row.add(sp.getChatLieu()); row.add(sp.getKieuDang());
        	model.addRow(row);
       
    	}
    	tblDSSP.setModel(model);
    	if(result.isEmpty())
    		JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void addProduct() 
    {
        if (!isEditMode) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        int selectedIndex = cbLoaiSP.getSelectedIndex();
        if (selectedIndex < 0) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maLoaiSP = loaiBUS.getDsloai().get(selectedIndex).getMaLoaiSP();
        String donGiaStr = txtDonGia.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String donViTinh = txtDonViTinh.getText().trim();
        String mauSac = rbDen.isSelected() ? "Đen" : rbTrang.isSelected() ? "Trắng" : rbXam.isSelected() ? "Xám" : "";
        int kichThuoc = Integer.parseInt(spinnerKichThuoc.getValue().toString());
        String chatLieu = txtChatLieu.getText().trim();
        String kieuDang = txtKieuDang.getText().trim();
        
        if (mauSac.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn màu sắc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(spBUS.checkMaSP(maSP))
        {
        	JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        try {
            double donGia = Double.parseDouble(donGiaStr);
            int soLuong = Integer.parseInt(soLuongStr);

            SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, maLoaiSP, soLuong, donGia, donViTinh,  mauSac, kichThuoc, chatLieu, kieuDang);
            if (spBUS.addSP(sp)) 
            {
                String tenLoaiSP = cbLoaiSP.getSelectedItem().toString();
                model.addRow(new Object[]{maSP, tenSP, tenLoaiSP, soLuong, donGia, donViTinh, mauSac, kichThuoc, chatLieu, kieuDang});
                tblDSSP.setModel(model);
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else 
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateProduct() 
    {
        if (!isEditMode) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = tblDSSP.getSelectedRow();
        if (row < 0) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        int selectedIndex = cbLoaiSP.getSelectedIndex();
        if (selectedIndex < 0) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại sản phẩm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int maLoaiSP = loaiBUS.getDsloai().get(selectedIndex).getMaLoaiSP();
        String donGiaStr = txtDonGia.getText().trim();
        String soLuongStr = txtSoLuong.getText().trim();
        String donViTinh = txtDonViTinh.getText().trim();
        String mauSac = rbDen.isSelected() ? "Đen" : rbTrang.isSelected() ? "Trắng" : rbXam.isSelected() ? "Xám" : "";
        int kichThuoc = Integer.parseInt(spinnerKichThuoc.getValue().toString());
        String chatLieu = txtChatLieu.getText().trim();
        String kieuDang = txtKieuDang.getText().trim();

        if (mauSac.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn màu sắc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
//        if(spBUS.checkMaSP(maSP))
//        {
//        	JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        	return;
//        }
        
        try {
            double donGia = Double.parseDouble(donGiaStr);
            int soLuong = Integer.parseInt(soLuongStr);

            SanPhamDTO sp = new SanPhamDTO(maSP, tenSP, maLoaiSP, soLuong, donGia, donViTinh,  mauSac, kichThuoc, chatLieu, kieuDang);
            if (spBUS.updateSP(sp)) {
                String tenLoaiSP = cbLoaiSP.getSelectedItem().toString();
                model.setValueAt(maSP, row, 0);
                model.setValueAt(tenSP, row, 1);
                model.setValueAt(tenLoaiSP, row, 2);
                model.setValueAt(donGia, row, 4);
                model.setValueAt(soLuong, row, 3);
                model.setValueAt(donViTinh, row, 5);
                model.setValueAt(mauSac, row, 6);
                model.setValueAt(kichThuoc, row, 7);
                model.setValueAt(chatLieu, row, 8);
                model.setValueAt(kieuDang, row, 9);
                tblDSSP.setModel(model);
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else 
                JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá và số lượng phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteProduct() 
    {
        int row = tblDSSP.getSelectedRow();
        if (row >= 0) {
            String maSP = tblDSSP.getValueAt(row, 0).toString();
            String tenSP = tblDSSP.getValueAt(row, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa sản phẩm " + tenSP + " có mã " + maSP + " không?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (spBUS.deleteSP(maSP)) {
                    model.removeRow(row);
                    tblDSSP.setModel(model);
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm " + tenSP + " thành công",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else 
                    JOptionPane.showMessageDialog(this, "Xóa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                
            }
        } else 
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
    }


    public void openLoaiSPDialog() 
    {
        LoaiSPDialog dialog = new LoaiSPDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        loadLoaiSPToComboBox();
    }

    public void clearForm() 
    {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtDonViTinh.setText("");
        mauSacGroup.clearSelection();
        spinnerKichThuoc.setValue(38);
        txtChatLieu.setText("");
        txtKieuDang.setText("");
        cbLoaiSP.setSelectedIndex(-1);
    }
    
    public void xuatExcel()
    {
    	try {
            ExcelExporter.exportJTableToExcel(tblDSSP);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
                    SanPhamBUS spBUS = new SanPhamBUS();
                    int[] importResult = spBUS.ImportExcel(selectedFile);
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
                    loadDataToTable();
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
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test SanPhamGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new SanPhamGUI());
        frame.setVisible(true);
    }
}