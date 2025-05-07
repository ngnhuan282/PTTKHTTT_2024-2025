package BUS;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
	private ArrayList<KhachHangDTO> listKhachHang;
	private KhachHangDAO khachHangDAO = new KhachHangDAO();
	
	public KhachHangBUS() throws SQLException {
		listKhachHang = new ArrayList<KhachHangDTO>();
		
		listKhachHang = khachHangDAO.getListKhachHang();
	}

	public ArrayList<KhachHangDTO> getListKhachHang() {
		return listKhachHang;
	}

	public void setListKhachHang(ArrayList<KhachHangDTO> listKhachHang) {
		this.listKhachHang = listKhachHang;
	}
	
	public boolean checkCustomerExist(String maKh) { // trả về true nếu makh đã tồn tại
		for(KhachHangDTO x : listKhachHang) {
			if(x.getMaKH().equals(maKh)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkRegexId(String maKh) {
		String regex = "KH\\d{2,6}$";
		if(maKh.matches(regex))
			return true;
		return false;
	}
	
	public boolean checkRegexSdt(String sdt) {
		String regex = "^(01|02|03|04|05|06|07|08|09)\\d{8,9}$";
		if(sdt.matches(regex))
			return true;
		return false;
	}
	
	public boolean checkRegexHo(String ho) {
		String regex = "^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*(?: [A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*$";
		if(ho.matches(regex)) {
			return true;
		}
		return false;
	}
	
	public boolean checkRegexTen(String ten) {
		String regex = "^[a-zA-zÀ-ỹ0-9]+$";
		if(ten.matches(regex)) {
			return true;
		}
		return false;
	}
	
	public boolean checkDulicatePhone(String sdt) {
		for(KhachHangDTO x : listKhachHang) {
			if(x.getSdt().equals(sdt))
				return true;
		}
		return false;
	}
	
	public String getMaKH() {
		int size = listKhachHang.size() + 1;
		String maKH = "KH00" + size;
		while(checkCustomerExist(maKH)) {
			size++;
			maKH = "KH00" + size;
		}
		return maKH;
	}
	
	public void addCustomer(String maKh, String ho, String ten, String diaChi, String sdt) {
		KhachHangDTO khachHang = new KhachHangDTO();
		khachHang.setMaKH(maKh);
		khachHang.setHo(ho);
		khachHang.setTen(ten);
		khachHang.setDiaChi(diaChi);
		khachHang.setSdt(sdt);
		listKhachHang.add(khachHang);
		khachHangDAO.addKhachHangDAO(khachHang);
	}
	
	public void updateCustomer(String ho, String ten, String diaChi, String sdt, int index) {
		KhachHangDTO khachHang = listKhachHang.get(index);
		khachHang.setHo(ho);
		khachHang.setTen(ten);
		khachHang.setDiaChi(diaChi);
		khachHang.setSdt(sdt);
		khachHangDAO.updateKhachHangDAO(khachHang);
	}
	
	public void deleteCustomer(int index) {
		KhachHangDTO khachHang = listKhachHang.get(index);
		listKhachHang.remove(index);
		khachHangDAO.deleteKhachHangDAO(khachHang);
	}
	
	public ArrayList<KhachHangDTO> searchCustomer(String key, String keyword) {
		ArrayList<KhachHangDTO> result = new ArrayList<KhachHangDTO>();
		
		for(KhachHangDTO x : listKhachHang) {
			if(key.equals("Mã khách hàng") && keyword.equals(x.getMaKH()))
				result.add(x);
			if(key.equals("Họ tên") && (x.getHo() + " " + x.getTen()).contains(keyword))
				result.add(x);
			if(key.equals("SĐT") && keyword.equals(x.getSdt()))
				result.add(x);
		}
		return result;
	}
	
	public int[] ImportExcel(File file) {
	    KhachHangDAO khDAO = new KhachHangDAO();
	    int[] result = khDAO.ImportExcel(file);
	    try {
	        listKhachHang = khDAO.getListKhachHang();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
	    }
	    return result;
	}
}	
