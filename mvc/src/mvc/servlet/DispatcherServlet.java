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
			model.put("session", request.getSession());
			
			Controller pageController = (Controller) sc.getAttribute(servletPath);
			
			if ("/member/add.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setMname(request.getParameter("mname")));
				}
				
			} else if ("/member/update.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
						.setMno(Integer.parseInt(request.getParameter("mno")))
						.setEmail(request.getParameter("email"))
						.setMname(request.getParameter("mname")));
				} else {
					model.put("mno", new Integer(request.getParameter("mno")));
				}
				
			} else if ("/member/delete.do".equals(servletPath)) {
				model.put("mno", new Integer(request.getParameter("mno")));
				
			} else if ("/auth/login.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("loginInfo", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password")));
				}
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



