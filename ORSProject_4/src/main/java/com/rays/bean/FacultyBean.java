package com.rays.bean;

import java.util.Date;

/**
 * The {@code FacultyBean} class represents a faculty member entity, containing
 * personal and academic details such as name, contact information, date of
 * birth, associated college, course, and subject.
 * <p>
 * It extends {@code BaseBean}, inheriting common properties like id, createdBy,
 * modifiedBy, etc.
 * 
 * This class also overrides {@code getKey()} and {@code getValue()} methods for
 * dropdown display purposes.
 * 
 * @author Aastik Sahu
 */
public class FacultyBean extends BaseBean {

	/** First name of the faculty member. */
	private String firstName;

	/** Last name of the faculty member. */
	private String lastName;

	/** Date of birth of the faculty member. */
	private Date dob;

	/** Gender of the faculty member. */
	private String gender;

	/** Mobile number of the faculty member. */
	private String mobileNo;

	/** Email address of the faculty member. */
	private String email;

	/** ID of the associated college. */
	private long collegeId;

	/** Name of the associated college. */
	private String collegeName;

	/** ID of the associated course. */
	private long courseId;

	/** Name of the associated course. */
	private String courseName;

	/** ID of the associated subject. */
	private long subjectId;

	/** Name of the associated subject. */
	private String subjectName;

	/**
	 * Gets the first name of the faculty.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the faculty.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the faculty.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the faculty.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the date of birth of the faculty.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the faculty.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the gender of the faculty.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the faculty.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the mobile number of the faculty.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the faculty.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the email address of the faculty.
	 *
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the faculty.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the college ID associated with the faculty.
	 *
	 * @return the college ID
	 */
	public long getCollegeId() {
		return collegeId;
	}

	/**
	 * Sets the college ID associated with the faculty.
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
	 * Gets the course ID associated with the faculty.
	 *
	 * @return the course ID
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID associated with the faculty.
	 *
	 * @param courseId the course ID to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Gets the name of the associated course.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the name of the associated course.
	 *
	 * @param courseName the course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the subject ID associated with the faculty.
	 *
	 * @return the subject ID
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject ID associated with the faculty.
	 *
	 * @param subjectId the subject ID to set
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Gets the name of the associated subject.
	 *
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the name of the associated subject.
	 *
	 * @param subjectName the subject name to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the unique key for the dropdown list.
	 *
	 * @return the string representation of the faculty ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in the dropdown list.
	 *
	 * @return the full name (first name + last name) of the faculty
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}

}
