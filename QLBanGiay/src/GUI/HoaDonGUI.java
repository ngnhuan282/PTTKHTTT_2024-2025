package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import BUS.CTKMBUS;
import BUS.ChiTietHoaDonBUS;
import BUS.HoaDonBUS;
import BUS.KMSPBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import DTO.CTKMDTO;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;
import DTO.KMSPDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextField;

public class HoaDonGUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int DEFAULT_WIDTH = 1200, DEFAULT_HEIGHT= 800;
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
	private JButton btnUpdateBillDetail;
	
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
	private String  maSPTruocKhiSua;
	private int soLuongKhiSua;

	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame("Hóa đơn");
		frame.setTitle("Hệ thống quản lý bán giày");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1248, 757);
		frame.getContentPane().add(new HoaDonGUI());
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public HoaDonGUI() throws SQLException {
		hoaDonBUS = new HoaDonBUS();
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
		initComponents();
	}
	
	/**
	 * 
	 */
	public void initComponents() {
		JPanel pHeaderMain = new JPanel();
		pHeaderMain.setBounds(0, 0, 1206, 100);
		pHeaderMain.setBackground(Color.WHITE);
		add(pHeaderMain);
		
		
		/************* PHẦN HEADER ************/
		pHeaderMain.setPreferredSize(new Dimension(1245, 815));
		pHeaderMain.setLayout(null);
		
		JPanel pLeftHeader = new JPanel();
		 pLeftHeader.setBorder(new TitledBorder(null, "Chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pLeftHeader.setBackground(Color.WHITE);
		pLeftHeader.setBounds(0, 0, 542, 90);
		pHeaderMain.add(pLeftHeader);
		pLeftHeader.setLayout(null);
		
		/************* PHẦN CHỨC NĂNG ************/
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
                                        
                                        txtSearch = new JTextField();
                                        txtSearch.setBounds(694, 31, 243, 39);
                                        pHeaderMain.add(txtSearch);
                                        txtSearch.setColumns(10);
                                        
                                        JButton btnLamMoi = new JButton("Làm mới");
                                        btnLamMoi.setBackground(Color.WHITE);
                                        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
                                        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
                                        btnLamMoi.setBounds(1023, 30, 126, 39);
                                        btnLamMoi.setActionCommand("Reload");
                                        btnLamMoi.addActionListener(e -> {
                                        	btnLamMoi.setVisible(false);
                                        	openBillTable();
                                        });
                                        btnLamMoi.setVisible(false);
                                        pHeaderMain.add(btnLamMoi);
                                        
                                        String[] keysearch = {"Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập"};
                                        JComboBox<String> cboxSearch = new JComboBox<String>(keysearch);
                                        cboxSearch.setFont(new Font("Arial", Font.PLAIN, 14));
                                        cboxSearch.setBounds(552, 29, 132, 41);
                                        cboxSearch.setBackground(Color.WHITE); // nền trắng
                                        cboxSearch.setForeground(Color.BLACK); // chữ đen cho dễ đọc
                                        cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // viền xám nhạt
                                        pHeaderMain.add(cboxSearch);
                                        
                                        JButton btnSearch = new JButton("");
                                        btnSearch.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/search30.png")));
                                        btnSearch.setBounds(947, 31, 66, 39);
                                        btnSearch.addActionListener(e -> {
                                        	String key = cboxSearch.getSelectedItem().toString();
                                        	String keyword = txtSearch.getText();
                                        	ArrayList<HoaDonDTO> result = hoaDonBUS.search(key, keyword);
                                        	if(result.isEmpty()) {
                                        		JOptionPane.showMessageDialog(this, "Không có kết quả phù hợp", "Thông báo", JOptionPane.WARNING_MESSAGE);
                                    			return;
                                        	}
                                        	
                                        	txtSearch.setText("");
                                        	btnLamMoi.setVisible(true);
                                        	openBillTable(result);
                                        	
                                        });
                                        pHeaderMain.add(btnSearch);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 91, 732, 374);
		pHeaderMain.add(panel);
		panel.setLayout(null);
		
//        scrollPane.getVerticalScrollBar().setUI(new MetalScrollBarUI());
//        scrollPane.getHorizontalScrollBar().setUI(new MetalScrollBarUI());
		
		table =  new JTable();
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
		jScrollPane.setBounds(0, 0, 732, 375);
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(732, 91, 445, 374);
		pHeaderMain.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Thông tin hóa đơn");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(108, 0, 201, 51);
		panel_3.add(lblNewLabel);
		
		txtMaHD = new JTextField();
		txtMaHD.setBounds(148, 61, 148, 31);
		panel_3.add(txtMaHD);
		txtMaHD.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Mã hóa đơn");
		lblNewLabel_1.setLabelFor(txtMaHD);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(20, 60, 83, 31);
		panel_3.add(lblNewLabel_1);
		
		JLabel lbMaKH = new JLabel("Mã khách hàng");
		lbMaKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbMaKH.setBounds(20, 142, 103, 31);
		panel_3.add(lbMaKH);
		
		txtMaKH = new JTextField();
		lbMaKH.setLabelFor(txtMaKH);
		txtMaKH.setColumns(10);
		txtMaKH.setBounds(148, 143, 148, 31);
		txtMaKH.setEditable(false);
		panel_3.add(txtMaKH);
		
		JButton btnOpenMaKHList = new JButton("...");
		
		btnOpenMaKHList.setBounds(308, 148, 21, 21);
		panel_3.add(btnOpenMaKHList);
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
			for(KhachHangDTO x : khachHangBUS.getListKhachHang()) {
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
     				// TODO Auto-generated method stub
     				String keyword = txtSearchMa.getText().trim().toLowerCase();
     				modelMaKHList.setRowCount(0);
     				for(KhachHangDTO nv : khachHangBUS.getListKhachHang()) {
     					if(nv.getMaKH().toLowerCase().contains(keyword))
     						modelMaKHList.addRow(new Object[]{nv.getMaKH(), nv.getHo(), nv.getTen(), nv.getSdt()});
     				}
     			}
     		}); 
		});
		
		JLabel lblMaNV = new JLabel("Mã nhân viên");
		lblMaNV.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMaNV.setBounds(20, 101, 93, 31);
		panel_3.add(lblMaNV);
		
		txtMaNV = new JTextField();
		lblMaNV.setLabelFor(txtMaNV);
		txtMaNV.setColumns(10);
		txtMaNV.setBounds(148, 102, 148, 31);
		txtMaNV.setEditable(false);
		panel_3.add(txtMaNV);
		
		JButton btnOpenMaNVList = new JButton("...");
		btnOpenMaNVList.setBounds(308, 107, 21, 21);
		panel_3.add(btnOpenMaNVList);
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
			for(NhanVienDTO x : nhanVienBUS.getListNhanVien()) {
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
      				// TODO Auto-generated method stub
      				String keyword = txtSearchMa.getText().trim().toLowerCase();
      				modelMaNVList.setRowCount(0);
      				for(NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
      					if(nv.getMaNV().toLowerCase().contains(keyword))
      						modelMaNVList.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
      				}
      			}
      		}); 
		});

		
		JLabel lblMSnPhm = new JLabel("Mã sản phẩm");
		lblMSnPhm.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMSnPhm.setBounds(20, 183, 93, 31);
		panel_3.add(lblMSnPhm);
		
		txtMaSP = new JTextField();
		lblMSnPhm.setLabelFor(txtMaSP);
		txtMaSP.setColumns(10);
		txtMaSP.setBounds(148, 184, 148, 31);
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
			for(SanPhamDTO x : sanPhamBUS.getDssp()) {
				Object[] row = {
						x.getMaSP(),
						(kmspBUS.checkMaSPKM(x.getMaSP())) ? x.getTenSP() + "(Sale)" : x.getTenSP(),
						(kmspBUS.checkMaSPKM(x.getMaSP())) ? Math.round((x.getDonGia() - x.getDonGia()*kmspBUS.getPhanTram(x.getMaSP())/100.0)) : x.getDonGia(),
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
            		 if(tonHang.equals("Hết hàng")) {
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
       				// TODO Auto-generated method stub
       				String keyword = txtSearchMa.getText().trim().toLowerCase();
       				modelMaSpList.setRowCount(0);
       				for(SanPhamDTO x : sanPhamBUS.getDssp()) {
       					if(x.getMaSP().toLowerCase().contains(keyword))
       						modelMaSpList.addRow(new Object[]{x.getMaSP(), (kmspBUS.checkMaSPKM(x.getMaSP())) ? x.getTenSP() + "(Sale)" : x.getTenSP(), (kmspBUS.checkMaSPKM(x.getMaSP())) ? Math.round((x.getDonGia() - x.getDonGia()*kmspBUS.getPhanTram(x.getMaSP())/100.0)) : x.getDonGia(), x.getMaLoaiSP(), (x.getSoLuong() == 0) ? "Hết hàng" : x.getSoLuong()});
       				}
       			}
       		}); 
             
		});
		btnOpenMaSPList.setBounds(308, 189, 21, 21);
		panel_3.add(btnOpenMaSPList);
		
		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSoLuong.setBounds(20, 224, 93, 31);
		panel_3.add(lblSoLuong);
		
		txtSoLuong = new JTextField();
		lblSoLuong.setLabelFor(txtSoLuong);
		txtSoLuong.setColumns(10);
		txtSoLuong.setBounds(148, 225, 148, 31);
		panel_3.add(txtSoLuong);
		
		btnComplete = new JButton("Hoàn tất hóa đơn");
		btnComplete.setBackground(Color.WHITE);
		btnComplete.setActionCommand("Hoàn tất hóa đơn");
		btnComplete.setBounds(10, 287, 154, 42);
		panel_3.add(btnComplete);
		
		btnAddProduct = new JButton("Thêm sản phẩm");
		btnAddProduct.setBounds(175, 287, 154, 42);
		btnAddProduct.setBackground(Color.WHITE);
		btnAddProduct.setActionCommand("Thêm sản phẩm");
		panel_3.add(btnAddProduct);
		
		
		btnCancel = new JButton("Hủy");
		btnCancel.setBounds(332, 287, 103, 42);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setActionCommand("Hủy");
		panel_3.add(btnCancel);
		
		btnUpdateBillDetail = new JButton("Sửa hóa đơn");
		 btnUpdateBillDetail.setBackground(Color.WHITE);
		 btnUpdateBillDetail.setBounds(175, 287, 154, 42);
		 btnUpdateBillDetail.addActionListener(e -> {
			 	if(txtMaHD.getText().equals("") || txtMaKH.getText().equals("") || txtMaNV.getText().equals("") || txtMaSP.getText().equals("") || txtSoLuong.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
					return;
				}
			 	if(chiTietHoaDonBUS.checkDulicate(txtMaHD.getText(), txtMaSP.getText()) && !txtMaSP.getText().equals(listTemp.get(h).getMaSP())) {
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
				
				chiTietHoaDonBUS.deleteCTHDByIndex(k);
				chiTietHoaDonBUS.addCTHD(chiTietHDDTO.getMaHD(), chiTietHDDTO.getMaSP(), chiTietHDDTO.getSoLuong(), chiTietHDDTO.getDonGia(), chiTietHDDTO.getThanhTien());
//				chiTietHoaDonBUS.updateSoLuongSP(chiTietHDDTO.getMaSP(), chiTietHDDTO.getSoLuong() - soLuongTruocKhiUpdate.get(h));
//				soLuongTruocKhiUpdate.set(h, chiTietHDDTO.getSoLuong());
				
				selectedRowCTHD = table_1.getSelectedRow();
				k = 0;
				donGia = 0;
				thanhTien = 0;
				 btnAddProduct.setVisible(true);
				 btnUpdateBillDetail.setVisible(false);
				 txtMaNV.setEditable(false);
				 txtMaSP.setText("");
				 txtSoLuong.setText("");
				 JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				 openBillDetailTable();
				 if(!maSP.equals(maSPTruocKhiSua)) {
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
			if(txtMaHD.getText().equals("") || txtMaKH.getText().equals("") || txtMaNV.getText().equals("") || txtMaSP.getText().equals("") || txtSoLuong.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hóa đơn", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(chiTietHoaDonBUS.checkDulicate(txtMaHD.getText(), txtMaSP.getText())) {
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
			
//			chiTietHoaDonBUS.addCTHD(maHD, maSP, soLuong, donGia, thanhTien);
			listTemp.add(new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien));
			chiTietHoaDonBUS.addCTHD(maHD, maSP, soLuong, donGia, thanhTien);
			donGia = 0;
			thanhTien = 0;
			JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			txtSoLuong.setText("");
			txtMaSP.setText("");
			openBillDetailTable();
		});
		
		
		btnComplete.addActionListener(e -> {
			if(listTemp.size() == 0) {
				JOptionPane.showMessageDialog(this, "Bạn cần thêm sản phẩm vào hóa đơn trước khi hoàn tất", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			LocalDate now = LocalDate.now();
			Date ngayLap = Date.valueOf(now);
			if(!update) {
				int i = 0;
				for(ChiTietHDDTO x : listTemp) {
					tongTien += x.getThanhTien();
				}
				
				try {
					if(ctkmBUS.getCTKM_HD(ngayLap) != null) {
						CTKMDTO ctkmDTO = ctkmBUS.getCTKM_HD(ngayLap);
						tongTien = tongTien - tongTien * (ctkmDTO.getPhanTramGiamGia()/100.0);
						
						JOptionPane.showMessageDialog(this, "Nhân dịp khuyến mãi "+ ctkmDTO.getTenCTKM() +" hóa đơn sẽ được giảm "+ ctkmDTO.getPhanTramGiamGia() +"% trên tổng giá trị hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				hoaDonBUS.addHoaDon(txtMaHD.getText(), txtMaKH.getText(), txtMaNV.getText(), ngayLap, Math.round(tongTien * 100.0)/100.0);
				for(ChiTietHDDTO x : listTemp) {
					chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong() - soLuongTruocKhiUpdate.get(i));
					if(chiTietHoaDonBUS.checkDulicateMaSP(x.getMaSP(), x.getMaHD())) {
						i++;
						continue;
					}
					chiTietHoaDonBUS.addCTHD(x.getMaHD(), x.getMaSP(), x.getSoLuong(), x.getDonGia(), x.getThanhTien());
					chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong());
					i++;
				}
			}
			else {
				int selectedRow = table.getSelectedRow();
				int i = 0;
				
				for(int index = 0; index < soLuongCanTang.size(); index++) {
					String maSP = maSPCanTang.get(index);
					chiTietHoaDonBUS.updateSoLuongSP(maSP, -soLuongCanTang.get(index));
				}
				
				for(ChiTietHDDTO x : listTemp) {
					
					tongTien += x.getThanhTien();
					chiTietHoaDonBUS.updateSoLuongSP(x.getMaSP(), x.getSoLuong() - soLuongTruocKhiUpdate.get(i));
//					System.out.println("x.getSoLuong " + x.getSoLuong());
//					System.out.println("soLuongTruockhiupdate "+ soLuongTruocKhiUpdate.get(i));
//					System.out.println(x.getSoLuong() - soLuongTruocKhiUpdate.get(i));
					soLuongTruocKhiUpdate.set(i, x.getSoLuong());
					if(chiTietHoaDonBUS.checkDulicateMaSP(x.getMaSP(), x.getMaHD())) {
						i++;
						continue;
					}
					chiTietHoaDonBUS.addCTHD(x.getMaHD(), x.getMaSP(), x.getSoLuong(), x.getDonGia(), x.getThanhTien());
					i++;
				}
				
				try {
					if(ctkmBUS.getCTKM_HD(ngayLap) != null) {
						CTKMDTO ctkmDTO = ctkmBUS.getCTKM_HD(ngayLap);
						tongTien = tongTien - tongTien * (ctkmDTO.getPhanTramGiamGia()/100.0);
						
						JOptionPane.showMessageDialog(this, "Nhân dịp khuyến mãi "+ ctkmDTO.getTenCTKM() +" hóa đơn sẽ được giảm "+ ctkmDTO.getPhanTramGiamGia() +"% trên tổng giá trị hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
						
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				hoaDonBUS.updateHoaDon(txtMaHD.getText(), txtMaKH.getText(), txtMaNV.getText(), ngayLap, Math.round(tongTien * 100.0)/100.0, selectedRow);
				update = false;
				soLuongTruocKhiUpdate.clear();
				soLuongCanTang.clear();
				maSPCanTang.clear();
				updateRow.clear();
			}
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
			if(listTemp.size() == 0) {
				txtMaHD.setText("");
				txtMaSP.setText("");
				txtMaNV.setText("");
				txtMaKH.setText("");
				txtSoLuong.setText("");
				txtMaSP.setText("");
			}
			else {
				int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy hóa đơn này?", "Thông báo", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
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
					update = false;
					openBillDetailTable();
				}
			}
		});
		
		table_1.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				k = 0;
				h = 0;
				 selectedRowCTHD = table_1.getSelectedRow();
				 ChiTietHDDTO chiTietHDDTO;
				 if(selectedRowCTHD != -1) {
					 if(hoaDonBUS.getListHoaDon().size() != 0)
						 txtMaNV.setText(table.getValueAt(selectedRowHoaDon, 2).toString());
					 txtMaSP.setText(table_1.getValueAt(selectedRowCTHD, 1).toString());
					 txtSoLuong.setText(table_1.getValueAt(selectedRowCTHD, 2).toString());
					 chiTietHDDTO = new ChiTietHDDTO(txtMaHD.getText(), txtMaSP.getText(), Integer.valueOf(txtSoLuong.getText()), Double.valueOf(table_1.getValueAt(selectedRowCTHD, 3).toString()), Double.valueOf(table_1.getValueAt(selectedRowCTHD, 4).toString()));
					 maSPTruocKhiSua = txtMaSP.getText();
					 soLuongKhiSua = Integer.valueOf(txtSoLuong.getText());
					 for(ChiTietHDDTO x : listTemp) {
						 if(x.equals(chiTietHDDTO)) {
							 break;
						 }
						 h++;
					 }
					 
					 for(ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
						 if(x.equals(chiTietHDDTO)) {
							 break;
						 }
						 k++;
					 }
					 donGia = Double.valueOf(table_1.getValueAt(selectedRowCTHD, 3).toString());
					 btnAddProduct.setVisible(false);
					 btnUpdateBillDetail.setVisible(true);
				 }
				 
			 }
		});
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				update = false;
				openUpdateBill();
//				txtMaKH.setEditable(true);
//				txtMaNV.setEditable(true);
//				txtMaHD.setEditable(true);
			}
		});
	}
	
	private void openBillDetailTable() {
		String[] columnNameBillDetail = { "Mã hóa đơn", "Mã sản phẩm", "Số lưọng", "Đơn giá", "Thành tiền" };
		DefaultTableModel model1 = new DefaultTableModel(columnNameBillDetail, 0);
		
		for(ChiTietHDDTO x : listTemp) {
			Object[] row = {
					x.getMaHD(),
					x.getMaSP(),
					x.getSoLuong(),
					x.getDonGia(),
					x.getThanhTien()
			};
			model1.addRow(row);
		}
		
		table_1.setModel(model1);

	}
	
	private void openBillTable(ArrayList<HoaDonDTO> result) {
		// Dữ liệu mẫu (ví dụ về sản phẩm)
		String[] columnNamesBill = { "Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Mã nhân viên", "Ngày lập", "Tổng tiền" };

		// Tạo DefaultTableModel với dữ liệu mẫu
		DefaultTableModel model = new DefaultTableModel(columnNamesBill, 0);
		for(HoaDonDTO x : result) {
			Object[] row = {
					x.getMaHD(),
					x.getMaKH(),
					x.getMaNV(),
					x.getNgayLap(),
					x.getTongTien()
			};
			model.addRow(row);
		}
		

		// Gán model vào JTable

		table.setModel(model);
		 Font font = new Font("Verdana", Font.PLAIN, 14);
		table.setFont(font);
	}
	
	private void openBillTable() {
		// Dữ liệu mẫu (ví dụ về sản phẩm)
		String[] columnNamesBill = { "Mã hóa đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Tổng tiền" };

		// Tạo DefaultTableModel với dữ liệu mẫu
		DefaultTableModel model = new DefaultTableModel(columnNamesBill, 0);
		for(HoaDonDTO x : hoaDonBUS.getListHoaDon()) {
			Object[] row = {
					x.getMaHD(),
					x.getMaKH(),
					x.getMaNV(),
					x.getNgayLap(),
					x.getTongTien()
			};
			model.addRow(row);
		}
		

		// Gán model vào JTable

		table.setModel(model);
		 Font font = new Font("Verdana", Font.PLAIN, 14);
		table.setFont(font);
	}
	
	public void openAddBill() {
		if(listTemp.size() != 0) {
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
		if(hoaDonBUS.getListHoaDon().size() == 0) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(selectedRowHoaDon == -1 ) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String maHD = (String) table.getValueAt(selectedRowHoaDon, 0);
		String maKH = (String) table.getValueAt(selectedRowHoaDon, 1);
		for(ChiTietHDDTO x : chiTietHoaDonBUS.getListCTHD()) {
			if(x.getMaHD().equals(maHD)) {
				listTemp.add(new ChiTietHDDTO(x.getMaHD(), x.getMaSP(), x.getSoLuong(), x.getDonGia(), x.getThanhTien()));
				soLuongTruocKhiUpdate.add(x.getSoLuong());
			}
		}
		txtMaHD.setText(maHD);
		txtMaKH.setText(maKH);
		update = true;
		openBillDetailTable();
		
	}
	
	public void openDeleteBill() {
		if(hoaDonBUS.getListHoaDon().size() == 0) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(selectedRowHoaDon == -1 ) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để sửa", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		btnAddProduct.setVisible(true);
		btnUpdateBillDetail.setVisible(false);
		int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hóa đơn này?", "Thông báo", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			selectedRowHoaDon = table.getSelectedRow();
			selectedRowCTHD = table_1.getSelectedRow();
			if(selectedRowHoaDon != -1 && selectedRowCTHD == -1) {
				String maHD = (String) table.getValueAt(selectedRowHoaDon, 0);
				chiTietHoaDonBUS.deleteCTHD(maHD);
				hoaDonBUS.deleteHoaDon(selectedRowHoaDon);
				listTemp.clear();
				openBillDetailTable();
				openBillTable();
				update = false;
				txtMaHD.setText("");
				JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
			else if(selectedRowCTHD != -1) {
				ChiTietHDDTO chiTietHDDTO = listTemp.get(h);
				chiTietHoaDonBUS.deleteCTHDByIndex(k, chiTietHDDTO.getMaSP());
				listTemp.remove(h);
				tongTien = 0;
				for(ChiTietHDDTO x : listTemp) {
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
	
	public void xuatPDF() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập để xuất PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            table.requestFocus();
            return;
        }

        String maHD = (String) table.getValueAt(selectedRow, 0);
        try {
            PDFReporter pdfReporter = new PDFReporter();
            pdfReporter.writeHoaDon(maHD);
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		
		if(str.equals("Thêm")) {
			openAddBill();
		}
		else if(str.equals("Sửa")) {
			if(!update) {
				openUpdateBill();
			}else {
				return;
			}
		}
		else if(str.equals("Xóa")) {
			openDeleteBill();
		}
		else if(str.equals("Xuất Excel")) {
			xuatExcel();
		}
		else if(str.equals("Xuất PDF")) {
			xuatPDF();
		}
	}
	
	public void xuatExcel()
	{
		try {
            ExcelExporter.exportJTableToExcel(table);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
}
