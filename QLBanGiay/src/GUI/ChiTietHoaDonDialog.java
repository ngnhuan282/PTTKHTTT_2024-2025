package GUI;

import DTO.*;
import BUS.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDonDialog extends JDialog {
    private JTextField txtMaKH;
    private JComboBox<HTTTDTO> comboHTTT;
    private JButton btnPrint, btnThanhToan;
    private boolean daThanhToan = false;
    private Runnable onSuccessCallback;

    public ChiTietHoaDonDialog(String maHD, String maKH, String maNV, ArrayList<ChiTietHDDTO> dsCTHD, double tongTien, Runnable onSuccessCallback) throws SQLException {
        this.onSuccessCallback = onSuccessCallback;

        setTitle("Chi tiết hoá đơn");
        setSize(600, 700);
        setLayout(new BorderLayout());

        KhachHangBUS khachHangBUS = new KhachHangBUS();
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        SanPhamBUS sanPhamBUS = new SanPhamBUS();
        HTTTBUS htttBUS = new HTTTBUS();

        String tenNV = "";
        for (NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
            if (nv.getMaNV().equals(maNV)) {
                tenNV = nv.getHo() + " " + nv.getTen();
                break;
            }
        }

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        topPanel.add(new JLabel("Mã khách hàng:"));
        JPanel khPanel = new JPanel(new BorderLayout(5, 0));
        txtMaKH = new JTextField(maKH);
        JButton btnChonKH = new JButton("...");
        khPanel.add(txtMaKH, BorderLayout.CENTER);
        khPanel.add(btnChonKH, BorderLayout.EAST);
        topPanel.add(khPanel);

        topPanel.add(new JLabel("Nhân viên:"));
        topPanel.add(new JLabel(maNV + " - " + tenNV));

        topPanel.add(new JLabel("Hình thức thanh toán:"));
        comboHTTT = new JComboBox<>();
        for (HTTTDTO h : htttBUS.getListHTTT()) {
            comboHTTT.addItem(h);
        }
        topPanel.add(comboHTTT);

        topPanel.add(new JLabel("Tổng tiền:"));
        topPanel.add(new JLabel(String.format("%,.0f VND", tongTien)));

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (ChiTietHDDTO cthd : dsCTHD) {
            String tenSP = sanPhamBUS.getTenSP(cthd.getMaSP());
            model.addRow(new Object[]{
                cthd.getMaSP(),
                tenSP,
                cthd.getSoLuong(),
                String.format("%,.0f", cthd.getDonGia()),
                String.format("%,.0f", cthd.getThanhTien())
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnChonKH.addActionListener(e -> {
            JDialog dialog = new JDialog(this, "Chọn khách hàng", true);
            dialog.setSize(600, 300);
            dialog.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JTextField txtSearch = new JTextField();
            JTable tblKH = new JTable();
            DefaultTableModel tblModel = new DefaultTableModel(new String[]{"Mã KH", "Họ", "Tên", "SĐT"}, 0);
            for (KhachHangDTO kh : khachHangBUS.getListKhachHang()) {
                tblModel.addRow(new Object[]{kh.getMaKH(), kh.getHo(), kh.getTen(), kh.getSdt()});
            }

            tblKH.setModel(tblModel);
            panel.add(txtSearch, BorderLayout.NORTH);
            panel.add(new JScrollPane(tblKH), BorderLayout.CENTER);
            dialog.add(panel);

            txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    String keyword = txtSearch.getText().toLowerCase();
                    tblModel.setRowCount(0);
                    for (KhachHangDTO kh : khachHangBUS.getListKhachHang()) {
                        if (kh.getMaKH().toLowerCase().contains(keyword) ||
                            kh.getHo().toLowerCase().contains(keyword) ||
                            kh.getTen().toLowerCase().contains(keyword)) {
                            tblModel.addRow(new Object[]{kh.getMaKH(), kh.getHo(), kh.getTen(), kh.getSdt()});
                        }
                    }
                }
            });

            tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int selectedRow = tblKH.getSelectedRow();
                    if (selectedRow != -1) {
                        txtMaKH.setText(tblModel.getValueAt(selectedRow, 0).toString());
                        checkEnableButtons();
                        dialog.dispose();
                    }
                }
            });

            dialog.setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        btnThanhToan = new JButton("Thanh toán");
        btnPrint = new JButton("In hóa đơn");
        bottomPanel.add(btnThanhToan);
        bottomPanel.add(btnPrint);
        add(bottomPanel, BorderLayout.SOUTH);

        btnThanhToan.setEnabled(false);
        btnPrint.setEnabled(false);

        comboHTTT.addActionListener(e -> checkEnableButtons());
        txtMaKH.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { checkEnableButtons(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { checkEnableButtons(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { checkEnableButtons(); }
        });

        btnThanhToan.addActionListener(e -> {
            if (!daThanhToan && handleLuuHoaDon(maHD, maNV, dsCTHD, tongTien)) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                if (onSuccessCallback != null) onSuccessCallback.run();
                dispose();
            }
        });

        btnPrint.addActionListener(e -> {
            if (!daThanhToan && handleLuuHoaDon(maHD, maNV, dsCTHD, tongTien)) {
                JOptionPane.showMessageDialog(this, "Đã lưu và in hóa đơn!");
                if (onSuccessCallback != null) onSuccessCallback.run();
            }

            try {
                HoaDonGUI.xuatPDF(maHD);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });



    }

    private void checkEnableButtons() {
        boolean enable = !txtMaKH.getText().trim().isEmpty() && comboHTTT.getSelectedItem() != null;
        btnThanhToan.setEnabled(enable);
        btnPrint.setEnabled(enable);
    }

    private boolean handleLuuHoaDon(String maHD, String maNV, ArrayList<ChiTietHDDTO> dsCTHD, double tongTien) {
        if (daThanhToan) return false;

        try {
            String maKHChon = txtMaKH.getText().trim();
            HTTTDTO selectedHTTT = (HTTTDTO) comboHTTT.getSelectedItem();
            String maHTTT = selectedHTTT != null ? selectedHTTT.getMaHTTT() : null;

            HoaDonBUS hoaDonBUS = new HoaDonBUS();
            ChiTietHoaDonBUS ctBUS = new ChiTietHoaDonBUS();
            SanPhamBUS spBUS = new SanPhamBUS();

            // ✅ XÓA dữ liệu chi tiết hóa đơn cũ (nếu có)
            ctBUS.deleteByMaHD(maHD);

            hoaDonBUS.addHoaDon(maHD, maKHChon, maNV, new java.sql.Date(System.currentTimeMillis()), Math.round(tongTien * 100.0) / 100.0);
            hoaDonBUS.docDSHD(); // ✅ cập nhật lại danh sách hóa đơn sau khi thêm
            
            for (ChiTietHDDTO cthd : dsCTHD) {
                cthd.setMaHTTT(maHTTT);
                ctBUS.addCTHD(
                    cthd.getMaHD(), cthd.getMaSP(), cthd.getSoLuong(),
                    cthd.getDonGia(), cthd.getThanhTien(), cthd.getTrangThai(), cthd.getMaTTT()
                );
                spBUS.truSoLuongSauKhiBan(cthd.getMaSP(), cthd.getSoLuong());
            }

            daThanhToan = true;
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }




}
