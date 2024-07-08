package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.InterestRateDTO;

import java.sql.SQLException;
import java.util.List;

public interface InterestRateBo extends SuperBO {
    boolean saveInterestRate(InterestRateDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateInterestRate(InterestRateDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteInterestRate(String id) throws SQLException, ClassNotFoundException;
    InterestRateDTO searchByIdInterestRate(String id) throws SQLException, ClassNotFoundException;
    List<InterestRateDTO> getAllInterestRates() throws SQLException, ClassNotFoundException;
    String generateNewInterestRateID() throws SQLException, ClassNotFoundException;
    public InterestRateDTO searchByNicCustomer(String nic) throws SQLException, ClassNotFoundException;

}

