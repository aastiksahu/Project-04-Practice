package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.CollegeBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * CollegeModel handles all database operations related to the College entity.
 * It includes operations such as add, update, delete, find, and search.
 * <p>
 * This model interacts with the {@code st_college} table in the database.
 * </p>
 * 
 * @author Aastik Sahu
 */
public class CollegeModel {

	Logger log = Logger.getLogger(CollegeModel.class);

	/**
	 * Returns the next primary key value for the College table.
	 * 
	 * @return next primary key value
	 * @throws DatabaseException if a database access error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		log.debug("CollegeModel nextPk() Method Started");

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
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

		log.debug("CollegeModel nextPk() Method Ended");
		return pk + 1;
	}

	/**
	 * Adds a new College record to the database.
	 * 
	 * @param bean the CollegeBean containing data to insert
	 * @return the primary key of the newly inserted record
	 * @throws ApplicationException     if an application-level exception occurs
	 * @throws DuplicateRecordException if a duplicate college name exists
	 */
	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("CollegeModel add() Method Started");
		
		Connection conn = null;
		int pk = 0;

		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("College Name Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("CollegeModel add() Method Ended");
		return pk;
	}

	/**
	 * Updates an existing College record.
	 * 
	 * @param bean the CollegeBean containing updated data
	 * @throws ApplicationException     if an application-level exception occurs
	 * @throws DuplicateRecordException if a duplicate college name exists
	 */
	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("CollegeModel update() Method Started");
		
		Connection conn = null;

		CollegeBean existBean = findByName(bean.getName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("College is Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

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
			throw new ApplicationException("Exception in updating College ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CollegeModel update() Method Ended");
	}

	/**
	 * Deletes a College record by its ID.
	 * 
	 * @param bean the CollegeBean containing the ID of the record to delete
	 * @throws ApplicationException if an application-level exception occurs
	 */
	public void delete(CollegeBean bean) throws ApplicationException {
		
		log.debug("CollegeModel delete() Method Started");
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CollegeModel delete() Method Ended");
	}

	/**
	 * Finds a College by primary key.
	 * 
	 * @param pk the primary key
	 * @return the matching CollegeBean or null if not found
	 * @throws ApplicationException if an application-level exception occurs
	 */
	public CollegeBean findByPk(long pk) throws ApplicationException {
		
		log.debug("CollegeModel findByPk() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_college where id = ?");
		CollegeBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting College byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CollegeModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a College by name.
	 * 
	 * @param name the college name
	 * @return the matching CollegeBean or null if not found
	 * @throws ApplicationException if an application-level exception occurs
	 */
	public CollegeBean findByName(String name) throws ApplicationException {
		
		log.debug("CollegeModel findByName() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_college where name = ?");
		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting College by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CollegeModel findByName() Method Ended");
		return bean;
	}

	/**
	 * Searches for Colleges based on search criteria with pagination support.
	 * 
	 * @param bean     the search criteria
	 * @param pageNo   the page number (1-based)
	 * @param pageSize the number of records per page
	 * @return list of CollegeBean matching the criteria
	 * @throws ApplicationException if an application-level exception occurs
	 */
	public List search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		log.debug("CollegeModel search() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" and address like '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" and state like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" and city like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" and phone_no like '" + bean.getPhoneNo() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql.toString());
		List list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CollegeModel search() Method Ended");
		return list;
	}

	/**
	 * Returns the list of all Colleges.
	 * 
	 * @return list of all CollegeBeans
	 * @throws ApplicationException if an application-level exception occurs
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

}
