package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser; // Thư viện JDateChooser

import BUS.CTKMBUS;
import DTO.CTKMDTO;

public class CTKMGUI extends JPanel {
    private static final Object String = null;
	private JTable table;
    private DefaultTableModel tableModel;
    private JTable tblCTKM;
    private JTextField txtMaCTKM;
    private JTextField txtTenCTKM;
    private JDateChooser dateNgayBD;
    private JDateChooser dateNgayKT;
    private JTextField txtPhanTramGiamGia;
    private JTextField txtMaSPHD;
    private JComboBox<String> cboxLoaiKM;
    private JComboBox<String> cboxMaSP;
    private JComboBox<String> cboxMaHD;
    private JComboBox<String> cboxSearch;
    private JTextField txtSearch;
    private boolean isEditMode = false;
    private CTKMBUS ctkmBUS;

    /**
     * @throws SQLException
     */
    /**
     * @throws SQLException
     */
    public CTKMGUI() throws SQLException {
        setLayout(null);
        setPreferredSize(new Dimension(1248, 757));

        ctkmBUS = new CTKMBUS();

        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Mã CTKM","Tên CTKM", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Loại KM","Mã SP/HD", "Phần Trăm Giảm Giá"}
        );
        tblCTKM = new JTable(tableModel);

        JPanel pMain = new JPanel(null);
        pMain.setBounds(0, 0, 1248, 757);
        pMain.setPreferredSize(new Dimension(1248, 757));
        add(pMain);

        JPanel pHeaderMain = new JPanel(null);
        pHeaderMain.setBackground(Color.WHITE);
        pHeaderMain.setBounds(0, 0, 1248, 100);
        pMain.add(pHeaderMain);

        JPanel pLeftHeader = new JPanel(null);
        pLeftHeader.setBorder(new TitledBorder("Chức năng"));
        pLeftHeader.setBackground(Color.WHITE);
        pLeftHeader.setBounds(2, 0, 512, 100);
        pHeaderMain.add(pLeftHeader);

        Box box = Box.createHorizontalBox();
        box.setBounds(0, 0, 512, 111);
        pLeftHeader.add(box);

        JButton btnThem = new JButton("Thêm");
        btnThem.setFocusPainted(false);
        btnThem.setActionCommand("Thêm");
        btnThem.addActionListener(this::actionPerformed);
        btnThem.setBackground(Color.WHITE);
        btnThem.setBorderPainted(false);
        btnThem.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/add48.png")));
        btnThem.setFont(new Font("Arial", Font.PLAIN, 15));
        btnThem.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnThem.setHorizontalTextPosition(SwingConstants.CENTER);
        btnThem.setPreferredSize(new Dimension(100, 140));
        box.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setFocusPainted(false);
        btnSua.setActionCommand("Sửa");
        btnSua.addActionListener(this::actionPerformed);
        btnSua.setBorderPainted(false);
        btnSua.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSua.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/edit48.png")));
        btnSua.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSua.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSua.setBackground(Color.WHITE);
        btnSua.setPreferredSize(new Dimension(100, 140));
        box.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setFocusPainted(false);
        btnXoa.setActionCommand("Xóa");
        btnXoa.addActionListener(this::actionPerformed);
        btnXoa.setBorderPainted(false);
        btnXoa.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXoa.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/remove48.png")));
        btnXoa.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXoa.setBackground(Color.WHITE);
        btnXoa.setPreferredSize(new Dimension(100, 140));
        box.add(btnXoa);

        JButton btnNhapExcel = new JButton("Nhập Excel");
        btnNhapExcel.setFocusPainted(false);
        btnNhapExcel.setActionCommand("Nhập Excel");
        btnNhapExcel.addActionListener(this::actionPerformed);
        btnNhapExcel.setBorderPainted(false);
        btnNhapExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNhapExcel.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/excel48.png")));
        btnNhapExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNhapExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnNhapExcel.setBackground(Color.WHITE);
        btnNhapExcel.setPreferredSize(new Dimension(100, 140));
        box.add(btnNhapExcel);

        JButton btnXuatExcel = new JButton("Xuất Excel");
        btnXuatExcel.setFocusPainted(false);
        btnXuatExcel.setActionCommand("Xuất Excel");
        btnXuatExcel.addActionListener(this::actionPerformed);
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnXuatExcel.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/xuatexcel48.png")));
        btnXuatExcel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnXuatExcel.setFont(new Font("Arial", Font.PLAIN, 15));
        btnXuatExcel.setBackground(Color.WHITE);
        btnXuatExcel.setPreferredSize(new Dimension(100, 140));
        box.add(btnXuatExcel);

        String[] listKeyWord = {"Mã CTKM", "Loại KM"};
        cboxLoaiKM = new JComboBox<String>(listKeyWord);
        cboxLoaiKM.setFont(new Font("Arial", Font.PLAIN, 14));
        cboxLoaiKM.setBackground(Color.WHITE);
        cboxLoaiKM.setForeground(Color.BLACK);
        cboxLoaiKM.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        cboxLoaiKM.setBounds(524, 30, 100, 28);
        pHeaderMain.add(cboxLoaiKM);

        txtSearch = new JTextField();
        txtSearch.setColumns(10);
        txtSearch.setBounds(658, 32, 290, 27);
        pHeaderMain.add(txtSearch);

        JButton btnSearch = new JButton("");
        btnSearch.setIcon(new ImageIcon(CTKMGUI.class.getResource("/image/search30.png")));
        btnSearch.setBounds(958, 29, 66, 30);
        btnSearch.setActionCommand("Tìm kiếm");
        btnSearch.addActionListener(this::actionPerformed);
        pHeaderMain.add(btnSearch);

        JPanel panel = new JPanel(null);
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel.setBackground(Color.WHITE);
        panel.setBounds(2, 110, 265, 647);
        pMain.add(panel);

        JLabel lbMaCTKM = new JLabel("Mã CTKM");
        lbMaCTKM.setFont(new Font("Verdana", Font.BOLD, 12));
        lbMaCTKM.setBounds(10, 30, 100, 23);
        panel.add(lbMaCTKM);

        txtMaCTKM = new JTextField();
        txtMaCTKM.setBounds(10, 55, 245, 32);
        txtMaCTKM.setEditable(false);
        panel.add(txtMaCTKM);

        // Tên CTKM
        JLabel lbTenCTKM = new JLabel("Tên CTKM");
        lbTenCTKM.setFont(new Font("Verdana", Font.BOLD, 12));
        lbTenCTKM.setBounds(10, 95, 100, 23);
       
        panel.add(lbTenCTKM);

        txtTenCTKM = new JTextField();
        txtTenCTKM.setBounds(10, 120, 245, 32);
        txtTenCTKM.setEditable(false);
        panel.add(txtTenCTKM);

        // Ngày Bắt Đầu
        JLabel lbNgayBD = new JLabel("Ngày Bắt Đầu");
        lbNgayBD.setFont(new Font("Verdana", Font.BOLD, 12));
        lbNgayBD.setBounds(10, 160, 100, 23);
        panel.add(lbNgayBD);

        dateNgayBD = new JDateChooser();
        dateNgayBD.setDateFormatString("yyyy-MM-dd");
        dateNgayBD.setBounds(10, 185, 245, 32);
        dateNgayBD.setEnabled(false);
        panel.add(dateNgayBD);

        // Ngày Kết Thúc
        JLabel lbNgayKT = new JLabel("Ngày Kết Thúc");
        lbNgayKT.setFont(new Font("Verdana", Font.BOLD, 12));
        lbNgayKT.setBounds(10, 225, 120, 23);
        panel.add(lbNgayKT);

        dateNgayKT = new JDateChooser();
        dateNgayKT.setDateFormatString("yyyy-MM-dd");
        dateNgayKT.setBounds(10, 250, 245, 32);
        dateNgayKT.setEnabled(false);
        panel.add(dateNgayKT);

        // Loại Khuyến Mãi
        JLabel lbLoaiKM = new JLabel("Loại Khuyến Mãi");
        lbLoaiKM.setFont(new Font("Verdana", Font.BOLD, 12));
        lbLoaiKM.setBounds(10, 290, 120, 23);
        panel.add(lbLoaiKM);

        cboxLoaiKM = new JComboBox<>(new String[]{"Sản Phẩm", "Hóa Đơn"});
        cboxLoaiKM.setBounds(10, 315, 245, 32);
        panel.add(cboxLoaiKM);

        // Mã SP/HD
        JLabel lbMaSPorHD = new JLabel("Mã SP/HD");
        lbMaSPorHD.setFont(new Font("Verdana", Font.BOLD, 12));
        lbMaSPorHD.setBounds(10, 355, 100, 23);
        panel.add(lbMaSPorHD);

        cboxMaSP = new JComboBox<>();
        cboxMaSP.setBounds(10, 380, 245, 32);
        panel.add(cboxMaSP);

        cboxMaHD = new JComboBox<>();
        cboxMaHD.setBounds(10, 380, 245, 32);
        panel.add(cboxMaHD);
        
