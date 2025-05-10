package BUS;

import java.util.ArrayList;
import java.util.List;

import DAO.QuyenDAO;
import DTO.QuyenDTO;

public class QuyenBUS {
	private ArrayList<QuyenDTO> listQuyen;
	private QuyenDAO quyenDAO;
	
	public QuyenBUS() {
		quyenDAO = new QuyenDAO();
		listQuyen = quyenDAO.getListQuyen();
	}

	public ArrayList<QuyenDTO> getListQuyen() {
		return listQuyen;
	}

	public void setListQuyen(ArrayList<QuyenDTO> listQuyen) {
		this.listQuyen = listQuyen;
	}

	public QuyenDAO getQuyenDAO() {
		return quyenDAO;
	}

	public void setQuyenDAO(QuyenDAO quyenDAO) {
		this.quyenDAO = quyenDAO;
	}
	
	public void phanQuyenNV(int maTK, List<Integer> listMaQuyen) {
		quyenDAO.clearPhanQuyenNV(maTK);
		
		for(int x : listMaQuyen) {
			quyenDAO.phanQuyenNV(maTK, x);
		}
	}
	
	public boolean checkQuyen(int maTK, int maQuyen) {
		return quyenDAO.checkQuyen(maTK, maQuyen);
	}
	
	public void clearPhanQueyn(int maTK) {
		quyenDAO.clearPhanQuyenNV(maTK);
	}
}
