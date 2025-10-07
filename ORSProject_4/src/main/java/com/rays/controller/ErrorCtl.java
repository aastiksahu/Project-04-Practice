/**
 * ErrorCtl.java
 * 
 * Controller to handle application-level errors and forward
 * the user to a predefined error view.
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.util.ServletUtility;

/**
 * ErrorCtl Servlet implementation class. Handles GET and POST requests by
 * forwarding to the error view. This controller is mapped to "/ErrorCtl".
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "ErrorCtl", urlPatterns = "/ErrorCtl")
public class ErrorCtl extends BaseCtl {

	/**
	 * Handles HTTP GET request. Forwards the request to the error view.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an input or output error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST request. Forwards the request to the error view.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an input or output error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the path of the error view.
	 *
	 * @return String containing the path to error view
	 */
	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW;
	}

}
