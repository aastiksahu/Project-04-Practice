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
import com.rays.bean.UserBean;
import com.rays.exception.ApplicationException;
import com.rays.model.RoleModel;
import com.rays.model.UserModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller to handle operations for listing users including pagination,
 * search, delete, and navigation to new user creation.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(UserListCtl.class);

	/**
	 * Loads the role list into request scope to be displayed in the search filter.
	 * 
	 * @param request HttpServletRequest object
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("UserListCtl Preload Method Started");
		
		RoleModel roleModel = new RoleModel();
		try {
			List<RoleBean> roleList = roleModel.list();
			request.setAttribute("roleList", roleList);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("UserListCtl Preload Method Ended");
	}

	/**
	 * Populates the UserBean object with values from request parameters.
	 * 
	 * @param request HttpServletRequest object
	 * @return Populated UserBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("UserListCtl Populate Bean Method Started");
		
		UserBean bean = new UserBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		log.debug("UserListCtl Populate Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET requests to display the initial user list with pagination.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("UserListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		try {
			List<UserBean> list = model.search(bean, pageNo, pageSize);
			List<UserBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("UserListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Handles HTTP POST requests for search, delete, pagination and navigation
	 * operations.
	 * 
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("UserListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				
				if (ids != null && ids.length > 0) {
					UserBean deletebean = new UserBean();
					
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
					
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("UserListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}	
	}

	/**
	 * Returns the view for the user list screen.
	 * 
	 * @return User list view path
	 */
	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}
}
