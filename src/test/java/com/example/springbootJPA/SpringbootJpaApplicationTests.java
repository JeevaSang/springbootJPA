package com.example.springbootJPA;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springbootJPA.model.Employee;
import com.example.springbootJPA.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaApplicationTests {

	@Autowired
	private EmployeeRepository repo;

	//@Before
	public void setUp() throws Exception {
		Employee employee = new Employee();
		employee.setFirstName("jeeva");
		employee.setLastName("sang");
		Employee employee1 = new Employee();
		employee1.setFirstName("dev");
		employee1.setLastName("s");
		List<Employee> listEmp = new ArrayList<Employee>();
		listEmp.add(employee);
		listEmp.add(employee1);
		repo.saveAll(listEmp);
	}
	
	//@Test
	public void testcase01() {
		assertEquals("Number of Employees in Database", 2, repo.count());
		Optional<Employee> emp1 = repo.findById((long) 2);
		//Optional<Employee> emp2 = repo.findById((long) 2);
		emp1.get().setLastName("SSK");
		//emp2.get().setLastName("RRR");
		assertEquals(0, emp1.get().getVersion().intValue());
		//assertEquals(0, emp2.get().getVersion().intValue());
		// The backend tries to save both.
		repo.save(emp1.get());
		// OUTCH! Exception!
		//repo.save(emp2.get());
	}
	
	//@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testcase02() {
		assertEquals("Number of Employees in Database", 2, repo.count());
		Optional<Employee> emp1 = repo.findById((long) 2);
		emp1.get().setVersion(0);
		repo.save(emp1.get());
		System.out.println("Done");
	}
	
	
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testcase003optimisticLockingTest() throws Exception {

		Optional<Employee> employee1 = repo.findById((long) 2);
		Optional<Employee> employee2 = repo.findById((long) 2);

		System.out.println(employee1.get());
		System.out.println(employee2.get());

		employee1.get().setFirstName("rai");
		System.out.println("Employee1---"+employee1.get().getVersion());
		if (repo.save(employee1.get()) != null) {
			System.out.println("Rai update succeeded.");
		} else {
			System.out.println("Rai update failed");
		}
		System.out.println("Employee2---"+employee2.get().getVersion());
		employee2.get().setFirstName("shetty");
		if (repo.save(employee2.get()) != null) { // Will be updated if without optimistic locking,
			System.out.println("Shetty update succeeded."); // since it's put after employee1.
		} else {
			System.out.println("Shetty update failed.");
		}

		Optional<Employee> employee3 = repo.findById((long) 2);
		System.out.println(employee3.get());
		assertEquals(employee3.get().getFirstName(), "rai");
	}

}
