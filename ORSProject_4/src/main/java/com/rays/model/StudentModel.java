package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CollegeBean;
import com.rays.bean.StudentBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * The StudentModel class provides methods for CRUD operations and search
 * functionality for student records in the database.
 *
 * @author Aastik Sahu
 */
public class StudentModel {
	
	Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * Gets the next primary key from the database.
	 *
	 * @return the next primary key value
	 * @throws DatabaseException if any database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("StudentModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");

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
		
		log.debug("StudentModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Adds a new student record into the database.
	 *
	 * @param bean the StudentBean object containing student details
	 * @return the generated primary key of the new student
	 * @throws ApplicationException     if any application error occurs
	 * @throws DuplicateRecordException if the student email already exists
	 */
	public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("StudentModel add() Method Started");

		CollegeBean collegeBean = new CollegeBean();
		CollegeModel collegeModel = new CollegeModel();

		Connection conn = null;
		int pk = 0;

		if (bean.getCollegeId() > 0) {

			collegeBean = collegeModel.findByPk(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getName());

		} else if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {

			collegeBean = collegeModel.findByName(bean.getCollegeName());
			bean.setCollegeId(collegeBean.getId());
		}

		StudentBean duplicateStudentEmail = findByEmail(bean.getEmail());

		if (duplicateStudentEmail != null && duplicateStudentEmail.getId() != bean.getId()) {
			throw new DuplicateRecordException("Student Email Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeId());
			pstmt.setString(9, bean.getCollegeName());
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel add() Method Ended");
		return pk;

	}

	/**
	 * Updates an existing student record in the database.
	 *
	 * @param bean the StudentBean object containing updated student details
	 * @throws ApplicationException     if any application error occurs
	 * @throws DuplicateRecordException if the student email already exists
	 */
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("StudentModel update() Method Started");

		Connection conn = null;

		CollegeBean collegeBean = new CollegeBean();
		CollegeModel collegeModel = new CollegeModel();

		if (bean.getCollegeId() > 0) {

			collegeBean = collegeModel.findByPk(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getName());

		} else if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {

			collegeBean = collegeModel.findByName(bean.getCollegeName());
			bean.setCollegeId(collegeBean.getId());
		}

		StudentBean duplicateStudentEmail = findByEmail(bean.getEmail());

		if (duplicateStudentEmail != null && duplicateStudentEmail.getId() != bean.getId()) {
			throw new DuplicateRecordException("Student Email Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_student set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.setLong(13, bean.getId());

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
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel update() Method Ended");

	}

	/**
	 * Deletes a student record from the database.
	 *
	 * @param bean the StudentBean object containing student ID to be deleted
	 * @throws ApplicationException if any application error occurs
	 */
	public void delete(StudentBean bean) throws ApplicationException {
		
		log.debug("StudentModel delete() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");

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
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel delete() Method Ended");

	}

	/**
	 * Finds a student record by primary key.
	 *
	 * @param pk the primary key of the student
	 * @return StudentBean object if found, otherwise null
	 * @throws ApplicationException if any application error occurs
	 */
	public StudentBean findByPk(long pk) throws ApplicationException {
		
		log.debug("StudentModel findByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_student where id = ?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new StudentBean();

				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception is getting Student byPK");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a student record by email.
	 *
	 * @param Email the email of the student
	 * @return StudentBean object if found, otherwise null
	 * @throws ApplicationException if any application error occurs
	 */
	public StudentBean findByEmail(String Email) throws ApplicationException {
		
		log.debug("StudentModel findByEmail() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_student where email = ?");
		StudentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Email);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new StudentBean();

				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception is getting Student by Email");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel findByEmail() Method Ended");
		return bean;
	}

	/**
	 * Searches student records based on given criteria.
	 *
	 * @param bean     the StudentBean object containing search criteria
	 * @param pageNo   the page number for pagination
	 * @param PageSize the number of records per page
	 * @return a list of StudentBean objects matching the search criteria
	 * @throws ApplicationException if any application error occurs
	 */
	public List search(StudentBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("StudentModel search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and first_name like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" and last_name like '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				sql.append(" and dob like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" and gender like '" + bean.getGender() + "%'");
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" and email like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" and college_id = " + bean.getCollegeId());
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" and college_name like '" + bean.getCollegeName() + "%'");
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

				bean = new StudentBean();

				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));

				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("StudentModel search() Method Ended");
		return list;

	}

	/**
	 * Retrieves the list of all student records.
	 *
	 * @return a list of all StudentBean objects
	 * @throws ApplicationException if any application error occurs
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
