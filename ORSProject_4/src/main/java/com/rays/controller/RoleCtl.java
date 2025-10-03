package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.RoleBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.RoleModel;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * RoleCtl class is a controller servlet that handles the CRUD operations for
 * the Role entity.
 * <p>
 * It performs validation, data population, and controls the flow of Role data
 * between the view and the model.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {

	Logger log = Logger.getLogger(RoleCtl.class);

	/**
	 * Validates the input data from the Role form.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return boolean true if validation passes, false otherwise
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("RoleCtl Validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Invalid Role Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("RoleCtl Validate Method Ended");

		return pass;
	}

	/**
	 * Populates a RoleBean object from the HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return BaseBean populated RoleBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("RoleCtl Populate Bean Method Started");

		RoleBean bean = new RoleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		log.debug("RoleCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles GET requests to load a role by its primary key and forward to view.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("RoleCtl Do Get Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		RoleModel model = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			RoleBean bean;
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
		log.debug("RoleCtl Do Get Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST requests to add, update, reset or cancel role operations.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("RoleCtl Do Post Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		RoleModel model = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			RoleBean bean = (RoleBean) populateBean(request);
			try {
				// Add new role
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully Saved", request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role Already Exists", request);
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			RoleBean bean = (RoleBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Is Successfully Updated", request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Role Already Exists", request);
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		}
		log.debug("RoleCtl Do Post Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view page of Role.
	 * 
	 * @return String containing path of Role view JSP
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}

}
