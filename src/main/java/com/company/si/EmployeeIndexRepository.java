package com.company.si;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * EmployeeIndex Interface
 * 
 * Basically just a typedef of a MongoRepository<Employee, Integer>. Spring did
 * not like using MongoRepository<> directly.
 * 
 * Spring was also having trouble finding it as a nested class. This was the
 * quickest work-around. Plus more function templates can be added if desired.
 * 
 * MongoRepository extends CrudRepository for basic CRUD functionality.
 * 
 * @author Brian Hession
 *
 */
public interface EmployeeIndexRepository extends MongoRepository<EmployeeIndex, Integer> {

}