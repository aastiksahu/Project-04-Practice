/**
 * FacultyListCtl.java
 * 
 * Controller to handle listing, searching, pagination, and deletion of Faculty records.
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.FacultyBean;
import com.rays.exception.ApplicationException;
import com.rays.model.FacultyModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class FacultyListCtl.
 * 
 * This controller handles the faculty list view operations, including: -
 * Displaying paginated list of faculty records - Searching faculty by name,
 * email - Deleting selected faculty records - Navigation via
 * next/previous/back/reset buttons
 * 
 * URL Mapping: /ctl/FacultyListCtl
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = "/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(FacultyListCtl.class);

	/**
	 * Populates FacultyBean with request parameters for search filtering.
	 * 
	 * @param request HttpServletRequest object
	 * @return populated FacultyBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("FacultyListCtl Populate Bean Method Started");

		FacultyBean bean = new FacultyBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		log.debug("FacultyListCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles GET requests to display the list of faculty records with pagination.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FacultyListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();

		try {
			List<FacultyBean> list = model.search(bean, pageNo, pageSize);
			List<FacultyBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("FacultyListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Handles POST requests for operations like search, delete, pagination, new,
	 * reset.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FacultyListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				
				if (ids != null && ids.length > 0) {
					FacultyBean deletebean = new FacultyBean();
					
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Faculty is deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("FacultyListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}		
	}

	/**
	 * Returns the path of the faculty list view page.
	 * 
	 * @return JSP view path for faculty list
	 */
	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}

}
