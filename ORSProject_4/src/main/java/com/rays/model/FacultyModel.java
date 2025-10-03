package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CollegeBean;
import com.rays.bean.CourseBean;
import com.rays.bean.FacultyBean;
import com.rays.bean.SubjectBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * FacultyModel is responsible for handling all database operations related to
 * Faculty entity such as add, update, delete, search, and findBy methods. It
 * connects with the database using JDBC and manages Faculty records.
 *
 * @author
 * @Author Name: Aastik Sahu
 */
public class FacultyModel {
	
	Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Gets the next primary key value for Faculty.
	 *
	 * @return next available primary key
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public Integer nextPk() throws ApplicationException, DatabaseException {
		
		log.debug("FacultyModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_faculty");
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
		
		log.debug("FacultyModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Adds a new Faculty record into the database.
	 *
	 * @param bean FacultyBean object containing faculty details
	 * @return generated primary key
	 * @throws ApplicationException
	 * @throws DuplicateRecordException if email already exists
	 */
	public long add(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("FacultyModel add() Method Started");
		
		CollegeBean collegeBean = new CollegeBean();
		CollegeModel collegeModel = new CollegeModel();

		CourseBean courseBean = new CourseBean();
		CourseModel courseModel = new CourseModel();

		SubjectBean subjectBean = new SubjectBean();
		SubjectModel subjectModel = new SubjectModel();

		Connection conn = null;
		int pk = 0;

		if (bean.getCollegeId() > 0) {
			collegeBean = collegeModel.findByPk(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getName());
		} else if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
			collegeBean = collegeModel.findByName(bean.getCollegeName());
			bean.setCollegeId(collegeBean.getId());
		}

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

		FacultyBean beanExist = findByEmail(bean.getEmail());
		if (beanExist != null) {
			throw new DuplicateRecordException("Email ID Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setString(7, bean.getEmail());
			pstmt.setLong(8, bean.getCollegeId());
			pstmt.setString(9, bean.getCollegeName());
			pstmt.setLong(10, bean.getCourseId());
			pstmt.setString(11, bean.getCourseName());
			pstmt.setLong(12, bean.getSubjectId());
			pstmt.setString(13, bean.getSubjectName());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("FacultyModel add() Method Ended");
		return pk;
	}

	/**
	 * Updates an existing Faculty record.
	 *
	 * @param bean FacultyBean object containing updated details
	 * @throws ApplicationException
	 * @throws DuplicateRecordException if email already exists
	 */
	public void update(FacultyBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("FacultyModel update() Method Started");
		
		CollegeBean collegeBean = new CollegeBean();
		CollegeModel collegeModel = new CollegeModel();

		CourseBean courseBean = new CourseBean();
		CourseModel courseModel = new CourseModel();

		SubjectBean subjectBean = new SubjectBean();
		SubjectModel subjectModel = new SubjectModel();

		if (bean.getCollegeId() > 0) {
			collegeBean = collegeModel.findByPk(bean.getCollegeId());
			bean.setCollegeName(collegeBean.getName());
		} else if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
			collegeBean = collegeModel.findByName(bean.getCollegeName());
			bean.setCollegeId(collegeBean.getId());
		}

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

		Connection conn = null;

		FacultyBean existBean = findByEmail(bean.getEmail());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Faculty email is Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_faculty set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, course_id = ?, course_name = ?, subject_id = ?, subject_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getGender());
			pstmt.setString(5, bean.getMobileNo());
			pstmt.setString(6, bean.getEmail());
			pstmt.setLong(7, bean.getCollegeId());
			pstmt.setString(8, bean.getCollegeName());
			pstmt.setLong(9, bean.getCourseId());
			pstmt.setString(10, bean.getCourseName());
			pstmt.setLong(11, bean.getSubjectId());
			pstmt.setString(12, bean.getSubjectName());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getId());

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
			throw new ApplicationException("Exception in updating Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel update() Method Ended");
	}

	/**
	 * Deletes a Faculty record from the database.
	 *
	 * @param bean FacultyBean containing the faculty ID to delete
	 * @throws ApplicationException
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		
		log.debug("FacultyModel delete() Method Started");
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_faculty where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("FacultyModel delete() Method Ended");
	}

	/**
	 * Finds a Faculty record by primary key.
	 *
	 * @param pk primary key
	 * @return FacultyBean object
	 * @throws ApplicationException
	 */
	public FacultyBean findByPk(long pk) throws ApplicationException {
		
		log.debug("FacultyModel findByPk() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_faculty where id = ?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Faculty byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("FacultyModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a Faculty record by email.
	 *
	 * @param email faculty email
	 * @return FacultyBean object
	 * @throws ApplicationException
	 */
	public FacultyBean findByEmail(String email) throws ApplicationException {
		
		log.debug("FacultyModel findByEmail() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_faculty where email = ?");
		FacultyBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Faculty by email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("FacultyModel findByEmail() Method Ended");
		return bean;
	}

	/**
	 * Searches Faculty records based on given criteria.
	 *
	 * @param bean     FacultyBean containing search filters
	 * @param pageNo   page number for pagination
	 * @param PageSize number of records per page
	 * @return List of FacultyBean
	 * @throws ApplicationException
	 */
	public List search(FacultyBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("FacultyModel search() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

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
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setMobileNo(rs.getString(6));
				bean.setEmail(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setCollegeName(rs.getString(9));
				bean.setCourseId(rs.getLong(10));
				bean.setCourseName(rs.getString(11));
				bean.setSubjectId(rs.getLong(12));
				bean.setSubjectName(rs.getString(13));
				bean.setCreatedBy(rs.getString(14));
				bean.setModifiedBy(rs.getString(15));
				bean.setCreatedDatetime(rs.getTimestamp(16));
				bean.setModifiedDatetime(rs.getTimestamp(17));

				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Facultys");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("FacultyModel search() Method Ended");
		return list;
	}

	/**
	 * Lists all Faculty records.
	 *
	 * @return List of FacultyBean
	 * @throws Exception
	 */
	public List list() throws Exception {
		return search(null, 0, 0);
	}
}
