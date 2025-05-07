package BUS;

import java.util.ArrayList;
import DAO.LoaiDAO;
import DTO.LoaiDTO;

public class LoaiBUS {
    private static ArrayList<LoaiDTO> dsloai;

    public LoaiBUS() 
    {
       
    }

   

    public static void setDsloai(ArrayList<LoaiDTO> dsloai) {
        LoaiBUS.dsloai = dsloai;
    }

    public void docDSLoai()
    {
    	if(dsloai == null)
    		dsloai = new ArrayList<LoaiDTO>();
    	LoaiDAO loaiDAO = new LoaiDAO();
    	dsloai = loaiDAO.docDSLoai();
    }
    
    public ArrayList<LoaiDTO> getDsloai() 
    {
    	if (dsloai == null) 
            docDSLoai(); 
        return dsloai;
    }
    
    public int getNextID() 
    {
        int max = 0;
        for (LoaiDTO loai : dsloai) 
        {
            if (loai.getMaLoaiSP() > max) 
            {
                max = loai.getMaLoaiSP();
            }
        }
        return max + 1;
    }
    
    public boolean add(LoaiDTO loai)
    {
    	if(loai.getTenLoaiSP().isEmpty() || loai.getTenLoaiSP() == null)
    		return false;
    	
    	for(LoaiDTO x : dsloai)
    	{
    		if(x.getMaLoaiSP() == loai.getMaLoaiSP())
    			return false;
    	}
    	
    	LoaiDAO loaiDAO = new LoaiDAO();
    	loaiDAO.add(loai);
    	dsloai.add(loai);
    	return true;
    }
    
    public boolean update(LoaiDTO loai)
    {
    	if(loai.getMaLoaiSP() <= 0)
    		return false;
    	if(loai.getTenLoaiSP().isEmpty() || loai.getTenLoaiSP() == null)
    		return false;
    	
    	for(int i=0; i < dsloai.size(); i++)
    	{
    		if(dsloai.get(i).getMaLoaiSP() == loai.getMaLoaiSP())
    		{
    			LoaiDAO loaiDAO = new LoaiDAO();
    			loaiDAO.update(loai);
    			dsloai.set(i, loai);
    			return true;
    		}
    	}
    	return false;
    }

    public boolean delete(int maLoai)
    {
    	if(maLoai <= 0)
    		return false;
    	for(int i=0; i < dsloai.size(); i++)
    	{
    		if(dsloai.get(i).getMaLoaiSP() == maLoai)
    		{
    			LoaiDAO loaiDAO = new LoaiDAO();
    			loaiDAO.delete(maLoai);
    			dsloai.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
}