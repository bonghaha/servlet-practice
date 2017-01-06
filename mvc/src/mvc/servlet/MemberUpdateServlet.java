package mvc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.vo.Member;


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
			String query = "SELECT mno, email, mname, cre_date, mod_date FROM members WHERE mno = " + request.getParameter("mno");
			rs = stmt.executeQuery(query);
			rs.next();
			
			response.setContentType("text/html; charset=UTF-8");
			Member member = new Member()
				.setMno(Integer.parseInt(request.getParameter("mno")))
				.setMname(rs.getString("mname"))
				.setEmail(rs.getString("email"))
				.setCreatedDate(rs.getDate("cre_date"))
				.setModifiedDate(rs.getDate("mod_date"));
			
			request.setAttribute("member", member);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdate.jsp");
			//include?? or forward?? jsp가 작업을 끝내고 추가작업 할 것이 있나???
			rd.forward(request, response);
			
		} catch (Exception e) {
//			throw new ServletException(e);
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(conn != null) conn.close();} catch(Exception e) {}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//			throw new ServletException(e);
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
			try {if(conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
