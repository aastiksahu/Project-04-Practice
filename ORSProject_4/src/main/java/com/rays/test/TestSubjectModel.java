package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.SubjectBean;
import com.rays.model.SubjectModel;

/**
 * Test class to test various CRUD operations and other functionalities of the
 * SubjectModel class.
 * 
 * @author Aastik Sahu
 */
public class TestSubjectModel {

	/**
	 * Main method to run the test methods.
	 * 
	 * @param args command line arguments
	 * @throws Exception if any error occurs during the test execution
	 */
	public static void main(String[] args) throws Exception {

		// Uncomment any test method to run the specific test case
//		testNextPk();
//		testAdd();
		testUpdate();
//		testDelete();
//		testfindByPk();
//		testfindByName();
//		testsearch();
//		testlist();
	}

	/**
	 * Tests listing all subjects by fetching them from the database and printing
	 * their details.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testlist() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (SubjectBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests searching subjects based on criteria set in the bean. Prints the list
	 * of subjects matching the search criteria.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testsearch() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		List list = new ArrayList();

		// Uncomment and set search criteria in bean before running test
		// bean.setId(9001);
		// bean.setName("Maths");
		// bean.setCourseId(22);
		// bean.setCourseName("B.Tech");
		// bean.setDescription("Corporate Java is a Placement training course");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (SubjectBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests finding a subject by its name and prints the details if found.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testfindByName() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean = model.findByName("Chemistry");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Subject Name...");
		}
	}

	/**
	 * Tests finding a subject by its primary key (ID) and prints the details if
	 * found.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testfindByPk() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean = model.findByPk(4002);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Subject Name...");
		}
	}

	/**
	 * Tests deletion of a subject based on its ID.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testDelete() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean.setId(9004);

		model.delete(bean);

	}

	/**
	 * Tests updating an existing subject's details.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testUpdate() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean.setName("Aastik");
		bean.setCourseId(22);
//		bean.setCourseName("Corporate Java");
		bean.setDescription("Corporate Java is a Placement training course");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(9004);

		model.update(bean);

	}

	/**
	 * Tests adding a new subject with the provided details.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testAdd() throws Exception {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean.setName("Aas");
		// bean.setCourseId(11);
		bean.setCourseName("Corporate Java");
		bean.setDescription("Corporate Java is a Placement training course");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
	 * Tests getting the next primary key for the Subject entity.
	 * 
	 * @throws Exception if any error occurs during the operation
	 */
	private static void testNextPk() throws Exception {

		SubjectModel model = new SubjectModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
