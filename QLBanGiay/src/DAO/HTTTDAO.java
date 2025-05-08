package DAO;
import DTO.HTTTDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class HTTTDAO {
    private MySQLConnect connection = new MySQLConnect();

    public ArrayList<HTTTDTO> getAllHTTT() {
        ArrayList<HTTTDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hinhthucthanhtoan";
        try {
            connection.getConnection();
            ResultSet rs = connection.executeQuery(sql);
            while (rs.next()) {
                list.add(new HTTTDTO(rs.getString("MaHTTT"), rs.getString("TenHTTT")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disConnect();
        }
        return list;
    }
}
