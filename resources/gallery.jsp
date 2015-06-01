<%--
  Filename: gallery.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Thu 23 Apr 2015 03:36:39 PM EDT
--%>

<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Gallery</title>
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
		PhotoDao photoDao = new PhotoDao();
	%>

	<h2>All photos at PicShare</h2>
	<p>
		<%
			List<Integer> photoIds = photoDao.allPhotoIds();
			for (Integer photoId : photoIds) {
		%>
		<div class="img">
			<a href="photo.jsp?photoIdPass=<%= photoId %>">
				<img src="/photoshare/img?t=1&photoIdPass=<%= photoId %>" alt="thumb" width="110" height="90"/>
			</a>
			<div class="desc">
				<%= photoDao.load(photoId).getCaption() %>
			</div>
		</div>
		<%
			}

			String photoIdStr = request.getParameter("photoIdDel");
			if (photoIdStr != null) {
				photoDao.delete(Integer.parseInt(photoIdStr));
				response.sendRedirect("photos.jsp");
			}
		%>
	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
