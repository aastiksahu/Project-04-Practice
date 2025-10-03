package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.MarksheetBean;
import com.rays.exception.ApplicationException;
import com.rays.model.MarksheetModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * MarksheetListCtl is a controller class to handle list, search, pagination,
 * deletion, and navigation operations on Marksheet entities.
 * <p>
 * It supports HTTP GET for displaying the list and POST for processing user
 * actions such as search, next, previous, new, delete, reset, and back.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

	Logger log = Logger.getLogger(MarksheetListCtl.class);

	/**
	 * Populates MarksheetBean with request parameters for search filtering.
	 * 
	 * @param request HttpServletRequest object containing request parameters
	 * @return BaseBean populated MarksheetBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MarksheetListCtl Populate Bean Method Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));

		log.debug("MarksheetListCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles HTTP GET method to load marksheet list with pagination. Sets
	 * attributes for the view including list data, pagination info, and any
	 * messages.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel model = new MarksheetModel();

		try {
			List<MarksheetBean> list = model.search(bean, pageNo, pageSize);
			List<MarksheetBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("MarksheetListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Handles HTTP POST method for various operations such as search, pagination,
	 * delete, reset, and navigation related to marksheet list.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel model = new MarksheetModel();

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
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					MarksheetBean deletebean = new MarksheetBean();

					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Marksheet is deleted successfully", request);
					}

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
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

			log.debug("MarksheetListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns the view (JSP page) path for marksheet list.
	 * 
	 * @return String view page path
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}
}
