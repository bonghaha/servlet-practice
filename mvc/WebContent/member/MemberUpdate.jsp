<%@ page import="mvc.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>회원정보</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>

<%
	Member member = (Member) request.getAttribute("member");
%>
	
	<h1>회원정보</h1>
	<form action="update" method="post">
		<p>번호 : <input type="text" name="mno" value="<%= member.getMno() %>" readonly></p>
		<p>이름 : <input type="text" name="mname" value="<%= member.getMname() %>"></p>
		<p>이메일 : <input type="text" name="email" value="<%= member.getEmail() %>"></p>
		<p>가입일 : <%= member.getCreatedDate() %></p>
		<p>최종수정일 : <%= member.getModifiedDate() %></p>
		<div>
			<input type="submit" value="저장">
			<input type="button" value="삭제" onclick="location.href='delete?mno=<%= member.getMno() %>'">
			<input type="button" value="취소" onclick="location.href='list'">
		</div>
	</form>
	
	<jsp:include page="/Tail.jsp"/>
</body>
</html>