package mvc.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.dao.MemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;

		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			
			request.setAttribute("members", memberDao.selectList());
			
			response.setContentType("text/html; charset=UTF-8");

			// JSP로 출력을 위임한다.
			// RequestDispatcher를 이용한 forward, include통해 JSP로 위임
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");	// 경로가 '/'로 시작하면 웹 애플리케이션 루트를 의미
			
			// JSP가 작업을 끝낸 후 MemberListServlet에서 추가 작업을 할 경우를 고려하여 include
			rd.include(request, response);
			// MemberListServlet과 MemberList.jsp는 request와 response를 공유하게 됨
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}
	}
}
