package mvc.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.dao.MemberDao;
import mvc.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			Member member = memberDao.exist(
				request.getParameter("email"),
				request.getParameter("password"));
			
			if(member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				
				request.setAttribute("viewUrl", "redirect:/member/list.do");
			} else {
				request.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}
			
		} catch(Exception e) {
			throw new ServletException(e);
			
		}
	}
}
