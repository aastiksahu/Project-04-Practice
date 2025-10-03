/**
 * FollowUpCtl class handles HTTP requests for Follow-Up operations.
 * This servlet controller manages follow-up related operations including
 * displaying follow-up forms, adding new follow-ups, updating existing ones,
 * and handling user input validation.
 * 
 * @author Aastik Sahu
 * @version 1.0
 * @package com.rays.controller
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
import com.rays.bean.FollowUpBean;
import com.rays.exception.ApplicationException;
import com.rays.model.DoctorModel;
import com.rays.model.FollowUpModel;
import com.rays.model.PatientModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

@WebServlet(name = "FollowUpCtl", urlPatterns = { "/ctl/FollowUpCtl" })
public class FollowUpCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(FollowUpCtl.class);
	
	/**
     * Preloads patient and doctor lists for dropdown menus before rendering the form.
     * 
     * @param request HttpServletRequest object
     */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("FollowUpCtl Preload Method Started");
		
		PatientModel patientModel = new PatientModel();
		DoctorModel doctorModel = new DoctorModel();

		try {
			List patientList = patientModel.list();
			request.setAttribute("patientList", patientList);

			List doctorList = doctorModel.list();
			request.setAttribute("doctorList", doctorList);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("FollowUpCtl Preload Method Ended");
	}
	
	/**
     * Validates user input from the follow-up form.
     * 
     * @param request HttpServletRequest object containing form data
     * @return boolean true if validation passes, false otherwise
     */
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("FollowUpCtl Validate Method Started");
		
		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("patientId"))) {
			request.setAttribute("patientId", PropertyReader.getValue("error.require", "Patient Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("doctorId"))) {
			request.setAttribute("doctorId", PropertyReader.getValue("error.require", "Doctor Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.require", "Date of Visit"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.date", "Date of Visit"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "Fees"));
			pass = false;
		}
		
		log.debug("FollowUpCtl Validate Method Ended");
		return pass;
	}
	
	/**
     * Populates FollowUpBean object with data from HTTP request parameters.
     * 
     * @param request HttpServletRequest object containing form data
     * @return BaseBean populated FollowUpBean object
     */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("FollowUpCtl Populate Bean Method Started");
		
		FollowUpBean bean = new FollowUpBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatientId(DataUtility.getLong(request.getParameter("patientId")));
		bean.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
		bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
		bean.setFees(DataUtility.getLong(request.getParameter("fees")));
		
		populateDTO(bean, request);

		log.debug("FollowUpCtl Populate Bean Method Ended");
		return bean;
	}
	
	/**
     * Handles HTTP GET requests for displaying follow-up form.
     * Loads existing follow-up data when editing.
     * 
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FollowUpCtl Do Get Method Started");		

		long id = DataUtility.getLong(request.getParameter("id"));

		FollowUpModel model = new FollowUpModel();

		if (id > 0) {
			try {
				FollowUpBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		log.debug("FollowUpCtl Do Get Method Ended");		
		ServletUtility.forward(getView(), request, response);
	}
	
	/**
     * Handles HTTP POST requests for follow-up operations.
     * Processes save, update, cancel, and reset operations.
     * 
     * @param request HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FollowUpCtl Do Post Method Started");	
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		FollowUpModel model = new FollowUpModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			FollowUpBean bean = (FollowUpBean) populateBean(request);
			
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Follow Up added successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			FollowUpBean bean = (FollowUpBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Follow Up updated successfully", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOW_UP_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOW_UP_CTL, request, response);
			return;
		}
		
		log.debug("FollowUpCtl Do Post Method Ended");	
		ServletUtility.forward(getView(), request, response);
	}

	/**
     * Returns the view path for the follow-up form.
     * 
     * @return String representing the view path
     */
	@Override
	protected String getView() {
		return ORSView.FOLLOW_UP_VIEW;
	}

	
}