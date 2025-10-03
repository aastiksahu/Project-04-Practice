package com.rays.bean;

import java.util.Date;

/**
 * The {@code UserBean} class represents a system user with personal details,
 * login credentials, and role-based access information.
 * <p>
 * This class extends {@code BaseBean} to include common fields like ID,
 * createdBy, modifiedBy, etc. It also overrides {@code getKey()} and
 * {@code getValue()} methods for use in dropdown lists or UI displays.
 * 
 * @author Aastik Sahu
 */
public class UserBean extends BaseBean {

	/** First name of the user. */
	private String firstName;

	/** Last name of the user. */
	private String lastName;

	/** Login ID (usually email or username) of the user. */
	private String login;

	/** Password for user authentication. */
	private String password;

	/** Confirm password used during registration or password change. */
	private String confirmPassword;

	/** Date of birth of the user. */
	private Date dob;

	/** Mobile number of the user. */
	private String mobileNo;

	/** Role ID assigned to the user. */
	private long roleId;

	/** Gender of the user. */
	private String gender;

	/**
	 * Gets the first name of the user.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the user.
	 *
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the user.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user.
	 *
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the login ID of the user.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login ID of the user.
	 *
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password of the user.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the confirm password of the user.
	 *
	 * @return the confirm password
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Sets the confirm password of the user.
	 *
	 * @param confirmPassword the confirm password to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Gets the date of birth of the user.
	 *
	 * @return the date of birth
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth of the user.
	 *
	 * @param dob the date of birth to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * Gets the mobile number of the user.
	 *
	 * @return the mobile number
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Sets the mobile number of the user.
	 *
	 * @param mobileNo the mobile number to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Gets the role ID assigned to the user.
	 *
	 * @return the role ID
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role ID assigned to the user.
	 *
	 * @param roleId the role ID to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the gender of the user.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the user.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the user ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the full name (first name + last name) of the user
	 */
	@Override
	public String getValue() {
		return firstName + " " + lastName;
	}

}
