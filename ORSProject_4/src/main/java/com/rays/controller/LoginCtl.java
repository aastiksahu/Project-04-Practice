/**
 * LoginCtl.java
 * 
 * This servlet handles user login, logout, and redirection.
 * It validates user credentials, authenticates users,
 * manages session attributes, and redirects users accordingly.
 * 
 * URL Pattern: /LoginCtl
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.RoleBean;
import com.rays.bean.UserBean;
import com.rays.model.RoleModel;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class LoginCtl Handles user login, logout, and
 * redirection. Validates login form inputs and authenticates user.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(LoginCtl.class);

	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";
	public static final String OP_LOG_OUT = "Log Out";

	/**
	 * Validates the login form input parameters.
	 * 
	 * @param request HttpServletRequest object containing the client request
	 * @return true if validation succeeds; false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("LoginCtl Validate Method Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		// Skip validation for sign up and logout operations
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		
		log.debug("LoginCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates the UserBean from the HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing the client request
	 * @return populated UserBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("LoginCtl Populate Bean Method Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET request. Supports logout functionality by invalidating
	 * session and showing logout message. Forwards to login view.
	 * 
	 * @param request  HttpServletRequest object containing the client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("LoginCtl Do Get Method Started");

		HttpSession session = request.getSession();

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op)) {
			session.invalidate();
			ServletUtility.setSuccessMessage("Logout Successfully", request);
			ServletUtility.forward(getView(), request, response);
			return;
		}
		log.debug("LoginCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST request. Processes login and sign-up operations.
	 * Authenticates user credentials and manages session attributes. Redirects
	 * users based on login success or sign-up operation.
	 * 
	 * @param request  HttpServletRequest object containing the client request
	 * @param response HttpServletResponse object for sending response
	 * @throws ServletException if servlet error occurs
	 * @throws IOException      if I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("LoginCtl Do Post Method Started");

		HttpSession session = request.getSession();
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		RoleModel roleModel = new RoleModel();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {

					session.setAttribute("user", bean);
					RoleBean roleBean = roleModel.findByPk(bean.getRoleId());

					if (roleBean != null) {
						session.setAttribute("role", roleBean.getName());
					}

					String uri = (String) request.getParameter("uri");

					if (uri == null || "null".equalsIgnoreCase(uri)) {
						
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;

					} else {

						ServletUtility.redirect(uri, request, response);
						return;
					}
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid Login Id or Password", request);
				}

			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		log.debug("LoginCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view URI of the login page.
	 * 
	 * @return String view URI
	 */
	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
