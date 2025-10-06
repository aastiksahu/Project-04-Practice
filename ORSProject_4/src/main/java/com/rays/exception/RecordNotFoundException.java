package com.rays.exception;

/**
 * RecordNotFoundException is a custom exception class that is thrown when a
 * requested record is not found in the system.
 * <p>
 * This class extends the standard {@link Exception} class.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class RecordNotFoundException extends Exception {

	/**
	 * Constructs a new RecordNotFoundException with the specified detail message.
	 *
	 * @param msg the detail message explaining the exception
	 */
	public RecordNotFoundException(String msg) {
		super(msg);
	}

}
