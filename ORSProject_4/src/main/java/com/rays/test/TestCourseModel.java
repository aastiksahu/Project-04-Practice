package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.CourseBean;
import com.rays.model.CourseModel;

/**
 * Test class for {@link CourseModel} to perform unit testing on CRUD
 * operations.
 * <p>
 * It includes methods to test:
 * <ul>
 * <li>Add Course</li>
 * <li>Update Course</li>
 * <li>Delete Course</li>
 * <li>Find Course by Primary Key</li>
 * <li>Find Course by Name</li>
 * <li>Search Courses</li>
 * <li>List all Courses</li>
 * <li>Get Next Primary Key</li>
 * </ul>
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestCourseModel {

	/**
	 * Main method to run all test cases.
	 * 
	 * @param args command line arguments
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
	 * Tests the list method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testlist() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (CourseBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests the search method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testsearch() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		List list = new ArrayList();

		// bean.setId(33);
		// bean.setName("BCA");
		// bean.setDescription("Placement Training");
		// bean.setDuration("3 Year");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (CourseBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}

	/**
	 * Tests the findByName method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testfindByName() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean = model.findByName("B.Tech");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Inalid Course Name...");
		}
	}

	/**
	 * Tests the findByPk method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testfindByPk() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean = model.findByPk(11);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDuration());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Inalid ID...");
		}
	}

	/**
	 * Tests the delete method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testDelete() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean.setId(66);

		model.delete(bean);

	}

	/**
	 * Tests the update method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testUpdate() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean.setName("B.Tec");
		bean.setDuration("4 Year");
		bean.setDescription("B.Tech in Computer Science");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(66);

		model.update(bean);

	}

	/**
	 * Tests the add method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testAdd() throws Exception {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean.setName("Bed");
		bean.setDuration("4 Year");
		bean.setDescription("B.Tech in Computer");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
	 * Tests the nextPk method of {@link CourseModel}.
	 * 
	 * @throws Exception if any exception occurs
	 */
	private static void testNextPk() throws Exception {

		CourseModel model = new CourseModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
