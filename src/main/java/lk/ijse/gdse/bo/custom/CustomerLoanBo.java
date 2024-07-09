package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBO;
import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.CustomerLoan;
import lk.ijse.gdse.model.CustomerLoanDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerLoanBo extends SuperBO {
    public boolean saveCustomerLoan(List<CustomerLoanDTO> clList) throws SQLException, ClassNotFoundException;

}
