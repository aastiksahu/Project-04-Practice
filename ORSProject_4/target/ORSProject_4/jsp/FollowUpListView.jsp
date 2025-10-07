<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rays.controller.FollowUpListCtl"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.bean.FollowUpBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Follow Up List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Follow
			Up List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<jsp:useBean id="bean" class="com.rays.bean.FollowUpBean"
			scope="request"></jsp:useBean>

		<form action="<%=ORSView.FOLLOW_UP_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<FollowUpBean> patientList = (List<FollowUpBean>) request.getAttribute("patientList");
			List<FollowUpBean> doctorList = (List<FollowUpBean>) request.getAttribute("doctorList");

			List<FollowUpBean> list = (List<FollowUpBean>) ServletUtility.getList(request);
			Iterator<FollowUpBean> it = list.iterator();

			if (list.size() != 0) {
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center">
						<label><b>Patient Name :</b></label> <%=HTMLUtility.getList("patientId", String.valueOf(bean.getPatientId()), patientList)%>&emsp;
						<label><b>Doctor Name :</b></label> <%=HTMLUtility.getList("doctorId", String.valueOf(bean.getDoctorId()), doctorList)%>&emsp;
						<%-- <label><b>Visit Date :</b></label></td><td align="left"><input type="text" name="visitDate" placeholder="Select Date of Visit" value="<%=ServletUtility.getParameter("visitDate", request)%>"></label>&emsp; --%> 
						<label><b>Fees :</b></label><input type="number" name="fees" placeholder="Enter Fees"	value="<%=(bean.getFees() != 0) ? ServletUtility.getParameter("fees", request) : ""%>"></label>&emsp;
						<input type="submit" name="operation" value="<%=FollowUpListCtl.OP_SEARCH%>">&nbsp; 
						<input type="submit" name="operation" value="<%=FollowUpListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th style="width: 5%;"><input type="checkbox" id="selectall" /></th>
					<th style="width: 5%;">S.No</th>
					<th style="width: 20%;">Patient Name</th>
					<th style="width: 20%;">Doctor Name</th>
					<th style="width: 20%;">Visit Date</th>
					<th style="width: 20%;">Fees</th>
					<th style="width: 10%;">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = it.next();
				%>
				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getPatientName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getDoctorName()%></td>
					<%
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					String date = sdf.format(bean.getVisitDate());
					%>
					<td style="text-align: center;"><%=date%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getFees()%></td>
					<td style="text-align: center;"><a href="FollowUpCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
				}
				%>
			</table>
			<table style="width: 100%">
				<tr>

					<td style="width: 25%"><input type="submit" name="operation" value="<%=FollowUpListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit" name="operation" value="<%=FollowUpListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit" name="operation" value="<%=FollowUpListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit" name="operation" value="<%=FollowUpListCtl.OP_NEXT%>" <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>
			<%
			}
			if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation" value="<%=FollowUpListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
			}
			%>

		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>