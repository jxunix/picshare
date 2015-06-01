<%--
  Filename: album.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Thu 23 Apr 2015 08:08:04 PM EDT
--%>

<%@ page import="photoshare.AlbumBean" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.ImageUploadBean" %>
<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<%
	int albumId = 0;
	String albumIdStr = request.getParameter("albumIdPass");
	if (albumIdStr != null) {
		albumId = Integer.parseInt(albumIdStr);
	}

	AlbumBean album = new AlbumBean();
	AlbumDao albumDao = new AlbumDao();
	PhotoDao photoDao = new PhotoDao();
%>

<html>

<head>
<title>Album <%= albumDao.get(albumId).getName() %></title>
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

	<h2>Upload a photo</h2>
	<p>
	<form action="album.jsp?albumIdPass=<%= albumId %>" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td>Filename</td>
				<td><input type="file" name="filename"/></td>
				<td/>
			</tr>
			<tr>
				<td>Caption</td>
				<td><input type="text" name="caption"/></td>
				<td><input type="submit" value="Upload"/><br/></td>
			</tr>
		</table>
	</form>
	</p>

	<%
		try {
			PhotoBean photo = new ImageUploadBean().upload(request, albumId);
			if (photo != null) {
				photoDao.save(photo);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	%>

	<h2>All photos</h2>
	<p>
		<%
			List<PhotoBean> photos = photoDao.getByAlbumId(albumId);
			for (PhotoBean pic : photos) {
		%>
		<div class="img">
			<a href="photo.jsp?photoIdPass=<%= pic.getId() %>">
				<img src="/photoshare/img?t=1&photoIdPass=<%= pic.getId() %>" alt="thumb" width="110" height="90"/>
			</a>
			<div class="desc">
				<a id="del" href="album.jsp?albumIdPass=<%= albumId %>&photoIdDel=<%= pic.getId() %>">
					<%= pic.getCaption() %>
				</a>
			</div>
		</div>
		<%
			}

			String photoIdStr = request.getParameter("photoIdDel");
			if (photoIdStr != null) {
				photoDao.delete(Integer.parseInt(photoIdStr));
				String urlPass = "album.jsp?albumIdPass=" + albumId;
				response.sendRedirect(urlPass);
			}
		%>
	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
