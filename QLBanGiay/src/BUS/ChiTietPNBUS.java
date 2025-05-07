package BUS;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import DAO.ChiTietPNDAO;
import DTO.ChiTietPNDTO;


public class ChiTietPNBUS {
	private ChiTietPNDAO ctpnDAO = new ChiTietPNDAO();
	public static ArrayList<ChiTietPNDTO> listCTPN = new ArrayList<>();

	public ArrayList<ChiTietPNDTO> getListCTPN(){
		docDSCTPN();
		return listCTPN;
	}
	
	public void docDSCTPN() {
		if(listCTPN==null)
			listCTPN = new ArrayList<ChiTietPNDTO>();
		listCTPN = ctpnDAO.xuatDSCTPN();
	}
	
	
	
	public ArrayList<ChiTietPNDTO> getChiTietTheoMaPhieu(String maPN) {
		ArrayList<ChiTietPNDTO> result = new ArrayList<>();
		for(ChiTietPNDTO ctpn : listCTPN) 
			if(ctpn.getMaPhieuNH().equals(maPN))
					result.add(ctpn);
		return result;
	}
	
	
	
	public void addCTPN(String maPN , String SP , int soLuong,double donGia,double thanhTien) {
		ChiTietPNDTO ctpn = new ChiTietPNDTO(maPN ,SP ,soLuong,donGia,thanhTien);
		listCTPN.add(ctpn);
		ctpnDAO.them(ctpn);
	}
	
	public void updateCTPN(String maPN, ArrayList<ChiTietPNDTO> newListCTPN) {
				ctpnDAO.xoa(maPN);
		for(ChiTietPNDTO ctpn : newListCTPN)
			ctpnDAO.them(ctpn);
		
		docDSCTPN();
	}
	
	public void deleteCTPN(String maPN) {
		for(int i = 0 ; i<listCTPN.size();i++) {
			if(listCTPN.get(i).getMaPhieuNH().equals(maPN))
			{
				ctpnDAO.xoa(maPN);
				listCTPN.remove(i);
				return;
			}
		}
	}
}
