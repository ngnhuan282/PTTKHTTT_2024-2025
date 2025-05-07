package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ModernScrollBarUI extends BasicScrollBarUI {
    private final Dimension zeroDimension = new Dimension();

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(zeroDimension);
        button.setMinimumSize(zeroDimension);
        button.setMaximumSize(zeroDimension);
        button.setVisible(false); // Ẩn luôn
        return button;
    }

    @Override
    protected void configureScrollBarColors() {
        thumbColor = new Color(180, 180, 180); // Màu xám nhẹ
        trackColor = new Color(245, 245, 245); // Nền nhạt hơn
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(thumbColor);
        g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 8, 8); // Bo tròn nhẹ
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(trackColor);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
    }

    @Override
	public Dimension getPreferredSize(JComponent c) {
        // Kích thước mỏng như Chrome
        return (scrollbar.getOrientation() == JScrollBar.VERTICAL)
                ? new Dimension(10, 0)
                : new Dimension(0, 10);
    }
}
