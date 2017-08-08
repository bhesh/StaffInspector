package com.company.si;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * SIRestController
 * 
 * The main entry point and defines the REST actions as well.
 * 
 * @author Brian Hession
 *
 */
@SpringBootApplication
@RestController
@RequestMapping("/staff")
public class SIRestController {

	/**
	 * Autowired for simplicity. Spring will create an implementation of the
	 * interface. This repository is used to interact with the MongoDB database.
	 */
	@Autowired
	private SIService service;

	/**
	 * createNewEmployee()
	 * 
	 * Creates a new employee.
	 * 
	 * URL Target: /staff Method: POST
	 * 
	 * Consumes a JSON object of any desired initial Employee fields. If _id is
	 * specified, it will be ignored as the employee ID is calculated as part of
	 * this function.
	 * 
	 * On success, it will respond with the newly created Employee object in a
	 * JSON format.
	 * 
	 * @param input
	 *            the employee fields to use during creation
	 * @return status 200 as well as the created employee on success. 5xx on
	 *         server error.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createNewEmployee(@RequestBody Employee input) {
		Employee employee = service.createNewEmployee(input);
		return ResponseEntity.ok(employee);
	}

	/**
	 * updateEmployee()
	 * 
	 * Updates an employee existing in the database.
	 * 
	 * URL Target: /staff/<employee-id> Method: PUT
	 * 
	 * Consumes a JSON object of the desired Employee fields to update. The _id
	 * field is ignored in the supplied JSON.
	 * 
	 * On success, it will respond with the newly updated Employee object in a
	 * JSON format. A 404 status code will be returned if the employee does not
	 * exist in the database.
	 * 
	 * @param employeeId
	 *            the employee ID to update
	 * @param input
	 *            the fields with which to update (_id ignored)
	 * @return status 200 as well as the updated employee on success. 404 if the
	 *         employee is not found. 5xx on server error.
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{employeeId}")
	public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee input) {
		Employee employee = service.updateEmployee(employeeId, input);
		return ResponseEntity.ok(employee);
	}

	/**
	 * getAllEmployees()
	 * 
	 * Retrieves all employees from the database.
	 * 
	 * URL Target: /staff Method: GET
	 * 
	 * On success, it will respond with the retrieved Employee objects in a JSON
	 * format.
	 * 
	 * @param employeeId
	 *            the employee ID to look up
	 * @return status 200 as well as the retrieved employees on success. 5xx on
	 *         server error.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllEmployees() {
		List<Employee> employees = service.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	/**
	 * getEmployee()
	 * 
	 * Retrieves an employee from the database.
	 * 
	 * URL Target: /staff/<employee-id> Method: GET
	 * 
	 * On success, it will respond with the retrieved Employee object in a JSON
	 * format.
	 * 
	 * @param employeeId
	 *            the employee ID to look up
	 * @return status 200 as well as the retrieved employee on success. 404 if
	 *         the employee is not found. 5xx on server error.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{employeeId}")
	public ResponseEntity<?> getEmployee(@PathVariable Integer employeeId) {
		Employee employee = service.getEmployee(employeeId);
		if (employee == null)
			throw new EmployeeNotFoundException();
		return ResponseEntity.ok(employee);
	}

	/**
	 * deleteEmployee()
	 * 
	 * Deletes an employee from the database.
	 * 
	 * URL Target: /staff/<employee-id> Method: DELETE
	 * 
	 * On success, it will respond with the deleted Employee object in a JSON
	 * format.
	 * 
	 * @param employeeId
	 *            the employee ID to delete
	 * @return status 200 as well as the deleted employee on success. 404 if the
	 *         employee is not found. 5xx on server error.
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{employeeId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
		Employee employee = service.deleteEmployee(employeeId);
		if (employee == null)
			throw new EmployeeNotFoundException();
		return ResponseEntity.ok(employee);
	}

	/**
	 * Defines an exception to throw when an Employee with the specified ID does
	 * not exist. SIErrorController will create the appropriate response.
	 * 
	 * @author Brian Hession
	 *
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee ID not found")
	public static class EmployeeNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 7703768712188805236L;
	}

	/**
	 * Main entry. Just starts the REST web service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SIRestController.class, args);
	}
}
