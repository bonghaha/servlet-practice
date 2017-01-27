package mvc.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.control.Controller;
import mvc.control.LoginController;
import mvc.control.LogoutController;
import mvc.control.MemberAddController;
import mvc.control.MemberDeleteController;
import mvc.control.MemberListController;
import mvc.control.MemberUpdateController;
import mvc.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		
		try {
			ServletContext sc = this.getServletContext();
			
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			String pageControllerPath = null;
			Controller pageController = null;
			
			if ("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
				
			} else if ("/member/add.do".equals(servletPath)) {
				pageController = new MemberAddController();
				
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setMname(request.getParameter("mname")));
				}
				
			} else if ("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
				
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
						.setMno(Integer.parseInt(request.getParameter("mno")))
						.setEmail(request.getParameter("email"))
						.setMname(request.getParameter("mname")));
				} else {
					model.put("mno", new Integer(request.getParameter("mno")));
				}
				
			} else if ("/member/delete.do".equals(servletPath)) {
				pageController = new MemberDeleteController();
				model.put("mno", new Integer(request.getParameter("mno")));
				
			} else if ("/auth/login.do".equals(servletPath)) {
				pageController = new LoginController();
				if (request.getParameter("email") != null) {
					model.put("loginInfo", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password")));
				}
				
			} else if ("/auth/logout.do".equals(servletPath)) {
				pageController = new LogoutController();
			}
			
			String viewUrl = pageController.execute(model);
			
			for (String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}



