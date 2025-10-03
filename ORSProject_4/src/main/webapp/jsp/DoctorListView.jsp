<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.rays.controller.DoctorListCtl"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.bean.DoctorBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Doctor List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@ include file="Header.jsp"%>
	<jsp:useBean id="bean" class="com.rays.bean.DoctorBean" scope="request"></jsp:useBean>
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">Doctor
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.DOCTOR_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("map");
			List<DoctorBean> list = (List<DoctorBean>) ServletUtility.getList(request);

			Iterator<DoctorBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Name :</b></label> <input
						type="text" name="name" placeholder="Enter Your Name"
						value="<%=ServletUtility.getParameter("name", request)%>">&nbsp;

						<%-- <label><b>Date Of Birth:</b></label> <input type="text" name="dob"
						placeholder="Enter your Date of Birth"
						value="<%=ServletUtility.getParameter("dob", request)%>">&nbsp; --%>

						<label><b>Mobile No. :</b></label> <input type="text"
						name="mobileNo" placeholder="Enter Your Mobile No."
						value="<%=ServletUtility.getParameter("mobileNo", request)%>">&nbsp;

						<label><b>Expertise : </b></label> <%=HTMLUtility.getList("expertise", String.valueOf(bean.getExpertise()), map)%>&nbsp;


						<input type="submit" name="operation"
						value="<%=DoctorListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=DoctorListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">Name</th>
					<th width="10%">Date of Birth</th>
					<th width="10%">Mobile No</th>
					<th width="8%">Expertise</th>
					<th width="5%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = (DoctorBean) it.next();

					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					String date = sdf.format(bean.getDob());
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getName()%></td>
					<td style="text-align: center;"><%=date%></td>
					<td style="text-align: center;"><%=bean.getMobileNo()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getExpertise()%></td>
					<td style="text-align: center;"><a
						href="<%=ORSView.DOCTOR_CTL%>?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=DoctorListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=DoctorListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=DoctorListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=DoctorListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=DoctorListCtl.OP_BACK%>"></td>
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