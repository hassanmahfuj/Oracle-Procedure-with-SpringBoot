package dev.mahfuj.procedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/id/{empId}")
    public String getEmployeeName(@PathVariable("empId") int empId) {
        return employeeRepository.getEmployeeNameById(empId);
    }

    @GetMapping("/department/{departmentId}")
    public List<String> getEmployeesByDepartmentId(@PathVariable("departmentId") int departmentId) {
        return employeeRepository.getEmployeesByDepartmentId(departmentId);
    }

    @GetMapping("/all")
    public List<EmployeeDto> getEmployeeDetailsList() {
        return employeeRepository.getEmployeeDetailsList();
    }
}
