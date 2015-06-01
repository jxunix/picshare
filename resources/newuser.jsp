<%--
	Filename: newuser.jsp
	Author: Jun Xu
	Email: jxu.vimacs@gmail.com
	Created Time: Thu 23 Apr 2015 12:15:26 AM EDT
--%>

<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>New user</title>
</head>

<body>

<div id="header">
	<h1><a id="title" href="index.jsp">PicShare</a></h1>
</div>

<div id="nav">
</div>

<div id="section">

	<%
		boolean success = false;
		String err = null;

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String gender = request.getParameter("gender");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String hmtnLocId = request.getParameter("hmtnLocId");
		String currLocId = request.getParameter("currLocId");
		String education = request.getParameter("education");

		UserBean user = new UserBean();
		UserDao userDao = new UserDao();

		if (firstName == null || firstName.equals("")) {
			err = "First name cannot be empty";
		} else if (lastName == null || lastName.equals("")) {
			err = "Last name cannot be empty";
		} else if (email == null || email.equals("")) {
			err = "Email cannot be empty";
		} else if (dob == null || dob.equals("")) {
			err = "Birthday cannot be empty";
		} else if (password1 == null || password1.equals("")) {
			err = "Password cannot be empty";
		} else if (password2 == null || password2.equals("")) {
			err = "Password cannot be empty";
		} else {
			if (!password1.equals(password2)) {
				err = "Both password strings must match";
			} else if (password1.length() < 4) {
				err = "Your password must be at least four characters long";
			} else {
				user = user.create(request);
				if (user == null) {
					err = "Couldn't create user";
				} else {
					success = userDao.create(user);
					if (!success) {
						err = "Couldn't create user (that email may already be in use)";
					}
				}
			}
		}
	%>

	<% if (err != null) { %>
	<font color=red><b>Error: <%= err %></b></font>
	<% } %>

	<% if (!success) { %>

	<h2>Create a new account</h2>
	<p>
	<form action="newuser.jsp" method="post">
		<table>
			<tr>
				<td align="left">First Name</td>
				<td><input type="text" name="firstName"/></td>
			</tr>
			<tr>
				<td align="left">Last Name</td>
				<td><input type="text" name="lastName"/></td>
			</tr>
			<tr>
				<td align="left">Email</td>
				<td><input type="text" name="email"/></td>
			</tr>
			<tr>
				<td align="left">Birthday</td>
				<td><input type="date" name="dob"/></td>
			</tr>
			<tr>
				<td align="left">Gender</td>
				<td><input type="radio" name="gender" value="male"/>male
				<input type="radio" name="gender" value="female"/>female</td>
			</tr>
			<tr>
				<td align="left">Password</td>
				<td><input type="password" name="password1"/></td>
			</tr>
			<tr>
				<td align="left">Re-enter password</td>
				<td><input type="password" name="password2"/></td>
			</tr>
			<tr>
				<td align="left">Hometown</td>
				<td><input type="text" name="hmtnLocId"/></td>
			</tr>
			<tr>
				<td align="left">Current Location</td>
				<td><input type="text" name="currLocId"/></td>
			</tr>
			<tr>
				<td align="left">Education</td>
				<td><input type="text" name="education"/></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<a href="login.jsp"><input type="button" value="Back"/></a>
					<input type="submit" value="Create"/>
				</td>
			</tr>
		</table>
	</form>
	</p>

	<% } else { %>

	<h2>Success!</h2>
	<p>
	A new user has been created with email <%= email %>.
	You can now return to the <a href="login.jsp">login page</a>.
	</p>

	<% } %>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