//        cboxLoaiKM.setEnabled(false);
//        cboxMaSP.setEnabled(false);
//        cboxMaHD.setEnabled(false);

        // Phần Trăm Giảm Giá
        JLabel lbPhanTramGiamGia = new JLabel("Phần trăm Giảm Giá");
        lbPhanTramGiamGia.setFont(new Font("Verdana", Font.BOLD, 12));
        lbPhanTramGiamGia.setBounds(10, 420, 150, 23);
        panel.add(lbPhanTramGiamGia);

        txtPhanTramGiamGia = new JTextField();
        txtPhanTramGiamGia.setBounds(10, 445, 245, 32);
        txtPhanTramGiamGia.setEditable(false);
        panel.add(txtPhanTramGiamGia);

        JButton btnEditMode = new JButton(new ImageIcon(CTKMGUI.class.getResource("/image/edit20.png")));
        btnEditMode.setBounds(215, 15, 37, 20);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setFocusPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.addActionListener(e -> toggleEditMode());
        panel.add(btnEditMode);

        JScrollPane scrollPane = new JScrollPane(tblCTKM);
        scrollPane.setBounds(265, 110, 965, 647);
        pMain.add(scrollPane);

        cboxLoaiKM.addActionListener(e -> switchMaBox());
//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
//        tblCTKM.setRowSorter(sorter);
        tblCTKM.getSelectionModel().addListSelectionListener(e -> getInforFromTable());
        switchMaBox();
        loadData();
        
        cboxLoaiKM.addActionListener(e -> {
            String selected = cboxLoaiKM.getSelectedItem().toString();
            
            if (selected.equals("Sản Phẩm")) {
                cboxMaSP.setVisible(true);
                cboxMaHD.setVisible(false);
                loadMaSP();  // Hàm load mã sản phẩm
            } else {
                cboxMaSP.setVisible(false);
                cboxMaHD.setVisible(true);
                loadMaHD();  // Hàm load mã hóa đơn
            }
        });

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Thêm": addKhuyenMai(); break;
            case "Sửa": updateKhuyenMai(); break;
