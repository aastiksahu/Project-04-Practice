package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.UserBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * MyProfileCtl is a controller class that handles the operations related to the
 * user's profile such as viewing and updating profile information.
 * <p>
 * It supports HTTP GET for displaying user profile details and HTTP POST for
 * updating profile or redirecting to change password.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = "/ctl/MyProfileCtl")
public class MyProfileCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(MyProfileCtl.class);

	public static final String OP_CHANGE_MY_PASSWORD = "Change Password";

	/**
	 * Validates the user input from the profile form. Checks for null or invalid
	 * data in first name, last name, gender, mobile number, and DOB.
	 * 
	 * @param request HttpServletRequest object containing the form data
	 * @return boolean true if input is valid, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("MyProfileCtl Validate Method Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}

		log.debug("MyProfileCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates a UserBean object from the HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing the form data
	 * @return BaseBean populated UserBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MyProfileCtl Populate Bean Method Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		populateDTO(bean, request);

		log.debug("MyProfileCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles the HTTP GET request to display the current user's profile data.
	 * Retrieves the user information from session and fetches from database.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MyProfileCtl Do Get Method Started");

		HttpSession session = request.getSession(true);
		UserBean user = (UserBean) session.getAttribute("user");
		long id = user.getId();

		UserModel model = new UserModel();

		if (id > 0) {
			try {
				UserBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("MyProfileCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles the HTTP POST request for updating profile or changing password. On
	 * update, the user's profile information is updated and success or error
	 * messages are set. On change password, redirects to Change Password
	 * controller.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MyProfileCtl Do Post Method Started");

		HttpSession session = request.getSession(true);

		UserBean user = (UserBean) session.getAttribute("user");
		long id = user.getId();

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			
			try {
				if (id > 0) {
					user.setFirstName(bean.getFirstName());
					user.setLastName(bean.getLastName());
					user.setGender(bean.getGender());
					user.setMobileNo(bean.getMobileNo());
					user.setDob(bean.getDob());
					model.update(user);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		log.debug("MyProfileCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view page for the My Profile page.
	 * 
	 * @return String view page path
	 */
	@Override
	protected String getView() {
		return ORSView.MY_PROFILE_VIEW;
	}
}
