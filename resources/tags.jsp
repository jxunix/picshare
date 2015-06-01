<%--
  Filename: tags.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Fri 24 Apr 2015 08:42:55 PM EDT
--%>

<%@ page import="photoshare.TagOnBean" %>
<%@ page import="photoshare.TagOnDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>
<head>
<title>Tag management</title>
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

	<%
		String userEmail = request.getUserPrincipal().getName(); 
		int ownerId = new UserDao().getIdByEmail(userEmail);

		TagOnBean tagOn = new TagOnBean();
		TagOnDao tagOnDao = new TagOnDao();

		String whoseStr;
		whoseStr = request.getParameter("belong");
	%>
	
	<h2>All tags</h2>
	<form action="tags.jsp?belong=<%= whoseStr %>" method="get">
		<input type="radio" name="belong" value="your"/>View your tags
		<input type="radio" name="belong" value="all"/>View all tags
		<input type="submit" value="Choose"/>
	</form>

	<p>
	<%
		List<String> words = null;
		if (whoseStr != null && whoseStr.equals("all")) {
			words = tagOnDao.getAll();
		} else {
			words = tagOnDao.getByOwner(ownerId);
		}
		for (String word: words) {
	%>
	
	<div class="img">
		<a href="tag.jsp?belong=<%= whoseStr %>&wordPass=<%= word %>">
			<img src="./tagIcon.png" alt="tagIcon" width="90" height="90"/>
		</a>
		<div class="desc">
			<%= word %>
		</div>
	</div>

	<% } %>

	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
