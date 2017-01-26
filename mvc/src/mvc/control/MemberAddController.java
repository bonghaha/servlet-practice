package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;
import mvc.vo.Member;

public class MemberAddController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// 입력폼을 요청할 때
		if (model.get("member") == null) {
			return "/member/MemberAdd.jsp";
		
		// 회원 등록을 요청할 때
		} else {
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member member = (Member) model.get("member");
			memberDao.insertMember(member);
			return "redirect:/member/list.do";
		}
	}
}
