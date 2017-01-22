package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mvc.vo.Member;

public class MemberDao {
	Connection conn;
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * 회원 등록하기
	 */
	public int insertMember(Member member) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			String query = "INSERT INTO members(email,pwd,mname,cre_date,mod_date) VALUES (?,?,?,NOW(),NOW())";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMname());
			return pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw e;
			
		} finally {
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
	
	/*
	 * 회원리스트 보여주기
	 */
	public List<Member> selectList() throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT mno, mname, email, cre_date FROM members ORDER BY mno ASC";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member()
					.setMno(rs.getInt("mno"))
					.setMname(rs.getString("mname"))
					.setEmail(rs.getString("email"))
					.setCreatedDate(rs.getDate("cre_date")));
			}
			return members;
			
		} catch(Exception e) {
			throw e;
			
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
	
	/*
	 * 회원 상세정보 조회
	 */
	public Member selectOne(int no) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "SELECT mno, email, mname, cre_date, mod_date FROM members WHERE mno = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			Member member = new Member();
			if(rs.next()) {
				member.setMno(rs.getInt("mno"));
				member.setEmail(rs.getString("email"));
				member.setMname(rs.getString("mname"));
				member.setCreatedDate(rs.getDate("cre_date"));
				member.setModifiedDate(rs.getDate("mod_date"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
			return member;
			
		} catch(Exception e) {
			throw e;
			
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
	
	/*
	 * 회원정보 변경
	 */
	public int updateMember(Member member) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			String query = "UPDATE members SET email=?, mname=?, mod_date=now() WHERE mno=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getMname());
			pstmt.setInt(3, member.getMno());
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
	
	/*
	 * 회원정보삭제
	 */
	public int deleteMember(int mno) throws Exception {
		PreparedStatement pstmt = null;
		
		try {
			String query = "DELETE FROM members WHERE mno=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, mno);
			return pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw e;
			
		} finally {
			try {if(pstmt != null) pstmt.close();} catch(Exception e) {}
		}
	}
	
	/*
	 * 회원 존재하면 Member 객체 리턴, 없으면 null 리턴
	 */
	public Member exist(String email, String password) throws Exception {
		return null;
	}
}
