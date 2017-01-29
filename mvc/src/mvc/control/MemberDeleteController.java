package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;

public class MemberDeleteController implements Controller {
	MemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		int mno = (int) model.get("mno");
		memberDao.deleteMember(mno);
		return "redirect:/member/list.do";
	}
}