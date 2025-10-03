<%@page import="com.rays.model.CollegeModel"%>
<%@page import="com.rays.controller.CollegeListCtl"%>
<%@page import="com.rays.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.util.DataUtility"%>
<%@page import="com.rays.util.ServletUtility"%>
<%@page import="com.rays.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>College List View</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="com.rays.bean.CollegeBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">College
			List</h1>
		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post">
			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<CollegeBean> collegeList = (List<CollegeBean>) request.getAttribute("collegeList");

			List<CollegeBean> list = (List<CollegeBean>) ServletUtility.getList(request);
			Iterator<CollegeBean> it = list.iterator();

			if (list.size() != 0) {
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>College Name : </b></label><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getId()), collegeList)%>&nbsp;
						<label><b>City :</b></label> <input type="text" name="city"
						placeholder="Enter city"
						value="<%=ServletUtility.getParameter("city", request)%>">&nbsp;
						<input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_SEARCH%>">&nbsp; <input
						type="submit" name="operation"
						value="<%=CollegeListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="20%">Name</th>
					<th width="20%">Address</th>
					<th width="20%">State</th>
					<th width="15%">City</th>
					<th width="10%">Phone No.</th>
					<th width="5%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					bean = (CollegeBean) it.next();
				%>
				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getAddress()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getState()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getCity()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getPhoneNo()%></td>
					<td style="text-align: center;"><a
						href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
				}
				%>
			</table>
			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CollegeListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CollegeListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=CollegeListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>
			<%
			}
			if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_BACK%>"></td>
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