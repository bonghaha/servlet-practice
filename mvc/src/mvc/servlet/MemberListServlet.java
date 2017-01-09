package mvc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.vo.Member;

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
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT mno,mname,email,cre_date" + 
					" FROM members" +
					" ORDER BY mno ASC");
			
			response.setContentType("text/html; charset=UTF-8");
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member()
					.setMno(rs.getInt("mno"))
					.setMname(rs.getString("mname"))
					.setEmail(rs.getString("email"))
					.setCreatedDate(rs.getDate("cre_date")));
//				System.out.println("MemberListServlet rs.getString(\"mname\") : " + rs.getString("mname"));
			}
			
			// request에 회원 목록 데이터 보관한다.
			request.setAttribute("members", members);
			
			// JSP로 출력을 위임한다.
			// RequestDispatcher를 이용한 forward, include통해 JSP로 위임
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");	// 경로가 '/'로 시작하면 웹 애플리케이션 루트를 의미
			
			// JSP가 작업을 끝낸 후 MemberListServlet에서 추가 작업을 할 경우를 고려하여 include
			rd.include(request, response);
			// MemberListServlet과 MemberList.jsp는 request와 response를 공유하게 됨
			
		} catch (Exception e) {
//			throw new ServletException(e);
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}

	}
}
