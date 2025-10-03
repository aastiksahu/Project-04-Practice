package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.bean.RoleBean;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model. Provides methods to perform CRUD
 * operations on Role entity.
 * 
 * @author Aastik Sahu
 */
public class RoleModel {
	
	Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * Gets the next primary key from database.
	 * 
	 * @return next PK value
	 * @throws DatabaseException if any database error occurs
	 */
	public Integer nextPk() throws DatabaseException {
		
		log.debug("RoleModel nextPk() Method Started");
		
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
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
		
		log.debug("RoleModel nextPk() Method Ended");
		return pk + 11111;
	}

	/**
	 * Adds a new Role into the database.
	 * 
	 * @param bean RoleBean containing role details
	 * @return primary key of newly inserted role
	 * @throws ApplicationException     if application error occurs
	 * @throws DuplicateRecordException if role name already exists
	 */
	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("RoleModel add() Method Started");
		
		Connection conn = null;
		int pk = 0;

		RoleBean duplicateRoleName = findByName(bean.getName());
		if (duplicateRoleName != null) {
			throw new DuplicateRecordException("Role Name Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,)");

			pstmt.setLong(1, nextPk());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());

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
			throw new ApplicationException("Exception : Exception in Add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel add() Method Ended");
		return pk;
	}

	/**
	 * Updates an existing role in the database.
	 * 
	 * @param bean RoleBean containing updated role details
	 * @throws ApplicationException     if application error occurs
	 * @throws DuplicateRecordException if role already exists with the same name
	 */
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("RoleModel update() Method Started");
		
		Connection conn = null;

		RoleBean beanExist = findByName(bean.getName());
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Role is Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());

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
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel update() Method Ended");
	}

	/**
	 * Deletes a role from the database.
	 * 
	 * @param bean RoleBean containing ID of the role to delete
	 * @throws ApplicationException if any application error occurs
	 */
	public void delete(RoleBean bean) throws ApplicationException {
		
		log.debug("RoleModel delete() Method Started");
		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel delete() Method Ended");
	}

	/**
	 * Finds a role by its primary key.
	 * 
	 * @param pk primary key of the role
	 * @return RoleBean object if found, otherwise null
	 * @throws ApplicationException if any application error occurs
	 */
	public RoleBean findByPk(long pk) throws ApplicationException {
		
		log.debug("RoleModel findByPk() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_role where id = ?");
		RoleBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Role byPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel findByPk() Method Ended");
		return bean;
	}

	/**
	 * Finds a role by its name.
	 * 
	 * @param Name name of the role
	 * @return RoleBean object if found, otherwise null
	 * @throws ApplicationException if any application error occurs
	 */
	public RoleBean findByName(String Name) throws ApplicationException {
		
		log.debug("RoleModel findByName() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_role where name = ?");
		RoleBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, Name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception is getting Role by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel findByName() Method Ended");
		return bean;
	}

	/**
	 * Searches roles with given criteria.
	 * 
	 * @param bean     RoleBean containing search parameters
	 * @param pageNo   current page number
	 * @param PageSize number of records per page
	 * @return list of RoleBean objects matching the criteria
	 * @throws ApplicationException if any application error occurs
	 */
	public List<RoleBean> search(RoleBean bean, int pageNo, int PageSize) throws ApplicationException {
		
		log.debug("RoleModel search() Method Started");
		
		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

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
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in Search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("RoleModel search() Method Ended");
		return list;
	}

	/**
	 * Returns list of all roles.
	 * 
	 * @return list of RoleBean
	 * @throws ApplicationException if any application error occurs
	 */
	public List<RoleBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
}
