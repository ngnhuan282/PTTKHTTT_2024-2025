package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import BUS.KhachHangBUS;
import BUS.SanPhamBUS;
import DAO.SanPhamDAO;
import DTO.KhachHangDTO;
import DTO.SanPhamDTO;

import java.awt.Panel;
import javax.swing.JTree;
import java.awt.List;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EtchedBorder;

public class KhachHangGUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private int DEFAULT_WIDTH = 1200, DEFAULT_HEIGHT= 800;
	private String color = "#FF5252";
	private JTable table;
	private JTable table_1;
	private KhachHangBUS khachHangBUS;
	private JTextField txtSearch;
	private JTextField txtMaKH;
	private JTextField txtTen;
	private JTextField txtHo;
	private JTextField txtSDT;
	private JTextField txtDiaChi;
	private JButton btnEditMode;
	private boolean isEditMode = false;


	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame("Hóa đơn");
		frame.setTitle("Hệ thống quản lý bán giày");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1248, 757);
		frame.getContentPane().add(new KhachHangGUI());
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public KhachHangGUI() throws SQLException {
		khachHangBUS = new KhachHangBUS();
		initComponents();
	}
	
	public void initComponents() {
//		setTitle("Hệ thống quản lý bán giày");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//		setLocationRelativeTo(null);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, DEFAULT_WIDTH, 757);
		pMain.setPreferredSize(new Dimension(1250, 815));
		pMain.setLayout(new BoxLayout(pMain, BoxLayout.X_AXIS));
		add(pMain);
		
		JPanel pHeaderMain = new JPanel();
		pHeaderMain.setBackground(Color.WHITE);
		pMain.add(pHeaderMain);
		
		
		/************* PHẦN HEADER ************/
		pHeaderMain.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));
		pHeaderMain.setLayout(null);
		
		
		/************* PHẦN CHỨC NĂNG ************/
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
        horizontalBox.add(btnThem);
        btnThem.setFocusPainted(false);
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
        btnSua.setFocusPainted(false);
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
        btnXoa.setFocusPainted(false);
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

        JButton btnNhapExcel = new JButton("Nhập Excel");
        horizontalBox.add(btnNhapExcel);
        btnNhapExcel.setActionCommand("Nhập Excel");
        btnNhapExcel.addActionListener(this);
        btnNhapExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNhapExcel.setPreferredSize(new Dimension(120, 140));
        btnNhapExcel.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/xuatexcel48.png")));
        btnNhapExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNhapExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNhapExcel.setBorderPainted(false);
        btnNhapExcel.setBackground(Color.WHITE);
		
        txtSearch = new JTextField();
        txtSearch.setBounds(700, 30, 237, 27);
        pHeaderMain.add(txtSearch);
        txtSearch.setColumns(10);
        
        String[] keySearch = {"Mã khách hàng", "Họ tên", "SĐT"};
        JComboBox<String> cboxSearch = new JComboBox<String>(keySearch);
        cboxSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        cboxSearch.setBounds(524, 30, 150, 28);
        cboxSearch.setBackground(Color.WHITE);
        cboxSearch.setForeground(Color.BLACK);
        cboxSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pHeaderMain.add(cboxSearch);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1045, 31, 126, 28);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(e -> {
        	btnLamMoi.setVisible(false);
        	fillTableWithSampleData();
        });
        btnLamMoi.setVisible(false);
        pHeaderMain.add(btnLamMoi);
        
        JButton btnSearch = new JButton("");
        btnSearch.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/search30.png")));
        btnSearch.setBounds(960, 22, 63, 39);
        btnSearch.addActionListener(e -> {
        	String key = cboxSearch.getSelectedItem().toString();
        	String keyword = txtSearch.getText();
        	ArrayList<KhachHangDTO> result = khachHangBUS.searchCustomer(key, keyword);
        	if(result.isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Không có kết quả phù hợp", "Thông báo", JOptionPane.WARNING_MESSAGE);
    			return;
        	}
        	txtSearch.setText("");
        	btnLamMoi.setVisible(true);
        	fillTableWithSampleData(result);
        	
        });
        pHeaderMain.add(btnSearch);
		
  
        
		table = new JTable();
		table.setFont(new Font("Verdana", Font.PLAIN, 12));
  		table.setGridColor(new Color(200, 200, 200));
  		table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
  		
  		table.setRowHeight(23);
  		table.getTableHeader().setPreferredSize(new Dimension(0, 23));
  		table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));	
  		
  		//can giua
  		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
  		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
  		table.setDefaultRenderer(Object.class, centerRenderer);
		JScrollPane scrollPane = new JScrollPane(table);
		pHeaderMain.add(scrollPane);
		scrollPane.setBounds(265, 110, 965, 655);
		scrollPane.setFont(new Font("Verdana", Font.PLAIN, 14));

		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		panel.setBackground(Color.WHITE);
		panel.setBounds(2, 110, 265, 655);
		pHeaderMain.add(panel);
		panel.setLayout(null);
		
		JLabel lbMaKH = new JLabel("Mã khách hàng");
		lbMaKH.setFont(new Font("Verdana", Font.BOLD, 12));
		lbMaKH.setBounds(10, 41, 100, 23);
		panel.add(lbMaKH);
		
		txtMaKH = new JTextField();
		txtMaKH.setBounds(10, 66, 245, 32);
		txtMaKH.setColumns(10);
		panel.add(txtMaKH);
		
		JLabel lbHo = new JLabel("Họ Lót");
		lbHo.setFont(new Font("Verdana", Font.BOLD, 12));
		lbHo.setBounds(10, 121, 100, 23);
		panel.add(lbHo);
		
		txtHo = new JTextField();
		txtHo.setColumns(10);
		txtHo.setBounds(10, 146, 245, 32);
		panel.add(txtHo);
		
		JLabel lbTen = new JLabel("Tên");
		lbTen.setFont(new Font("Verdana", Font.BOLD, 12));
		lbTen.setBounds(10, 201, 100, 23);
		panel.add(lbTen);
		
		txtTen = new JTextField();
		txtTen.setColumns(10);
		txtTen.setBounds(10, 226, 245, 32);
		panel.add(txtTen);
		
		JLabel lbSDT = new JLabel("Số điện thoại");
		lbSDT.setFont(new Font("Verdana", Font.BOLD, 12));
		lbSDT.setBounds(10, 281, 100, 23);
		panel.add(lbSDT);
		
		txtSDT = new JTextField();
		txtSDT.setColumns(10);
		txtSDT.setBounds(10, 306, 245, 32);
		panel.add(txtSDT);
		
		JLabel lbDiaChi = new JLabel("Địa chỉ");
		lbDiaChi.setFont(new Font("Verdana", Font.BOLD, 12));
		lbDiaChi.setBounds(10, 361, 122, 23);
		panel.add(lbDiaChi);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(10, 386, 245, 32);
		
		btnEditMode = new JButton("");
		btnEditMode.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit20.png")));
		btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.setBounds(215, 15, 37, 20);
        btnEditMode.addActionListener(e->toggleEditMode());
        panel.add(btnEditMode);   
		panel.add(txtDiaChi);
		
		txtMaKH.setEditable(false);
		txtMaKH.setFocusable(false);
		txtHo.setEditable(false);
		txtHo.setFocusable(false);
		txtTen.setEditable(false);
		txtTen.setFocusable(false);
		txtSDT.setEditable(false);
		txtSDT.setFocusable(false);
		txtDiaChi.setEditable(false);
		txtDiaChi.setFocusable(false);
		
		fillTableWithSampleData();
		
		table.getSelectionModel().addListSelectionListener(e -> getInforFromTable());
		
		
	}
	
	public void toggleEditMode() {
		isEditMode  = !isEditMode;
		txtMaKH.setEditable(isEditMode);
		txtMaKH.setFocusable(isEditMode);
		txtHo.setEditable(isEditMode);
		txtHo.setFocusable(isEditMode);
		txtTen.setEditable(isEditMode);
		txtTen.setFocusable(isEditMode);
		txtSDT.setEditable(isEditMode);
		txtSDT.setFocusable(isEditMode);
		txtDiaChi.setEditable(isEditMode);
		txtDiaChi.setFocusable(isEditMode);
	}
	
	public void getInforFromTable() {
		int selectedRow =table.getSelectedRow();
		if(selectedRow>=0) {
			txtMaKH.setEditable(false);
			txtMaKH.setText(table.getValueAt(selectedRow, 0).toString());
			txtHo.setText(table.getValueAt(selectedRow, 1).toString());
			txtTen.setText(table.getValueAt(selectedRow, 2).toString());
			txtSDT.setText(table.getValueAt(selectedRow, 3).toString());
			txtDiaChi.setText(table.getValueAt(selectedRow, 4).toString());
		}
	}
	
	private void fillTableWithSampleData() {
		// Dữ liệu mẫu (ví dụ về sản phẩm)
		String[] columnNames = { "Mã KH", "Họ Lót", "Tên", "Số điện thoại", "Địa chỉ" };
		 

		// Tạo DefaultTableModel với dữ liệu mẫu
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		
		for(KhachHangDTO x : khachHangBUS.getListKhachHang()) {
			Object[] row = {
					x.getMaKH(),
					x.getHo(),
					x.getTen(),
					x.getSdt(),
					x.getDiaChi(),
			};
			model.addRow(row);
		}
		
		
		
		

		// Gán model vào JTable
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
  		table.getColumnModel().getColumn(1).setPreferredWidth(180);
  		table.getColumnModel().getColumn(2).setPreferredWidth(90);
  		table.getColumnModel().getColumn(3).setPreferredWidth(200);
  		table.getColumnModel().getColumn(4).setPreferredWidth(200);
	}
	
	private void fillTableWithSampleData(ArrayList<KhachHangDTO> result) {
		// Dữ liệu mẫu (ví dụ về sản phẩm)
		String[] columnNames = { "Mã KH", "Họ Lót", "Tên", "Số điện thoại", "Địa chỉ" };
		 

		// Tạo DefaultTableModel với dữ liệu mẫu
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		
		for(KhachHangDTO x : result) {
			Object[] row = {
					x.getMaKH(),
					x.getHo(),
					x.getTen(),
					x.getSdt(),
					x.getDiaChi(),
			};
			model.addRow(row);
		}
		
		
		
		

		// Gán model vào JTable
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
  		table.getColumnModel().getColumn(1).setPreferredWidth(180);
  		table.getColumnModel().getColumn(2).setPreferredWidth(90);
  		table.getColumnModel().getColumn(3).setPreferredWidth(200);
  		table.getColumnModel().getColumn(4).setPreferredWidth(200);
	}
	
	
	
	
	public void addCustomer() {
		String maKH = txtMaKH.getText().trim();
		String ho = txtHo.getText().trim();
		String ten = txtTen.getText().trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		
		if (maKH.isEmpty() || ten.isEmpty() || ho.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin !", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
		if(khachHangBUS.checkCustomerExist(txtMaKH.getText())) {
			JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!\nHệ thống sẽ tự sinh ra mã khách hàng cho bạn", "Thông báo", JOptionPane.WARNING_MESSAGE);
			txtMaKH.setText(khachHangBUS.getMaKH());
			return;
		}
		if(!khachHangBUS.checkRegexId(txtMaKH.getText())) {
			JOptionPane.showMessageDialog(this, "Mã khách hàng không đúng định dạng!\nHệ thống sẽ tự sinh ra mã khách hàng cho bạn", "Thông báo", JOptionPane.WARNING_MESSAGE);
			txtMaKH.setText(khachHangBUS.getMaKH());
			return;
		}
		if(!khachHangBUS.checkRegexHo(txtHo.getText())) {
			JOptionPane.showMessageDialog(this, "Họ sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!khachHangBUS.checkRegexHo(txtTen.getText())) {
			JOptionPane.showMessageDialog(this, "Tên sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(khachHangBUS.checkDulicatePhone(sdt)) {
			JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!khachHangBUS.checkRegexSdt(txtSDT.getText())) {
			JOptionPane.showMessageDialog(this, "Số điện thoại sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		khachHangBUS.addCustomer(maKH, ho, ten, diaChi, sdt);
		fillTableWithSampleData();
		//update table
		
		JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		clearField();
		toggleEditInTheEnd();

	}
	
	public void updateCustomer() {
		if(!isEditMode) {
			JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước !", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
		}
		
		//lay dong dc selected
		int selectedRow = table.getSelectedRow();
		if(selectedRow < 0)
		{
			JOptionPane.showMessageDialog(null,"Vui lòng chọn một khách hàng để sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}			
		txtMaKH.setEditable(false);
		String maKH = table.getValueAt(selectedRow, 0).toString();
		String ho = table.getValueAt(selectedRow, 1).toString();
		String ten = table.getValueAt(selectedRow, 2).toString();
		String sdt = table.getValueAt(selectedRow, 3).toString();
		String diaChi = table.getValueAt(selectedRow, 4).toString();		
        //event 
//		for (ActionListener listener : btnEdit.getActionListeners()) {
//		        btnEdit.removeActionListener(listener);
//		}
			
        	String newHo = txtHo.getText().trim();
			String newTen = txtTen.getText().trim();
			String newSDT = txtSDT.getText().trim();
			String newDiaChi = txtDiaChi.getText().trim();
			
			if(!txtMaKH.getText().trim().equals(maKH)) {
				 JOptionPane.showMessageDialog(null, "Bạn không thể sửa mã khách hàng !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				 txtMaKH.setText(maKH);
		         return;
			}
			if (maKH.isEmpty() || ten.isEmpty() || ho.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin !", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
			
			if(!khachHangBUS.checkRegexId(txtMaKH.getText())) {
				JOptionPane.showMessageDialog(this, "Mã khách hàng không đúng định dạng!\nVD: KH01", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!khachHangBUS.checkRegexHo(txtHo.getText())) {
				JOptionPane.showMessageDialog(this, "Họ sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!khachHangBUS.checkRegexHo(txtTen.getText())) {
				JOptionPane.showMessageDialog(this, "Tên sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(khachHangBUS.checkDulicatePhone(newSDT) && !newSDT.equals(sdt)) {
				JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!khachHangBUS.checkRegexSdt(txtSDT.getText())) {
				JOptionPane.showMessageDialog(this, "Số điện thoại sai định dạng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			khachHangBUS.updateCustomer(newHo, newTen, newDiaChi, newSDT, selectedRow);
        	
        	//update table
        	
        	
        	    	
        	//reset text fields
			fillTableWithSampleData();
        	clearField();
        	toggleEditInTheEnd();
        	JOptionPane.showMessageDialog(null, "Sửa thông tin thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void deleteCustomer() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow < 0)
			{
				JOptionPane.showMessageDialog(null,"Vui lòng chọn một khách hàng để xóa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}	
		
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này?","Xóa Nhà Cung Cấp",JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION)
		{
			khachHangBUS.deleteCustomer(selectedRow);
			JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);	
			fillTableWithSampleData();
			clearField();
			toggleEditInTheEnd();
		}
	}
	
	public void clearField() {
		txtMaKH.setText("");
		txtHo.setText("");
		txtTen.setText("");
		txtDiaChi.setText("");
		txtSDT.setText("");
	}
	
	public void toggleEditInTheEnd() {
		isEditMode=false;
		txtMaKH.setEditable(false);
		txtMaKH.setFocusable(false);
		txtHo.setEditable(false);
		txtHo.setFocusable(false);
		txtTen.setEditable(false);
		txtTen.setFocusable(false);
		txtSDT.setEditable(false);
		txtSDT.setFocusable(false);
		txtDiaChi.setEditable(false);
		txtDiaChi.setFocusable(false);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		
		if(str.equals("Thêm"))
			addCustomer(); 	
		else if(str.equals("Sửa"))
			updateCustomer();
		else if(str.equals("Xóa"))
			deleteCustomer();
		else if(str.equals("Xuất Excel"))
			xuatExcel();
		else if(str.equals("Nhập Excel"))
			nhapExcel();
		
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
	
	public void nhapExcel() {
	    JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
	    fileChooser.setFileFilter(filter);
	    int result = fileChooser.showOpenDialog(this);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        
	        int confirm = JOptionPane.showConfirmDialog(
	            this,
	            "Bạn có muốn nạp dữ liệu mới từ file Excel này không?\nDữ liệu hiện có sẽ được kiểm tra và cập nhật nếu cần.",
	            "Xác nhận nhập Excel",
	            JOptionPane.YES_NO_OPTION,
	            JOptionPane.QUESTION_MESSAGE
	        );
	        
	        if (confirm == JOptionPane.YES_OPTION) {
	            try {
	                KhachHangBUS khBUS = new KhachHangBUS();
	                int[] importResult = khBUS.ImportExcel(selectedFile);
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
}
