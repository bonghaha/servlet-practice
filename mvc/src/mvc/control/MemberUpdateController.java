package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;
import mvc.vo.Member;

public class MemberUpdateController implements Controller {
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			int mno = (int) model.get("mno");
			Member member = memberDao.selectOne(mno);
			model.put("member", member);
			return "/member/MemberUpdate.jsp";
			
		} else {
			Member member = (Member) model.get("member");
			memberDao.updateMember(member);
			return "redirect:/member/list.do";
		}
	}
}
