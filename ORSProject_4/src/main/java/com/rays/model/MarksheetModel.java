package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.MarksheetBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * MarksheetModel provides methods to perform CRUD operations on Marksheet data.
 * It interacts with the database using JDBC.
 * 
 * @author Aastik Sahu
 */
public class MarksheetModel {
	
	Logger log = Logger.getLogger(MarksheetModel.class);

	/**
	 * Gets the next primary key from the database.
	 * 
	 * @return next available primary key
	 * @throws DatabaseException if database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("MarksheetModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");
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
		
		log.debug("MarksheetModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Adds a new Marksheet record.
	 * 
	 * @param bean the MarksheetBean containing data
	 * @return primary key of inserted record
	 * @throws ApplicationException     if application error occurs
	 * @throws DuplicateRecordException if duplicate record exists
	 */
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("MarksheetModel add() Method Started");

		Connection conn = null;
		int pk = 0;
		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Marksheet Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getRollNo());
			pstmt.setLong(3, bean.getStudentId());
			pstmt.setString(4, bean.getName());
			pstmt.setInt(5, bean.getPhysics());
			pstmt.setInt(6, bean.getChemistry());
			pstmt.setInt(7, bean.getMaths());
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
			throw new ApplicationException("Exception : Exception in Add Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel add() Method Ended");
		return pk;

	}

	/**
	 * Updates an existing Marksheet record.
	 * 
	 * @param bean the MarksheetBean containing updated data
	 * @throws ApplicationException     if application error occurs
	 * @throws DuplicateRecordException if duplicate record exists
	 */
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("MarksheetModel update() Method Started");

		Connection conn = null;

		MarksheetBean existBean = findByRollNo(bean.getRollNo());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Marksheet is Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_marksheet set roll_no = ?, student_id = ?, name = ?, physics = ?, chemistry = ?, maths = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
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
			throw new ApplicationException("Exception in updating Marksheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel update() Method Ended");
	}

	/**
	 * Deletes a Marksheet record.
	 * 
	 * @param bean the MarksheetBean containing record ID
	 * @throws ApplicationException if application error occurs
	 */
	public void delete(MarksheetBean bean) throws ApplicationException {
		
		log.debug("MarksheetModel delete() Method Started");

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel delete() Method Ended");
	}

	/**
	 * Finds a Marksheet by primary key.
	 * 
	 * @param pk the primary key
	 * @return MarksheetBean if found, otherwise null
	 * @throws ApplicationException if application error occurs
	 */
	public MarksheetBean findByPk(long pk) throws ApplicationException {
		
		log.debug("MarksheetModel findByPk() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Marksheet byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a Marksheet by roll number.
	 * 
	 * @param roll_no the roll number
	 * @return MarksheetBean if found, otherwise null
	 * @throws ApplicationException if application error occurs
	 */
	public MarksheetBean findByRollNo(String roll_no) throws ApplicationException {
		
		log.debug("MarksheetModel findByRollNo() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no = ?");
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, roll_no);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Marksheet by Roll No");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel findByRollNo() Method Ended");
		return bean;
	}

	/**
	 * Searches Marksheet records based on criteria.
	 * 
	 * @param bean     the MarksheetBean with search criteria
	 * @param pageNo   page number for pagination
	 * @param PageSize number of records per page
	 * @return list of matching MarksheetBean
	 * @throws ApplicationException if application error occurs
	 */
	public List search(MarksheetBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("MarksheetModel search() Method Started");

		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" and roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getStudentId() > 0) {
				sql.append(" and student_id = " + bean.getStudentId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
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
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
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
			throw new ApplicationException("Exception : Exception in Search Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel search() Method Ended");
		return list;
	}

	/**
	 * Lists all Marksheet records.
	 * 
	 * @return list of all MarksheetBean
	 * @throws ApplicationException if application error occurs
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Gets the merit list (students who passed all subjects) sorted by total marks.
	 * 
	 * @param pageNo   page number for pagination
	 * @param pageSize number of records per page
	 * @return list of top scoring MarksheetBean
	 * @throws ApplicationException if application error occurs
	 */
	public List<MarksheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("MarksheetModel getMeritList() Method Started");

		ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
		StringBuffer sql = new StringBuffer(
				"select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_marksheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("MarksheetModel getMeritList() Method Ended");
		return list;
	}

}
