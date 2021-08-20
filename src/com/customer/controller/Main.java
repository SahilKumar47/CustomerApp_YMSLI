package com.customer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.customer.dao.CustomerDao;
import com.customer.dao.CustomerDaoImplJdbc;
import com.customer.entities.Customer;

public class Main {
	public static void main(String[] args) {
		CustomerDao dao = new CustomerDaoImplJdbc();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose option 1. Add Customer 2. Print Customer by ID ");
		int val = scanner.nextInt();
		switch (val) {
		// Add customer
		case 1:
			System.out.println(
					"Enter the details of the book: Name, Address,Date of Birth in (dd/mm/yyyy) and Phone number");
			String name = scanner.next();
			String address = scanner.next();
			String sDate = scanner.next();
			int phoneNo = scanner.nextInt();
			Date dob = null;
			try {
				dob = new SimpleDateFormat("dd/MM/yyyy"). parse(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dao.addCustomer(new Customer(name, address, phoneNo, dob));
			System.out.println("Customer Added Successfully");
			break;
		case 2:
			//get customer by id
			System.out.println("Enter Customer ID");
			int id = scanner.nextInt();
			System.out.println(dao.getCustomerById(id).toString());
			break;
		default:
			System.out.println("Wrong choice!");
			break;
		}
		scanner.close();
	}
}
