<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>로그인</title>
</head>
<body>
	<h2>사용자 로그인</h2>
	<form action="/auth/login.do" method="post">
		<p>이메일 : <input type="text" name="email"></p>
		<p>암호 : <input type="password" name="password"></p>
		<div>
			<input type="submit" value="Login">
		</div>
	</form>
</body>
</html>