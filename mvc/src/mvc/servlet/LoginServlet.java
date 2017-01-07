package mvc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginForm.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			String query = "SELECT mname, email FROM members WHERE email=? AND pwd=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, request.getParameter("email"));
			pstmt.setString(2, request.getParameter("password"));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Member member = new Member()
						.setEmail(rs.getString("email"))
						.setMname(rs.getString("mname"));
				
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				
				response.sendRedirect("/member/list");
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginFail.jsp");
				rd.forward(request, response);
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
}
