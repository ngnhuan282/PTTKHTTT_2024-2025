package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import BUS.NhaCungCapBUS;
import BUS.SanPhamBUS;
import DTO.NhaCungCapDTO;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NhaCungCapGUI extends JPanel implements ActionListener{

	private JPanel contentPane;
	private int DEFAULT_WIDTH = 1450, DEFAULT_HEIGHT = 800;
	private String color = "#FF5252";
	public DefaultTableModel model;
	private JTable table;
	private JPanel inforConent;
	private JButton btnEditMode;
	private JTextField txtSearch;
	private JComboBox<String> cboxSearch;
	private JTextField txtMaNCC,txtTenNCC,txtSDT,txtDiaChi;
	private NhaCungCapBUS nccBUS = new NhaCungCapBUS();	
	private boolean isEditMode = false; 


	 public static void main(String[] args) {
	        JFrame frame = new JFrame("Test NCCGUI");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1248, 757);
	        frame.getContentPane().add(new NhaCungCapGUI());
	        frame.setVisible(true);
	    }
	
	public NhaCungCapGUI()
	{
		String[] columnNames = { "Mã NCC", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Địa Chỉ" };
		model = new DefaultTableModel(columnNames, 0);
		initComponents();
		//aaaaa
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

        JButton btnNhapExcel = new JButton("Nhập Excel", new ImageIcon(SanPhamGUI.class.getResource("/image/excel48.png")));
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
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(Color.WHITE);
        btnLamMoi.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/reload30.png")));
        btnLamMoi.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLamMoi.setBounds(1045, 31, 126, 28);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        pHeaderMain.add(btnLamMoi);
		
        String []listKeyWord = {"Mã NCC","Tên NCC","SĐT"};
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
        
        JPanel pContent = new JPanel();
        pContent.setBackground(SystemColor.control);;
        pContent.setBounds(0, 103, 1248, 654); // Đặt bên dưới pHeaderMain
        pContent.setLayout(null);
        add(pContent);  
        
        inforConent = new JPanel();
        inforConent.setBackground(new Color(255, 255, 255));
        inforConent.setBounds(10, 10, 255, 634);
        pContent.add(inforConent);
        inforConent.setLayout(null);
        
        JLabel lbMaNCC = new JLabel("Mã nhà cung cấp");
        lbMaNCC.setFont(new Font("Verdana", Font.BOLD, 12));
        lbMaNCC.setBounds(10, 20, 127, 30);
        inforConent.add(lbMaNCC);
        
        txtMaNCC = new JTextField();
        txtMaNCC.setBounds(10, 61, 235, 35);
        inforConent.add(txtMaNCC);
        txtMaNCC.setColumns(10);
        txtMaNCC.setEditable(false);
        txtMaNCC.setFocusable(false);
        
        txtTenNCC = new JTextField();
        txtTenNCC.setColumns(10);
        txtTenNCC.setBounds(10, 161, 235, 35);
        inforConent.add(txtTenNCC);
        txtTenNCC.setEditable(false);
        txtTenNCC.setFocusable(false);
        
        JLabel lbTenNCC = new JLabel("Tên nhà cung cấp");
        lbTenNCC.setFont(new Font("Verdana", Font.BOLD, 12));
        lbTenNCC.setBounds(10, 120, 127, 30);
        inforConent.add(lbTenNCC);
        
        txtSDT = new JTextField();
        txtSDT.setColumns(10);
        txtSDT.setBounds(10, 261, 235, 35);
        inforConent.add(txtSDT);
        txtSDT.setEditable(false);
        txtSDT.setFocusable(false);
        
        JLabel lbSDT = new JLabel("Số điện thoại");
        lbSDT.setFont(new Font("Verdana", Font.BOLD, 12));
        lbSDT.setBounds(10, 220, 127, 30);
        inforConent.add(lbSDT);
        
        txtDiaChi = new JTextField();
        txtDiaChi.setColumns(10);
        txtDiaChi.setBounds(10, 361, 235, 35);
        inforConent.add(txtDiaChi);
        txtDiaChi.setEditable(false);
        txtDiaChi.setFocusable(false);
        
        JLabel lbDiaChi = new JLabel("Địa chỉ");
        lbDiaChi.setFont(new Font("Verdana", Font.BOLD, 12));
        lbDiaChi.setBounds(10, 320, 127, 30);
        inforConent.add(lbDiaChi);      
        
        //Cac button bi an
		btnEditMode = new JButton("");
		btnEditMode.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit20.png")));
		btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.setBounds(208, 11, 37, 20);
        btnEditMode.addActionListener(e->toggleEditMode());
        inforConent.add(btnEditMode);   
    
      //khoi tao table voi cac header cot co san
      		table = new JTable(model);
      		// Gọi hàm khi click chuột
      		table.getSelectionModel().addListSelectionListener(e->getInforFromTable());
      		//khong cho edit truc tiep 
//      		table.setDefaultEditor(Object.class, null);
//      		//khong cho thay doi cot
//      		table.getTableHeader().setReorderingAllowed(false); 
      		
//      		//chon hang roi lay thong tin 
//      		table.getSelectionModel().addListSelectionListener(e->{});
      		
      		table.setFont(new Font("Verdana", Font.PLAIN, 12));
      		table.setGridColor(new Color(200, 200, 200));
      		table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
      		
      		table.setRowHeight(23);
      		table.getTableHeader().setPreferredSize(new Dimension(0, 23));
      		table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
      		
      		table.getColumnModel().getColumn(0).setPreferredWidth(10);
      		table.getColumnModel().getColumn(1).setPreferredWidth(270);
      		table.getColumnModel().getColumn(2).setPreferredWidth(43);
      		table.getColumnModel().getColumn(3).setPreferredWidth(350);
      		
      		//can giua
      		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      		table.setDefaultRenderer(Object.class, centerRenderer);
      		
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(275, 10, 914, 633);
            scrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220))); //do dai vien tren duoi trai phai
            scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
            scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
            pContent.add(scrollPane);			
      		
      		
              
            nccBUS.docDSNCC();
            nccBUS.updateTable(model);
        }
	
		public void getInforFromTable() {
			int selectedRow =table.getSelectedRow();
			if(selectedRow>=0) {
				txtMaNCC.setText(model.getValueAt(selectedRow, 0).toString());
				txtTenNCC.setText(model.getValueAt(selectedRow, 1).toString());
				txtSDT.setText(model.getValueAt(selectedRow, 2).toString());
				txtDiaChi.setText(model.getValueAt(selectedRow, 3).toString());
			}
		}
		
		public void toggleEditMode() {
			isEditMode = !isEditMode;
			txtMaNCC.setEditable(isEditMode);
			txtMaNCC.setFocusable(isEditMode);
			txtTenNCC.setEditable(isEditMode);
			txtTenNCC.setFocusable(isEditMode);
			txtSDT.setEditable(isEditMode);
			txtSDT.setFocusable(isEditMode);
			txtDiaChi.setEditable(isEditMode);
			txtDiaChi.setFocusable(isEditMode);
		}
		
		public void toggleEditInTheEnd() {
			isEditMode=false;
			txtMaNCC.setEditable(false);
			txtMaNCC.setFocusable(false);
			txtTenNCC.setEditable(false);
			txtTenNCC.setFocusable(false);
			txtSDT.setEditable(false);
			txtSDT.setFocusable(false);
			txtDiaChi.setEditable(false);
			txtDiaChi.setFocusable(false);
		}
	
	
