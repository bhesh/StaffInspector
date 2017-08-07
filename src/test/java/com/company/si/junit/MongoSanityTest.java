package com.company.si.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.si.Employee;
import com.company.si.StaffRepository;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoSanityTest extends NoSQLUnitTest {

	@Autowired
	private StaffRepository staffRepo;

	@Test
	public void testBasicNew() {
		Employee employee = new Employee(0);
		Employee result = staffRepo.save(employee);
		if (result == null)
			Assert.fail();
		Assert.assertTrue(result.getEmployeeId() == 1);
	}
}
