package mvc.vo;

import java.util.Date;

public class Member {
	protected int mno;
	protected String mname;
	protected String email;
	protected String password;
	protected Date createdDate;
	protected Date modifiedDate;
	
	public int getMno() {
		return mno;
	}
	// return값이 void가 아니고 member인 이유 = 셋터 메서드를 연속으로 호출하여 값을 할당할 수 있게 하기 위함
	// new Member().setMno(1).setMname("홍길동").setEmail("hong@gil.dong");
	public Member setMno(int mno) {
		this.mno = mno;
		return this;
	}
	
	public String getMname() {
		return mname;
	}
	public Member setMname(String mname) {
		this.mname = mname;
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	public Member setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	public Member setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public Member setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public Member setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
		return this;
	}
}
