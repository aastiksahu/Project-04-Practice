/**
 * DoctorModel class provides CRUD operations for DoctorBean entities.
 * This model class handles database interactions for doctor records including
 * add, update, delete, find, and search operations.
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
import com.rays.bean.PatientBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.util.JDBCDataSource;

public class DoctorModel {
	
	Logger log = Logger.getLogger(DoctorModel.class);

	/**
     * Generates the next primary key value for the doctor table.
     * 
     * @return Integer value representing the next primary key
     * @throws DatabaseException if there is an error in database operation
     */
	public Integer nextPk() throws DatabaseException {

		log.debug("DoctorModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_doctor");

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
		log.debug("DoctorModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
     * Adds a new doctor record to the database.
     * 
     * @param bean DoctorBean object containing doctor details
     * @return long value representing the generated primary key
     * @throws ApplicationException if there is an error in the application operation
     */
	public long add(DoctorBean bean) throws ApplicationException {
		
		log.debug("DoctorModel Add() Method Started");

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_doctor values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getMobileNo());
			pstmt.setString(5, bean.getExpertise());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add Doctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("DoctorModel Add() Method Ended");
		return pk;
	}

	/**
     * Updates an existing doctor record in the database.
     * 
     * @param bean DoctorBean object containing updated doctor details
     * @throws ApplicationException if there is an error in the application operation
     */
	public void update(DoctorBean bean) throws ApplicationException {
		
		log.debug("DoctorModel Update() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_doctor set name = ?, dob = ?, mobile_no = ?, expertise = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(3, bean.getMobileNo());
			pstmt.setString(4, bean.getExpertise());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

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
			throw new ApplicationException("Exception in updating Doctor ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("DoctorModel Update() Method Ended");
	}

	/**
     * Deletes a doctor record from the database.
     * 
     * @param bean DoctorBean object containing the id of doctor to be deleted
     * @throws ApplicationException if there is an error in the application operation
     */
	public void delete(DoctorBean bean) throws ApplicationException {
		
		log.debug("DoctorModel Delete() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_doctor where id = ?");

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
			throw new ApplicationException("Exception : Exception in delete Doctor");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("DoctorModel Delete() Method Ended");
	}

	/**
     * Finds a doctor record by primary key.
     * 
     * @param pk long value representing the primary key
     * @return DoctorBean object if found, null otherwise
     * @throws ApplicationException if there is an error in the application operation
     */
	public DoctorBean findByPk(long pk) throws ApplicationException {
		
		log.debug("DoctorModel FindByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_doctor where id = ?");
		DoctorBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DoctorBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setExpertise(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception is getting Doctor byPK");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		log.debug("DoctorModel FindByPk() Method Ended");
		return bean;
	}
	
	/**
     * Finds a doctor record by name.
     * 
     * @param name String value representing the doctor's name
     * @return DoctorBean object if found, null otherwise
     * @throws ApplicationException if there is an error in the application operation
     */
	public DoctorBean findByName(String name) throws ApplicationException {
		
		log.debug("DoctorModel FindByName() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_doctor where name = ?");

		DoctorBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DoctorBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setExpertise(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Doctor by name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("DoctorModel FindByName() Method Ended");
		return bean;
	}

	/**
     * Searches for doctor records based on criteria with pagination support.
     * 
     * @param bean DoctorBean object containing search criteria
     * @param pageNo integer value representing page number
     * @param PageSize integer value representing number of records per page
     * @return List of DoctorBean objects matching search criteria
     * @throws ApplicationException if there is an error in the application operation
     */
	public List search(DoctorBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("DoctorModel Search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_doctor where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sql.append(" and dob like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			if (bean.getExpertise() != null && bean.getExpertise().length() > 0) {
				sql.append(" and expertise like '" + bean.getExpertise() + "%'");
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '" + bean.getMobileNo() + "%'");
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

				bean = new DoctorBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setExpertise(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Doctor");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("DoctorModel Search() Method Ended");
		return list;

	}

	/**
     * Retrieves all doctor records from the database.
     * 
     * @return List of all DoctorBean objects
     * @throws ApplicationException if there is an error in the application operation
     */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}