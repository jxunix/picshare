<%--
  Filename: logout.jsp
  Author: Jun Xu
  Email: jxu.vimacs@gmail.com
  Created Time: Thu 23 Apr 2015 12:12:25 AM EDT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="styles.css"/>

<html>

<head>
<title>Logout</title>
</head>

<body>

<div id="header">
	<h1><a id="title" href="index.jsp">PicShare</a></h1>
</div>

<div id="nav">
</div>

<div id="section">

	<h2>Logged out</h2>
	<% session.invalidate(); %>
	<p>
	<a href="/photoshare/index.jsp">Login again</a>
	</p>

</div>

<div id="footer">
	Copyright © 2015 Jun Xu
</div>

</body>
</html>
