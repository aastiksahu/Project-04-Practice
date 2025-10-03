package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CourseBean;
import com.rays.bean.SubjectBean;
import com.rays.bean.TimeTableBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * TimeTable Model class contains CRUD operations for TimeTable.
 * 
 * @author Aastik Sahu
 */
public class TimeTableModel {
	
	Logger log = Logger.getLogger(TimeTableModel.class);

	/**
	 * Generates the next primary key value for the timetable table.
	 *
	 * @return the next primary key value
	 * @throws DatabaseException if a database access error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("TimeTableModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_timetable");

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
		
		log.debug("TimeTableModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Adds a TimeTable record to the database.
	 *
	 * @param bean the TimeTableBean to be added
	 * @return the generated primary key
	 * @throws ApplicationException     if an application error occurs
	 * @throws DuplicateRecordException if a duplicate record is found
	 */
	public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("TimeTableModel add() Method Started");

		CourseBean courseBean = new CourseBean();
		CourseModel courseModel = new CourseModel();

		SubjectBean subjectBean = new SubjectBean();
		SubjectModel subjectModel = new SubjectModel();

		Connection conn = null;
		int pk = 0;

		if (bean.getCourseId() > 0) {

			courseBean = courseModel.findByPk(bean.getCourseId());
			bean.setCourseName(courseBean.getName());

		} else if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {

			courseBean = courseModel.findByName(bean.getCourseName());
			bean.setCourseId(courseBean.getId());
		}

