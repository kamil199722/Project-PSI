<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	start
	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test" user="sa" password="" />
	<c:set var="select1" value="select id, name from test order by 1" />
	<c:set var="select2" value="insert into test (ID, NAME) values (3,'Damian')" />
	<c:out value="${select1}" />
	
	<br>
	
	<sql:query var="query1" dataSource="${db1}" sql="${select1}" ></sql:query>
	

	<table border="1">
		<tr>
			<th>Nazwisko</th>
			<th>Pensja</th>
			<th>Numer</th>
			<th>Kasuj</th>
		</tr>
		<c:forEach var="row" items="${query1.rows}">
			<tr>
				<td><c:out value="${row.ID}" /></td>
				<td><c:out value="${row.name}" /></td>
				<td><c:out value="${row.ID}" /></td>
				<td><a href="kasuj.jsp?x=${row.ID}">kasuj</a></td>
			</tr>
		</c:forEach>
	</table>
	stop
</body>
</html>