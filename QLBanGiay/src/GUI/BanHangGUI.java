package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import BUS.HTTTBUS;
import BUS.HoaDonBUS;
import BUS.KhachHangBUS;
import BUS.LoaiBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietHDDTO;
import DTO.HTTTDTO;
import DTO.KhachHangDTO;
import DTO.LoaiDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;



public class BanHangGUI extends JPanel {
    private JTable tableSanPham, tableGioHang;
    private JTextField txtMaSP, txtTenSP, txtDonGia, txtSoLuong,txtNhanVien;
    private JComboBox<String> cbbLoaiSP;
    private JLabel lblAnh;
    private JButton btnThemVaoGio, btnXoa, btnXuatHD,btnChonNV,btnCapNhat,btnXoaSanPham;
    private JComboBox<HTTTDTO> comboBoxHTTT;
    private HTTTBUS htttBUS = new HTTTBUS();
    private SanPhamBUS sanPhamBUS = new SanPhamBUS();
    private LoaiBUS loaiBUS = new LoaiBUS();
    private int lastSelectedRowSanPham = -1;
    private int lastSelectedRowGioHang = -1;


    public BanHangGUI() {
        setLayout(null);
        setPreferredSize(new Dimension(1248, 800));

        // ==== Danh sách sản phẩm ====
        JPanel panelDanhSach = new JPanel(null);
        panelDanhSach.setBorder(new TitledBorder("Danh sách sản phẩm"));
        panelDanhSach.setBounds(10, 10, 800, 320);
        add(panelDanhSach);

        tableSanPham = new JTable();
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        scrollSanPham.setBounds(10, 20, 780, 290);
        panelDanhSach.add(scrollSanPham);

        // ==== Chi tiết sản phẩm ====
        JPanel panelChiTiet = new JPanel(null);
        panelChiTiet.setBorder(new TitledBorder("Chi tiết sản phẩm"));
        panelChiTiet.setBounds(820, 10, 400, 320);
        add(panelChiTiet);

        JLabel lblLoai = new JLabel("Loại SP");
        lblLoai.setBounds(20, 30, 80, 25);
        panelChiTiet.add(lblLoai);
        cbbLoaiSP = new JComboBox<>(new String[]{"0 - Chọn loại"});
        cbbLoaiSP.setBounds(120, 30, 250, 25);
        panelChiTiet.add(cbbLoaiSP);
        cbbLoaiSP.setEditable(false); // ✅ Không cho người dùng gõ vào

        JLabel lblMa = new JLabel("Mã SP");
        lblMa.setBounds(20, 70, 80, 25);
        panelChiTiet.add(lblMa);
        txtMaSP = new JTextField();
        txtMaSP.setBounds(120, 70, 250, 25);
        txtMaSP.setEditable(false);
        panelChiTiet.add(txtMaSP);

        JLabel lblTen = new JLabel("Tên SP");
        lblTen.setBounds(20, 110, 80, 25);
        panelChiTiet.add(lblTen);
        txtTenSP = new JTextField();
        txtTenSP.setBounds(120, 110, 250, 25);
        txtTenSP.setEditable(false);
        panelChiTiet.add(txtTenSP);

        JLabel lblGia = new JLabel("Đơn giá");
        lblGia.setBounds(20, 150, 80, 25);
        panelChiTiet.add(lblGia);
        txtDonGia = new JTextField();
        txtDonGia.setBounds(120, 150, 250, 25);
        txtDonGia.setEditable(false);
        panelChiTiet.add(txtDonGia);

        JLabel lblSL = new JLabel("Số lượng");
        lblSL.setBounds(20, 190, 80, 25);
        panelChiTiet.add(lblSL);
        txtSoLuong = new JTextField("1");
        txtSoLuong.setBounds(120, 190, 250, 25);
        panelChiTiet.add(txtSoLuong);
        

        // === Dời Nhân viên xuống dưới HTTT ===
        JLabel lblNV = new JLabel("Nhân Viên");
        lblNV.setBounds(20, 230, 80, 25);
        panelChiTiet.add(lblNV);
        JTextField txtMaNV = new JTextField();
        txtMaNV.setBounds(120, 230, 200, 25);
        txtMaNV.setEditable(false);
        panelChiTiet.add(txtMaNV);
        txtNhanVien = txtMaNV;

        JButton btnChonNV = new JButton("...");
        btnChonNV.setBounds(325, 230, 30, 25);
        panelChiTiet.add(btnChonNV);
        


        // Bắt sự kiện click để hiển thị danh sách nhân viên
        btnChonNV.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Chọn nhân viên");
            dialog.setSize(600, 300);
            dialog.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JTextField txtSearch = new JTextField();
            JTable table = new JTable();
            JScrollPane scrollPane = new JScrollPane(table);
            DefaultTableModel model = new DefaultTableModel(new String[]{"Mã NV", "Họ", "Tên", "SĐT"}, 0);

            final NhanVienBUS nvBUS;
            try {
                nvBUS = new NhanVienBUS();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                return;
            }

            for (NhanVienDTO nv : nvBUS.getListNhanVien()) {
                model.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
            }

            table.setModel(model);
            panel.add(txtSearch, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            dialog.add(panel);

            txtSearch.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String keyword = txtSearch.getText().toLowerCase();
                    model.setRowCount(0);
                    for (NhanVienDTO nv : nvBUS.getListNhanVien()) {
                        if (nv.getMaNV().toLowerCase().contains(keyword) ||
                            nv.getHo().toLowerCase().contains(keyword) ||
                            nv.getTen().toLowerCase().contains(keyword)) {
                            model.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getSdt()});
                        }
                    }
                }
            });

            table.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String maNV = model.getValueAt(selectedRow, 0).toString();
                        txtMaNV.setText(maNV);
                        dialog.dispose();
                    }
                }
            });

            dialog.setVisible(true);
        });




        int buttonWidth = 110;
        int buttonHeight = 25;
        int gap = 20;
        int buttonY = 280;
        int totalWidth = buttonWidth * 3 + gap * 2; // = 330 + 40 = 370
        int startX = (400 - totalWidth) / 2; // căn giữa trong panelChiTiet

        btnThemVaoGio = new JButton("Thêm vào giỏ");
        btnThemVaoGio.setBounds(startX, buttonY, buttonWidth, buttonHeight);
        panelChiTiet.add(btnThemVaoGio);

        btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setBounds(startX + buttonWidth + gap, buttonY, buttonWidth, buttonHeight);
        btnCapNhat.setEnabled(false);
        panelChiTiet.add(btnCapNhat);

        btnXoaSanPham = new JButton("Xóa SP");
        btnXoaSanPham.setBounds(startX + 2 * (buttonWidth + gap), buttonY, buttonWidth, buttonHeight);
        btnXoaSanPham.setEnabled(false);
        panelChiTiet.add(btnXoaSanPham);


        btnXoaSanPham.addActionListener(e -> {
            int selectedRow = tableGioHang.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa sản phẩm này khỏi giỏ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
                    String maSP = model.getValueAt(selectedRow, 0).toString();
                    int slXoa = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());

                    model.removeRow(selectedRow);
                    capNhatTonKhoTamThoiSauKhiXoa(maSP, slXoa); // ✅ cộng lại tồn kho

                    JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi giỏ hàng.");

                    // Reset form
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtDonGia.setText("");
                    txtSoLuong.setText("1");
                    cbbLoaiSP.setSelectedIndex(0);
                    btnCapNhat.setEnabled(false);
                    btnXoaSanPham.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trong giỏ để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });


        
        
        
        btnCapNhat.addActionListener(e -> {
            int row = tableGioHang.getSelectedRow();
            if (row != -1) {
                int slMoi = Integer.parseInt(txtSoLuong.getText());
                double dg = Double.parseDouble(txtDonGia.getText());
                String maSP = txtMaSP.getText().trim();

                // Lấy số lượng cũ trong giỏ
                int slCu = Integer.parseInt(tableGioHang.getValueAt(row, 2).toString());

                // Tính tồn kho hiện tại trong bảng sản phẩm
                int tonKhoDB = sanPhamBUS.getDssp().stream()
                        .filter(sp -> sp.getMaSP().equals(maSP))
                        .mapToInt(sp -> sp.getSoLuong())
                        .findFirst()
                        .orElse(0);

                int daDatTruChinhNo = 0; // tổng đã đặt trong giỏ trừ chính nó
                DefaultTableModel modelGH = (DefaultTableModel) tableGioHang.getModel();
                for (int i = 0; i < modelGH.getRowCount(); i++) {
                    if (i != row && modelGH.getValueAt(i, 0).toString().equals(maSP)) {
                        daDatTruChinhNo += Integer.parseInt(modelGH.getValueAt(i, 2).toString());
                    }
                }

                int tonKhoConLai = tonKhoDB - daDatTruChinhNo;
                if (slMoi > tonKhoConLai) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho còn lại! Chỉ còn: " + tonKhoConLai, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // ✅ Cập nhật tồn kho tạm theo chênh lệch
                int chenhLech = slCu - slMoi; // nếu > 0 là giảm sl -> cộng lại tồn kho
                if (chenhLech != 0) {
                    capNhatTonKhoTamThoiSauKhiXoa(maSP, chenhLech);
                }

                modelGH.setValueAt(slMoi, row, 2);
                modelGH.setValueAt(dg, row, 3);
                modelGH.setValueAt(slMoi * dg, row, 4);

                // Reset form
                txtMaSP.setText("");
                txtTenSP.setText("");
                txtDonGia.setText("");
                txtSoLuong.setText("1");
                cbbLoaiSP.setSelectedIndex(0);

                btnThemVaoGio.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnXoaSanPham.setEnabled(false);
            }
        });



       

        
