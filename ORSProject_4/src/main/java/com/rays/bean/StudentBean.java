package com.rays.bean;

import java.util.Date;

/**
 * The {@code StudentBean} class represents a student entity with personal and
 * academic details such as name, date of birth, gender, contact information,
 * and college association.
 * <p>
 * It extends {@code BaseBean}, inheriting common fields like ID, createdBy,
 * modifiedBy, timestamps, etc.
 * <p>
 * This class also overrides {@code getKey()} and {@code getValue()} methods for
 * dropdown display purposes.
 * 
 * @author Aastik Sahu
 */
public class StudentBean extends BaseBean {

	/** First name of the student. */
	private String firstName;

	/** Last name of the student. */
	private String lastName;

	/** Date of birth of the student. */
	private Date dob;

	/** Gender of the student. */
	private String gender;

	/** Mobile number of the student. */
	private String mobileNo;

	/** Email address of the student. */
	private String email;

	/** ID of the college the student is associated with. */
	private long collegeId;

	/** Name of the college the student is associated with. */
	private String collegeName;

	/**
	 * Gets the first name of the student.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the student.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the date of birth of the student.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the student.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the gender of the student.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the student.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the mobile number of the student.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the student.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the email address of the student.
	 *
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the student.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the ID of the associated college.
	 *
	 * @return the college ID
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the ID of the associated college.
	 *
	 * @param collegeId the college ID to set
	 */
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	/**
	 * Gets the name of the associated college.
	 *
	 * @return the college name
	 */
	public String getCollegeName() {
		return collegeName;
	}

	/**
	 * Sets the name of the associated college.
	 *
	 * @param collegeName the college name to set
	 */
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the student ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the full name (first name + last name) of the student
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}

}
