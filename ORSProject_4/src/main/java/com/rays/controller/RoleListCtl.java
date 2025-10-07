package com.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.RoleBean;
import com.rays.exception.ApplicationException;
import com.rays.model.RoleModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * RoleListCtl class handles the listing, searching, pagination, and deletion
 * operations for Role entities.
 * <p>
 * It loads the list of roles, supports search criteria, pagination and provides
 * methods to delete selected roles.
 * </p>
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(RoleListCtl.class);

	/**
	 * Preloads the role list before the view is rendered.
	 * 
	 * @param request HttpServletRequest object to set attributes
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("RoleListCtl Preload Method Started");

		RoleModel roleModel = new RoleModel();

		try {
			List roleList = roleModel.list();
			request.setAttribute("roleList", roleList);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			return;
		}

		log.debug("RoleListCtl Preload Method Ended");
	}

	/**
	 * Populates RoleBean object from HTTP request parameters.
	 * 
	 * @param request HttpServletRequest object containing form data
	 * @return BaseBean populated RoleBean object
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("RoleListCtl Populate Bean Method Started");

		RoleBean bean = new RoleBean();

		// bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("roleId")));

		log.debug("RoleListCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET requests for loading the role list with pagination support.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("RoleListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();

		try {
			List<RoleBean> list = model.search(bean, pageNo, pageSize);
			List<RoleBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("RoleListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Handles HTTP POST requests for searching, pagination, deleting, and resetting
	 * the role list.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException in case of Servlet errors
	 * @throws IOException      in case of I/O errors
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("RoleListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		RoleBean bean = (RoleBean) populateBean(request);
		RoleModel model = new RoleModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;

				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					RoleBean deletebean = new RoleBean();

					for (String id : ids) {

						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data is Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select At Least One Record", request);
				}
				
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
				
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("RoleListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}	
	}

	/**
	 * Returns the view page of Role list.
	 * 
	 * @return String containing path of Role list JSP
	 */
	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}

}
