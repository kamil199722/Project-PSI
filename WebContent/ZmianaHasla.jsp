<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Zmiana Hasła</title>
</head>
<body>
	<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	
	String user = request.getParameter("user");
	%>
	
	<h2>Zmiana hasła użytkownika <%=user %>:</h2>
	
	<form action="ZmianaHasla" method="POST">
	<input type= "hidden" name="user" value="<%=user%>"  >
		<span style="color: red"><%=error_message%></span>
		<br>
		<br>
		<table border="1">
		<tr>
			<td>Wprowadz nowe hasło</td>
			<td><input type= "password" name="password1"  >
			</td>
		</tr>
		<tr>
			<td>Powtórz hasło</td>
			<td><input type ="password"name="password2"  >
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<input type ="submit"value="Zapisz" />
			<a href="hello-world.jsp?user=<%=user%>">Cancel</a>
			</td>
		</tr>
		</table>
	</form>
</body>