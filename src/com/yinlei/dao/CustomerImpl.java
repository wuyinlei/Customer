package com.yinlei.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yinlei.bean.Customer;
import com.yinlei.utils.JdbcUtils;

public class CustomerImpl implements CustomerDao {

	@Override
	public boolean add(Customer customer) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		;
		PreparedStatement pstmt = null;
		int n = -1;
		// 创建sql语句
		String sql = "insert into customer (id,name,gender,birthday,cellphone,email,hobby,type,description) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		// 创建预处理对象
		try {
			pstmt = conn.prepareStatement(sql);
			// 指定问号的值
			pstmt.setString(1, customer.getId());
			pstmt.setString(2, customer.getName());
			pstmt.setString(3, customer.getGender());
			pstmt.setDate(4, new Date(customer.getBirthday().getTime()));
			pstmt.setInt(5, customer.getCellphone());
			pstmt.setString(6, customer.getEmail());
			pstmt.setString(7, customer.getHobby());
			pstmt.setString(8, customer.getType());
			pstmt.setString(9, customer.getDescription());
			// 执行sql语句
			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(null, pstmt, conn);
		}
		return n > 0 ? true : false;
	}

	@Override
	public boolean update(Customer customer) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		;
		PreparedStatement pstmt = null;
		int n = -1;
		// 创建sql语句
		String sql = "update customer set name=?,gender=?,birthday=?,cellphone=?,email=?,hobby=?,type=?,description=? where id = ?";

		// 创建预处理对象
		try {
			pstmt = conn.prepareStatement(sql);
			// 指定问号的值

			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getGender());
			pstmt.setDate(3, new Date(customer.getBirthday().getTime()));
			pstmt.setInt(4, customer.getCellphone());
			pstmt.setString(5, customer.getEmail());
			pstmt.setString(6, customer.getHobby());
			pstmt.setString(7, customer.getType());
			pstmt.setString(8, customer.getDescription());
			pstmt.setString(9, customer.getId());
			// 执行sql语句
			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(null, pstmt, conn);
		}
		return n > 0 ? true : false;
	}

	@Override
	public boolean delete(String id) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		;
		PreparedStatement pstmt = null;
		int n = -1;
		// 创建sql语句
		String sql = "delete from customer where id = ?";

		// 创建预处理对象
		try {
			pstmt = conn.prepareStatement(sql);
			// 指定问号的值

			pstmt.setString(1, id);

			n = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(null, pstmt, conn);
		}
		return n > 0 ? true : false;
	}

	@Override
	public List<Customer> getAllCustomer() {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Customer> list = new ArrayList<>();
		int n = -1;
		// 创建sql语句
		String sql = "select id,name,gender,birthday,cellphone,email,hobby,type,description from customer";

		// 创建预处理对象
		try {
			pstmt = conn.prepareStatement(sql);
			// 指定问号的值

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer c = new Customer();
				try {
					String id = URLEncoder.encode(rs.getString("id"), "utf-8");
					// 服了自己啊，这个已经转码了，但是自己存储的时候存储的不是转过码，导致长时间在这个bug下困扰，要细心啊
					c.setId(id);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setCellphone(rs.getInt("cellphone"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));
				list.add(c);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public Customer findCustomerById(String id) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int n = -1;
		// 创建sql语句
		String sql = "select id ,name,gender,birthday,cellphone,email,hobby,type,description from customer where id='"
				+ id + "'";

		// 创建预处理对象
		try {
			pstmt = conn.prepareStatement(sql);
			// 指定问号的值

			rs = pstmt.executeQuery();
			if (rs.next()) {
				Customer c = new Customer();
				c.setId(rs.getString("id"));
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setCellphone(rs.getInt("cellphone"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));
				return c;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public List<Customer> getPageList(int currentPageIndex, int count) {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		// 创建预处理命令对象
		try {
			pstmt = conn.prepareStatement(
					"select id,name,gender,birthday,cellphone,email,hobby,type,description from customer order by cellphone limit ?,?");
			// 指定?的值
			pstmt.setInt(1, (currentPageIndex - 1) * count);
			pstmt.setInt(2, count);
			// 执行sql语句
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 封装数据
				Customer c = new Customer();
				try {
					String id = URLEncoder.encode(rs.getString("id"), "UTF-8");
					c.setId(id);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.setName(rs.getString("name"));
				c.setGender(rs.getString("gender"));
				c.setBirthday(rs.getDate("birthday"));
				c.setCellphone(rs.getInt("cellphone"));
				c.setEmail(rs.getString("email"));
				c.setHobby(rs.getString("hobby"));
				c.setType(rs.getString("type"));
				c.setDescription(rs.getString("description"));

				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		// 拿到连接对象
		Connection conn = JdbcUtils.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 创建预处理命令对象
		try {
			pstmt = conn.prepareStatement("select count(*) from customer");

			// 执行sql语句
			rs = pstmt.executeQuery();
			if (rs.next()) // 执行第一条记录
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, pstmt, conn);
		}
		return 0;
	}

}
