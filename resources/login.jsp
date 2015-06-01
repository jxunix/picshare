<%--
	Filename: login.jsp
	Author: Jun Xu
	Email: jxu.vimacs@gmail.com
	Created Time: Thu 23 Apr 2015 12:03:21 AM EDT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Login</title>
</head>

<body>

<div id="header">
	<h1><a id="title" href="index.jsp">PicShare</a></h1>
</div>

<div id="nav">
</div>

<div id="section">

	<h2>Please login</h2>
	<p>
	<form method="POST" action="j_security_check">
		<table>
			<tr>
				<th>Email</th>
				<td><input type="text" name="j_username"></td>
			</tr>
			<tr>
				<th>Password</th>
				<td><input type="password" name="j_password"></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<a href="newuser.jsp"><input type="button" value="New User"/></a>
					<input type="submit" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
