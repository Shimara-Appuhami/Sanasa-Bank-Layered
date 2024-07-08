package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.custom.EmployeeBo;
import lk.ijse.gdse.dao.DAOFactory;
import lk.ijse.gdse.dao.custom.EmployeeDAO;
import lk.ijse.gdse.entity.Employee;
import lk.ijse.gdse.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getEmployeeId(), dto.getEmployeeName(), dto.getEmployeeContact(), dto.getEmployeeAddress(), dto.getEmployeeSalary(), dto.getPosition()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee( dto.getEmployeeName(), dto.getEmployeeContact(), dto.getEmployeeAddress(), dto.getEmployeeSalary(), dto.getPosition(),dto.getEmployeeId()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchByIdEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee dto = employeeDAO.searchById(id);
        if (dto != null) {
            return new EmployeeDTO(dto.getEmployeeId(), dto.getEmployeeName(), dto.getEmployeeContact(), dto.getEmployeeAddress(), dto.getEmployeeSalary(), dto.getPosition());
        }
        return null;
    }



    @Override
    public List<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        List<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee dto : employees) {
            employeeDTOS.add(new EmployeeDTO(dto.getEmployeeId(), dto.getEmployeeName(), dto.getEmployeeContact(), dto.getEmployeeAddress(), dto.getEmployeeSalary(), dto.getPosition()));
        }
        return employeeDTOS;
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }

    @Override
    public EmployeeDTO searchByNicEmployee(String nic) throws SQLException, ClassNotFoundException {
        return null;
    }

}
