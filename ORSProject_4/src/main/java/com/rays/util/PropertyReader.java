package com.rays.util;

import java.util.ResourceBundle;

/**
 * Utility class to read values from a properties file (ResourceBundle).
 * <p>
 * This class supports fetching property values by key, and also supports
 * parameterized values using placeholders like {0}, {1}, etc.
 * </p>
 * 
 * The properties file it reads from is located at
 * <code>com.rays.bundle.system</code>.
 * 
 * Example usage:
 * 
 * <pre>
 * PropertyReader.getValue("error.require");
 * PropertyReader.getValue("error.require", "Login Id");
 * </pre>
 * 
 * @author Aastik Sahu
 */
public class PropertyReader {

	/** Loads the resource bundle for system configuration */
	private static ResourceBundle rb = ResourceBundle.getBundle("com.rays.bundle.system");

	/**
	 * Returns the value associated with the given key from the properties file. If
	 * the key is not found, the key itself is returned.
	 *
	 * @param key the property key
	 * @return the value corresponding to the key, or the key itself if not found
	 */
	public static String getValue(String key) {
		String val = null;

		try {
			val = rb.getString(key);
		} catch (Exception e) {
			val = key;
		}
		return val;
	}

	/**
	 * Returns the property value by replacing a single parameter placeholder {0}.
	 *
	 * @param key   the property key
	 * @param param the parameter to replace {0}
	 * @return the formatted message string
	 */
	public static String getValue(String key, String param) {
		String msg = getValue(key);
		msg = msg.replace("{0}", param);
		return msg;
	}

	/**
	 * Returns the property value by replacing multiple parameter placeholders {0},
	 * {1}, etc.
	 *
	 * @param key    the property key
	 * @param params the parameters to replace the placeholders
	 * @return the formatted message string
	 */
	public static String getValue(String key, String[] params) {
		String msg = getValue(key);
		for (int i = 0; i < params.length; i++) {
			msg = msg.replace("{" + i + "}", params[i]);
		}
		return msg;
	}

	/**
	 * Demonstrates usage of the PropertyReader methods.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		System.out.println("Single key example:");
		System.out.println(PropertyReader.getValue("error.require"));

		System.out.println("\nSingle parameter replacement example:");
		System.out.println(PropertyReader.getValue("error.require", "loginId"));

		System.out.println("\nMultiple parameter replacement example:");
		String[] params = { "Roll No", "Student Name" };
		System.out.println(PropertyReader.getValue("error.multipleFields", params));
	}
}
