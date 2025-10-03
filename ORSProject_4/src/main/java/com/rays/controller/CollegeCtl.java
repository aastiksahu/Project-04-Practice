/**
 * CollegeCtl servlet handles Create and Update operations for College entities.
 * It validates form inputs and interacts with the CollegeModel to perform database operations.
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
import com.rays.bean.CollegeBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.CollegeModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class CollegeCtl. Performs operations such as Add,
 * Update, and Reset on the College form.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

	Logger log = Logger.getLogger(CollegeCtl.class);

	/**
	 * Validates the input fields for the College form.
	 * 
	 * @param request the HttpServletRequest object
	 * @return boolean indicating whether validation passed or failed
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CollegeCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid College Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Invalid Phone No.");
			pass = false;
		} else if (!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", "Phone No. Must Be 10 Digits");
			pass = false;
		}

		log.debug("CollegeCtl Validate Method Ended");
		
		return pass;
	}

	/**
	 * Populates the CollegeBean with values from the request.
	 * 
	 * @param request the HttpServletRequest object
	 * @return BaseBean populated CollegeBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CollegeCtl Populate Bean Method Started");

		CollegeBean bean = new CollegeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

		populateDTO(bean, request);

		log.debug("CollegeCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles GET requests to load the form for College.
	 * 
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CollegeCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModel model = new CollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			CollegeBean bean;
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
		log.debug("CollegeCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST requests for Save, Update, Cancel, and Reset operations.
	 * 
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("CollegeCtl Do Post Method Started");	

		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModel model = new CollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			CollegeBean bean = (CollegeBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Successfully saved", request);
				
			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Already Exists", request);
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			CollegeBean bean = (CollegeBean) populateBean(request);

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
				ServletUtility.setErrorMessage("College Already Exists", request);
			}
			
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
			
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
			
		}
		log.debug("CollegeCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view path for the College form.
	 * 
	 * @return String representing the view path
	 */
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
