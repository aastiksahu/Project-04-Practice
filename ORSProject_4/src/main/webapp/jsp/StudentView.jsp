<%@page import="com.rays.controller.StudentCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Student</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.STUDENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.rays.bean.StudentBean"
			scope="request"></jsp:useBean>

		<%
		List<CollegeBean> collegeList = (List<CollegeBean>) request.getAttribute("collegeList");
		HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("map");
		%>
		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
				if (bean != null && bean.getId() > 0) {
				%>Update<%
				} else {
				%>Add<%
				}
				%>
				Student
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">First Name<span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Last Name<span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Email Id<span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email ID"
						value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Date of Birth<span style="width: 98%"
						style="color: red">*</span></th>
					<td><input type="text" name="dob" id="udate"
						placeholder="Select Date of Birth"
						value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("gender", bean.getGender(), map)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>

				<tr>
					<th align="left">College<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), collegeList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No."
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<%
					if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=StudentCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=StudentCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>">
						<%
						}
						%>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>