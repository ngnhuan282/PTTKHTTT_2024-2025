package BUS;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;

public class PhieuNhapBUS {
	private PhieuNhapDAO pnDAO = new PhieuNhapDAO();
	public static ArrayList<PhieuNhapDTO> listPN = new ArrayList<>();

	
	public ArrayList<PhieuNhapDTO> getListPN(){
		docDSPN();
		return listPN;
	}
	
	public void docDSPN() {
		if(listPN==null)
			listPN = new ArrayList<PhieuNhapDTO>();
		listPN = pnDAO.xuatDSPN();
	}
	
	
	public String generateMaPN() {
		int size = listPN.size()+ 1;
		return size +"";
	}

	public void addPN(String maPN , String NV , String NCC,double tongTien, Date ngayNhap) {
		PhieuNhapDTO pn = new PhieuNhapDTO(maPN,NV,NCC,tongTien,ngayNhap);
		listPN.add(pn);
		pnDAO.them(pn);
	}
	
	
	public void updatePN(String maPN , String NV , String NCC,double tongTien, Date ngayNhap) {
		for (PhieuNhapDTO pn : listPN) {
			if (pn.getMaPhieuNH().equals(maPN)) {
	            pn.setMaNV(NV);
	            pn.setMaNCC(NCC);
	            pn.setTongTien(tongTien);
	            pn.setNgayNhap(ngayNhap);
	            pnDAO.sua(pn, maPN);
	            break;
	        }
	    }
	}
	
	
	public void deletePN(String maPN) {
		for(int i = 0 ;i<listPN.size();i++)
		{
			if(listPN.get(i).getMaPhieuNH().equals(maPN))
			{
				pnDAO.xoa(maPN);
				listPN.remove(i);
				return;
			}
		}
	}
	
	public ArrayList<PhieuNhapDTO> search(String tuKhoa, String tieuChi) {
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
		for(PhieuNhapDTO pn : listPN){
			if(tieuChi.equals("Mã PN")&& pn.getMaPhieuNH().equalsIgnoreCase(tuKhoa))
				result.add(pn);
			if(tieuChi.equals("Mã NV")&& pn.getMaNV().equalsIgnoreCase(tuKhoa))
				result.add(pn);
			if(tieuChi.equals("Mã NCC")&& pn.getMaNCC().equalsIgnoreCase(tuKhoa))
				result.add(pn);		
		}
		return result;
	}
	
	
	public ArrayList<PhieuNhapDTO> searchByDate(Date startDate, Date endDate){
		ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
		for (PhieuNhapDTO pn : listPN) {
			 Date ngayNhap = pn.getNgayNhap();
			 if(ngayNhap != null && !ngayNhap.before(startDate) && !ngayNhap.after(endDate)) {
			 	result.add(pn); 
			 }
		 }
		 return result;
	}
	
	 public ArrayList<PhieuNhapDTO> searchByMaPhieuNHOrMaNCC(String maPNNC, String maNCCNC) {
		 ArrayList<PhieuNhapDTO> result = new ArrayList<PhieuNhapDTO>();
		 for (PhieuNhapDTO pn : listPN) 
			 if(pn.getMaPhieuNH().equalsIgnoreCase(maPNNC) || pn.getMaNCC().equalsIgnoreCase(maNCCNC))
				 result.add(pn);			 		 
		 return result;
	 }
	
	
	
	
	public boolean checkMaPN(String maPN) {
		for(PhieuNhapDTO pn : listPN) 
			if(pn.getMaPhieuNH().equals(maPN))
				return true;
		return false;
	}
}
