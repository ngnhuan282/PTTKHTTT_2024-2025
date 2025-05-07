package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TrangChuGUI extends JPanel {
    public TrangChuGUI() {
       
        setBackground(new Color(245, 245, 245)); // Màu xám nhạt
        setLayout(null);
        setPreferredSize(new Dimension(1248, 757)); 

   
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBounds(0, 0, 1248, 250); 
        headerPanel.setLayout(null);
        add(headerPanel);

        // Logo cửa hàng 
        JLabel lbLogo = new JLabel(new ImageIcon(TrangChuGUI.class.getResource("/image/store96.png")));
        lbLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lbLogo.setBounds(584, 20, 80, 80); 
        headerPanel.add(lbLogo);

        // Tiêu đề cửa hàng
        JLabel lbStoreName = new JLabel("HỆ THỐNG QUẢN LÝ CỬA HÀNG BÁN GIÀY");
        lbStoreName.setFont(new Font("Arial", Font.BOLD, 36));
        lbStoreName.setForeground(Color.decode("#006666"));
        lbStoreName.setHorizontalAlignment(SwingConstants.CENTER);
        lbStoreName.setBounds(0, 110, 1248, 50);
        headerPanel.add(lbStoreName);

        // Slogan
        JLabel lbSlogan = new JLabel("- Hãy hướng về phía mặt trời, bóng tối sẽ ngả sau lưng bạn. –");
        lbSlogan.setFont(new Font("Arial", Font.ITALIC, 18));
        lbSlogan.setForeground(Color.decode("#006666"));
        lbSlogan.setHorizontalAlignment(SwingConstants.CENTER);
        lbSlogan.setBounds(0, 180, 1248, 30);
        headerPanel.add(lbSlogan);

    
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setBounds(32, 331, 350, 300); // x = 50
        panel1.setLayout(null);
        add(panel1);

        JLabel imgPanel1 = new JLabel(new ImageIcon(TrangChuGUI.class.getResource("/image/love96.png")));
        imgPanel1.setBounds(135, 20, 80, 80);
        panel1.add(imgPanel1);

        JLabel titlePanel1 = new JLabel("SẢN PHẨM CHẤT LƯỢNG");
        titlePanel1.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel1.setForeground(Color.decode("#006666"));
        titlePanel1.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel1.setBounds(0, 120, 350, 30);
        panel1.add(titlePanel1);

        JLabel descPanel1 = new JLabel("<html><center>Cam kết cung cấp giày chính hãng, chất lượng cao, được chọn lọc kỹ lưỡng từ các thương hiệu uy tín, đảm bảo sự bền bỉ và thoải mái cho khách hàng.</center></html>");
        descPanel1.setFont(new Font("Arial", Font.PLAIN, 14));
        descPanel1.setForeground(Color.DARK_GRAY);
        descPanel1.setHorizontalAlignment(SwingConstants.CENTER);
        descPanel1.setBounds(20, 160, 310, 100);
        panel1.add(descPanel1);


        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setBounds(430, 331, 350, 300); 
        panel2.setLayout(null);
        add(panel2);

        JLabel imgPanel2 = new JLabel(new ImageIcon(TrangChuGUI.class.getResource("/image/shield96.png")));
        imgPanel2.setBounds(135, 20, 80, 80);
        panel2.add(imgPanel2);

        JLabel titlePanel2 = new JLabel("QUAN TÂM KHÁCH HÀNG");
        titlePanel2.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel2.setForeground(Color.decode("#006666"));
        titlePanel2.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel2.setBounds(0, 120, 350, 30);
        panel2.add(titlePanel2);

        JLabel descPanel2 = new JLabel("<html><center>Luôn lắng nghe ý kiến khách hàng, hỗ trợ tận tâm, đổi trả dễ dàng, mang đến trải nghiệm mua sắm tuyệt vời và sự hài lòng tối đa.</center></html>");
        descPanel2.setFont(new Font("Arial", Font.PLAIN, 14));
        descPanel2.setForeground(Color.DARK_GRAY);
        descPanel2.setHorizontalAlignment(SwingConstants.CENTER);
        descPanel2.setBounds(20, 160, 310, 100);
        panel2.add(descPanel2);


        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        panel3.setBounds(827, 331, 350, 300); 
        panel3.setLayout(null);
        add(panel3);

        JLabel imgPanel3 = new JLabel(new ImageIcon(TrangChuGUI.class.getResource("/image/stock96.png")));
        imgPanel3.setBounds(135, 20, 80, 80);
        panel3.add(imgPanel3);

        JLabel titlePanel3 = new JLabel("QUẢN LÝ HIỆU QUẢ");
        titlePanel3.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel3.setForeground(Color.decode("#006666"));
        titlePanel3.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel3.setBounds(0, 120, 350, 30);
        panel3.add(titlePanel3);

        JLabel descPanel3 = new JLabel("<html><center>Quản lý danh sách sản phẩm hiệu quả, tính toán tự động, giúp tiết kiệm thời gian, giảm sai sót và tối ưu hóa quy trình vận hành cửa hàng.</center></html>");
        descPanel3.setFont(new Font("Arial", Font.PLAIN, 14));
        descPanel3.setForeground(Color.DARK_GRAY);
        descPanel3.setHorizontalAlignment(SwingConstants.CENTER);
        descPanel3.setBounds(20, 160, 310, 100);
        panel3.add(descPanel3);
    }
}