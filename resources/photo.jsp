<%--
	Filename: photo.jsp
	Author: Jun Xu
	Email: jxu.vimacs@gmail.com
	Created Time: Fri 24 Apr 2015 08:19:43 PM EDT
--%>

<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.LikeBean" %>
<%@ page import="photoshare.LikeDao" %>
<%@ page import="photoshare.TagOnBean" %>
<%@ page import="photoshare.TagOnDao" %>
<%@ page import="photoshare.CommentBean" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Photo</title>
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
		UserDao userDao = new UserDao();

		String userEmail = request.getUserPrincipal().getName(); 
		int userId = userDao.getIdByEmail(userEmail);

		int photoId = 0;
		String photoIdStr = request.getParameter("photoIdPass");
		if (photoIdStr != null) {
			photoId = Integer.parseInt(photoIdStr);
		}

		TagOnDao tagOnDao = new TagOnDao();
		TagOnBean tagOn = new TagOnBean();
	%>

	<div style="width: 100%;">
		<div style="float:left; width: 50%">
			<img src="/photoshare/img?photoIdPass=<%= photoId %>" alt="photo"  height="450"/>
		</div>
		<div style="float:left;">

			<%
				tagOn = tagOn.create(request, photoId);
				if (tagOn != null) {
					tagOnDao.create(tagOn);
				}
			%>

			<p>
			<form action="photo.jsp?photoIdPass=<%= photoId %>" method="post">
				<tr>
					<td>Create tag</td>&nbsp;
					<td><input type="text" name="tagWord"/></td>
					<td><input type="hidden" name="action" value="Create"/></td>
				</tr>
			</form>
			</p>

			<p>
			<%
				List<String> words = tagOnDao.getByPhotoId(photoId);
				for (String word: words) {
			%>
			
			<a id="navi" href="photo.jsp?wordDel=<%= word %>&photoIdPass=<%= photoId %>" style="float:left;">
				<input type="button" value="<%= word %>">&nbsp;
			</a>

			<%
				}

				String word = request.getParameter("wordDel");
				if (word != null) {
					TagOnBean tagOnDel = new TagOnBean();
					tagOnDel.setWord(word);
					tagOnDel.setPhotoId(photoId);
					tagOnDao.delete(tagOnDel);
					String urlPass = "photo.jsp?photoIdPass=" + photoId;
					response.sendRedirect(urlPass);
				}
			%>
			</p>

			<p>
			<br/>
			<br/>

			<%
				LikeBean like = new LikeBean();
				LikeDao likeDao = new LikeDao();

				String flagStr;
				flagStr = request.getParameter("likeFlag");
				if (flagStr != null && flagStr.equals("true")) {
					like.setUserId(userId);
					like.setPhotoId(photoId);
					likeDao.create(like);
				}

				int cnt = likeDao.getCntByPhotoId(photoId);
			%>
			 
			<div class="split">
				<a id="navi" href="photo.jsp?photoIdPass=<%= photoId %>&likeFlag=<%= true %>">
					<img src="./likeIcon.png" alt="like" width="40"/>+<%= cnt %>
				</a>
			</div>
			</p>

			<p>
			<br/>
			<br/>
			
			<%
				CommentBean comment = new CommentBean();
				CommentDao commentDao = new CommentDao();
				int ownerId = new PhotoDao().getOwner(photoId);

				comment = comment.create(request, ownerId, photoId);
				if (comment != null) {
					commentDao.create(comment);
				}
			%>

			<p>
			<br/>
			<%
				List<CommentBean> comments = commentDao.getByPhotoId(photoId);
				for (CommentBean com: comments) {
			%>
			<%= userDao.get(com.getUserId()).getFirstName() %>:&nbsp;<%= com.getText() %><br/>
			<% } %>
			</p>

			<p>
			<form action="photo.jsp?photoIdPass=<%= photoId %>" method="post">
				<tr>
					<td><input type="text" name="commentText"/></td>
					<td><input type="hidden" name="action" value="Create"/></td>
				</tr>
			</form>
			</p>
			 
			<div class="split">
			</div>
			</p>

		</div>
	</div>
	<div style="clear:both"></div>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
