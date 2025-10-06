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
import com.rays.bean.DoctorBean;
import com.rays.exception.ApplicationException;
import com.rays.model.DoctorModel;
import com.rays.util.DataUtility;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller to handle operations related to listing, searching, and deleting doctor records.
 * It supports pagination and integrates with DoctorModel for business logic execution.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "DoctorListCtl", urlPatterns = { "/ctl/DoctorListCtl" })
public class DoctorListCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(DoctorListCtl.class);

    /**
     * Preloads data required for the doctor list view.
     * 
     * @param request the HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("DoctorListCtl Preload Method Started");
    	
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("General Physician (MBBS Doctor)", "General Physician (MBBS Doctor)");
        map.put("Dentist", "Dentist");
        map.put("Eye Specialist", "Eye Specialist");
        map.put("ENT Specialist", "ENT Specialist");
        map.put("Gynecologist", "Gynecologist");

        request.setAttribute("map", map);
        
        log.debug("DoctorListCtl Preload Method Ended");
    }

    /**
     * Populates the DoctorBean object from request parameters.
     * 
     * @param request the HttpServletRequest object
     * @return the populated DoctorBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("DoctorListCtl Populate Bean Method Started");
    	
        DoctorBean bean = new DoctorBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));

        log.debug("DoctorListCtl Populate Bean Method Ended");
        
        return bean;
    }

    /**
     * Handles GET requests to display the initial list of doctors.
     * 
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("DoctorListCtl Do Get Method Started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        DoctorBean bean = (DoctorBean) populateBean(request);
        DoctorModel model = new DoctorModel();

        try {
            List<DoctorBean> list = model.search(bean, pageNo, pageSize);
            List<DoctorBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            log.debug("DoctorListCtl Do Get Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Handles POST requests to perform search, delete, pagination, and navigation actions.
     * 
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("DoctorListCtl Do Post Method Started");
    	
        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        DoctorBean bean = (DoctorBean) populateBean(request);
        DoctorModel model = new DoctorModel();

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
                ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    DoctorBean deletebean = new DoctorBean();

                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
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

            log.debug("DoctorListCtl Do Post Method Ended");
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
        	log.error(e);
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
            return;
        }
    }

    /**
     * Returns the view page for the doctor list.
     * 
     * @return the view path as a String
     */
    @Override
    protected String getView() {
        return ORSView.DOCTOR_LIST_VIEW;
    }
}
