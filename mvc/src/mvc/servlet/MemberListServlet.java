package mvc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),			//JDBC URL
					sc.getInitParameter("username"),	// DBMS 사용자 아이디
					sc.getInitParameter("password"));	// DBMS 사용자 암호
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT mno,mname,email,cre_date" + 
					" FROM members" +
					" ORDER BY mno ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			while(rs.next()) {
				out.println(
					rs.getInt("mno") + "," +
					"<a href='update?mno=" + rs.getInt("mno") + "'>" + rs.getString("mname") + "</a>," +
					rs.getString("email") + "," + 
					rs.getDate("cre_date") +
					"<a href='delete?mno=" + rs.getInt("mno") + "'>[삭제]</a><br>"
				);
			}
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
