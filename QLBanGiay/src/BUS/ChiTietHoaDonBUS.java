package BUS;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHDDTO;

public class ChiTietHoaDonBUS {
	private ArrayList<ChiTietHDDTO> listCTHD;
	private ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
	
	public ChiTietHoaDonBUS() throws SQLException {
		listCTHD = new ArrayList<ChiTietHDDTO>();
		listCTHD = chiTietHoaDonDAO.getListCTHD();
	}

	public ArrayList<ChiTietHDDTO> getListCTHD() {
		return listCTHD;
	}

	public void setListCTHD(ArrayList<ChiTietHDDTO> listCTHD) {
		this.listCTHD = listCTHD;
	}
	
	public void addCTHD(String maHD, String maSP, int soLuong, double donGia, double thanhTien) {
		ChiTietHDDTO chiTietHDDTO = new ChiTietHDDTO(maHD, maSP, soLuong, donGia, thanhTien);
		listCTHD.add(chiTietHDDTO);
		chiTietHoaDonDAO.addCTHD(chiTietHDDTO);
	}
	
	public double getTongTien() {
		double tongTien = 0;
		for(ChiTietHDDTO x : listCTHD) {
			tongTien += x.getThanhTien();
		}
		return tongTien;
	}
	
	public void updateCTHD(String maHD, String maSP, int soLuong, double donGia, double thanhTien, int index) {
		ChiTietHDDTO chiTietHDDTO = listCTHD.get(index);
		chiTietHDDTO.setMaHD(maHD);
		chiTietHDDTO.setMaSP(maSP);
		chiTietHDDTO.setThanhTien(thanhTien);
		chiTietHDDTO.setSoLuong(soLuong);
		chiTietHDDTO.setDonGia(donGia);
		chiTietHoaDonDAO.updateCTHD(chiTietHDDTO);
	}
	
	public void deleteCTHD(String maHD) {
		for(ChiTietHDDTO x : listCTHD) {
			if(x.getMaHD().equals(maHD)) {
				chiTietHoaDonDAO.tangSoLuongSP(x.getMaSP(), x.getSoLuong());
			}
		}
		listCTHD.removeIf(x -> x.getMaHD().equals(maHD));
		chiTietHoaDonDAO.deleteCTHD(maHD);
	}
	
	public void deleteCTHDByIndex(int index) {
		ChiTietHDDTO chiTietHDDTO = listCTHD.get(index);
		listCTHD.remove(index);
		chiTietHoaDonDAO.deleteCTHD(chiTietHDDTO.getMaHD(), chiTietHDDTO.getMaSP());
	}
	
	public void deleteCTHDByIndex(int index, String maSP) {
		ChiTietHDDTO chiTietHDDTO = listCTHD.get(index);
		listCTHD.remove(index);
		chiTietHoaDonDAO.tangSoLuongSP(maSP, chiTietHDDTO.getSoLuong());
		chiTietHoaDonDAO.deleteCTHD(chiTietHDDTO.getMaHD(), chiTietHDDTO.getMaSP());
	}
	
	public boolean checkDulicateMaSP(String maSP, String maHD) {
		for(ChiTietHDDTO x : listCTHD) {
			if(x.getMaSP().equals(maSP) && x.getMaHD().equals(maHD))
				return true;
		}
		return false;
	}
	
	public boolean checkDulicate(String maHD, String maSP) {
		for(ChiTietHDDTO x : listCTHD) {
			if(x.getMaHD().equals(maHD) && x.getMaSP().equals(maSP))
				return true;
		}
		return false;
	}
	
	public void updateSoLuongSP(String maSP, int soLuong) {
		chiTietHoaDonDAO.updateSoLuongSP(maSP, soLuong);
	}
}
