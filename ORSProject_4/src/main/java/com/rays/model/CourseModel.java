package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CourseBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * Model class for managing Course data in the database. Provides methods for
 * CRUD operations and search functionality.
 * 
 * @author Aastik Sahu
 */
public class CourseModel {
	
	Logger log = Logger.getLogger(CourseModel.class);

	/**
	 * Returns the next primary key for the course table.
	 *
	 * @return next primary key value
	 * @throws DatabaseException if a database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("CourseModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_course");
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
		
		log.debug("CourseModel nextPk() Method Ended");
		return pk + 11;
	}

	/**
	 * Adds a new Course record to the database.
	 *
	 * @param bean the CourseBean object containing course data
	 * @return primary key of the newly added course
	 * @throws ApplicationException     if an application error occurs
	 * @throws DuplicateRecordException if the course name already exists
	 */
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("CourseModel add() Method Started");
		
		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourseName = findByName(bean.getName());
		if (duplicateCourseName != null) {
			throw new DuplicateRecordException("Course Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("CourseModel add() Method Ended");
		return pk;
	}

	/**
	 * Updates an existing Course record in the database.
	 *
	 * @param bean the CourseBean object containing updated data
	 * @throws ApplicationException     if an application error occurs
	 * @throws DuplicateRecordException if another course with the same name exists
	 */
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("CourseModel update() Method Started");
		
		Connection conn = null;

		CourseBean existBean = findByName(bean.getName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course is Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_course set name = ?, duration = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDuration());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());

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
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel update() Method Ended");
	}

	/**
	 * Deletes a Course record by ID.
	 *
	 * @param bean the CourseBean object containing the ID to delete
	 * @throws ApplicationException if an application error occurs
	 */
	public void delete(CourseBean bean) throws ApplicationException {
		
		log.debug("CourseModel delete() Method Started");
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("CourseModel delete() Method Ended");
	}

	/**
	 * Finds a Course by primary key.
	 *
	 * @param pk the primary key
	 * @return the CourseBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public CourseBean findByPk(long pk) throws ApplicationException {
		
		log.debug("CourseModel findByPk() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_course where id = ?");
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Course byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("CourseModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a Course by name.
	 *
	 * @param name the course name
	 * @return the CourseBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public CourseBean findByName(String name) throws ApplicationException {
		
		log.debug("CourseModel findByName() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_course where name = ?");
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Course byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("CourseModel findByName() Method Ended");
		return bean;
	}

	/**
	 * Searches for courses based on given criteria and pagination.
	 *
	 * @param bean     the search criteria
	 * @param pageNo   current page number
	 * @param PageSize number of records per page
	 * @return list of matching CourseBean objects
	 * @throws ApplicationException if an application error occurs
	 */
	public List search(CourseBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("CourseModel search() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_course where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" and duration like '" + bean.getDuration() + "%'");
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
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("CourseModel search() Method Ended");
		return list;
	}

	/**
	 * Returns a list of all courses.
	 *
	 * @return list of CourseBean objects
	 * @throws ApplicationException if an application error occurs
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
