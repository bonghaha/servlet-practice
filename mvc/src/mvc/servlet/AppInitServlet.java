package mvc.servlet;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class AppInitServlet extends HttpServlet{
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppinitServlet 준비...");
		super.init(config);
		
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			
			// 나머지 모든 서블릿들이 사용할 수 있도록 ServletContext객체에 저장
			sc.setAttribute("conn", conn);
			
		} catch(Throwable e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리...");
		super.destroy();
		
		Connection conn = (Connection) this.getServletContext().getAttribute("conn");
		
		try {if(conn != null && conn.isClosed() == false) conn.close();} catch(Exception e) {}
	}
}
