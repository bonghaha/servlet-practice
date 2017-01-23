package mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	ArrayList<Connection> connList = new ArrayList<>();
	
	public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver);
	}
	
	public Connection getconnection() throws Exception {
		if (connList.size() > 0) {
			Connection conn = connList.get(0);
			if (conn.isValid(10)) {	// Connection객체가 닫히지 않고 아직 유효한지 여부를 나타냄. 10 => 연결의 유효성을 검사하는 동안 대기하는 시간(초)을 지정하는 int
				return conn;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}
	
	// 커넥션 객체를 쓰고 난 다음에 이 메서드를 호출하여 커넥션 풀에 반환. 그래야만 다음에 다시 사용할 수 있음.
	public void returnConnection(Connection conn) throws Exception {
		connList.add(conn);
	}
	
	// 웹 애플리케이션을 종료하기 전에 이 메서드를 호출하여 데이터베이스와 연결된 것을 모두 끊어야 함.
	public void closeAll() {
		for(Connection conn : connList) {
			try {conn.close();} catch (Exception e) {}
		}
	}
}


