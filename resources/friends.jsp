<%--
  Filename: friends.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Fri 24 Apr 2015 03:27:16 PM EDT
--%>

<%@ page import="photoshare.FriendBean" %>
<%@ page import="photoshare.FriendDao" %>
<%@ page import="photoshare.UserBean" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Friends</title>
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

	<h2>Find friends</h2>
	<p>
	<form>
		<tr>
			<td>Name</td>
			<td><input type="text" name="frdname"/></td>
			<td><input type="submit" name="action" value="Search"/></td>
		</tr>
	</form>
	</p>

	<%
		String userEmail = request.getUserPrincipal().getName();
		int userId = new UserDao().getIdByEmail(userEmail);

		FriendBean friend = new FriendBean();
		FriendDao friendDao = new FriendDao();
		UserBean user = new UserBean();
		UserDao userDao = new UserDao();

		String frdName = request.getParameter("frdname");
		List<UserBean> addFriends = userDao.getByFirstName(frdName);
		for (UserBean frd: addFriends) {
			if (frd.getId() == userId) {
				continue;
			}
	%>
		
	<p>
	<div class="img">
		<a id="add" href="friends.jsp?friendIdAdd=<%= frd.getId() %>">
			<img src="./addFriendIcon.png" alt="add friend" width="90" height="90"/>
		</a>
		<div class="desc">
			<%= frd.getFirstName() + " " + frd.getLastName() %>
		</div>
	</div>
	
	<%
		}

		addFriends = userDao.getByLastName(frdName);
		for (UserBean frd: addFriends) {
			if (frd.getId() == userId) {
				continue;
			}
	%>

	<div class="img">
		<a id="add" href="friends.jsp?friendIdAdd=<%= frd.getId() %>">
			<img src="./addFriendIcon.png" alt="add friend" width="90" height="90"/>
		</a>
		<div class="desc">
			<%= frd.getFirstName() + " " + frd.getLastName() %>
		</div>
	</div>
	</p>

	<%
		}
	%>
	
	<div class="split">
	
	<h2>All friends</h2>
	<p>
	<%
		List<FriendBean> friends = friendDao.getByUserId(userId);
		for (FriendBean frd: friends) {
			user = userDao.get(frd.getFriendId());
	%>
		
	<div class="img">
		<img src="./friendIcon.png" alt="friend" width="90" height="90"/>
		<div class="desc">
			<a id="del" href="friends.jsp?friendIdDel=<%= frd.getFriendId() %>">
				<%= user.getFirstName() + " " + user.getLastName() %>
			</a>
		</div>
	</div>
		
	<%
		}

		String friendIdAddStr = request.getParameter("friendIdAdd");
		if (friendIdAddStr != null) {
			FriendBean friendAdd = new FriendBean();
			friendAdd.setUserId(userId);
			friendAdd.setFriendId(Integer.parseInt(friendIdAddStr));
			friendDao.create(friendAdd);
			response.sendRedirect("friends.jsp");
		}

		String friendIdDelStr = request.getParameter("friendIdDel");
		if (friendIdDelStr != null) {
			FriendBean friendDel = new FriendBean();
			friendDel.setUserId(userId);
			friendDel.setFriendId(Integer.parseInt(friendIdDelStr));
			friendDao.delete(friendDel);
			response.sendRedirect("friends.jsp");
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
