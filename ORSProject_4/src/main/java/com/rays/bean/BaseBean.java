package com.rays.bean;

import java.sql.Timestamp;

/**
 * The {@code BaseBean} class serves as a base class for other beans, providing
 * common properties like id, createdBy, modifiedBy, createdDatetime, and
 * modifiedDatetime.
 * <p>
 * This class implements the {@code DropdownListBean} interface.
 * 
 * @author Aastik Sahu
 */
public abstract class BaseBean implements DropdownListBean {

	/** Unique identifier for the bean. */
	protected long id;

	/** Username or identifier of the creator of the record. */
	protected String createdBy;

	/** Username or identifier of the last person who modified the record. */
	protected String modifiedBy;

	/** Timestamp when the record was created. */
	protected Timestamp createdDatetime;

	/** Timestamp when the record was last modified. */
	protected Timestamp modifiedDatetime;

	/**
	 * Gets the ID of the bean.
	 *
	 * @return the ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the ID of the bean.
	 *
	 * @param id the new ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the username of the creator.
	 *
	 * @return the creator's username
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the username of the creator.
	 *
	 * @param createdBy the creator's username
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the username of the person who last modified the record.
	 *
	 * @return the modifier's username
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the username of the person who last modified the record.
	 *
	 * @param modifiedBy the modifier's username
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Gets the timestamp of when the record was created.
	 *
	 * @return the creation timestamp
	 */
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * Sets the timestamp of when the record was created.
	 *
	 * @param createdDatetime the creation timestamp
	 */
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * Gets the timestamp of the last modification to the record.
	 *
	 * @return the modification timestamp
	 */
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	/**
	 * Sets the timestamp of the last modification to the record.
	 *
	 * @param modifiedDatetime the modification timestamp
	 */
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

}
