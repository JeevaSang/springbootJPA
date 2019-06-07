package com.example.springbootJPA.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootJPA.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
