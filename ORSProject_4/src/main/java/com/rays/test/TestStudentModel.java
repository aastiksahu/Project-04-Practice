package com.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.StudentBean;
import com.rays.model.StudentModel;

/**
 * Test class for {@link StudentModel} to perform CRUD operations and
 * validations.
 * <p>
 * Includes test methods for:
 * <ul>
 * <li>Adding a student</li>
 * <li>Updating student data</li>
 * <li>Deleting a student</li>
 * <li>Finding by primary key or email</li>
 * <li>Searching students</li>
 * <li>Listing all students</li>
 * <li>Getting next primary key</li>
 * </ul>
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestStudentModel {

	/**
	 * Main method to invoke test cases.
	 *
	 * @param args command-line arguments
	 * @throws Exception in case of any error
	 */
	public static void main(String[] args) throws Exception {

//        testNextPk();
//        testAdd();
		testUpdate();
//        testDelete();
//        testfindByPk();
//        testfindByEmail();
//        testsearch();
//        testlist();
	}

	/**
	 * Tests listing of all students from the database.
	 *
	 * @throws Exception if listing fails
	 */
	private static void testlist() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (StudentBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}
	}

	/**
	 * Tests searching for students based on criteria.
	 *
	 * @throws Exception if search fails
	 */
	private static void testsearch() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List list = new ArrayList();

		// Uncomment to set search criteria
//        bean.setId(111);
//        bean.setFirstName("Aditi");
//        bean.setLastName("Shukala");
//        bean.setDob(sdf.parse("2000-09-05"));
//        bean.setGender("Female");
//        bean.setMobileNo("8200133445");
//        bean.setEmail("avi@gmail.com");
//        bean.setCollegeId(10);
//        bean.setCollegeName("SGSITS");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (StudentBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}

	}

	/**
	 * Tests finding a student by email.
	 *
	 * @throws Exception if email not found
	 */
	private static void testfindByEmail() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		bean = model.findByEmail("aastik@gmail.com");

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Email...");
		}
	}

	/**
	 * Tests finding a student by primary key (ID).
	 *
	 * @throws Exception if ID not found
	 */
	private static void testfindByPk() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		bean = model.findByPk(131);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
	 * Tests deletion of a student by ID.
	 *
	 * @throws Exception if deletion fails
	 */
	private static void testDelete() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		bean.setId(131);

		model.delete(bean);
	}

	/**
	 * Tests updating a student record.
	 *
	 * @throws Exception if update fails
	 */
	private static void testUpdate() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFirstName("Aastik");
		bean.setLastName("Sahu");
		bean.setDob(sdf.parse("1998-02-03"));
		bean.setGender("Male");
		bean.setMobileNo("9669866628");
		bean.setEmail("aastik@gmail.com");
		// bean.setCollegeId(1);
		bean.setCollegeName("IPS Academy");
		bean.setCreatedBy("Faculty");
		bean.setModifiedBy("Faculty");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(131);

		model.update(bean);
	}

	/**
	 * Tests adding a new student.
	 *
	 * @throws Exception if insertion fails
	 */
	private static void testAdd() throws Exception {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFirstName("Aastik");
		bean.setLastName("Sa");
		bean.setDob(sdf.parse("1998-02-03"));
		bean.setGender("Male");
		bean.setMobileNo("9669866628");
		bean.setEmail("aastik@gmail.com");
		// bean.setCollegeId(1);
		bean.setCollegeName("IPS Academy");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
	}

	/**
	 * Tests getting the next available primary key.
	 *
	 * @throws Exception if failed
	 */
	private static void testNextPk() throws Exception {

		StudentModel model = new StudentModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
