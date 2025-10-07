package com.rays.bean;

/**
 * The {@code DropdownListBean} interface defines methods required for
 * populating dropdown lists in UI components.
 * <p>
 * Implementing classes must provide a key (usually an ID) and a value (usually
 * a display name) for the dropdown item.
 * 
 * @author Aastik Sahu
 */
public interface DropdownListBean {

	/**
	 * Returns the key used for the dropdown item, typically the unique identifier.
	 *
	 * @return the key as a {@code String}
	 */
	public String getKey();

	/**
	 * Returns the value to be displayed in the dropdown list.
	 *
	 * @return the display value as a {@code String}
	 */
	public String getValue();

}
