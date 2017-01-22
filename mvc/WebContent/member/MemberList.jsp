<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>회원 목록</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	<!-- 
		위의 include는 
		RequestDispatcher rd = request.getRequestDispatcher("/Header.jsp");
		rd.include(request, response);
		와 같다
	 -->
	<h1>회원 목록</h1>
	<p><a href="/member/add">신규 회원</a></p>
	<c:if test="${not empty sessionScope.member}">
		${sessionScope.member.mname}님 환영합니다.
	</c:if>
	
	<c:forEach var="member" items="${members}">
		<p>
			${member.mno},
			<a href="/member/update?mno=${member.mno}">${member.mname}</a>,
			${member.email},
			${member.createdDate}
			<a href="/member/delete?mno=${member.mno}">[삭제]</a>
		</p>
	</c:forEach>
	
	<jsp:include page="/Tail.jsp"/>
</body>
</html>