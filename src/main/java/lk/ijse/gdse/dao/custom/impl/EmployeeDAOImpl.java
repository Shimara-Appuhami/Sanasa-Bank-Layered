package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.EmployeeDAO;
import lk.ijse.gdse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee (e_id, e_name, e_contact, e_address, e_salary, position) VALUES (?, ?, ?, ?, ?, ?)", employee.getEmployeeId(),employee.getEmployeeName(), employee.getEmployeeContact(),employee.getEmployeeAddress(),employee.getEmployeeSalary(),employee.getPosition());
    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "UPDATE employee SET e_name=?, e_contact=?, e_address=?, e_salary=?, position=? WHERE e_id=?",   employee.getEmployeeId(),employee.getEmployeeName(), employee.getEmployeeContact(),employee.getEmployeeAddress(),employee.getEmployeeSalary(),employee.getPosition());
    }

    @Override
    public boolean delete(String eId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE e_id = ?",eId);
    }

    @Override
    public Employee searchById(String eId) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT * FROM employee WHERE e_id=?");
        if(rst.next()){
            return new Employee(rst.getString("employeeId"), rst.getString("employeeName"), rst.getString("employeeContact"), rst.getString("employeeAddress"), rst.getString("employeeSalary"), rst.getString("position"));
        }
        return null;

    }

    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString("e_id"),
                    rst.getString("e_name"),
                    rst.getString("e_contact"),
                    rst.getString("e_address"),
                    rst.getString("e_salary"),
                    rst.getString("position"));
            allEmployee.add(employee);
        }
        return allEmployee;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT e_id FROM employee ORDER BY e_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("e_id");
            int newCustomerId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E%03d", newCustomerId);
        } else {
            return "E001";
        }
    }



    @Override
    public Employee searchByNic(String nic) throws SQLException, ClassNotFoundException {
        return null;
    }


}
