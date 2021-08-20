package com.customer.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.customer.exception.DataAccessException;
import com.customer.exception.ResourceNotFoundException;
import com.customer.dao.factory.DBConnectionFactory;
import com.customer.entities.Customer;

public class CustomerDaoImplJdbc implements CustomerDao {
	private Connection connection = null;

	public CustomerDaoImplJdbc() {
		connection = DBConnectionFactory.getConnection();
	}

	@Override
	public Customer addCustomer(Customer customer) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"insert into customer(name, address, phoneNo, dob) values(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getAddress());
			pstmt.setInt(3, customer.getPhoneNo());
			pstmt.setDate(4, new Date(customer.getDob().getTime()));
			pstmt.executeUpdate();

			ResultSet rSet = pstmt.getGeneratedKeys();
			if (rSet.next()) {
				customer.setId(rSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Customer getCustomerById(int id) {
		Customer customer = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from customer where id=?");
			pstmt.setInt(1, id);

			ResultSet rSet = pstmt.executeQuery();
			if (rSet.next()) {
				customer = new Customer(rSet.getString("name"), rSet.getString("address"), rSet.getInt("phoneNo"),
						 rSet.getDate("dob"));
			} else {
				throw new ResourceNotFoundException("ID: " + id + " not found");
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.toString());
		}
		return customer;
	}

}
