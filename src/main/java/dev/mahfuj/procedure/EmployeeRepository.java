package dev.mahfuj.procedure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    @Autowired
    private EntityManager entityManager;

    /*
    CREATE OR REPLACE PROCEDURE GET_NAME_BY_ID (
      V_EMP_ID IN NUMBER,
      V_EMP_NAME OUT VARCHAR2
    ) AS
    BEGIN
      SELECT E.FIRST_NAME || ' ' || E.LAST_NAME INTO V_EMP_NAME
      FROM EMPLOYEES E
      WHERE E.EMPLOYEE_ID = V_EMP_ID;
    END GET_NAME_BY_ID;
     */
    public String getEmployeeNameById(int empId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GET_NAME_BY_ID");

        storedProcedureQuery.registerStoredProcedureParameter("V_EMP_ID", Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("V_EMP_ID", empId);

        storedProcedureQuery.registerStoredProcedureParameter("V_EMP_NAME", String.class, ParameterMode.OUT);

        storedProcedureQuery.execute();

        String empName = (String) storedProcedureQuery.getOutputParameterValue("V_EMP_NAME");

        return empName;
    }

    /*
    CREATE OR REPLACE PROCEDURE GET_EMPLOYEES_BY_DEP_ID (
      V_DEP_ID IN NUMBER,
      V_EMPLOYEE_NAMES OUT SYS_REFCURSOR
    ) AS
    BEGIN
      OPEN V_EMPLOYEE_NAMES FOR
      SELECT E.FIRST_NAME || ' ' || E.LAST_NAME
      FROM EMPLOYEES E
      WHERE E.DEPARTMENT_ID = V_DEP_ID;
    END GET_EMPLOYEES_BY_DEP_ID;
     */
    public List<String> getEmployeesByDepartmentId(int departmentId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GET_EMPLOYEES_BY_DEP_ID");

        storedProcedureQuery.registerStoredProcedureParameter("V_DEP_ID", Integer.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("V_DEP_ID", departmentId);

        storedProcedureQuery.registerStoredProcedureParameter("V_EMPLOYEE_NAMES", String.class, ParameterMode.REF_CURSOR);

        storedProcedureQuery.execute();

        List<String> empNames = storedProcedureQuery.getResultList();

        return empNames;
    }

    /*
    CREATE OR REPLACE PROCEDURE GET_EMPLOYEE_DETAILS_LIST (
        V_EMP_DETAILS OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN V_EMP_DETAILS FOR
        SELECT EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME AS FULL_NAME, HIRE_DATE, DEPARTMENT_NAME
        FROM EMPLOYEES E
        JOIN DEPARTMENTS D ON E.DEPARTMENT_ID = D.DEPARTMENT_ID;
    END GET_EMPLOYEE_DETAILS_LIST;
     */
    public List<EmployeeDto> getEmployeeDetailsList() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GET_EMPLOYEE_DETAILS_LIST");

        storedProcedureQuery.registerStoredProcedureParameter("V_EMP_DETAILS", void.class, ParameterMode.REF_CURSOR);

        storedProcedureQuery.execute();

        List<Object[]> resultList = storedProcedureQuery.getResultList();

        return mapResultToEmployeeDetailsList(resultList);
    }

    private List<EmployeeDto> mapResultToEmployeeDetailsList(List<Object[]> resultList) {

        return resultList.stream()
                .map(row -> new EmployeeDto(
                        (int) row[0],
                        (String) row[1],
                        (java.sql.Timestamp) row[2],
                        (String) row[3]
                ))
                .toList();
    }
}
