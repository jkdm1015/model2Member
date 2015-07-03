<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="context" value="<%=request.getContextPath() %>"></c:set>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>인덱스</title>
</head>
<body>
	<h1> Chapter 01. jQuery 기본 </h1>
	<ol>
		<li> <a href="${context}/jquery/example01_01.do"> 페이지가 렌더링된 후 CSS 변경하기 </a> </li>
		<li> <a href="${context}/jquery/example01_02.do"> 준비 핸들러 사용하기 </a> </li>
		<li> <a href="${context}/jquery/example01_03.do"> $, jQuery 출력하기</a> </li>
	</ol>
</body>
</html>