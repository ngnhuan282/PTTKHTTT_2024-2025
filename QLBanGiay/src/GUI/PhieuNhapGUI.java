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

import BUS.ChiTietPNBUS;
import BUS.LoaiBUS;
import BUS.NhaCungCapBUS;
import BUS.NhanVienBUS;
import BUS.PhieuNhapBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietPNDTO;
import DTO.LoaiDTO;
import DTO.NhaCungCapDTO;
import DTO.NhanVienDTO;
import DTO.PhieuNhapDTO;
import DTO.SanPhamDTO;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.EtchedBorder;



public class PhieuNhapGUI extends JPanel  implements ActionListener{
	private JPanel contentPane;
	private String color = "#FF5252";
	public DefaultTableModel modelPhieuNhap,modelCTPN;
	private JTable tbPhieuNhap,tbCTPN;
	private JTextField txtSearch;
	private JPanel inforPanel;
	private JTextField txtMaPN,txtNCC,txtNV,txtSP,txtSoLuong,txtDonGia;
	private JButton btnOpenNVList,btnOpenNCCList,btnOpenSPList,btnDeleteProduct,btnEditProduct,btnComplete,btnAddProduct;
	private NhaCungCapBUS nhaCungCapBUS = new NhaCungCapBUS();	
	private NhanVienBUS nhanVienBUS;
	private SanPhamBUS sanPhamBUS = new SanPhamBUS();
	private PhieuNhapBUS phieuNhapBUS = new PhieuNhapBUS();
	private ChiTietPNBUS chiTietPNBUS = new ChiTietPNBUS();
	private LoaiBUS loaiBUS = new LoaiBUS();
	private ArrayList<ChiTietPNDTO> listTemp = new ArrayList<ChiTietPNDTO>();
	private boolean isEditing = false;
	private JLabel lbTongTien , lbNgayNhap;
	private JTextField txtTongTien,txtNgayNhap;
	private boolean update = false;
	private boolean add = false;
	private JComboBox<String> cboxSearch;
	private JDateChooser dateStart, dateEnd;
	private JTextField txtMaPNNC , txtMaNCCNC;

	
	public static void main(String[] args) {
        JFrame frame = new JFrame("PhieuNhapGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new PhieuNhapGUI());
        frame.setVisible(true);
    }
	
	public PhieuNhapGUI() {
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

        JButton btnXuatExcel = new JButton("Xuất Excel", new ImageIcon(SanPhamGUI.class.getResource("/image/xuatexcel48.png")));
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
        btnXuatPDF.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/pdf48.png"))); // Thay bằng icon PDF
        btnXuatPDF.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatPDF.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatPDF.setBackground(Color.WHITE);
        btnXuatPDF.setPreferredSize(new Dimension(120, 140));
        horizontalBox.add(btnXuatPDF);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1029, 33, 128, 30);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        pHeaderMain.add(btnLamMoi);
        
        String []listKeyWord = {"Mã PN", "Mã NV", "Mã NCC"};
        
        JButton btnSearch = new JButton("", new ImageIcon(SanPhamGUI.class.getResource("/image/search30.png")));
        btnSearch.setBackground(Color.WHITE);
        btnSearch.setActionCommand("Tìm kiếm");
        btnSearch.addActionListener(this);
        btnSearch.setBounds(956, 33, 64, 30);
        pHeaderMain.add(btnSearch);
        
        txtSearch = new JTextField();
        txtSearch.setBounds(627, 18, 290, 27);
        pHeaderMain.add(txtSearch);
        txtSearch.setColumns(10);
        cboxSearch = new JComboBox<String>(listKeyWord);
        cboxSearch.setBounds(540, 18, 77, 27);
        pHeaderMain.add(cboxSearch);
        cboxSearch.setForeground(Color.BLACK);
        cboxSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cboxSearch.setBackground(Color.WHITE);
        
        JLabel lblDateStart = new JLabel("Từ ngày:");
        lblDateStart.setBounds(557, 56, 60, 27);
        pHeaderMain.add(lblDateStart);
        lblDateStart.setFont(new Font("Arial", Font.PLAIN, 13));
        
        dateStart = new JDateChooser();
        dateStart.setBounds(627, 56, 105, 27);
        pHeaderMain.add(dateStart);
        dateStart.setDateFormatString("yyyy-MM-dd");
        
        JLabel lblDateEnd = new JLabel("Đến ngày:");
        lblDateEnd.setBounds(742, 56, 60, 27);
        pHeaderMain.add(lblDateEnd);
        lblDateEnd.setFont(new Font("Arial", Font.PLAIN, 13));
        
        dateEnd = new JDateChooser();
        dateEnd.setBounds(812, 56, 105, 27);
        pHeaderMain.add(dateEnd);
        dateEnd.setDateFormatString("yyyy-MM-dd");
        
        
        JPanel pContent = new JPanel();
        pContent.setBackground(SystemColor.control);;
        pContent.setBounds(0, 103, 1248, 654); // Đặt bên dưới pHeaderMain
        pContent.setLayout(null);
        add(pContent);  
        
        inforPanel = new JPanel();
        inforPanel.setBackground(Color.WHITE);
        inforPanel.setBounds(10, 11, 387, 328);
        pContent.add(inforPanel);
        inforPanel.setLayout(null);
        
        JLabel lbThongTin = new JLabel("Thông tin phiếu nhập");
        lbThongTin.setBounds(83, 11, 225, 25);
        lbThongTin.setFont(new Font("Tahoma", Font.BOLD, 20));
        inforPanel.add(lbThongTin);
        
        JLabel lbMaPN = new JLabel("Mã phiếu nhập");
        lbMaPN.setBounds(10, 55, 114, 25);
        lbMaPN.setFont(new Font("Tahoma", Font.PLAIN, 13));
        inforPanel.add(lbMaPN);
        
        txtMaPN = new JTextField();
        txtMaPN.setBounds(124, 55, 184, 25);
        inforPanel.add(txtMaPN);
        txtMaPN.setColumns(10);

        txtNCC = new JTextField();
        txtNCC.setColumns(10);
        txtNCC.setBounds(124, 127, 184, 25);
        inforPanel.add(txtNCC);
        txtNCC.setEditable(false);
        txtNCC.setFocusable(false);
        
        
        JLabel lbNCC = new JLabel("Nhà cung cấp");
        lbNCC.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbNCC.setBounds(10, 127, 114, 25);
        inforPanel.add(lbNCC);
        
        JLabel lbNV = new JLabel("Nhân viên nhập");
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
        lbSP.setBounds(10, 163, 114, 25);
        inforPanel.add(lbSP);
        
        txtSP = new JTextField();
        txtSP.setColumns(10);
        txtSP.setBounds(124, 163, 184, 25);
        inforPanel.add(txtSP);
        txtSP.setEditable(false);
        txtSP.setFocusable(false);
        
        
        
        JLabel lbSoLuong = new JLabel("Số lượng");
        lbSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbSoLuong.setBounds(10, 199, 114, 25);
        inforPanel.add(lbSoLuong);
        
        txtSoLuong = new JTextField();
        txtSoLuong.setColumns(10);
        txtSoLuong.setBounds(124, 199, 184, 25);
        inforPanel.add(txtSoLuong);

          
        JLabel lbDonGia = new JLabel("Đơn giá nhập");
        lbDonGia.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lbDonGia.setBounds(10, 235, 114, 25);
        inforPanel.add(lbDonGia);
          
        txtDonGia = new JTextField();
        txtDonGia.setColumns(10);
        txtDonGia.setBounds(124, 235, 184, 25);
        inforPanel.add(txtDonGia);

      
        JButton btnCancel = new JButton("");
        btnCancel.setBorderPainted(false); 
        btnCancel.setFocusPainted(false);  
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setIcon(new ImageIcon(PhieuNhapGUI.class.getResource("/image/icons8-cancel-20.png")));
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(20, 11, 32, 23);
        inforPanel.add(btnCancel);
        
        btnComplete = new JButton("Hoàn tất");
        btnComplete.setBounds(292, 271, 85, 33);
        inforPanel.add(btnComplete);
        btnComplete.addActionListener(e->complete());
        btnComplete.setVisible(false);
         
        btnOpenNVList = new JButton("...");
        btnOpenNVList.setBounds(318, 92, 32, 23);
        inforPanel.add(btnOpenNVList);
        btnOpenNVList.addActionListener(e->openNVList());
        
        
        btnOpenNCCList = new JButton("...");
        btnOpenNCCList.setBounds(318, 129, 32, 23);
        inforPanel.add(btnOpenNCCList);
        btnOpenNCCList.addActionListener(e->openNCCList());
      
      	
        btnOpenSPList = new JButton("...");
        btnOpenSPList.setBounds(318, 165, 32, 23);
        inforPanel.add(btnOpenSPList);
        btnOpenSPList.addActionListener(e->openSPList());
        
        
        btnEditProduct = new JButton("Sửa SP");
        btnEditProduct.setBounds(102, 271, 85, 33);
        inforPanel.add(btnEditProduct);
        btnEditProduct.addActionListener(e->editProduct());
        btnEditProduct.setVisible(false);
        
        
        btnDeleteProduct = new JButton("Xóa SP");
        btnDeleteProduct.setBounds(7, 271, 85, 33);
        inforPanel.add(btnDeleteProduct);
        btnDeleteProduct.addActionListener(e->deleteProduct());
        btnDeleteProduct.setVisible(false);
        
        
        btnAddProduct = new JButton("Thêm SP");
        btnAddProduct.setBounds (197, 271, 85, 33);
        inforPanel.add(btnAddProduct);
        btnAddProduct.addActionListener(e->addProduct());
        btnAddProduct.setVisible(false);
        
        
       
        
        JPanel phieuNhapPanel = new JPanel();
        phieuNhapPanel.setBackground(Color.WHITE);
        phieuNhapPanel.setBounds(407, 11, 831, 328);
        pContent.add(phieuNhapPanel);
        phieuNhapPanel.setLayout(null);
        
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBounds(10, 351, 1228, 292);
        pContent.add(detailsPanel);
        detailsPanel.setLayout(null);
        
        
        //bang phieu nhap (ben phai)
        String[] columns = { "Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp", "Tổng tiền", "Ngày nhập"};
        modelPhieuNhap = new DefaultTableModel(columns,0);
        
        tbPhieuNhap = new JTable(modelPhieuNhap);
        tbPhieuNhap.setFont(new Font("Verdana", Font.PLAIN, 12));
        tbPhieuNhap.setGridColor(new Color(200, 200, 200));
        tbPhieuNhap.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
  		
        tbPhieuNhap.setRowHeight(23);
        tbPhieuNhap.getTableHeader().setPreferredSize(new Dimension(0, 25));
        tbPhieuNhap.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        
        //lay selected 
        tbPhieuNhap.getSelectionModel().addListSelectionListener(e->getSelectedRowTbPhieuNhap());
        ////////
        
        JScrollPane scrollPanePhieuNhap = new JScrollPane(tbPhieuNhap);
        scrollPanePhieuNhap.setBounds(0, 0, 831, 328);
        scrollPanePhieuNhap.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220))); //do dai vien tren duoi trai phai
        scrollPanePhieuNhap.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPanePhieuNhap.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        phieuNhapPanel.add(scrollPanePhieuNhap);
        
        
        //Lay thong tin cho DSPN
        phieuNhapBUS.docDSPN();
        updateTablePN();
        /////////////////////////////////////
        
        //bang chi tiet phieu nhap (o phia duoi)
        String[] columns2 = { "Mã phiếu nhập", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        modelCTPN = new DefaultTableModel(columns2,0);
        
        tbCTPN = new JTable(modelCTPN);
        tbCTPN.setFont(new Font("Verdana", Font.PLAIN, 12));
        tbCTPN.setGridColor(new Color(200, 200, 200));
        tbCTPN.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        tbCTPN.setRowHeight(23);
        tbCTPN.getTableHeader().setPreferredSize(new Dimension(0, 23));
        tbCTPN.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        
      //lay selected 
        tbCTPN.getSelectionModel().addListSelectionListener(e->getInforFromTbCTPN());
        ////////
        
        JScrollPane scrollPaneCTPN = new JScrollPane(tbCTPN);
        scrollPaneCTPN.setBounds(0, 0, 1228, 292);
        scrollPaneCTPN.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220))); //do dai vien tren duoi trai phai
        scrollPaneCTPN.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPaneCTPN.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        detailsPanel.add(scrollPaneCTPN);
        
        chiTietPNBUS.docDSCTPN();
        
        sanPhamBUS.docDSSP();
	}
	
	
	
	
	public void openNCCList() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Nhà Cung Cấp");
		dialog.setSize(650, 300);
		dialog.getContentPane().setLayout(new BorderLayout(5, 5));
		
		JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        JTextField txtSearchNCC = new JTextField();
        txtSearchNCC.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(txtSearchNCC, BorderLayout.CENTER);
        dialog.getContentPane().add(searchPanel, BorderLayout.NORTH);

		
		String[] columns = { "Mã NCC", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Địa Chỉ" };
		
		JTable tbNCC = new JTable();
		JScrollPane jScrollPaneNCC = new JScrollPane(tbNCC);
		tbNCC.setFont(new Font("Arial", Font.PLAIN, 12));
		tbNCC.setRowHeight(23);
		tbNCC.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		
		DefaultTableModel modelNCC = new DefaultTableModel(columns, 0);
		for(NhaCungCapDTO ncc : nhaCungCapBUS.getListNCC()) 
			modelNCC.addRow(new Object[] {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getDiaChi()});
		tbNCC.setModel(modelNCC);
		
		dialog.getContentPane().add(jScrollPaneNCC);
		dialog.setBounds(0, 270 , 650, 250);
		dialog.setVisible(true);
		
		//search 
		txtSearchNCC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String keyword = txtSearchNCC.getText().trim().toLowerCase();
				modelNCC.setRowCount(0);
				for(NhaCungCapDTO ncc : nhaCungCapBUS.getListNCC())
					if(ncc.getMaNCC().toLowerCase().contains(keyword))
						modelNCC.addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getDiaChi()});
			}
		});
		
		  //Su kien click vao table 
      	tbNCC.getSelectionModel().addListSelectionListener(e->{
      		int selectedRow = tbNCC.getSelectedRow();
      		
      		String maNCC = (String) tbNCC.getValueAt(selectedRow, 0);
      		txtNCC.setText(maNCC);
      		dialog.dispose();
      	});
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
		for(NhanVienDTO nv : nhanVienBUS.getListNhanVien())
			modelNV.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
		
		tbNV.setModel(modelNV);
		
		dialog.getContentPane().add(jScrollPaneNV);
		dialog.setBounds(0, 300 , 650, 250);
		dialog.setVisible(true);
		
		txtSearchNV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String keyword = txtSearchNV.getText().trim().toLowerCase();
				modelNV.setRowCount(0);
				for(NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
					if(nv.getMaNV().toLowerCase().contains(keyword))
						modelNV.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
				}
			}
		}); 
		
		
		
		//su kien click 
		tbNV.getSelectionModel().addListSelectionListener(e->{
			int selectedRow = tbNV.getSelectedRow();
			
			String maNV = (String) tbNV.getValueAt(selectedRow, 0);
			txtNV.setText(maNV);
			dialog.dispose();
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
		for(SanPhamDTO sp : sanPhamBUS.getDssp()) {
			String tenLoaiSP = "";
			for (LoaiDTO loai : loaiBUS.getDsloai()) {
                if (loai.getMaLoaiSP() == sp.getMaLoaiSP()) {
                    tenLoaiSP = loai.getTenLoaiSP();
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
		dialog.setBounds(0, 330 , 750, 250);
		dialog.setVisible(true);
		
//		search
		txtSearchSP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String keyword = txtSearchSP.getText().trim().toLowerCase();
				modelSP.setRowCount(0);
				for(SanPhamDTO sp : sanPhamBUS.getDssp()) {
				String tenLoaiSP = "";
					for (LoaiDTO loai : loaiBUS.getDsloai()) {
						if (loai.getMaLoaiSP() == sp.getMaLoaiSP()) {
	                    tenLoaiSP = loai.getTenLoaiSP();
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
		
		
		//su kien click 
		tbSP.getSelectionModel().addListSelectionListener(e->{
			int selectedRow = tbSP.getSelectedRow();
			
			String maSP = (String) tbSP.getValueAt(selectedRow, 0);
			txtSP.setText(maSP);
			dialog.dispose();
		});
		
		
	}
	
	
	//SU KIEN THEM PHIEU NHAPPPPPPPPPP///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addCTPN() {
		clear();
		
		txtMaPN.setText(phieuNhapBUS.generateMaPN());
		btnAddProduct.setVisible(true);
		btnEditProduct.setVisible(true);
		btnDeleteProduct.setVisible(true);
		btnComplete.setVisible(true);
		//dua vao trang thai add (han che select cac pn va ctpn)
		add = true;
	}
	
	public void editProduct() {
		 int selectedRow = tbCTPN.getSelectedRow();
		    if (selectedRow == -1) {
		        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    
		    String maPN = txtMaPN.getText().trim();
		    String SP = txtSP.getText().trim();
		    int soLuong;
		    double donGia;

		    // Kiểm tra dữ liệu đầu vào
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
		    
		    for(int i = 0 ; i<listTemp.size();i++)
		    	if(i!= selectedRow && listTemp.get(i).getMaSP().equals(SP)) {
		    		  JOptionPane.showMessageDialog(this, "Sản phẩm " + SP + " đã tồn tại! Vui lòng chọn mã sản phẩm khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		    		  txtSP.requestFocus();
		    		  return;
		    	}
		    
		    double thanhTien = soLuong * donGia;   
		    listTemp.set(selectedRow,new ChiTietPNDTO(maPN,SP, soLuong,donGia,thanhTien));
		    
		    modelCTPN.setRowCount(0);	
			for(ChiTietPNDTO ctpn : listTemp)
				modelCTPN.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});
			
			JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			
			 txtSP.setText("");
			 txtSoLuong.setText("");
			 txtDonGia.setText("");
			 
	}
	
	
	public void deleteProduct() {
	    int selectedRow = tbCTPN.getSelectedRow();
	    if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chi tiết để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa chi tiết này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	    if (confirm != JOptionPane.YES_OPTION) {
	        return;
	    }
	    
	    listTemp.remove(selectedRow);
	    
	    modelCTPN.setRowCount(0); 
		
		for(ChiTietPNDTO ctpn : listTemp)
			modelCTPN.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});
		
		txtSP.setText("");
	    txtSoLuong.setText("");
	    txtDonGia.setText("");
	    
	    
	}
	
/////////////////////////////////////////////Ket thuc them phieu////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
///////////////////////////////////EDITTTTTTTTTTTTTTT////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void editPN() {
		//truong hop dang coi chi tiet ma bam sua 
		btnOpenNVList.setVisible(true);
		btnOpenNCCList.setVisible(true);
		btnOpenSPList.setVisible(true);
		
		txtSoLuong.setText("");
		txtDonGia.setText("");
		txtSoLuong.setEditable(true);
		txtSoLuong.setFocusable(true);
		txtDonGia.setEditable(true);
		txtDonGia.setFocusable(true);
		
		update = true;
		int selectedRowPN = tbPhieuNhap.getSelectedRow();
		if(selectedRowPN == -1) {
			 JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập ở bảng phiếu nhập để tiến hành sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        return;
		}
		
		String maPN = (String) modelPhieuNhap.getValueAt(selectedRowPN, 0);
	    String NV = (String) modelPhieuNhap.getValueAt(selectedRowPN, 1);
	    String NCC = (String) modelPhieuNhap.getValueAt(selectedRowPN, 2);
	    
	    txtMaPN.setText(maPN);
	    txtMaPN.setForeground(new Color(155,155,155));
	    txtNV.setText(NV);
	    txtNCC.setText(NCC);
	    
	    txtMaPN.setEditable(false);
		txtMaPN.setFocusable(false);
		
		//lay du lieu qua temp
		listTemp.clear();
		modelCTPN.setRowCount(0);
		listTemp.addAll(chiTietPNBUS.getChiTietTheoMaPhieu(maPN));
		for(ChiTietPNDTO ctpn : listTemp)
			modelCTPN.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});
			
		
		btnAddProduct.setVisible(true);
		btnEditProduct.setVisible(true);
		btnDeleteProduct.setVisible(true);
		btnComplete.setVisible(true);
	}
	
	
	public void addProduct() {
		String maPN = txtMaPN.getText();
		String NV = txtNV.getText();
		String NCC = txtNCC.getText();
		String SP = txtSP.getText();
		
		if (maPN.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        txtMaPN.requestFocus();
	        return;
	    }
		if(!update) {
			if(phieuNhapBUS.checkMaPN(maPN)) {
				JOptionPane.showMessageDialog(this,"Mã phiếu nhập " + maPN + " đã tồn tại! Vui lòng chọn mã khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
				txtMaPN.requestFocus();
				return;
			}
		}
		
	    if (NV.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        txtNV.requestFocus();
	        return;
	    }
	    if (NCC.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        txtNCC.requestFocus();
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
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    
	    //check xem co trong chi tiet chua 
	    for(ChiTietPNDTO ctpn : listTemp)
	    	if(ctpn.getMaSP().equals(SP)) {
	    		JOptionPane.showMessageDialog(this, "Sản phẩm " + SP + " đã tồn tại! Vui lòng chọn dòng và nhấn Sửa để cập nhật số lượng hoặc đơn giá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	    	}
	    
		double thanhTien = soLuong*donGia;
		
		listTemp.add(new ChiTietPNDTO(maPN,SP,soLuong,donGia,thanhTien));
		modelCTPN.setRowCount(0);	
		for(ChiTietPNDTO ctpn : listTemp)
			modelCTPN.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});

		txtMaPN.setEditable(false);
		txtMaPN.setFocusable(false);
		txtMaPN.setForeground(new Color(155,155,155));
		txtSP.setText("");
		txtSoLuong.setText("");
		txtDonGia.setText("");
		
		
		JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào chi tiết phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
////////////////////////////////KET THUC EDIT///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void complete() {
		 String maPN = txtMaPN.getText().trim();
		 String NV = txtNV.getText().trim();
		 String NCC = txtNCC.getText().trim();
		 
		 
		 if (maPN.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        txtMaPN.requestFocus();
		        return;
		    }
		 if (NV.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        txtNV.requestFocus();
		        return;
		    }
		    if (NCC.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        txtNCC.requestFocus();
		        return;
		    }
		 if (listTemp.isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một chi tiết phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        txtSP.requestFocus();
		        return;
		    }
		 
		 
		 double tongTien = 0;
		    for (ChiTietPNDTO ctpn : listTemp) {
		        tongTien += ctpn.getThanhTien();
		    }
		    
		    LocalDate now = LocalDate.now();
		 Date ngayNhap =  Date.valueOf(now);
		 if(update) {
			 //lay array list cu~ cua chi tiet tuc la cac san pham nhap vao 
			 ArrayList<ChiTietPNDTO> oldListCTPN = chiTietPNBUS.getChiTietTheoMaPhieu(maPN);
			 // tuc la o day array moi , dang sua la list temp nhung van chua complete 
			 
			//delete nhung so luong cu~ bang cach tru so luong hien tai cho list cu~
			 for(ChiTietPNDTO oldCTPN : oldListCTPN)		//chuyen tru de - tru so cu~ 
				 sanPhamBUS.updateSoLuong(oldCTPN.getMaSP(), -oldCTPN.getSoLuong());
			 //sau do thay doi bang cach cong thanh so moi , tuc tru di phieu nhap cu , roi cong lai tu dau
			 for(ChiTietPNDTO newCTPN : listTemp)
				 sanPhamBUS.updateSoLuong(newCTPN.getMaSP(), newCTPN.getSoLuong());
			 
		   //Dang sua phieu nhap
			 phieuNhapBUS.updatePN(maPN, NV, NCC, tongTien, ngayNhap);
			 chiTietPNBUS.updateCTPN(maPN, listTemp);
			 
			 int selectedRow = tbPhieuNhap.getSelectedRow();
			  if (selectedRow != -1) {
				  modelPhieuNhap.setValueAt(NV, selectedRow, 1);
				  modelPhieuNhap.setValueAt(NCC, selectedRow, 2);
	              modelPhieuNhap.setValueAt(tongTien, selectedRow, 3);
	              modelPhieuNhap.setValueAt(ngayNhap, selectedRow, 4);
			  }
		}else {
			//them moi phieu nhap
			phieuNhapBUS.addPN(maPN, NV, NCC, tongTien, ngayNhap);
			 for(ChiTietPNDTO ctpn : listTemp) 
			    	chiTietPNBUS.addCTPN(maPN, ctpn.getMaSP(), ctpn.getSoLuong(), ctpn.getDonGia(), ctpn.getThanhTien());
			 for(ChiTietPNDTO ctpn : listTemp)
				 sanPhamBUS.updateSoLuong(ctpn.getMaSP(), ctpn.getSoLuong());
			    
			    modelPhieuNhap.addRow(new Object[] { maPN, NV, NCC, tongTien, ngayNhap });
			    
			    
			    JOptionPane.showMessageDialog(this, "Thêm phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}
		 clear();
		
	}
	
//////////////////////////////////////////DELETE PHIEU NHAP/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deletePN() {
		  int selectedRow = tbPhieuNhap.getSelectedRow();
		    if (selectedRow == -1) {
		        JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        tbPhieuNhap.requestFocus();
		        return;
		    }
		    
		    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu nhập này? Tất cả chi tiết phiếu nhập cũng sẽ bị xóa.", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
		    if (confirm != JOptionPane.YES_OPTION) {
		    	return;
		    }
		    String maPN = (String) modelPhieuNhap.getValueAt(selectedRow, 0);
		    //giam so luong sp sau khi xoa 
		    ArrayList<ChiTietPNDTO> list = chiTietPNBUS.getChiTietTheoMaPhieu(maPN);
		    for(ChiTietPNDTO ctpn : list)
		    	sanPhamBUS.updateSoLuong(ctpn.getMaSP(), -ctpn.getSoLuong());
		    //////////////////
		    chiTietPNBUS.deleteCTPN(maPN);
		    
		    phieuNhapBUS.deletePN(maPN);
		    
		    modelPhieuNhap.removeRow(selectedRow);
		    
		    clear();
		    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	

//	public DefaultTableModel updateTableCTPN(DefaultTableModel model) {
//		model.setRowCount(0); 
//		
//		for(ChiTietPNDTO ctpn : chiTietPNBUS.getListCTPN())
//			model.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});
//		return model;
//	}
	
	
	public DefaultTableModel updateTablePN() {
		txtSearch.setText("");
		dateEnd.setDate(null);
		dateStart.setDate(null);
		
		modelPhieuNhap.setRowCount(0);
		
		for(PhieuNhapDTO pn : phieuNhapBUS.listPN)
			modelPhieuNhap.addRow(new Object[] {pn.getMaPhieuNH(),pn.getMaNV(), pn.getMaNCC(),pn.getTongTien(),pn.getNgayNhap()});
		return modelPhieuNhap;
	}
	
	
	public void loadChiTietPN(String maPN) {
		ArrayList<ChiTietPNDTO> dsCTPN = chiTietPNBUS.getChiTietTheoMaPhieu(maPN); 
		
		modelCTPN.setRowCount(0);
		
		for(ChiTietPNDTO ctpn : dsCTPN)
			modelCTPN.addRow(new Object[] {ctpn.getMaPhieuNH(),ctpn.getMaSP(), ctpn.getSoLuong(),ctpn.getDonGia(),ctpn.getThanhTien()});
	}

	
	public void getSelectedRowTbPhieuNhap() {
		int selectedRow = tbPhieuNhap.getSelectedRow();
		if(selectedRow!=-1 && update == false)
		{
			String maPN = (String) modelPhieuNhap.getValueAt(selectedRow, 0);
			loadChiTietPN(maPN);
		}
	}
	
	
	public void getInforFromTbCTPN() {
		int selectedRowPN = tbPhieuNhap.getSelectedRow();
		int selectedRowCTPN = tbCTPN.getSelectedRow();
		if(selectedRowCTPN != -1 && selectedRowPN != -1 && listTemp.isEmpty() && update == false && add == false) {
			String maPN = (String) tbPhieuNhap.getValueAt(selectedRowPN,0);
			String NV = (String) tbPhieuNhap.getValueAt(selectedRowPN, 1);
			String NCC = (String) tbPhieuNhap.getValueAt(selectedRowPN, 2);
			String SP = (String) tbCTPN.getValueAt(selectedRowCTPN, 1);
			int soLuong = (int) tbCTPN.getValueAt(selectedRowCTPN, 2);
			double donGia = (double) tbCTPN.getValueAt(selectedRowCTPN, 3);
			
			txtMaPN.setText(maPN);
			txtNV.setText(NV);
			txtNCC.setText(NCC);
			txtSP.setText(SP);
			txtSoLuong.setText(String.valueOf(soLuong));
			txtDonGia.setText(String.valueOf(donGia));
			
			txtMaPN.setEditable(false);
			txtMaPN.setFocusable(false);
			txtSoLuong.setEditable(false);
			txtSoLuong.setFocusable(false);
			txtDonGia.setEditable(false);
			txtDonGia.setFocusable(false);
			
			btnOpenNVList.setVisible(false);
			btnOpenNCCList.setVisible(false);
			btnOpenSPList.setVisible(false);
			btnComplete.setVisible(false);
			
		}

	}
	//Tim kiem nang cao va co ban
	public void timKiem() {
		String tieuChi = cboxSearch.getSelectedItem().toString();
		String tuKhoa = txtSearch.getText().trim();
		ArrayList<PhieuNhapDTO> result;
		
		java.util.Date utilStartDate = dateStart.getDate(); // java.util.Date
        java.util.Date utilEndDate = dateEnd.getDate();
        
        boolean isTimKiemCoBan = !tuKhoa.isEmpty();
        boolean isTimKiemTheoNgay = utilStartDate != null && utilEndDate != null;
        
        int count = 0;
        if (isTimKiemCoBan) count++;
        if (isTimKiemTheoNgay) count++;
        
        if (count > 1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chỉ chọn một loại tìm kiếm: cơ bản hoặc theo ngày", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        
		if(isTimKiemCoBan) {
			result = phieuNhapBUS.search(tuKhoa, tieuChi);
		}
		else if(isTimKiemTheoNgay) {
         // reset tg ve 0 0 0 0
            Calendar cal = Calendar.getInstance();
            cal.setTime(utilStartDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date startDate = new Date(cal.getTimeInMillis());

            cal.setTime(utilEndDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date endDate = new Date(cal.getTimeInMillis());
            
            	if (startDate.after(endDate)) {
	                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
			 result = phieuNhapBUS.searchByDate(startDate, endDate);
		}
		else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin để tìm kiếm !", "Lỗi", JOptionPane.ERROR_MESSAGE);
       	 	return;
		}
		
		
		modelPhieuNhap.setRowCount(0);
		for(PhieuNhapDTO pn : result)
			modelPhieuNhap.addRow(new Object[] {pn.getMaPhieuNH(),pn.getMaNV(), pn.getMaNCC(),pn.getTongTien(),pn.getNgayNhap()});
		if(result.isEmpty())
    		JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public void clear() {
		txtMaPN.setText("");
		txtNV.setText("");
		txtNCC.setText("");
		txtSP.setText("");
		txtSoLuong.setText("");
		txtDonGia.setText("");
		
		tbPhieuNhap.clearSelection();
		tbCTPN.clearSelection();
		
		modelCTPN.setRowCount(0);
		
		txtMaPN.setEditable(true);
		txtMaPN.setFocusable(true);
		txtSoLuong.setEditable(true);
		txtSoLuong.setFocusable(true);
		txtDonGia.setEditable(true);
		txtDonGia.setFocusable(true);
		
		btnOpenNVList.setVisible(true);
		btnOpenNCCList.setVisible(true);
		btnOpenSPList.setVisible(true);
		
		btnAddProduct.setVisible(false);
		btnEditProduct.setVisible(false);
		btnDeleteProduct.setVisible(false);
	    btnComplete.setVisible(false);
	    update = false;//bien giup cho su kien xem details chi thuc hien duoc khi click vao xem thong tin
	    add = false;
	    
	    txtMaPN.setForeground(new Color(60,60,60));
	    
		listTemp.clear();
	}
	
	  public void xuatExcel()
	    {
	    	try {
	            ExcelExporter.exportJTableToExcel(tbPhieuNhap);
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
	                    "Lỗi", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace();
	        }
	    }
	    
	    public void xuatPDF() {
	        int selectedRow = tbPhieuNhap.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xuất PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            tbPhieuNhap.requestFocus();
	            return;
	        }

	        String maPN = (String) modelPhieuNhap.getValueAt(selectedRow, 0);
	        try {
	            PDFReporter pdfReporter = new PDFReporter();
	            pdfReporter.writePhieuNhap(maPN);
	            JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	            ex.printStackTrace();
	        }
	    }
	
  
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Cancel"))
			clear();
		else if (command.equals("Thêm"))
			addCTPN();
		else if(command.equals("Sửa"))
			editPN();
		else if (command.equals("Xóa"))
			deletePN();
		else if(command.equals("Reload"))
			updateTablePN();
		else if(command.equals("Tìm kiếm"))
			timKiem();
		else if(command.equals("Xuất Excel"))
			xuatExcel();
		else if(command.equals("Xuất PDF"))
			xuatPDF();
	}
}
