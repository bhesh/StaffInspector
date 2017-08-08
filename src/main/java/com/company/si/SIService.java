package com.company.si;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SIService {

	/**
	 * Autowired for simplicity. Spring will create an implementation of the
	 * interface. This repository is used to interact with the MongoDB database.
	 */
	@Autowired
	private StaffRepository staffRepo;

	@Autowired
	private StaffIndexRepository eiRepo;

	/**
	 * createNewEmployee()
	 * 
	 * Creates a new employee.
	 * 
	 * @param input
	 *            the employee fields to use during creation
	 * @return the employee saved in the database or null
	 */
	public Employee createNewEmployee(Employee input) {
		StaffIndex ei = eiRepo.findOne(StaffIndex.EMPLOYEE_INDEX_ID);
		if (ei == null)
			ei = new StaffIndex(StaffIndex.EMPLOYEE_INDEX_ID, 0);
		input.setEmployeeId(ei.getNextIndex());
		eiRepo.save(ei);
		Employee employee = staffRepo.save(input);
		return employee;
	}

	/**
	 * updateEmployee()
	 * 
	 * Updates an employee existing in the database.
	 * 
	 * @param employeeId
	 *            the employee ID to update
	 * @param input
	 *            the fields with which to update (_id ignored)
	 * @return the updated employee, or null on error
	 */
	public Employee updateEmployee(Integer employeeId, Employee input) {
		Employee employee = staffRepo.findOne(employeeId);
		if (employee == null)
			return null;
		if (input.getAddress() != null)
			employee.setAddress(input.getAddress().clone());
		if (input.getDateHired() != null)
			employee.setDateHired(new Date(input.getDateHired().getTime()));
		if (input.getEmail() != null)
			employee.setEmail(input.getEmail());
		if (input.getFirstname() != null)
			employee.setFirstname(input.getFirstname());
		if (input.getLastname() != null)
			employee.setLastname(input.getLastname());
		if (input.getMiddleinitial() != null)
			employee.setMiddleinitial(input.getMiddleinitial());
		if (input.getPhone() != null)
			employee.setPhone(input.getPhone());
		if (input.getPosition() != null)
			employee.setPosition(input.getPosition());
		employee = staffRepo.save(employee);
		return employee;
	}

	/**
	 * getAllEmployees()
	 * 
	 * Retrieves all employees from the database.
	 * 
	 * @param employeeId
	 *            the employee ID to look up
	 * @return a list of the retrieved employees
	 */
	public List<Employee> getAllEmployees() {
		return staffRepo.findAll();
	}

	/**
	 * getEmployee()
	 * 
	 * Retrieves an employee from the database.
	 * 
	 * @param employeeId
	 *            the employee ID to look up
	 * @return the retrieved employee or null if not found
	 */
	public Employee getEmployee(Integer employeeId) {
		return staffRepo.findOne(employeeId);
	}

	/**
	 * deleteEmployee()
	 * 
	 * Deletes an employee from the database.
	 * 
	 * @param employeeId
	 *            the employee ID to delete
	 * @return the deleted employee or null if not found
	 */
	public Employee deleteEmployee(Integer employeeId) {
		Employee employee = staffRepo.findOne(employeeId);
		if (employee == null)
			return null;
		staffRepo.delete(employee);
		return employee;
	}
}
