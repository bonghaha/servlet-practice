package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;

public class MemberDeleteController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		int mno = (int) model.get("mno");
		
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		memberDao.deleteMember(mno);
		return "redirect:/member/list.do";
	}
}