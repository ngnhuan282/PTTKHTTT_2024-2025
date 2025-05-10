package GUI;

import javax.swing.*;
import java.awt.*;

public class BanHangTabGUI extends JPanel {
    public BanHangTabGUI() throws Exception {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Bán hàng
        JPanel banHangPanel = new BanHangGUI(); // Giao diện chọn sản phẩm, giỏ hàng
        tabbedPane.addTab("Bán hàng", banHangPanel);

        // Tab 2: Hóa đơn
        JPanel danhSachHDPanel = new HoaDonGUI(); // Chỉ còn chức năng xem/tìm hóa đơn
        tabbedPane.addTab("Hóa đơn", danhSachHDPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }
}
