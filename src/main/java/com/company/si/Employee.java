package com.company.si;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateHired;
	private Boolean active;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", middleinitial=" + middleinitial + ", email=" + email + ", phone=" + phone + ", position="
				+ position + ", address=" + address + ", dateHired=" + dateHired + "]";
	}

	private boolean checkNulls(Employee other) {
		if (this.getAddress() == null || other.getAddress() == null)
			if (this.getAddress() != other.getAddress())
				return false;
		if (this.getDateHired() == null || other.getDateHired() == null)
			if (this.getDateHired() != other.getDateHired())
				return false;
		if (this.getEmail() == null || other.getEmail() == null)
			if (this.getEmail() != other.getEmail())
				return false;
		if (this.getFirstname() == null || other.getFirstname() == null)
			if (this.getFirstname() != other.getFirstname())
				return false;
		if (this.getLastname() == null || other.getLastname() == null)
			if (this.getLastname() != other.getLastname())
				return false;
		if (this.getMiddleinitial() == null || other.getMiddleinitial() == null)
			if (this.getMiddleinitial() != other.getMiddleinitial())
				return false;
		if (this.getPhone() == null || other.getPhone() == null)
			if (this.getPhone() != other.getPhone())
				return false;
		if (this.getPosition() == null || other.getPosition() == null)
			if (this.getPosition() != other.getPosition())
				return false;
		if (this.getActive() == null || other.getActive() == null)
			if (this.getActive() != other.getActive())
				return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee other = (Employee) obj;
			if (!this.checkNulls(other))
				return false;
			return (this.getAddress() == null || this.getAddress().equals(other.getAddress()))
					&& (this.getDateHired() == null || this.getDateHired().equals(other.getDateHired()))
					&& (this.getEmail() == null || this.getEmail().equals(other.getEmail()))
					&& (this.getFirstname() == null || this.getFirstname().equals(other.getFirstname()))
					&& (this.getLastname() == null || this.getLastname().equals(other.getLastname()))
					&& (this.getMiddleinitial() == null || this.getMiddleinitial().equals(other.getMiddleinitial()))
					&& (this.getPhone() == null || this.getPhone().equals(other.getPhone()))
					&& (this.getPosition() == null || this.getPosition().equals(other.getPosition()))
					&& (this.getActive() == null || this.getActive() == other.getActive())
					&& this.getEmployeeId() == other.getEmployeeId();
		}
		return false;
	}
}
