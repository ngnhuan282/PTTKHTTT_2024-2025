package BUS;

import java.sql.Date;
import java.util.ArrayList;

import DAO.PhieuXuatDAO;
import DTO.PhieuXuatDTO;

public class PhieuXuatBUS {
    private PhieuXuatDAO pxDAO = new PhieuXuatDAO();
    public static ArrayList<PhieuXuatDTO> listPX = new ArrayList<>();
    private static int lastMaPX = 0;
    public ArrayList<PhieuXuatDTO> getListPX() {
        docDSPX();
        return listPX;
    }

    public void docDSPX() {
        if (listPX == null)
            listPX = new ArrayList<PhieuXuatDTO>();
        listPX = pxDAO.xuatDSPX();
    }

 // Tạo mã phiếu xuất mới
    public String generateMaPX() {
        int newMaPX = 1;
        while (true) {
            String maPXStr = String.valueOf(newMaPX);
            if (!checkMaPX(maPXStr)) {
                return maPXStr;
            }
            newMaPX++;
        }
    }

    public void addPX(String maPX, String maNV, double tongTien, Date ngayXuat, String ghiChu) {
        PhieuXuatDTO px = new PhieuXuatDTO(maPX, maNV, tongTien, ngayXuat, ghiChu);
        listPX.add(px);
        pxDAO.them(px);
    }

    public void updatePX(String maPX, String maNV, double tongTien, Date ngayXuat, String ghiChu) {
        for (PhieuXuatDTO px : listPX) {
            if (px.getMaPX().equals(maPX)) {
                px.setMaNV(maNV);
                px.setTongTien(tongTien);
                px.setNgayXuat(ngayXuat);
                px.setGhiChu(ghiChu);
                pxDAO.sua(px, maPX);
                break;
            }
        }
    }

    public void deletePX(String maPX) {
        for (int i = 0; i < listPX.size(); i++) {
            if (listPX.get(i).getMaPX().equals(maPX)) {
                pxDAO.xoa(maPX);
                listPX.remove(i);
                return;
            }
        }
    }

    public ArrayList<PhieuXuatDTO> search(String tuKhoa, String tieuChi) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        for (PhieuXuatDTO px : listPX) {
            if (tieuChi.equals("Mã PX") && px.getMaPX().equalsIgnoreCase(tuKhoa))
                result.add(px);
            if (tieuChi.equals("Mã NV") && px.getMaNV().equalsIgnoreCase(tuKhoa))
                result.add(px);
        }
        return result;
    }

    public ArrayList<PhieuXuatDTO> searchByDate(Date startDate, Date endDate) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        for (PhieuXuatDTO px : listPX) {
            Date ngayXuat = px.getNgayXuat();
            if (ngayXuat != null && !ngayXuat.before(startDate) && !ngayXuat.after(endDate)) {
                result.add(px);
            }
        }
        return result;
    }

    public boolean checkMaPX(String maPX) {
        for (PhieuXuatDTO px : listPX) {
            if (px.getMaPX().equals(maPX))
                return true;
        }
        return false;
    }
}