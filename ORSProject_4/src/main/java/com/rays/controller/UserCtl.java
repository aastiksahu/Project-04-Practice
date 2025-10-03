package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.RoleBean;
import com.rays.bean.UserBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.RoleModel;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller class to handle User operations such as add, update, reset, and
 * cancel. Handles HTTP GET and POST methods for User data.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(UserCtl.class);

	/**
	 * Loads the Role list and sets it in the request scope.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("UserCtl Preload Method Started");
		
		RoleModel model = new RoleModel();
		try {
			List<RoleBean> roleList = model.list();
			request.setAttribute("roleList", roleList);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("UserCtl Preload Method Ended");
	}

	/**
	 * Validates input data from the form.
	 * 
	 * @param request HttpServletRequest object
	 * @return true if all validations pass, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("UserCtl Validate Method Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");
		String password = request.getParameter("password");

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

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		if (DataValidator.isNull(password)) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(password)) {
			request.setAttribute("password", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(password)) {
			request.setAttribute("password", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role"));
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
		
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Password and Confirm Password must be Same!");
			pass = false;
		}

		log.debug("UserCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates UserBean from request parameters.
	 * 
	 * @param request HttpServletRequest object
	 * @return Populated UserBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("UserCtl Populate Bean Method Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateDTO(bean, request);

		log.debug("UserCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET requests. Loads a user for update based on ID.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("UserCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			UserBean bean;

			try {
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("UserCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests. Performs save, update, reset, and cancel
	 * operations.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("UserCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User added successfully", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				if (bean.getId() > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		log.debug("UserCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view for UserCtl.
	 * 
	 * @return User view path
	 */
	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
