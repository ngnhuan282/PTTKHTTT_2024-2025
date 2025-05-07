package GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

public class Chart {
    // Lớp vẽ biểu đồ cho Tab "Thống kê từ ngày đến ngày"
    public static class CustomChartPanelDateRange extends JPanel {
        private double[] revenue;
        private String[] days;

        public CustomChartPanelDateRange(double[] revenue, String[] days) {
            this.revenue = revenue;
            this.days = days;
            setBackground(Color.WHITE);
            setBounds(10, 10, 1128, 280);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int barWidth = (width - 40) / days.length;
            int maxValue = 3500000; // 3.5M VNĐ
            DecimalFormat df = new DecimalFormat("#,###,###");

            // Vẽ lưới trục Y
            g2.setColor(new Color(211, 211, 211));
            for (int i = 0; i <= 5; i++) {
                int y = height - 30 - (int)((i * maxValue / 5.0) / maxValue * (height - 60));
                g2.drawLine(30, y, width - 30, y);
                g2.setColor(Color.BLACK);
                g2.drawString(df.format(i * maxValue / 5) + "đ", 5, y + 5);
                g2.setColor(new Color(211, 211, 211));
            }

            // Vẽ trục X và nhãn ngày
            g2.setColor(Color.BLACK);
            g2.drawLine(30, height - 30, width - 30, height - 30);
            for (int i = 0; i < days.length; i++) {
                int x = 30 + i * barWidth + barWidth / 2;
                g2.drawString(days[i], x - barWidth / 2, height - 10);
            }

            // Vẽ cột và nhãn
            for (int i = 0; i < days.length; i++) {
                int baseX = 30 + i * barWidth;
                if (revenue[i] > 0) {
                    int barHeight = (int)((revenue[i] / maxValue) * (height - 60));
                    int y = height - 30 - barHeight;
                    GradientPaint gradient = new GradientPaint(baseX, y, new Color(33, 150, 243),
                            baseX, height - 30, new Color(129, 212, 250));
                    g2.setPaint(gradient);
                    g2.fill(new RoundRectangle2D.Float(baseX + barWidth / 4, y, barWidth / 2, barHeight, 10, 10));
                    g2.setColor(Color.BLACK);
                    drawCenteredString(g2, df.format((int)revenue[i]) + "đ", baseX + barWidth / 2, y - 10, barWidth / 2);
                }
            }
        }

        private void drawCenteredString(Graphics2D g2, String text, int x, int y, int width) {
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x - textWidth / 2, y);
        }
    }

    // Lớp vẽ biểu đồ cho Tab "Thống kê theo tháng trong năm"
    public static class CustomChartPanelMonthly extends JPanel {
        private double[] capital;
        private double[] revenue;
        private double[] profit;
        private String[] months;

        public CustomChartPanelMonthly(double[] capital, double[] revenue, double[] profit, String[] months) {
            this.capital = capital;
            this.revenue = revenue;
            this.profit = profit;
            this.months = months;
            setBackground(Color.WHITE);
            setBounds(10, 10, 1128, 280);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int barWidth = (width - 40) / (months.length * 3 + 1);
            int maxValue = 500000000; // 500M VNĐ
            DecimalFormat df = new DecimalFormat("#,###,###,###");

            // Vẽ lưới trục Y
            g2.setColor(new Color(211, 211, 211));
            for (int i = 0; i <= 5; i++) {
                int y = height - 30 - (int)((i * maxValue / 5.0) / maxValue * (height - 60));
                g2.drawLine(30, y, width - 30, y);
                g2.setColor(Color.BLACK);
                g2.drawString(df.format(i * maxValue / 5) + "đ", 5, y + 5);
                g2.setColor(new Color(211, 211, 211));
            }

            // Vẽ trục X và nhãn tháng
            g2.setColor(Color.BLACK);
            g2.drawLine(30, height - 30, width - 30, height - 30);
            for (int i = 0; i < months.length; i++) {
                int x = 30 + i * barWidth * 3 + barWidth;
                g2.drawString(months[i], x - barWidth / 2, height - 10);
            }

            // Vẽ cột và nhãn
            for (int i = 0; i < months.length; i++) {
                int baseX = 30 + i * barWidth * 3;

                // Vốn (cam)
                if (capital[i] > 0) {
                    int barHeight = (int)((capital[i] / maxValue) * (height - 60));
                    int y = height - 30 - barHeight;
                    GradientPaint gradient = new GradientPaint(baseX, y, new Color(255, 152, 0),
                            baseX, height - 30, new Color(255, 204, 128));
                    g2.setPaint(gradient);
                    g2.fill(new RoundRectangle2D.Float(baseX, y, barWidth - 5, barHeight, 10, 10));
                    g2.setColor(Color.BLACK);
                    drawCenteredString(g2, df.format((int)capital[i]) + "đ", baseX + (barWidth - 5) / 2, y - 10, barWidth - 5);
                }

                // Doanh thu (xanh)
                if (revenue[i] > 0) {
                    int barHeight = (int)((revenue[i] / maxValue) * (height - 60));
                    int y = height - 30 - barHeight;
                    GradientPaint gradient = new GradientPaint(baseX + barWidth, y, new Color(33, 150, 243),
                            baseX + barWidth, height - 30, new Color(129, 212, 250));
                    g2.setPaint(gradient);
                    g2.fill(new RoundRectangle2D.Float(baseX + barWidth, y, barWidth - 5, barHeight, 10, 10));
                    g2.setColor(Color.BLACK);
                    drawCenteredString(g2, df.format((int)revenue[i]) + "đ", baseX + barWidth + (barWidth - 5) / 2, y - 10, barWidth - 5);
                }

                // Lợi nhuận (tím)
                if (profit[i] > 0) {
                    int barHeight = (int)((profit[i] / maxValue) * (height - 60));
                    int y = height - 30 - barHeight;
                    GradientPaint gradient = new GradientPaint(baseX + 2 * barWidth, y, new Color(128, 0, 128),
                            baseX + 2 * barWidth, height - 30, new Color(191, 128, 191));
                    g2.setPaint(gradient);
                    g2.fill(new RoundRectangle2D.Float(baseX + 2 * barWidth, y, barWidth - 5, barHeight, 10, 10));
                    g2.setColor(Color.BLACK);
                    drawCenteredString(g2, df.format((int)profit[i]) + "đ", baseX + 2 * barWidth + (barWidth - 5) / 2, y - 10, barWidth - 5);
                }
            }

            // Chú thích (legend)
            g2.setColor(new Color(255, 152, 0));
            g2.fillRect(30, 10, 20, 10);
            g2.setColor(Color.BLACK);
            g2.drawString("Vốn", 60, 18);

            g2.setColor(new Color(33, 150, 243));
            g2.fillRect(120, 10, 20, 10);
            g2.setColor(Color.BLACK);
            g2.drawString("Doanh thu", 150, 18);

            g2.setColor(new Color(128, 0, 128));
            g2.fillRect(230, 10, 20, 10);
            g2.setColor(Color.BLACK);
            g2.drawString("Lợi nhuận", 260, 18);
        }

        private void drawCenteredString(Graphics2D g2, String text, int x, int y, int width) {
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x - textWidth / 2, y);
        }
    }
}