package com.rays.util;

/**
 * A simple JavaBean representing an email message.
 * 
 * It contains recipient information, subject, content, and message type (HTML
 * or Text).
 * 
 * @author Aastik Sahu
 */
public class EmailMessage {

	/** Recipient email address */
	private String to;

	/** Subject of the email */
	private String subject;

	/** Content/body of the email */
	private String message;

	/** Message type: HTML or TEXT */
	private int messageType = TEXT_MSG;

	/** Constant for HTML message type */
	public static final int HTML_MSG = 1;

	/** Constant for plain text message type */
	public static final int TEXT_MSG = 2;

	/**
	 * Default constructor
	 */
	public EmailMessage() {
	}

	/**
	 * Parameterized constructor to initialize email message.
	 *
	 * @param to      recipient email address
	 * @param subject subject of the email
	 * @param message content of the email
	 */
	public EmailMessage(String to, String subject, String message) {
		this.to = to;
		this.subject = subject;
		this.message = message;
	}

	/**
	 * Sets the recipient email address.
	 *
	 * @param to recipient email
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Gets the recipient email address.
	 *
	 * @return recipient email
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the subject of the email.
	 *
	 * @param subject subject of the email
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the subject of the email.
	 *
	 * @return subject of the email
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the content/body of the email.
	 *
	 * @param message email message content
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the content/body of the email.
	 *
	 * @return email message content
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message type (HTML or TEXT).
	 *
	 * @param messageType type of the message (HTML_MSG or TEXT_MSG)
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * Gets the message type.
	 *
	 * @return type of the message
	 */
	public int getMessageType() {
		return messageType;
	}

}
