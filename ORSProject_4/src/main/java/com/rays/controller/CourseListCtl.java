/**
 * Controller to handle listing, searching, deleting, and paginating Course records.
 * 
 * Operations handled:
 * - Search
 * - Pagination (Next, Previous)
 * - Delete
 * - Redirect to new course form
 * 
 * @author Aastik Sahu
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
import com.rays.bean.CourseBean;
import com.rays.exception.ApplicationException;
import com.rays.model.CourseModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Servlet implementation class CourseListCtl Manages Course list operations
 * like search, delete, pagination, and redirection.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(CourseListCtl.class);

	/**
	 * Preloads course list into request scope to support dropdowns or filters.
	 * 
	 * @param request HttpServletRequest
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("CourseListCtl Preload Method Started");
		
		CourseModel courseModel = new CourseModel();

		try {
			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);
			
		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
		}
		
		log.debug("CourseListCtl Preload Method Ended");
	}

	/**
	 * Populates a CourseBean from request parameters.
	 * 
	 * @param request HttpServletRequest
	 * @return populated CourseBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("CourseListCtl Populated Bean Method Started");
		
		CourseBean bean = new CourseBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));

		log.debug("CourseListCtl Populated Bean Method Ended");
		
		return bean;
	}

	/**
	 * Handles HTTP GET request to show initial list of courses. Supports pagination
	 * and initial loading.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("CourseListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();

		try {
			List<CourseBean> list = model.search(bean, pageNo, pageSize);
			List<CourseBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("CourseListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}	
	}

	/**
	 * Handles HTTP POST requests for course list operations like search, delete,
	 * reset, etc.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("CourseListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();

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
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					CourseBean deletebean = new CourseBean();
					
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Course deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
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

			log.debug("CourseListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns the path of the course list view.
	 * 
	 * @return View path as String
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
