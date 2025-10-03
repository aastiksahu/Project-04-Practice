package com.rays.bean;

import java.util.Date;

/**
 * Represents a Doctor entity containing basic personal and professional details.
 * Inherits from {@link BaseBean}.
 * 
 * @author Aastik Sahu
 */
public class DoctorBean extends BaseBean {

	/**
	 * Name of the doctor.
	 */
	private String name;

	/**
	 * Date of birth of the doctor.
	 */
	private Date dob;

	/**
	 * Mobile number of the doctor.
	 */
	private String mobileNo;

	/**
	 * Area of expertise or specialization of the doctor.
	 */
	private String expertise;

	/**
	 * Gets the name of the doctor.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the doctor.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the date of birth of the doctor.
	 * 
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the doctor.
	 * 
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the mobile number of the doctor.
	 * 
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the doctor.
	 * 
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the area of expertise of the doctor.
	 * 
	 * @return the expertise
	 */
	public String getExpertise() {
		return expertise;
	}

	/**
	 * Sets the area of expertise of the doctor.
	 * 
	 * @param expertise the expertise to set
	 */
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	/**
	 * Gets the unique key (ID) of the doctor as a String.
	 * 
	 * @return the ID as a String
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Gets the value representing the doctor, typically the name.
	 * 
	 * @return the name
	 */
	@Override
	public String getValue() {
		return name;
	}

}
