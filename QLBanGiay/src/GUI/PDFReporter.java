package GUI;

import BUS.SanPhamBUS;
import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.ChiTietHDDTO;
import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;
import DTO.SanPhamDTO;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import DAO.PhieuNhapDAO;
import DAO.ChiTietPNDAO;
import DAO.NhaCungCapDAO;
import DTO.PhieuNhapDTO;
import DTO.ChiTietPNDTO;
import DTO.NhaCungCapDTO;

public class PDFReporter {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    Document document = new Document();
    FileOutputStream file;
    JFrame jf = new JFrame();
    FileDialog fd = new FileDialog(jf, "Xuất pdf", FileDialog.SAVE);
    Font fontNormal10;
    Font fontBold15;
    Font fontBold25;
    Font fontBoldItalic15;

    SanPhamBUS sanPhamBus = new SanPhamBUS();

    public PDFReporter() {
        try {
            fontNormal10 = new Font(BaseFont.createFont("font/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.NORMAL);
            fontBold25 = new Font(BaseFont.createFont("font/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontBold15 = new Font(BaseFont.createFont("font/SVN-Times New Roman Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
            fontBoldItalic15 = new Font(BaseFont.createFont("font/SVN-Times New Roman Bold Italic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(PDFReporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy đường dẫn file " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Không gọi được document!");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontBold25));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private String getFile(String name) {
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
        fd.setFile(name);
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Chunk createWhiteSpace(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return new Chunk(builder.toString());
    }

    public void writePhieuNhap(String maPhieuNH) {
        String url = "";
        try {
            fd.setTitle("In phiếu nhập hàng");
            fd.setLocationRelativeTo(null);
            url = getFile(maPhieuNH);
            if (url == null || url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            // Thêm tiêu đề công ty và thời gian
            Paragraph company = new Paragraph("Cửa hàng bán giày LOOPY", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
            company.add(new Chunk("Thời gian in phiếu: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            document.add(Chunk.NEWLINE);

            // Thêm tiêu đề phiếu nhập
            Paragraph header = new Paragraph("THÔNG TIN PHIẾU NHẬP HÀNG", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // Lấy thông tin phiếu nhập
            PhieuNhapDAO phieuNhapHangDAO = new PhieuNhapDAO();
            ArrayList<PhieuNhapDTO> danhSachPhieuNhap = phieuNhapHangDAO.xuatDSPN();
            PhieuNhapDTO pn = null;
            for (PhieuNhapDTO phieuNhap : danhSachPhieuNhap) {
                if (phieuNhap.getMaPhieuNH().equals(maPhieuNH)) {
                    pn = phieuNhap;
                    break;
                }
            }
            if (pn == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập với mã " + maPhieuNH);
                document.close();
                writer.close();
                return;
            }

            // Thêm thông tin phiếu nhập
            Paragraph paragraph1 = new Paragraph("Mã phiếu nhập: PNH-" + pn.getMaPhieuNH(), fontNormal10);

            // Lấy thông tin nhà cung cấp
            NhaCungCapDAO nhaCCDAO = new NhaCungCapDAO();
            ArrayList<NhaCungCapDTO> danhSachNhaCC = nhaCCDAO.xuatDSNCC(); // Assuming this method exists
            NhaCungCapDTO nhaCC = null;
            for (NhaCungCapDTO ncc : danhSachNhaCC) {
                if (ncc.getMaNCC().equals(pn.getMaNCC())) {
                    nhaCC = ncc;
                    break;
                }
            }
            if (nhaCC == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung cấp với mã " + pn.getMaNCC());
                document.close();
                writer.close();
                return;
            }
            Paragraph paragraph2 = new Paragraph("Nhà cung cấp: " + nhaCC.getTenNCC(), fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            paragraph2.add(new Chunk("-"));
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            paragraph2.add(new Chunk(nhaCC.getDiaChi(), fontNormal10));

            // Lấy thông tin nhân viên
            NhanVienDAO nhanVienDAO = NhanVienDAO.getNhanVienDAO();
            ArrayList<NhanVienDTO> danhSachNhanVien = nhanVienDAO.getListNhanVien();
            NhanVienDTO nhanVien = null;
            for (NhanVienDTO nv : danhSachNhanVien) {
                if (nv.getMaNV().equals(pn.getMaNV())) {
                    nhanVien = nv;
                    break;
                }
            }
            if (nhanVien == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với mã " + pn.getMaNV());
                document.close();
                writer.close();
                return;
            }
            String tenNhanVien = nhanVien.getHo() + " " + nhanVien.getTen();
            Paragraph paragraph3 = new Paragraph("Người thực hiện: " + tenNhanVien, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("-"));
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("Mã nhân viên: " + pn.getMaNV(), fontNormal10));
            Paragraph paragraph4 = new Paragraph("Thời gian nhập: " + formatDate.format(pn.getNgayNhap()), fontNormal10);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(Chunk.NEWLINE);

            // Thêm bảng chi tiết phiếu nhập
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{30f, 35f, 20f, 15f, 20f});
            PdfPCell cell;

            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Thông tin", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Giá nhập", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold15)));

            // Thêm dòng trống
            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                table.addCell(cell);
            }

            // Thêm chi tiết sản phẩm
            ChiTietPNDAO ctpnDAO = new ChiTietPNDAO();
            ArrayList<ChiTietPNDTO> danhSachCTPNH = ctpnDAO.xuatDSCTPN(); // Assuming this method exists
            for (ChiTietPNDTO ctpn : danhSachCTPNH) {
                if (ctpn.getMaPhieuNH().equals(maPhieuNH)) {
                    ArrayList<SanPhamDTO> danhSachSanPham = sanPhamBus.getDssp();
                    SanPhamDTO sanPham = null;
                    for (SanPhamDTO sp : danhSachSanPham) {
                        if (sp.getMaSP().equals(ctpn.getMaSP())) {
                            sanPham = sp;
                            break;
                        }
                    }
                    if (sanPham != null) {
                        table.addCell(new PdfPCell(new Phrase(sanPham.getTenSP(), fontNormal10)));
//                        table.addCell(new PdfPCell(new Phrase(sanPham.getMauSac() + " - " + sanPham.getKichThuoc() + " - " + sanPham.getChatLieu() +" - " + sanPham.getKieuDang(), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(sanPham.getMauSac() + " - " + sanPham.getKichThuoc() + " - " + sanPham.getChatLieu(), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(formatter.format(ctpn.getDonGia()) + "đ", fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(formatter.format(ctpn.getThanhTien()) + "đ", fontNormal10)));
                    }
                }
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Thêm tổng thành tiền
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(pn.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Thêm phần chữ ký
            Paragraph paragraph = new Paragraph();
            paragraph.setIndentationLeft(22);
            paragraph.add(new Chunk("Người lập phiếu", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Nhân viên nhận", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Nhà cung cấp", fontBoldItalic15));

            Paragraph sign = new Paragraph();
            sign.setIndentationLeft(23);
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(30)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(28)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            document.add(paragraph);
            document.add(sign);

            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi không xác định: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void writeHoaDon(String maHD) {
        String url = "";
        try {
            fd.setTitle("In hóa đơn");
            fd.setLocationRelativeTo(null);
            url = getFile(maHD);
            if (url == null || url.equals("nullnull")) {
                return;
            }
            url = url + ".pdf";
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            // Thêm tiêu đề công ty và thời gian
            Paragraph company = new Paragraph("Cửa hàng bán giày LOOPY", fontBold15);
            company.add(new Chunk(createWhiteSpace(20)));
            Date today = new Date(System.currentTimeMillis());
            company.add(new Chunk("Thời gian in hóa đơn: " + formatDate.format(today), fontNormal10));
            company.setAlignment(Element.ALIGN_LEFT);
            document.add(company);
            document.add(Chunk.NEWLINE);

            // Thêm tiêu đề hóa đơn
            Paragraph header = new Paragraph("THÔNG TIN HÓA ĐƠN", fontBold25);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // Lấy thông tin hóa đơn
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            ArrayList<HoaDonDTO> danhSachHoaDon = hoaDonDAO.getListHoaDon();
            HoaDonDTO hd = null;
            for (HoaDonDTO hoaDon : danhSachHoaDon) {
                if (hoaDon.getMaHD().equals(maHD)) {
                    hd = hoaDon;
                    break;
                }
            }
            if (hd == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn với mã " + maHD);
                document.close();
                writer.close();
                return;
            }

            // Thêm thông tin hóa đơn
            Paragraph paragraph1 = new Paragraph("Mã hóa đơn: HD-" + hd.getMaHD(), fontNormal10);

            // Lấy thông tin khách hàng
            KhachHangDAO khachHangDAO = KhachHangDAO.getKhachHangDAO();
            ArrayList<KhachHangDTO> danhSachKhachHang = khachHangDAO.getListKhachHang();
            KhachHangDTO khachHang = null;
            for (KhachHangDTO kh : danhSachKhachHang) {
                if (kh.getMaKH().equals(hd.getMaKH())) {
                    khachHang = kh;
                    break;
                }
            }
            if (khachHang == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với mã " + hd.getMaKH());
                document.close();
                writer.close();
                return;
            }
            String tenKhachHang = khachHang.getHo() + " " + khachHang.getTen();
            Paragraph paragraph2 = new Paragraph("Khách hàng: " + tenKhachHang, fontNormal10);
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            paragraph2.add(new Chunk("-"));
            paragraph2.add(new Chunk(createWhiteSpace(5)));
            String diaChiKhachHang = khachHang.getDiaChi();
            paragraph2.add(new Chunk(diaChiKhachHang, fontNormal10));

            // Lấy thông tin nhân viên
            NhanVienDAO nhanVienDAO = NhanVienDAO.getNhanVienDAO();
            ArrayList<NhanVienDTO> danhSachNhanVien = nhanVienDAO.getListNhanVien();
            NhanVienDTO nhanVien = null;
            for (NhanVienDTO nv : danhSachNhanVien) {
                if (nv.getMaNV().equals(hd.getMaNV())) {
                    nhanVien = nv;
                    break;
                }
            }
            if (nhanVien == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với mã " + hd.getMaNV());
                document.close();
                writer.close();
                return;
            }
            String tenNhanVien = nhanVien.getHo() + " " + nhanVien.getTen();
            Paragraph paragraph3 = new Paragraph("Người lập hóa đơn: " + tenNhanVien, fontNormal10);
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("-"));
            paragraph3.add(new Chunk(createWhiteSpace(5)));
            paragraph3.add(new Chunk("Mã nhân viên: " + hd.getMaNV(), fontNormal10));
            Paragraph paragraph4 = new Paragraph("Thời gian lập: " + formatDate.format(hd.getNgayLap()), fontNormal10);

            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(Chunk.NEWLINE);

            // Thêm bảng chi tiết hóa đơn
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{30f, 35f, 20f, 15f, 20f});
            PdfPCell cell;

            table.addCell(new PdfPCell(new Phrase("Tên sản phẩm", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Thông tin", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Giá", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Số lượng", fontBold15)));
            table.addCell(new PdfPCell(new Phrase("Thành tiền", fontBold15)));

            // Thêm dòng trống
            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                table.addCell(cell);
            }

            // Thêm chi tiết sản phẩm
            ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
            ArrayList<ChiTietHDDTO> danhSachChiTietHoaDon = chiTietHoaDonDAO.getListCTHD();
            for (ChiTietHDDTO chiTietHoaDon : danhSachChiTietHoaDon) {
                if (chiTietHoaDon.getMaHD().equals(maHD)) {
                    ArrayList<SanPhamDTO> danhSachSanPham = sanPhamBus.getDssp();
                    SanPhamDTO sanPham = null;
                    for (SanPhamDTO sp : danhSachSanPham) {
                        if (sp.getMaSP().equals(chiTietHoaDon.getMaSP())) {
                            sanPham = sp;
                            break;
                        }
                    }
                    if (sanPham != null) {
                        table.addCell(new PdfPCell(new Phrase(sanPham.getTenSP(), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(sanPham.getMauSac() + " - " + sanPham.getKichThuoc() + " - " + sanPham.getChatLieu(), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(formatter.format(chiTietHoaDon.getDonGia()) + "đ", fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(String.valueOf(chiTietHoaDon.getSoLuong()), fontNormal10)));
                        table.addCell(new PdfPCell(new Phrase(formatter.format(chiTietHoaDon.getThanhTien()) + "đ", fontNormal10)));
                    }
                }
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // Thêm tổng thành tiền
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thành tiền: " + formatter.format(hd.getTongTien()) + "đ", fontBold15));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // Thêm phần chữ ký
            Paragraph paragraph = new Paragraph();
            paragraph.setIndentationLeft(22);
            paragraph.add(new Chunk("Người lập", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(30)));
            paragraph.add(new Chunk("Nhân viên bán hàng", fontBoldItalic15));
            paragraph.add(new Chunk(createWhiteSpace(25)));
            paragraph.add(new Chunk("Khách hàng", fontBoldItalic15));

            Paragraph sign = new Paragraph();
            sign.setIndentationLeft(20);
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(20)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            sign.add(new Chunk(createWhiteSpace(28)));
            sign.add(new Chunk("(Ký và ghi rõ họ tên)", fontNormal10));
            document.add(paragraph);
            document.add(sign);

            document.close();
            writer.close();
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi không xác định: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}