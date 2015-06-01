<%--
	Filename: loginerror.jsp
	Author: Jun Xu
	Email: jxu.vimacs@gmail.com
	Created Time: Thu 23 Apr 2015 12:09:12 AM EDT
--%>

<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.Contribution" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Stars</title>
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

	<p>
	<%
		UserDao userDao = new UserDao();
			UserBean user = null;
		List<Contribution> contributions = userDao.getContributions();
		if (contributions.size() <= 10) {
			for (Contribution contri: contributions) {
				user = userDao.get(contri.getUserId());
	%>
	<div class="img">
		<img src="./friendIcon.png" alt="star" width="90" height="90"/>
		<div class="desc">
			<%= user.getFirstName() + " " + user.getLastName() %><br/>
			<%= contri.getContribution() %>
		</div>
	</div>
	<%
			}

			for (int i = 0; i < 10 - contributions.size(); i++) {
	%>
	<div class="img">
		<img src="./friendIcon.png" alt="star" width="90" height="90"/>
		<div class="desc">
			empty<br/>
			--
		</div>
	</div>
	<%
			}
		} else {
			for (int i = 0; i < 10; i++) {
				Contribution contri = contributions.get(i);
				user = userDao.get(contri.getUserId());
	%>
	<div class="img">
		<img src="./friendIcon.png" alt="star" width="90" height="90"/>
		<div class="desc">
			<%= user.getFirstName() + " " + user.getLastName() %><br/>
			<%= contri.getContribution() %>
		</div>
	</div>
	<%
			}
		}
	%>

	</p>
</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
