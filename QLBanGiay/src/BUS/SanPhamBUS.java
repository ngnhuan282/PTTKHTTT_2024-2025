package BUS;

import java.io.File;
import java.util.ArrayList;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

public class SanPhamBUS {
    private static ArrayList<SanPhamDTO> dssp;

    public SanPhamBUS() 
    {
    	
    }

    public void docDSSP() 
    {	
    	if(dssp == null)
    		dssp = new ArrayList<SanPhamDTO>();
        SanPhamDAO spDAO = new SanPhamDAO();
        dssp = spDAO.docDSSP();
    }

    public ArrayList<SanPhamDTO> getDssp() 
    {	
    	if (dssp == null) 
            docDSSP(); 
        return dssp;
    }

    public String validateSanPham(SanPhamDTO sp) 
    {
        if (sp.getMaSP().isEmpty()) return "Vui lòng nhập mã sản phẩm";
        if (sp.getTenSP().isEmpty()) return "Vui lòng nhập tên sản phẩm";
        if (sp.getMaLoaiSP() <= 0) return "Vui lòng nhập mã loại sản phẩm";
        if (sp.getDonGia() <= 0) return "Đơn giá phải lớn hơn 0";
        if (sp.getSoLuong() <= 0) return "Số lượng phải lớn hơn 0";
        if (sp.getDonViTinh().isEmpty()) return "Vui lòng nhập đơn vị tính";
        if (!sp.getMaSP().matches("SP\\d{3}")) return "Mã SP phải có dạng SPxxx";
        return null;
    }

    public boolean checkMaSP(String maSP) 
    {
        for (SanPhamDTO sp : dssp) 
            if (sp.getMaSP().equals(maSP)) 
                return true;
        return false;
    }

    public boolean addSP(SanPhamDTO sp) 
    { 
     
        if (checkMaSP(sp.getMaSP())) 
        {
            System.out.println("Mã sản phẩm đã tồn tại: " + sp.getMaSP());
            return false;
        }

        SanPhamDAO spDAO = new SanPhamDAO();
        try {
            spDAO.add(sp);
            dssp.add(sp);
            System.out.println("Thêm sản phẩm thành công: " + sp.getMaSP());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSP(String maSP) 
    {
        for (int i = 0; i < dssp.size(); i++) 
        {
            if (dssp.get(i).getMaSP().equals(maSP)) 
            {
                SanPhamDAO dao = new SanPhamDAO();
                try {
                    dao.delete(maSP);
                    dssp.remove(i);
                    System.out.println("Xóa sản phẩm thành công: " + maSP);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Không tìm thấy sản phẩm để xóa: " + maSP);
        return false;
    }

    public boolean updateSP(SanPhamDTO sp) 
    { 
    	
        for (int i = 0; i < dssp.size(); i++) 
        {
            if (dssp.get(i).getMaSP().equals(sp.getMaSP()))
            {
                SanPhamDAO dao = new SanPhamDAO();
                try {
                    dao.update(sp);
                    dssp.set(i, sp);
                    System.out.println("Cập nhật sản phẩm thành công: " + sp.getMaSP());
                    return true;
                } catch (Exception e) 
                {
                    e.printStackTrace();
                    System.out.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
                    return false;
                }
            }
        }
        System.out.println("Không tìm thấy sản phẩm để cập nhật: " + sp.getMaSP());
        return false;
    }
    
    public ArrayList<SanPhamDTO> searchSP(String tuKhoa, String tieuChi)
    {
    	ArrayList<SanPhamDTO> result = new ArrayList<SanPhamDTO>();
    	for(SanPhamDTO sp : dssp)
    	{
    		if(tieuChi.equals("Mã SP") && sp.getMaSP().equalsIgnoreCase(tuKhoa))
    			result.add(sp);
    		if(tieuChi.equals("Tên SP") && sp.getTenSP().equalsIgnoreCase(tuKhoa))
    			result.add(sp);
    	}
    	return result;
    }
    
    
    //Phieu nhappppppppp
    public void updateSoLuong(String maSP , int soLuongThayDoi) {
    	for(SanPhamDTO sp : dssp) {
    		if(sp.getMaSP().equals(maSP)) {
    			int soLuongMoi = sp.getSoLuong()+soLuongThayDoi;
    			if(soLuongMoi < 0) {
    				System.out.println("Số lượng sản phẩm không thể âm!");
    				return;
    			}
    			sp.setSoLuong(soLuongMoi);
    			SanPhamDAO spDAO = new SanPhamDAO();
    			spDAO.capNhapSoLuong(maSP, soLuongMoi); 
    			break;
    		}
    	}
    }
    
    public int[] ImportExcel(File file) 
    {
        SanPhamDAO spDAO = new SanPhamDAO();
        int[] result = spDAO.ImportExcel(file);
        docDSSP(); // Cập nhật DSSP
        return result;
    }
}