package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.MarksheetBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.MarksheetModel;
import com.rays.model.StudentModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * MarksheetCtl is a controller class to handle Marksheet related HTTP requests
 * and responses. It extends BaseCtl to leverage common controller
 * functionalities.
 * 
 * <p>
 * This class handles preload of students list, validation of input data,
 * population of MarksheetBean from request parameters, and processes GET and
 * POST requests for add, update, cancel, and reset operations related to
 * marksheets.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(MarksheetCtl.class);

	/**
	 * Loads the list of students and sets it in the request attribute to be used in
	 * the view.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("MarksheetCtl Preload Method Started");
		
		StudentModel model = new StudentModel();
		
		try {
			List studentList = model.list();
			request.setAttribute("studentList", studentList);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			return;
		}
		
		log.debug("MarksheetCtl Preload Method Ended");
	}

	/**
	 * Validates the input data from the request for marksheet form. Checks for
	 * null, integer validations, range validations (0-100) and roll number format.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return boolean true if all validations pass, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("MarksheetCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Roll No is invalid");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("physics")) > 100
				|| DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100
				|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) > 100
				|| DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}
		
		log.debug("MarksheetCtl Validate Method Ended");

		return pass;
	}

	/**
	 * Populates MarksheetBean from the HttpServletRequest parameters.
	 * 
	 * @param request HttpServletRequest containing form data
	 * @return BaseBean populated MarksheetBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MarksheetCtl Populate Bean Method Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));

		if (request.getParameter("physics") != null && request.getParameter("physics").length() != 0) {
			bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
			System.out.println("in");
		}
		if (request.getParameter("chemistry") != null && request.getParameter("chemistry").length() != 0) {
			bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		}
		if (request.getParameter("maths") != null && request.getParameter("maths").length() != 0) {
			bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		}

		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		populateDTO(bean, request);
		
		log.debug("MarksheetCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles GET requests. Loads marksheet by id if present and forwards to the
	 * view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MarksheetCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		MarksheetModel model = new MarksheetModel();

		if (id > 0 || op != null) {
			MarksheetBean bean;
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
		log.debug("MarksheetCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST requests for add, update, cancel and reset operations on
	 * marksheet.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("MarksheetCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet added successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll No already exists", request);
			}
			
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			
			try {
				if (bean.getId() > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet updated successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll No already exists", request);
				
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}
		log.debug("MarksheetCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view (JSP page) for marksheet.
	 * 
	 * @return String view page path
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}
}
