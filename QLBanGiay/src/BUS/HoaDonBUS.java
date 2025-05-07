package BUS;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;

public class HoaDonBUS {
	private ArrayList<HoaDonDTO> listHoaDon;
	private HoaDonDAO hoaDonDAO = new HoaDonDAO();
	
	public HoaDonBUS() throws SQLException {
		listHoaDon = new ArrayList<HoaDonDTO>();
		listHoaDon = hoaDonDAO.getListHoaDon();
	}

	public ArrayList<HoaDonDTO> getListHoaDon() {
		return listHoaDon;
	}

	public void setListHoaDon(ArrayList<HoaDonDTO> listHoaDon) {
		this.listHoaDon = listHoaDon;
	}
	
	public String getMaHD() {
		int size = listHoaDon.size() + 1;
		while(checkDuplicateMaHD(size+""))
			size++;
		return size + "";
	}
	
	public boolean checkDuplicateMaHD(String maHD) {
		for(HoaDonDTO x : listHoaDon) {
			if(x.getMaHD().equals(maHD))
				return true;
		}
		return false;
	}
	
	public void addHoaDon(String maHD, String maKH, String maNV, Date ngayLap, double tongTien) {
		HoaDonDTO hoaDon = new HoaDonDTO(maHD, maKH, maNV, ngayLap, tongTien);
		listHoaDon.add(hoaDon);
		hoaDonDAO.addHoaDon(hoaDon);
	}
	
	public void updateHoaDon(String maHD, String maKH, String maNV, Date ngayLap, double tongTien, int index) {
		HoaDonDTO hoaDon = listHoaDon.get(index);
		hoaDon.setMaHD(maHD);
		hoaDon.setMaKH(maKH);
		hoaDon.setMaNV(maNV);
		hoaDon.setNgayLap(ngayLap);
		hoaDon.setTongTien(tongTien);
		hoaDonDAO.updateHoaDon(hoaDon);
	}
	
	
	public int getIndex(String maHD) {
		int i = 0;
		for(HoaDonDTO x : listHoaDon) {
			if(x.getMaHD().equals(maHD))
				return i;
			i++;
		}
		return -1;
	}
	
	public void updateTongTien(String maHD, double tongTien) {
		HoaDonDTO hoaDon = listHoaDon.get(getIndex(maHD));
		hoaDon.setTongTien(tongTien);
		hoaDonDAO.updateTongTien(hoaDon);
	}
	
	public void deleteHoaDon(int index) {
		HoaDonDTO hoaDon = listHoaDon.get(index);
		listHoaDon.remove(index);
		hoaDonDAO.deleteHoaDon(hoaDon);
	}
	
	public ArrayList<HoaDonDTO> search(String key, String keyword) {
		ArrayList<HoaDonDTO> result = new ArrayList<HoaDonDTO>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		for(HoaDonDTO x : listHoaDon) {
			if(key.equals("Mã hóa đơn") && keyword.equals(x.getMaHD()))
				result.add(x);
			if(key.equals("Mã khách hàng") && keyword.equals(x.getMaKH()))
				result.add(x);
			if(key.equals("Ngày lập")) {
				try {
					java.util.Date dateUtil = format.parse(keyword);
					Date dateSQL = new Date(dateUtil.getTime());
					if(x.getNgayLap().equals(dateSQL))
						result.add(x);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			if(key.equals("Mã nhân viên") && keyword.equals(x.getMaNV()))
				result.add(x);
				
		}
		return result;
	}
	
	
}
