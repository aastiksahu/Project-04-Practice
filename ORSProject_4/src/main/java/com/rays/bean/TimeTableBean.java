package com.rays.bean;

import java.util.Date;

/**
 * The {@code TimeTableBean} class represents a timetable entry for exams,
 * including information about the semester, exam date and time, course,
 * subject, and description.
 * <p>
 * This class extends {@code BaseBean} to inherit standard entity fields like
 * ID, createdBy, modifiedBy, and timestamps.
 * <p>
 * It also overrides {@code getKey()} and {@code getValue()} methods for use in
 * dropdown lists or UI elements.
 * 
 * @author Aastik Sahu
 */
public class TimeTableBean extends BaseBean {

	/** Semester for which the exam is scheduled. */
	private String semester;

	/** Description or notes about the timetable entry. */
	private String description;

	/** Date on which the exam will be conducted. */
	private Date examDate;

	/** Time at which the exam will be conducted. */
	private String examTime;

	/** ID of the associated course. */
	private long courseId;

	/** Name of the associated course. */
	private String courseName;

	/** ID of the associated subject. */
	private long subjectId;

	/** Name of the associated subject. */
	private String subjectName;

	/**
	 * Gets the semester of the timetable.
	 *
	 * @return the semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * Sets the semester of the timetable.
	 *
	 * @param semester the semester to set
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * Gets the description of the timetable entry.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the timetable entry.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the exam date.
	 *
	 * @return the exam date
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * Sets the exam date.
	 *
	 * @param examDate the exam date to set
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * Gets the exam time.
	 *
	 * @return the exam time
	 */
	public String getExamTime() {
		return examTime;
	}

	/**
	 * Sets the exam time.
	 *
	 * @param examTime the exam time to set
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	/**
	 * Gets the course ID.
	 *
	 * @return the course ID
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course ID.
	 *
	 * @param courseId the course ID to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName the course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the subject ID.
	 *
	 * @return the subject ID
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets the subject ID.
	 *
	 * @param subjectId the subject ID to set
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Gets the subject name.
	 *
	 * @return the subject name
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets the subject name.
	 *
	 * @param subjectName the subject name to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the timetable ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the semester associated with the timetable
	 */
	@Override
	public String getValue() {
		return semester;
	}

}