		if (bean.getSubjectId() > 0) {

			subjectBean = subjectModel.findByPk(bean.getSubjectId());
			bean.setSubjectName(subjectBean.getName());

		} else if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {

			subjectBean = subjectModel.findByName(bean.getSubjectName());
			bean.setSubjectId(subjectBean.getId());
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getSemester());
			pstmt.setString(3, bean.getDescription());
			pstmt.setDate(4, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(5, bean.getExamTime());
			pstmt.setLong(6, bean.getCourseId());
			pstmt.setString(7, bean.getCourseName());
			pstmt.setLong(8, bean.getSubjectId());
			pstmt.setString(9, bean.getSubjectName());
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
			throw new ApplicationException("Exception : Exception in Add TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("TimeTableModel add() Method Ended");
		return pk;

	}

	/**
	 * Updates a TimeTable record in the database.
	 *
	 * @param bean the TimeTableBean to be updated
	 * @throws ApplicationException     if an application error occurs
	 * @throws DuplicateRecordException if a duplicate record is found
	 */
	public void update(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("TimeTableModel update() Method Started");

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.findByPk(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_timetable set semester = ?, description = ?, exam_date = ?, exam_time = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getSemester());
			pstmt.setString(2, bean.getDescription());
			pstmt.setDate(3, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(4, bean.getExamTime());
			pstmt.setLong(5, bean.getCourseId());
			pstmt.setString(6, bean.getCourseName());
			pstmt.setLong(7, bean.getSubjectId());
			pstmt.setString(8, bean.getSubjectName());
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
			throw new ApplicationException("Exception in updating TimeTable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("TimeTableModel update() Method Ended");

	}

	/**
	 * Deletes a TimeTable record from the database.
	 *
	 * @param bean the TimeTableBean to be deleted
	 * @throws ApplicationException if an application error occurs
	 */
	public void delete(TimeTableBean bean) throws ApplicationException {
		
		log.debug("TimeTableModel delete() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_timetable where id = ?");

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
			throw new ApplicationException("Exception : Exception in delete TimeTable");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel delete() Method Ended");

	}

	/**
	 * Finds a TimeTable record by primary key.
	 *
	 * @param pk the primary key of the record to find
	 * @return the TimeTableBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public TimeTableBean findByPk(long pk) throws ApplicationException {
		
		log.debug("TimeTableModel findByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_timetable where id = ?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new TimeTableBean();

				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception is getting TimeTable byPK");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Searches for TimeTable records with pagination.
	 *
	 * @param bean     the TimeTableBean containing search criteria
	 * @param pageNo   the page number
	 * @param PageSize the page size
	 * @return a list of TimeTableBean matching the search criteria
	 * @throws ApplicationException if an application error occurs
	 */
	public List search(TimeTableBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("TimeTableModel search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getSemester() != null && bean.getSemester().length() > 0) {
				sql.append(" and semester like '" + bean.getSemester() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
			if (bean.getExamDate() != null && bean.getExamDate().getTime() > 0) {
				sql.append(" and exam_date like '" + new java.sql.Date(bean.getExamDate().getTime()) + "%'");
			}
			if (bean.getExamTime() != null && bean.getExamTime().length() > 0) {
				sql.append(" and exam_time like '" + bean.getExamTime() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" and course_id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubjectId() > 0) {
				sql.append(" and subject_id = " + bean.getSubjectId());
			}
			if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
				sql.append(" and subject_name like '" + bean.getSubjectName() + "%'");
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

				bean = new TimeTableBean();

				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
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
			throw new ApplicationException("Exception : Exception in Search TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel search() Method Ended");
		return list;

	}

	/**
	 * Gets all TimeTable records.
	 *
	 * @return a list of all TimeTableBean
	 * @throws Exception if any error occurs
	 */
	public List list() throws Exception {
		return search(null, 0, 0);
	}

	/**
	 * Checks for a TimeTable record by course ID and exam date.
	 *
	 * @param courseId the course ID
	 * @param examDate the exam date
	 * @return the TimeTableBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public TimeTableBean checkByCourseName(Long courseId, Date examDate) throws ApplicationException {
		
		log.debug("TimeTableModel checkByCourseName() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_timetable where course_id = ? and exam_date = ?");
		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setDate(2, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel checkByCourseName() Method Ended");
		return bean;
	}

	/**
	 * Checks for a TimeTable record by course ID, subject ID, and exam date.
	 *
	 * @param courseId  the course ID
	 * @param subjectId the subject ID
	 * @param examDate  the exam date
	 * @return the TimeTableBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public TimeTableBean checkBySubjectName(Long courseId, Long subjectId, Date examDate) throws ApplicationException {
		
		log.debug("TimeTableModel checkBySubjectName() Method Started");
		
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and exam_date = ?");
		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setDate(3, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel checkBySubjectName() Method Ended");
		return bean;
	}

	/**
	 * Checks for a TimeTable record by course ID, subject ID, semester, and exam
	 * date.
	 *
	 * @param courseId  the course ID
	 * @param subjectId the subject ID
	 * @param semester  the semester
	 * @param examDate  the exam date
	 * @return the TimeTableBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public TimeTableBean checkBySemester(Long courseId, Long subjectId, String semester, Date examDate)
			throws ApplicationException {
		
		log.debug("TimeTableModel checkBySemester() Method Started");
		
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ?");
		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setString(3, semester);
			pstmt.setDate(4, new java.sql.Date(examDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("TimeTableModel checkBySemester() Method Ended");
		return bean;
	}

	/**
	 * Checks for a TimeTable record by course ID, subject ID, semester, exam date,
	 * exam time, and description.
	 *
	 * @param courseId    the course ID
	 * @param subjectId   the subject ID
	 * @param semester    the semester
	 * @param examDate    the exam date
	 * @param examTime    the exam time
	 * @param description the description
	 * @return the TimeTableBean if found, otherwise null
	 * @throws ApplicationException if an application error occurs
	 */
	public TimeTableBean checkByExamTime(Long courseId, Long subjectId, String semester, Date examDate, String examTime,
			String description) throws ApplicationException {
		
		log.debug("TimeTableModel checkByExamTime() Method Started");
		
		StringBuffer sql = new StringBuffer(
				"select * from st_timetable where course_id = ? and subject_id = ? and semester = ? and exam_date = ? and exam_time = ? and description = ?");
		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setLong(2, subjectId);
			pstmt.setString(3, semester);
			pstmt.setDate(4, new java.sql.Date(examDate.getTime()));
			pstmt.setString(5, examTime);
			pstmt.setString(6, description);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setSemester(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setExamDate(rs.getDate(4));
				bean.setExamTime(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubjectId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in get Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("TimeTableModel checkByExamTime() Method Ended");
		return bean;
	}

}