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
import com.rays.model.CourseModel;
import com.rays.model.SubjectModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller to handle Subject List screen operations like pagination,
 * searching, deleting subjects, etc.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

	Logger log = Logger.getLogger(SubjectListCtl.class);

	/**
	 * Preloads data required to display in the dropdowns (like course list).
	 * 
	 * @param request HttpServletRequest
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		log.debug("SubjectListCtl Preload Method Started");

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
			return;
		}

		log.debug("SubjectListCtl Preload Method Ended");
	}

	/**
	 * Populates the SubjectBean from request parameters.
	 * 
	 * @param request HttpServletRequest
	 * @return BaseBean populated SubjectBean
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("SubjectListCtl Populate Bean Method Started");

		SubjectBean bean = new SubjectBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setId(DataUtility.getLong(request.getParameter("subjectId")));

		log.debug("SubjectListCtl Populate Bean Method Ended");

		return bean;
	}

	/**
	 * Handles GET requests for displaying the subject list with pagination.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectListCtl Do Get Method Started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();

		try {
			List<SubjectBean> list = model.search(bean, pageNo, pageSize);
			List<SubjectBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("SubjectListCtl Do Get Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
	}

	/**
	 * Handles POST requests for searching, paginating, deleting and navigating the
	 * subject list.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectListCtl Do Post Method Started");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();

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
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;

				if (ids != null && ids.length > 0) {
					SubjectBean deletebean = new SubjectBean();

					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (!OP_DELETE.equalsIgnoreCase(op)) {
				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());

			log.debug("SubjectListCtl Do Post Method Ended");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	/**
	 * Returns the view path of the Subject List screen.
	 * 
	 * @return String representing the view path
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}
}