//        btnThemVaoGio.addActionListener(e -> {
//            String ma = txtMaSP.getText().trim();
//            String ten = txtTenSP.getText().trim();
//            String gia = txtDonGia.getText().trim();
//            String sl = txtSoLuong.getText().trim();
//            String maNV = txtMaNV.getText().trim(); // kiểm tra thêm nhân viên
//            
//
//            if (ma.isEmpty() || ten.isEmpty() || gia.isEmpty() || sl.isEmpty() || maNV.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Không được để trống bất kỳ ô nào!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            try {
//                int soLuong = Integer.parseInt(sl);
//                if (soLuong <= 0) {
//                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//
//             // ✅ Tính tồn kho sau khi trừ những sản phẩm đã có trong giỏ
//                int tonKhoDB = sanPhamBUS.getDssp().stream()
//                    .filter(sp -> sp.getMaSP().equals(ma))
//                    .mapToInt(sp -> sp.getSoLuong())
//                    .findFirst()
//                    .orElse(0);
//
//                // ✅ Tính số lượng đã đặt tạm trong giỏ
//                int daDat = 0;
//                DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
//                for (int i = 0; i < model.getRowCount(); i++) {
//                    if (model.getValueAt(i, 0).toString().equals(ma)) {
//                        daDat += Integer.parseInt(model.getValueAt(i, 2).toString());
//                    }
//                }
//
//                int tonKhoConLai = tonKhoDB - daDat;
//
//                if (soLuong > tonKhoConLai) {
//                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho còn lại! Chỉ còn: " + tonKhoConLai, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//
//
//                double donGia = Double.parseDouble(gia);
//                double thanhTien = soLuong * donGia;
//
//                DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
//                model.addRow(new Object[]{ma, ten, soLuong, donGia, thanhTien});
//
//                // Reset form (trừ mã nhân viên)
//                txtMaSP.setText("");
//                txtTenSP.setText("");
//                txtDonGia.setText("");
//                txtSoLuong.setText("1");
//                cbbLoaiSP.setSelectedIndex(0);
//
//                btnThemVaoGio.setEnabled(true);
//                btnCapNhat.setEnabled(false);
//                btnXoaSanPham.setEnabled(false);
//
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Đơn giá hoặc số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        });
        btnThemVaoGio.addActionListener(e -> {
            String ma = txtMaSP.getText().trim();
            String ten = txtTenSP.getText().trim();
            String gia = txtDonGia.getText().trim();
            String sl = txtSoLuong.getText().trim();
            String maNV = txtMaNV.getText().trim();

            if (ma.isEmpty() || ten.isEmpty() || gia.isEmpty() || sl.isEmpty() || maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống bất kỳ ô nào!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int soLuong = Integer.parseInt(sl);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int tonKhoDB = sanPhamBUS.getDssp().stream()
                    .filter(sp -> sp.getMaSP().equals(ma))
                    .mapToInt(sp -> sp.getSoLuong())
                    .findFirst()
                    .orElse(0);

                int daDat = 0;
                DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).toString().equals(ma)) {
                        daDat += Integer.parseInt(model.getValueAt(i, 2).toString());
                    }
                }

                int tonKhoConLai = tonKhoDB - daDat;
                if (soLuong > tonKhoConLai) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho còn lại! Chỉ còn: " + tonKhoConLai, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double donGia = Double.parseDouble(gia);
                double thanhTien = soLuong * donGia;

                model.addRow(new Object[]{ma, ten, soLuong, donGia, thanhTien});
                truTonKhoTamThoi(ma, soLuong); // ✅ Trừ tồn kho tạm trên table sản phẩm

                // Reset form
                txtMaSP.setText("");
                txtTenSP.setText("");
                txtDonGia.setText("");
                txtSoLuong.setText("1");
                cbbLoaiSP.setSelectedIndex(0);

                btnThemVaoGio.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnXoaSanPham.setEnabled(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá hoặc số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });



        // ==== Giỏ hàng ====
        JPanel panelGioHang = new JPanel(null);
        panelGioHang.setBorder(new TitledBorder("Giỏ hàng"));
        panelGioHang.setBounds(10, 350, 1210, 300);
        add(panelGioHang);

        tableGioHang = new JTable();
        JScrollPane scrollGio = new JScrollPane(tableGioHang);
        scrollGio.setBounds(10, 20, 1190, 230);
        panelGioHang.add(scrollGio);
        
