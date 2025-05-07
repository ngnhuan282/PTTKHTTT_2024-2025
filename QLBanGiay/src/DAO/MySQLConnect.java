package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {
	private String user = "root";
	private String password = "";
	private String url = "jdbc:mysql://localhost:3306/QuanLyBanGiay";
	private Connection conn = null;
	private Statement st = null;
	
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
	}
	
	public void disConnect()
	{
		try {
			st.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sql)
	{
		ResultSet rs = null;
		try {
			Connect();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	public void executeUpdate(String sql)
	{
		try {
			Connect();
			st = conn.createStatement();
			st.executeUpdate(sql);
			disConnect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		Connect();
		return conn;
	}
	
	public boolean isConnect()
	{
		if(conn != null)
			return true;
		return false;
	}
}