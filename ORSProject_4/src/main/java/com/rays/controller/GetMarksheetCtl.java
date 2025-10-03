/**
 * GetMarksheetCtl.java
 * 
 * This servlet handles the retrieval of a marksheet based on the provided roll number.
 * It validates the roll number input, fetches the marksheet details using the model,
 * and forwards the request to the appropriate view.
 * 
 * URL Pattern: /ctl/GetMarksheetCtl
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;

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
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class GetMarksheetCtl Responsible for getting
 * marksheet details using roll number.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(GetMarksheetCtl.class);

	/**
	 * Validates the input parameters of the request. Checks if the roll number is
	 * provided.
	 * 
	 * @param request HttpServletRequest object containing the client request
	 * @return true if validation passes; false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("GetMarksheetCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}

		log.debug("GetMarksheetCtl Validate Method Ended");

		return pass;
	}

	/**
	 * Populates the MarksheetBean from the HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing the client request
	 * @return populated MarksheetBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("GetMarksheetCtl Populate Bean Method Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		log.debug("GetMarksheetCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles the HTTP GET request by forwarding to the marksheet view.
	 * 
	 * @param request  HttpServletRequest object containing the client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("GetMarksheetCtl Do Get Method Started");
		
		ServletUtility.forward(getView(), request, response);
		
		log.debug("GetMarksheetCtl Do Get Method Ended");
	}

	/**
	 * Handles the HTTP POST request. Processes the form submission for retrieving
	 * marksheet based on roll number. On success, sets the bean in request; on
	 * failure, sets error message.
	 * 
	 * @param request  HttpServletRequest object containing the client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("GetMarksheetCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		if (OP_GO.equalsIgnoreCase(op)) {
	
			try {
				bean = model.findByRollNo(bean.getRollNo());
				if (bean != null) {
					ServletUtility.setBean(bean, request);
				} else {
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
				}
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("GetMarksheetCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view URI of the marksheet page.
	 * 
	 * @return String view URI
	 */
	@Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}
}
