package com.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.TimeTableBean;
import com.rays.model.TimeTableModel;

/**
 * Test class to perform CRUD operations on TimeTableModel. This class tests
 * adding, updating, deleting, searching, listing, and fetching TimeTableBean
 * objects by primary key.
 * 
 * @author Aastik Sahu
 */
public class TestTimeTableModel {

	/**
	 * Main method to execute test methods.
	 * 
	 * @param args command line arguments
	 * @throws Exception when any test method throws exception
	 */
	public static void main(String[] args) throws Exception {

		// Uncomment the test you want to execute
		// testNextPk();
		// testAdd();
		testUpdate();
		// testDelete();
		// testfindByPk();
		// testsearch();
		// testlist();
	}

	/**
	 * Tests listing of all TimeTable records.
	 * 
	 * @throws Exception if an error occurs during listing
	 */
	private static void testlist() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();

		List list = new ArrayList();

		list = model.list();

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (TimeTableBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getSemester());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getExamDate());
			System.out.print("\t" + bean.getExamTime());
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
	 * Tests searching TimeTable records with pagination.
	 * 
	 * @throws Exception if an error occurs during search
	 */
	private static void testsearch() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();

		List list = new ArrayList();

		// Uncomment and set search criteria if needed
		// bean.setId(771);
		// bean.setSemester("8th");
		// bean.setDescription("Placement Training");
		// bean.setExamTime("9 to 12");
		// bean.setCourseId(11);
		// bean.setCourseName("Corporate Java");
		// bean.setSubjectId(4001);
		// bean.setSubjectName("Physics");

		list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {

			bean = (TimeTableBean) it.next();

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getSemester());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getExamDate());
			System.out.print("\t" + bean.getExamTime());
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
	 * Tests finding a TimeTable record by primary key.
	 * 
	 * @throws Exception if an error occurs during find operation
	 */
	private static void testfindByPk() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();

		bean = model.findByPk(767);

		if (bean != null) {

			System.out.print("\t" + bean.getId());
			System.out.print("\t" + bean.getSemester());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getExamDate());
			System.out.print("\t" + bean.getExamTime());
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
	 * Tests deleting a TimeTable record.
	 * 
	 * @throws Exception if an error occurs during deletion
	 */
	private static void testDelete() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();

		bean.setId(782);

		model.delete(bean);

	}

	/**
	 * Tests updating a TimeTable record.
	 * 
	 * @throws Exception if an error occurs during update
	 */
	private static void testUpdate() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setSemester("8th");
		bean.setDescription("Last Semester");
		bean.setExamDate(sdf.parse("2025-06-14"));
		bean.setExamTime("9 to 12");
		bean.setCourseId(1);
		bean.setCourseName("Corporate Java");
		bean.setSubjectId(12);
		// bean.setSubjectName("Core Java");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(782);

		model.update(bean);

	}

	/**
	 * Tests adding a new TimeTable record.
	 * 
	 * @throws Exception if an error occurs during add operation
	 */
	private static void testAdd() throws Exception {

		TimeTableBean bean = new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setSemester("6th");
		bean.setDescription("Semester");
		bean.setExamDate(sdf.parse("2025-06-14"));
		bean.setExamTime("9 to 12");
		bean.setCourseId(11);
		bean.setCourseName("Corporate Java");
		// bean.setSubjectId(1001);
		bean.setSubjectName("Physics");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);

	}

	/**
	 * Tests retrieving the next primary key for TimeTable.
	 * 
	 * @throws Exception if an error occurs during pk retrieval
	 */
	private static void testNextPk() throws Exception {

		TimeTableModel model = new TimeTableModel();

		int i = model.nextPk();

		System.out.println("NextPk is ..." + i);
	}

}
