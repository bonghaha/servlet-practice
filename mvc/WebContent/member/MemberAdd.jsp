<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>회원 등록</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	
	<h1>회원 등록</h1>
	<form action="/member/add.do" method="post">
		<p>이름 : <input type="text" name="mname"></p>
		<p>이메일 : <input type="text" name="email"></p>
		<p>암호 : <input type="password" name="password"></p>
		<div>
			<input type="submit" value="추가">
			<input type="reset" value="취소">
		</div>
	</form>
	
	<jsp:include page="/Tail.jsp"/>
</body>
</html>