package com.rays.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.CollegeBean;
import com.rays.bean.StudentBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CollegeModel;
import com.rays.model.StudentModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * StudentCtl class performs the operations for adding, updating, validating,
 * and loading student data.
 * <p>
 * It handles HTTP GET and POST requests for student data, interacts with
 * StudentModel and CollegeModel for database operations, performs input
 * validations, and sets appropriate success or error messages.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(StudentCtl.class);

	/**
	 * Preloads gender map and list of colleges into the request before the view is
	 * rendered.
	 * 
	 * @param request HttpServletRequest object to set attributes
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("StudentCtl Preload Method Started");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Male", "Male");
		map.put("Female", "Female");
		request.setAttribute("map", map);

		CollegeModel collegeModel = new CollegeModel();
		try {
			List<CollegeBean> collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("StudentCtl Preload Method Ended");
	}

	/**
	 * Validates the input fields from the HTTP request.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return boolean true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("StudentCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("fristName", "Invalid First Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", "Invalid Last Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Moble No.");
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No. Must Have 10 Digits.");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College"));
			pass = false;
		}

		log.debug("StudentCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates StudentBean object from the HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return BaseBean populated StudentBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("StudentCtl Populate Bean Method Started");

		StudentBean bean = new StudentBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		populateDTO(bean, request);

		log.debug("StudentCtl Populate Bean Method Ended");
		
		return bean;

	}

	/**
	 * Handles HTTP GET requests to load student data by ID.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("StudentCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			StudentBean bean;

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
		log.debug("StudentCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests for adding or updating student data.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("StudentCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Successfully Added", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Student Already Exists", request);
			}
			
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			StudentBean bean = (StudentBean) populateBean(request);
			try {

				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Updated Successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Student Already Exists", request);
			}
			
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		log.debug("StudentCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view page of Student form.
	 * 
	 * @return String containing path of Student JSP view
	 */
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}
