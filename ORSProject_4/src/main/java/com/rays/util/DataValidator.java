package com.rays.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for validating various types of data such as strings, numbers,
 * emails, passwords, dates, etc.
 * 
 * This class provides static methods to check for nulls, valid formats,
 * lengths, and specific conditions (like Sundays).
 * 
 * @author Aastik Sahu
 */
public class DataValidator {

	/**
	 * Checks if the string is null or empty after trimming.
	 * 
	 * @param val the input string
	 * @return true if null or empty, false otherwise
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the string is not null and not empty.
	 * 
	 * @param val the input string
	 * @return true if not null and not empty, false otherwise
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Validates whether the input string is a valid integer.
	 * 
	 * @param val the input string
	 * @return true if valid integer, false otherwise
	 */
	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string is a valid long.
	 * 
	 * @param val the input string
	 * @return true if valid long, false otherwise
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string is a valid email address.
	 * 
	 * @param val the input email string
	 * @return true if valid email, false otherwise
	 */
	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string is a valid name.
	 * 
	 * @param val the input name string
	 * @return true if valid name, false otherwise
	 */
	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";

		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string is a valid roll number.
	 * 
	 * @param val the roll number string
	 * @return true if valid, false otherwise
	 */
	public static boolean isRollNo(String val) {

		String rollreg = "[a-zA-Z]{2}[0-9]{3}";

		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string meets password complexity rules.
	 * 
	 * Rules: 8-12 characters, at least one digit, one uppercase, one lowercase, one
	 * special character, and no whitespace.
	 * 
	 * @param val the password string
	 * @return true if valid password, false otherwise
	 */
	public static boolean isPassword(String val) {

		String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";

		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks whether the password length is between 8 and 12 characters.
	 * 
	 * @param val the password string
	 * @return true if length is valid, false otherwise
	 */
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the input string is a valid Indian phone number.
	 * 
	 * @param val the phone number string
	 * @return true if valid phone number, false otherwise
	 */
	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * Checks if phone number is exactly 10 digits long.
	 * 
	 * @param val the phone number string
	 * @return true if valid length, false otherwise
	 */
	public static boolean isPhoneLength(String val) {

		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Validates whether the string can be parsed into a valid date.
	 * 
	 * @param val the date string
	 * @return true if valid date, false otherwise
	 */
	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	/**
	 * Checks if the given date string represents a Sunday.
	 * 
	 * @param val the date string
	 * @return true if date is a Sunday, false otherwise
	 */
	public static boolean isSunday(String val) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DataUtility.getDate(val));
		int i = cal.get(Calendar.DAY_OF_WEEK);

		if (i == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Main method to test validation methods.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		// Test isNull and isNotNull
		System.out.println("isNull Test:");
		System.out.println("Empty String: " + isNull(""));
		System.out.println("Null String: " + isNull(null));
		System.out.println("Non-null String: " + isNotNull("Hello"));

		// Test isInteger
		System.out.println("\nisInteger Test:");
		System.out.println("Valid Integer String: '123' -> " + isInteger("123"));
		System.out.println("Invalid Integer String: 'abc' -> " + isInteger("abc"));
		System.out.println("Null String: -> " + isInteger(null));

		// Test isLong
		System.out.println("\nisLong Test:");
		System.out.println("Valid Long String: '1234567890' -> " + isLong("1234567890"));
		System.out.println("Invalid Long String: 'abc' -> " + isLong("abc"));

		// Test isEmail
		System.out.println("\nisEmail Test:");
		System.out.println("Valid Email: 'test@example.com' -> " + isEmail("test@example.com"));
		System.out.println("Invalid Email: 'test@.com' -> " + isEmail("test@.com"));

		// Test isName
		System.out.println("\nisName Test:");
		System.out.println("Valid Name: 'John Doe' -> " + isName("John Doe"));
		System.out.println("Invalid Name: '123John' -> " + isName("123John"));

		// Test isRollNo
		System.out.println("\nisRollNo Test:");
		System.out.println("Valid RollNo: 'AB123' -> " + isRollNo("AB123"));
		System.out.println("Invalid RollNo: 'A1234' -> " + isRollNo("A1234"));

		// Test isPassword
		System.out.println("\nisPassword Test:");
		System.out.println("Valid Password: 'Passw0rd@123' -> " + isPassword("Passw0rd@123"));
		System.out.println("Invalid Password: 'pass123' -> " + isPassword("pass123"));

		// Test isPasswordLength
		System.out.println("\nisPasswordLength Test:");
		System.out.println("Valid Password Length: 'Passw0rd' -> " + isPasswordLength("Passw0rd"));
		System.out.println("Invalid Password Length: 'pass' -> " + isPasswordLength("pass"));

		// Test isPhoneNo
		System.out.println("\nisPhoneNo Test:");
		System.out.println("Valid PhoneNo: '9876543210' -> " + isPhoneNo("9876543210"));
		System.out.println("Invalid PhoneNo: '1234567890' -> " + isPhoneNo("1234567890"));

		// Test isPhoneLength
		System.out.println("\nisPhoneLength Test:");
		System.out.println("Valid Phone Length: '9876543210' -> " + isPhoneLength("9876543210"));
		System.out.println("Invalid Phone Length: '98765' -> " + isPhoneLength("98765"));

		// Test isDate
		System.out.println("\nisDate Test:");
		System.out.println("Valid Date: '10/15/2024' -> " + isDate("10/15/2024"));
		System.out.println("Invalid Date: '2024-10-15' -> " + isDate("2024-10-15"));

		// Test isSunday
		System.out.println("\nisSunday Test:");
		System.out.println("Date on Sunday: '10/13/2024' -> " + isSunday("02-17-2025"));
		System.out.println("Date not on Sunday: '10/15/2024' -> " + isSunday("10/15/2024"));
	}
}
