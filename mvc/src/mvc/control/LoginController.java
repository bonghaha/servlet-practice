package mvc.control;

import java.util.Map;

import javax.servlet.http.HttpSession;

import mvc.dao.MemberDao;
import mvc.vo.Member;

public class LoginController implements Controller {
	MemberDao memberDao;
	
	public LoginController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginInfo") == null) {	// 로그인 입력폼 요청
			return "/auth/LoginForm.jsp";
			
		} else {	// 로그인 요청
			Member loginInfo = (Member) model.get("loginInfo");
			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			
			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:/member/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
		}
	}
}
