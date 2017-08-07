package com.company.si;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Employee
 * 
 * Defines an employee and all the fields that are stored/retrieved from the
 * MongoDB database.
 * 
 * This employee object will be stored in a collection labeled 'staff'.
 * 
 * @author Brian Hession
 *
 */
@Document(collection = "staff")
public class Employee {

	@Id
	private int employeeId;
	private String firstname;
	private String lastname;
	private String middleinitial;
	private String email;
	private String phone;
	private String position;
	private Address address;
	private Date dateHired;

	public Employee() {
		employeeId = 0;
	}

	public Employee(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddleinitial() {
		return middleinitial;
	}

	public void setMiddleinitial(String middleinitial) {
		this.middleinitial = middleinitial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", middleinitial=" + middleinitial + ", email=" + email + ", phone=" + phone + ", position="
				+ position + ", address=" + address + ", dateHired=" + dateHired + "]";
	}
}
