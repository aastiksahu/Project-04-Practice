package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.RoleBean;
import com.rays.model.RoleModel;

/**
 * Test class for {@link RoleModel} to validate CRUD operations.
 * <p>
 * This class includes test methods for:
 * <ul>
 * <li>Adding a role</li>
 * <li>Updating a role</li>
 * <li>Deleting a role</li>
 * <li>Finding a role by primary key</li>
 * <li>Finding a role by name</li>
 * <li>Searching roles</li>
 * <li>Listing all roles</li>
 * <li>Getting the next primary key</li>
 * </ul>
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestRoleModel {

	/**
	 * Main method to execute test cases for {@link RoleModel}.
	 *
	 * @param args command-line arguments
	 * @throws Exception if any exception occurs during testing
	 */
	public static void main(String[] args) throws Exception {

//        testNextPk();
//        testAdd();
//        testUpdate();
//        testDelete();
//        testfindByPk();
//        testfindByName();
//        testsearch();
		testlist();
	}

	/**
	 * Tests the list functionality of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testlist() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (RoleBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests the search functionality of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testsearch() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		List list = new ArrayList();

		// bean.setId(44444);
		// bean.setName("Kiosk");
		// bean.setDescription("Faculty has limited Access.");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (RoleBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}

	/**
	 * Tests the findByName method of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindByName() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean = model.findByName("del");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Role Name...");
		}

	}

	/**
	 * Tests the findByPk method of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindByPk() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean = model.findByPk(66666);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}

	}

	/**
	 * Tests the delete functionality of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testDelete() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setId(44445);

		model.delete(bean);
	}

	/**
	 * Tests the update functionality of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testUpdate() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setName("del");
		bean.setDescription("delete has limited Access.");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(66666);

		model.update(bean);
	}

	/**
	 * Tests the add functionality of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testAdd() throws Exception {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setName("delete");
		bean.setDescription("delete has limited Access.");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
	}

	/**
	 * Tests the nextPk method of {@link RoleModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testNextPk() throws Exception {

		RoleModel model = new RoleModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
