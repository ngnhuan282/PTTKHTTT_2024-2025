package BUS;

import java.util.ArrayList;

import DAO.ChucVuDAO;
import DTO.ChucVuDTO;

public class ChucVuBUS {
	private ChucVuDAO chucVuDAO;
	private ArrayList<ChucVuDTO> listChucVu;
	
	public ChucVuBUS() {
		chucVuDAO = new ChucVuDAO();
		listChucVu = chucVuDAO.getListChucVuDTO();
	}

	public ChucVuDAO getChucVuDAO() {
		return chucVuDAO;
	}

	public void setChucVuDAO(ChucVuDAO chucVuDAO) {
		this.chucVuDAO = chucVuDAO;
	}

	public ArrayList<ChucVuDTO> getListChucVu() {
		return listChucVu;
	}

	public void setListChucVu(ArrayList<ChucVuDTO> listChucVu) {
		this.listChucVu = listChucVu;
	}
	
	public int getMaChucVu(String tenChucVu) {
		return chucVuDAO.getMaChucVu(tenChucVu);
	}
	
	public ChucVuDTO getChucVu(String maNV) {
		return chucVuDAO.getChucVu(maNV);
	}
	
	public int getCurrentMaChucVu(int maTK) {
		return chucVuDAO.getCurrentMaChucVu(maTK);
	}
}
