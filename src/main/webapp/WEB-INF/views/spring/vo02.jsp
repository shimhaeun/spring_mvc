<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>VO02만듦</h1>
	
	<!-- 전달받은 자료가 객체라면, 자바코드에서와 마찬가지로 객체.변수명과 같이 내부 변수를 하나하나 따로 출력가능 -->
	<h3>${BaseVO }</h3>
	<h3>${BaseVO.name }</h3>
	<h3>${BaseVO.age }</h3>
	<h3>${BaseVO.job }</h3>
</body>
</html>