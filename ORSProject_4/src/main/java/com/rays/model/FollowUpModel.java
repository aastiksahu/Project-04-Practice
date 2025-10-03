/**
 * FollowUpModel class provides CRUD operations for FollowUpBean entities.
 * This model class handles database interactions for follow-up records including
 * add, update, delete, find, and search operations. It maintains relationships
 * with Patient and Doctor entities.
 * 
 * @author Aastik Sahu
 * @version 1.0
 * @package com.rays.model
 */

package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.DoctorBean;
import com.rays.bean.FollowUpBean;
import com.rays.bean.PatientBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.util.JDBCDataSource;

public class FollowUpModel {
	
	Logger log = Logger.getLogger(FollowUpModel.class);

	/**
     * Generates the next primary key value for the follow-up table.
     * 
     * @return Integer value representing the next primary key
     * @throws DatabaseException if there is an error in database operation
     */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("FollowUpModel NextPk() Method Started");

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_follow_up");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new DatabaseException("Exceptio :Exception in getting PK");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FollowUpModel NextPk() Method Ended");
		return pk + 1;
	}

	/**
     * Adds a new follow-up record to the database.
     * Automatically resolves patient and doctor names/IDs if provided.
     * 
     * @param bean FollowUpBean object containing follow-up details
     * @return long value representing the generated primary key
     * @throws ApplicationException if there is an error in the application operation
     */
	public long add(FollowUpBean bean) throws ApplicationException {
		
		log.debug("FollowUpModel Add() Method Started");
		
		PatientBean patientBean = new PatientBean();
		PatientModel patientModel = new PatientModel();
		
		DoctorBean doctorBean = new DoctorBean();
		DoctorModel doctorModel = new DoctorModel();

		Connection conn = null;
		int pk = 0;
		
		System.out.println(bean.getPatientId());
		
		if (bean.getPatientId() > 0) {
			patientBean = patientModel.findByPk(bean.getPatientId());
			bean.setPatientName(patientBean.getName());
			
		} else if (bean.getPatientName() != null && bean.getPatientName().length() > 0) {
			patientBean = patientModel.findByName(bean.getPatientName());
			bean.setPatientId(patientBean.getId());
		}
		
		if (bean.getDoctorId() > 0) {
			doctorBean = doctorModel.findByPk(bean.getDoctorId());
			bean.setDoctorName(doctorBean.getName());
			
		} else if (bean.getDoctorName() != null && bean.getDoctorName().length() > 0) {
			doctorBean = doctorModel.findByName(bean.getDoctorName());
			bean.setDoctorId(doctorBean.getId());
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_follow_up values(?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setLong(2, bean.getPatientId());
			pstmt.setString(3, bean.getPatientName());
			pstmt.setLong(4, bean.getDoctorId());
			pstmt.setString(5, bean.getDoctorName());
			pstmt.setDate(6, new java.sql.Date(bean.getVisitDate().getTime()));
			pstmt.setLong(7, bean.getFees());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, bean.getCreatedDatetime());
			pstmt.setTimestamp(11, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
			System.out.println("Data Added Successfully...." + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Add RollBack Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in Add Follow Up");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FollowUpModel Add() Method Ended");
		return pk;
	}

	/**
     * Updates an existing follow-up record in the database.
     * Automatically resolves patient and doctor names/IDs if provided.
     * 
     * @param bean FollowUpBean object containing updated follow-up details
     * @throws ApplicationException if there is an error in the application operation
     */
	public void update(FollowUpBean bean) throws ApplicationException {
		
		log.debug("FollowUpModel Update() Method Started");
		
		PatientBean patientBean = new PatientBean();
		PatientModel patientModel = new PatientModel();
		
		DoctorBean doctorBean = new DoctorBean();
		DoctorModel doctorModel = new DoctorModel();

		Connection conn = null;
		
		if (bean.getPatientId() > 0) {
			patientBean = patientModel.findByPk(bean.getPatientId());
			bean.setPatientName(patientBean.getName());
			
		} else if (bean.getPatientName() != null && bean.getPatientName().length() > 0) {
			patientBean = patientModel.findByName(bean.getPatientName());
			bean.setPatientId(patientBean.getId());
		}
		
		if (bean.getDoctorId() > 0) {
			doctorBean = doctorModel.findByPk(bean.getDoctorId());
			bean.setDoctorName(doctorBean.getName());
			
		} else if (bean.getDoctorName() != null && bean.getDoctorName().length() > 0) {
			doctorBean = doctorModel.findByName(bean.getDoctorName());
			bean.setDoctorId(doctorBean.getId());
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_follow_up set patient_id = ?, patient_name = ?, doctor_id = ?, doctor_name = ?, visit_date = ?, fees = ? ,created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setLong(1, bean.getPatientId());
			pstmt.setString(2, bean.getPatientName());
			pstmt.setLong(3, bean.getDoctorId());
			pstmt.setString(4, bean.getDoctorName());
			pstmt.setDate(5, new java.sql.Date(bean.getVisitDate().getTime()));
			pstmt.setLong(6, bean.getFees());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

			System.out.println("Data Updated Successfully..." + i);

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update RollBack Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Follow Up ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FollowUpModel Update() Method Ended");
	}

	/**
     * Deletes a follow-up record from the database.
     * 
     * @param bean FollowUpBean object containing the id of follow-up to be deleted
     * @throws ApplicationException if there is an error in the application operation
     */
	public void delete(FollowUpBean bean) throws ApplicationException {

		log.debug("FollowUpModel Delete() Method Started");
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_follow_up where id = ?");

			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

			System.out.println("Data Deleted Successfully..." + i);

		} catch (Exception e) {

			try {
				conn.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception :Delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Follow Up");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FollowUpModel Delete() Method Ended");
	}

	/**
     * Finds a follow-up record by primary key.
     * 
     * @param pk long value representing the primary key
     * @return FollowUpBean object if found, null otherwise
     * @throws ApplicationException if there is an error in the application operation
     */
	public FollowUpBean findByPk(long pk) throws ApplicationException {
		
		log.debug("FollowUpModel FindByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_follow_up where id = ?");
		
		FollowUpBean bean = null;
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new FollowUpBean();

				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setVisitDate(rs.getDate(6));
				bean.setFees(rs.getLong(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception is getting Follow up by PK");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("FollowUpModel FindByPk() Method Ended");
		return bean;
	}

	/**
     * Searches for follow-up records based on criteria with pagination support.
     * Supports searching by patient ID/name, doctor ID/name, visit date, and fees.
     * 
     * @param bean FollowUpBean object containing search criteria
     * @param pageNo integer value representing page number
     * @param PageSize integer value representing number of records per page
     * @return List of FollowUpBean objects matching search criteria
     * @throws ApplicationException if there is an error in the application operation
     */
	public List search(FollowUpBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("FollowUpModel Search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_follow_up where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			
			if (bean.getPatientId() > 0) {
				sql.append(" and patient_id = " + bean.getPatientId());
			}
			if (bean.getPatientName() != null && bean.getPatientName().length() > 0) {
				sql.append(" and patient_name like '" + bean.getPatientName() + "%'");
			}
			
			if (bean.getDoctorId() > 0) {
				sql.append(" and doctor_id = " + bean.getDoctorId());
			}
			if (bean.getDoctorName() != null && bean.getDoctorName().length() > 0) {
				sql.append(" and doctor_name like '" + bean.getDoctorName() + "%'");
			}
			
			if (bean.getVisitDate() != null && bean.getVisitDate().getTime() > 0) {
				sql.append(" and visit_date like '" + new java.sql.Date(bean.getVisitDate().getTime()) + "%'");
			}
			
			if (bean.getFees() > 0) {
				sql.append(" and fees = " + bean.getFees());
			}
			
		}

		if (PageSize > 0) {

			pageNo = (pageNo - 1) * PageSize;
			sql.append(" limit " + pageNo + "," + PageSize);
		}
		System.out.println(sql.toString());
		List list = new ArrayList();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new FollowUpBean();

				bean.setId(rs.getLong(1));
				bean.setPatientId(rs.getLong(2));
				bean.setPatientName(rs.getString(3));
				bean.setDoctorId(rs.getLong(4));
				bean.setDoctorName(rs.getString(5));
				bean.setVisitDate(rs.getDate(6));
				bean.setFees(rs.getLong(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));

				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Follow Up");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("FollowUpModel Search() Method Ended");
		return list;

	}

	/**
     * Retrieves all follow-up records from the database.
     * 
     * @return List of all FollowUpBean objects
     * @throws Exception if there is an error in the operation
     */
	public List list() throws Exception {
		return search(null, 0, 0);
	}



}