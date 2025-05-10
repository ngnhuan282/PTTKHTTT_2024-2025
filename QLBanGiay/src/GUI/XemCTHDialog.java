package GUI;

import DTO.ChiTietHDDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import BUS.HTTTBUS;
import BUS.KhachHangBUS;
import BUS.NhanVienBUS;
import BUS.SanPhamBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class XemCTHDialog extends JDialog {
    public XemCTHDialog(String maHD, String maKH, String maNV,String maHTTT, ArrayList<ChiTietHDDTO> dsCTHD, double tongTien) throws SQLException {
        setTitle("Chi tiết hoá đơn");
        setSize(600, 600);
        setLayout(new BorderLayout());

        KhachHangBUS khachHangBUS = new KhachHangBUS();
        NhanVienBUS nhanVienBUS = new NhanVienBUS();
        SanPhamBUS sanPhamBUS = new SanPhamBUS();
        HTTTBUS htttBUS = new HTTTBUS();

        // Lấy thông tin KH + NV
        
        String tenHTTT = "";
        for (var httt : htttBUS.getListHTTT()) {
            if (httt.getMaHTTT().equals(maHTTT)) {
                tenHTTT = httt.getTenHTTT();
                break;
            }
        }

        
        String tenKH = "";
        String tenNV = "";
        for (KhachHangDTO kh : khachHangBUS.getListKhachHang()) {
            if (kh.getMaKH().equals(maKH)) {
                tenKH = kh.getHo() + " " + kh.getTen();
                break;
            }
        }
        for (NhanVienDTO nv : nhanVienBUS.getListNhanVien()) {
            if (nv.getMaNV().equals(maNV)) {
                tenNV = nv.getHo() + " " + nv.getTen();
                break;
            }
        }

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết hoá đơn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: Khách hàng
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Khách hàng:"), gbc);
        gbc.gridx = 1;
        topPanel.add(new JLabel(maKH + " - " + tenKH), gbc);

        // Hàng 2: Nhân viên
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx = 1;
        topPanel.add(new JLabel(maNV + " - " + tenNV), gbc);

        // Hàng 3: Tổng tiền
        gbc.gridx = 0; gbc.gridy = 2;
        topPanel.add(new JLabel("Tổng tiền:"), gbc);
        gbc.gridx = 1;
        topPanel.add(new JLabel(String.format("%,.0f VND", tongTien)), gbc);

        // Hàng 4: Hình thức thanh toán
        gbc.gridx = 0; gbc.gridy = 3;
        topPanel.add(new JLabel("Hình thức thanh toán:"), gbc);
        gbc.gridx = 1;
        topPanel.add(new JLabel(maHTTT + " - " + tenHTTT), gbc);

        add(topPanel, BorderLayout.NORTH);
;

        // Bảng chi tiết sản phẩm
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

        // Nút In hóa đơn
        JPanel bottomPanel = new JPanel();
        JButton btnPrint = new JButton("In hóa đơn");
        btnPrint.addActionListener(e -> {
            try {
                HoaDonGUI.xuatPDF(maHD); // GỌI TRỰC TIẾP
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất PDF: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });




        bottomPanel.add(btnPrint);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
