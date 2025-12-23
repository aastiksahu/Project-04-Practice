<%@page import="com.rays.bean.RoleBean"%>
<%@page import="com.rays.controller.ORSView"%>
<html>
<head>
<title>Welcome to ORS</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.WELCOME_CTL%>">
		<%@ include file="Header.jsp"%>
		<br> <br> <br>
		<h1 align="center">
			<font size="10px" color="navy">Welcome to ORS 23-12-2025</font>
		</h1>

		<%
		UserBean beanUserBean = (UserBean) session.getAttribute("user");
		if (beanUserBean != null) {
			if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
		%>

		<h2 align="Center">
			<a style="color: maroon" href="<%=ORSView.GET_MARKSHEET_CTL%>">Click
				here to see your Marksheet </a>
		</h2>

		<%
		}
		}
		%>
		<%@ include file="Footer.jsp"%>
	</form>
</body>
</html>
