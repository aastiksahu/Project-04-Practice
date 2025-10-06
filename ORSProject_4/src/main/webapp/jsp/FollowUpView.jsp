<%@page import="com.rays.controller.FollowUpCtl"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.bean.FollowUpBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Follow Up</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.FOLLOW_UP_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="com.rays.bean.FollowUpBean"
			scope="request"></jsp:useBean>

		<%
		List<FollowUpBean> patientList = (List<FollowUpBean>) request.getAttribute("patientList");
		List<FollowUpBean> doctorList = (List<FollowUpBean>) request.getAttribute("doctorList");
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
				Follow Up
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
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
					<th align="left">Patient<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("patientId", String.valueOf(bean.getPatientId()), patientList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("patientId", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Doctor<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("doctorId", String.valueOf(bean.getDoctorId()), doctorList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("doctorId", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Visit Date<span style="color: red">*</span></th>
					<td><input type="text" id="udatee" name="visitDate"
						placeholder="Select Visit Date"
						value="<%=DataUtility.getDateString(bean.getVisitDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("visitDate", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Fees<span style="color: red">*</span></th>
					<td><input type="number" name="fees" placeholder="Enter Fees"
						value="<%=(bean.getFees() != 0) ? DataUtility.getStringData(bean.getFees()) : ""%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("fees", request)%></font>
					</td>
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
						name="operation" value="<%=FollowUpCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=FollowUpCtl.OP_CANCEL%>">
						<%
						} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=FollowUpCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=FollowUpCtl.OP_RESET%>">
						<%
						}
						%>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>