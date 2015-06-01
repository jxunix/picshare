<%--
  Filename: recommend.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Sat 25 Apr 2015 11:42:42 PM EDT
--%>

<%@ page import="photoshare.RecommendDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.PhotoBean" %>
<%@ page import="photoshare.PhotoDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.StringBuffer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Recommendation</title>
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

	<h2>Recommendation</h2>
	<p>
	<form action="recommend.jsp" method="post">
		<textarea rows="4" cols="50" name="tagWord">Enter tags here...</textarea>
		<br/>
		<input type="submit" name="action" value="Recommend"/>
	</form>
	</p>

	<p>
	<%
		RecommendDao recDao = new RecommendDao();
		PhotoDao photoDao = new PhotoDao();
		String tagsPre = request.getParameter("tagWord");

		if (tagsPre != null) {
			String[] inTags = tagsPre.split("[-|\\.|,|\\r?\\n| ]+");
			List<String> outTags = recDao.getTag(inTags);
			if (outTags != null && outTags.size() != 0) {
				%> How about <b><%= outTags.get(0) %></b>? <%
			} else {
				%> Please try some other tags. <%
			}
		} else {
			%> Please enter some tags. <%
		}
	%>
	</p>

	<div class="split">
		<p>
		<h2>You may also like</h2>
		<%
			int num = 5;
			String userEmail = request.getUserPrincipal().getName(); 
			int ownerId = new UserDao().getIdByEmail(userEmail);

			List<Integer> photoIds = null;
			photoIds = recDao.getPhotoId(ownerId, num);
			if (photoIds != null) {
				for (Integer photoId: photoIds) {
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
			} else {
				photoIds = recDao.getPhotoId(ownerId, num - 1);
				if (photoIds != null) {
					for (Integer photoId: photoIds) {
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
				} else {
					photoIds = recDao.getPhotoId(ownerId, num - 2);
					if (photoIds != null) {
						for (Integer photoId: photoIds) {
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
					} else {
		%>
		Sorry we don't have any recommendation.
		<%
					}
				}
			}
		%>
		</p>
	</div>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
