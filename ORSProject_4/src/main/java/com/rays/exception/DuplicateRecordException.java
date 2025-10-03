package com.rays.exception;

/**
 * DuplicateRecordException is a custom exception class that indicates
 * an attempt to create a duplicate record which already exists.
 * <p>
 * This class extends the standard {@link Exception} class.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class DuplicateRecordException extends Exception {

    /**
     * Constructs a new DuplicateRecordException with the specified detail message.
     *
     * @param msg the detail message explaining the exception
     */
    public DuplicateRecordException(String msg) {
        super(msg);
    }

}
