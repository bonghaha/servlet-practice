package mvc.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.dao.MemberDao;
import mvc.vo.Member;


@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("mno")));
			request.setAttribute("member", member);
			request.setAttribute("viewUrl", "/member/MemberUpdate.jsp");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			memberDao.updateMember(new Member()
				.setEmail(request.getParameter("email"))
				.setMname(request.getParameter("mname"))
				.setMno(Integer.parseInt(request.getParameter("mno"))));
			
			response.sendRedirect("/member/list");
			
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}
	}
}
