package BUS;

import java.util.ArrayList;

import DAO.ChiTietSPDAO;
import DTO.ChiTietSPDTO;

public class ChiTietSPBUS {
    private static ArrayList<ChiTietSPDTO> dsCTSP;

    public ChiTietSPBUS() {
    }

    public static ArrayList<ChiTietSPDTO> getDsCTSP() {
        return dsCTSP;
    }

    public void docDSCTSP(String maSP) {
        ChiTietSPDAO dao = new ChiTietSPDAO();
        dsCTSP = dao.docDSCTSP(maSP);
    }

    public void add(ChiTietSPDTO ctsp) {
        ChiTietSPDAO dao = new ChiTietSPDAO();
        dao.add(ctsp);
        if (dsCTSP == null) {
            dsCTSP = new ArrayList<>();
        }
        dsCTSP.add(ctsp);
    }

    public void delete(String maSP, String mauSac, int kichThuoc) {
        ChiTietSPDAO dao = new ChiTietSPDAO();
        dao.delete(maSP, mauSac, kichThuoc);
        if (dsCTSP != null) {
            dsCTSP.removeIf(ctsp -> ctsp.getMaSP().equals(maSP) && ctsp.getMauSac().equals(mauSac) && ctsp.getKichThuoc() == kichThuoc);
        }
    }

    // Thêm phương thức mới để xóa toàn bộ chi tiết theo maSP
    public void deleteAllByMaSP(String maSP) {
        ChiTietSPDAO dao = new ChiTietSPDAO();
        dao.delete(maSP); // Gọi delete cũ trong DAO để xóa toàn bộ
        if (dsCTSP != null) {
            dsCTSP.removeIf(ctsp -> ctsp.getMaSP().equals(maSP));
        }
    }

    public void update(ChiTietSPDTO ctsp) {
        ChiTietSPDAO dao = new ChiTietSPDAO();
        dao.update(ctsp);
        if (dsCTSP != null) {
            for (int i = 0; i < dsCTSP.size(); i++) {
                ChiTietSPDTO existing = dsCTSP.get(i);
                if (existing.getMaSP().equals(ctsp.getMaSP()) && 
                    existing.getMauSac().equals(ctsp.getMauSac()) && 
                    existing.getKichThuoc() == ctsp.getKichThuoc()) {
                    dsCTSP.set(i, ctsp);
                    break;
                }
            }
        }
    }
}