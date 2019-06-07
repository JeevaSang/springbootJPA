package com.example.springbootJPA.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the employees database table.
 * 
 */
@Entity
@Table(name = "employees")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", unique = true, nullable = false)
	private Long employeeId;

	@Column(name = "first_name", length = 2147483647)
	private String firstName;

	@Column(name = "last_name", length = 2147483647)
	private String lastName;

	@Version
	private Integer version;

	public Employee() {
	}

	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}