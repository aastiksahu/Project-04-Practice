/**
 * FrontController.java
 * 
 * This Filter acts as a front controller for all requests matching the specified URL patterns.
 * It checks if a user session exists and forwards to the login page if the session has expired.
 * Otherwise, it allows the request to proceed.
 * 
 * Filter URL Patterns: /doc/*, /ctl/*
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rays.util.ServletUtility;

/**
 * Main Controller Perform Session Checking and Login Operation Before Calling
 * Any Controller It Prevents Any User To Access Application Without Login.
 * 
 * @author Aastik Sahu
 */
@WebFilter(filterName = "FrontController", urlPatterns = { "/doc/*", "/ctl/*" })
public class FrontController implements Filter {

	/**
	 * Initializes the filter. No specific initialization needed.
	 * 
	 * @param filterConfig The filter configuration
	 * @throws ServletException if an error occurs during initialization
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// No initialization required
	}

	/**
	 * Checks if the user session exists before allowing access to protected
	 * resources. If session is null, forwards the request to the login view with an
	 * error message. Otherwise, allows the request to continue through the filter
	 * chain.
	 * 
	 * @param request  ServletRequest object
	 * @param response ServletResponse object
	 * @param chain    FilterChain to pass control to the next filter or resource
	 * @throws IOException      if an I/O error occurs
	 * @throws ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		String uri = req.getRequestURI();
		req.setAttribute("uri", uri);

		if (session.getAttribute("user") == null) {
			req.setAttribute("error", "your session is expired. Please Login again!!");
			ServletUtility.forward(ORSView.LOGIN_VIEW, req, resp);
			return;
			
		} else {
			chain.doFilter(request, response);
		}

	}

	/**
	 * Destroys the filter. No specific cleanup needed.
	 */
	@Override
	public void destroy() {
		// No cleanup required
	}

}
