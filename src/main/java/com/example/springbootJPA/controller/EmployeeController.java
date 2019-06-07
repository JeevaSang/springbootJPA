package com.example.springbootJPA.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootJPA.model.Employee;
import com.example.springbootJPA.repository.EmployeeRepository;

@RestController

public class EmployeeController {
	@Autowired
	EmployeeRepository repository;

	@GetMapping("/users")
	public ResponseEntity<List<Employee>> listAllEmployees() {
		List<Employee> employees = (List<Employee>) repository.findAll();
		if (employees.isEmpty())
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable long id) {
		Optional<Employee> employee = repository.findById(id);
		if (employee.isPresent())
			return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody List<Employee> employees) {
		repository.saveAll(employees);
		return new ResponseEntity<Employee>(HttpStatus.CREATED);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody Employee employee) {
		Optional<Employee> findEmployee = repository.findById(id);
		if (findEmployee.isPresent())
			findEmployee.get().setFirstName(employee.getFirstName());
		findEmployee.get().setLastName(employee.getLastName());
		repository.save(findEmployee.get());		
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
}
