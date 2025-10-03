package com.rays.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.bean.BaseBean;
import com.rays.bean.PatientBean;
import com.rays.exception.ApplicationException;
import com.rays.model.PatientModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller to handle the Patient list operations.
 * Provides functionality for search, pagination, delete, reset, and redirection.
 * 
 * URL Mapping: /ctl/PatientListCtl
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "PatientListCtl", urlPatterns = { "/ctl/PatientListCtl" })
public class PatientListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(PatientListCtl.class);

    /**
     * Populates dropdown map of diseases for filtering patient list.
     * 
     * @param request the HttpServletRequest
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("PatientListCtl Preload Method Started");
    	
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Common Cold", "Common Cold");
        map.put("Influenza (Flu)", "Influenza (Flu)");
        map.put("Chickenpox", "Chickenpox");
        map.put("Malaria", "Malaria");
        map.put("Typhoid", "Typhoid");

        request.setAttribute("map", map);
        
        log.debug("PatientListCtl Preload Method Ended");
    }

    /**
     * Populates a PatientBean object from the request parameters.
     * 
     * @param request the HttpServletRequest
     * @return populated PatientBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("PatientListCtl Populate Bean Method Started");

        PatientBean bean = new PatientBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDateOfVisit(DataUtility.getDate(request.getParameter("dateOfVisit")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setDisease(DataUtility.getString(request.getParameter("disease")));

        log.debug("PatientListCtl Populate Bean Method Ended");
        return bean;
    }

    /**
     * Handles GET requests to load and display the patient list.
     * Performs initial search and handles pagination.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("PatientListCtl Do Get Method Started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        PatientBean bean = (PatientBean) populateBean(request);
        PatientModel model = new PatientModel();

        try {
            List<PatientBean> list = model.search(bean, pageNo, pageSize);
            List<PatientBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            log.debug("PatientListCtl Do Get Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Handles POST requests for search, pagination, delete, reset, and navigation.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("PatientListCtl Do Post Method Started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        PatientBean bean = (PatientBean) populateBean(request);
        PatientModel model = new PatientModel();

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
                ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    PatientBean deletebean = new PatientBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
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

            log.debug("PatientListCtl Do Post Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Returns the path of the view to be rendered.
     * 
     * @return Patient List view JSP path
     */
    @Override
    protected String getView() {
        return ORSView.PATIENT_LIST_VIEW;
    }

}
