package mvc.control;

import java.util.Map;

import mvc.dao.MemberDao;

public class MemberListController implements Controller{
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberdao = (MemberDao) model.get("memberDao");
		model.put("members", memberdao.selectList());
		return "/member/MemberList.jsp";
	}
}
