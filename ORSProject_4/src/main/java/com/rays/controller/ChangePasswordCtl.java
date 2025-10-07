/**
 * ChangePasswordCtl Servlet handles the logic for allowing users to change their password.
 * It validates input fields, ensures old and new passwords are different, and updates the password
 * using the UserModel.
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
import com.rays.bean.UserBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.RecordNotFoundException;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class ChangePasswordCtl Handles password change
 * functionality.
 *
 * @author Aastik Sahu
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = "/ctl/ChangePasswordCtl")
public class ChangePasswordCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(ChangePasswordCtl.class);

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	/**
	 * Validates the input parameters for changing the password.
	 *
	 * @param request HttpServletRequest
	 * @return boolean indicating whether validation passed or failed
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("ChangePasswordCtl Validate Method Started");

		boolean pass = true;

		String op = request.getParameter("operation");

		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		} else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Old and New passwords should be different");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New and confirm passwords not matched");
			pass = false;
		}

		log.debug("ChangePasswordCtl validate Method Ended");
		return pass;
	}

	/**
	 * Populates the UserBean with request parameters.
	 *
	 * @param request HttpServletRequest
	 * @return BaseBean populated UserBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("ChangePasswordCtl Populated Bean Method Started");

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		populateDTO(bean, request);

		log.debug("ChangePasswordCtl Populated Bean Method Ended");
		return bean;
	}

	/**
	 * Handles GET requests to forward to the view.
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("ChangePasswordCtl doGet Method Started");
		
		ServletUtility.forward(getView(), request, response);
		
		log.debug("ChangePasswordCtl doGet Method Ended");
	}

	/**
	 * Handles POST requests for password change operation.
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("ChangePasswordCtl doPost Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		String newPassword = (String) request.getParameter("newPassword");

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		HttpSession session = request.getSession(true);
		UserBean user = (UserBean) session.getAttribute("user");
		long id = user.getId();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
				if (flag == true) {
					bean = model.findByLogin(user.getLogin());
					session.setAttribute("user", bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
				}
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Old Password is Invalid", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		log.debug("ChangePasswordCtl doPost Method Ended");
		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
	}

	/**
	 * Returns the view to be rendered.
	 *
	 * @return String view path
	 */
	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}
}
