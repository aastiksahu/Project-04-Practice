package com.rays.exception;

/**
 * DatabaseException is a custom exception class used to handle exceptions
 * related to database operations.
 * <p>
 * This class extends the standard {@link Exception} class.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class DatabaseException extends Exception {

	/**
	 * Constructs a new DatabaseException with the specified detail message.
	 *
	 * @param msg the detail message describing the database exception
	 */
	public DatabaseException(String msg) {
		super(msg);
	}

}
