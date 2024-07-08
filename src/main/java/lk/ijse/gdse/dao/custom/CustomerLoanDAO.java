package lk.ijse.gdse.dao.custom;

import lk.ijse.gdse.dao.CrudDAO;
import lk.ijse.gdse.entity.CustomerLoan;

import java.sql.SQLException;

public interface CustomerLoanDAO extends CrudDAO<CustomerLoan> {
    CustomerLoan searchByNic(String nic) throws SQLException, ClassNotFoundException;
}
