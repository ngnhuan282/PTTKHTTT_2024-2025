package BUS;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.NhaCungCapDAO;
import DAO.SanPhamDAO;
import DTO.NhaCungCapDTO;



public class NhaCungCapBUS {
	private NhaCungCapDAO nccDAO = new NhaCungCapDAO();
	public static ArrayList<NhaCungCapDTO> listNCC = new ArrayList<>();
	private NhaCungCapDTO ncc = new NhaCungCapDTO();
	public NhaCungCapBUS() {
		
	}
	

	public ArrayList<NhaCungCapDTO> getListNCC(){
		docDSNCC();
		return listNCC;
	}
	
	public void docDSNCC() {
		if(listNCC == null)
			listNCC = new ArrayList<NhaCungCapDTO>();
		listNCC = nccDAO.xuatDSNCC();
	}
	
	
	//update table 
	public DefaultTableModel updateTable(DefaultTableModel model){
		
		model.setRowCount(0);
		
		for(NhaCungCapDTO ncc : listNCC)
			model.addRow(new Object[] {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSDT(), ncc.getDiaChi()});
		return model;
	}
	

	
	public void addNCC(String maNCC,String tenNCC,String sdt,String diaChi) {		
		NhaCungCapDTO ncc = new NhaCungCapDTO(maNCC, tenNCC, sdt, diaChi);
		
		nccDAO.them(ncc);
		listNCC.add(ncc);
			
//		System.out.println(listNCC);
	}
	
	
	
	public void editNCC(String newMaNCC,String newTenNCC,String newSDT,String newDiaChi,String maNCC) {
		for(NhaCungCapDTO ncc : listNCC) {
			if(ncc.getMaNCC().equals(maNCC)) {
				ncc.setMaNCC(newMaNCC);
				ncc.setTenNCC(newTenNCC);
				ncc.setSDT(newSDT);
				ncc.setDiaChi(newDiaChi);
				nccDAO = new NhaCungCapDAO();
				nccDAO.sua(ncc,maNCC);
				return;
			}
		}	 
	}
	
	
	public void deleteNCC(String maNCC) {
		for(int i =0; i<listNCC.size();i++)
		{
			if(listNCC.get(i).getMaNCC().equals(maNCC))
			{
				nccDAO.xoa(maNCC);
				listNCC.remove(i);
				return;
			}
		}
	}
	
	
	public ArrayList<NhaCungCapDTO> searchNCC(String tuKhoa, String tieuChi){
		ArrayList<NhaCungCapDTO> result = new ArrayList<NhaCungCapDTO>();
		for(NhaCungCapDTO ncc : listNCC) {
			if(tieuChi.equals("Mã NCC") && ncc.getMaNCC().equalsIgnoreCase(tuKhoa)) 
				result.add(ncc);
			if(tieuChi.equals("Tên NCC") && ncc.getTenNCC().equalsIgnoreCase(tuKhoa))
				result.add(ncc);
			if(tieuChi.equals("SĐT") && ncc.getSDT().equalsIgnoreCase(tuKhoa))
				result.add(ncc);
		}
		return result;
	}
	
	//cac ham nhoooooooooooooo
	
	public boolean checkEdit(String newMaNCC,String maNCC) {
		for(NhaCungCapDTO ncc : listNCC) {
			 if(ncc.getMaNCC().equals(newMaNCC)&&(!ncc.getMaNCC().equals(maNCC))) 
			 	return true;
		 }
		return false;
	}
	
	public boolean checkSDT(String newSDT,String sdt) {
		for(NhaCungCapDTO ncc : listNCC) {
			 if(ncc.getSDT().equals(newSDT)&&(!ncc.getSDT().equals(sdt))) 
			 	return true;
		 }
		return false;
	}
	
	public boolean isDuplicateNCC(String maNCC) {
		for(NhaCungCapDTO ncc: listNCC ) 
			if(ncc.getMaNCC().equals(maNCC))
				return true;
		return false;	
	}
	
	public boolean isDuplicateSDT(String sdt) {
		for(NhaCungCapDTO ncc : listNCC) 
			if(ncc.getSDT().equals(sdt)) 
				return true;
		return false;
	}
	
	public int[] ImportExcel(File file) {
		NhaCungCapDAO nccDAO = new NhaCungCapDAO();
		int[] result = nccDAO.ImportExcel(file);
		listNCC = nccDAO.xuatDSNCC();
		return result;
	}
}
