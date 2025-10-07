<%@page import="com.rays.controller.PatientCtl"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Patient</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.PATIENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.rays.bean.PatientBean" scope="request"></jsp:useBean>

		<%
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
				Patient
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
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Your Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Date of Visit<span	style="color: red">*</span></th>
					<td><input type="text" name="dateOfVisit" id="udatee"
						placeholder="Select Date of Visit"
						value="<%=DataUtility.getDateString(bean.getDateOfVisit())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dateOfVisit", request)%></font></td>
				</tr>
				
				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No."
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Disease<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("disease", String.valueOf(bean.getDisease()), map)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("disease", request)%></font></td>
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
						name="operation" value="<%=PatientCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=PatientCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PatientCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=PatientCtl.OP_RESET%>">
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