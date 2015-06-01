<%--
  Filename: tag.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Sat 25 Apr 2015 11:21:33 AM EDT
--%>

<%@ page import="photoshare.TagOnBean" %>
<%@ page import="photoshare.TagOnDao" %>
<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<%
	String userEmail = request.getUserPrincipal().getName(); 
	int ownerId = new UserDao().getIdByEmail(userEmail);
	String word = request.getParameter("wordPass");

	TagOnBean tagOn = new TagOnBean();
	TagOnDao tagOnDao = new TagOnDao();
	PhotoDao photoDao = new PhotoDao();
%>

<html>

<head>
<title>Tag <%= word %></title>
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

	<h2>Existing photos</h2>
	<%
		String whoseStr;
		whoseStr = request.getParameter("belong");
	%>

	<form action="tag.jsp?belong=<%= whoseStr %>&wordPass=<%= word %>" method="post">
		<input type="radio" name="belong" value="your"/>View your photos
		<input type="radio" name="belong" value="all"/>View all photos
		<input type="submit" value="Choose"/>
	</form>

	<p>
		<%
			List<PhotoBean> photos = null;
			if (whoseStr != null && whoseStr.equals("all")) {
				photos = photoDao.getByTag(word);
			} else {
				photos = photoDao.getByOwnerTag(ownerId, word);
			}

			for (PhotoBean pic : photos) {
		%>
		<div class="img">
			<a href="photo.jsp?photoIdPass=<%= pic.getId() %>">
				<img src="/photoshare/img?t=1&photoIdPass=<%= pic.getId() %>" alt="thumb" width="110" height="90"/>
			</a>
			<div class="desc">
				<a id="del" href="tag.jsp?wordPass=<%= word %>&photoIdDel=<%= pic.getId() %>">
					<%= pic.getCaption() %>
				</a>
			</div>
		</div>
		<%
			}

			String photoIdStr = request.getParameter("photoIdDel");
			if (photoIdStr != null) {
				TagOnBean tagDel = new TagOnBean();
				tagDel.setWord(word);
				tagDel.setPhotoId(Integer.parseInt(photoIdStr));
				tagOnDao.delete(tagDel);
				String urlPass = "tag.jsp?wordPass=" + word;
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
