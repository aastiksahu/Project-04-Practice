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
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller to handle listing, searching, and deleting of follow-up records.
 * Supports pagination and integrates with {@link FollowUpModel}, {@link PatientModel}, and {@link DoctorModel}.
 * 
 * Handles HTTP GET and POST requests for listing and managing follow-up data.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "FollowUpListCtl", urlPatterns = { "/ctl/FollowUpListCtl" })
public class FollowUpListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(FollowUpListCtl.class);

    /**
     * Preloads required data for dropdowns (patients and doctors).
     * 
     * @param request the HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("FollowUpListCtl Preload Method Started");
    	
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
        
        log.debug("FollowUpListCtl Preload Method Ended");
    }

    /**
     * Populates the FollowUpBean from request parameters.
     * 
     * @param request the HttpServletRequest object
     * @return populated FollowUpBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("FollowUpListCtl Populate Bean Method Started");
    	
        FollowUpBean bean = new FollowUpBean();

        bean.setPatientId(DataUtility.getLong(request.getParameter("patientId")));
        bean.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
        bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
        bean.setFees(DataUtility.getLong(request.getParameter("fees")));

    	log.debug("FollowUpListCtl Populate Bean Method Ended");
    	
        return bean;
    }

    /**
     * Handles HTTP GET request to display follow-up list with pagination.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("FollowUpListCtl Do Get Method Started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        FollowUpBean bean = (FollowUpBean) populateBean(request);
        FollowUpModel model = new FollowUpModel();

        try {
            List<FollowUpBean> list = model.search(bean, pageNo, pageSize);
            List<FollowUpBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            log.debug("FollowUpListCtl Do Get Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Handles HTTP POST request for operations like search, delete, pagination, reset, and back.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("FollowUpListCtl Do Post Method Started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        FollowUpBean bean = (FollowUpBean) populateBean(request);
        FollowUpModel model = new FollowUpModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FOLLOW_UP_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    FollowUpBean deletebean = new FollowUpBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FOLLOW_UP_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.FOLLOW_UP_LIST_CTL, request, response);
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

            log.debug("FollowUpListCtl Do Post Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Returns the view path for the follow-up list view.
     * 
     * @return the logical view name
     */
    @Override
    protected String getView() {
        return ORSView.FOLLOW_UP_LIST_VIEW;
    }

}
