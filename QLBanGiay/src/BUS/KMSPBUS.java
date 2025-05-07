package BUS;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import DAO.KMSPDAO;
import DTO.KMSPDTO;

public class KMSPBUS {
	private ArrayList<KMSPDTO> listKMSP;
	private KMSPDAO khuyenMaiSPDAO;
	
	public KMSPBUS() throws SQLException {
		LocalDate now = LocalDate.now();
		Date ngayLap = Date.valueOf(now);
		khuyenMaiSPDAO = new KMSPDAO();
		listKMSP = khuyenMaiSPDAO.getListKMSP(ngayLap);
	}

	public ArrayList<KMSPDTO> getListKMSP() {
		return listKMSP;
	}

	public void setListKMSP(ArrayList<KMSPDTO> listKMSP) {
		this.listKMSP = listKMSP;
	}
	
	public ArrayList<KMSPDTO> getListKMSP(Date ngayLap) throws SQLException{
		return khuyenMaiSPDAO.getListKMSP(ngayLap);
	}
	
	public boolean checkMaSPKM(String maSP) {
		for(KMSPDTO x : listKMSP) {
			if(maSP.equals(x.getMaSP()))
				return true;
		}
		return false;
	}
	
	public float getPhanTram(String maSP) {
		for(KMSPDTO x : listKMSP) {
			if(maSP.equals(x.getMaSP()))
				return x.getPhanTramGiamGia();
		}
		return 0;
	}
	
	public String tenCTKM(String maSP) {
		for(KMSPDTO x : listKMSP) {
			if(maSP.equals(x.getMaSP()))
				return x.getTenCTKM();
		}
		return null;
	}
	
	public static void main(String[] args) throws SQLException {
		LocalDate now = LocalDate.now();
		Date ngayLap = Date.valueOf(now);
		
		KMSPBUS kmspbus = new KMSPBUS();
		ArrayList<KMSPDTO> list = kmspbus.getListKMSP(ngayLap);
		
		String maSp = "SP001";
		
		
	}
}
