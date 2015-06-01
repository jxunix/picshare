<%--
  Filename: albums.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Tue 21 Apr 2015 06:37:02 PM EDT
--%>

<%@ page import="photoshare.AlbumBean" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>
<head>
<title>Album management</title>
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
		AlbumDao albumDao = new AlbumDao();
		AlbumBean album = new AlbumBean();

		album = album.create(request);
		if (album != null) {
			albumDao.create(album);
		}

		String userEmail = request.getUserPrincipal().getName(); 
		int ownerId = new UserDao().getIdByEmail(userEmail);
	%>

	<h2>Create a album</h2>
	<p>
	<form action="albums.jsp" method="post">
		<tr>
			<td>Name</td>
			<td><input type="text" name="albumName"/></td>
			<td><input type="submit" name="action" value="Create"/></td>
		</tr>
	</form>
	</p>

	<h2>All albums</h2>
	<p>
	<%
		List<AlbumBean> albums = albumDao.getByOwnerId(ownerId);
		for (AlbumBean al: albums) {
	%>
	
	<div class="img">
		<a href="album.jsp?albumIdPass=<%= al.getId() %>">
			<img src="./albumIcon.png" alt="albumIcon" width="110" height="90"/>
		</a>
		<div class="desc">
			<a id="del" href="albums.jsp?albumIdDel=<%= al.getId() %>">
				<%= al.getName() %>
			</a>
		</div>
	</div>
	<%
		}

		String albumIdStr = request.getParameter("albumIdDel");
		if (albumIdStr != null) {
			albumDao.delete(Integer.parseInt(albumIdStr));
			response.sendRedirect("albums.jsp");
		}
	%>
	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
