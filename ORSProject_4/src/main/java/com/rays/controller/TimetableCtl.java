package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.TimeTableBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CourseModel;
import com.rays.model.SubjectModel;
import com.rays.model.TimeTableModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * TimetableCtl controller class to handle Add/Edit operations for Timetable.
 * Manages validation, bean population, and CRUD operations through model.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "TimetableCtl", urlPatterns = "/ctl/TimetableCtl")
public class TimetableCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(TimetableCtl.class);

	/**
	 * Preloads the required data for course and subject dropdowns.
	 * 
	 * @param request HttpServletRequest
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("TimetableCtl Preload Method Started");
		
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("TimetableCtl Preload Method Ended");
	}

	/**
	 * Validates the user input from the form.
	 * 
	 * @param request HttpServletRequest
	 * @return boolean indicating if validation passed or failed
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("TimetableCtl Validate Method Started");
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Date of Exam"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Date of Exam"));
			pass = false;
		} else if (DataValidator.isSunday(request.getParameter("examDate"))) {
			request.setAttribute("examDate", "Exam should not be on Sunday");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
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

		log.debug("TimetableCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates the TimeTableBean from the request parameters.
	 * 
	 * @param request HttpServletRequest
	 * @return populated TimeTableBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("TimetableCtl Populate Bean Method Started");

		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		System.out.println("courseId " + bean.getCourseId());
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populateDTO(bean, request);

		log.debug("TimetableCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET request to fetch Timetable by ID and load the form.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("TimetableCtl Do Get Method Started");

		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();

		if (id > 0) {
			try {
				TimeTableBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("TimetableCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles HTTP POST request for Save, Update, Cancel, and Reset operations.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("TimetableCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		TimeTableModel model = new TimeTableModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			TimeTableBean bean1;
			TimeTableBean bean2;
			TimeTableBean bean3;

			try {
				bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());
				bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubjectId(), bean.getExamDate());
				bean3 = model.checkBySemester(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate());

				if (bean1 == null && bean2 == null && bean3 == null) {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Timetable added successfully", request);
					
				} else {
					bean = (TimeTableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Timetable already exist!", request);
				}
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Timetable already exist!", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			TimeTableBean bean4;

			try {
				bean4 = model.checkByExamTime(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate(), bean.getExamTime(), bean.getDescription());

				if (id > 0 && bean4 == null) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Timetable updated successfully", request);
					
				} else {
					bean = (TimeTableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Timetable already exist!", request);
				}
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Timetable already exist!", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		log.debug("TimetableCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view path for the timetable form.
	 * 
	 * @return String path to the timetable view
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}
}
