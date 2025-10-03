package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CourseBean;
import com.rays.bean.SubjectBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * The SubjectModel class handles all database operations related to the Subject
 * entity. It provides methods for CRUD operations such as add, update, delete,
 * find, and search.
 * 
 * @author Aastik Sahu
 */
public class SubjectModel {
	
	Logger log = Logger.getLogger(SubjectModel.class);

	/**
	 * Get the next primary key from the database.
	 * 
	 * @return the next primary key value
	 * @throws DatabaseException if any database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("SubjectModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_subject");
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
		
		log.debug("SubjectModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Add a new Subject record into the database.
	 * 
	 * @param bean the SubjectBean object containing subject details
	 * @return the primary key of the newly added subject
	 * @throws ApplicationException     if any application error occurs
	 * @throws DuplicateRecordException if the subject name already exists
	 */
	public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("SubjectModel add() Method Started");

		CourseBean courseBean = new CourseBean();
		CourseModel courseModel = new CourseModel();

		Connection conn = null;
		int pk = 0;

		if (bean.getCourseId() > 0) {
			courseBean = courseModel.findByPk(bean.getCourseId());
			bean.setCourseName(courseBean.getName());
		} else if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
			courseBean = courseModel.findByName(bean.getCourseName());
			bean.setCourseId(courseBean.getId());
		}

		SubjectBean duplicateSubjectName = findByName(bean.getName());

		if (duplicateSubjectName != null && duplicateSubjectName.getId() != bean.getId()) {
			throw new DuplicateRecordException("Subject Name Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getName());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getDescription());
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
			throw new ApplicationException("Exception : Exception in Add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("SubjectModel add() Method Ended");
		return pk;
	}

	/**
	 * Update an existing Subject record in the database.
	 * 
	 * @param bean the SubjectBean object containing updated details
	 * @throws ApplicationException     if any application error occurs
	 * @throws DuplicateRecordException if the subject name already exists
	 */
	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("SubjectModel update() Method Started");

		CourseBean courseBean = new CourseBean();
		CourseModel courseModel = new CourseModel();

		Connection conn = null;

		if (bean.getCourseId() > 0) {
			courseBean = courseModel.findByPk(bean.getCourseId());
			bean.setCourseName(courseBean.getName());
		} else if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
			courseBean = courseModel.findByName(bean.getCourseName());
			bean.setCourseId(courseBean.getId());
		}

		SubjectBean duplicateSubjectName = findByName(bean.getName());

		if (duplicateSubjectName != null && duplicateSubjectName.getId() != bean.getId()) {
			throw new DuplicateRecordException("Subject Name Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_subject set name = ?, course_id = ?, course_name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(4, bean.getDescription());
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
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel update() Method Ended");
	}

	/**
	 * Delete a Subject record from the database.
	 * 
	 * @param bean the SubjectBean object containing the subject id to delete
	 * @throws ApplicationException if any application error occurs
	 */
	public void delete(SubjectBean bean) throws ApplicationException {
		
		log.debug("SubjectModel delete() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("SubjectModel delete() Method Ended");
	}

	/**
	 * Find a Subject by its primary key.
	 * 
	 * @param pk the primary key
	 * @return the SubjectBean object
	 * @throws ApplicationException if any application error occurs
	 */
	public SubjectBean findByPk(long pk) throws ApplicationException {
		
		log.debug("SubjectModel findByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_subject where id = ?");
		SubjectBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Subject byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("SubjectModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Find a Subject by its name.
	 * 
	 * @param Name the subject name
	 * @return the SubjectBean object
	 * @throws ApplicationException if any application error occurs
	 */
	public SubjectBean findByName(String Name) throws ApplicationException {
		
		log.debug("SubjectModel findByName() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_subject where name = ?");
		SubjectBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Subject by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("SubjectModel findByName() Method Ended");
		return bean;
	}

	/**
	 * Search subjects based on given criteria with pagination.
	 * 
	 * @param bean     the SubjectBean containing search filters
	 * @param pageNo   the page number
	 * @param PageSize the size of each page
	 * @return list of subjects
	 * @throws ApplicationException if any application error occurs
	 */
	public List search(SubjectBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("SubjectModel search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" and course_id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
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
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
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
			throw new ApplicationException("Exception : Exception in Search Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("SubjectModel search() Method Ended");
		return list;
	}

	/**
	 * Get list of all subjects.
	 * 
	 * @return list of subjects
	 * @throws ApplicationException if any application error occurs
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
