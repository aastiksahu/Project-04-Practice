package com.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.bean.BaseBean;
import com.rays.bean.UserBean;
import com.rays.util.DataUtility;
import com.rays.util.DataValidator;
import com.rays.util.ServletUtility;

/**
 * The {@code BaseCtl} class serves as a base controller for all servlets in the
 * application. It provides common constants, utility methods for validation,
 * preloading, bean population, and request handling.
 * <p>
 * Subclasses are expected to override {@code getView()} and may override other
 * methods as needed.
 * 
 * @author Aastik Sahu
 */
public abstract class BaseCtl extends HttpServlet {

	/** Operation constants */
	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_RESET = "Reset";

	/** Message constants */
	public static final String MSG_SUCCESS = "success";
	public static final String MSG_ERROR = "error";

	/**
	 * Validates the input data from request. Subclasses should override this method
	 * to implement custom validation logic.
	 * 
	 * @param request HTTP request
	 * @return true if validation passes, false otherwise
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Loads data required to display on HTML forms (like dropdowns). Subclasses
	 * should override this method to preload data.
	 * 
	 * @param request HTTP request
	 */
	protected void preload(HttpServletRequest request) {
	}

	/**
	 * Populates a bean from request parameters. Subclasses must override this to
	 * provide specific population logic.
	 * 
	 * @param request HTTP request
	 * @return populated bean
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	/**
	 * Populates DTO with system fields like createdBy, modifiedBy, and timestamps.
	 * 
	 * @param dto     the base DTO
	 * @param request HTTP request
	 * @return populated DTO
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			createdBy = "root";
			modifiedBy = "root";
		} else {
			modifiedBy = userbean.getLogin();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	/**
	 * Overrides the default {@code service} method of {@code HttpServlet}. Performs
	 * preloading, validation, and forwards the request to the view if validation
	 * fails.
	 * 
	 * @param request  HTTP request
	 * @param response HTTP response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("BaseCtl Service Started.");

		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {

			if (!validate(request)) {
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		
		System.out.println("BaseCtl Service Ended. Method : " + request.getMethod());
		
		super.service(request, response);
	}

	/**
	 * Abstract method to return the view (JSP/HTML) for the controller. Subclasses
	 * must implement this method.
	 * 
	 * @return the view path as a string
	 */
	protected abstract String getView();

}
