package com.rays.bean;

/**
 * The {@code MarksheetBean} class represents the academic marksheet of a
 * student. It contains the student's roll number, name, student ID, and marks
 * for physics, chemistry, and maths.
 * <p>
 * It extends {@code BaseBean} to inherit common fields like ID, createdBy, etc.
 * <p>
 * This class also overrides {@code getKey()} and {@code getValue()} methods for
 * dropdown usage.
 * 
 * @author Aastik Sahu
 */
public class MarksheetBean extends BaseBean {

	/** Roll number of the student. */
	private String rollNo;

	/** ID of the student associated with this marksheet. */
	private long studentId;

	/** Name of the student. */
	private String name;

	/** Marks obtained in Physics. */
	private Integer physics;

	/** Marks obtained in Chemistry. */
	private Integer chemistry;

	/** Marks obtained in Maths. */
	private Integer maths;

	/**
	 * Gets the roll number of the student.
	 *
	 * @return the roll number
	 */
	public String getRollNo() {
		return rollNo;
	}

	/**
	 * Sets the roll number of the student.
	 *
	 * @param rollNo the roll number to set
	 */
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	/**
	 * Gets the ID of the student.
	 *
	 * @return the student ID
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Sets the ID of the student.
	 *
	 * @param studentId the student ID to set
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Gets the name of the student.
	 *
	 * @return the student's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the student.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the marks obtained in Physics.
	 *
	 * @return the Physics marks
	 */
	public Integer getPhysics() {
		return physics;
	}

	/**
	 * Sets the marks obtained in Physics.
	 *
	 * @param physics the Physics marks to set
	 */
	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	/**
	 * Gets the marks obtained in Chemistry.
	 *
	 * @return the Chemistry marks
	 */
	public Integer getChemistry() {
		return chemistry;
	}

	/**
	 * Sets the marks obtained in Chemistry.
	 *
	 * @param chemistry the Chemistry marks to set
	 */
	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	/**
	 * Gets the marks obtained in Maths.
	 *
	 * @return the Maths marks
	 */
	public Integer getMaths() {
		return maths;
	}

	/**
	 * Sets the marks obtained in Maths.
	 *
	 * @param maths the Maths marks to set
	 */
	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	/**
	 * Returns the unique key for dropdown lists.
	 *
	 * @return the string representation of the ID
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns the value to be displayed in dropdown lists.
	 *
	 * @return the name of the student
	 */
	@Override
	public String getValue() {
		return name;
	}

}
