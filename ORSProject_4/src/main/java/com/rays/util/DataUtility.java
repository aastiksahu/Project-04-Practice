package com.rays.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for data conversion and formatting operations.
 * 
 * Provides methods for handling Strings, Integers, Longs, Dates, and
 * Timestamps.
 * 
 * @author Aastik Sahu
 */
public class DataUtility {

	/** Application date format: dd-MM-yyyy */
	public static final String APP_DATE_FORMAT = "dd-MM-yyyy";

	/** Application time format: dd-MM-yyyy HH:mm:ss */
	public static final String APP_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);

	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);

	/**
	 * Trims a string if it is not null.
	 * 
	 * @param val the input string
	 * @return trimmed string or original if null
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts an object to a string.
	 * 
	 * @param val the input object
	 * @return string representation or empty string if null
	 */
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 * Converts a string to an integer.
	 * 
	 * @param val the input string
	 * @return integer value or 0 if invalid
	 */
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	/**
	 * Converts a string to a long.
	 * 
	 * @param val the input string
	 * @return long value or 0 if invalid
	 */
	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	/**
	 * Parses a date from a string using application date format.
	 * 
	 * @param val the date string
	 * @return Date object or null if parsing fails
	 */
	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {

		}
		return date;
	}

	/**
	 * Formats a Date object to a string using application date format.
	 * 
	 * @param date the date object
	 * @return formatted date string or empty string if error occurs
	 */
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * Returns a date with days added or subtracted (method not implemented).
	 * 
	 * @param date the original date
	 * @param day  number of days to add/subtract
	 * @return null (currently unimplemented)
	 */
	public static Date getDate(Date date, int day) {
		return null;
	}

	/**
	 * Parses a timestamp from a string using application time format.
	 * 
	 * @param val the timestamp string
	 * @return Timestamp object or null if parsing fails
	 */
	public static Timestamp getTimestamp(String val) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Converts a long value to a Timestamp.
	 * 
	 * @param l the long value representing time in milliseconds
	 * @return Timestamp object or null if error occurs
	 */
	public static Timestamp getTimestamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * Returns the current system timestamp.
	 * 
	 * @return current timestamp
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
		}
		return timeStamp;
	}

	/**
	 * Converts a Timestamp to long (milliseconds).
	 * 
	 * @param tm the timestamp object
	 * @return time in milliseconds or 0 if error occurs
	 */
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Main method for testing the utility methods.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		// Test getString
		System.out.println("getString Test:");
		System.out.println("Original: '  Hello World  ' -> Trimmed: '" + getString("  Hello World  ") + "'");
		System.out.println("Null input: " + getString(null));

		// Test getStringData
		System.out.println("\ngetStringData Test:");
		System.out.println("Object to String: " + getStringData(1234));
		System.out.println("Null Object: " + getStringData(null) + "");

		// Test getInt
		System.out.println("\ngetInt Test:");
		System.out.println("Valid Integer String: '124' -> " + getInt("124"));
		System.out.println("Invalid Integer String: 'abc' -> " + getInt("abc"));
		System.out.println("Null String: -> " + getInt(null));

		// Test getLong
		System.out.println("\ngetLong Test:");
		System.out.println("Valid Long String: '123456789' -> " + getLong("123456789"));
		System.out.println("Invalid Long String: 'abc' -> " + getLong("abc"));

		// Test getDate
		System.out.println("\ngetDate Test:");
		String dateStr = "10/15/2024";
		Date date = getDate(dateStr);
		System.out.println("String to Date: '" + dateStr + "' -> " + date);

		// Test getDateString
		System.out.println("\ngetDateString Test:");
		System.out.println("Date to String: '" + getDateString(new Date()) + "'");

		// Test getTimestamp (String)
		System.out.println("\ngetTimestamp(String) Test:");
		String timestampStr = "10/15/2024 10:30:45";
		Timestamp timestamp = getTimestamp(timestampStr);
		System.out.println("String to Timestamp: '" + timestampStr + "' -> " + timestamp);

		// Test getTimestamp (long)
		System.out.println("\ngetTimestamp(long) Test:");
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp ts = getTimestamp(currentTimeMillis);
		System.out.println("Current Time Millis to Timestamp: '" + currentTimeMillis + "' -> " + ts);

		// Test getCurrentTimestamp
		System.out.println("\ngetCurrentTimestamp Test:");
		Timestamp currentTimestamp = getCurrentTimestamp();
		System.out.println("Current Timestamp: " + currentTimestamp);

		// Test getTimestamp(Timestamp)
		System.out.println("\ngetTimestamp(Timestamp) Test:");
		System.out.println("Timestamp to long: " + getTimestamp(currentTimestamp));
	}
}