//            case "Xóa": deleteKhuyenMai(); break;
          //  case "Tìm kiếm": searchCTKM(); break;
           // case "Nhập Excel": nhapExcel(); break;
           // case "Xuất Excel": xuatExcel(); break;

//            case "Tìm kiếm": timKiem(); break;
        }
    }

    public void addKhuyenMai() {
        if (!isEditMode) {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maKM = txtMaCTKM.getText().trim();
        Date ngayBD = dateNgayBD.getDate();
        Date ngayKT = dateNgayKT.getDate();
        String tenKM = txtTenCTKM.getText().trim();
        String phanTramGiamGiaStr = txtPhanTramGiamGia.getText();
        String loaiKM = cboxLoaiKM.getSelectedItem().toString();
        String maSPorHD = loaiKM.equals("Sản Phẩm") ? cboxMaSP.getSelectedItem().toString() : cboxMaHD.getSelectedItem().toString();

        if (maKM.isEmpty() || ngayBD == null || ngayKT == null || tenKM.isEmpty() || phanTramGiamGiaStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ctkmBUS.checkMaCTKMexist(maKM)) {
            JOptionPane.showMessageDialog(null, "Mã CTKM đã tồn tại trong bảng!");
            return;
        }

        if (!ctkmBUS.isNgayKTAfterNgayBD(ngayBD, ngayKT)) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày KT sau ngày BD", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float phanTram = Float.parseFloat(phanTramGiamGiaStr);
        if (phanTram < 0 || phanTram > 100) {
            JOptionPane.showMessageDialog(this, "Phần trăm giảm giá phải trong khoảng từ 0 đến 100!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.sql.Date sqlNgayBD = new java.sql.Date(ngayBD.getTime());
        java.sql.Date sqlNgayKT = new java.sql.Date(ngayKT.getTime());

        boolean success = ctkmBUS.themKhuyenMai(maKM, sqlNgayBD, sqlNgayKT, tenKM, loaiKM, maSPorHD, phanTram);
        if (success) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            tableModel.addRow(new Object[]{maKM, tenKM, sdf.format(ngayBD), sdf.format(ngayKT), loaiKM, maSPorHD, phanTram + "%"});
            tblCTKM.setModel(tableModel);
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clearField();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    
    public void clearField() {
        txtMaCTKM.setText("");
        dateNgayBD.setDate(null);
        dateNgayKT.setDate(null);
        txtPhanTramGiamGia.setText("");
        txtTenCTKM.setText("");
        cboxLoaiKM.setSelectedIndex(0); 
    }
    
    

    public void updateKhuyenMai() {
        if (!isEditMode) {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = tblCTKM.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình khuyến mãi để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String maCTKM = tblCTKM.getValueAt(selectedRow, 0).toString();
            String tenCTKM = txtTenCTKM.getText().trim();
            Date ngayBD = dateNgayBD.getDate();
            Date ngayKT = dateNgayKT.getDate();
            String loaiCTKM = cboxLoaiKM.getSelectedItem().toString();
            String maSPorHD;
            String phanTramText = txtPhanTramGiamGia.getText().replace("%", "").trim();
            float phanTramValue = Float.parseFloat(phanTramText);
            
            
            if (loaiCTKM.equals("Sản Phẩm")) {
                maSPorHD = cboxMaSP.getSelectedItem().toString();
            } else {
                maSPorHD = cboxMaHD.getSelectedItem().toString();
            }
			System.out.println("haha"+ loaiCTKM + "hihi" + maSPorHD);
            if (maSPorHD.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn mã SP/HD", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (ngayBD.after(ngayKT)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không thể sau ngày kết thúc!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
         // Lấy ngày từ đối tượng Date (ngayBD hoặc ngayKT)
            java.sql.Date sqlNgayBD = new java.sql.Date(ngayBD.getTime());
            java.sql.Date sqlNgayKT = new java.sql.Date(ngayKT.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Lấy loại khuyến mãi cũ trước khi thay đổi
            String loaiCTKMCu = tblCTKM.getValueAt(selectedRow, 4).toString();

            // Lấy index của chương trình khuyến mãi
            int index = selectedRow;  // Vị trí của dòng trong bảng, được dùng làm index

            // Cập nhật dữ liệu trong CSDL
            String maSPorHDCu = tblCTKM.getValueAt(selectedRow, 5).toString();
            CTKMDTO ctkm = new CTKMDTO(maCTKM, sqlNgayBD, sqlNgayKT, tenCTKM, phanTramValue);
            ctkmBUS.updateCTKM(ctkm, loaiCTKMCu, loaiCTKM, maSPorHD, phanTramValue,maSPorHDCu, index);

            // Cập nhật lại bảng giao diện
            tableModel.setValueAt(maCTKM, selectedRow, 0);
            tableModel.setValueAt(tenCTKM, selectedRow, 1);
            tableModel.setValueAt(sdf.format(ngayBD), selectedRow, 2);
            tableModel.setValueAt(sdf.format(ngayKT), selectedRow, 3);
            tableModel.setValueAt(loaiCTKM, selectedRow, 4);
            tableModel.setValueAt(maSPorHD, selectedRow, 5);
            tableModel.setValueAt(phanTramValue + "%", selectedRow, 6);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            clearField();
            toggleEditInTheEnd();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    
    
    
    
    
    
    
    
    public void nhapExcel() {
//      JFileChooser fileChooser = new JFileChooser();
//      FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx", "xls");
//      fileChooser.setFileFilter(filter);
//      int result = fileChooser.showOpenDialog(this);
//      if (result == JFileChooser.APPROVE_OPTION) {
//          File selectedFile = fileChooser.getSelectedFile();
//          try {
//              CTKMBUS ctkmBUS = new CTKMBUS();
//              JOptionPane.showMessageDialog(this, "Nhập dữ liệu từ Excel thành công!", 
//                      "Thành công", JOptionPane.INFORMATION_MESSAGE);
//          } catch (Exception e) {
//              JOptionPane.showMessageDialog(this, "Lỗi khi nhập Excel: " + e.getMessage(), 
//                      "Lỗi", JOptionPane.ERROR_MESSAGE);
//              e.printStackTrace();
//          }
//      }
  }

  public void xuatExcel() {
//      System.out.println("Exporting Excel for CTKMGUI...");
//      try {
//          ExcelExporter.exportJTableToExcel(tblCTKM);
//      } catch (IOException e) {
//          JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(),
//                  "Lỗi", JOptionPane.ERROR_MESSAGE);
//          e.printStackTrace();
//      }
  }




                                  
    

    private JButton createButton(String title, String iconPath) {
        JButton btn = new JButton(title, new ImageIcon(CTKMGUI.class.getResource(iconPath)));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFont(new Font("Arial", Font.PLAIN, 15));
        btn.setBackground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 100));
        return btn;
    }

    private JTextField createLabeledTextField(JPanel panel, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Verdana", Font.BOLD, 12));
        label.setBounds(10, y, 200, 23);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(10, y + 25, 245, 32);
        textField.setEditable(false);
        panel.add(textField);
        return textField;
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        txtMaCTKM.setEditable(isEditMode);
        txtTenCTKM.setEditable(isEditMode);
        dateNgayBD.setEnabled(isEditMode);
        dateNgayKT.setEnabled(isEditMode);
        txtPhanTramGiamGia.setEditable(isEditMode);
        cboxLoaiKM.setEnabled(isEditMode);
        cboxMaSP.setEnabled(isEditMode);
        cboxMaHD.setEnabled(isEditMode);
    }

    private void switchMaBox() {
        String selected = (String) cboxLoaiKM.getSelectedItem();
        boolean isSP = "Sản Phẩm".equals(selected);
        cboxMaSP.setVisible(isSP);
        cboxMaHD.setVisible(!isSP);
    }
    public void toggleEditInTheEnd() {
        // Tắt chế độ chỉnh sửa
        isEditMode = false;
        
        // Tắt chỉnh sửa cho các TextField
        txtMaCTKM.setEditable(false);
        txtMaCTKM.setFocusable(false);
        txtTenCTKM.setEditable(false);
        txtTenCTKM.setFocusable(false);
        txtPhanTramGiamGia.setEditable(false);
        txtPhanTramGiamGia.setFocusable(false);

        // Tắt ComboBox (cả loại khuyến mãi và mã SP/HĐ)
        cboxLoaiKM.setEnabled(false);
        cboxMaSP.setEnabled(false);
        cboxMaHD.setEnabled(false);

        // Tắt chỉnh sửa cho các trường ngày (JDateChooser)
        dateNgayBD.setEnabled(false);
        dateNgayKT.setEnabled(false);
    }
    public void setEditMode(boolean isEditing) {
        isEditMode = isEditing;

        txtMaCTKM.setEditable(isEditing);
        txtMaCTKM.setFocusable(isEditing);
        txtTenCTKM.setEditable(isEditing);
        txtTenCTKM.setFocusable(isEditing);
        txtPhanTramGiamGia.setEditable(isEditing);
        txtPhanTramGiamGia.setFocusable(isEditing);

        cboxLoaiKM.setEnabled(isEditing);
        cboxMaSP.setEnabled(isEditing);
        cboxMaHD.setEnabled(isEditing);

        dateNgayBD.setEnabled(isEditing);
        dateNgayKT.setEnabled(isEditing);
    }


    private void loadData() throws SQLException {
        ArrayList<CTKMDTO> list = ctkmBUS.getListKhuyenMai();
        tableModel.setRowCount(0);
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlybangiay", "root", "");

        try {
            Statement st = con.createStatement();

            for (CTKMDTO dto : list) {
                String maCTKM = dto.getMaCTKM();
                String tenCTKM = dto.getTenCTKM();
                Date ngayBD = dto.getNgayBD();
                Date ngayKT = dto.getNgayKT();

                String loaiKM = "";
                String maSPorHD = "";
                String phanTram = "";

                // Check trong bảng ctkm_sp
                String sqlSP = "SELECT * FROM ctkm_sp WHERE MaCTKM = '" + maCTKM + "'";
                ResultSet rsSP = st.executeQuery(sqlSP);
                if (rsSP.next()) {
                    loaiKM = "Sản phẩm";
                    maSPorHD = rsSP.getString("MaSP");
                    phanTram = rsSP.getString("PhanTramGiamGia") + "%";
                } else {
                    // Nếu không có thì check trong ctkm_hd
                    String sqlHD = "SELECT * FROM ctkm_hd WHERE MaCTKM = '" + maCTKM + "'";
                    ResultSet rsHD = st.executeQuery(sqlHD);
                    if (rsHD.next()) {
                        loaiKM = "Hóa đơn";
                        maSPorHD = rsHD.getString("MaHD");
                        phanTram = rsHD.getString("PhanTramGiamGia") + "%";
                    }
                }

                // Thêm vào bảng
                tableModel.addRow(new Object[]{
                    maCTKM, tenCTKM, ngayBD, ngayKT,
                    loaiKM, maSPorHD, phanTram
                });
            }

            tblCTKM.setModel(tableModel);
            tblCTKM.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblCTKM.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblCTKM.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblCTKM.getColumnModel().getColumn(3).setPreferredWidth(150);
            // Add width cho 3 cột mới
            tblCTKM.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblCTKM.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblCTKM.getColumnModel().getColumn(6).setPreferredWidth(100);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public void getInforFromTable() {
        int selectedRow = tblCTKM.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaCTKM.setEditable(false);

            String maCTKM = tblCTKM.getValueAt(selectedRow, 0).toString();
            String tenCTKM = tblCTKM.getValueAt(selectedRow, 1).toString();
            String ngayBDStr = tblCTKM.getValueAt(selectedRow, 2).toString();
            String ngayKTStr = tblCTKM.getValueAt(selectedRow, 3).toString();
            String loaiKM = tblCTKM.getValueAt(selectedRow, 4).toString();
            String maSPOrHD = tblCTKM.getValueAt(selectedRow, 5).toString();
            String phanTramGiamGia = tblCTKM.getValueAt(selectedRow, 6).toString().replace("%", "").trim();
            
         // In ra giá trị đang chọn trong ComboBox cboxLoaiKM
            System.out.println("Giá trị đang chọn trong cboxLoaiKM: " + loaiKM);
            System.out.println(maSPOrHD);

            txtMaCTKM.setText(maCTKM);
            txtTenCTKM.setText(tenCTKM);
            txtPhanTramGiamGia.setText(phanTramGiamGia);

            cboxLoaiKM.setSelectedItem(loaiKM);

            if (loaiKM.equals("Hóa đơn")) {
            	loadMaHD();
                cboxMaHD.setSelectedItem(maSPOrHD); 
            } else if (loaiKM.equals("Sản phẩm")) {
            	loadMaSP();
                cboxMaSP.setSelectedItem(maSPOrHD); 
            }
            cboxMaSP.setVisible(loaiKM.equals("Sản phẩm"));
            cboxMaHD.setVisible(loaiKM.equals("Hóa đơn"));
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date ngayBD = sdf.parse(ngayBDStr);
                Date ngayKT = sdf.parse(ngayKTStr);

                dateNgayBD.setDate(ngayBD);
                dateNgayKT.setDate(ngayKT);
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi chuyển đổi ngày!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } 
    }

    
    public void loadMaSP() {
        try {
            cboxMaSP.removeAllItems();
            ArrayList<String> listMaSP = ctkmBUS.getListMaSP();
            for (String maSP : listMaSP) {
                cboxMaSP.addItem(maSP);
                System.out.println("Đã thêm MaSP: " + maSP);
            }
            if (cboxMaSP.getItemCount() > 0) {
                cboxMaSP.setSelectedIndex(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi nạp danh sách mã sản phẩm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadMaHD() {
        try {
            cboxMaHD.removeAllItems();
            ArrayList<String> listMaHD = ctkmBUS.getListMaHD();
            for (String maHD : listMaHD) {
                cboxMaHD.addItem(maHD);
                System.out.println("Đã thêm MaHD: " + maHD);
            }
            if (cboxMaHD.getItemCount() > 0) {
                cboxMaHD.setSelectedIndex(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi nạp danh sách mã hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }




    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Quản Lý Chương Trình Khuyến Mãi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.add(new CTKMGUI());
        frame.setVisible(true);
    }
}