//Main eventtttttttttttttttt
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(command.equals("Thêm"))
			addNCC();
		else if(command.equals("Sửa")) 
			editNCC();
		else if(command.equals("Xóa"))
			deleteNCC();
		else if(command.equals("Tìm kiếm"))
			timKiem();
		else if(command.equals("Xuất Excel"))
			xuatExcel();
		else if(command.equals("Nhập Excel"))
			nhapExcel();
		else if(command.equals("Reload"))
			nccBUS.updateTable(model);
	}
	
	//search
	public void timKiem() {
		String tuKhoa = txtSearch.getText().trim();
    	String tieuChi = cboxSearch.getSelectedItem().toString();
    	ArrayList<NhaCungCapDTO> result = nccBUS.searchNCC(tuKhoa, tieuChi);
    	
    	model.setRowCount(0);
		for(NhaCungCapDTO ncc : result)
			model.addRow(new Object[] {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getDiaChi()});
		if(result.isEmpty())
    		JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	
	public void deleteNCC() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow < 0)
			{
				JOptionPane.showMessageDialog(null,"Vui lòng chọn một nhà cung cấp để xóa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}	
		
		String maNCC = table.getValueAt(selectedRow, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhà cung cấp này?","Xóa Nhà Cung Cấp",JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION)
		{
			nccBUS.deleteNCC(maNCC);
			//update model
			model.removeRow(selectedRow);

			//reset textfield
			JOptionPane.showMessageDialog(null, "Xóa nhà cung cấp thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);	
			clearFields();
			toggleEditInTheEnd();
		}
	}
	
	
	public void addNCC() {
		String maNCC = txtMaNCC.getText().trim();
		String tenNCC = txtTenNCC.getText().trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		
		if (maNCC.isEmpty() || tenNCC.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin !", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
		
		if (regex(maNCC,tenNCC,sdt,diaChi)) {
			 return;
		 }
		
		if (nccBUS.isDuplicateNCC(maNCC)) {
			 JOptionPane.showMessageDialog(null, "Mã nhà cung cấp đã tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			 return;
		}
		
		if (nccBUS.isDuplicateSDT(sdt)) {
			 JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			 return;
		}
		
		nccBUS.addNCC(maNCC, tenNCC, sdt, diaChi);
		//update table
		model.addRow(new Object[] { maNCC, tenNCC, sdt, diaChi });
		
		JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		clearFields();
		toggleEditInTheEnd();

	}
	
	
	public void clearFields() {
		txtMaNCC.setText("");
    	txtTenNCC.setText("");
    	txtSDT.setText("");
    	txtDiaChi.setText("");
	}
		
	public void editNCC() {
		
		if(!isEditMode) {
			JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước !", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
		}
		
		//lay dong dc selected
		int selectedRow = table.getSelectedRow();
		if(selectedRow < 0)
		{
			JOptionPane.showMessageDialog(null,"Vui lòng chọn một nhà cung cấp để sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}			
			
		String maNCC = model.getValueAt(selectedRow, 0).toString();
		String sdt =model.getValueAt(selectedRow, 2).toString();
		
        	String newMaNCC = txtMaNCC.getText().trim();
			String newTenNCC = txtTenNCC.getText().trim();
			String newSDT = txtSDT.getText().trim();
			String newDiaChi = txtDiaChi.getText().trim();
			
			 if (newMaNCC.isEmpty() || newTenNCC.isEmpty() || newSDT.isEmpty() || newDiaChi.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin !", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
			 
			 if (regex(newMaNCC,newTenNCC,newSDT,newDiaChi)) {
				 return;
			 }
			 
			if(nccBUS.checkEdit(newMaNCC, maNCC)){
				JOptionPane.showMessageDialog(null,"Đã tồn tại mã nhà cung cấp này trong danh sách!" ,  "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
			
			if(nccBUS.checkSDT(newSDT, sdt)){
				JOptionPane.showMessageDialog(null,"Đã tồn tại số điện thoại này trong danh sách!" ,  "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
			
        	nccBUS.editNCC(newMaNCC, newTenNCC, newSDT, newDiaChi, maNCC);
        	
        	//update table
        	
        	model.setValueAt(newMaNCC, selectedRow, 0);        	
        	model.setValueAt(newTenNCC, selectedRow, 1);  
        	model.setValueAt(newSDT, selectedRow, 2);  
        	model.setValueAt(newDiaChi, selectedRow, 3);  
        	    	
        	//reset text fields
        	clearFields();
        	toggleEditInTheEnd();
        	JOptionPane.showMessageDialog(null, "Sửa thông tin thành công !", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
	}		
	
	//check regex
	public boolean regex(String maNCC,String tenNCC,String sdt,String diaChi) {
		if(!maNCC.matches("^[a-zA-Z0-9]+$")) {
			JOptionPane.showMessageDialog(null, "Mã nhà cung cấp chỉ được chứa chữ và số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if(!sdt.matches("^\\d{10}$")) {
			JOptionPane.showMessageDialog(null, "Số điện thoại phải gồm đúng 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		
		return false;
	}
	
	public void xuatExcel()
	{	
		System.out.println("Exporting Excel...");
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
	                NhaCungCapBUS nccBUS = new NhaCungCapBUS();
	                int[] importResult = nccBUS.ImportExcel(selectedFile);
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
	                nccBUS.updateTable(model);
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
