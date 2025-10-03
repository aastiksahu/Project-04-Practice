package com.rays.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.bean.BaseBean;
import com.rays.controller.BaseCtl;
import com.rays.controller.ORSView;

/**
 * Servlet Utility class to provide utility methods commonly used in servlets
 * for handling requests, responses, setting attributes, and forwarding or
 * redirecting.
 * 
 * @author Aastik Sahu
 */
public class ServletUtility {

	/**
	 * Forwards request to the specified view.
	 *
	 * @param page     the target page
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	/**
	 * Redirects to the specified page.
	 *
	 * @param page     the page URL
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect(page);
	}

	/**
	 * Gets the error message from the request attribute.
	 *
	 * @param property the key for the error attribute
	 * @param request  the HttpServletRequest object
	 * @return error message string or empty string if not found
	 */
	public static String getErrorMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Gets the general message from the request attribute.
	 *
	 * @param property the key for the message attribute
	 * @param request  the HttpServletRequest object
	 * @return message string or empty string if not found
	 */
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Sets an error message in the request scope.
	 *
	 * @param msg     the error message
	 * @param request the HttpServletRequest object
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	/**
	 * Gets the error message from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return error message string or empty string
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		return (val == null) ? "" : val;
	}

	/**
	 * Sets a success message in the request scope.
	 *
	 * @param msg     the success message
	 * @param request the HttpServletRequest object
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Gets the success message from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return success message string or empty string
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		return (val == null) ? "" : val;
	}

	/**
	 * Stores a bean in the request scope.
	 *
	 * @param bean    the bean to set
	 * @param request the HttpServletRequest object
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	/**
	 * Retrieves the bean from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return the BaseBean object or null
	 */
	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	/**
	 * Gets a parameter value from request.
	 *
	 * @param property the parameter name
	 * @param request  the HttpServletRequest object
	 * @return parameter value or empty string
	 */
	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		return (val == null) ? "" : val;
	}

	/**
	 * Sets a list in the request scope.
	 *
	 * @param list    the list to store
	 * @param request the HttpServletRequest object
	 */
	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	/**
	 * Gets the list from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return the list or null
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Sets the page number in the request scope.
	 *
	 * @param pageNo  the page number
	 * @param request the HttpServletRequest object
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Gets the page number from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return the page number
	 */
	public static int getPageNo(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageNo");
	}

	/**
	 * Sets the page size in the request scope.
	 *
	 * @param pageSize the page size
	 * @param request  the HttpServletRequest object
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Gets the page size from request scope.
	 *
	 * @param request the HttpServletRequest object
	 * @return the page size
	 */
	public static int getPageSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageSize");
	}

	/**
	 * Handles exceptions by setting the exception in request scope and redirecting
	 * to error page.
	 *
	 * @param e        the Exception object
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
	}
}
