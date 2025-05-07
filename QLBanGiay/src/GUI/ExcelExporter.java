package GUI;

import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    /**
     * Xuất dữ liệu từ JTable sang file Excel (.xlsx) với đường dẫn do người dùng chọn.
     *
     * @param table JTable chứa dữ liệu cần xuất
     * @throws IOException Nếu có lỗi khi ghi file
     */
    public static void exportJTableToExcel(JTable table) throws IOException {
        JFrame jf = new JFrame();
        FileDialog fd = new FileDialog(jf, "Chọn đường dẫn lưu file Excel", FileDialog.SAVE);
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile("export.xlsx"); // Tên tệp mặc định
        fd.setVisible(true);

        String filePath = fd.getDirectory() + fd.getFile();
        if (filePath.equals("nullnull")) {
            return;
        }
        if (!filePath.toLowerCase().endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        TableModel model = table.getModel();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            // Tạo các hàng dữ liệu
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = model.getValueAt(i, j);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            // Tự động điều chỉnh kích thước cột
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                JOptionPane.showMessageDialog(null, "Xuất file Excel thành công tại " + filePath,
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
}