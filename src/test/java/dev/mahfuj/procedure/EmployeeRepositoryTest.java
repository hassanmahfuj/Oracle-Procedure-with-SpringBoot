package dev.mahfuj.procedure;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployeeNameById() {
        int empId = 200;
        String empName = employeeRepository.getEmployeeNameById(empId);

        assertEquals("Douglas Grant", empName);
    }
}
