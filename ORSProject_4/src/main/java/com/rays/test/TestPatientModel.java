/**
 * TestPatientModel class provides test cases for PatientModel class.
 * This test class verifies the CRUD operations and functionality of the PatientModel
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

import com.rays.bean.PatientBean;
import com.rays.model.PatientModel;


public class TestPatientModel {

	/**
     * Main method to execute test cases for PatientModel.
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
//		testsearch();
//		 testlist();
	}
	
	/**
     * Tests the list method to retrieve all patient records from the database.
     * 
     * @throws Exception if the list operation fails
     */
	private static void testlist() throws Exception {

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (PatientBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDateOfVisit());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getDisease());
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

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List list = new ArrayList();

		// Set search criteria if needed
		// bean.setId(1);
		// bean.setName("Aastik Sahu");
		// bean.setDateOfVisit(sdf.parse("2025-09-25"));
		// bean.setMobileNo("9669866628");
		// bean.setDisease("Malaria");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (PatientBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDateOfVisit());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getDisease());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}
	}

	/**
     * Tests the findByPk method to retrieve a patient record by primary key.
     * 
     * @throws Exception if the find operation fails
     */
	private static void testfindByPk() throws Exception {

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();

		bean = model.findByPk(1);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDateOfVisit());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getDisease());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		} else {
			System.out.println("Invalid ID...");
		}
	}

	/**
     * Tests the delete method to remove a patient record from the database.
     * 
     * @throws Exception if the delete operation fails
     */
	private static void testDelete() throws Exception {

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();

		bean.setId(1);

		model.delete(bean);

	}

	/**
     * Tests the update method to modify an existing patient record.
     * 
     * @throws Exception if the update operation fails
     */
	private static void testUpdate() throws Exception {

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setName("Aastik Sahu");
		bean.setDateOfVisit(sdf.parse("2025-09-25"));
		bean.setMobileNo("9669866628");
		bean.setDisease("Malaria");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(2);

		model.update(bean);

	}

	/**
     * Tests the add method to create a new patient record in the database.
     * 
     * @throws Exception if the add operation fails
     */
	private static void testAdd() throws Exception {

		PatientBean bean = new PatientBean();
		PatientModel model = new PatientModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setName("Jeet Rathore");
		bean.setDateOfVisit(sdf.parse("2025-09-25"));
		bean.setMobileNo("9752971930");
		bean.setDisease("Influenza (The Flu)");
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

		PatientModel model = new PatientModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}
}