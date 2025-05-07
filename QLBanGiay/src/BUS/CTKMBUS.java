package BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date; 

import DAO.CTKMDAO;
import DTO.CTKMDTO;

public class CTKMBUS {
	private ArrayList<CTKMDTO> listKhuyenMai;
	private CTKMDAO khuyenMaiDAO = new CTKMDAO();
	
	public CTKMBUS() throws SQLException {
		listKhuyenMai = new ArrayList<CTKMDTO>();
		
		listKhuyenMai = khuyenMaiDAO.getListkhuyenMai();
	}

	public ArrayList<CTKMDTO> getListKhuyenMai() {
		return listKhuyenMai;
	}

	public void setListKhuyenMai(ArrayList<CTKMDTO> listKhuyenMai) {
		this.listKhuyenMai = listKhuyenMai;
	}
	
	public boolean checkMaCTKMexist(String maCTKM) {
		for(CTKMDTO x : listKhuyenMai) {
			if(x.getMaCTKM().equalsIgnoreCase(maCTKM)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isNgayKTAfterNgayBD(java.util.Date ngayBD, java.util.Date ngayKT) {
	    if (ngayBD == null || ngayKT == null) {
	        return false;
	    }
	    return ngayKT.after(ngayBD);
	}

	
	
	
	public boolean themKhuyenMai(String maCTKM, Date ngayBD, Date ngayKT, String tenCTKM, String loaiKM, String maSPorHD, float phanTram) {
	    try {
	        CTKMDTO khuyenMai = new CTKMDTO(maCTKM, new java.sql.Date(ngayBD.getTime()), new java.sql.Date(ngayKT.getTime()), tenCTKM, phanTram);
	        khuyenMaiDAO.addkhuyenMaiDAO(khuyenMai);

	        if (loaiKM.equals("Sản Phẩm")) {
	        	khuyenMaiDAO.addCTKM_SP(maCTKM, maSPorHD, phanTram);
	        } else {
	        	khuyenMaiDAO.addCTKM_HD(maCTKM, maSPorHD, phanTram);
	        }

	        listKhuyenMai.add(khuyenMai); // cập nhật list trong BUS
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void updateCTKM(CTKMDTO ctkm, String loaiCTKMCu, String loaiCTKM, String maSPorHD, double phanTramValue,String maSPorHDCu, int index) {
	    // Cập nhật thông tin của chương trình khuyến mãi trong danh sách
		listKhuyenMai.set(index, ctkm);

	    // Gọi DAO để cập nhật vào cơ sở dữ liệu
		khuyenMaiDAO.updateCTKMDAO(ctkm,loaiCTKMCu,loaiCTKM,maSPorHD,maSPorHDCu,phanTramValue);
	}




	
	public void deleteKhuyenMai(int index) {
		CTKMDTO khuyenMai = listKhuyenMai.get(index);
		listKhuyenMai.remove(index);
		khuyenMaiDAO.deletekhuyenMaiDAO(khuyenMai);
	}
	
	public ArrayList<CTKMDTO> searchKhuyenMai(String key, String keyword) {
		ArrayList<CTKMDTO> result = new ArrayList<CTKMDTO>();
		
		for(CTKMDTO x : listKhuyenMai) {
			if(key.equals("Mã chương trình khuyến mãi") && keyword.equals(x.getMaCTKM()))
				result.add(x);
		}
		return result;
	}

	
	 // Thêm phương thức để lấy danh sách MaSP
    public ArrayList<String> getListMaSP() throws SQLException {
        if (khuyenMaiDAO == null) {
            throw new IllegalStateException("CTKMDAO chưa được khởi tạo!");
        }
        return khuyenMaiDAO.getListMaSP();
    }
    
    // Thêm phương thức để lấy danh sách MaHD
    public ArrayList<String> getListMaHD() throws SQLException {
        if (khuyenMaiDAO == null) {
            throw new IllegalStateException("CTKMDAO chưa được khởi tạo!");
        }
        return khuyenMaiDAO.getListMaHD();
    }
    
    public CTKMDTO getCTKM_HD(Date ngayLap) throws SQLException {
    	return khuyenMaiDAO.getCTKM_HD(ngayLap);
    }
    
}	
