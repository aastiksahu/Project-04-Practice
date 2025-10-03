package com.rays.bean;

/**
 * The {@code SubjectBean} class represents a subject entity that is associated
 * with a specific course. It includes subject name, description, and
 * corresponding course details.
 * <p>
 * This class extends {@code BaseBean}, which provides common attributes like
 * ID, createdBy, modifiedBy, and timestamps.
 * <p>
 * It also implements the {@code getKey()} and {@code getValue()} methods used
 * for dropdown lists.
 * 
 * @author Aastik Sahu
 */
public class SubjectBean extends BaseBean {

	/** Name of the subject. */
	private String name;

	/** ID of the associated course. */
	private long courseId;

	/** Name of the associated course. */
	private String courseName;

	/** Description of the subject. */
	private String description;

	/**
	 * Gets the name of the subject.
	 *
	 * @return the subject name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the subject.
	 *
	 * @param name the subject name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the course ID associated with the subject.
	 *
	 * @return the course ID
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID associated with the subject.
	 *
	 * @param courseId the course ID to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Gets the course name associated with the subject.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name associated with the subject.
	 *
	 * @param courseName the course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the description of the subject.
	 *
	 * @return the subject description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the subject.
	 *
	 * @param description the subject description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the subject ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the subject name
	 */
	@Override
	public String getValue() {
		return name;
	}

}
