package com.rays.bean;

import java.util.Date;

/**
 * Bean class representing a follow-up appointment between a patient and a doctor.
 * Extends {@link BaseBean}.
 * Contains patient and doctor details along with visit date and fees.
 * 
 * author Aastik Sahu
 */
public class FollowUpBean extends BaseBean {

    /**
     * ID of the patient.
     */
    private long patientId;

    /**
     * Name of the patient.
     */
    private String patientName;

    /**
     * ID of the doctor.
     */
    private long doctorId;

    /**
     * Name of the doctor.
     */
    private String doctorName;

    /**
     * Date of the follow-up visit.
     */
    private Date visitDate;

    /**
     * Fees charged for the visit.
     */
    private long fees;

    /**
     * Gets the ID of the patient.
     * 
     * @return patientId
     */
    public long getPatientId() {
        return patientId;
    }

    /**
     * Sets the ID of the patient.
     * 
     * @param patientId the patient ID to set
     */
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    /**
     * Gets the name of the patient.
     * 
     * @return patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the name of the patient.
     * 
     * @param patientName the patient name to set
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Gets the ID of the doctor.
     * 
     * @return doctorId
     */
    public long getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the ID of the doctor.
     * 
     * @param doctorId the doctor ID to set
     */
    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Gets the name of the doctor.
     * 
     * @return doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Sets the name of the doctor.
     * 
     * @param doctorName the doctor name to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * Gets the date of the follow-up visit.
     * 
     * @return visitDate
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * Sets the date of the follow-up visit.
     * 
     * @param visitDate the visit date to set
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * Gets the fees charged for the visit.
     * 
     * @return fees
     */
    public long getFees() {
        return fees;
    }

    /**
     * Sets the fees charged for the visit.
     * 
     * @param fees the fees to set
     */
    public void setFees(long fees) {
        this.fees = fees;
    }

    /**
     * Returns the unique key for this bean. Not implemented.
     * 
     * @return null
     */
    @Override
    public String getKey() {
        return null;
    }

    /**
     * Returns the display value for this bean. Not implemented.
     * 
     * @return null
     */
    @Override
    public String getValue() {
        return null;
    }

}
