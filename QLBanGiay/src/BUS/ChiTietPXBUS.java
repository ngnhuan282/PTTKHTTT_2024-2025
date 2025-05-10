package BUS;

import java.util.ArrayList;

import DAO.ChiTietPXDAO;
import DTO.ChiTietPXDTO;

public class ChiTietPXBUS {
    private ChiTietPXDAO ctpxDAO = new ChiTietPXDAO();
    public static ArrayList<ChiTietPXDTO> listCTPX = new ArrayList<>();

    public ArrayList<ChiTietPXDTO> getListCTPX() {
        docDSCTPX();
        return listCTPX;
    }

    public void docDSCTPX() {
        if (listCTPX == null)
            listCTPX = new ArrayList<ChiTietPXDTO>();
        listCTPX = ctpxDAO.xuatDSCTPX();
    }

    public ArrayList<ChiTietPXDTO> getChiTietTheoMaPhieu(String maPX) {
        ArrayList<ChiTietPXDTO> result = new ArrayList<>();
        for (ChiTietPXDTO ctpx : listCTPX) {
            if (ctpx.getMaPX().equals(maPX))
                result.add(ctpx);
        }
        return result;
    }

    public void addCTPX(String maPX, String maSP, int soLuong, double donGia, double thanhTien) {
        ChiTietPXDTO ctpx = new ChiTietPXDTO(maPX, maSP, soLuong, donGia, thanhTien);
        listCTPX.add(ctpx);
        ctpxDAO.them(ctpx);
    }

    public void updateCTPX(String maPX, ArrayList<ChiTietPXDTO> newListCTPX) {
        ctpxDAO.xoa(maPX);
        for (ChiTietPXDTO ctpx : newListCTPX)
            ctpxDAO.them(ctpx);
        docDSCTPX();
    }

    public void deleteCTPX(String maPX) {
        for (int i = 0; i < listCTPX.size(); i++) {
            if (listCTPX.get(i).getMaPX().equals(maPX)) {
                ctpxDAO.xoa(maPX);
                listCTPX.remove(i);
                return;
            }
        }
    }
}