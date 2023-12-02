package dev.mahfuj.procedure;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class EmployeeDto {
    int employeeId;
    String fullName;
    Date hireDate;
    String departmentName;

    public EmployeeDto(int employeeId, String fullName, Timestamp hireDate, String departmentName) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.hireDate = new Date(hireDate.getTime());
        this.departmentName = departmentName;
    }
}
