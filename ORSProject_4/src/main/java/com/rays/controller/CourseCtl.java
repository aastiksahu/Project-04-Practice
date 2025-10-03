/**
 * CourseCtl servlet handles operations for adding and updating Course entities.
 * It includes validation, data population, and interaction with the CourseModel.
 * 
 * Supported operations: Save, Update, Cancel, Reset
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
import com.rays.bean.CourseBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CourseModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class CourseCtl Manages form handling for Course:
 * adding new courses and updating existing ones.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

	Logger log = Logger.getLogger(CourseCtl.class);

	/**
	 * Validates the input data from the course form.
	 * 
	 * @param request HttpServletRequest
	 * @return true if data is valid, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CourseCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("CourseCtl Validate Method Ended");

		return pass;
	}

	/**
	 * Populates the CourseBean with values from the request.
	 * 
	 * @param request HttpServletRequest
	 * @return populated CourseBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CourseCtl Populate Bean Method Started");

		CourseBean bean = new CourseBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		log.debug("CourseCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles HTTP GET requests to load the course form with existing data (if
	 * editing).
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CourseCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		CourseModel model = new CourseModel();

		if (id > 0 || op != null) {
			CourseBean bean;
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
		log.debug("CourseCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST requests for form submissions (Save, Update, Cancel,
	 * Reset).
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("CourseCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CourseModel model = new CourseModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course added successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course already exists", request);
			}
			
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			
			try {
				if (bean.getId() > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course updated successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course already exists", request);
			}
			
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		log.debug("CourseCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view path for the Course form.
	 * 
	 * @return String view path
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}
