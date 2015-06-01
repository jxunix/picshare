<%--
  Filename: index.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Wed 22 Apr 2015 11:06:42 PM EDT
--%>

<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Welcome</title>
</head>

<body>

<div id="header">
	<h1><a id="title" href="index.jsp">PicShare</a></h1>
</div>

<div id="nav">
	<a id="navi" href="friends.jsp">Friends</a><br>
	<a id="navi" href="albums.jsp">Albums</a><br>
	<a id="navi" href="photos.jsp">Photos</a><br>
	<a id="navi" href="tags.jsp">Tags</a><br>
	<a id="navi" href="gallery.jsp">Gallery</a><br>
	<a id="navi" href="stars.jsp">Stars</a><br>
	<a id="navi" href="recommend.jsp">Recommend</a><br>
</div>

<div id="section">

	<h2>Welcome!</h2>
	<p>

	<%
		UserDao userDao = new UserDao();
		String email = request.getUserPrincipal().getName();
		int userId = userDao.getIdByEmail(email);
		UserBean user = userDao.get(userId);
	%>
	 
	Hello <b><code><%= user.getFirstName() %></code></b>, click here to
	<a href="/photoshare/logout.jsp">log out</a>.

	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
