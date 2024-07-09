package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.CustomerLoan;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.CustomerLoanDTO;
import lk.ijse.gdse.model.LoanDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerLoanDAO extends CrudDAO<CustomerLoan> {
    CustomerLoan searchByNic(String nic) throws SQLException, ClassNotFoundException;

    public boolean saveCustomerLoan(List<CustomerLoanDTO> clList) throws SQLException, ClassNotFoundException;

}