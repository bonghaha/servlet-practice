<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<div style="
	background-color:#00008b;
	color:#ffffff;
	height:20px;
	padding:5px;">
	Simple Project Management System
	<c:if test="${not empty sessionScope.member}">
		<span style="float:right;">
			${sessionScope.member.mname}
			<a style="color:white;" href="<%=request.getContextPath()%>/auth/logout.do">Logout</a>
		</span>
	</c:if>
	<c:if test="${empty sessionScope.member}">
		<span style="float:right;">
			<a style="color:white;" href="<%=request.getContextPath()%>/auth/login.do">Login</a>
		</span>
	</c:if>
</div>