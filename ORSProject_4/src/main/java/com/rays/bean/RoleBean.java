package com.rays.bean;

/**
 * The {@code RoleBean} class represents a user role in the system, such as
 * Admin, Faculty, Student, Kiosk, or College.
 * <p>
 * Each role is identified by a constant integer value and contains a name and
 * description.
 * <p>
 * It extends {@code BaseBean}, which provides common attributes like ID,
 * createdBy, and timestamps.
 * 
 * This class also overrides {@code getKey()} and {@code getValue()} methods for
 * dropdown display purposes.
 * 
 * @author Aastik Sahu
 */
public class RoleBean extends BaseBean {

	/** Constant value for Admin role. */
	public static final int ADMIN = 11111;

	/** Constant value for Faculty role. */
	public static final int FACULTY = 22222;

	/** Constant value for Student role. */
	public static final int STUDENT = 33333;

	/** Constant value for Kiosk role. */
	public static final int KIOSK = 44444;

	/** Constant value for College role. */
	public static final int COLLEGE = 55555;

	/** Name of the role. */
	private String name;

	/** Description of the role. */
	private String description;

	/**
	 * Gets the name of the role.
	 *
	 * @return the role name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the role.
	 *
	 * @param name the role name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the role.
	 *
	 * @return the role description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the role.
	 *
	 * @param description the role description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the role ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the name of the role
	 */
	@Override
	public String getValue() {
		return name;
	}
}
