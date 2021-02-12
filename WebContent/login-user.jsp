<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proces logowania</title>
</head>
<body>
	<h1>Logowanie do serwisu </h1>
	<%
		String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString(); 
	
	%>
	
	<form action="HelloServlet" method="POST">
		<span style="color: red"><%=error_message%></span>
		<br>
		<br>
		<table border="1">
		<tr>
			<td>Nazwa użytkownia</td>
			<td><input type= "text" name="user" value= "${user.userName}" >
			</td>
		</tr>
		<tr>
			<td>Hasło</td>
			<td><input type ="password"name="password" value="${user.password}" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<input type ="submit"value="Zaloguj" />
			</td>
		</tr>	
		</table>
	</form>
</body>
</html>