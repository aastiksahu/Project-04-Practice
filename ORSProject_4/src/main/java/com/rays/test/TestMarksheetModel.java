package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.MarksheetBean;
import com.rays.model.MarksheetModel;

/**
 * Test class for {@link MarksheetModel} to test CRUD operations.
 * <p>
 * Includes methods to test:
 * <ul>
 * <li>Add</li>
 * <li>Update</li>
 * <li>Delete</li>
 * <li>Find by PK</li>
 * <li>Find by Roll Number</li>
 * <li>Search</li>
 * <li>List</li>
 * <li>Get Next Primary Key</li>
 * </ul>
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestMarksheetModel {

	/**
	 * Main method to run specific test cases for {@link MarksheetModel}.
	 * 
	 * @param args command line arguments
	 * @throws Exception in case of any error
	 */
	public static void main(String[] args) throws Exception {

//        testNextPk();
//        testAdd();
//        testUpdate();
//        testDelete();
//        testfindByPk();
//        testfindByRollNo();
		testsearch();
//        testlist();
	}

	/**
	 * Tests the list method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testlist() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (MarksheetBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getRollNo());
			System.out.print("\t" + bean.getStudentId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getPhysics());
			System.out.print("\t" + bean.getChemistry());
			System.out.print("\t" + bean.getMaths());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests the search method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testsearch() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		List list = new ArrayList();

		// Uncomment to set search filters
		// bean.setId(2);
		// bean.setRollNo("2012");
		// bean.setStudentId(107);
		// bean.setName("Ashwin Khatri");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (MarksheetBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getRollNo());
			System.out.print("\t" + bean.getStudentId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getPhysics());
			System.out.print("\t" + bean.getChemistry());
			System.out.print("\t" + bean.getMaths());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests the findByRollNo method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindByRollNo() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean = model.findByRollNo("101");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getRollNo());
			System.out.print("\t" + bean.getStudentId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getPhysics());
			System.out.print("\t" + bean.getChemistry());
			System.out.print("\t" + bean.getMaths());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Roll No...");
		}
	}

	/**
	 * Tests the findByPk method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindByPk() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean = model.findByPk(31);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getRollNo());
			System.out.print("\t" + bean.getStudentId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getPhysics());
			System.out.print("\t" + bean.getChemistry());
			System.out.print("\t" + bean.getMaths());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
	 * Tests the delete method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testDelete() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean.setId(31);

		model.delete(bean);
	}

	/**
	 * Tests the update method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testUpdate() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean.setRollNo("101");
		bean.setStudentId(20251);
		bean.setName("Aastik");
		bean.setPhysics(84);
		bean.setChemistry(87);
		bean.setMaths(94);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(31);

		model.update(bean);
	}

	/**
	 * Tests the add method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testAdd() throws Exception {

		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean.setRollNo("101");
		bean.setStudentId(20251);
		bean.setName("Aa");
		bean.setPhysics(84);
		bean.setChemistry(87);
		bean.setMaths(94);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
	}

	/**
	 * Tests the nextPk method of {@link MarksheetModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testNextPk() throws Exception {

		MarksheetModel model = new MarksheetModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
