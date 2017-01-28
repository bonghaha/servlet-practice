package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;

public class MemberListController implements Controller{
	MemberDao memberDao;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
//		MemberDao memberdao = (MemberDao) model.get("memberDao"); // 외부에서 MemberDao 객체를 주입해 줄 것이기 때문에 이제 더이상 Map 객체에서 MemberDao를 꺼낼 필요가 없다.
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}
}
