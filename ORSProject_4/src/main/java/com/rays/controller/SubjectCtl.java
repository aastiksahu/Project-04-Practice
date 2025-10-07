package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.SubjectBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CourseModel;
import com.rays.model.SubjectModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller class to handle Subject operations like Add, Update, and input
 * validation. This class manages interactions between the Subject form and
 * SubjectModel.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(SubjectCtl.class);

	/**
	 * Loads Course data to preload into the form dropdown.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("SubjectCtl Preload Method Started");
		
		CourseModel model = new CourseModel();
		try {
			List courseList = model.list();
			request.setAttribute("courseList", courseList);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			return;
		}
		
		log.debug("SubjectCtl Preload Method Ended");
	}

	/**
	 * Validates the form input fields.
	 * 
	 * @param request HttpServletRequest containing user inputs
	 * @return boolean indicating success or failure of validation
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("SubjectCtl Validate Method Started");
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("SubjectCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates the SubjectBean from request parameters.
	 * 
	 * @param request HttpServletRequest
	 * @return SubjectBean with data filled from the form
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("SubjectCtl Populate Bean Method Started");
		
		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);
		
		log.debug("SubjectCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles HTTP GET requests for loading the subject form in edit/view mode.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("SubjectCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectModel model = new SubjectModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			SubjectBean bean;
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
		log.debug("SubjectCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests for form operations: Save, Update, Reset, Cancel.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("SubjectCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectModel model = new SubjectModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			SubjectBean bean = (SubjectBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject added successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			SubjectBean bean = (SubjectBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Subject updated successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Subject Name already exists", request);
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		log.debug("SubjectCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view path of the Subject form.
	 * 
	 * @return String view path
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}
