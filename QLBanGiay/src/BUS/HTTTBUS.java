package BUS;
import DTO.HTTTDTO;
import java.util.ArrayList;
import DAO.HTTTDAO;
public class HTTTBUS {
    private ArrayList<HTTTDTO> dsHTTT = new ArrayList<>();
    private HTTTDAO dao = new HTTTDAO();

    public HTTTBUS() {
        dsHTTT = dao.getAllHTTT();
    }

    public ArrayList<HTTTDTO> getListHTTT() {
        return dsHTTT;
    }
}
