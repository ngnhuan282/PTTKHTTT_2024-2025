package BUS;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.PhieuXuatDAO;
import DTO.PhieuXuatDTO;

public class PhieuXuatBUS {
    private PhieuXuatDAO pxDAO = new PhieuXuatDAO();
    public static ArrayList<PhieuXuatDTO> listPX = new ArrayList<>();
    private PhieuXuatDTO px = new PhieuXuatDTO();

    public PhieuXuatBUS() {
    }

    public ArrayList<PhieuXuatDTO> getListPX() {
        docDSPhieuXuat();
        return listPX;
    }

    public void docDSPhieuXuat() {
        if (listPX == null)
            listPX = new ArrayList<PhieuXuatDTO>();
        listPX = pxDAO.xuatDSPhieuXuat();
    }

    public DefaultTableModel updateTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (PhieuXuatDTO px : listPX)
            model.addRow(new Object[] {px.getMaPX(), px.getNgayXuat(), px.getGhiChu()});
        return model;
    }

    public void addPhieuXuat(String maPX, String ngayXuat, String ghiChu) {
        PhieuXuatDTO px = new PhieuXuatDTO(maPX, ngayXuat, ghiChu);
        pxDAO.them(px);
        listPX.add(px);
    }

    public void editPhieuXuat(String newMaPX, String newNgayXuat, String newGhiChu, String maPX) {
        for (PhieuXuatDTO px : listPX) {
            if (px.getMaPX().equals(maPX)) {
                px.setMaPX(newMaPX);
                px.setNgayXuat(newNgayXuat);
                px.setGhiChu(newGhiChu);
                pxDAO.sua(px, maPX);
                return;
            }
        }
    }

    public void deletePhieuXuat(String maPX) {
        for (int i = 0; i < listPX.size(); i++) {
            if (listPX.get(i).getMaPX().equals(maPX)) {
                pxDAO.xoa(maPX);
                listPX.remove(i);
                return;
            }
        }
    }

    public ArrayList<PhieuXuatDTO> searchPhieuXuat(String tuKhoa, String tieuChi) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<PhieuXuatDTO>();
        for (PhieuXuatDTO px : listPX) {
            if (tieuChi.equals("Mã PX") && px.getMaPX().equalsIgnoreCase(tuKhoa))
                result.add(px);
            if (tieuChi.equals("Ngày Xuất") && px.getNgayXuat().equalsIgnoreCase(tuKhoa))
                result.add(px);
        }
        return result;
    }

    public boolean checkEdit(String newMaPX, String maPX) {
        for (PhieuXuatDTO px : listPX) {
            if (px.getMaPX().equals(newMaPX) && (!px.getMaPX().equals(maPX)))
                return true;
        }
        return false;
    }

    public boolean isDuplicatePX(String maPX) {
        for (PhieuXuatDTO px : listPX)
            if (px.getMaPX().equals(maPX))
                return true;
        return false;
    }
}