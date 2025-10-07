package com.rays.controller;

import java.io.IOException;
import java.util.HashMap;

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
import com.rays.util.DataValidator;
import com.rays.util.PropertyReader;
import com.rays.util.ServletUtility;

/**
 * Controller class to handle Doctor operations such as add, update, reset, and cancel.
 * 
 * @author Aastik Sahu
 */
@WebServlet(name = "DoctorCtl", urlPatterns = { "/ctl/DoctorCtl" })
public class DoctorCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(DoctorCtl.class);

    /**
     * Preloads data required for the doctor form.
     * 
     * @param request the HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("DoctorCtl Preload Method Started");
    	
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("General Physician (MBBS Doctor)", "General Physician (MBBS Doctor)");
        map.put("Dentist", "Dentist");
        map.put("Eye Specialist", "Eye Specialist");
        map.put("ENT Specialist", "ENT Specialist");
        map.put("Gynecologist", "Gynecologist");

        request.setAttribute("map", map);
        
        log.debug("DoctorCtl Preload Method Ended");
    }

    /**
     * Validates input data from the doctor form.
     * 
     * @param request the HttpServletRequest object
     * @return true if data is valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	
    	log.debug("DoctorCtl Validate Method Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
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

        if (DataValidator.isNull(request.getParameter("expertise"))) {
            request.setAttribute("expertise", PropertyReader.getValue("error.require", "Expertise"));
            pass = false;
        }
        
        log.debug("DoctorCtl Validate Method Ended");
        
        return pass;
    }

    /**
     * Populates DoctorBean object from request parameters.
     * 
     * @param request the HttpServletRequest object
     * @return populated DoctorBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("DoctorCtl Populate Bean Method Started");

        DoctorBean bean = new DoctorBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
        bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));

        populateDTO(bean, request);

        log.debug("DoctorCtl Populate Bean Method Ended");
        
        return bean;
    }

    /**
     * Handles GET requests to display doctor form with pre-filled data if ID is present.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("DoctorCtl Do Get Method Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        DoctorModel model = new DoctorModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0 || op != null) {

            DoctorBean bean;

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
        
        log.debug("DoctorCtl Do Get Method Ended");
        
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST requests for various operations like Save, Update, Reset, and Cancel.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("DoctorCtl Do Post Method Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        DoctorModel model = new DoctorModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            DoctorBean bean = (DoctorBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Doctor added successfully", request);

            } catch (ApplicationException e) {
            	log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            DoctorBean bean = (DoctorBean) populateBean(request);

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
            ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
            return;
        }
        
        log.debug("DoctorCtl Do Post Method Ended");
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for the doctor form.
     * 
     * @return the view page path
     */
    @Override
    protected String getView() {
        return ORSView.DOCTOR_VIEW;
    }

}
