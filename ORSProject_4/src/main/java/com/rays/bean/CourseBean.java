package com.rays.bean;

/**
 * The {@code CourseBean} class represents a course entity, containing details
 * such as course name, duration, and description.
 * <p>
 * It extends {@code BaseBean}, inheriting common attributes like ID, createdBy,
 * modifiedBy, etc.
 * <p>
 * This class also overrides {@code getKey()} and {@code getValue()} methods for
 * dropdown display purposes.
 * 
 * @author Aastik Sahu
 */
public class CourseBean extends BaseBean {

	/** Name of the course. */
	private String name;

	/** Duration of the course. */
	private String duration;

	/** Description of the course content. */
	private String description;

	/**
	 * Gets the name of the course.
	 *
	 * @return the name of the course
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the course.
	 *
	 * @param name the course name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the duration of the course.
	 *
	 * @return the duration of the course
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the course.
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Gets the description of the course.
	 *
	 * @return the course description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the course.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the unique key for the dropdown list.
	 *
	 * @return the string representation of the course ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in the dropdown list.
	 *
	 * @return the name of the course
	 */
	@Override
	public String getValue() {
		return name;
	}

}
