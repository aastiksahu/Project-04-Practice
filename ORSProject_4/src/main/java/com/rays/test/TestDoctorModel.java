/**
 * TestDoctorModel class provides test cases for DoctorModel class.
 * This test class verifies the CRUD operations and functionality of the DoctorModel
 * including next primary key generation, add, update, delete, find, and search operations.
 * 
 * @author Aastik Sahu
 * @version 1.0
 * @package com.rays.test
 */

package com.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.DoctorBean;
import com.rays.model.DoctorModel;

public class TestDoctorModel {

	/**
     * Main method to execute test cases for DoctorModel.
     * Uncomment specific test methods to run individual tests.
     * 
     * @param args command line arguments
     * @throws Exception if any test operation fails
     */
	public static void main(String[] args) throws Exception {

//		testNextPk();
//		testAdd();
//		testUpdate();
//		testDelete();
//		testfindByPk();
		testsearch();
//		 testlist();
	}

	/**
     * Tests the list method to retrieve all doctor records from the database.
     * 
     * @throws Exception if the list operation fails
     */
	private static void testlist() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (DoctorBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getExpertise());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
     * Tests the search method with optional criteria and pagination.
     * Can be customized with specific search criteria.
     * 
     * @throws Exception if the search operation fails
     */
	private static void testsearch() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List list = new ArrayList();

		// Set search criteria if needed
		// bean.setId(1);
		// bean.setName("Aastik Sahu");
		// bean.setDob(sdf.parse("2025-09-25"));
		// bean.setMobileNo("9669866628");
		// bean.setExpertise("General Physician (MBBS Doctor)");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (DoctorBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getExpertise());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
     * Tests the findByPk method to retrieve a doctor record by primary key.
     * 
     * @throws Exception if the find operation fails
     */
	private static void testfindByPk() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();

		bean = model.findByPk(1);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getExpertise());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
     * Tests the delete method to remove a doctor record from the database.
     * 
     * @throws Exception if the delete operation fails
     */
	private static void testDelete() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();

		bean.setId(7);

		model.delete(bean);

	}

	/**
     * Tests the update method to modify an existing doctor record.
     * 
     * @throws Exception if the update operation fails
     */
	private static void testUpdate() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setName("Aastik Sahu");
		bean.setDob(sdf.parse("2025-09-25"));
		bean.setMobileNo("9669866628");
		bean.setExpertise("General Physician (MBBS Doctor)");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(2);

		model.update(bean);

	}

	/**
     * Tests the add method to create a new doctor record in the database.
     * 
     * @throws Exception if the add operation fails
     */
	private static void testAdd() throws Exception {

		DoctorBean bean = new DoctorBean();
		DoctorModel model = new DoctorModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setName("test1");
		bean.setDob(sdf.parse("2025-09-25"));
		bean.setMobileNo("9907755803");
		bean.setExpertise("test1");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
     * Tests the nextPk method to generate the next primary key value.
     * 
     * @throws Exception if the nextPk operation fails
     */
	private static void testNextPk() throws Exception {

		DoctorModel model = new DoctorModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}
}