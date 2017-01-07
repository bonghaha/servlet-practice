<%@ page import="mvc.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="member" scope="session" class="mvc.vo.Member"/>
<%
// 	Member member = (Member) session.getAttribute("member"); // jsp:useBean 액션태그로 인해 삭제
%>
<div style="
	background-color:#00008b;
	color:#ffffff;
	height:20px;
	padding:5px;">
	Simple Project Management System
<%
	if(member.getEmail() != null) {
%>
		<span style="float:right;">
			<%= member.getMname() %>
			<a style="color:white;" href="<%= request.getContextPath() %>/auth/logout">Logout</a>
		</span>
<%
	} else {
%>
		<span style="float:right;">
			<a style="color:white;" href="<%= request.getContextPath() %>/auth/login">Login</a>
		</span>
<%
	}
%>
</div>