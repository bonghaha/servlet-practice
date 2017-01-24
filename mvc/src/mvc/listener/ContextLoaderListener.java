package mvc.listener;


import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import mvc.dao.MemberDao;

@WebListener // 애노테이션으로 리스너 배치. or DD파일에 XML태그 선언
public class ContextLoaderListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		try {
			ServletContext sc = event.getServletContext();
			
			// Tomcat자원을 찾기 위해 InitialContext객체 생성. InitialContext의 lookup()메서드를 이요하면, JNDI이름으로 등록되어 있는 서버 자원을 찾을 수 있다.
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/mvc");
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {} // Tomcat서버에 자동으로 해제하라고 설정되어 있기 때문에 메서드안에 내용 작성 안함
}

