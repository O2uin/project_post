package crawlingDataDML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {   // DB 자바 연결 클래스
	
	protected Connection conn = null;
	protected PreparedStatement statement = null;
	protected ResultSet select = null;
	
	//오라클 연결에 필요한 변수 선언
	String query = null;
	String url = "jdbc:oracle:thin:@192.168.0.5:1521:xe"; 
	String username = "WEBMASTER";
	String password = "webmaster";
	
	
	public void DBstart() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void DBstop() {   //쿼리문 사용 후 close
		try {
			if(select != null) {
				select.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(conn != null) {
				conn.close();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}