package com.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.FacultyBean;
import com.rays.model.FacultyModel;

/**
 * Test class for {@link FacultyModel} to perform unit testing on CRUD
 * operations.
 * <p>
 * Includes tests for:
 * <ul>
 * <li>Add</li>
 * <li>Update</li>
 * <li>Delete</li>
 * <li>Find by Primary Key</li>
 * <li>Find by Email</li>
 * <li>Search</li>
 * <li>List</li>
 * <li>Get Next Primary Key</li>
 * </ul>
 * </p>
 * 
 * @author Aastik Sahu
 */
public class TestFacultyModel {

	/**
	 * Main method to execute test cases.
	 * 
	 * @param args command line arguments
	 * @throws Exception in case of any error
	 */
	public static void main(String[] args) throws Exception {

//      testNextPk();
		testAdd();
//      testUpdate();
//      testDelete();
//      testfindByPk();
//      testfindbyEmail();
//      testsearch();
//      testlist();
	}

	/**
	 * Tests the list method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testlist() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (FacultyBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getSubjectId());
			System.out.print("\t" + bean.getSubjectName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
	 * Tests the search method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testsearch() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List list = new ArrayList();

		// Set search parameters here if needed

		list = model.search(bean, 2, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (FacultyBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getEmail());
			System.out.print("\t" + bean.getCollegeId());
			System.out.print("\t" + bean.getCollegeName());
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getSubjectId());
			System.out.print("\t" + bean.getSubjectName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}

	}

	/**
	 * Tests the findByEmail method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindbyEmail() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		bean = model.findByEmail("kalpana@gmail.com");

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
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getSubjectId());
			System.out.print("\t" + bean.getSubjectName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid Faculty Email...");
		}
	}

	/**
	 * Tests the findByPk method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testfindByPk() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		bean = model.findByPk(700016);

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
			System.out.print("\t" + bean.getCourseId());
			System.out.print("\t" + bean.getCourseName());
			System.out.print("\t" + bean.getSubjectId());
			System.out.print("\t" + bean.getSubjectName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
	 * Tests the delete method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testDelete() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		bean.setId(700017);

		model.delete(bean);

	}

	/**
	 * Tests the update method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testUpdate() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFirstName("Mamta");
		bean.setLastName("korde");
		bean.setDob(sdf.parse("1976-12-20"));
		bean.setGender("Female");
		bean.setMobileNo("9407126877");
		bean.setEmail("mamta@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("Rays");
		bean.setCourseId(33);
		bean.setCourseName("Corporate Java");
		bean.setSubjectId(4003);
		bean.setSubjectName("Core Java");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(700016);

		model.update(bean);

	}

	/**
	 * Tests the add method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testAdd() throws Exception {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFirstName("Kalpana");
		bean.setLastName("Sahu");
		bean.setDob(sdf.parse("1976-12-20"));
		bean.setGender("Female");
		bean.setMobileNo("9407126877");
		bean.setEmail("kalpana@gmail.com");
		// bean.setCollegeId(1);
		bean.setCollegeName("Rays");
		// bean.setCourseId(33);
		bean.setCourseName("Corporate Java");
		// bean.setSubjectId(4003);
		bean.setSubjectName("Physics");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
	 * Tests the nextPk method of {@link FacultyModel}.
	 * 
	 * @throws Exception if any error occurs
	 */
	private static void testNextPk() throws Exception {

		FacultyModel model = new FacultyModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
