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

    // ✅ Lớp vẽ biểu đồ cho Tab "Thống kê từ ngày đến ngày" (Đã chỉnh style giống biểu đồ tháng)
    public static class CustomChartPanelDateRange extends JPanel {
        private double[] revenue;
        private String[] days;

        public CustomChartPanelDateRange(double[] revenue, String[] days) {
            this.revenue = revenue;
            this.days = days;
            setBackground(Color.WHITE);
            setBounds(10, 10, 1128, 250); // nhỏ gọn giống bên tháng
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (revenue == null || revenue.length == 0) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 50;
            int barWidth = Math.max(20, (width - 2 * padding) / (days.length * 2));
            int maxBarHeight = height - 80;

            // Tính max giá trị doanh thu
            double max = 0;
            for (double val : revenue) {
                if (val > max) max = val;
            }
            if (max == 0) max = 1; // tránh chia 0

            // Vẽ trục Y và lưới
            g2.setColor(new Color(211, 211, 211));
            DecimalFormat df = new DecimalFormat("#,###,###");
            for (int i = 0; i <= 5; i++) {
                int y = height - padding - (int)(i / 5.0 * maxBarHeight);
                g2.drawLine(padding, y, width - padding, y);
                g2.setColor(Color.BLACK);
                g2.drawString(df.format((int)(i * max / 5)) + "đ", 5, y + 5);
                g2.setColor(new Color(211, 211, 211));
            }

            // Vẽ trục X và nhãn ngày
            g2.setColor(Color.BLACK);
            g2.drawLine(padding, height - padding, width - padding, height - padding);
            for (int i = 0; i < days.length; i++) {
                int x = padding + i * 2 * barWidth + barWidth / 2;
                g2.drawString(days[i], x - barWidth / 2, height - padding + 15);
            }

            // Vẽ cột doanh thu
            for (int i = 0; i < revenue.length; i++) {
                if (revenue[i] <= 0) continue;
                int barHeight = (int)((revenue[i] / max) * maxBarHeight);
                int x = padding + i * 2 * barWidth;
                int y = height - padding - barHeight;

                GradientPaint gradient = new GradientPaint(x, y, new Color(33, 150, 243),
                        x, height - padding, new Color(129, 212, 250));
                g2.setPaint(gradient);
                g2.fill(new RoundRectangle2D.Float(x, y, barWidth, barHeight, 10, 10));

                // Vẽ nhãn doanh thu
                g2.setColor(Color.BLACK);
                String label = df.format((int)revenue[i]) + "đ";
                drawCenteredString(g2, label, x + barWidth / 2, y - 10, barWidth);
            }

            // Vẽ chú thích (legend)
            g2.setColor(new Color(33, 150, 243));
            g2.fillRect(30, 10, 20, 10);
            g2.setColor(Color.BLACK);
            g2.drawString("Doanh thu", 60, 18);
        }

        private void drawCenteredString(Graphics2D g2, String text, int x, int y, int width) {
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x - textWidth / 2, y);
        }
    }

    // Giữ nguyên biểu đồ tháng (không đổi)
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

            // Trục Y
            g2.setColor(new Color(211, 211, 211));
            for (int i = 0; i <= 5; i++) {
                int y = height - 30 - (int)((i * maxValue / 5.0) / maxValue * (height - 60));
                g2.drawLine(30, y, width - 30, y);
                g2.setColor(Color.BLACK);
                g2.drawString(df.format(i * maxValue / 5) + "đ", 5, y + 5);
                g2.setColor(new Color(211, 211, 211));
            }

            g2.setColor(Color.BLACK);
            g2.drawLine(30, height - 30, width - 30, height - 30);
            for (int i = 0; i < months.length; i++) {
                int x = 30 + i * barWidth * 3 + barWidth;
                g2.drawString(months[i], x - barWidth / 2, height - 10);
            }

            for (int i = 0; i < months.length; i++) {
                int baseX = 30 + i * barWidth * 3;

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
