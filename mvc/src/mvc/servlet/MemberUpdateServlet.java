package mvc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			stmt = conn.createStatement();
			String query = "SELECT mno, email, mname, cre_date FROM members WHERE mno = " + request.getParameter("mno");
			rs = stmt.executeQuery(query);
			rs.next();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원정보</title></head>");
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호 : <input type='text' name='mno' value='" + request.getParameter("mno") + "'readonly><br>");
			out.println("이름 : <input type='text' name='mname' value='" + rs.getString("mname") + "'><br>");
			out.println("이메일 : <input type='text' name='email' value='" + rs.getString("email") + "'><br>");
			out.println("가입일 : " + rs.getDate("cre_date") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='삭제' onclick='location.href=\"delete?mno=" + request.getParameter("mno") + "\"'>");
			out.println("<input type='button' value='취소' onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(conn != null) conn.close();} catch(Exception e) {}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			String query = "UPDATE members SET email=?, mname=?, mod_date=now() WHERE mno=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, request.getParameter("email"));
			pstmt.setString(2, request.getParameter("mname"));
			pstmt.setInt(3, Integer.parseInt(request.getParameter("mno")));
			pstmt.executeUpdate();
			response.sendRedirect("list");
			
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
			try {if(conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