//        tableGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int row = tableGioHang.getSelectedRow();
//                if (row != -1) {
//                    txtMaSP.setText(tableGioHang.getValueAt(row, 0).toString());
//                    txtTenSP.setText(tableGioHang.getValueAt(row, 1).toString());
//                    txtSoLuong.setText(tableGioHang.getValueAt(row, 2).toString());
//                    txtDonGia.setText(tableGioHang.getValueAt(row, 3).toString());
//
//                    btnThemVaoGio.setEnabled(false);
//                    btnCapNhat.setEnabled(true);
//                    btnXoaSanPham.setEnabled(true);
//                }
//            }
//        });
        tableGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableGioHang.getSelectedRow();
                if (row == lastSelectedRowGioHang) {
                    tableGioHang.clearSelection();
                    lastSelectedRowGioHang = -1;
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtDonGia.setText("");
                    txtSoLuong.setText("1");
                    cbbLoaiSP.setSelectedIndex(0);
                    btnCapNhat.setEnabled(false);
                    btnXoaSanPham.setEnabled(false);
                    btnThemVaoGio.setEnabled(true);
                    return;
                }

                lastSelectedRowGioHang = row;
                txtMaSP.setText(tableGioHang.getValueAt(row, 0).toString());
                txtTenSP.setText(tableGioHang.getValueAt(row, 1).toString());
                txtSoLuong.setText(tableGioHang.getValueAt(row, 2).toString());
                txtDonGia.setText(tableGioHang.getValueAt(row, 3).toString());

                btnThemVaoGio.setEnabled(false);
                btnCapNhat.setEnabled(true);
                btnXoaSanPham.setEnabled(true);
            }
        });


        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(980, 260, 100, 25);
        panelGioHang.add(btnXoa);
        
        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa toàn bộ giỏ hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel gioModel = (DefaultTableModel) tableGioHang.getModel();

                // ✅ BƯỚC 1: Cập nhật lại tồn kho tạm
                for (int i = 0; i < gioModel.getRowCount(); i++) {
                    String maSP = gioModel.getValueAt(i, 0).toString();
                    int soLuong = Integer.parseInt(gioModel.getValueAt(i, 2).toString());
                    capNhatTonKhoTamThoiSauKhiXoa(maSP, soLuong);
                }

                // ✅ BƯỚC 2: Xóa tất cả dòng
                gioModel.setRowCount(0);

                // Reset form (trừ mã nhân viên)
                txtMaSP.setText("");
                txtTenSP.setText("");
                txtDonGia.setText("");
                txtSoLuong.setText("1");
                cbbLoaiSP.setSelectedIndex(0);
                txtNhanVien.setText("");

                btnThemVaoGio.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnXoaSanPham.setEnabled(false);

                JOptionPane.showMessageDialog(this, "Đã xóa toàn bộ giỏ hàng.");
            }
        });



        btnXuatHD = new JButton("Xuất hóa đơn");
        btnXuatHD.setBounds(1090, 260, 110, 25);
        panelGioHang.add(btnXuatHD);
        

        btnXuatHD.addActionListener(e -> {
            if (tableGioHang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Giỏ hàng trống, không thể xuất hóa đơn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNV = txtNhanVien.getText().trim();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên trước khi xuất hóa đơn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<ChiTietHDDTO> dsCTHD = new ArrayList<>();
            double tongTien = 0;

            try {
                HoaDonBUS hoaDonBUS = new HoaDonBUS();
                String maHD = hoaDonBUS.getMaHD();

                DefaultTableModel model = (DefaultTableModel) tableGioHang.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maSP = model.getValueAt(i, 0).toString();
                    int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                    double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());
                    double thanhTien = soLuong * donGia;
                    tongTien += thanhTien;

                    dsCTHD.add(new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien, "0", null));
                }
                dsCTHD = gopSanPhamTrung(dsCTHD);
                // Tạo dialog, truyền callback để xoá giỏ hàng nếu thành công
                ChiTietHoaDonDialog dialog = new ChiTietHoaDonDialog(maHD, "", maNV, dsCTHD, tongTien, () -> {
                    DefaultTableModel cartModel = (DefaultTableModel) tableGioHang.getModel();
                    cartModel.setRowCount(0);
                    txtMaSP.setText("");
                    txtTenSP.setText("");
                    txtDonGia.setText("");
                    txtSoLuong.setText("1");
                    cbbLoaiSP.setSelectedIndex(0);

                    btnThemVaoGio.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoaSanPham.setEnabled(false);
                    
                    txtNhanVien.setText("");
                    // 👉 Thêm dòng dưới để cập nhật lại bảng sản phẩm
                    sanPhamBUS.docDSSP();     // đọc lại dữ liệu từ DB
                    hienThiSanPham();         // cập nhật lại bảng hiển thị
                });
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xử lý: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });







        // ==== Giả lập dữ liệu mẫu ====
        String[] cols = {"Mã SP", "Tên SP", "Đơn giá", "Còn lại", "Đơn vị tính"};
        Object[][] data = {
                {"111", "Pizza Hải Sản Pesto Xanh", "169000", "34", "Cái"},
                {"112", "Pizza Hải Sản Nhiệt Đới", "149000", "30", "Cái"},
                {"113", "Pizza Cocktail", "139000", "25", "Cái"},
        };
        tableSanPham.setModel(new DefaultTableModel(data, cols));

        tableGioHang.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}
        ));
        tableGioHang.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tableGioHang.setRowHeight(23);
        tableGioHang.setFont(new Font("Tahoma", Font.PLAIN, 13));

        sanPhamBUS.docDSSP(); // load dữ liệu từ DB
        hienThiSanPham();     // gán vào table
        themSuKienClickTable(); // gán sự kiện click vào bảng
        LoaiBUS loaiBUS = new LoaiBUS();
        loaiBUS.docDSLoai();
        cbbLoaiSP.removeAllItems();
        cbbLoaiSP.addItem("0 - Chọn loại"); // mục đầu
        for (LoaiDTO loai : loaiBUS.getDsloai()) {
            cbbLoaiSP.addItem(loai.getTenLoaiSP());
        }

        cbbLoaiSP.setEditable(false); // Cực kỳ quan trọng
    }
    
    private String getTenLoaiSP(int maLoaiSP) {
        for (LoaiDTO loai : loaiBUS.getDsloai()) {
            if (loai.getMaLoaiSP() == maLoaiSP) {
                return loai.getTenLoaiSP();
            }
        }
        return "Không xác định";
    }

    
    private void hienThiSanPham() {
        String[] cols = {
            "Mã SP", "Tên SP", "Loại sản phẩm", "Đơn giá", "Còn lại", 
            "Đơn vị tính", "Kích thước", "Chất liệu", "Kiểu dáng"
        };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        tableSanPham.setRowHeight(23);
        tableSanPham.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tableSanPham.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        for (SanPhamDTO sp : sanPhamBUS.getDssp()) {
            String tenLoai = getTenLoaiSP(sp.getMaLoaiSP());
            Object[] row = {
                sp.getMaSP(),
                sp.getTenSP(),
                tenLoai,
                sp.getDonGia(),
                sp.getSoLuong(),
                sp.getDonViTinh(),
                sp.getKichThuoc(),
                sp.getChatLieu(),
                sp.getKieuDang()
            };
            model.addRow(row);
        }

        tableSanPham.setModel(model);
    }


    private void themSuKienClickTable() {
//        tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                int row = tableSanPham.getSelectedRow();
//                if (row != -1) {
//                    // Lấy dữ liệu từ hàng được chọn
//                    String maSP = tableSanPham.getValueAt(row, 0).toString();
//                    String tenSP = tableSanPham.getValueAt(row, 1).toString();
//                    String tenLoai = tableSanPham.getValueAt(row, 2).toString(); // cột 2 là Tên loại
//                    String donGia = tableSanPham.getValueAt(row, 3).toString();
//
//                    // Đưa vào form chi tiết
//                    txtMaSP.setText(maSP);
//                    txtTenSP.setText(tenSP);
//                    txtDonGia.setText(donGia);
//                    txtSoLuong.setText("1");
//
//                    // Set lại combo box theo tên loại
//                    for (int i = 0; i < cbbLoaiSP.getItemCount(); i++) {
//                        if (cbbLoaiSP.getItemAt(i).equalsIgnoreCase(tenLoai)) {
//                            cbbLoaiSP.setSelectedIndex(i);
//                            break;
//                        }
//                    }
//                }
//            }
//        });
    	tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
    	    public void mouseClicked(java.awt.event.MouseEvent e) {
    	        int row = tableSanPham.getSelectedRow();
    	        if (row == lastSelectedRowSanPham) {
    	            tableSanPham.clearSelection();
    	            lastSelectedRowSanPham = -1;
    	            txtMaSP.setText("");
    	            txtTenSP.setText("");
    	            txtDonGia.setText("");
    	            txtSoLuong.setText("1");
    	            cbbLoaiSP.setSelectedIndex(0);
    	            return;
    	        }

    	        lastSelectedRowSanPham = row;
    	        String maSP = tableSanPham.getValueAt(row, 0).toString();
    	        String tenSP = tableSanPham.getValueAt(row, 1).toString();
    	        String tenLoai = tableSanPham.getValueAt(row, 2).toString();
    	        String donGia = tableSanPham.getValueAt(row, 3).toString();

    	        txtMaSP.setText(maSP);
    	        txtTenSP.setText(tenSP);
    	        txtDonGia.setText(donGia);
    	        txtSoLuong.setText("1");

    	        for (int i = 0; i < cbbLoaiSP.getItemCount(); i++) {
    	            if (cbbLoaiSP.getItemAt(i).equalsIgnoreCase(tenLoai)) {
    	                cbbLoaiSP.setSelectedIndex(i);
    	                break;
    	            }
    	        }
    	    }
    	});

    }

    public ArrayList<ChiTietHDDTO> gopSanPhamTrung(ArrayList<ChiTietHDDTO> dsCTHD) {
        Map<String, ChiTietHDDTO> map = new LinkedHashMap<>();

        for (ChiTietHDDTO item : dsCTHD) {
            String key = item.getMaSP();

            if (map.containsKey(key)) {
                ChiTietHDDTO existing = map.get(key);
                int tongSL = existing.getSoLuong() + item.getSoLuong();
                double tongTT = existing.getThanhTien() + item.getThanhTien();

                existing.setSoLuong(tongSL);
                existing.setThanhTien(tongTT);
            } else {
                map.put(key, item);
            }
        }

        return new ArrayList<>(map.values());
    }

    private void truTonKhoTamThoi(String maSP, int soLuongTru) {
        DefaultTableModel model = (DefaultTableModel) tableSanPham.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equals(maSP)) {
                int soLuongHienTai = Integer.parseInt(model.getValueAt(i, 4).toString()); // Cột 4 là tồn kho
                model.setValueAt(soLuongHienTai - soLuongTru, i, 4); // Cập nhật tồn kho mới
                break;
            }
        }
    }
    
    private void capNhatTonKhoTamThoiSauKhiXoa(String maSP, int soLuongXoa) {
        DefaultTableModel modelSP = (DefaultTableModel) tableSanPham.getModel();
        for (int i = 0; i < modelSP.getRowCount(); i++) {
            if (modelSP.getValueAt(i, 0).toString().equals(maSP)) {
                int slHienTai = Integer.parseInt(modelSP.getValueAt(i, 4).toString()); // cột tồn kho
                modelSP.setValueAt(slHienTai + soLuongXoa, i, 4);
                break;
            }
        }
    }
    


}