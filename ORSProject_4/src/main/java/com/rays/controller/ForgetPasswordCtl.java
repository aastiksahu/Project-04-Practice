/**
 * ForgetPasswordCtl.java
 * 
 * Controller for handling "Forget Password" functionality.
 * Allows users to request a password reset by entering their registered email.
 * If the email exists, the password is sent to the user's email address.
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
import com.rays.bean.UserBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.RecordNotFoundException;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class ForgetPasswordCtl.
 * 
 * This controller validates the email input and interacts with the model to
 * send the password to the user's registered email address.
 * 
 * URL Mapping: /ForgetPasswordCtl
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = "/ForgetPasswordCtl")
public class ForgetPasswordCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	/**
	 * Validates the input email for null and correct format.
	 *
	 * @param request HttpServletRequest object
	 * @return true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("ForgetPasswordCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		log.debug("ForgetPasswordCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates UserBean with the email provided in the form.
	 *
	 * @param request HttpServletRequest object
	 * @return UserBean containing the login (email)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("ForgetPasswordCtl Populate Bean Method Started");

		UserBean bean = new UserBean();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		log.debug("ForgetPasswordCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles GET requests and forwards the user to the forget password view.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("ForgetPasswordCtl Do Get Method Started");
		
		ServletUtility.forward(getView(), request, response);
		
		log.debug("ForgetPasswordCtl Do Get Method Ended");
	}

	/**
	 * Handles POST requests for forget password operation. Sends password to
	 * registered email if the email is valid.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("ForgetPasswordCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UserBean bean = (UserBean) populateBean(request);

		UserModel model = new UserModel();

		if (OP_GO.equalsIgnoreCase(op)) {
			
			try {
				boolean flag = model.forgetPassword(bean.getLogin());
				
				if (flag) {
					ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
				}
				
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
				ServletUtility.handleException(e, request, response);
				
			}
			log.debug("ForgetPasswordCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);
		}	
	}

	/**
	 * Returns the path to the forget password JSP view.
	 *
	 * @return view path for forget password page
	 */
	@Override
	protected String getView() {
		return ORSView.FORGET_PASSWORD_VIEW;
	}
}
