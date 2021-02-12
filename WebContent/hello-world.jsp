<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Strona użytkownika</title>
</head>
<body>



	<%
		String user = request.getParameter("user");
		String password = request.getParameter("password");

	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	%>

	<h3>
		Witaj
		<%=user%>
		!
	</h3>

	<span style="color: red"><%=error_message%></span>
	<br>

	<HR>

	<form action="ZmianaHasla.jsp" method="POST">
		<table border="0">
			<tr>
				<td></td>
				<td><input type="hidden" name="user" value="<%=user%>">
				<td><input type="submit" value="Zmień hasło " />
				<td></td>
			</tr>
		</table>
	</form>


	<form action="Wyloguj" method="POST">
		<table border="0">
			<tr>
				<td></td>
				<td><input type="hidden" name="user" value="<%=user%>">
				<td><input type="submit" value="Wyloguj" />
				<td></td>
			</tr>
		</table>
	</form>




	<HR>

	<sql:setDataSource var="db1" driver="org.h2.Driver"
		url="jdbc:h2:tcp://localhost/~/test" user="sa" password="" />




	<sql:query dataSource="${db1}" var="query1">
	SELECT * FROM PERSONS where user =?
	<sql:param value="${param.user}" />
	</sql:query>

	<br> 
	Twoje aktualne dane logowania
	<br>
	<br>
	<table border="1">
		<tr>
			
			<th>Nazwa użytkownika</th>
			<th>Hasło</th>
			<th>Ustawione</th>
			<th>Wygasa</th>
			<th>Możliwa zmiana</th>
		</tr>
		<c:forEach var="row" items="${query1.rows}">
			<tr width="100%">
				
				<td><c:out value="${row.USER}" /></td>
				<td><c:out value="${row.PASSWORD}" /></td>
				<td><c:out value="${row.DATA_USTAWIENIA}" /></td>
				<td><c:out value="${row.DATA_WYGASNIECIA}" /></td>
				<td><c:out value="${row.DATA_NOWE}" /></td>
			</tr>
		</c:forEach>
	</table>

<%int i=0; %>

<sql:query dataSource="${db1}" var="query2">
	SELECT * FROM PASSWORDHISTORY where user =? order by data_ustawienia desc limit 1,20 
	<sql:param value="${param.user}" />
	</sql:query>

	<HR>
	<br> 
	Twoje wcześniej używane hasła
	<br>
	<br>
	<table border="1">
		<tr>
			<th>Lp.</th>
			<th>Nazwa użytkownika</th>	
			<th>Hasło</th>
			<th>Ustawione</th>
		</tr>
		<c:forEach var="row" items="${query2.rows}">
			<tr width="100%">
			<%;
			i=i+1;%>
				<td><c:out value="<%=i %>" /></td>
				<td><c:out value="${row.USER}" /></td>
				<td><c:out value="${row.PASSWORD}" /></td>
				<td><c:out value="${row.DATA_USTAWIENIA}" /></td>
			</tr>
		</c:forEach>
	</table>


<sql:query dataSource="${db1}" var="query3">
	SELECT * FROM LOGOWANIA where user =? order by ID desc limit 15
	<sql:param value="${param.user}" />
	</sql:query>

<%int j=0; %>

	<HR>
	<br> 
	Historia logowań użytkownika <%=user %>
	<br>
	<br>
	<table border="1">
		<tr>
			<th>Lp.</th>
			<th>Nazwa użytkownika</th>	
			<th>Data logowania</th>
			<th>Data wylogowania</th>
			<th>Czy udane?</th>
		</tr>
		<c:forEach var="row" items="${query3.rows}">
			<tr width="100%">
			<%;
			j=j+1;%>
				<td><c:out value="<%=j %>" /></td>
				<td><c:out value="${row.USER}" /></td>
				<td><c:out value="${row.Data_logowania}" /></td>
				<td><c:out value="${row.Data_wylogowania}" /></td>
				<td><c:out value="${row.Stan}" /></td>
			</tr>
		</c:forEach>
	</table>


</body>
</html>