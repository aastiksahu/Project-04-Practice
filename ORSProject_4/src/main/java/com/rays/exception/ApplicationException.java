package com.rays.exception;

/**
 * ApplicationException is a custom exception class used to handle
 * application-level exceptions in the system.
 * <p>
 * This class extends the standard {@link Exception} class.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class ApplicationException extends Exception {

    /**
     * Constructs a new ApplicationException with the specified detail message.
     *
     * @param msg the detail message describing the exception
     */
    public ApplicationException(String msg) {
        super(msg);
    }

}
