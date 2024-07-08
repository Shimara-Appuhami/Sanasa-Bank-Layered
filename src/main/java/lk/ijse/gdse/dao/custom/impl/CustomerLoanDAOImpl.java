package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.SQLUtil;
import lk.ijse.gdse.dao.custom.CustomerLoanDAO;
import lk.ijse.gdse.entity.CustomerLoan;

import java.sql.SQLException;
import java.util.List;

public class CustomerLoanDAOImpl implements CustomerLoanDAO {
    @Override
    public boolean save(CustomerLoan dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer_loan_details (loan_id, c_id) VALUES (?, ?)",dto.getLoanId(),dto.getCustomerId());
    }

    @Override
    public boolean update(CustomerLoan dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cId) throws SQLException, ClassNotFoundException {
    return false;
    }

    @Override
    public CustomerLoan searchById(String cId) throws SQLException, ClassNotFoundException {
       return null;
    }

    @Override
    public List<CustomerLoan> getAll() throws SQLException, ClassNotFoundException {
       return null;
    }



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
      return null;
    }



    @Override
    public CustomerLoan searchByNic(String nic) throws SQLException, ClassNotFoundException {
       return null;
    }

}
