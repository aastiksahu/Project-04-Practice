package com.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.bean.FollowUpBean;
import com.rays.model.FollowUpModel;

/**
 * Test class for {@link FollowUpModel}.
 * Contains methods to test CRUD operations and search functionality.
 * 
 * Uncomment method calls in {@code main} to test specific operations.
 * 
 * @author Aastik Sahu
 */
public class TestFollowUpModel {

    /**
     * Main method to execute test cases for FollowUpModel.
     *
     * @param args command-line arguments
     * @throws Exception if any exception occurs during execution
     */
    public static void main(String[] args) throws Exception {

//        testNextPk();
//        testAdd();
//        testUpdate();
//        testDelete();
//        testfindByPk();
        testsearch();
//        testlist();
    }

    /**
     * Tests the list method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testlist() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();

        List list = new ArrayList();

        list = model.list();

        Iterator it = list.iterator();

        while (it.hasNext()) {

            bean = (FollowUpBean) it.next();

            System.out.print("\t" + bean.getId());
            System.out.print("\t" + bean.getPatientId());
            System.out.print("\t" + bean.getPatientName());
            System.out.print("\t" + bean.getDoctorId());
            System.out.print("\t" + bean.getDoctorName());
            System.out.print("\t" + bean.getVisitDate());
            System.out.print("\t" + bean.getFees());
            System.out.print("\t" + bean.getCreatedBy());
            System.out.print("\t" + bean.getModifiedBy());
            System.out.print("\t" + bean.getCreatedDatetime());
            System.out.println("\t" + bean.getModifiedDatetime());

        }
    }

    /**
     * Tests the search method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testsearch() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List list = new ArrayList();

        // You can uncomment fields to apply search filters
//        bean.setId(1);
//        bean.setPatientId(3);
//        bean.setPatientName("Jitesh Bagadiya");
//        bean.setDoctorId(5);
//        bean.setDoctorName("Harsha Purohit");
//        bean.setVisitDate(sdf.parse("2025-09-29"));
//        bean.setFees(1500);

        list = model.search(bean, 1, 10);

        Iterator it = list.iterator();

        while (it.hasNext()) {

            bean = (FollowUpBean) it.next();

            System.out.print("\t" + bean.getId());
            System.out.print("\t" + bean.getPatientId());
            System.out.print("\t" + bean.getPatientName());
            System.out.print("\t" + bean.getDoctorId());
            System.out.print("\t" + bean.getDoctorName());
            System.out.print("\t" + bean.getVisitDate());
            System.out.print("\t" + bean.getFees());
            System.out.print("\t" + bean.getCreatedBy());
            System.out.print("\t" + bean.getModifiedBy());
            System.out.print("\t" + bean.getCreatedDatetime());
            System.out.println("\t" + bean.getModifiedDatetime());

        }

    }

    /**
     * Tests the findByPk method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testfindByPk() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();

        bean = model.findByPk(1);

        if (bean != null) {

            System.out.print("\t" + bean.getId());
            System.out.print("\t" + bean.getPatientId());
            System.out.print("\t" + bean.getPatientName());
            System.out.print("\t" + bean.getDoctorId());
            System.out.print("\t" + bean.getDoctorName());
            System.out.print("\t" + bean.getVisitDate());
            System.out.print("\t" + bean.getFees());
            System.out.print("\t" + bean.getCreatedBy());
            System.out.print("\t" + bean.getModifiedBy());
            System.out.print("\t" + bean.getCreatedDatetime());
            System.out.println("\t" + bean.getModifiedDatetime());
        } else {
            System.out.println("Invalid ID...");
        }
    }

    /**
     * Tests the delete method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testDelete() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();

        bean.setId(2);

        model.delete(bean);
    }

    /**
     * Tests the update method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testUpdate() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        bean.setPatientId(2);
//        bean.setPatientName("Raj Shah");
        bean.setDoctorId(5);
//        bean.setDoctorName("Jeet Rathore");
        bean.setVisitDate(sdf.parse("2025-09-26"));
        bean.setFees(1000);
        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
        bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
        bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
        bean.setId(1);

        model.update(bean);
    }

    /**
     * Tests the add method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testAdd() throws Exception {

        FollowUpBean bean = new FollowUpBean();
        FollowUpModel model = new FollowUpModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        bean.setPatientId(3);
//        bean.setPatientName("Raj Chourasiya");
        bean.setDoctorId(5);
//        bean.setDoctorName("Jeet Rathore");
        bean.setVisitDate(sdf.parse("2025-09-26"));
        bean.setFees(500);
        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
        bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
        bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

        model.add(bean);
    }

    /**
     * Tests the nextPk method of {@link FollowUpModel}.
     *
     * @throws Exception if any exception occurs
     */
    private static void testNextPk() throws Exception {

        FollowUpModel model = new FollowUpModel();

        int i = model.nextPk();

        System.out.println("NextPk is ..." + i);
    }

}
