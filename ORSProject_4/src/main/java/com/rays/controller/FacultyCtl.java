/**
 * FacultyCtl.java
 * 
 * Controller to handle operations related to Faculty data such as
 * create, update, validate and preload lists (College, Course, Subject).
 * 
 * @author Aastik Sahu
 */
package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.FacultyBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CollegeModel;
import com.rays.model.CourseModel;
import com.rays.model.FacultyModel;
import com.rays.model.SubjectModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class FacultyCtl. This controller manages the
 * add/update operations for Faculty entity. It also performs validation and
 * preloads dropdown data for College, Course, and Subject.
 * 
 * Mapped to /ctl/FacultyCtl
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "FacultyCtl", urlPatterns = "/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(FacultyCtl.class);

	/**
	 * Loads the list of colleges, subjects, and courses to be shown in the form
	 * dropdowns.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("FacultyCtl Preload Method Started");

		CollegeModel collegeModel = new CollegeModel();
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);

			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("FacultyCtl Preload Method Ended");
	}

	/**
	 * Validates the input fields of the faculty form.
	 * 
	 * @param request HttpServletRequest object
	 * @return true if all fields are valid, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("FacultyCtl Validate Method Started");

		boolean pass = true;

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

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No must have 10 digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Invalid Mobile No");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		
		log.debug("FacultyCtl Validate Method Ended");

		return pass;
	}

	/**
	 * Populates a FacultyBean object from the request parameters.
	 * 
	 * @param request HttpServletRequest object
	 * @return FacultyBean populated with form data
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("FacultyCtl Populate Bean Method Started");

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populateDTO(bean, request);

		log.debug("FacultyCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles GET requests to load Faculty data for update.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FacultyCtl Do Get Method Started");


		long id = DataUtility.getLong(request.getParameter("id"));

		FacultyModel model = new FacultyModel();

		if (id > 0) {
			try {
				FacultyBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		log.debug("FacultyCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST requests for Save, Update, Reset, and Cancel operations.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FacultyCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		FacultyModel model = new FacultyModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Faculty added successfully", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already exists", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Faculty updated successfully", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Email already exists", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		
		log.debug("FacultyCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view to be rendered by this controller.
	 * 
	 * @return path of faculty view JSP
	 */
	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}
}