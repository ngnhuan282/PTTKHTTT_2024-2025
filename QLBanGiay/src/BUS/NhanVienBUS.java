package BUS;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import DTO.KhachHangDTO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
	private ArrayList<NhanVienDTO> listNhanVien;
	private NhanVienDAO nhanVienDAO = new NhanVienDAO();
	
	public NhanVienBUS() throws SQLException {
		listNhanVien = new ArrayList<NhanVienDTO>();
		listNhanVien = nhanVienDAO.getListNhanVien();
	}

	public ArrayList<NhanVienDTO> getListNhanVien() {
		return listNhanVien;
	}

	public void setListNhanVien(ArrayList<NhanVienDTO> listNhanVien) {
		this.listNhanVien = listNhanVien;
	}
	
	public boolean checkmanvStaff(String maNV) { 
		for(NhanVienDTO x : listNhanVien) {
			if(x.getMaNV() == maNV) {
				return true;
			}
		}
		return false;
	}
	
	public void addStaff(String maNV, String ho, String ten, String sdt,double luong) {
		NhanVienDTO nhanVien = new NhanVienDTO();
		nhanVien.setMaNV(maNV);
		nhanVien.setHo(ho);
		nhanVien.setTen(ten);
		nhanVien.setSdt(sdt);
		nhanVien.setLuong(luong);
		listNhanVien.add(nhanVien);
		nhanVienDAO.addNhanVienDAO(nhanVien);
	}
	
	public void fixStaff(String ho, String ten, String sdt,double luong,int index) {
		NhanVienDTO nv = listNhanVien.get(index);
		nv.setHo(ho);
		nv.setTen(ten);
		nv.setSdt(sdt);
		nv.setLuong(luong);
		nhanVienDAO.updateNhanVienDAO(nv);
	}
	public void deleteStaff(int index) {
		NhanVienDTO nv = listNhanVien.get(index);
		listNhanVien.remove(index);
		nhanVienDAO.deleteNhanVienDAO(nv);
	}

	public int[] ImportExcel(File file) {
	    NhanVienDAO nvDAO = new NhanVienDAO();
	    int[] result = nvDAO.ImportExcel(file);
	    // Cập nhật danh sách nhân viên sau khi nhập
	    try {
			listNhanVien = nvDAO.getListNhanVien();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return result;
	}
}
