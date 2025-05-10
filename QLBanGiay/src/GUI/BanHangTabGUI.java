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
        HoaDonGUI  danhSachHDPanel = new HoaDonGUI(); // Chỉ còn chức năng xem/tìm hóa đơn
        tabbedPane.addTab("Hóa đơn", danhSachHDPanel);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
            if (selectedTitle.equals("Hóa đơn")) {
                ((HoaDonGUI) danhSachHDPanel).refreshData(); // Gọi làm mới
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
    }
}
