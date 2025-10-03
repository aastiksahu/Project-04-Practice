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
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller class to handle Add, Update, and Form operations for Patient.
 * Supports field validation, form population, and interaction with PatientModel.
 * 
 * URL Mapping: /ctl/PatientCtl
 * 
 * Handles operations like Save, Update, Reset, Cancel via HTTP POST and retrieves
 * data via HTTP GET.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "PatientCtl", urlPatterns = { "/ctl/PatientCtl" })
public class PatientCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(PatientCtl.class);

    /**
     * Preloads disease options into request scope as a map for dropdown population.
     * 
     * @param request the HttpServletRequest
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("PatientCtl Preload Method Started");
    	
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Common Cold", "Common Cold");
        map.put("Influenza (Flu)", "Influenza (Flu)");
        map.put("Chickenpox", "Chickenpox");
        map.put("Malaria", "Malaria");
        map.put("Typhoid", "Typhoid");

        request.setAttribute("map", map);
        
        log.debug("PatientCtl Preload Method Ended");
    }

    /**
     * Validates the input fields from the request.
     * 
     * @param request the HttpServletRequest
     * @return true if all fields are valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	
    	log.debug("PatientCtl Validate Method Started");
    	
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dateOfVisit"))) {
            request.setAttribute("dateOfVisit", PropertyReader.getValue("error.require", "Date Of Visit"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dateOfVisit"))) {
            request.setAttribute("dateOfVisit", PropertyReader.getValue("error.date", "Date of Visit"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", "Mobile No must have 10 digits");
            pass = false;
        } else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
            request.setAttribute("mobileNo", "Invalid Mobile No");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("disease"))) {
            request.setAttribute("disease", PropertyReader.getValue("error.require", "Disease"));
            pass = false;
        }

        log.debug("PatientCtl Validate Method Ended");
        
        return pass;
    }

    /**
     * Populates the PatientBean with values from the request parameters.
     * 
     * @param request the HttpServletRequest
     * @return populated PatientBean object
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("PatientCtl Populate Bean Method Started");

        PatientBean bean = new PatientBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDateOfVisit(DataUtility.getDate(request.getParameter("dateOfVisit")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setDisease(DataUtility.getString(request.getParameter("disease")));

        populateDTO(bean, request);

        log.debug("PatientCtl Populate Bean Method Ended");
        
        return bean;
    }

    /**
     * Handles HTTP GET requests for loading a patient form.
     * If ID is present, loads the corresponding patient record into the form.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("PatientCtl Do Get Method Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        PatientModel model = new PatientModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0 || op != null) {
            PatientBean bean;
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
        
        log.debug("PatientCtl Do Get Method Ended");
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for save, update, reset, and cancel operations.
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("PatientCtl Do Post Method Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        PatientModel model = new PatientModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            PatientBean bean = (PatientBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Patient added successfully", request);
            } catch (ApplicationException e) {
            	log.error(e);
    			e.printStackTrace();
    			ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            PatientBean bean = (PatientBean) populateBean(request);
            try {
                if (bean.getId() > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully updated", request);
            } catch (ApplicationException e) {
            	log.error(e);
    			e.printStackTrace();
    			ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PATIENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.PATIENT_CTL, request, response);
            return;
        }

        log.debug("PatientCtl Do Post Method Ended");
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for the Patient form.
     * 
     * @return path to Patient view JSP
     */
    @Override
    protected String getView() {
        return ORSView.PATIENT_VIEW;
    }

}
