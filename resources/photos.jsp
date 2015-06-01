<%--
  Filename: photos.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Thu 23 Apr 2015 03:36:39 PM EDT
--%>

<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Photo management</title>
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

	<h2>All photos</h2>
	<p>
		<%
			String userEmail = request.getUserPrincipal().getName(); 
			int ownerId = new UserDao().getIdByEmail(userEmail);

			List<PhotoBean> photos = photoDao.getByOwnerId(ownerId);
			for (PhotoBean pic : photos) {
		%>
		<div class="img">
			<a href="photo.jsp?photoIdPass=<%= pic.getId() %>">
				<img src="/photoshare/img?t=1&photoIdPass=<%= pic.getId() %>" alt="thumb" width="110" height="90"/>
			</a>
			<div class="desc">
				<a id="del" href="photos.jsp?photoIdDel=<%= pic.getId() %>">
					<%= pic.getCaption() %></a>
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
