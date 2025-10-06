package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.MarksheetBean;
import com.rays.exception.ApplicationException;
import com.rays.model.MarksheetModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * MarksheetMeritListCtl is a controller class to handle displaying the merit
 * list of marksheets with pagination support.
 * <p>
 * It supports HTTP GET to fetch and display the merit list and POST for
 * handling navigation operations.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(MarksheetMeritListCtl.class);

	/**
	 * Handles HTTP GET method to fetch the merit list of marksheets. Sets the list,
	 * pagination attributes, and error messages if no data found.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MarksheetMeritListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		MarksheetModel model = new MarksheetModel();

		try {
			List<MarksheetBean> list = model.getMeritList(pageNo, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			log.debug("MarksheetMeritListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Handles HTTP POST method for operations such as back navigation.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MarksheetMeritListCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			return;
		}
		
		log.debug("MarksheetMeritListCtl Do Post Method Ended");
	}

	/**
	 * Returns the view (JSP page) path for the marksheet merit list.
	 * 
	 * @return String view page path
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}
}
