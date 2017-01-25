package mvc.servlet;

import java.io.IOException;

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

		try {
			ServletContext sc = this.getServletContext();
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			request.setAttribute("members", memberDao.selectList());
			
			// JSP URL 정보를 프런트 컨트롤러에게 알려주고자 ServletRequest 보관소에 저장합니다.
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
