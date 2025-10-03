/**
 * PatientBean class represents a Patient entity in the system.
 * This bean class stores patient information including personal details,
 * visit information, and medical data. It extends BaseBean for common properties.
 * 
 * @author Aastik Sahu
 * @version 1.0
 * @package com.rays.bean
 */

package com.rays.bean;

import java.util.Date;

public class PatientBean extends BaseBean {

	private String name;
	private Date dateOfVisit;
	private String mobileNo;
	private String disease;

	/**
     * Gets the patient's name.
     * 
     * @return String representing the patient's name
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the patient's name.
     * 
     * @param name String representing the patient's name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Gets the date of the patient's visit.
     * 
     * @return Date representing the visit date
     */
	public Date getDateOfVisit() {
		return dateOfVisit;
	}

	/**
     * Sets the date of the patient's visit.
     * 
     * @param dateOfVisit Date representing the visit date
     */
	public void setDateOfVisit(Date dateOfVisit) {
		this.dateOfVisit = dateOfVisit;
	}

	/**
     * Gets the patient's mobile number.
     * 
     * @return String representing the mobile number
     */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
     * Sets the patient's mobile number.
     * 
     * @param mobileNo String representing the mobile number
     */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
     * Gets the patient's disease or medical condition.
     * 
     * @return String representing the disease
     */
	public String getDisease() {
		return disease;
	}

	/**
     * Sets the patient's disease or medical condition.
     * 
     * @param disease String representing the disease
     */
	public void setDisease(String disease) {
		this.disease = disease;
	}

	/**
     * Gets the key value for the patient (ID as string).
     * 
     * @return String representing the patient ID
     */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
     * Gets the display value for the patient (name).
     * 
     * @return String representing the patient name
     */
	@Override
	public String getValue() {
		return name;
	}

}