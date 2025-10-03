package com.rays.util;

import java.util.HashMap;

/**
 * Utility class for building HTML email messages for various user account
 * events such as registration, password recovery, and password changes.
 * 
 * All methods take a {@code HashMap<String, String>} containing necessary user
 * information and return a formatted HTML string as the email body.
 * 
 * @author Aastik Sahu
 */
public class EmailBuilder {

	/**
	 * Builds a user registration confirmation email message.
	 *
	 * @param map A HashMap containing user details. Expected keys:
	 *            <ul>
	 *            <li>login</li>
	 *            <li>password</li>
	 *            </ul>
	 * @return A String containing the HTML formatted registration email message.
	 */
	public static String getUserRegistrationMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Welcome to ORS, ").append(map.get("login")).append("!</H1>");
		msg.append("<P>Your registration is successful. You can now log in and manage your account.</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("<P>Change your password after logging in for security reasons.</P>");
		msg.append("<P>For support, contact +91 98273 60504 or hrd@sunrays.co.in.</P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}

	/**
	 * Builds a password recovery email message.
	 *
	 * @param map A HashMap containing user details. Expected keys:
	 *            <ul>
	 *            <li>firstName</li>
	 *            <li>lastName</li>
	 *            <li>login</li>
	 *            <li>password</li>
	 *            </ul>
	 * @return A String containing the HTML formatted forgot password email message.
	 */
	public static String getForgetPasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Password Recovery</H1>");
		msg.append("<P>Hello, ").append(map.get("firstName")).append(" ").append(map.get("lastName")).append(".</P>");
		msg.append("<P>Your login details are:</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}

	/**
	 * Builds a change password confirmation email message.
	 *
	 * @param map A HashMap containing user details. Expected keys:
	 *            <ul>
	 *            <li>firstName</li>
	 *            <li>lastName</li>
	 *            <li>login</li>
	 *            <li>password</li>
	 *            </ul>
	 * @return A String containing the HTML formatted change password email message.
	 */
	public static String getChangePasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<HTML><BODY>");
		msg.append("<H1>Password Changed Successfully</H1>");
		msg.append("<P>Dear ").append(map.get("firstName")).append(" ").append(map.get("lastName"))
				.append(", your password has been updated.</P>");
		msg.append("<P>Your updated login details are:</P>");
		msg.append("<P><B>Login Id: ").append(map.get("login")).append("<BR>New Password: ").append(map.get("password"))
				.append("</B></P>");
		msg.append("</BODY></HTML>");
		return msg.toString();
	}

}
