package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.CollegeBean;
import com.rays.model.CollegeModel;

/**
 * Test class for CollegeModel.
 * <p>
 * This class contains methods to test all CRUD operations and search/list
 * functions provided by the {@link CollegeModel} class.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestCollegeModel {

	/**
	 * Main method to execute all test methods.
	 * 
	 * @param args command-line arguments
	 * @throws Exception if any exception occurs
	 */
	public static void main(String[] args) throws Exception {

		testNextPk();
		testAdd();
		testUpdate();
		testDelete();
		testfindByPk();
		testfindByName();
		testsearch();
		testlist();
	}

	/**
	 * Tests the list() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testlist() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (CollegeBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}

	/**
	 * Tests the search() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testsearch() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		List list = new ArrayList();

		// bean.setId(2);
		// bean.setName("Madhav");
		// bean.setAddress("Teen Batti Chauraha");
		// bean.setState("Madhya Pradesh");
		// bean.setCity("Indore");
		// bean.setPhoneNo("9669866628");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (CollegeBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}

	/**
	 * Tests the findByName() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testfindByName() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean = model.findByName("Ips Academy");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid College Name...");
		}

	}

	/**
	 * Tests the findByPk() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testfindByPk() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean = model.findByPk(1);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
	 * Tests the delete() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testDelete() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setId(12);

		model.delete(bean);

	}

	/**
	 * Tests the update() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testUpdate() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("Aastik");
		bean.setAddress("Pandariba");
		bean.setState("Madhya Pradesh");
		bean.setCity("Ujjain");
		bean.setPhoneNo("9669866628");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(12);

		model.update(bean);

	}

	/**
	 * Tests the add() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testAdd() throws Exception {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("Aa");
		bean.setAddress("Pandariba");
		bean.setState("Madhya Pradesh");
		bean.setCity("Ujjain");
		bean.setPhoneNo("9669866628");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
	 * Tests the nextPk() method of CollegeModel.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testNextPk() throws Exception {

		CollegeModel model = new CollegeModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
