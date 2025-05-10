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
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import BUS.PhieuXuatBUS;
import DTO.PhieuXuatDTO;

public class PhieuXuatGUI extends JPanel implements ActionListener {

    private JPanel contentPane;
    private int DEFAULT_WIDTH = 1450, DEFAULT_HEIGHT = 800;
    private String color = "#FF5252";
    public DefaultTableModel model;
    private JTable table;
    private JPanel inforContent;
    private JButton btnEditMode;
    private JTextField txtSearch;
    private JComboBox<String> cboxSearch;
    private JTextField txtMaPX, txtNgayXuat, txtGhiChu;
    private PhieuXuatBUS pxBUS = new PhieuXuatBUS();
    private boolean isEditMode = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test PhieuXuatGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1248, 757);
        frame.getContentPane().add(new PhieuXuatGUI());
        frame.setVisible(true);
    }

    public PhieuXuatGUI() {
        String[] columnNames = {"Mã PX", "Ngày Xuất", "Ghi Chú"};
        model = new DefaultTableModel(columnNames, 0);
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
        pLeftHeader.setBorder(new javax.swing.border.TitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, null, null));
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
        btnLamMoi.setBounds(1045, 31, 126, 28);
        btnLamMoi.setActionCommand("Reload");
        btnLamMoi.addActionListener(this);
        pHeaderMain.add(btnLamMoi);

        String[] listKeyWord = {"Mã PX", "Ngày Xuất"};
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
        pContent.setBackground(SystemColor.control);
        pContent.setBounds(0, 103, 1248, 654);
        pContent.setLayout(null);
        add(pContent);

        inforContent = new JPanel();
        inforContent.setBackground(new Color(255, 255, 255));
        inforContent.setBounds(10, 10, 255, 634);
        pContent.add(inforContent);
        inforContent.setLayout(null);

        JLabel lbMaPX = new JLabel("Mã phiếu xuất");
        lbMaPX.setFont(new Font("Verdana", Font.BOLD, 12));
        lbMaPX.setBounds(10, 20, 127, 30);
        inforContent.add(lbMaPX);

        txtMaPX = new JTextField();
        txtMaPX.setBounds(10, 61, 235, 35);
        inforContent.add(txtMaPX);
        txtMaPX.setColumns(10);
        txtMaPX.setEditable(false);
        txtMaPX.setFocusable(false);

        JLabel lbNgayXuat = new JLabel("Ngày xuất");
        lbNgayXuat.setFont(new Font("Verdana", Font.BOLD, 12));
        lbNgayXuat.setBounds(10, 120, 127, 30);
        inforContent.add(lbNgayXuat);

        txtNgayXuat = new JTextField();
        txtNgayXuat.setColumns(10);
        txtNgayXuat.setBounds(10, 161, 235, 35);
        inforContent.add(txtNgayXuat);
        txtNgayXuat.setEditable(false); // Không cho phép chỉnh sửa
        txtNgayXuat.setFocusable(false); // Không cho phép focus

        JLabel lbGhiChu = new JLabel("Ghi chú");
        lbGhiChu.setFont(new Font("Verdana", Font.BOLD, 12));
        lbGhiChu.setBounds(10, 220, 127, 30);
        inforContent.add(lbGhiChu);

        txtGhiChu = new JTextField();
        txtGhiChu.setColumns(10);
        txtGhiChu.setBounds(10, 261, 235, 35);
        inforContent.add(txtGhiChu);
        txtGhiChu.setEditable(false);
        txtGhiChu.setFocusable(false);

        btnEditMode = new JButton("");
        btnEditMode.setIcon(new ImageIcon(SanPhamGUI.class.getResource("/image/edit20.png")));
        btnEditMode.setFocusPainted(false);
        btnEditMode.setBorderPainted(false);
        btnEditMode.setBackground(null);
        btnEditMode.setBounds(208, 11, 37, 20);
        btnEditMode.addActionListener(e -> toggleEditMode());
        inforContent.add(btnEditMode);

        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> getInforFromTable());
        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        table.setRowHeight(23);
        table.getTableHeader().setPreferredSize(new Dimension(0, 23));
        table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));

        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(275, 10, 914, 633);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(220, 220, 220)));
        pContent.add(scrollPane);

        pxBUS.docDSPhieuXuat();
        pxBUS.updateTable(model);
    }

    public void getInforFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaPX.setText(model.getValueAt(selectedRow, 0).toString());
            txtNgayXuat.setText(model.getValueAt(selectedRow, 1).toString());
            txtGhiChu.setText(model.getValueAt(selectedRow, 2).toString());
        }
    }

    public void toggleEditMode() {
        isEditMode = !isEditMode;
        txtMaPX.setEditable(isEditMode);
        txtMaPX.setFocusable(isEditMode);
        txtGhiChu.setEditable(isEditMode);
        txtGhiChu.setFocusable(isEditMode);
    }

    public void toggleEditInTheEnd() {
        isEditMode = false;
        txtMaPX.setEditable(false);
        txtMaPX.setFocusable(false);
        txtGhiChu.setEditable(false);
        txtGhiChu.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Thêm"))
            addPhieuXuat();
        else if (command.equals("Sửa"))
            editPhieuXuat();
        else if (command.equals("Xóa"))
            deletePhieuXuat();
        else if (command.equals("Tìm kiếm"))
            timKiem();
        else if (command.equals("Reload"))
            pxBUS.updateTable(model);
        else if(command.equals("Xuất Excel"))
			xuatExcel();
		else if(command.equals("Xuất PDF"))
			xuatPDF();
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
    
    public void xuatPDF() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu xuất để xuất PDF!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            table.requestFocus();
            return;
        }

        String maPX = (String) table.getValueAt(selectedRow, 0);
        try {
            PDFReporter pdfReporter = new PDFReporter();
            pdfReporter.writePhieuXuat(maPX);
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    public void timKiem() {
        String tuKhoa = txtSearch.getText().trim();
        String tieuChi = cboxSearch.getSelectedItem().toString();
        ArrayList<PhieuXuatDTO> result = pxBUS.searchPhieuXuat(tuKhoa, tieuChi);

        model.setRowCount(0);
        for (PhieuXuatDTO px : result)
            model.addRow(new Object[] {px.getMaPX(), px.getNgayXuat(), px.getGhiChu()});
        if (result.isEmpty())
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deletePhieuXuat() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu xuất để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maPX = table.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phiếu xuất này?", "Xóa Phiếu Xuất", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            pxBUS.deletePhieuXuat(maPX);
            model.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Xóa phiếu xuất thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            toggleEditInTheEnd();
        }
    }

    public void addPhieuXuat() {
        String maPX = txtMaPX.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        LocalDate now = LocalDate.now();
        String ngayXuat = now.toString(); // Tự động lấy ngày hiện tại

        if (maPX.isEmpty() || ghiChu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!maPX.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(null, "Mã phiếu xuất phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pxBUS.isDuplicatePX(maPX)) {
            JOptionPane.showMessageDialog(null, "Mã phiếu xuất đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pxBUS.addPhieuXuat(maPX, ngayXuat, ghiChu);
        model.addRow(new Object[] {maPX, ngayXuat, ghiChu});
        JOptionPane.showMessageDialog(null, "Thêm phiếu xuất thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
        toggleEditInTheEnd();
    }

    public void clearFields() {
        txtMaPX.setText("");
        txtNgayXuat.setText("");
        txtGhiChu.setText("");
    }

    public void editPhieuXuat() {
        if (!isEditMode) {
            JOptionPane.showMessageDialog(this, "Vui lòng bật chế độ chỉnh sửa trước!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một phiếu xuất để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String maPX = model.getValueAt(selectedRow, 0).toString();
        String newMaPX = txtMaPX.getText().trim();
        String newGhiChu = txtGhiChu.getText().trim();

        if (newMaPX.isEmpty() || newGhiChu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!newMaPX.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(null, "Mã phiếu xuất phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pxBUS.checkEdit(newMaPX, maPX)) {
            JOptionPane.showMessageDialog(null, "Đã tồn tại mã phiếu xuất này trong danh sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pxBUS.editPhieuXuat(newMaPX, model.getValueAt(selectedRow, 1).toString(), newGhiChu, maPX);
        model.setValueAt(newMaPX, selectedRow, 0);
        model.setValueAt(newGhiChu, selectedRow, 2);
        clearFields();
        toggleEditInTheEnd();
        JOptionPane.showMessageDialog(null, "Sửa thông tin thành công!", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
    }
}