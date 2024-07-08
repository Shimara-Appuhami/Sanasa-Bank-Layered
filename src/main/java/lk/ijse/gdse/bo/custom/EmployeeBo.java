package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBo extends SuperBO {
    boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    EmployeeDTO searchByIdEmployee(String id) throws SQLException, ClassNotFoundException;
    List<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    String generateNewEmployeeID() throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchByNicEmployee(String nic) throws SQLException, ClassNotFoundException;

}
